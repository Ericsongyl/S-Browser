package com.nicksong.falcon.model;

import java.io.Serializable;
import java.util.ArrayList;

public class WeatherNow implements Serializable, IBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String currentCity;
	public String pm25;
	public ArrayList<WeatherIndex> index;

	@Override
	public String toString() {
		return currentCity + pm25 + index.toString();
	}
}
