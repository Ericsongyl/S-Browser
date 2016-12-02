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
import android.text.InputType;

public class AskUserInputAction extends BaseAskUserAction {

	private String mTitle;
	private String mMessage;
	private String mInputHint;
	private String mDefaultInput;
	private int mInputType;
	
	public AskUserInputAction(int id, String title, String message) {
		this(id, title, message, null, null, InputType.TYPE_CLASS_TEXT);
	}
	
	public AskUserInputAction(int id, String title, String message, String inputHint) {
		this(id, title, message, inputHint, null, InputType.TYPE_CLASS_TEXT);
	}
	
	public AskUserInputAction(int id, String title, String message, String inputHint, String defaultInput) {
		this(id, title, message, inputHint, defaultInput, InputType.TYPE_CLASS_TEXT);
	}
	
	public AskUserInputAction(int id, String title, String message, String inputHint, String defaultInput, int inputType) {
		super(ACTION_ASK_USER_INPUT, id);		
		
		mTitle = title;
		mMessage = message;
		mInputHint = inputHint;
		mDefaultInput = defaultInput;
		mInputType = inputType;
	}
	
	public AskUserInputAction(Parcel in) {
		super(in, ACTION_ASK_USER_INPUT);
		
		mTitle = in.readString();
		mMessage = in.readString();
		mInputHint = in.readString();
		mDefaultInput = in.readString();
		mInputType = in.readInt();
	}
	
	public String getTitle() {
		return mTitle;
	}

	public String getMessage() {
		return mMessage;
	}
	
	public String getInputHint() {
		return mInputHint;
	}
	
	public String getDefaultInput() {
		return mDefaultInput;
	}
	
	public int getInputType() {
		return mInputType;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		
		dest.writeString(mTitle);
		dest.writeString(mMessage);
		dest.writeString(mInputHint);
		dest.writeString(mDefaultInput);
		dest.writeInt(mInputType);
	}

}
