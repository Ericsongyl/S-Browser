/*
 * Tint Browser for Android
 * 
 * Copyright (C) 2012 - to infinity and beyond J. Devauchelle and contributors.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.nicksong.falcon.addons.executors;

import org.tint.addons.framework.Action;

import android.content.Context;

import com.nicksong.falcon.addons.Addon;
import com.nicksong.falcon.ui.components.CustomWebView;
import com.nicksong.falcon.ui.managers.UIManager;

public abstract class BaseActionExecutor {

	protected Context mContext;
	protected UIManager mUIManager;
	protected CustomWebView mWebView;
	protected Addon mAddon;

	private void init(Context context, UIManager uiManager, CustomWebView webView, Addon addon, Action addonAction) {
		mContext = context;
		mUIManager = uiManager;
		mWebView = webView;
		mAddon = addon;

		finishInit(addonAction);
	}

	protected abstract void finishInit(Action addonAction);

	protected abstract void internalExecute();

	public synchronized void execute(Context context, UIManager uiManager, CustomWebView webView, Addon addon, Action addonAction) {
		init(context, uiManager, webView, addon, addonAction);
		internalExecute();
	}

}
