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
		Log.writeLog("��ʼ��ȡ��" + path, true);

		File checkFile = new File(path);
		if (checkFile.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(path);
				POIFSFileSystem fs = new POIFSFileSystem(fis); // ����poi��ȡexcel�ļ���
				this.wb = new HSSFWorkbook(fs); // ��ȡexcel������
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
		for (SheetConfig sc : sheetConfigs) {// �ж��м���sheet��
			/** ***********������̬��Start**************** */
			String className = sc.getClassName();
			List<ColumnConfig> cc = sc.getColumns();
			String[] properties = new String[cc.size()];
			for (int i = 0; i < cc.size(); i++) {
				properties[i] = cc.get(i).getField();
			}
			// �������������Լ��ϴ�����̬��
			createExcelClass(className, properties);
			/** ***********������̬��End**************** */
			Class clazz = null;
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			// ��ѭ��,����ӵ�������
			HSSFSheet sheet = getSheet(Integer.parseInt(sc.getIndex()) - 1);
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				rowsCount = i;
				HSSFRow row = sheet.getRow(i); // ȡ��sheet�е�ĳһ������
				if (row != null) {
					Object obj = null;
					try {
						obj = clazz.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					// ��ѭ��
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
										Log.writeLog("�´����Ķ�̬��û�� set" + field
												+ " ����", true);
									} catch (IllegalAccessException e) {
										Log.writeLog("set" + field
												+ " ���������쳣,����ϵ����Ա��", true);
									} catch (InvocationTargetException e) {
										Log.writeLog("set" + field
												+ " ���������쳣,����ϵ����Ա��", true);
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
						Log.writeLog("��" + i + "�β�������ύ�������쳣,�п��ܵ������ݲ�׼ȷ��",
								true);
					}

				}

			}
			finishRead(sc.getSheetName(), sc.getTable(), rowsCount);
		}

	}

	private void finishRead(String sheetName, String tableName, int rowsCount) {
		Log.writeLog("��ȡ���:" + DateUtils.getLongSysDate(), true);
		Log.writeLog("��Excel��" + sheetName + "���������ݿ��" + tableName + "��" + "��"
				+ (rowsCount + 1) + "��������", true);
	}

	private void createExcelClass(String className, String[] properties) {
		CreateDynamicExcel dynamicExcel = new CreateDynamicExcel(className,
				properties);
		dynamicExcel.createClass();// ������̬��
		dynamicExcel.compileClass();// ���붯̬��
	}
}
