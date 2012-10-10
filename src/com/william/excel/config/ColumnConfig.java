package com.william.excel.config;

public class ColumnConfig {

	private String field;
	private String database_column;
	private String file_column_index;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDatabase_column() {
		return database_column;
	}
	public void setDatabase_column(String database_column) {
		this.database_column = database_column;
	}
	public String getFile_column_index() {
		return file_column_index;
	}
	public void setFile_column_index(String file_column_index) {
		this.file_column_index = file_column_index;
	}
}
