package com.william.excel.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 验证类 用于对数据安全性合法性的检验
 * @author it2
 *
 */
public class ValidatorUtils {
	public static boolean isEmpty(String property) {

		return (property == null || "".equals(property.trim())) ? true : false;
	}

	public static boolean isInteger(String property) {
		try {

			Integer.parseInt(property);

		} catch (NumberFormatException e) {

			return false;
		}

		return true;

	}

	public static boolean isBoolean(String property) {
		
		try {

			Boolean.parseBoolean(property);

		} catch (NumberFormatException e) {

			return false;
		}

		return true;
	}
	
	public static boolean isLong(String property) {
		try {

			Long.parseLong(property);

		} catch (NumberFormatException e) {

			return false;
		}

		return true;

	}

	public static boolean isFloat(String property) {
		try {

			Float.parseFloat(property);

		} catch (NumberFormatException e) {

			return false;
		}

		return true;

	}

	public static boolean isDouble(String property) {
		try {

			Double.parseDouble(property);

		} catch (NumberFormatException e) {

			return false;
		}

		return true;

	}

	public static boolean isMask(String property, String express) {
		Pattern p = Pattern.compile(express);
		Matcher m = p.matcher(property);
		return m.find();
	}

	public static boolean checkStringEquals(String first, String second) {

		if (first == null && second == null)
			return true;

		if (first == null && second != null)
			return false;

		return first.equals(second);

	}

	public static boolean checkIntegerEquals(String first, String second) {

		if (first == null && second == null)
			return true;

		if (first == null && second != null)
			return false;

		if (!isInteger(first) || !isInteger(second))
			return false;

		return Integer.parseInt(first) == Integer.parseInt(second);

	}

	public static boolean checkLongEquals(String first, String second) {
		if (first == null && second == null)
			return true;

		if (first == null && second != null)
			return false;

		if (!isLong(first) || !isLong(second))
			return false;

		return Long.parseLong(first) == Long.parseLong(second);
	}

	public static boolean checkFloatEquals(String first, String second) {
		if (first == null && second == null)
			return true;

		if (first == null && second != null)
			return false;

		if (!isFloat(first) || !isFloat(second))
			return false;

		return Float.parseFloat(first) == Float.parseFloat(second);
	}

	public static boolean checkDoubleEquals(String first, String second) {

		if (first == null && second == null)
			return true;

		if (first == null && second != null)
			return false;

		if (!isDouble(first) || !isDouble(second))
			return false;

		return Double.parseDouble(first) == Double.parseDouble(second);
	}

	public static boolean checkIntegerRange(String property, Integer from,
			Integer to) {
		if (property == null || !isInteger(property))
			return false;

		if (Integer.parseInt(property) >= from
				&& Integer.parseInt(property) <= to)
			return true;

		return false;
	}

	public static boolean checkLongRange(String property, Long from, Long to) {
		if (property == null || !isLong(property))
			return false;

		if (Long.parseLong(property) >= from && Long.parseLong(property) <= to)
			return true;

		return false;
	}

	public static boolean checkFloatRange(String property, Float from, Float to) {
		if (property == null || !isFloat(property))
			return false;

		if (Float.parseFloat(property) >= from
				&& Float.parseFloat(property) <= to)
			return true;

		return false;
	}

	public static boolean checkDoubleRange(String property, Double from,
			Double to) {
		if (property == null || !isDouble(property))
			return false;

		if (Double.parseDouble(property) >= from
				&& Double.parseDouble(property) <= to)
			return true;

		return false;
	}
	
	public static boolean checkLength(String property,Integer len) {
		if (isEmpty(property))
			return false;
		
		return (property.length() <= len);
	}
}
