package com.nicksong.s.browser.view;

import com.nicksong.s.browser.R;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

public class MoreMenuPopWindow extends PopupWindow implements OnClickListener{
	
	private Context mContext;
	private View mView;
	private FrameLayout mViewGroup;
	
	public MoreMenuPopWindow(Context context, FrameLayout viewGroup) {
		mContext = context;
		mViewGroup = viewGroup;
		initView();
		initListener();
		initData();
	}
	
	private void initView() {
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.pop_more_menu, null);
		setContentView(mView);
	}
	
	private void initListener() {
		
	}
	
	private void initData() {
		setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setFocusable(true);
		//设置SelectPicPopupWindow弹出窗体动画效果
		setAnimationStyle(R.style.BottomDialogAnimation);
		setOutsideTouchable(true);
		setWindowBackgroundAlpha(0.75f);
	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
		setWindowBackgroundAlpha(1.0f);
	}
	
	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
//		showAnimator().start();
	}

	/**
	 * 控制窗口背景的不透明度
	 * */
	private void setWindowBackgroundAlpha(float alpha) {
		//整个屏幕半透明、底部菜单栏也被半透明了
//		Window window = ((Activity)mContext).getWindow();
//		WindowManager.LayoutParams layoutParams = window.getAttributes();
//		layoutParams.alpha = alpha;
//		window.setAttributes(layoutParams);
		//中间webview半透明、底部菜单栏保持原始状态不变
		if (alpha < 1.0f) {
			mViewGroup.setForeground(mContext.getResources().getDrawable(R.drawable.gray));
		} else {
			mViewGroup.setForeground(mContext.getResources().getDrawable(R.drawable.transparent));
		}
	}

	/**
	 * 窗口显示，窗口背景透明度渐变动画
	 * */
	private ValueAnimator showAnimator() {
		ValueAnimator animator = ValueAnimator.ofFloat(1.0f, 0.75f);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float alpha = Float.valueOf(animation.getAnimatedValue().toString());
				setWindowBackgroundAlpha(alpha);
			}
		});
		animator.setDuration(360);
		return animator;
	}

	/**
	 * 窗口隐藏，窗口背景透明度渐变动画
	 * */
	private ValueAnimator dismissAnimator() {
		ValueAnimator animator = ValueAnimator.ofFloat(0.75f, 1.0f);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float alpha = Float.valueOf(animation.getAnimatedValue().toString());
				setWindowBackgroundAlpha(alpha);
			}
		});
		animator.setDuration(320);
		return animator;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
