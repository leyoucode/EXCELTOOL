package com.william.excel.config;

import java.util.List;

public class ExcelConfig {

	private String path;
    private List<SheetConfig> sheets;
    
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<SheetConfig> getSheets() {
		return sheets;
	}
	public void setSheets(List<SheetConfig> sheets) {
		this.sheets = sheets;
	}
	
}
