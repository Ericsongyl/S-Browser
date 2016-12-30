package com.nicksong.s.browser.view;

import java.util.HashMap;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.ValueCallback;

import com.tencent.smtt.export.external.extension.interfaces.IX5WebChromeClientExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.MediaAccessPermissionsCallback;
import com.tencent.smtt.sdk.WebViewCallbackClient;

public class X5WebViewEventHandler extends ProxyWebViewClientExtension implements IX5WebChromeClientExtension{
	
	/**
	 * 这个类用于实现由于X5webview适配架构导致的部分client回调不会发生，或者回调中传入的值不正确
	 * 这个方法中所有的interface均是直接从内核中获取值并传入内核，请谨慎修改
	 * 使用时只需要在对应的方法中加入你自己的逻辑就可以
	 * 同时注意：具有返回值的方法在正常情况下保持其返回效果
	 * 一般而言：返回true表示消费事件，由用户端直接处理事件
	 * 			返回false表示需要内核使用默认机制处理事件
	 */
	private X5WebView webView; //the vote of x5webview
	
	public X5WebViewEventHandler(X5WebView webView){
		this.webView = webView;
		this.webView.setWebViewCallbackClient(callbackClient);
		
	}

	@Override
	public void acquireWakeLock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFlashView(View arg0, LayoutParams arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exitFullScreenFlash() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Context getApplicationContex() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getVideoLoadingProgressView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getX5WebChromeClientInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void h5videoExitFullScreen(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void h5videoRequestFullScreen(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onAddFavorite(IX5WebViewExtension arg0, String arg1,
			String arg2, JsResult arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onAllMetaDataFinished(IX5WebViewExtension arg0,
			HashMap<String, String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBackforwardFinished(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onHitTestResultFinished(IX5WebViewExtension arg0,
			HitTestResult arg1) {
		// TODO Auto-generated method stub
		Log.i("yuanhaizhou", "onHitTestResultFinished");
	}

	@Override
	public void onHitTestResultForPluginFinished(IX5WebViewExtension arg0,
			HitTestResult arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		arg1.getData();
		Log.i("yuanhaizhou", "onHitTestResultForPluginFinished");
	}

	@Override
	public boolean onPageNotResponding(Runnable arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onPrepareX5ReadPageDataFinished(IX5WebViewExtension arg0,
			HashMap<String, String> arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPromptNotScalable(IX5WebViewExtension arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPromptScaleSaved(IX5WebViewExtension arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSavePassword(String arg0, String arg1, String arg2,
			boolean arg3, Message arg4) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onX5ReadModeAvailableChecked(HashMap<String, String> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void releaseWakeLock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestFullScreenFlash() {
		// TODO Auto-generated method stub
		
	}
	
	
	/////////////////////////////////////////////////////
	
	/**
	 * 这里使用内核的事件回调链接到对应webview的事件回调
	 */
	private WebViewCallbackClient callbackClient = new WebViewCallbackClient() {
		
		@Override
		public boolean onTouchEvent(MotionEvent event, View view) {
			Log.i("yuanhaizhou", "tbs_onTouchEvent view is " + view.getClass().toString());
			return webView.tbs_onTouchEvent(event, view);			
		}


		@Override
		public boolean overScrollBy(int deltaX, int deltaY, int scrollX,
				int scrollY, int scrollRangeX, int scrollRangeY,
				int maxOverScrollX, int maxOverScrollY, 
				boolean isTouchEvent, View view) {
			return webView.tbs_overScrollBy(deltaX, deltaY, scrollX, scrollY, 
					scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, 
					isTouchEvent, view);
		}

		@Override
		public void computeScroll(View view) {
			webView.tbs_computeScroll(view);
		}


		@Override
		public void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
				boolean clampedY, View view) {
			webView.tbs_onOverScrolled(scrollX, scrollY, clampedX, clampedY, view);			
		}

		@Override
		public void onScrollChanged(int l, int t, int oldl, int oldt, View view) {
			webView.tbs_onScrollChanged(l, t, oldl, oldt, view);	
		}

		@Override
		public boolean dispatchTouchEvent(MotionEvent ev, View view) {
			return webView.tbs_dispatchTouchEvent(ev, view);
		}

		@Override
		public boolean onInterceptTouchEvent(MotionEvent ev, View view) {
			return webView.tbs_onInterceptTouchEvent(ev, view);
		}
	};
	
	
	//////////////////////////////////////////////////////////////////////
	/**
	 * 这里是内核代理的事件处理方法
	 */
	
	@Override
	public Object onMiscCallBack(String method,
			Bundle bundle) {

		return null;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event, View view) {
		return callbackClient.onTouchEvent(event, view);
	}
	
	   // 1
	public boolean onInterceptTouchEvent(MotionEvent ev, View view) {
		return callbackClient.onInterceptTouchEvent(ev, view);
	}	

	// 3
	public boolean dispatchTouchEvent(MotionEvent ev, View view) {
		return callbackClient.dispatchTouchEvent(ev, view);
	}
	// 4
	public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
			                    int scrollRangeX, int scrollRangeY, 
			                    int maxOverScrollX, int maxOverScrollY,
			                    boolean isTouchEvent, View view) {
		return callbackClient.overScrollBy(deltaX, deltaY, scrollX, scrollY, 
				scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent, view);
	}
	// 5
    public void onScrollChanged(int l, int t, int oldl, int oldt, View view) {
    	callbackClient.onScrollChanged(l, t, oldl, oldt, view);
	}
    // 6
    public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, 
			boolean clampedY, View view) {
    	callbackClient.onOverScrolled(scrollX, scrollY, clampedX, clampedY, view);
	}
	// 7
	public void computeScroll(View view) {
		callbackClient.computeScroll(view);
	}

	@Override
	public void onPrintPage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSavePassword(ValueCallback<String> arg0, String arg1,
			String arg2, String arg3, String arg4, String arg5, boolean arg6) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openFileChooser(ValueCallback<Uri[]> arg0, String arg1,
			String arg2) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onColorModeChanged(long arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jsExitFullScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jsRequestFullScreen() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onPermissionRequest(String arg0, long arg1,
			MediaAccessPermissionsCallback arg2) {
		// TODO Auto-generated method stub
		return false;
	}








	

}
