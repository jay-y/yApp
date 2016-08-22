package com.dream.example.presenter;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dream.example.R;
import com.dream.example.data.support.AppConsts;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.utils.SPUtil;

import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Toast;

/**
 * Description: DebugPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class DebugPresenter extends AppBaseActivityPresenter implements View.OnClickListener {
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
                SPUtil.put(getContent(),AppConsts._SERVER_ADDRESS,mEtUrl.getText().toString());
                Toast.showMessageForButtomShort("保存成功!");
                break;
        }
    }

    @Override
    public void onInit() {
        setTitle(R.string.test);
        String serverAddress = (String) SPUtil.get(getContent(), AppConsts._SERVER_ADDRESS, "");

        mTvUrl.setText("当前服务器:" + AppConsts.ServerConfig.MAIN_HOST);
        if(!TextUtils.isEmpty(serverAddress)){
            mEtUrl.setText(serverAddress);
            AppConsts.ServerConfig.MAIN_HOST = serverAddress;
        }

        mBtnOK.setOnClickListener(this);
    }
}
