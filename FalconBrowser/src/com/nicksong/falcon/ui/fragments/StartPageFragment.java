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

package com.nicksong.falcon.ui.fragments;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.nicksong.falcon.R;
import com.nicksong.falcon.data.GsonDataRequest;
import com.nicksong.falcon.data.RequestManager;
import com.nicksong.falcon.model.Weather;
import com.nicksong.falcon.model.WeatherDetail;
import com.nicksong.falcon.model.WeatherDetail.WeatherFuture;
import com.nicksong.falcon.ui.activities.FLAppication;
import com.nicksong.falcon.ui.activities.FalconBrowserActivity;
import com.nicksong.falcon.ui.adapter.ExpandableListViewAdapter;
import com.nicksong.falcon.ui.adapter.WeatherViewAdapter;
import com.nicksong.falcon.ui.managers.UIManager;
import com.nicksong.falcon.ui.views.DepthPageTransformer;
import com.nicksong.falcon.ui.views.ViewPagerCompat;
import com.nicksong.falcon.ui.views.ViewPagerCompat.OnPageChangeListener;
import com.nicksong.falcon.utils.Constants;
import com.nicksong.falcon.utils.NetUtil;
import com.nicksong.falcon.utils.WeatherUtils;

public abstract class StartPageFragment extends Fragment {
	private View viewOne = null;
	private View viewTwo = null;
	private View mParentView = null;
	private View[] views = null;
	protected UIManager mUIManager;
	private boolean mInitialized;
	private ExpandableListView mStartListView;
	private ExpandableListViewAdapter mStartListViewAdapter;
	// weather
	private Map<String, String> mMaps;
	private LocationClient mLocationClient;
	private static final int LOACTION_OK = 0;
	private Application mApplication;
	private GridView futureWeatherGrid;
	private LinearLayout news;
	private LinearLayout nav;
	private LinearLayout baidu;
	private LinearLayout shipin;
	private WeatherViewAdapter futureWeatherAdapter;
	private ArrayList<WeatherFuture> datas;
	private String currentCity = null;
	private TextView weatherTitle;
	private TextView locationTitle;
	private TextView pm25Title;
	private ImageView weatherRefresh;
	private TextView loadingTitle;

	// viewpager
	private ViewPagerCompat start_viewpager;
	private ImageView indicator_1;
	private ImageView indicator_2;

	public StartPageFragment() {
		mInitialized = false;
	}

	public interface OnStartPageItemClickedListener {
		public void onStartPageItemClicked(String url);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOACTION_OK:
				Bundle b = msg.getData();
				locationTitle.setText(b.getString("cityname") + b.getString("discname"));
				currentCity = b.getString("cityname");
				getBeautyData(currentCity);
				break;
			default:
				break;
			}
		}

	};

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (!mInitialized) {
			try {
				mUIManager = ((FalconBrowserActivity) activity).getUIManager();
			} catch (ClassCastException e) {
				Log.e("StartPageFragment.onAttach()", e.getMessage());
			}

			mInitialized = true;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mMaps = new HashMap<String, String>();
		datas = new ArrayList<WeatherFuture>();
		mApplication = FLAppication.getInstance();
		mLocationClient = ((FLAppication) mApplication).getLocationClient();
		mLocationClient.registerLocationListener(mLocationListener);
		if (NetUtil.isNetworkConnected()) {
			mLocationClient.start();
			mLocationClient.requestLocation();
		} else {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mParentView == null) {
			mParentView = inflater.inflate(getStartPageFragmentLayout(), container, false);
		}
		viewOne = inflater.inflate(R.layout.phone_start_page_one, null);
		viewTwo = inflater.inflate(R.layout.phone_start_page_two, null);
		views = new View[] { viewOne, viewTwo };
		initView();
		return mParentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		iniListener();
		super.onActivityCreated(savedInstanceState);
	}

	private void initView() {
		start_viewpager = (ViewPagerCompat) mParentView.findViewById(R.id.start_viewpager);
		start_viewpager.setPageTransformer(true, new DepthPageTransformer());
		indicator_1 = (ImageView) mParentView.findViewById(R.id.indicator_1);
		indicator_2 = (ImageView) mParentView.findViewById(R.id.indicator_2);
		futureWeatherGrid = (GridView) viewOne.findViewById(R.id.ll_future_weather);
		news = (LinearLayout) viewOne.findViewById(R.id.news);
		nav = (LinearLayout) viewOne.findViewById(R.id.nav);
		baidu = (LinearLayout) viewOne.findViewById(R.id.baidu);
		shipin = (LinearLayout) viewOne.findViewById(R.id.shipin);
		mStartListView = (ExpandableListView) viewTwo.findViewById(R.id.startListView);

		weatherTitle = (TextView) viewOne.findViewById(R.id.weatherTitle);
		locationTitle = (TextView) viewOne.findViewById(R.id.tvTitle);
		weatherRefresh = (ImageView) viewOne.findViewById(R.id.weatherRefresh);
		loadingTitle = (TextView) viewOne.findViewById(R.id.loadingTitle);
		pm25Title = (TextView) viewOne.findViewById(R.id.pm25Title);
		weatherTitle.setText("N/A");
		pm25Title.setText("N/A");
	}

	private void iniListener() {
		if (mUIManager != null) {
			mStartListViewAdapter = new ExpandableListViewAdapter(getActivity(), mUIManager);
			mStartListView.setAdapter(mStartListViewAdapter);
			mStartListView.expandGroup(0);
			mStartListView.expandGroup(1);
			mStartListView.expandGroup(2);
			mStartListView.expandGroup(3);
		}
		futureWeatherGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				mUIManager.loadUrl("http://m.weather.com.cn");
			}
		});

		start_viewpager.setAdapter(new PagerAdapter() {
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(views[position]);
				return views[position];
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {

				container.removeView(views[position]);
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public int getCount() {
				return views.length;
			}
		});
		start_viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					indicator_1.setImageResource(R.drawable.ic_pageindicator_current);
					indicator_2.setImageResource(R.drawable.ic_pageindicator_default);
					break;
				case 1:
					indicator_1.setImageResource(R.drawable.ic_pageindicator_default);
					indicator_2.setImageResource(R.drawable.ic_pageindicator_current);
					break;
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});
		weatherRefresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(currentCity)) {
					getBeautyData(currentCity);
				}
			}
		});
		news.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUIManager.loadUrl("http://m.toutiao.com");
			}
		});
		nav.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUIManager.loadUrl("http://h.xiami.com");
			}
		});
		baidu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUIManager.loadUrl("http://m.baidu.com");
			}
		});
		shipin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mUIManager.loadUrl("http://m.video.baidu.com");
			}
		});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	protected abstract int getStartPageFragmentLayout();

	private void getBeautyData(String city) {
		loadingTitle.setVisibility(View.VISIBLE);
		mMaps.put("output", "json");
		mMaps.put("ak", Constants.BAIDU_MAP_KAY);
		mMaps.put("mcode", Constants.BAIDU_MAP_MCODE);
		try {
			executeRequest(new GsonDataRequest<Weather>(Constants.BAIDU_BASE_URL + URLEncoder.encode(city, "utf-8"), Weather.class, mMaps,
					responseListener(), errorListener()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void executeRequest(Request<?> request) {
		RequestManager.addRequest(request, this);
	}

	private Response.Listener<Weather> responseListener() {
		return new Response.Listener<Weather>() {
			@Override
			public void onResponse(Weather weather) {
				loadingTitle.setVisibility(View.GONE);
				if (weather != null) {
					ArrayList<WeatherDetail> details = weather.results;
					if (details != null ) {
						WeatherDetail detail = details.get(0);
						// String currentCity = detail.currentCity;
						datas = detail.weather_data;
						weatherTitle.setText(datas.get(0).temperature);
						pm25Title.setText(WeatherUtils.setWeatherAQI(Integer.parseInt(detail.pm25)));
						futureWeatherAdapter = new WeatherViewAdapter(FLAppication.getContext(), datas);
						futureWeatherGrid.setAdapter(futureWeatherAdapter);
					}
				}
			}
		};
	}

	/**
	 * 获取数据错误回调
	 * 
	 * @return
	 */
	protected Response.ErrorListener errorListener() {

		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError volleyError) {
				volleyError.printStackTrace();
				// loadingTitle.setVisibility(View.GONE);
			}
		};
	}

	BDLocationListener mLocationListener = new BDLocationListener() {

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// do nothing
		}

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null || TextUtils.isEmpty(location.getCity())) {
				return;
			}
			String disctrictName = location.getDistrict();
			mLocationClient.stop();
			Message msg = mHandler.obtainMessage();
			msg.what = LOACTION_OK;
			Bundle b = new Bundle();
			b.putString("cityname", location.getCity());
			if (!TextUtils.isEmpty(disctrictName)) b.putString("discname", disctrictName);
			else
				b.putString("discname", "市区");
			msg.setData(b);
			mHandler.sendMessage(msg);
		}
	};

}
