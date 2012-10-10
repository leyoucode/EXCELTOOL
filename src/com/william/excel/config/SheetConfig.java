package com.william.excel.config;

import java.util.List;

public class SheetConfig {

	private String className;
	private String sheetName;
	private String index;
	private String table;
	private String autoCommitRowIndex;
	private List<ColumnConfig> columns;
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public List<ColumnConfig> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnConfig> columns) {
		this.columns = columns;
	}
	public String getAutoCommitRowIndex() {
		return autoCommitRowIndex;
	}
	public void setAutoCommitRowIndex(String autoCommitRowIndex) {
		this.autoCommitRowIndex = autoCommitRowIndex;
	}
	
	
}
