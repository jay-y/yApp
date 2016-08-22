package com.dream.example.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.dream.example.R;
import com.squareup.picasso.Picasso;

import org.yapp.core.ui.adapter.BasePageAdapter;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Description: 图片组适配器. <br>
 * Date: 2016/3/30 17:43 <br>
 * Author: ysj
 */
public class ImageDetailAdapter extends BasePageAdapter<String, PhotoView> {

    public ImageDetailAdapter(Context context) {
        super(context);
    }

    public ImageDetailAdapter(Context context, List<String> dataList) {
        super(context, dataList);
    }

    @Override
    public PhotoView newView(int position) {
        PhotoView imageView = new PhotoView(mContext);
        imageView.setTag(get(position));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        int w = imageView.getMeasuredWidth() > 0 ? imageView.getMeasuredWidth() : 400;
        int h = imageView.getMeasuredHeight() > 0 ? imageView.getMeasuredHeight() : 400;
        Picasso.with(mContext)
                .load(get(position))
                .resize(w, h)
                .placeholder(R.drawable.pic_load)
                .error(R.drawable.pic_load_error)
                .centerInside()
                .into(imageView);
        return imageView;
    }
}
