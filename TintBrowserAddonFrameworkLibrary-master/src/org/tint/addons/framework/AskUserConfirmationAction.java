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

import android.os.Parcel;

public class AskUserConfirmationAction extends BaseAskUserAction {
	
	private String mTitle;
	private String mMessage;
	private String mPositiveButtonCaption;
	private String mNegativeButtonCaption;

	public AskUserConfirmationAction(int id, String title, String message, String positiveButtonCaption, String negativeButtonCaption) {
		super(ACTION_ASK_USER_CONFIRMATION, id);
		
		mTitle = title;
		mMessage = message;
		mPositiveButtonCaption = positiveButtonCaption;
		mNegativeButtonCaption = negativeButtonCaption;
	}
	
	public AskUserConfirmationAction(Parcel in) {
		super(in, ACTION_ASK_USER_CONFIRMATION);
		
		mTitle = in.readString();
		mMessage = in.readString();
		mPositiveButtonCaption = in.readString();
		mNegativeButtonCaption = in.readString();
	}
	
	public String getTitle() {
		return mTitle;
	}

	public String getMessage() {
		return mMessage;
	}

	public String getPositiveButtonCaption() {
		return mPositiveButtonCaption;
	}

	public String getNegativeButtonCaption() {
		return mNegativeButtonCaption;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		
		dest.writeString(mTitle);
		dest.writeString(mMessage);
		dest.writeString(mPositiveButtonCaption);
		dest.writeString(mNegativeButtonCaption);
	}

}
