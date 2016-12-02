package com.nicksong.falcon.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ScrollView;

import com.nicksong.falcon.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class PullLayout extends ScrollView {

	private View rl_top;
	private View ll_weather;
	private View ll_content;
	private View tv;
	// private EyeView ev;
	private ObjectAnimator oa;
	private float lastY = -1;
	private float detalY = -1;
	private int range;
	private int tvHeight;
	private int tvWidth;
	private boolean isTouchOrRunning;
	private boolean isActionCancel;

	public PullLayout(Context context) {
		super(context);
	}

	public PullLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		setVerticalScrollBarEnabled(false);
		rl_top = findViewById(R.id.rl_top);
		ll_content = findViewById(R.id.ll_content);
		tv = findViewById(R.id.tvTitle);
		// ev = (EyeView) findViewById(R.id.ev);
		ll_weather = findViewById(R.id.ll_weather);

		rl_top.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				rl_top.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				range = rl_top.getHeight();
				scrollTo(0, range);
				rl_top.getLayoutParams().height = range;
			}
		});
		tv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				tv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				tvHeight = tv.getHeight();
				tvWidth = tv.getWidth();
				ViewHelper.setTranslationY(ll_content, tvHeight);
			}
		});

		/*ev.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				close();
			}
		});*/

		tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				open();
			}
		});

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isActionCancel = false;
			isTouchOrRunning = true;
			lastY = ev.getY();
			break;
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (oa != null && oa.isRunning()) {
			ev.setAction(MotionEvent.ACTION_UP);
			isActionCancel = true;
		}
		if (isActionCancel && ev.getAction() != MotionEvent.ACTION_DOWN) {
			return false;
		}
		if (ev.getActionIndex() != 0 && getScrollY() < range) {
			ev.setAction(MotionEvent.ACTION_UP);
			isActionCancel = true;
		}

		switch (ev.getAction()) {
		case MotionEvent.ACTION_MOVE:
			isTouchOrRunning = true;
			if (getScrollY() != 0) {
				detalY = 0;
				lastY = ev.getY();
			} else {
				detalY = ev.getY() - lastY;
				if (detalY > 0) {
					setT((int) -detalY / 5);
					return true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			isTouchOrRunning = false;
			if (getScrollY() < range) {
				if (detalY != 0) {
					reset();
				} else {
					toggle();
				}
				return true;
			}
			break;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (t > range) {
			return;
		} else if (!isTouchOrRunning && t != range) {
			scrollTo(0, range);
		} else {
			animateScroll(t);
		}
	}

	public void setT(int t) {
		scrollTo(0, t);
		if (t < 0) {
			animatePull(t);
		}
	}

	private void animateScroll(int t) {
		float percent = (float) t / range;
		ViewHelper.setTranslationY(rl_top, t);
		ViewHelper.setTranslationY(ll_content, tvHeight * percent);
		ViewHelper.setTranslationX(tv, tvWidth * (1 - percent) / 2f);
		ViewHelper.setTranslationY(tv, t + tvHeight * (1 - percent) / 2f);
		ViewHelper.setTranslationY(ll_weather, -t / 3);
	}

	private void animatePull(int t) {
		rl_top.getLayoutParams().height = range - t;
		rl_top.requestLayout();
		float percent = (float) t / range;
		ViewHelper.setTranslationX(tv, tvWidth * (1 - percent) / 2f);
		ViewHelper.setTranslationY(ll_weather, t / 3);
	}

	public void toggle() {
		if (isOpen()) {
			close();
		} else {
			open();
		}
	}

	private Status status;

	public enum Status {
		Open, Close;
	}

	public boolean isOpen() {
		return status == Status.Open;
	}

	private void reset() {
		if (oa != null && oa.isRunning()) {
			return;
		}
		oa = ObjectAnimator.ofInt(this, "t", (int) -detalY / 5, 0);
		oa.setDuration(150);
		oa.start();
	}

	public void close() {
		if (oa != null && oa.isRunning()) {
			return;
		}
		oa = ObjectAnimator.ofInt(this, "t", getScrollY(), range);
		oa.setInterpolator(new DecelerateInterpolator());
		oa.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				isTouchOrRunning = true;
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				isTouchOrRunning = false;
				status = Status.Close;
			}

			@Override
			public void onAnimationCancel(Animator arg0) {

			}
		});
		oa.setDuration(250);
		oa.start();
	}

	public void open() {
		if (oa != null && oa.isRunning()) {
			return;
		}
		oa = ObjectAnimator.ofInt(this, "t", getScrollY(), (int) (-getScrollY() / 2.2f), 0);
		oa.setInterpolator(new DecelerateInterpolator());
		oa.addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				isTouchOrRunning = true;
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				isTouchOrRunning = false;
				status = Status.Open;
			}

			@Override
			public void onAnimationCancel(Animator arg0) {

			}
		});
		oa.setDuration(400);
		oa.start();
	}

}
