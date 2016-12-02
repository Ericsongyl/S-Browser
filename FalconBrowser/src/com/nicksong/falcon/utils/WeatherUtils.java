/*
 * Tint Browser for Android
 * 
 * Copyright (C) 2012 - to infinity and beyond J. Devauchelle and contributors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.nicksong.falcon.utils;

import com.nicksong.falcon.R;

public class WeatherUtils {
	/*
	 * 晴|多云|阴|阵雨|雷阵雨|雷阵雨伴有冰雹|雨夹雪|小雨|中雨|大雨|暴雨|大暴雨|特大暴雨|阵雪|小雪|中雪|大雪|暴雪|雾|冻雨|沙尘暴|小雨转中雨
	 * |中雨转大雨|大雨转暴雨|暴雨转大暴雨|大暴雨转特大暴雨|小雪转中雪|中雪转大雪|大雪转暴雪|浮尘|扬沙|强沙尘暴|霾
	 */
	public static int getWeatherIcon(String weather) {
		boolean isSun = DateUtil.isSun();
		if (weather.equals("晴")) {
			return isSun ? R.drawable.ww0 : R.drawable.ww30;
		} else if (weather.equals("阴") || weather.equals("多云转阴")) {
			return isSun ? R.drawable.ww2 : R.drawable.ww31;
		} else if (weather.equals("多云") || weather.equals("多云转晴")) {
			return isSun ? R.drawable.ww1 : R.drawable.ww31;
		} else if (weather.equals("小雨") || weather.equals("阴转小雨")) {
			return R.drawable.ww7;
		} else if (weather.equals("中雨") || weather.equals("小雨转中雨")) {
			return R.drawable.ww8;
		} else if (weather.equals("大雨") || weather.equals("中雨转大雨")) {
			return R.drawable.ww19;
		} else if (weather.equals("暴雨") || weather.equals("大雨转暴雨") || weather.equals("大暴雨") || weather.equals("暴雨转大暴雨")) {
			return R.drawable.ww9;
		} else if (weather.equals("特大暴雨") || weather.equals("大暴雨转特大暴雨")) {
			return R.drawable.ww10;
		} else if (weather.equals("阵雨") || weather.equals("雷阵雨") || weather.equals("雷阵雨伴有冰雹")) {
			return R.drawable.ww4;
		} else if (weather.equals("雷阵雨伴有冰雹")) {
			return R.drawable.ww20;
		} else if (weather.equals("雨夹雪")) {
			return R.drawable.ww6;
		} else if (weather.equals("小雪")) {
			return R.drawable.ww14;
		} else if (weather.equals("中雪")) {
			return R.drawable.ww15;
		} else if (weather.equals("大雪")) {
			return R.drawable.ww16;
		} else if (weather.equals("暴雪") || weather.equals("大暴雪")) {
			return R.drawable.ww17;
		} else if (weather.equals("雾") || weather.equals("沙尘暴") || weather.equals("浮尘") || weather.equals("扬沙") || weather.equals("强沙尘暴")
				|| weather.equals("霾")) {
			return R.drawable.ww45;
		} else {
			return isSun ? R.drawable.ww0 : R.drawable.ww30;
		}
	}

	/**
	 * 设置空气质量
	 */
	public static String setWeatherAQI(int aqi) {
		String s = "";
		if (aqi >= 0 && aqi <= 50) {
			s = "空气质量优";
		} else if (aqi > 50 && aqi <= 100) {
			s = "空气质量良好";
		} else if (aqi > 100 && aqi <= 200) {
			s = "空气轻度污染";
		} else if (aqi > 200 && aqi <= 300) {
			s = "空气中度污染";
		} else if (aqi > 300) {
			s = "空气重度污染";
		}
		return s;
	}
}
