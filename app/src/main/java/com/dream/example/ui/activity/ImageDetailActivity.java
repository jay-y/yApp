package com.dream.example.ui.activity;

import com.dream.example.R;
import com.dream.example.presenter.ImageDetailPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * ClassName: ImageViewActivity <br>
 * Description: 图片展示Activity. <br>
 * Date: 2015-8-21 上午10:25:10 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.activity_image_detail
        , presenter = ImageDetailPresenter.class)
public class ImageDetailActivity extends AppBaseAppCompatActivity<ImageDetailPresenter> {

}
