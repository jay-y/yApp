package com.dream.example.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.dream.example.R;
import com.dream.example.data.support.AppConsts;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.ui.adapter.ImageDetailAdapter;
import com.dream.example.utils.IntentUtil;
import com.dream.example.view.IImageDetailView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.yapp.core.ui.inject.annotation.ViewInject;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Description: ImageDetailPresenter. <br>
 * Date: 2016/3/14 18:37 <br>
 * Author: ysj
 */
public class ImageDetailPresenter extends
        AppBaseActivityPresenter implements IImageDetailView, ViewPager.OnPageChangeListener {

    @ViewInject(R.id.image_pager)
    private ViewPager mViewPager;
    private ImageDetailAdapter mAdapter;

    @ViewInject(R.id.image_content)
    private ImageView mIvContent;

    private List<String> mUrlList;
    private int index = 0;

    @Override
    public void onInit() {
        Intent intent = getContent().getIntent();
        Bundle extras = null != intent ? intent.getExtras() : null;
        if (null != extras) {
            if (extras.containsKey(IntentUtil.EXTRA_URL)
                    && !TextUtils.isEmpty(extras.getString(IntentUtil.EXTRA_URL))) {//单图
                String uri = extras.getString(IntentUtil.EXTRA_URL);
                display(uri);
            } else if (extras.containsKey(IntentUtil.EXTRA_URLS)
                    && null != extras.get(IntentUtil.EXTRA_URLS)
                    && extras.getStringArrayList(IntentUtil.EXTRA_URLS).size() > 0) {//多图
                mUrlList = extras.getStringArrayList(IntentUtil.EXTRA_URLS);
                index = extras.getInt(IntentUtil.EXTRA_INDEX);
                display(mUrlList, 5);
                onPageSelected(index);
            }
            setTitle(getContent().getString(R.string.title_activity_img));
        } else {
            getContent().finish();
        }
    }

    @Override
    public void onClear() {
        mViewPager.removeAllViews();
        mViewPager = null;
        mAdapter = null;
        releaseImageView(mIvContent);
    }

    /**
     * 单图展示
     *
     * @param url
     */
    @Override
    public void display(String url) {
        mIvContent.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.GONE);
        int w = mIvContent.getMeasuredWidth() > 0 ? mIvContent.getMeasuredWidth() : 400;
        int h = mIvContent.getMeasuredHeight() > 0 ? mIvContent.getMeasuredHeight() : 400;
        // TODO 根据需要调整
        url = (url.indexOf(AppConsts.AppConfig.PATH_HTTP) != -1 || url.indexOf(AppConsts.AppConfig.PATH_HTTPS) != -1) ? url : AppConsts.AppConfig.PATH_FILE + url;
        Picasso.with(getContent())
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_STORE, MemoryPolicy.NO_CACHE)
                .config(Bitmap.Config.RGB_565)
                .resize(w, h)
                .placeholder(R.drawable.pic_load)
                .error(R.drawable.pic_load_error)
                .centerInside()
                .into(mIvContent);
    }

    /**
     * 多图展示
     * TODO 注:图片量过大请勿使用(容易内存溢出)
     *
     * @param urlList
     */
    @Override
    public void display(List<String> urlList) {
        mViewPager.setVisibility(View.VISIBLE);
        mIvContent.setVisibility(View.GONE);
        mAdapter = new ImageDetailAdapter(getContent());
        mViewPager.setAdapter(mAdapter);
        mAdapter.update(urlList);
        mViewPager.setOffscreenPageLimit(urlList.size());
        mViewPager.setOnPageChangeListener(this);
    }

    /**
     * 多图展示(定制显示量)
     * TODO 再加载会出现缩放功能无效(待修复)
     *
     * @param urlList
     * @param count
     */
    @Override
    public void display(List<String> urlList, int count) {
        mViewPager.setVisibility(View.VISIBLE);
        mIvContent.setVisibility(View.GONE);
        mAdapter = new ImageDetailAdapter(getContent());
        mViewPager.setAdapter(mAdapter);
        mAdapter.update(urlList);
        mViewPager.setOffscreenPageLimit(count);
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void addImage(PhotoView image) {
        mViewPager.addView(image);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mViewPager.setCurrentItem(index);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
