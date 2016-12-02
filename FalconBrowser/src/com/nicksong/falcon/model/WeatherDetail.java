package com.nicksong.falcon.model;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherDetail implements Serializable, IBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String currentCity;
	public String pm25;
	public ArrayList<WeatherIndex> index;
    public ArrayList<WeatherFuture> weather_data;
    
	public  class WeatherIndex {
		public String title;
		public String zs;
		public String tipt;
		public String des;
	}
	public   class WeatherFuture{
		public String date;
		public String dayPictureUrl;
		public String nightPictureUrl;
		public String weather;
		public String wind;
		public String temperature;
	}
	@Override
	public String toString() {
		return currentCity + pm25 + index.toString();
	}
}
