package org.yapp.core.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import java.util.List;

/**
 * ClassName: BaseFragmentPagerAdapter <br>
 * Description: 抽象的FragmentPagerAdapter实现类(实现循环播放). <br>
 * Date: 2015-12-14 下午6:29:38 <br>
 * Author: ysj
 */
public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter implements OnPageChangeListener {
	private boolean isAutoPlay = true;
	
	private ViewPager mViewPager;

	/**
	 * 保存滑动子页的list
	 */
	private List<Fragment> mPagerList;

	/**
	 * Creates a new instance of HomePageListAdapter.
	 * 
	 * @param fm
	 */
	private BaseFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public BaseFragmentPagerAdapter(FragmentManager fm,ViewPager viewPager,List<Fragment> pagerList) {
		super(fm);
		this.mPagerList = pagerList;
		this.mViewPager = viewPager;
	}

	/**
	 * @see FragmentPagerAdapter#getItem(int)
	 */
	@Override
	public Fragment getItem(int arg0) {
		return mPagerList.get(arg0);
	}

	/**
	 * @see android.support.v4.view.PagerAdapter#getCount()
	 */
	@Override
	public int getCount() {
		return mPagerList == null ? 0 : mPagerList.size();
	}

	/**
	 * @see OnPageChangeListener#onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		switch (arg0) {
		case 1:// 手势滑动，空闲中
			isAutoPlay = false;
			break;
		case 2:// 界面切换中
			isAutoPlay = true;
			break;
		case 0:// 滑动结束，即切换完毕或者加载完毕
				// 当前为最后一张，此时从右向左滑，则切换到第一张
			if (mViewPager.getCurrentItem() == mViewPager.getAdapter()
					.getCount() - 1 && !isAutoPlay) {
				mViewPager.setCurrentItem(0);
			}
			// 当前为第一张，此时从左向右滑，则切换到最后一张
			else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
				mViewPager
						.setCurrentItem(mViewPager.getAdapter().getCount() - 1);
			}
			break;
		}
	}
}