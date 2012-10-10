package com.william.excel.utils;

import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

public class ExcelCellUtil {
	
	private static final double EXP = 0.000000000000000001;
	
	public static  String getCellStringValue(HSSFCell hssfCell) {   
        String retStr = "";   
        if (hssfCell != null) {  
            int type = hssfCell.getCellType();   
  
            switch (type) {   
  
            case HSSFCell.CELL_TYPE_NUMERIC: // Numeric   
                if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {   
                    retStr = (new SimpleDateFormat("yyyy-MM-dd")   
                            .format(hssfCell.getDateCellValue()));   
                } else {   
                    double value = hssfCell.getNumericCellValue();   
                    // ���������ֵ����С,��ת��Ϊ����   
                    if (Math.abs(value - (long) value) < EXP) {   
                        retStr = String.valueOf((long) value);   
                    } else {   
                        // doubleֵ   
                        retStr = String.valueOf(hssfCell.getNumericCellValue());   
                    }   
                }   
                break;   
            case ExcelCellType.CELL_TYPE_STRING: // String   
                retStr = hssfCell.getRichStringCellValue().getString();   
                break;   
            case ExcelCellType.CELL_TYPE_FORMULA: // Formula ��ʽ, ����ʽ   
                retStr = String.valueOf(hssfCell.getNumericCellValue());   
                break;   
            case ExcelCellType.CELL_TYPE_BLANK: // Blank   
                retStr = "";   
                break;   
            case ExcelCellType.CELL_TYPE_BOOLEAN: // boolean   
                retStr = Boolean.valueOf(hssfCell.getBooleanCellValue())   
                        .toString();   
                break;   
            case HSSFCell.CELL_TYPE_ERROR: // Error   
                break;   
            }   
        }   
        // ��ȥ�ַ����еĿո�   
        return retStr.replaceAll("\\s", "");   
    }  
}
