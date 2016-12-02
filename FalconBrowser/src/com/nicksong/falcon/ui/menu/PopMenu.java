package com.nicksong.falcon.ui.menu;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.nicksong.falcon.R;
import com.nicksong.falcon.ui.activities.SealBrowserActivity;
import com.nicksong.falcon.ui.preferences.PreferencesActivity;
import com.nicksong.falcon.utils.Utils;

public class PopMenu {

	private SealBrowserActivity mActivity;
	private PopupWindow popupWindow;
	/*Popu Menu*/
	private LinearLayout mExit;
	private LinearLayout mSettings;
	private LinearLayout mShare;
	private LinearLayout mFullScreen;
	private LinearLayout mGotoBookmark;
	private LinearLayout mAddBookmark;
	private LinearLayout mSearch;
	private View view = null;

	public PopMenu(final SealBrowserActivity mActivity) {

		// TODO Auto-generated constructor stub
		this.mActivity = mActivity;
		view = View.inflate(mActivity, R.layout.settings_popmenu, null);
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, mActivity.getResources().getDimensionPixelSize(R.dimen.popmenu_h));
		ColorDrawable cd = new ColorDrawable(-0000);
		popupWindow.setBackgroundDrawable(cd);
		popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		/*Menu Bar*/
		mExit = (LinearLayout) view.findViewById(R.id.menu_exit);
		mExit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mActivity != null) {
					dismiss();
					mActivity.finish();
				}
			}
		});
		mSettings = (LinearLayout) view.findViewById(R.id.menu_settings);
		mSettings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				Intent i = new Intent(mActivity, PreferencesActivity.class);
				mActivity.startActivity(i);
			}
		});
		mShare = (LinearLayout) view.findViewById(R.id.menu_share);
		mShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				mActivity.shareCurrentPage();
			}
		});
		mFullScreen = (LinearLayout) view.findViewById(R.id.menu_fullscreen);
		mFullScreen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mActivity != null) {
					dismiss();
					mActivity.toggleFullScreen();
				}
			}
		});
		mGotoBookmark = (LinearLayout) view.findViewById(R.id.menu_bookmark);
		mGotoBookmark.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mActivity != null) {
					dismiss();
					mActivity.goToBookMark();
				}
			}
		});
		mAddBookmark = (LinearLayout) view.findViewById(R.id.menu_addbookmark);
		mAddBookmark.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mActivity != null) {
					dismiss();
					mActivity.addBookMark();
				}
			}
		});
		mSearch = (LinearLayout) view.findViewById(R.id.menu_search);
		mSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (mActivity != null) {
					dismiss();
					mActivity.pageSearch();
				}
			}
		});
	}

	public void show(View parent) {
		// popupWindow.showAsDropDown(parent, -1000, context.getResources()
		// .getDimensionPixelSize(R.dimen.popmenu_yoff));
		popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, Utils.dpToPx(50.0f, mActivity.getResources()));
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		// popupWindow.update();
	}

	public void dismiss() {
		if (popupWindow != null) popupWindow.dismiss();
	}
}
