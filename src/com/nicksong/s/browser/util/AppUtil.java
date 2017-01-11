package com.nicksong.s.browser.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class AppUtil {

	private static boolean DEBUG = true;
	private static String TAG = "S-Browser";

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
	 * 
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

	/**
	 * 从下载url获取文件名
	 * 
	 * @param url
	 * @return fileName
	 */
	public static String getFileName(String path) {
		try {
			HttpURLConnection conn;
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			String filename = path.substring(path.lastIndexOf('/') + 1);
			if (filename == null || "".equals(filename.trim())) {// 如果获取不到文件名称
				for (int i = 0;; i++) {
					String mine = conn.getHeaderField(i);
					if (mine == null)
						break;
					if ("content-disposition".equals(conn.getHeaderFieldKey(i).toLowerCase())) {
						Matcher m = Pattern.compile(".*filename=(.*)").matcher(mine.toLowerCase());
						if (m.find()) {
							return m.group(1);
						}
					}
				}
				filename = UUID.randomUUID() + ".tmp";// 默认取一个文件名
			}
			return filename;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
