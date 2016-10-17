package com.ktfootball.app.UI.Activity.setting;

import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.R;

/**
 * Created by jy on 16/6/9.
 */
public class CalculateBattlePointsActivity extends BaseActivity {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_calculatebattlepoints);
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
}
