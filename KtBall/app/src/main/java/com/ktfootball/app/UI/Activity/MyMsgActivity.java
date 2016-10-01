package com.ktfootball.app.UI.Activity;

import android.os.Message;
import android.os.Bundle;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.R;

public class MyMsgActivity extends BaseActivity {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_msg);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void doFinsh(View view) {//退出
        finish();
    }
}
