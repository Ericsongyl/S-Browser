package com.nicksong.falcon.data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;

public class VolleyTool {
	private static VolleyTool mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    
    private VolleyTool(Context context) {
    	mRequestQueue = RequestManager.getRuquestQueue();
    	mImageLoader = new ImageLoader(mRequestQueue, new BitmapCache());
    }
    
    public static VolleyTool getInstance(Context context){
        if(mInstance == null){
    		mInstance = new VolleyTool(context);
        }
        return mInstance;
    }

	public ImageLoader getmImageLoader() {
		return mImageLoader;
	}

	public void release() {
		this.mImageLoader = null;
		this.mRequestQueue = null;
		mInstance = null;
	}
}
