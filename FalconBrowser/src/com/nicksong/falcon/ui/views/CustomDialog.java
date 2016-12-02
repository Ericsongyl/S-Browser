package com.nicksong.falcon.ui.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.nicksong.falcon.utils.Utils;

public class CustomDialog extends Dialog {
	Context context;
	View contentView;

	public CustomDialog(Context context) {
		super(context);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public CustomDialog(Context context, int theme, View view) {
		super(context, theme);
		this.context = context;
		this.contentView = view;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(contentView);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height by density and gravity
		// float density = getDensity(context);
		params.width = Utils.dpToPx(225.0f, context.getResources());
		params.height = window.getWindowManager().getDefaultDisplay().getHeight();
		params.gravity = Gravity.RIGHT;
		window.setAttributes(params);
		setCanceledOnTouchOutside(true);

	}

	/*private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}*/

}
