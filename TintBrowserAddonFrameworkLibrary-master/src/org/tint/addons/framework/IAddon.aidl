/*
 * Tint Browser for Android
 * 
 * Copyright (C) 2012 - to infinity and beyond J. Devauchelle and contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tint.addons.framework;

import org.tint.addons.framework.Action;

/**
 * AIDL interface for an addon service.
 */
interface IAddon {
	void onBind();
	void onUnbind();

	String getName();
	String getShortDescription();
	String getDescription();
	String getContact();
	
	int getCallbacks();
	
	List<Action> onPageStarted(String tabId, String url);
	List<Action> onPageFinished(String tabId, String url);
	
	List<Action> onTabOpened(String tabId);
	List<Action> onTabClosed(String tabId);
	List<Action> onTabSwitched(String tabId);
	
	String getContributedMainMenuItem(String currentTabId, String currentTitle, String currentUrl);
	List<Action> onContributedMainMenuItemSelected(String currentTabId, String currentTitle, String currentUrl);
	
	String getContributedLinkContextMenuItem(String currentTabId, int hitTestResult, String url);
	List<Action> onContributedLinkContextMenuItemSelected(String currentTabId, int hitTestResult, String url);
	
	String getContributedHistoryBookmarksMenuItem(String currentTabId);
	List<Action> onContributedHistoryBookmarksMenuItemSelected(String currentTabId);
	
	String getContributedBookmarkContextMenuItem(String currentTabId);
	List<Action> onContributedBookmarkContextMenuItemSelected(String currentTabId, String title, String url);
	
	String getContributedHistoryContextMenuItem(String currentTabId);
	List<Action> onContributedHistoryContextMenuItemSelected(String currentTabId, String title, String url);
	
	List<Action> onUserConfirm(String currentTabId, int questionId, boolean positiveAnswer);
	List<Action> onUserInput(String currentTabId, int questionId, boolean cancelled, String userInput);
	List<Action> onUserChoice(String currentTabId, int questionId, boolean cancelled, int userChoice);
	
	void showAddonSettingsActivity();	
}