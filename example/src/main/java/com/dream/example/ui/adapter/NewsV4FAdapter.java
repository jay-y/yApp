package com.dream.example.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.example.R;
import com.dream.example.ui.adapter.base.AppBaseRecyclerViewHolder;
import com.dream.example.utils.ImageLoader;

import org.yapp.core.ui.adapter.BaseRecyclerViewAdapter;
import org.yapp.core.ui.inject.annotation.ViewInject;

import java.util.Map;

/**
 * ClassName: OrdersAdapter <br>
 * Description: 订单数据适配. <br>
 * Date: 2015-8-21 上午10:25:10 <br>
 * Author: ysj
 */
public class NewsV4FAdapter extends BaseRecyclerViewAdapter<Map<String,Object>,NewsV4FAdapter.ViewHolder> {
    private IClickItem mIClickItem;

    public NewsV4FAdapter(Context context) {
        super(context);
    }

    public void setIClickItem(IClickItem iClickItem){
        this.mIClickItem = iClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_163_car, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Map<String,Object> entity = mDataList.get(position);
        holder.bindItem(mContext, entity);
    }

    public interface IClickItem{
        void onClickItem(Map<String,Object> entity, View view);
    }

    public class ViewHolder extends AppBaseRecyclerViewHolder {
        @ViewInject(R.id.item_parent)
        public View mParent;
        @ViewInject(R.id.item_iv_photo)
        public ImageView mImgIc;
        @ViewInject(R.id.item_tv_time)
        public TextView mTvTime;

        public ViewHolder(View view) {
            super(view);
        }

        private void bindItem(final Context context,final Map<String,Object> entity) {
            String imgUri = (String) entity.get("imgsrc");
            ImageLoader.display(imgUri, R.drawable.pic_load, R.drawable.pic_load_error, mImgIc);
            mTvTime.setText((String) entity.get("ptime"));
            mParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mIClickItem) {
                        mIClickItem.onClickItem(entity, v);
                    }
                }
            });
        }
    }
}
