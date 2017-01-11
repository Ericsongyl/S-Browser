package com.nicksong.s.browser.activity;

import java.net.MalformedURLException;
import java.net.URL;

import com.nicksong.s.browser.R;
import com.nicksong.s.browser.util.AppUtil;
import com.nicksong.s.browser.util.Constant;
import com.nicksong.s.browser.util.DownloadUtil;
import com.nicksong.s.browser.view.MoreMenuPopWindow;
import com.nicksong.s.browser.view.X5WebView;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.utils.TbsLog;
import com.zbar.lib.CaptureActivity;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class BrowserActivity extends Activity implements OnClickListener{
	
	private ProgressBar mPb;
	private X5WebView mWebView;
	private FrameLayout mViewParent;
	private LinearLayout llQrcode;
	private ImageView ivQrcode;
	private ImageView ivWebIcon;
	private TextView tvInputUrl;
	private LinearLayout llRefresh;
	private LinearLayout llBack;
	private LinearLayout llForward;
	private LinearLayout llMenu;
	private LinearLayout llHome;
	private LinearLayout llMultiWin;
	private ImageView ivRefresh;
	private ImageView ivBack;
	private ImageView ivForward;
	private ImageView ivMenu;
	private ImageView ivHome;
	private Button btnMultiWindows;
	private LinearLayout llBottom;
	private MoreMenuPopWindow menuPopWindow;
	private URL mIntentUrl;
	private WebViewClient mWebViewClient;
	private WebChromeClient mWebChromeClient;
	private DownloadListener mDownloadListener;
	private String webUrl;
	private final float disable = 0.3f; //按钮半透明
	private final float enable = 1.0f; //按钮透明
	private boolean loadDone = true;
	private long firstClickTime;
	private DownloadUtil downloadUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_main);
		initView();
		initData();
		initListener();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		if (intent == null || mWebView == null || intent.getData() == null) {
			return;
		}
		mWebView.loadUrl(intent.getData().toString());
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (mWebView != null) {
			mWebView.destroy();
		}
		super.onDestroy();
	}
	
	private void initView() {
		mViewParent = (FrameLayout)findViewById(R.id.x5_wv_main);
		mPb = (ProgressBar)findViewById(R.id.pb_web_loading);
		llQrcode = (LinearLayout)findViewById(R.id.ll_ic_qrcode);
		ivQrcode = (ImageView)findViewById(R.id.iv_qrcode);
		ivWebIcon = (ImageView)findViewById(R.id.iv_web_icon);
		tvInputUrl = (TextView)findViewById(R.id.tv_search_input);
		llRefresh = (LinearLayout)findViewById(R.id.ll_ic_refresh);
		ivRefresh = (ImageView)findViewById(R.id.iv_refresh);
		llBottom = (LinearLayout)findViewById(R.id.ll_bottom);
		llBack = (LinearLayout)findViewById(R.id.ll_ic_back);
		llForward = (LinearLayout)findViewById(R.id.ll_ic_forward);
		llMenu = (LinearLayout)findViewById(R.id.ll_ic_menu);
		llHome = (LinearLayout)findViewById(R.id.ll_ic_home);
		llMultiWin = (LinearLayout)findViewById(R.id.ll_muti_windows);
		ivBack = (ImageView)findViewById(R.id.iv_back);
		ivForward = (ImageView)findViewById(R.id.iv_forward);
		ivMenu = (ImageView)findViewById(R.id.iv_menu);
		ivHome = (ImageView)findViewById(R.id.iv_home);
		btnMultiWindows = (Button)findViewById(R.id.bt_muti_windows);
	}
	
	private void initListener() {
		llQrcode.setOnClickListener(this);
		tvInputUrl.setOnClickListener(this);
		llRefresh.setOnClickListener(this);
		llBack.setOnClickListener(this);
		llForward.setOnClickListener(this);
		llMenu.setOnClickListener(this);
		llHome.setOnClickListener(this);
		llMultiWin.setOnClickListener(this);
	}
	
	private void initWebView() {
		Intent intent = getIntent();
		if (intent != null) {
			try {
				mIntentUrl = new URL(intent.getData().toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
				getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
								android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		mWebView = new X5WebView(this, null);
		mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
		X5WebView.setSmallWebViewEnabled(true);
		mWebViewClient = new MyWebViewClient();
		mWebChromeClient = new MyWebChromeClient();
		mDownloadListener = new MyWebDownloadListener();
	}
	
	private void initData() {
		initWebView();
		mWebView.setWebViewClient(mWebViewClient);
		mWebView.setWebChromeClient(mWebChromeClient);
		mWebView.setDownloadListener(mDownloadListener);
		long time = System.currentTimeMillis();
		if (mIntentUrl == null) {
			mWebView.loadUrl(Constant.URL_HOME_PAGE);
		} else {
			mWebView.loadUrl(mIntentUrl.toString());
		}
		TbsLog.d("time-cost", "cost time: "
				+ (System.currentTimeMillis() - time));
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().sync();
	}
	
	private void toInputSearchActivity() {
		Intent it = new Intent(BrowserActivity.this, InputSearchActivity.class);
		it.setData(Uri.parse(webUrl));
		startActivity(it);
	}
	
	private void btnRefreshAction() {
		if (loadDone) {
			mWebView.reload();
		} else {
			mWebView.stopLoading();
		}
	}
	
	private void changeBackForwardButton(WebView webView) {
		if (webView.canGoBack()) {
			ivBack.setAlpha(enable);
			llBack.setEnabled(true);
		} else {
			ivBack.setAlpha(disable);
			llBack.setEnabled(false);
		}
		if (webView.canGoForward()) {
			ivForward.setAlpha(enable);
			llForward.setEnabled(true);
		} else {
			ivForward.setAlpha(disable);
			llForward.setEnabled(false);
		}
		if (webView.getUrl() != null && webView.getUrl().equalsIgnoreCase(Constant.URL_HOME_PAGE)) {
			ivHome.setAlpha(disable);
			llHome.setEnabled(false);
		} else {
			ivHome.setAlpha(enable);
			llHome.setEnabled(true);
		}
	}
	
	private void goBackPage() {
		if (mWebView != null && mWebView.canGoBack()) {
			mWebView.goBack();
		}
	}
	
	private void goForwardPage() {
		if (mWebView != null && mWebView.canGoForward()) {
			mWebView.goForward();
		}
	}
	
	private void goHomePage() {
		if (mWebView != null) {
			mWebView.loadUrl(Constant.URL_HOME_PAGE);
		}
	}
	
	private void goToScanQrcode() {
		startActivity(new Intent(BrowserActivity.this, CaptureActivity.class));
	}
	
	private void showMoreMenu() {
		menuPopWindow = new MoreMenuPopWindow(BrowserActivity.this, mViewParent);
		menuPopWindow.showAtLocation(llBottom, Gravity.BOTTOM, 0, llBottom.getHeight());
	}
	
	private void showToastMsg(String msg) {
		Toast.makeText(BrowserActivity.this, msg, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.ll_ic_qrcode:
			goToScanQrcode();
			break;
		case R.id.tv_search_input:
			toInputSearchActivity();
			break;
		case R.id.ll_ic_refresh:
			btnRefreshAction();
			break;
		case R.id.ll_ic_back:
			goBackPage();
			break;
		case R.id.ll_ic_forward:
			goForwardPage();
			break;
		case R.id.ll_ic_menu:
//			showMoreMenu();
			showToastMsg("敬请期待");
			break;
		case R.id.ll_ic_home:
			goHomePage();
			break;
		case R.id.ll_muti_windows:
			showToastMsg("敬请期待");
			break;
		default:
			break;
		}
	}
	
	private void exitApp() {
		long currClickTime = System.currentTimeMillis();
		if (currClickTime - firstClickTime >= 2000) {
			firstClickTime = System.currentTimeMillis();
			showToastMsg("再按一次退出浏览器");
		} else {
//			finish();
			Process.killProcess(Process.myPid());
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView != null && mWebView.canGoBack()) {
				mWebView.goBack();
				return true;
			} else {
				exitApp();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private class MyWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public WebResourceResponse shouldInterceptRequest(WebView arg0,
				String arg1) {
			// TODO Auto-generated method stub
			return super.shouldInterceptRequest(arg0, arg1);
		}
		
		@Override
		public void onPageStarted(WebView view, String url, Bitmap arg2) {
			// TODO Auto-generated method stub
			super.onPageStarted(view, url, arg2);
			webUrl = url;
			loadDone = false;
			ivRefresh.setBackgroundResource(R.drawable.ic_stop);
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			// TODO Auto-generated method stub
			super.onPageFinished(view, url);
			webUrl = url;
			loadDone = true;
			ivRefresh.setBackgroundResource(R.drawable.ic_refersh);
			changeBackForwardButton(view);
		}
	}
	
	private class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int progress) {
			// TODO Auto-generated method stub
			super.onProgressChanged(view, progress);
			if (progress >= 100) {
				progress = 100;
				mPb.setProgress(progress);
				mPb.setVisibility(View.GONE);
			} else {
				if (progress >= 10) {
					mPb.setProgress(progress);
				}
				mPb.setVisibility(View.VISIBLE);
			}
		}
		
		@Override
		public void onReceivedTitle(WebView arg0, String title) {
			// TODO Auto-generated method stub
			super.onReceivedTitle(arg0, title);
			if (tvInputUrl != null && !AppUtil.isStringEmpty(title)) {
				tvInputUrl.setText(title);
			}
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void onReceivedIcon(WebView arg0, Bitmap icon) {
			// TODO Auto-generated method stub
			super.onReceivedIcon(arg0, icon);
//			if (ivWebIcon != null) {
//				Drawable db = AppUtil.bitmap2Drawable(icon);
//				if (db != null) {
//					ivWebIcon.setBackgroundDrawable(db);
//				}
//			}
		}
	}
	
	private class MyWebDownloadListener implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String arg1, String arg2,
				String arg3, long arg4) {
			// TODO Auto-generated method stub
			AppUtil.logInfo("downloadStart...");
			downloadUtil = new DownloadUtil(BrowserActivity.this, url);
			downloadUtil.startDownload();
		}
		
	}

}
