package com.william.excel.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
/**
 * ������Excel�ĵ�Ԫ������
 * ��Ӧ��org.apache.poi.hssf.usermodel.HSSFCell�ж��������
 * ������������չ
 * @author it2
 *
 */
public class ExcelCellType{

	public static final int CELL_TYPE_NUMERIC = HSSFCell.CELL_TYPE_NUMERIC;//���ָ�ʽ 
	public static final int CELL_TYPE_STRING = HSSFCell.CELL_TYPE_STRING;//�ַ�����Ĭ�ϣ�
	public static final int CELL_TYPE_FORMULA = HSSFCell.CELL_TYPE_FORMULA;//��ʽ
	public static final int CELL_TYPE_BLANK = HSSFCell.CELL_TYPE_BLANK;//�հ�
	public static final int CELL_TYPE_BOOLEAN = HSSFCell.CELL_TYPE_BOOLEAN;//����
	public static final int CELL_TYPE_ERROR = HSSFCell.CELL_TYPE_ERROR;//����
	
}
