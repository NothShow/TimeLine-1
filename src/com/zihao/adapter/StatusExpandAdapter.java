package com.zihao.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zihao.R;

public class StatusExpandAdapter extends BaseExpandableListAdapter {
	private LayoutInflater inflater = null;
	private ExpandableListView elvCompanyContacts;
	private Context context;
	
	private ArrayList<HashMap<String, Object>> father_array;
	private ArrayList<ArrayList<HashMap<String, Object>>> son_array;

	/**
	 * 构造方法
	 * 
	 * @param context
	 * @param oneList
	 */
	public StatusExpandAdapter(Context context, ExpandableListView elvCompanyContacts,
			ArrayList<HashMap<String, Object>> father_array,
			ArrayList<HashMap<String, Object>> son_array) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.elvCompanyContacts = elvCompanyContacts;
		this.father_array = father_array;
		this.son_array = itjj(this.father_array, son_array);
	}
	
	public void setItemFatherData (ArrayList<HashMap<String, Object>> father_array) {
		this.father_array = father_array;
	}
	
	public void setItemSonData (ArrayList<HashMap<String, Object>> son_array) {
		this.son_array = itjj(this.father_array, son_array);
	}

	public ArrayList<ArrayList<HashMap<String, Object>>> itjj(ArrayList<HashMap<String, Object>> father_array,
			ArrayList<HashMap<String, Object>> son_array) {
		ArrayList<ArrayList<HashMap<String, Object>>> son_list = new ArrayList<ArrayList<HashMap<String,Object>>>();
		for (int i = 0; i < father_array.size(); i++) {
			ArrayList<HashMap<String, Object>> son = new ArrayList<HashMap<String,Object>>();
			for (int j = 0; j < son_array.size(); j++) {
				if (son_array.get(j).get("roomerPeriod").toString().equals(father_array.get(i).get("period").toString())) {
					son.add(son_array.get(j));
				}
			}
			son_list.add(son);
		}
		return son_list;
	}
	
	/**
	 * 返回一级Item总数
	 */
	@Override
	public int getGroupCount() {
		Log.e("fasize", father_array.size()+"");
		return father_array.size();
	}

	/**
	 * 返回二级Item总数
	 */
	@Override
	public int getChildrenCount(int groupPosition) {
		/*if (son_array.get(groupPosition).size() <= 0) {
			return 1;
		} else {
			return son_array.get(groupPosition).size();
		}*/
		Log.e("size", son_array.get(groupPosition).size()+"");
		return son_array.get(groupPosition).size();
	}

	/**
	 * 获取一级Item内容
	 */
	@Override
	public Object getGroup(int groupPosition) {
		return father_array.get(groupPosition);
	}

	/**
	 * 获取二级Item内容
	 */
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return son_array.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		GroupViewHolder holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.group_status_item, null);
			holder = new GroupViewHolder();
			holder.groupName = (TextView) convertView
					.findViewById(R.id.one_status_name);
			holder.groupName.setText(father_array.get(groupPosition).get("period").toString());
			convertView.setTag(holder);
		} else {
			holder = (GroupViewHolder) convertView.getTag();
		}
		

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewHolder viewHolder = null;
		if (convertView != null) {
			viewHolder = (ChildViewHolder) convertView.getTag();
		} else {
			viewHolder = new ChildViewHolder();
			convertView = inflater.inflate(R.layout.child_status_item, null);
			viewHolder.twoStatusTime = (TextView) convertView
					.findViewById(R.id.two_complete_time);
			convertView.setTag(viewHolder);
		}
		viewHolder.twoStatusTime.setText(son_array.get(groupPosition).get(childPosition).get("address").toString());
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "点我了", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
		for (int i = 0; i < father_array.size(); i++) {    
				elvCompanyContacts.expandGroup(i);    
		}    
	}
	
	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
		for (int i = 0; i < father_array.size(); i++) {    
			elvCompanyContacts.expandGroup(i);    
	} 
	}
	
	private class GroupViewHolder {
		TextView groupName;
	}

	private class ChildViewHolder {
		public TextView twoStatusTime;
	}

}
