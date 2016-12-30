package com.nicksong.s.browser.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
public class AppUtil {
	
	private static boolean DEBUG = true;
	private static String TAG = "FalconBrowser";

	public static boolean isStringEmpty(String string) {
		if (string == null) {
			return true;
		} else {
			if ("".equals(string)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断字符串是否包含中文
	 * @param string
	 * @return
	 */
	public static boolean isContainsChinese(String string) {
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(string);
		if (m.find()) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public static Drawable bitmap2Drawable(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		} else {
			return new BitmapDrawable(bitmap);
		}
	}
	
	public static void logDebug(String string) {
		if (DEBUG) {
			Log.d(TAG, string);
		}
	}
	
	public static void logInfo(String string) {
		if (DEBUG) {
			Log.i(TAG, string);
		}
	}
	
	public static void logError(String string) {
		if (DEBUG) {
			Log.e(TAG, string);
		}
	}
	
	public static void logVerbose(String string) {
		if (DEBUG) {
			Log.v(TAG, string);
		}
	}
	
	public static void logWarn(String string) {
		if (DEBUG) {
			Log.w(TAG, string);
		}
	}
	
}
