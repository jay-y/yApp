//package com.dream.example.ui.activity;
//
//import android.graphics.Bitmap;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.hy.card.R;
//import com.hy.card.data.support.AppConsts;
//import com.hy.card.presenter.ImagePresenter;
//import com.hy.card.ui.activity.base.AppBaseAppCompatActivity;
//import com.hy.card.ui.adapter.ImageDetailAdapter;
//import com.hy.card.view.IImageView;
//import com.squareup.picasso.MemoryPolicy;
//import com.squareup.picasso.Picasso;
//
//import org.yapp.view.annotation.ViewInject;
//
//import java.util.List;
//
//import uk.co.senab.photoview.PhotoView;
//
///**
// * ClassName: ImageViewActivity <br>
// * Description: 图片展示Activity. <br>
// * Date: 2015-8-21 上午10:25:10 <br>
// * Author: ysj
// */
//public class ImageDetailActivity extends AppBaseAppCompatActivity<ImagePresenter> implements IImageView,ViewPager.OnPageChangeListener{
//	@ViewInject(R.id.image_pager)
//	private ViewPager mViewPager;
//	private ImageDetailAdapter mAdapter;
//
//	@ViewInject(R.id.image_content)
//	private ImageView mIvContent;
//
//	@Override
//	public int getLayoutId() {
//		return R.layout.activity_image_detail;
//	}
//
//	@Override
//	public void onInit() {
//		if(!mPresenter.init(getIntent().getExtras())) finish();
//		setTitle(getString(R.string.title_activity_img), true);
//	}
//
//	@Override
//	public void initPresenter() {
//		mPresenter = new ImagePresenter(this,this);
//	}
//
//	@Override
//	public void onClear() {
//		mViewPager.removeAllViews();
//		mViewPager = null;
//		mAdapter = null;
//		if (null != mIvContent.getDrawable())
//			mIvContent.getDrawable().setCallback(null);
//		mIvContent.setImageDrawable(null);
//		mIvContent.setBackgroundDrawable(null);
//		mIvContent = null;
//	}
//
//	/**
//	 * 单图展示
//	 *
//	 * @param url
//	 */
//	@Override
//	public void display(String url) {
//		mIvContent.setVisibility(View.VISIBLE);
//		mViewPager.setVisibility(View.GONE);
//		int w = mIvContent.getMeasuredWidth()>0?mIvContent.getMeasuredWidth():400;
//		int h = mIvContent.getMeasuredHeight()>0?mIvContent.getMeasuredHeight():400;
//		// TODO 根据需要调整
//		url = (url.indexOf(AppConsts.AppConfig.PATH_HTTP)!=-1 || url.indexOf(AppConsts.AppConfig.PATH_HTTPS)!=-1)?url:AppConsts.AppConfig.PATH_FILE+url;
//		Picasso.with(this)
//				.load(url)
//				.memoryPolicy(MemoryPolicy.NO_STORE,MemoryPolicy.NO_CACHE)
//				.config(Bitmap.Config.RGB_565)
//				.resize(w, h)
//				.placeholder(R.drawable.pic_load)
//				.error(R.drawable.pic_load_error)
//				.centerInside()
//				.into(mIvContent);
//	}
//
//	/**
//	 * TODO 注:图片量过大请勿使用(容易内存溢出)
//	 *
//	 * @param urlList
//	 */
//	@Override
//	public void display(List<String> urlList) {
//		mViewPager.setVisibility(View.VISIBLE);
//		mIvContent.setVisibility(View.GONE);
//		mAdapter = new ImageDetailAdapter(this);
//		mViewPager.setAdapter(mAdapter);
//		mAdapter.update(urlList);
//		mViewPager.setOffscreenPageLimit(urlList.size());
//		mViewPager.setOnPageChangeListener(this);
//	}
//
//	/**
//	 * 定制显示量
//	 * TODO 再加载会出现缩放功能无效(待修复)
//	 *
//	 * @param urlList
//	 * @param count
//	 */
//	@Override
//	public void display(List<String> urlList, int count) {
//		mViewPager.setVisibility(View.VISIBLE);
//		mIvContent.setVisibility(View.GONE);
//		mAdapter = new ImageDetailAdapter(this);
//		mViewPager.setAdapter(mAdapter);
//		mAdapter.update(urlList);
//		mViewPager.setOffscreenPageLimit(count);
//		mViewPager.setOnPageChangeListener(this);
//	}
//
//	@Override
//	public void addImage(PhotoView image) {
//		mViewPager.addView(image);
//	}
//
//	@Override
//	public void setTabSelection(int index) {
//		mViewPager.setCurrentItem(index);
//	}
//
//	@Override
//	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//	}
//
//	@Override
//	public void onPageSelected(int position) {
//		mPresenter.setTabSelection(position);
//	}
//
//	@Override
//	public void onPageScrollStateChanged(int state) {
//
//	}
//}
