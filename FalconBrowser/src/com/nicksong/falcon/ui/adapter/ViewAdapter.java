package com.nicksong.falcon.ui.adapter;

import java.util.ArrayList;

import android.view.View.OnClickListener;
import android.widget.BaseAdapter;

import com.nicksong.falcon.model.IBaseModel;

public abstract class ViewAdapter<T extends IBaseModel> extends BaseAdapter {
	private ArrayList<T> mDataList = new ArrayList<T>();
	private OnClickListener mListener;

	public ViewAdapter() {
	}

	public ViewAdapter(OnClickListener listener) {
		mListener = listener;

	}

	public void add(ArrayList<T> list) {
		if (list == null) {
			return;
		}
		int size = mDataList.size();
		while (!list.isEmpty()) {
			T t = list.remove(0);
			if (t == null) {
				continue;
			}
			mDataList.add(t);
		}
		if (mDataList.size() != size) {
			notifyDataSetChanged();
		}
	}

	public boolean addOnly(T t) {
		if (t == null) {
			return false;
		}
		mDataList.add(t);
		return true;
	}

	public void set(ArrayList<T> list) {
		if (list == null) {
			mDataList.clear();
		} else {
			mDataList = list;
		}
		notifyDataSetChanged();
	}

	public void clear() {
		set(null);
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public T getItem(int position) {
		if (position < 0 || position >= mDataList.size()) {
			return null;
		}
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	public OnClickListener getOnClickListener() {
		return mListener;
	}

	public void setOnClickListener(OnClickListener listener) {
		mListener = listener;
	}

	public ArrayList<T> getDataList() {
		return mDataList;
	}

}
