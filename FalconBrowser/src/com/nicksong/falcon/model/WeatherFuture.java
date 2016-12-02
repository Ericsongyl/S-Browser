package com.nicksong.falcon.model;

import java.io.Serializable;

public class WeatherFuture implements Serializable, IBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String date;
	public String dayPictureUrl;
	public String nightPictureUrl;
	public String weather;
	public String wind;
	public String temperature;

	@Override
	public String toString() {
		return date + dayPictureUrl + nightPictureUrl + weather + wind + temperature;
	}

}
