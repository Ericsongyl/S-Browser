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
import android.os.Parcelable;

public class Action implements Parcelable {
	
	public static final int ACTION_NONE = 0;
	
	public static final int ACTION_SHOW_TOAST = 1;
	public static final int ACTION_SHOW_DIALOG = 2;
	public static final int ACTION_ASK_USER_CONFIRMATION = 3;
	public static final int ACTION_ASK_USER_INPUT = 4;
	public static final int ACTION_ASK_USER_CHOICE = 5;
		
	public static final int ACTION_OPEN_TAB = 6;
	public static final int ACTION_CLOSE_TAB = 7;
	
	public static final int ACTION_LOAD_URL = 8;
	
	public static final int ACTION_BROWSE_STOP = 9;
	public static final int ACTION_BROWSE_RELOAD = 10;
	public static final int ACTION_BROWSE_FORWARD = 11;
	public static final int ACTION_BROWSE_BACK = 12;
	
	public static final Parcelable.Creator<Action> CREATOR	= new Parcelable.Creator<Action>() {

		public Action createFromParcel(Parcel in) {
			int action = in.readInt();
			
			switch (action) {
			case ACTION_CLOSE_TAB:
			case ACTION_BROWSE_STOP:
			case ACTION_BROWSE_RELOAD:
			case ACTION_BROWSE_FORWARD:
			case ACTION_BROWSE_BACK:
				return new TabAction(in, action);
			case ACTION_SHOW_TOAST:
				return new ShowToastAction(in);
			case ACTION_SHOW_DIALOG:
				return new ShowDialogAction(in);
			case ACTION_ASK_USER_CONFIRMATION:
				return new AskUserConfirmationAction(in);
			case ACTION_ASK_USER_INPUT:
				return new AskUserInputAction(in);
			case ACTION_ASK_USER_CHOICE:
				return new AskUserChoiceAction(in);
			case ACTION_OPEN_TAB:
				return new OpenTabAction(in);
			case ACTION_LOAD_URL:
				return new LoadUrlAction(in);
			default:
				return new Action(ACTION_NONE);
			}
		}

		public Action[] newArray(int size) {
			return new Action[size];
		}
	};
	
	protected int mAction;
	
	public Action(int action) {
		mAction = action;
	} 
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mAction);
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	public int getAction() {
		return mAction;
	}
}
