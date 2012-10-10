package com.william.excel.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
/**
 * 该类是Excel的单元格类型
 * 对应于org.apache.poi.hssf.usermodel.HSSFCell中定义的类型
 * 将来可用来扩展
 * @author it2
 *
 */
public class ExcelCellType{

	public static final int CELL_TYPE_NUMERIC = HSSFCell.CELL_TYPE_NUMERIC;//数字格式 
	public static final int CELL_TYPE_STRING = HSSFCell.CELL_TYPE_STRING;//字符串（默认）
	public static final int CELL_TYPE_FORMULA = HSSFCell.CELL_TYPE_FORMULA;//公式
	public static final int CELL_TYPE_BLANK = HSSFCell.CELL_TYPE_BLANK;//空白
	public static final int CELL_TYPE_BOOLEAN = HSSFCell.CELL_TYPE_BOOLEAN;//布尔
	public static final int CELL_TYPE_ERROR = HSSFCell.CELL_TYPE_ERROR;//错误
	
}
