package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.SharedPreferencesUtils;
import com.ktfootball.app.Constants;

/**
 * Created by leo on 16/10/13.
 */

public class StartActivity extends BaseActivity {
    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = new Intent();
        /**
         * 不是第一次登錄
         */
        if (SharedPreferencesUtils.getBoolean(getThis(), Constants.IS_FIRST, false)) {
            if (SharedPreferencesUtils.getString(getThis(), "username", "").equals(""))
                intent.setClass(getThis(), LoginActivity.class);
            else
                intent.setClass(getThis(), MainActivity.class);
        } else {
            intent.setClass(getThis(), CarouselActivity.class);
        }
        startActivity(intent);
        finish();


    }
}
