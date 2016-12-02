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

package com.nicksong.falcon.ui.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.nicksong.falcon.R;
import com.nicksong.falcon.utils.Constants;

public class PrivacyPreferencesFragment extends PreferenceFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences_privacy_settings);

		PreferenceScreen websiteSettings = (PreferenceScreen) findPreference(Constants.PREFERENCE_WEBSITES_SETTINGS);
		websiteSettings.setFragment(WebsitesSettingsFragment.class.getName());

		PreferenceScreen sslExceptionsSettings = (PreferenceScreen) findPreference(Constants.PREFERENCE_SSL_EXCEPTIONS);
		sslExceptionsSettings.setFragment(SslExceptionsFragment.class.getName());
	}

}
