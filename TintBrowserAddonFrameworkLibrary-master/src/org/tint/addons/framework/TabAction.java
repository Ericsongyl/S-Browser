package org.tint.addons.framework;

import android.os.Parcel;

public class TabAction extends Action {

	protected String mTabId;
	
	public TabAction(int action) {
		this(action, null);
	}
	
	public TabAction(int action, String tabId) {
		super(action);
		mTabId = tabId;
	}
	
	public TabAction(Parcel in, int action) {
		super(action);
		mTabId = in.readString();
	}
	
	public String getTabId() {
		return mTabId;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(mTabId);
	}
	
	public static TabAction createCloseTabAction() {
		return new TabAction(ACTION_CLOSE_TAB);
	}
	
	public static TabAction createCloseTabAction(String tabId) {
		return new TabAction(ACTION_CLOSE_TAB, tabId);
	}
	
	public static TabAction createBrowseStopAction() {
		return new TabAction(ACTION_BROWSE_STOP);
	}
	
	public static TabAction createBrowseStopAction(String tabId) {
		return new TabAction(ACTION_BROWSE_STOP, tabId);
	}
	
	public static TabAction createBrowseReloadAction() {
		return new TabAction(ACTION_BROWSE_RELOAD);
	}
	
	public static TabAction createBrowseReloadAction(String tabId) {
		return new TabAction(ACTION_BROWSE_RELOAD, tabId);
	}
	
	public static TabAction createBrowseForwardAction() {
		return new TabAction(ACTION_BROWSE_FORWARD);
	}
	
	public static TabAction createBrowseForwardAction(String tabId) {
		return new TabAction(ACTION_BROWSE_FORWARD, tabId);
	}
	
	public static TabAction createBrowseBackAction() {
		return new TabAction(ACTION_BROWSE_BACK);
	}
	
	public static TabAction createBrowseBackAction(String tabId) {
		return new TabAction(ACTION_BROWSE_BACK, tabId);
	}

}
