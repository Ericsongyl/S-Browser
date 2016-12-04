package com.nicksong.falcon.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nicksong.falcon.ui.activities.FLAppication;
import com.nicksong.falcon.utils.FileUtils;

public class RequestManager {

	private static FileUtils fileUtils;
	private static RequestQueue mRequestQueue;

	public static void init(Context context) {
		fileUtils = new FileUtils();
		mRequestQueue = Volley.newRequestQueue(context, fileUtils.getCacheFile());
	}

	public static RequestQueue getRuquestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(FLAppication.getContext(), fileUtils.getCacheFile());
		}
		return mRequestQueue;
	}

	public static void addRequest(Request<?> request, Object tag) {
		if (tag != null) {
			request.setTag(tag);
		}
		mRequestQueue.add(request);
	}

	public static void cancelAll(Object tag) {
		mRequestQueue.cancelAll(tag);
	}
}
