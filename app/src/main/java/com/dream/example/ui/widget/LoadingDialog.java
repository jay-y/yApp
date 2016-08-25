package com.dream.example.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.dream.example.R;

import org.yapp.y;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhaokaiqiang
 * @ClassName: com.example.animationloading.LoadingDialog
 * @Description: 动画加载Dialog
 * @date 2014-10-27 下午4:42:52
 */
public class LoadingDialog extends Dialog {

    // 动画间隔
    private static final int DURATION = 800;
    // 前景图片
    private ImageView mImgFront;
    // 定时器，用来不断的播放动画
    private Timer mAnimationTimer;
    // 旋转动画
    private RotateAnimation mAnimationL2R;

    private Animation mAnimation;

    private Context mContext;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadDialog);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        mImgFront = (ImageView) findViewById(R.id.img_front);

        mAnimationTimer = new Timer();

//        // 从左到右的旋转动画，设置旋转角度和旋转中心
//        mAnimationL2R = new RotateAnimation(0f, -90f,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//                0.5f);
//        // 设置动画的运行时长
//        mAnimationL2R.setDuration(DURATION);
//        // 动画运行结束之后，保存结束之后的状态
//        mAnimationL2R.setFillAfter(true);
//        // 设置重复的次数
//        mAnimationL2R.setRepeatCount(1);
//        // 设置重复模式为逆运动
//        mAnimationL2R.setRepeatMode(Animation.REVERSE);

        mAnimation = AnimationUtils.loadAnimation(mContext, R.anim.anim_load_dialog);

        // 执行间隔任务，开始间隔是0，每隔DURATION * 2执行一次
        mAnimationTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                y.task().post(new Runnable() {
                    @Override
                    public void run() {
                        mImgFront.setAnimation(mAnimation);
                        mAnimation.start();
//                        mImgFront.setAnimation(mAnimationL2R);
//                        mAnimationL2R.start();
                    }
                });
            }
        }, 0, DURATION * 2);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAnimationTimer.cancel();
    }

}