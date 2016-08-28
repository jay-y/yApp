package com.dream.example.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.yapp.utils.Log;
import org.yapp.y;

/**
 * Description: 图片加载工具类(适用于一般图片加载操作，特殊情况请直接使用Picasso). <br>
 * Date: 2016/4/5 11:43 <br>
 * Author: ysj
 */
public class ImageLoader {
    public static int reWidth = 200;
    public static int reHeight = 200;
    public static Bitmap.Config reConfig = Bitmap.Config.RGB_565;

    /**
     * 构建Picasso Downloader
     *
     * @param context
     * @param downloader
     */
    public static void build(Context context, Downloader downloader) {
        Picasso picasso = new Picasso.Builder(context)
                .downloader(downloader)
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    /**
     * 获取位图
     *
     * @param uri
     * @return
     */
    public static Bitmap get(String uri) {
        try {
            return Picasso.with(y.app())
                    .load(uri)
                    .config(reConfig).get();
        } catch (Exception e) {
            Log.e(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 图片加载展示
     *
     * @param uri
     * @param view
     */
    public static void display(String uri, ImageView view) {
        display(processScaleType(uri, view), view);
    }

    /**
     * 图片加载展示
     *
     * @param uri
     * @param placeholderResId 0则不展示
     * @param errorResId       0则不展示
     * @param view
     */
    public static void display(String uri, int placeholderResId, int errorResId, ImageView view) {
        display(processResId(processScaleType(uri, view), placeholderResId, errorResId), view);
    }

    /**
     * 图片加载展示
     *
     * @param uri
     * @param placeholderDrawable null则不展示
     * @param errorDrawable       null则不展示
     * @param view
     */
    public static void display(String uri, Drawable placeholderDrawable, Drawable errorDrawable, ImageView view) {
        display(processResId(processScaleType(uri, view), placeholderDrawable, errorDrawable), view);
    }

    /**
     * 图片加载展示
     *
     * @param uri
     * @param view
     * @param isTrust 是否信任缓存
     */
    public static void display(String uri, ImageView view, boolean isTrust) {
        if (!isTrust) Picasso.with(view.getContext()).invalidate(uri);
        display(processScaleType(uri, view), view);
    }

    /**
     * 图片加载展示
     *
     * @param uri
     * @param placeholderResId 0则不展示
     * @param errorResId       0则不展示
     * @param view
     * @param isTrust          是否信任缓存
     */
    public static void display(String uri, int placeholderResId, int errorResId, ImageView view, boolean isTrust) {
        if (!isTrust) Picasso.with(view.getContext()).invalidate(uri);
        display(processResId(processScaleType(uri, view), placeholderResId, errorResId), view);
    }

    /**
     * 图片加载展示
     *
     * @param uri
     * @param placeholderDrawable null则不展示
     * @param errorDrawable       null则不展示
     * @param isTrust             是否信任缓存
     */
    public static void display(String uri, Drawable placeholderDrawable, Drawable errorDrawable, ImageView view, boolean isTrust) {
        if (!isTrust) Picasso.with(view.getContext()).invalidate(uri);
        display(processResId(processScaleType(uri, view), placeholderDrawable, errorDrawable), view);
    }

    private static void display(RequestCreator taget, ImageView view) {
        taget.into(view);
    }

    /**
     * 处理ScaleType
     *
     * @param uri
     * @param view
     * @return
     */
    private static RequestCreator processScaleType(String uri, ImageView view) {
        if (view.getScaleType() == ImageView.ScaleType.CENTER) {
            view.setScaleType(ImageView.ScaleType.CENTER);
            return initWithResize(uri, view);
        } else if (view.getScaleType() == ImageView.ScaleType.CENTER_INSIDE) {
            return initWithResize(uri, view).centerInside();
        } else if (view.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
            return initWithResize(uri, view).centerCrop();
        } else if (view.getScaleType() == ImageView.ScaleType.FIT_CENTER) {
            view.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return init(uri, view);
        } else if (view.getScaleType() == ImageView.ScaleType.FIT_START) {
            view.setScaleType(ImageView.ScaleType.FIT_START);
            return init(uri, view);
        } else if (view.getScaleType() == ImageView.ScaleType.FIT_END) {
            view.setScaleType(ImageView.ScaleType.FIT_END);
            return init(uri, view);
        } else {
            return init(uri, view).fit();
        }
    }

    private static RequestCreator processResId(RequestCreator taget, int placeholderResId, int errorResId) {
        if (0 != placeholderResId && 0 != errorResId) {
            return taget
                    .placeholder(placeholderResId)
                    .error(errorResId);
        } else if (0 != placeholderResId) {
            return taget.placeholder(placeholderResId);
        } else if (0 != errorResId) {
            return taget.error(errorResId);
        }
        return taget;
    }

    private static RequestCreator processResId(RequestCreator taget, Drawable placeholderDrawable, Drawable errorDrawable) {
        if (null != placeholderDrawable && null != errorDrawable) {
            return taget
                    .placeholder(placeholderDrawable)
                    .error(errorDrawable);
        } else if (null != placeholderDrawable) {
            return taget.placeholder(placeholderDrawable);
        } else if (null != errorDrawable) {
            return taget.error(errorDrawable);
        }
        return taget;
    }

    private static RequestCreator initWithResize(String uri, ImageView view) {
        return init(uri, view).resize(reWidth, reHeight);
    }

    private static RequestCreator init(String uri, ImageView view) {
        return Picasso.with(view.getContext())
                .load(uri)
                .config(reConfig);
    }
}
