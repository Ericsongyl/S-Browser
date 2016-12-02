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

package com.nicksong.falcon.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.nicksong.falcon.R;
import com.nicksong.falcon.data.VolleyTool;
import com.nicksong.falcon.model.WeatherDetail.WeatherFuture;
import com.nicksong.falcon.utils.DateUtil;
import com.nicksong.falcon.utils.NetUtil;
import com.nicksong.falcon.utils.WeatherUtils;

public class WeatherViewAdapter extends BaseAdapter {
	private Context context;
	ArrayList<WeatherFuture> datas;
	private LayoutInflater listContainer;

	public WeatherViewAdapter(Context context, ArrayList<WeatherFuture> datas) {
		this.context = context;
		listContainer = LayoutInflater.from(context);
		this.datas = datas;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = listContainer.inflate(R.layout.weather_gridview_item,
					null);
			holder.day_icon = (ImageView) convertView
					.findViewById(R.id.day_icon);
			holder.day_weather = (TextView) convertView
					.findViewById(R.id.day_weather);
			holder.day_wind = (TextView) convertView
					.findViewById(R.id.day_wind);
			holder.day_temperature = (TextView) convertView
					.findViewById(R.id.day_temperature);
			holder.day_week = (TextView) convertView
					.findViewById(R.id.day_week);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (NetUtil.isNetworkConnected()) {
			holder.day_weather.setText(datas.get(position).weather);
			holder.day_wind.setText(datas.get(position).wind);
			holder.day_week.setText(datas.get(position).date);
			holder.day_temperature.setText(datas.get(position).temperature);
			holder.day_icon.setImageResource(WeatherUtils.getWeatherIcon(datas.get(position).weather));
					
		} else {
			holder.day_weather.setText("N/A");
			holder.day_wind.setText("N/A");
			holder.day_week.setText("N/A");	
			holder.day_temperature.setText("N/A");
			holder.day_icon.setImageResource(R.drawable.icon_default);
		}
		return convertView;
	}

	class ViewHolder {
		ImageView day_icon;
		TextView day_weather;
		TextView day_temperature;
		TextView day_wind;
		TextView day_week;
	}
}
