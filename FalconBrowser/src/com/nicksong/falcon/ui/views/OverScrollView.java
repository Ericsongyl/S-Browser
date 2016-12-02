package com.nicksong.falcon.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 具有上下弹性滚动的ScrollView<br>
 * <br>
 * <strong>策略:</strong> 获取ScrollView的子视图并添加到自定义的{@link OverScrollWarpLayout}滚动视图中 将滚动视图添加到ScrollView作为子视图,所有的弹性滚动都由{@link OverScrollWarpLayout}来完成
 */
public class OverScrollView extends ScrollView {

	/**
	 * 滚动系数, 视图滚动距离与手指滑动距离的比值
	 */
	private static final float ELASTICITY_COEFFICIENT = 0.25f;

	/**
	 * 无弹性滚动状态
	 */
	private static final int NO_OVERSCROLL_STATE = 0;

	/**
	 * 上方弹性滚动状态
	 */
	private static final int TOP_OVERSCROLL_STATE = 1;

	/**
	 * 下方弹性滚动状态
	 */
	private static final int BOTTOM_OVERSCROLL_STATE = 2;

	/**
	 * 滚动最大高度,超过此高度不再滚动
	 */
	private static final int OVERSCROLL_MAX_HEIGHT = 1200;

	/**
	 * Sentinel value for no current active pointer. Used by {@link #mActivePointerId}.
	 */
	private static final int INVALID_POINTER = -1;

	/**
	 * 触发事件的高度默认阀值
	 */
	private static final int TRIGGER_HEIGHT = 120;

	/**
	 * 弹性滚动状态
	 */
	private int overScrollSate;

	/**
	 * 属性标志位:是否可以弹性滚动
	 */
	private boolean mIsUseOverScroll = true;

	/**
	 * 是否标记可以滚动
	 */
	private boolean isRecord;

	/**
	 * 自定义的弹性滚动视图
	 */
	private OverScrollWarpLayout mContentLayout;

	/**
	 * OverScroll监听器
	 */
	private OverScrollListener mOverScrollListener;

	/**
	 * OverScroll细致监听器
	 */
	private OverScrollTinyListener mOverScrollTinyListener;

	/**
	 * Scroll细致监听器
	 */
	private OnScrollListener mScrollListener;

	/**
	 * 最新一次的手指触摸位置
	 */
	private float mLastMotionY;

	/**
	 * 弹性滚动总距离,向下为负，向上为正
	 */
	private int overScrollDistance;

	/**
	 * 按在屏幕上的手指的id
	 */
	private int mActivePointerId = INVALID_POINTER;

	/**
	 * 是否触摸
	 */
	private boolean isOnTouch;

	/**
	 * 是否具有惯性
	 */
	private boolean isInertance;

	/**
	 * 是否使用惯性
	 */
	private boolean mIsUseInertance = true;

	/**
	 * 是否禁用快速滚动
	 */
	private boolean mIsBanQuickScroll;

	/**
	 * 惯性距离(与滑动速率有关)
	 */
	private int inertanceY;

	/**
	 * 触发事件的高度阀值，最小值为30
	 */
	private int mOverScrollTrigger = TRIGGER_HEIGHT;

	public OverScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initScrollView();
	}

	public OverScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public OverScrollView(Context context) {
		this(context, null);
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	private void initScrollView() {
		// 设置滚动无阴影( API Level 9 )
		if (Build.VERSION.SDK_INT >= 9) {
			setOverScrollMode(View.OVER_SCROLL_NEVER);
		} else {
			ViewCompat.setOverScrollMode(this, ViewCompat.OVER_SCROLL_NEVER);
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// 如果禁用，不做任何处理
		if (!mIsUseOverScroll) {
			return super.onInterceptTouchEvent(ev);
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (isOverScrolled()) {
				isRecord = true;
				// Remember where the motion event started
				mLastMotionY = (int) ev.getY();

				mActivePointerId = ev.getPointerId(0);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isRecord && Math.abs(ev.getY() - mLastMotionY) > 20) {
				return true;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			if (isRecord) {
				isRecord = false;
			}
		}
		return super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		isOnTouch = true;
		if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
			if (mOverScrollTinyListener != null) {
				mOverScrollTinyListener.scrollLoosen();
			}
			isOnTouch = false;
		}

		// 如果禁用，不做任何处理
		if (!mIsUseOverScroll) {
			return super.onTouchEvent(ev);
		}

		if (!isOverScrolled()) {
			mLastMotionY = (int) ev.getY();
			return super.onTouchEvent(ev);
		}

		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mActivePointerId = ev.getPointerId(0);
			mLastMotionY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			final int index = ev.getActionIndex();
			mLastMotionY = (int) ev.getY(index);
			mActivePointerId = ev.getPointerId(index);
			break;
		case MotionEvent.ACTION_POINTER_UP:
			onSecondaryPointerUp(ev);
			if (mActivePointerId != INVALID_POINTER) {
				mLastMotionY = (int) ev.getY(ev.findPointerIndex(mActivePointerId));
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (isRecord) {
				final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
				if (activePointerIndex == -1) {
					break;
				}

				final float y = ev.getY(activePointerIndex);
				// 滚动距离
				int deltaY = (int) (mLastMotionY - y);
				// 记录新的触摸位置
				mLastMotionY = y;

				if (Math.abs(overScrollDistance) >= OVERSCROLL_MAX_HEIGHT && overScrollDistance * deltaY > 0) {
					deltaY = 0;
				}

				// 如果滚动到ScrollView自身滚动边界，直接调用自身滚动
				if (overScrollDistance * (overScrollDistance + deltaY) < 0) {
					mContentLayout.smoothScrollToNormal();
					overScrollDistance = 0;
					break;
				}

				// 如果处于ScrollView滚动状态，直接调用ScrollView自身滚动
				if ((!isOnBottom() && overScrollDistance > 0) || (!isOnTop() && overScrollDistance < 0)) {
					mContentLayout.smoothScrollToNormal();
					overScrollDistance = 0;
					break;
				}

				if (overScrollDistance * deltaY > 0) {
					deltaY = (int) (deltaY * ELASTICITY_COEFFICIENT);
				}

				if (overScrollDistance == 0) {
					deltaY = (int) (deltaY * ELASTICITY_COEFFICIENT * 0.5f);
				}

				if (overScrollDistance == 0 && deltaY == 0) {
					break;
				}

				// 检测最终滚动距离，最大为20
				if (Math.abs(deltaY) > 20) {
					deltaY = deltaY > 0 ? 20 : -20;
				}

				// 记录滚动总距离
				overScrollDistance += deltaY;

				if (isOnTop() && overScrollDistance > 0 && !isOnBottom()) {
					overScrollDistance = 0;
					break;
				}

				if (isOnBottom() && overScrollDistance < 0 && !isOnTop()) {
					overScrollDistance = 0;
					break;
				}

				// 滚动视图
				mContentLayout.smoothScrollBy(0, deltaY);

				if (mOverScrollTinyListener != null) {
					mOverScrollTinyListener.scrollDistance(deltaY, overScrollDistance);
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mContentLayout.smoothScrollToNormal();
			overScrollTrigger();
			// 重置滑动总距离
			overScrollDistance = 0;
			// 重置标记
			isRecord = false;
			// 重置手指触摸id
			mActivePointerId = INVALID_POINTER;
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 功能描述: 防止出现pointerIndex out of range异常<br>
	 * 
	 * @param ev
	 */
	private void onSecondaryPointerUp(MotionEvent ev) {
		final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
		final int pointerId = ev.getPointerId(pointerIndex);
		if (pointerId == mActivePointerId) {
			// This was our active pointer going up. Choose a new
			// active pointer and adjust accordingly.
			// Make this decision more intelligent.
			final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
			mLastMotionY = (int) ev.getY(newPointerIndex);
			mActivePointerId = ev.getPointerId(newPointerIndex);
		}

	}

	public boolean isOverScrolled() {
		return isOnTop() || isOnBottom();
	}

	private boolean isOnTop() {
		return getScrollY() == 0;
	}

	private boolean isOnBottom() {
		return getScrollY() + getHeight() == mContentLayout.getHeight();
	}

	/**
	 * 功能描述:初始化滚动视图 <br>
	 * <br>
	 * <strong>策略:</strong> 获取ScrollView的子视图并添加到自定义的{@link OverScrollWarpLayout}滚动视图中 将滚动视图添加到ScrollView作为子视图
	 */
	private void initOverScrollLayout() {
		// 必须设置为true,否则添加子视图时高度不会填充到整个ScrollView的高度
		setFillViewport(true);
		if (mContentLayout == null) {
			// 获取ScrollView的子视图
			View child = getChildAt(0);
			// 初始化弹性滚动视图
			mContentLayout = new OverScrollWarpLayout(getContext());
			// 移除ScrollView所有视图
			this.removeAllViews();
			// 将原先ScrollView子视图加入到弹性滚动视图中
			mContentLayout.addView(child);
			// 添加弹性滚动视图，作为ScrollView子视图
			this.addView(mContentLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		}
		// mIsUseOverScroll = true;
	}

	/**
	 * 功能描述:设置是否可以弹性滚动 <br>
	 * 
	 * @param isOverScroll
	 */
	public void setOverScroll(boolean isOverScroll) {
		mIsUseOverScroll = isOverScroll;
	}

	/**
	 * 功能描述: 设置是否使用惯性<br>
	 * 
	 * @param isInertance
	 */
	public void setUseInertance(boolean isInertance) {
		mIsUseInertance = isInertance;
	}

	@Override
	protected void onAttachedToWindow() {
		initOverScrollLayout();
		super.onAttachedToWindow();
	}

	/**
	 * 功能描述: 获取弹性状态<br>
	 * 
	 * @return
	 */
	public int getScrollState() {
		invalidateState();
		return overScrollSate;
	}

	/**
	 * 功能描述: 刷新弹性滚动状态<br>
	 */
	private void invalidateState() {

		if (mContentLayout.getScrollerCurrY() == 0) {
			overScrollSate = NO_OVERSCROLL_STATE;
		}

		if (mContentLayout.getScrollerCurrY() < 0) {
			overScrollSate = TOP_OVERSCROLL_STATE;
		}

		if (mContentLayout.getScrollerCurrY() > 0) {
			overScrollSate = BOTTOM_OVERSCROLL_STATE;
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX,
			int maxOverScrollY, boolean isTouchEvent) {
		// Log.v("test", "deltaY "+deltaY+"   scrollY "+scrollY);
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		if (mScrollListener != null && overScrollDistance == 0) {
			mScrollListener.onScroll(l, t, oldl, oldt);
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@Override
	protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
		if (mIsUseInertance && !isInertance && scrollY != 0) {
			isInertance = true;
		}
		if (clampedY && !isOnTouch && isInertance) {
			mContentLayout.smoothScrollBy(0, inertanceY);
			mContentLayout.smoothScrollToNormal();
			inertanceY = 0;
		}
		super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
	}

	/**
	 * 功能描述: 设置OverScroll滚动监听器<br>
	 * 
	 * @param listener
	 */
	public void setOverScrollListener(OverScrollListener listener) {
		mOverScrollListener = listener;
	}

	/**
	 * 功能描述: 设置OverScroll滚动监听器<br>
	 * 
	 * @param listener
	 */
	public void setOverScrollTinyListener(OverScrollTinyListener listener) {
		mOverScrollTinyListener = listener;
	}

	/**
	 * 功能描述: 设置Scroll滚动监听器<br>
	 * 
	 * @param listener
	 */
	public void setOnScrollListener(OnScrollListener listener) {
		mScrollListener = listener;
	}

	/**
	 * 设置OverScrollListener出发阀值
	 * 
	 * @param height
	 */
	public void setOverScrollTrigger(int height) {
		if (height >= 30) {
			mOverScrollTrigger = height;
		}
	}

	private void overScrollTrigger() {
		if (mOverScrollListener == null) {
			return;
		}

		if (overScrollDistance > mOverScrollTrigger && isOnBottom()) {
			mOverScrollListener.footerScroll();
		}

		if (overScrollDistance < -mOverScrollTrigger && isOnTop()) {
			mOverScrollListener.headerScroll();
		}

	}

	public void setQuickScroll(boolean isEnable) {
		mIsBanQuickScroll = !isEnable;
	}

	@Override
	public void computeScroll() {
		if (!mIsBanQuickScroll) {
			super.computeScroll();
		}
	}

	/**
	 * 获取ScrollView可滚动高度
	 * 
	 * @return
	 */
	public int getScrollHeight() {
		return mContentLayout.getHeight() - getHeight();
	}

	@Override
	public void fling(int velocityY) {
		inertanceY = 50 * velocityY / 5000;
		super.fling(velocityY);
	}

	/**
	 * 当OverScroll超出一定值时，调用此监听
	 * 
	 * @author King
	 * @since 2014-4-9 下午4:36:29
	 */
	public interface OverScrollListener {

		/**
		 * 顶部
		 */
		void headerScroll();

		/**
		 * 底部
		 */
		void footerScroll();

	}

	/**
	 * 每当OverScroll时，都能触发的监听
	 * 
	 * @author King
	 * @since 2014-4-9 下午4:39:06
	 */
	public interface OverScrollTinyListener {

		/**
		 * 滚动距离
		 * 
		 * @param tinyDistance
		 *            当前滚动的细小距离
		 * @param totalDistance
		 *            滚动的总距离
		 */
		void scrollDistance(int tinyDistance, int totalDistance);

		/**
		 * 滚动松开
		 */
		void scrollLoosen();
	}

	/**
	 * 普通滚动监听器<br>
	 * overScroll距离为0切无惯性时调用
	 * 
	 * @author king
	 * 
	 */
	public interface OnScrollListener {
		void onScroll(int l, int t, int oldl, int oldt);
	}
}
