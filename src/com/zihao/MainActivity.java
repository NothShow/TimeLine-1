package com.zihao;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.zihao.adapter.StatusExpandAdapter;

public class MainActivity extends Activity {

	private final static String TASK_INFO_URL = "http://130.234.7.253/SMUProject_Server/task_info.php";

	private ExpandableListView expandlistView;
	private StatusExpandAdapter statusAdapter;

	private ArrayList<HashMap<String, Object>> father_array = new ArrayList<HashMap<String,Object>>();;
	private ArrayList<HashMap<String, Object>> son_array = new ArrayList<HashMap<String,Object>>();;

	private RequestQueue mQueue = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		expandlistView = (ExpandableListView) findViewById(R.id.expandlist);
		initExpandListView();
	}

	/**
	 * 初始化可拓展列表
	 */
	private void initExpandListView() {
		statusAdapter = new StatusExpandAdapter(this, expandlistView, father_array, son_array);
		//		statusAdapter = new StatusExpandAdapter(context, getListData());
		getFatherData();
		getSonData();

		expandlistView.setAdapter(statusAdapter);
		expandlistView.setGroupIndicator(null); // 去掉默认带的箭头
		expandlistView.setSelection(0);// 设置默认选中项

		/*// 遍历所有group,将所有项设置成默认展开
		int groupCount = expandlistView.getExpandableListAdapter().getGroupCount();
		Log.e("groupCount", groupCount+"");
		for (int i = 0; i < groupCount; i++) {
			Log.e("haha", "hahaha");
			if (expandlistView.isGroupExpanded(groupCount)) {
			}
		}*/
	}

	private void getFatherData () {
		for (int i = 0; i < 4; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("period", i + 1);
			father_array.add(map);
		}
		statusAdapter.setItemFatherData(father_array);
		statusAdapter.notifyDataSetChanged();

	}

	private void getSonData () {
		JSONObject json = new JSONObject();
		try {
			json.put("userName", "");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		mQueue = Volley.newRequestQueue(getApplicationContext());
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(TASK_INFO_URL, 
				new Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				try {
					for (int i = 0; i < response.length(); i++) {
						JSONObject jObj = (JSONObject) response.get(i);
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("roomerPeriod", jObj.get("roomer_period"));
						map.put("address", jObj.get("house_address"));
						son_array.add(map);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				statusAdapter.setItemSonData(son_array);
				statusAdapter.notifyDataSetChanged();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("TAG", error.getMessage(), error);
			}
		});
		mQueue.add(jsonArrayRequest);
	}


	/*	private List<GroupStatusEntity> getListData() {
		List<GroupStatusEntity> groupList;
		String[] strArray = new String[] { "10月22日", "10月23日", "10月25日" };
		String[][] childTimeArray = new String[][] {
				{ "俯卧撑十次", "仰卧起坐二十次", "大喊我爱紫豪二十次", "每日赞紫豪一次" },
				{ "亲，快快滴点赞哦~" }, { "没有赞的，赶紧去赞哦~" } };
		groupList = new ArrayList<GroupStatusEntity>();
		for (int i = 0; i < strArray.length; i++) {
			GroupStatusEntity groupStatusEntity = new GroupStatusEntity();
			groupStatusEntity.setGroupName(strArray[i]);

			List<ChildStatusEntity> childList = new ArrayList<ChildStatusEntity>();

			for (int j = 0; j < childTimeArray[i].length; j++) {
				ChildStatusEntity childStatusEntity = new ChildStatusEntity();
				childStatusEntity.setCompleteTime(childTimeArray[i][j]);
				childStatusEntity.setIsfinished(true);
				childList.add(childStatusEntity);
			}

			groupStatusEntity.setChildList(childList);
			groupList.add(groupStatusEntity);
		}
		return groupList;
	}
	 */}