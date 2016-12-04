package com.nicksong.falcon.ui.activities;

import android.app.Application;
import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nicksong.falcon.data.RequestManager;

public class FLAppication extends Application {
	private static Context sContext;
	public static final boolean DEBUG = true;
	private LocationClient mLocationClient = null;
	public static int mNetWorkState;
	private static Application mApplication;

	public static Context getContext() {
		return sContext;
	}

	public static synchronized Application getInstance() {
		return mApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initData();
		RequestManager.init(this);
	}

	private LocationClientOption getLocationClientOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");
		option.setServiceName(this.getPackageName());
		option.setScanSpan(0);
		option.disableCache(true);
		return option;
	}

	private void initData() {
		mApplication = this;
		sContext = this.getApplicationContext();
		mLocationClient = new LocationClient(this, getLocationClientOption());

	}

	public synchronized LocationClient getLocationClient() {
		if (mLocationClient == null) mLocationClient = new LocationClient(this, getLocationClientOption());
		return mLocationClient;
	}
}
