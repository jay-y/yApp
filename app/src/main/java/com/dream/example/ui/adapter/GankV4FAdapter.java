package com.dream.example.ui.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dream.example.R;
import com.dream.example.data.entity.Gank;
import com.dream.example.ui.adapter.base.AppBaseRecyclerViewHolder;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import org.yapp.core.ui.adapter.BaseRecyclerViewAdapter;
import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.DimenUtil;

/**
 * ClassName: GankV4FAdapter <br>
 * Description: 干货数据适配. <br>
 * Date: 2015-8-21 上午10:25:10 <br>
 * Author: ysj
 */
public class GankV4FAdapter extends BaseRecyclerViewAdapter<Gank, GankV4FAdapter.ViewHolder> {
    private IClickItem mIClickItem;

    public GankV4FAdapter(Context context) {
        super(context);
    }

    public void setIClickItem(IClickItem iClickItem) {
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

    public interface IClickItem {
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

        private void bindItem(final Context context, final Gank gank) {
            mTvTitle.setText(getGankInfoSequence(context, gank));
            switch (gank.getType()) {
                case "Android":
                    setIconDrawable(context,mTvTitle,MaterialDesignIconic.Icon.gmi_android);
                    break;
                case "iOS":
                    setIconDrawable(context,mTvTitle, MaterialDesignIconic.Icon.gmi_apple);
                    break;
                case "休息视频":
                    setIconDrawable(context,mTvTitle, MaterialDesignIconic.Icon.gmi_collection_video);
                    break;
                case "前端":
                    setIconDrawable(context,mTvTitle, MaterialDesignIconic.Icon.gmi_language_javascript);
                    break;
                case "拓展资源":
                    setIconDrawable(context,mTvTitle, FontAwesome.Icon.faw_location_arrow);
                    break;
                case "App":
                    setIconDrawable(context,mTvTitle, MaterialDesignIconic.Icon.gmi_apps);
                    break;
                case "瞎推荐":
                    setIconDrawable(context,mTvTitle, MaterialDesignIconic.Icon.gmi_more);
                    break;

            }
            mParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mIClickItem) {
                        mIClickItem.onClickItem(gank, v);
                    }
                }
            });
        }

        private SpannableString format(Context context, String text, int style) {
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new TextAppearanceSpan(context, style), 0, text.length(), 0);
            return spannableString;
        }

        private CharSequence getGankInfoSequence(Context context, Gank mGank) {
            SpannableStringBuilder builder = new SpannableStringBuilder(mGank.getDesc()).append(
                    format(context, " (by " + mGank.getWho() + ")", R.style.ViaTextAppearance));
            return builder.subSequence(0, builder.length());
        }

        private void setIconDrawable(Context context, TextView view, IIcon icon) {
            view.setCompoundDrawablesWithIntrinsicBounds(new IconicsDrawable(context)
                            .icon(icon)
                            .color(R.color.primary)
                            .sizeDp(14),
                    null, null, null);
            view.setCompoundDrawablePadding(DimenUtil.dp2px(context, 8));
        }
    }
}
