package com.william.excel.test;

import com.william.excel.config.Configuration;
import com.william.excel.service.ReadExcel;

public class MainTest {
	public static void main(String[] args) {
	  ReadExcel re = new ReadExcel(Configuration.getExcelConfig().getPath());
	   re.run();
	}
}
