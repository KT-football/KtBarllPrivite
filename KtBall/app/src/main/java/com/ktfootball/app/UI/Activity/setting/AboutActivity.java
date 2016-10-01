package com.ktfootball.app.UI.Activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.UI.Activity.RegisterNoticeActivity;
import com.ktfootball.app.R;

import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_about);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void doBack(View view) {
        finish();
    }

    @OnClick(R.id.layout_set_js)
    public void toFootBallRecommend(View v) {
        startActivity(new Intent(getThis(), FootBallRecommendActivity.class));
    }

    @OnClick(R.id.layout_set_fltw)
    public void toFLTW(View v) {//法律条文
        startActivity(new Intent(getThis(), RegisterNoticeActivity.class));
    }

    @OnClick(R.id.layout_set_lxwm)
    public void toLXWM(View v) {
        startActivity(new Intent(getThis(), ContactUsActivity.class));
    }

    @OnClick(R.id.layout_set_yjfk)
    public void toYJFK(View v) {
        startActivity(new Intent(getThis(), FeedbackAvtivity.class));
    }
}
