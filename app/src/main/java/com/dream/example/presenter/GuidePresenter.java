package com.dream.example.presenter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dream.example.App;
import com.dream.example.R;
import com.dream.example.data.support.AppConsts;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.ui.activity.MainActivity;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.utils.SPUtil;
import com.dream.example.view.IGuideView;

import org.yapp.core.ui.inject.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: TemplateActivityPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class GuidePresenter extends
        AppBaseActivityPresenter implements IGuideView {

    @ViewInject(R.id.guide_pager)
    private ViewPager mViewPager;
    @ViewInject(R.id.guide_dot)
    private LinearLayout mDotLayout;
    @ViewInject(R.id.guide_btn)
    private View mBtnOk;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<Fragment> pagerList = new ArrayList<>();
    /**
     * 放圆点的View的list
     */
    private List<View> dotViewsList = new ArrayList<>();
    private int[] rid = {
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3
    };

    @Override
    public void onInit() {
        for (int i = 0; i < rid.length; i++) {
            PlaceholderFragment itemFragment = PlaceholderFragment.newInstance(rid[i]);
            pagerList.add(itemFragment);
            ImageView dotView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 5;
            params.rightMargin = 5;
            mDotLayout.addView(dotView, params);
            dotViewsList.add(dotView);
        }
        mSectionsPagerAdapter = new SectionsPagerAdapter(getContext().getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(pagerList.size()); // 根据滑动页数目指定
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(mSectionsPagerAdapter);
        if (mSectionsPagerAdapter.getCount() > 0) mSectionsPagerAdapter.onPageSelected(0);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtil.put(getContext(), AppConsts.AppConfig.PARAM_HASAPP, AppConsts._NO);
                goThenKill(MainActivity.class);
            }
        });
    }

    @Override
    public void onClear() {
        mViewPager = null;
        mDotLayout = null;
        mBtnOk = null;
        mSectionsPagerAdapter = null;
        pagerList = null;
        dotViewsList = null;
        rid = null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(null);
            return true;
        }
        return false;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        private boolean isAutoPlay = true;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pagerList.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return pagerList == null ? 0 : pagerList.size();
        }

        /**
         * @see ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
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

        /**
         * @see ViewPager.OnPageChangeListener#onPageScrolled(int,
         * float, int)
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        /**
         * @see ViewPager.OnPageChangeListener#onPageSelected(int)
         */
        @Override
        public void onPageSelected(int arg0) {
            mViewPager.setCurrentItem(arg0);
            for (int i = 0; i < dotViewsList.size(); i++) {
                if (i == arg0) {
                    dotViewsList.get(arg0).setBackgroundResource(R.drawable.guide_dot_point_foucus);
                } else {
                    dotViewsList.get(i).setBackgroundResource(R.drawable.guide_dot_point_blur);
                }
            }
            if (arg0 == (pagerList.size() - 1)) {
                mBtnOk.setVisibility(View.VISIBLE);
            } else {
                mBtnOk.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_RID = "rid";

        private View mParent;

        /**
         * Returns a new instance of this fragment for the given section number.
         */
        public static PlaceholderFragment newInstance(int resourceId) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_RID, resourceId);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_guide,
                    container, false);
            mParent = rootView.findViewById(R.id.guide_item_bg);
            mParent.setBackgroundResource(getArguments().getInt(ARG_RID));
            return rootView;
        }

        /**
         * @see Fragment#onDestroyView()
         */
        @Override
        public void onDestroyView() {
            super.onDestroyView();
            if (null != mParent.getBackground())
                mParent.getBackground().setCallback(null);
            mParent.setBackgroundDrawable(null);
            mParent = null;
        }
    }
}
