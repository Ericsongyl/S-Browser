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

package com.nicksong.falcon.model;

import java.io.Serializable;
import java.util.ArrayList;

public class HotNews implements Serializable, IBaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String success;
	private String total;
	private ArrayList<NewsInfo> yi18;

	@Override
	public String toString() {
		return success + total + yi18.toString();
	}
}
