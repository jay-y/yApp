package com.dream.example.ui.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.example.R;
import com.dream.example.data.entity.Gank;
import com.dream.example.ui.adapter.base.AppBaseRecyclerViewHolder;

import org.yapp.core.ui.adapter.BaseRecyclerViewAdapter;
import org.yapp.core.ui.inject.annotation.ViewInject;

/**
 * ClassName: GankV4FAdapter <br>
 * Description: 干货数据适配. <br>
 * Date: 2015-8-21 上午10:25:10 <br>
 * Author: ysj
 */
public class GankV4FAdapter extends BaseRecyclerViewAdapter<Gank,GankV4FAdapter.ViewHolder> {
    private IClickItem mIClickItem;

    public GankV4FAdapter(Context context) {
        super(context);
    }

    public void setIClickItem(IClickItem iClickItem){
        this.mIClickItem = iClickItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank_normal, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Gank entity = mDataList.get(position);
        holder.bindItem(mContext, entity);
    }

    public interface IClickItem{
        void onClickItem(Gank entity, View view);
    }

    public class ViewHolder extends AppBaseRecyclerViewHolder {
        @ViewInject(R.id.item_parent)
        public View mParent;
        @ViewInject(R.id.item_tv_title)
        public TextView mTvTitle;

        public ViewHolder(View view) {
            super(view);
        }

        private void bindItem(final Context context,final Gank gank) {
            mTvTitle.setText(getGankInfoSequence(context, gank));
            mParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(null != mIClickItem){
                        mIClickItem.onClickItem(gank,v);
                    }
                }
            });
        }

        private SpannableString format(Context context, String text, int style) {
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(), 0);
            return spannableString;
        }

        private CharSequence getGankInfoSequence(Context context,Gank mGank) {
            SpannableStringBuilder builder = new SpannableStringBuilder(mGank.getDesc()).append(
                    format(context, " (via. " + mGank.getWho() + ")", R.style.ViaTextAppearance));
            return builder.subSequence(0, builder.length());
        }
    }
}
