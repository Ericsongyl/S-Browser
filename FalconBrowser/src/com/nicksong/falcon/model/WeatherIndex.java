package com.nicksong.falcon.model;

import java.io.Serializable;

public class WeatherIndex implements Serializable, IBaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String title;
	public String zs;
	public String tipt;
	public String des;

	@Override
	public String toString() {
		return title + zs + tipt + des;
	}

}
