package com.william.excel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.william.excel.config.ColumnConfig;
import com.william.excel.config.Configuration;
import com.william.excel.config.SheetConfig;
import com.william.excel.log.Log;
import com.william.excel.utils.CreateDynamicExcel;
import com.william.excel.utils.DateUtils;
import com.william.excel.utils.ExcelCellUtil;

public class ReadExcel {

	private HSSFWorkbook wb;

	private int rowsCount;

	public ReadExcel(String path) {
		// Log.writeLog("\r\n", true);
		Log.writeLog("-----------------" + DateUtils.getLongSysDate()
				+ "----------------", true);
		Log.writeLog("开始读取：" + path, true);

		File checkFile = new File(path);
		if (checkFile.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(path);
				POIFSFileSystem fs = new POIFSFileSystem(fis); // 利用poi读取excel文件流
				this.wb = new HSSFWorkbook(fs); // 读取excel工作簿
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private HSSFSheet getSheet(int sheetIndex) {
		return wb.getSheetAt(sheetIndex);
	}

	@SuppressWarnings("unchecked")
	public void run() {

		List<SheetConfig> sheetConfigs = Configuration.getExcelConfig()
				.getSheets();
		for (SheetConfig sc : sheetConfigs) {// 判断有几张sheet表
			/** ***********创建动态类Start**************** */
			String className = sc.getClassName();
			List<ColumnConfig> cc = sc.getColumns();
			String[] properties = new String[cc.size()];
			for (int i = 0; i < cc.size(); i++) {
				properties[i] = cc.get(i).getField();
			}
			// 根据类名和属性集合创建动态类
			createExcelClass(className, properties);
			/** ***********创建动态类End**************** */
			Class clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// 行循环,并添加到集合中
			HSSFSheet sheet = getSheet(Integer.parseInt(sc.getIndex()) - 1);
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				rowsCount = i;
				HSSFRow row = sheet.getRow(i); // 取出sheet中的某一行数据
				if (row != null) {
					Object obj = null;
					try {
						obj = clazz.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					// 列循环
					for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {

						HSSFCell cell = row.getCell((short) j);

						if (cell != null) {

							for (int x = 0; x < cc.size(); x++) {

								if (Integer.parseInt(cc.get(x)
										.getFile_column_index()) - 1 == j) {
									Class params[] = { String.class };
									Object paramsObj[] = new Object[1];
									Method thisMethod;
									String field = null;
									try {
										field = cc.get(x).getField();
										field = field.replace(field.substring(
												0, 1), field.substring(0, 1)
												.toUpperCase());
										thisMethod = clazz.getDeclaredMethod(
												"set" + field, params);
										paramsObj[0] = ExcelCellUtil
												.getCellStringValue(cell);
										thisMethod.invoke(obj, paramsObj);

									} catch (NoSuchMethodException e) {
										Log.writeLog("新创建的动态类没有 set" + field
												+ " 方法", true);
									} catch (IllegalAccessException e) {
										Log.writeLog("set" + field
												+ " 方法访问异常,请联系管理员。", true);
									} catch (InvocationTargetException e) {
										Log.writeLog("set" + field
												+ " 方法反射异常,请联系管理员。", true);
									}
								}
							}

						}
					}
					boolean isException = false;

					if(i==0){
						ExcelToData.dropTable(sc.getTable());
						ExcelToData.createTable(sc.getTable(), sc
								.getColumns());
					}
					if (i % Integer.parseInt(sc.getAutoCommitRowIndex()) == 0) {
						isException = ExcelToData.insert(sc.getTable(), sc
								.getColumns(), obj, true);
					} else if (i == sheet.getPhysicalNumberOfRows() - 1) {
						isException = ExcelToData.insert(sc.getTable(), sc
								.getColumns(), obj, true);
					} else {
						isException = ExcelToData.insert(sc.getTable(), sc
								.getColumns(), obj, false);
					}

					if (isException) {
						Log.writeLog(DateUtils.getLongSysDate() + " ERROR:",
								true);
						Log.writeLog("第" + i + "次插入或者提交事务发生异常,有可能导致数据不准确！",
								true);
					}

				}

			}
			finishRead(sc.getSheetName(), sc.getTable(), rowsCount);
		}

	}

	private void finishRead(String sheetName, String tableName, int rowsCount) {
		Log.writeLog("读取完毕:" + DateUtils.getLongSysDate(), true);
		Log.writeLog("从Excel【" + sheetName + "】导入数据库表【" + tableName + "】" + "【"
				+ (rowsCount + 1) + "】条数据", true);
	}

	private void createExcelClass(String className, String[] properties) {
		CreateDynamicExcel dynamicExcel = new CreateDynamicExcel(className,
				properties);
		dynamicExcel.createClass();// 创建动态类
		dynamicExcel.compileClass();// 编译动态类
	}
}
