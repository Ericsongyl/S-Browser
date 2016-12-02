package com.nicksong.falcon.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Ho on 2014/7/8.
 */
public class DateUtil {

	/**
	 * 获取当前时间 以HH:mm的形式返回
	 * 
	 * @return
	 */
	public static String getCurTime() {
		return getCurDate("HH:mm");
	}

	/**
	 * 获取当前日期 以MM-dd E的形式返回
	 * 
	 * @return
	 */
	public static String getCurWeek() {
		return getCurDate("E");
	}

	public static String getCurDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat(str);
		Date date = new Date(System.currentTimeMillis());

		return format.format(date);
	}

	/**
	 * 获取当前时间（小时）
	 */
	public static int getHour() {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		return now.get(Calendar.HOUR_OF_DAY);
	}

	public static String getMonthDay(long time) {
		SimpleDateFormat formatMD = new SimpleDateFormat("MM/dd", Locale.CHINA);
		String date = formatMD.format(new Date(time * 1000));
		return date;
	}

	public static boolean isSun() {
		int currentHour = getHour();
		if (currentHour > 6 && currentHour < 19) {
			return true;
		}
		return false;
	}
}
