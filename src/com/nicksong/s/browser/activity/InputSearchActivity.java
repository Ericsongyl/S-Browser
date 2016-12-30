package com.nicksong.s.browser.activity;

import com.nicksong.s.browser.R;
import com.nicksong.s.browser.util.AppUtil;
import com.nicksong.s.browser.util.Constant;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class InputSearchActivity extends Activity implements OnClickListener{
	
	private View viewBody;
	private EditText etSearchInput;
	private LinearLayout llGo;
	private String inputString;
	private String webUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_input_search);
		initView();
		initListener();
		initData();
	}
	
	private void initView() {
		viewBody = (View)findViewById(R.id.view_body);
		etSearchInput = (EditText)findViewById(R.id.et_search_input);
		llGo = (LinearLayout)findViewById(R.id.ll_ic_go);
	}
	
	private void initListener() {
		viewBody.setOnClickListener(this);
		llGo.setOnClickListener(this);
	}
	
	private void initData() {
		if (getIntent() != null) {
			webUrl = getIntent().getDataString();
			if (!AppUtil.isStringEmpty(webUrl)) {
				etSearchInput.setText(webUrl);
				etSearchInput.setSelectAllOnFocus(true);
			}
		}
	}
	
	private void toSearch() {
		inputString = etSearchInput.getText().toString().trim();
		if (AppUtil.isStringEmpty(inputString)) {
			Toast.makeText(InputSearchActivity.this, "请输入搜索内容或网址", Toast.LENGTH_SHORT).show();
		} else {
			if (AppUtil.isContainsChinese(inputString)) {
				inputString = Constant.URL_SEARCH_KEYWORD + inputString;
			} else {
				if (!(inputString.startsWith("http://") || inputString.startsWith("https://"))) {
					inputString = "http://" + inputString;
				}
			}
			Intent it = new Intent(InputSearchActivity.this, BrowserActivity.class);
			it.setData(Uri.parse(inputString));
			startActivity(it);
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.view_body:
			finish();
			break;
		case R.id.ll_ic_go:
			toSearch();
			break;
		default:
			break;
		}
	}

}
