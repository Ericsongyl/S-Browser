package com.nicksong.falcon.data;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonDataRequest<T> extends Request<T> {

	protected final static String TAG = "GsonRequest";
	public static final int TIMEOUT_ERROR = 600;
	public static final int BUSSINESS_ERROR = 610;

	private final Gson mGson = new Gson();
	private final Class<T> mClazz;
	private final Response.Listener<T> mListener;
	private Map<String, String> mMaps;

	public GsonDataRequest(String url, Class<T> clazz, Map<String, String> mMaps, Response.Listener<T> listener, Response.ErrorListener errorListener) {
		super(Request.Method.POST, url, errorListener);
		this.mClazz = clazz;
		this.mListener = listener;
		this.mMaps = mMaps;
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		// TODO Auto-generated method stub
		return mMaps;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			return Response.success(mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T t) {
		mListener.onResponse(t);
	}
}
