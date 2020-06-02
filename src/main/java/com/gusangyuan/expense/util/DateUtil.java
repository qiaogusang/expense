package com.gusangyuan.expense.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * jdk8日期处理类
 * <br>
 * @author Administrator
 *
 */
public class DateUtil {
	
	private DateUtil() {}
	
	private static LocalDate localDate = LocalDate.now();
	
	/**
	 * 获取当前日期
	 * @return String
	 */
	public static String getCurrentDate() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return df.format(localDate);
	}
	
	/**
	 * 获取当前年份
	 * @return String
	 */
	public static String getCurrentYear() {
		return String.valueOf(localDate.getYear());
	}
	
	/**
	 * 获取当前月份
	 * @return String
	 */
	public static String getCurrentMonth() {
		return String.valueOf(localDate.getMonth().getValue());
	}
	
	/**
	 * 获取当前天
	 * @return String
	 */
	public static String getCurrentDay() {
		return String.valueOf(localDate.getDayOfMonth());
	}
	
	/**
	 * 字符串日期是否格式匹配
	 * @param date 字符串日期
	 * @param formatter 日期格式
	 * @return boolean
	 */
	public static boolean formatDate(String date, String formatter) {
		boolean flag = true;
		DateTimeFormatter df = DateTimeFormatter.ofPattern(formatter);
		
		try {
			LocalDate.parse(date, df);
		}catch(DateTimeParseException ex) {
			flag = false;
		}
		return flag;
	}
	
}
