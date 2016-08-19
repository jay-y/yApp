package org.yapp.core.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import org.yapp.core.ui.abase.BaseAdapterApi;

import java.util.ArrayList;
import java.util.List;

/** 
 * ClassName: BasePageAdapter<T> <br> 
 * Description: 抽象的PagerAdapter实现类,封装了内容为View的公共操作. <br> 
 * Date: 2015-12-14 下午6:29:38 <br> 
 * Author: ysj
 */
public abstract class BasePageAdapter<T,V extends View> extends PagerAdapter implements BaseAdapterApi<T>{
    protected Context mContext;
    protected List<T> mDataList;
    private SparseArray<V> mViewList;

    public BasePageAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
        mViewList = new SparseArray<V>();
    }

    public BasePageAdapter(Context context,List<T> listData) {
        mContext = context;
        if(null != listData){
            mDataList = listData;
            mViewList = new SparseArray<V>(listData.size());
        }else{
            mDataList = new ArrayList<>();
            mViewList = new SparseArray<V>();
        }
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        V view = mViewList.get(position);
        if (null == view) {
            view = newView(position);
            mViewList.put(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    public void clear() {
        mDataList.clear();
        mViewList.clear();
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

    public List<T> get(){
        return mDataList;
    }

    public T get(int position) {
        return mDataList.get(position);
    }

    public V getView(int position) {
        return mViewList.get(position);
    }

    public abstract V newView(int position);
}
