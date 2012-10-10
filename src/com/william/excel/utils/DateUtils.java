package com.william.excel.utils;

import java.util.Calendar;

public final class DateUtils {

	private DateUtils() {
	}

	public static String getSysDateNumberStream() {
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		int month = cal.get(Calendar.MONTH) + 1;

		int day = cal.get(Calendar.DAY_OF_MONTH);

		int hour = cal.get(Calendar.HOUR_OF_DAY);

		int minute = cal.get(Calendar.MINUTE);

		int second = cal.get(Calendar.SECOND);

		int millisecond = cal.get(Calendar.MILLISECOND);

		return String.valueOf(year) + String.valueOf(month)
				+ String.valueOf(day) + String.valueOf(hour)
				+ String.valueOf(minute) + String.valueOf(second)
				+ String.valueOf(millisecond);
	}

	/**
	 * �õ�ϵͳ����
	 * 
	 * @return ϵͳ����
	 */
	public static String getSysDate() {
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		int month = cal.get(Calendar.MONTH) + 1;

		int day = cal.get(Calendar.DAY_OF_MONTH);

		return StringUtils.format(String.valueOf(year), "0000") + "-"
				+ StringUtils.format(String.valueOf(month), "00") + "-"
				+ StringUtils.format(String.valueOf(day), "00");
	}
	
	/**
	 * �õ�ϵͳʱ��
	 * 
	 * @return ϵͳʱ��
	 */
	public static String getSysTime() {
		Calendar cal = Calendar.getInstance();

		int hour = cal.get(Calendar.HOUR_OF_DAY);
		
		int minute = cal.get(Calendar.MINUTE);

		int second = cal.get(Calendar.SECOND);

		return StringUtils.format(String.valueOf(hour), "00") + ":"
				+ StringUtils.format(String.valueOf(minute), "00") + ":"
				+ StringUtils.format(String.valueOf(second), "00");
	}
	
	/**
	 * �õ�ϵͳʱ��
	 * 
	 * @return ϵͳʱ��
	 */
	public static String getLongSysDate() {

		return getSysDate() + " " + getSysTime();
	}
	
	/**
	 * �õ���
	 * 
	 * @return ϵͳʱ��
	 */
	public static String getSysYear() {
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		return StringUtils.format(String.valueOf(year), "0000");
	}
}
