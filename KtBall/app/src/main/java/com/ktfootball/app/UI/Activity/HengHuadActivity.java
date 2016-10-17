package com.ktfootball.app.UI.Activity;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.kt.ktball.entity.Users;
import com.kt.ktball.entity.VideoDetailsData;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.ktfootball.app.R;
import com.ktfootball.app.Views.SharedDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by leo on 16/10/15.
 */

public class HengHuadActivity extends BaseActivity {

    private SharedDialog sharedDialog;
    private VideoDetailsData mVideoDetailsData;
    @Bind(R.id.relative_image1)
    RelativeLayout mImage1;
    @Bind(R.id.relative_image2)
    RelativeLayout mImage2;
    @Bind(R.id.relative_image3)
    RelativeLayout mImage3;
    @Bind(R.id.imageview4)
    ImageView mImage4;
    @Bind(R.id.relative_image4)
    RelativeLayout mPaly1;
    @Bind(R.id.relative_image5)
    RelativeLayout mPaly2;
    @Bind(R.id.image_s)
    ImageView mImage_S;
    @Bind(R.id.image_v)
    ImageView mImage_V;
    private Timer timer;
    @Bind(R.id.image_head1)
    ImageView mHead1;
    @Bind(R.id.image_head2)
    ImageView mHead2;
    @Bind(R.id.image_head3)
    ImageView mHead3;
    @Bind(R.id.image_head4)
    ImageView mHead4;
    @Bind(R.id.image_head5)
    ImageView mHead5;


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_henhua);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShareSDK.initSDK(this);
        mVideoDetailsData = (VideoDetailsData) getIntent().getSerializableExtra("info");
        Glide.with(getThis()).load("http://www.ktfootball.com"+mVideoDetailsData.getUsers().get(0).avatar).transform(new GlideCircleTransform(getThis())).error(R.drawable.result_btnkt).into(mHead1);
        Glide.with(getThis()).load("http://www.ktfootball.com"+mVideoDetailsData.getUsers().get(1).avatar).transform(new GlideCircleTransform(getThis())).error(R.drawable.result_btnkt).into(mHead2);
        Glide.with(getThis()).load("http://www.ktfootball.com"+mVideoDetailsData.getUsers().get(1).avatar).transform(new GlideCircleTransform(getThis())).error(R.drawable.result_btnkt).into(mHead3);
        Glide.with(getThis()).load("http://www.ktfootball.com"+mVideoDetailsData.getUsers().get(0).avatar).transform(new GlideCircleTransform(getThis())).error(R.drawable.result_btnkt).into(mHead4);
        Glide.with(getThis()).load("http://www.ktfootball.com"+mVideoDetailsData.getUsers().get(1).avatar).transform(new GlideCircleTransform(getThis())).error(R.drawable.result_btnkt).into(mHead5);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                i+=1;
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        switch (i){
                            case 1:
                                mImage1.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                                TranslateAnimation animation = new TranslateAnimation(-mImage1.getMeasuredWidth(), 0,0, 0);
                                animation.setDuration(1000);//设置动画持续时间
                                mImage1.setVisibility(View.VISIBLE);
                                mImage1.startAnimation(animation);
                                break;
                            case 2:
                                mImage2.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                                TranslateAnimation animation1 = new TranslateAnimation(mImage2.getMeasuredWidth(), 0,0, 0);
                                animation1.setDuration(1000);//设置动画持续时间
                                mImage2.setVisibility(View.VISIBLE);
                                mImage2.startAnimation(animation1);
                                break;
                            case 3:
                                mImage3.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                                TranslateAnimation animation2 = new TranslateAnimation(-mImage3.getMeasuredWidth(), 0,0, 0);
                                animation2.setDuration(1000);//设置动画持续时间
                                mImage3.setVisibility(View.VISIBLE);
                                mImage3.startAnimation(animation2);
                                break;
                            case 4:
                                mImage4.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                                TranslateAnimation animation3 = new TranslateAnimation(mImage4.getMeasuredWidth(), 0,0, 0);
                                animation3.setDuration(1000);//设置动画持续时间
                                mImage4.setVisibility(View.VISIBLE);
                                mImage4.startAnimation(animation3);
                                break;
                            case 5:
                                mPaly1.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                                TranslateAnimation animation4 = new TranslateAnimation(mPaly1.getMeasuredWidth(), 0,0, 0);
                                animation4.setDuration(1000);//设置动画持续时间
                                mPaly2.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                                TranslateAnimation animation5 = new TranslateAnimation(-mPaly2.getMeasuredWidth(), 0,0, 0);
                                animation5.setDuration(1000);//设置动画持续时间
                                mPaly1.setVisibility(View.VISIBLE);
                                mPaly1.startAnimation(animation4);
                                mPaly2.setVisibility(View.VISIBLE);
                                mPaly2.startAnimation(animation5);
                                break;
                            case 6:
                                WindowManager wm = (WindowManager) getThis()
                                        .getSystemService(Context.WINDOW_SERVICE);
                                int width = wm.getDefaultDisplay().getWidth();
                                mImage_S.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                                TranslateAnimation animation6 = new TranslateAnimation(mImage_S.getMeasuredWidth()+width, 0,0, 0);
                                animation6.setDuration(1000);//设置动画持续时间
                                mImage_V.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
                                TranslateAnimation animation7 = new TranslateAnimation(-mImage_V.getMeasuredWidth()-width, 0,0, 0);
                                animation7.setDuration(1000);//设置动画持续时间
                                mImage_S.setVisibility(View.VISIBLE);
                                mImage_S.startAnimation(animation6);
                                mImage_V.setVisibility(View.VISIBLE);
                                mImage_V.startAnimation(animation7);
                                break;
                            case 7:
                                timer.cancel();
                                break;
                        }
                    }
                });
            }
        }, 500, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
        if (timer!=null){
            timer.cancel();
        }
    }

    @OnClick(R.id.imageView45)
    public void back() {
        finish();
    }


    @OnClick(R.id.dianji)
    public void back1() {
        finish();
    }

    @OnClick(R.id.layout_qrcode_how_ktbi)
    public void share() {
        showDialog();
    }

    private void showDialog() {
        if (sharedDialog == null) {
            sharedDialog = new SharedDialog(this, R.style.transparentFrameWindowStyle);
            sharedDialog.setTitleUrl(getIntent().getStringExtra("url"));
            sharedDialog.setTitle(getIntent().getStringExtra("title"));
            sharedDialog.setText("KT足球比赛视频精选");
        }
        sharedDialog.show();
        dimActivity(sharedDialog, 0.6f);
    }


}
