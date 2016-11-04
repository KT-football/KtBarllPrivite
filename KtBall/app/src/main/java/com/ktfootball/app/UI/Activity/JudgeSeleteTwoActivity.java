package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.ktfootball.app.R;

public class JudgeSeleteTwoActivity extends BaseToolBarActivity2 {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_judge_selete_two);
    }

    @Override
    protected void initToolBar() {
        setToolBarTitle(getString(R.string.referee_choose));
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void doGamePlace(View view) {//比赛地点
        startActivity(new Intent(this,GamePlaceActivity.class));
    }

    public void doFinsh(View view) {//退出
        finish();
    }

    public void doMyGame(View view) {//我的赛事
        startActivity(new Intent(this,MyGameActivity.class));
    }
}
