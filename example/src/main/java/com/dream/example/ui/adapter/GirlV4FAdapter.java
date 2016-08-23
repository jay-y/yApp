package com.dream.example.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.example.R;
import com.dream.example.data.entity.Gank;
import com.dream.example.ui.adapter.base.AppBaseRecyclerViewHolder;
import com.dream.example.utils.ImageLoader;

import org.yapp.core.ui.adapter.BaseRecyclerViewAdapter;
import org.yapp.core.ui.inject.annotation.ViewInject;

/**
 * ClassName: GirlAdapter <br>
 * Description: 妹纸数据适配. <br>
 * Date: 2015-8-21 上午10:25:10 <br>
 * Author: ysj
 */
public class GirlV4FAdapter extends BaseRecyclerViewAdapter<Gank, GirlV4FAdapter.ViewHolder> {

    private IClickItem mIClickItem;

    public GirlV4FAdapter(Context context) {
        super(context);
    }

    public void setIClickItem(IClickItem IClickItem) {
        mIClickItem = IClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_girl, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Gank entity = mDataList.get(position);
        holder.bindItem(mContext, entity);
    }

    public interface IClickItem {
        void onClickItem(Gank entity, View view);
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

        private void bindItem(Context context, final Gank entity) {
            String imgUri = entity.getUrl();
            ImageLoader.display(imgUri, R.drawable.pic_load, R.drawable.pic_load_error, mImgIc);
            mTvTime.setText(entity.getPublishedAt().toString());
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
