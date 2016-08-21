package com.dream.example.presenter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dream.example.App;
import com.dream.example.R;
import com.dream.example.data.support.AppConsts;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.ui.activity.DebugActivity;
import com.dream.example.ui.activity.SplashActivity;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.ui.fragment.NewsV4Fragment;
import com.dream.example.utils.SPUtil;
import com.dream.example.view.IMainView;

import org.yapp.core.ui.adapter.BaseFragmentPagerAdapter;
import org.yapp.core.ui.fragment.FragmentFactory;
import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Callback;
import org.yapp.y;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: MainPresenter. <br>
 * Date: 2016/3/14 18:37 <br>
 * Author: ysj
 */
public class MainPresenter extends
        AppBaseActivityPresenter<AppBaseAppCompatActivity, App> implements IMainView, NavigationView.OnNavigationItemSelectedListener {

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout mDrawer;
    @ViewInject(R.id.nav_view)
    private NavigationView mNavigationView;

    @ViewInject(R.id.main_vp)
    private ViewPager mViewPager;
    private PageListAdapter mPageListAdapter;

    @ViewInject(R.id.lyt_select_group)
    private LinearLayout mLytSelect;

    private List<TextView> mSelectList;
    private ArrayList<Fragment> mFragmentList;
    private FragmentFactory mFactory = FragmentFactory.getInstance();
    private int[] icons = {R.drawable.bottom_contacts_selector, R.drawable.bottom_my_selector};

    public FragmentFactory getFragmentFactory() {
        return mFactory;
    }

    public ArrayList<Fragment> getFragmentList() {
        return mFragmentList;
    }

    @Override
    public void onInit() {
        if (SPUtil.get(getContent(), AppConsts._ERROR_CODE, 0) == 0) {
            FragmentFactory.releaseInstance();
            goThenKill(SplashActivity.class);
            return;
        }
        SPUtil.put(getContent(), AppConsts._ERROR_CODE, 0);
        mToggle = new ActionBarDrawerToggle(getContent(), mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(mToggle);
        mToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        checkDebug();
        // 提醒更新
//        if (3 == App.getStatus()) {
//            IntentUtil.gotoWebActivity(this, RequestFactory.newVersionInfo(app.getVersionName()), getString(R.string.app_update));
//        }
//        mFactory.registerFragment(R.string.fragment_contacts, ContactsV4Fragment.newInstance());
//        mFactory.registerFragment(R.string.fragment_my, MyV4Fragment.newInstance());
//        mFactory.registerFragment(R.string.fragment_contacts, TemplateV4Fragment.newInstance());
        mFactory.registerFragment(R.string.fragment_my, NewsV4Fragment.newInstance());
        if (mFactory.getCount() <= 0) return;
        mFragmentList = new ArrayList<>();
        mSelectList = new ArrayList<>();
        for (int i = 0; i < mFactory.getCount(); i++) {
            final int x = i;
            int id = mFactory.getId(i);
            Bundle data = new Bundle();
            data.putInt(AppConsts._ID, id);
            data.putString(AppConsts._DATA, mContext.getString(id));
            Fragment item = mFactory.getV4Fragment(id);
            item.setArguments(data);
            mFragmentList.add(item);
            TextView view = new TextView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            Integer resId = icons.length > i ? icons[i] : icons[0];
            Drawable drawable = mContext.getResources().getDrawable(resId);
            drawable.setBounds(0, 0, 80, 80);
            view.setCompoundDrawables(null, drawable, null, null);
            view.setBackground(mContext.getResources().getDrawable(R.drawable.tabs_btn_selector));
            view.setGravity(Gravity.CENTER);
            view.setPadding(15, 15, 15, 10);
            view.setLayoutParams(params);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTabSelection(x);
                }
            });
            view.setTextColor(Color.BLACK);
            view.setTextSize(14);
            view.setText(id);
            addTabView(view);
            mSelectList.add(view);
        }
        initPagerView();
        if (mFactory.getCount() > 0) setTabSelection(0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawer.isDrawerOpen(Gravity.LEFT)) {
                mDrawer.closeDrawer(Gravity.LEFT);
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                getContent().startActivity(intent);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onBackPressed() {
        if (null != mDrawer && mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getContent().getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        mMenu.getItem(2).setVisible(false);
        mMenu.getItem(3).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
//            case R.id.action_scan:
//                go(CaptureActivity.class);
//                break;
//            case R.id.action_save:
//                ((MyV4Fragment) getFragmentFactory()
//                        .getV4Fragment(R.string.fragment_my)).attemptSave();
//                break;
        }
        return false;
    }

    /**
     * 清除掉所有的选中状态。
     */
    @Override
    public void clearSelection() {
        if (null != mMenu) {
            for (int i = 0; i < mMenu.size(); i++) {
                mMenu.getItem(i).setVisible(false);
            }
        }
        for (TextView tv : mSelectList) {
            tv.setSelected(false);
        }
    }

    @Override
    public void addTabView(View view) {
        mLytSelect.addView(view);
    }

    @Override
    public void initPagerView() {
        mPageListAdapter = new PageListAdapter(getContent().getSupportFragmentManager(), mViewPager, getFragmentList());
        mViewPager.setAdapter(mPageListAdapter);
        mViewPager.setOffscreenPageLimit(getFragmentList().size());
        mViewPager.addOnPageChangeListener(mPageListAdapter);
    }

    /**
     * setTabSelection:(选择视图). <br>
     *
     * @param index
     */
    @Override
    public void setTabSelection(int index) {
        if (getFragmentFactory().getCount() <= 0) return;
        int resId = getFragmentFactory().getId(index);
        mToolbar.setTitle(getContent().getString(resId));
        clearSelection();
        mViewPager.setCurrentItem(index, true);
        mSelectList.get(index).setSelected(true);
        switch (resId) {
            case R.string.fragment_contacts:
                if (null != mMenu) {
                    mMenu.getItem(0).setVisible(true);
                    mMenu.getItem(1).setVisible(true);
                }
                break;
            case R.string.fragment_my:
                if (null != mMenu) {
                    mMenu.getItem(3).setVisible(true);
                }
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exit) {
            exit(new Callback.ExecCallback() {
                @Override
                public void run() {
                    App.getClient().setExpires(
                            System.currentTimeMillis() + (1800 * 1000)); // 延长缓存有效时长
//                    App.getClientDao().save(App.getClient()); // 更新保存客户端缓存
                }
            });
        } else if (id == R.id.nav_safe) {
//            IntentUtil.gotoWebActivity(this, AppConsts.ServerConfig.WEBSITES_SECURITYCENTER, getString(R.string.nav_safe));
        } else if (id == R.id.nav_help) {
//            IntentUtil.gotoWebActivity(this, AppConsts.ServerConfig.WEBSITES_ANSWERQUESTIONS, getString(R.string.my_about));
        }
        if (null != mDrawer) mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * checkDebug:(检查Debug模式是否启用). <br>
     *
     * @author ysj
     * @since JDK 1.7
     * date: 2015-12-15 上午10:23:07 <br>
     */
    private void checkDebug() {
        if (y.isDebug()) {
            if (mTitle == null) return;
            mTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    go(DebugActivity.class);
                }
            });
        }
    }

    private class PageListAdapter extends BaseFragmentPagerAdapter {

        public PageListAdapter(FragmentManager fm, ViewPager viewPager, List<Fragment> pagerList) {
            super(fm, viewPager, pagerList);
        }

        /**
         * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int,
         * float, int)
         */
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        /**
         * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
         */
        @Override
        public void onPageSelected(int arg0) {
            int index = arg0;
            TextView mTvDialog = null;
//            if (arg0 > 0 && arg0 < getFragmentList().size()
//                    && getFragmentFactory().getV4Fragment(getFragmentFactory().getId(arg0 - 1)) instanceof ContactsV4Fragment) {
//                index = arg0 - 1;
//                mTvDialog = ((ContactsV4Fragment) getFragmentFactory().getV4Fragment(getFragmentFactory().getId(index))).getSideBar().getTextDialog();
//            } else if (getFragmentFactory().getV4Fragment(getFragmentFactory().getId(arg0)) instanceof ContactsV4Fragment) {
//                mTvDialog = ((ContactsV4Fragment) getFragmentFactory().getV4Fragment(getFragmentFactory().getId(index))).getSideBar().getTextDialog();
//            }
//            if (null != mTvDialog) {
//                mTvDialog.setVisibility(View.GONE);
//            }
            setTabSelection(arg0);
        }
    }
}
