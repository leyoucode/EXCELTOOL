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

	private static String DRIVER_CLASS;// ������
	private static String DATABASE_URL;// ����URL
	private static String DATABASE_USER;// ���ݿ��û���
	private static String DATABASE_PASS;// ���ݿ�����

	private static String LOGFILE; // ��־�ļ�

	private static String XMLFILE = "etc/config.xml";// �����ļ�

	private static Element ROOT;// �����ļ����ڵ�

	private static ExcelConfig excelConfig = new ExcelConfig();

	static {
		SAXBuilder sb = new SAXBuilder();
		try {
			Document doc = sb.build(new File(XMLFILE));
			ROOT = doc.getRootElement(); // ���ڵ�
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// ��ȡ��������
		Element base = ROOT.getChild("base_config");
		// ��ȡĿ¼������Ϣ
		LOGFILE = base.getChildText("log_file"); // ��ȡ��Ŀ¼

		// ��ȡ���ݿ�������Ϣ
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
		
		//����Excel �� sheet
		List<SheetConfig> sheets = new ArrayList<SheetConfig>();
		List<Element>  sheetElements = base.getChildren("sheet");
		for(Element sheetElement : sheetElements){
			SheetConfig sc = new SheetConfig();
			sc.setIndex(sheetElement.getAttributeValue("index"));
			sc.setTable(sheetElement.getAttributeValue("table"));
			sc.setClassName(sheetElement.getAttributeValue("className"));
			sc.setSheetName(sheetElement.getAttributeValue("sheetName"));
			sc.setAutoCommitRowIndex(sheetElement.getAttributeValue("autoCommitRowIndex"));
			//����Excel �� sheet �� column
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
