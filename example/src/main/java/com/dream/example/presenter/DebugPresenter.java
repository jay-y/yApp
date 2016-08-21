package com.dream.example.presenter;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dream.example.App;
import com.dream.example.R;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ViewInject;

/**
 * Description: DebugPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class DebugPresenter extends AppBaseActivityPresenter<AppBaseAppCompatActivity, App> implements View.OnClickListener {
    @ViewInject(R.id.debug_request)
    private TextView mTvUrl;

    @ViewInject(R.id.debug_et_request)
    private EditText mEtUrl;

    @ViewInject(R.id.debug_btn_ok)
    private Button mBtnOK;

    /**
     * @see View.OnClickListener#onClick(View)
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.debug_btn_ok:
                showMsg("Hello World!");
                break;
        }
    }

    @Override
    public void onInit() {
        setTitle(R.string.test);

        mBtnOK.setOnClickListener(this);
    }
}
