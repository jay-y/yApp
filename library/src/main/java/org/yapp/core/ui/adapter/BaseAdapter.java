package org.yapp.core.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import org.yapp.core.ui.abase.BaseAdapterApi;
import org.yapp.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BaseAdapter<T> <br>
 * Description: 抽象的Adapter类,封装了内容为View的公共操作. <br>
 * Date: 2015-12-14 下午6:29:38 <br>
 * Author: ysj
 */
public abstract class BaseAdapter<T extends Object,ViewHolder extends BaseViewHolder> extends android.widget.BaseAdapter implements BaseAdapterApi<T> {
	protected List<T> mDataList;
	protected Context mContext;

	public BaseAdapter(Context context) {
		mContext = context;
		mDataList = new ArrayList<>();
	}

	public BaseAdapter(Context context, List<T> dataList) {
		mContext = context;
		if (null != dataList) {
			mDataList = dataList;
		} else {
			mDataList = new ArrayList<>();
		}
	}
	
	/**
	 * @return the mDataList
	 */
	public List<T> getmDataList() {
		return mDataList;
	}

	@Override
	public int getCount() {
		return mDataList.size();
	}

	@Override
	public Object getItem(int position) {
		return mDataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/** 
	 * @see android.widget.Adapter#getView(int, View, ViewGroup)
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		if (type == -1)
			return null;
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = onInflateView(type,position,parent);
			holder = onCreateViewHolder(convertView, type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder)convertView.getTag();
		}
		onBindViewHolder(holder, type, position);
		return convertView;
	}
	
	/**
	 * TODO 多视图时重写. 
	 * @see BaseAdapter#getItemViewType(int)
	 */
	@Override
	public int getItemViewType(int position) {
		return 0;
	}
	
	/**
	 * TODO 多视图时重写. 
	 * @see BaseAdapter#getViewTypeCount()
	 */
	@Override
	public int getViewTypeCount() {
		return 1;
	}

	public void clear() {
		mDataList.clear();
		notifyDataSetChanged();
	}

	public void update(List<T> dataList) {
		mDataList.clear();
		mDataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void addAll(List<T> dataList) {
		mDataList.addAll(dataList);
		notifyDataSetChanged();
	}

	public void add(T data) {
		mDataList.add(data);
		notifyDataSetChanged();
	}

	public T get(int pos) {
		return mDataList.get(pos);
	}

	public List<T> get() {
		return mDataList;
	}
	
	/**
	 * onInflateView:(填充布局). <br> 
	 * 
	 * @author ysj 
	 * @param position
	 * @return 
	 * @since JDK 1.7
	 * date: 2016-3-3 上午11:12:40 <br>
	 */
	protected abstract View onInflateView(int type,int position,ViewGroup parent);
	
	/**
	 * onCreateViewHolder:(创建视图支架). <br> 
	 * 
	 * @author ysj 
	 * @param convertView
	 * @param type
	 * @return 
	 * @since JDK 1.7
	 * date: 2016-3-3 上午11:15:31 <br>
	 */
	protected abstract ViewHolder onCreateViewHolder(View convertView,int type);
	
	/**
	 * onBindViewHolder:(绑定数据). <br> 
	 * 
	 * @author ysj 
	 * @param holder
	 * @param type
	 * @param position 
	 * @since JDK 1.7
	 * date: 2016-3-3 上午11:16:08 <br>
	 */
	protected abstract void onBindViewHolder(ViewHolder holder,int type ,int position);
}
