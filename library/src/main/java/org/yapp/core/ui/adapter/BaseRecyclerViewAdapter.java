package org.yapp.core.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.yapp.core.ui.abase.BaseAdapterApi;
import org.yapp.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BaseRecyclerViewAdapter<T> <br>
 * Description: 抽象的RecyclerView Adapter类,封装了内容为View的公共操作. <br>
 * Date: 2015-12-14 下午6:29:38 <br>
 * Author: ysj
 */
public abstract class BaseRecyclerViewAdapter<T extends Object, ViewHolder extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<ViewHolder> implements BaseAdapterApi<T> {
    protected List<T> mDataList;
    protected Context mContext;

    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    public BaseRecyclerViewAdapter(Context context, List<T> dataList) {
        mContext = context;
        if (null != dataList) {
            mDataList = dataList;
        } else {
            mDataList = new ArrayList<>();
        }
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

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
}
