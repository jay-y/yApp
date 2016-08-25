package com.dream.example.ui.activity.base;

import android.view.Menu;
import android.view.MenuItem;

import com.dream.example.presenter.base.AppBaseActivityPresenter;

import org.yapp.core.ui.activity.BaseAppCompatActivity;

/**
 * Description: 应用Activity基类. <br>
 * Date: 2016/3/15 9:22 <br>
 * Author: ysj
 */
public abstract class AppBaseAppCompatActivity<P extends AppBaseActivityPresenter> extends BaseAppCompatActivity<P> {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return mPresenter.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mPresenter.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
