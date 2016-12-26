package com.nicksong.falcon.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nicksong.falcon.R;
import com.nicksong.falcon.ui.managers.UIManager;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
	public String[] group = { "资讯", "视频", "购物", "生活" , "旅游"};
	public String[][] gridViewChild = { { "腾讯", "搜狐", "新浪", "网易", "凤凰" }, { "优酷", "爱奇艺", "乐视", "PPTV" }, { "淘宝", "天猫", "京东", "唯品会", "1号店" },
			{ "58同城", "赶集网", "大众点评", "百姓网" }, {"携程", "同程", "途牛", "艺龙"} };
	public String[][] gridViewSite = { { "http://3g.qq.com", "http://m.souhu.com", "http://sina.cn", "http://3g.163.com", "http://i.ifeng.com" },
			{ "http://m.youku.com", "http://m.iqiyi.com", "http://m.letv.com", "http://m.pptv.com" },
			{ "http://m.taobao.com", "http://m.tmall.com", "http://m.jd.com", "http://m.vip.com", "http://m.yhd.com" },
			{ "http://m.58.com", "http://wap.ganji.com", "http://wap.dianping.com", "http://shanghai.baixing.com" },
			{"http://m.ctrip.com/html5/", "http://m.ly.com/", "https://m.tuniu.com/", "http://m.elong.com/"}};
	String[][] child = { { "" }, { "" }, { "" }, { "" }, { "" } };
	LayoutInflater mInflater;
	Context context;
	UIManager mUIManager;

	public ExpandableListViewAdapter(Context context, UIManager mUIManager) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		this.mUIManager = mUIManager;
		this.context = context;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return child[groupPosition][childPosition];
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			mViewChild = new ViewChild();
			convertView = mInflater.inflate(R.layout.channel_expandablelistview_item, null);
			mViewChild.gridView = (GridView) convertView.findViewById(R.id.channel_item_child_gridView);
			convertView.setTag(mViewChild);
		} else {
			mViewChild = (ViewChild) convertView.getTag();
		}

		SimpleAdapter mSimpleAdapter = new SimpleAdapter(context, setGridViewData(gridViewChild[groupPosition]), R.layout.channel_gridview_item,
				new String[] { "channel_gridview_item" }, new int[] { R.id.channel_gridview_item });
		mViewChild.gridView.setAdapter(mSimpleAdapter);
		mViewChild.gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				if (view instanceof TextView) {
					if (view instanceof TextView) {
						if (mUIManager != null) mUIManager.loadUrl(gridViewSite[groupPosition][position]);
						// Log.e("YM", gridViewSite[groupPosition][position]);
					}
				}
			}
		});
		mViewChild.gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		return convertView;
	}

	/**
	 * 设置gridview数据
	 * 
	 * @param data
	 * @return
	 */
	private ArrayList<HashMap<String, Object>> setGridViewData(String[] data) {
		ArrayList<HashMap<String, Object>> gridItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < data.length; i++) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("channel_gridview_item", data[i]);
			gridItem.add(hashMap);
		}
		return gridItem;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return child[groupPosition].length;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return group[groupPosition];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return group.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			mViewChild = new ViewChild();
			convertView = mInflater.inflate(R.layout.channel_expandablelistview, null);
			mViewChild.textView = (TextView) convertView.findViewById(R.id.channel_group_name);
			mViewChild.imageView = (ImageView) convertView.findViewById(R.id.channel_imageview_orientation);
			convertView.setTag(mViewChild);
		} else {
			mViewChild = (ViewChild) convertView.getTag();
		}
		if (isExpanded) {
			mViewChild.imageView.setImageResource(R.drawable.channel_expandablelistview_top_icon);
		} else {
			mViewChild.imageView.setImageResource(R.drawable.channel_expandablelistview_bottom_icon);
		}
		mViewChild.textView.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	ViewChild mViewChild;

	static class ViewChild {
		ImageView imageView;
		TextView textView;
		GridView gridView;
	}
}
