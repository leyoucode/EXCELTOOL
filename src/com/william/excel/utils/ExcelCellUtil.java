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
                    // 如果两个数值相差很小,则转换为整数   
                    if (Math.abs(value - (long) value) < EXP) {   
                        retStr = String.valueOf((long) value);   
                    } else {   
                        // double值   
                        retStr = String.valueOf(hssfCell.getNumericCellValue());   
                    }   
                }   
                break;   
            case ExcelCellType.CELL_TYPE_STRING: // String   
                retStr = hssfCell.getRichStringCellValue().getString();   
                break;   
            case ExcelCellType.CELL_TYPE_FORMULA: // Formula 公式, 方程式   
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
        // 除去字符串中的空格   
        return retStr.replaceAll("\\s", "");   
    }  
}
