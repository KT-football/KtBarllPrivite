package com.ktfootball.app.UI.Activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.UI.Activity.MyQRCodeActivity;
import com.ktfootball.app.R;

import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class HowToJoinKTFootBallAvtivity extends BaseActivity {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_howtojoinktfootball);
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

    @OnClick(R.id.layout_footballrecommend_erweima)
    public void erweima(View view) {
        startActivity(new Intent(getThis(), MyQRCodeActivity.class));
    }

    @OnClick(R.id.layout_footballrecommend_ktbi)
    public void ktbi(View view) {
        startActivity(new Intent(getThis(), WhatIsKTBiActivity.class));
    }
}
