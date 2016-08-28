package com.dream.example.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import org.yapp.core.ui.adapter.BaseFragmentStatePagerAdapter;

import java.util.List;

/**
 * FileName:ViewPageFragmentAdapter.java
 * Description:
 * Author:dingboyang
 * Email:445850053@qq.com
 * Date:16/4/2
 */
public class MainAdapter extends BaseFragmentStatePagerAdapter {

    public MainAdapter(FragmentManager fm, ViewPager pager, List<Fragment> pagerList) {
        super(fm, pager, pagerList);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }
}
