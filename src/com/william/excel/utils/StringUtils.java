package com.william.excel.utils;

import java.io.UnsupportedEncodingException;

public final class StringUtils {

	private StringUtils() {
	}

	public static final String GBK_CHARSET = "GBK";
	public static final String ISO_CHARSET = "ISO8859-1";

	/**
	 * 在Get请求的情况下，将ISO8859-1转换成指定的编码集
	 * 
	 * @param value
	 * @return
	 */
	public static String toNewCharset(String value, String charset) {

		if (value == null || charset == null)
			return value;

		try {

			byte[] bytes = value.getBytes(ISO_CHARSET);

			return new String(bytes, charset);

		} catch (UnsupportedEncodingException e) {
			return value;
		}

	}

	/**
	 * 省略显示数据
	 * 
	 * @param value
	 * @param length
	 * @return
	 */
	public static String omissionString(String value, int length) {

		if (value == null || length <= 0)
			return value;

		if (value.length() > length && value.getBytes().length > length)
			return value.substring(0, length) + "...";

		return value;
	}

	/**
	 * 格式化字符串
	 * 
	 * @param val
	 *            要格式化的字符串
	 * @param flag
	 *            格式
	 * @return 格式化后的字符串
	 */
	public static String format(String val, String flag) {

		if (val == null || flag == null)
			return "";

		String ret = flag + val;

		return ret.substring(ret.length() - flag.length());

	}
}
