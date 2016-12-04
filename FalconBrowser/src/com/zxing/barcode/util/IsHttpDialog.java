package com.zxing.barcode.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.nicksong.falcon.R;
import com.nicksong.falcon.ui.activities.FalconBrowserActivity;

public class IsHttpDialog extends Activity {

	private Intent intent;
	private Bundle bundle;
	private String URL;
	private TextView http_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ishttpdialog);
		intent = this.getIntent();
		bundle = intent.getExtras();
		URL = bundle.getString("Url");
		http_tv = (TextView) findViewById(R.id.http);
		http_tv.setText(URL);
	}

	public void open_link(View v) {
		Intent iBrowser = new Intent(this, FalconBrowserActivity.class);
		iBrowser.putExtra("QRCODE_URL", URL);
		startActivity(iBrowser);
	}

	public void cancel_link(View v) {
		this.finish();
	}

}
