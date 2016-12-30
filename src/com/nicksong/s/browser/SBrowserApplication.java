package com.nicksong.s.browser;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import android.app.Application;

public class SBrowserApplication extends Application{
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
			
			@Override
			public void onViewInitFinished(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCoreInitFinished() {
				// TODO Auto-generated method stub
				
			}
		};
		QbSdk.setTbsListener(new TbsListener() {

			@Override
			public void onDownloadFinish(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onDownloadProgress(int arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onInstallFinish(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		QbSdk.initX5Environment(getApplicationContext(), cb);
	}

}
