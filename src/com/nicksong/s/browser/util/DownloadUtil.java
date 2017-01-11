package com.nicksong.s.browser.util;

import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.net.Uri;

public class DownloadUtil {
	
	private Context mContext;
	private String mUrl;
	private String fileName;
	private DownloadManager mDownloadManager;
	
	public DownloadUtil(Context context, String url) {
		mContext = context;
		mUrl = url;
		fileName = AppUtil.getFileName(url);
		mDownloadManager = (DownloadManager)context.getSystemService(Context.DOWNLOAD_SERVICE);
	}

	public void startDownload() {
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mUrl));
		// 设置在什么网络情况下进行下载
//		request.setAllowedNetworkTypes(Request.NETWORK_WIFI); //默认：任何网络都可以下载
		// 设置通知栏标题
		request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setVisibleInDownloadsUi(true);
		request.setTitle(fileName);
		request.setDescription("正在下载");
		request.setAllowedOverRoaming(false);
		// 设置文件存放目录
		request.setDestinationInExternalPublicDir(Constant.DOWNLOAD_DIR, fileName);
		mDownloadManager.enqueue(request);
	}

}
