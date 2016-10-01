package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.R;

public class JudgeSeleteActivity extends BaseActivity {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_judge_selete);
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

    public void doRenGongZhiCai(View view) {//人工执裁
        Intent intent = new Intent(this,JudgeSeleteTwoActivity.class);
        startActivity(intent);
    }

    public void doShiPinShangChuan(View view) {//视频上传
        startActivity(new Intent(this,VideoActivity.class));
    }
}
