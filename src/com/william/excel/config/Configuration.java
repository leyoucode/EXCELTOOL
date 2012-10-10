package com.william.excel.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class Configuration {

	private static String DRIVER_CLASS;// 驱动类
	private static String DATABASE_URL;// 连接URL
	private static String DATABASE_USER;// 数据库用户名
	private static String DATABASE_PASS;// 数据库密码

	private static String LOGFILE; // 日志文件

	private static String XMLFILE = "etc/config.xml";// 配置文件

	private static Element ROOT;// 配置文件根节点

	private static ExcelConfig excelConfig = new ExcelConfig();

	static {
		SAXBuilder sb = new SAXBuilder();
		try {
			Document doc = sb.build(new File(XMLFILE));
			ROOT = doc.getRootElement(); // 根节点
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 获取基础配置
		Element base = ROOT.getChild("base_config");
		// 读取目录配置信息
		LOGFILE = base.getChildText("log_file"); // 获取家目录

		// 读取数据库配置信息
		setDRIVER_CLASS();
		setDATABASE_URL();
		setDATABASE_USER();
		setDATABASE_PASS();
		
		setExcelConfig();
	}

	public static void setDRIVER_CLASS() {
		Element base = ROOT.getChild("db_conection");
		DRIVER_CLASS = base.getChildText("driver_class").trim();
	}

	public static void setDATABASE_URL() {
		Element base = ROOT.getChild("db_conection");
		DATABASE_URL = base.getChildText("url").trim();
	}

	public static void setDATABASE_USER() {
		Element base = ROOT.getChild("db_conection");
		DATABASE_USER = base.getChildText("user").trim();
	}

	public static void setDATABASE_PASS() {
		Element base = ROOT.getChild("db_conection");
		DATABASE_PASS = base.getChildText("pass").trim();
	}

	@SuppressWarnings("unchecked")
	public static void setExcelConfig() {
		Element base = ROOT.getChild("Excel");
		excelConfig.setPath(base.getAttributeValue("path"));
		
		//设置Excel → sheet
		List<SheetConfig> sheets = new ArrayList<SheetConfig>();
		List<Element>  sheetElements = base.getChildren("sheet");
		for(Element sheetElement : sheetElements){
			SheetConfig sc = new SheetConfig();
			sc.setIndex(sheetElement.getAttributeValue("index"));
			sc.setTable(sheetElement.getAttributeValue("table"));
			sc.setClassName(sheetElement.getAttributeValue("className"));
			sc.setSheetName(sheetElement.getAttributeValue("sheetName"));
			sc.setAutoCommitRowIndex(sheetElement.getAttributeValue("autoCommitRowIndex"));
			//设置Excel → sheet → column
			List<Element> columnElements =sheetElement.getChildren("column");
			List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
			for(Element columnElement : columnElements){
				ColumnConfig cc = new ColumnConfig();
				cc.setDatabase_column(columnElement.getAttributeValue("database_column"));
				cc.setField(columnElement.getAttributeValue("field"));
				cc.setFile_column_index(columnElement.getAttributeValue("file_column_index"));
				columns.add(cc);
			}
			sc.setColumns(columns);
			sheets.add(sc);
		}
			excelConfig.setSheets(sheets);
	}

	public static String getDRIVER_CLASS() {
		return DRIVER_CLASS;
	}

	public static String getDATABASE_URL() {
		return DATABASE_URL;
	}

	public static String getDATABASE_USER() {
		return DATABASE_USER;
	}

	public static String getDATABASE_PASS() {
		return DATABASE_PASS;
	}

	public static String getLOGFILE() {
		return LOGFILE;
	}

	public static ExcelConfig getExcelConfig() {
		return excelConfig;
	}

}
