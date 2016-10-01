package com.ktfootball.app.UI.Activity.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.R;

import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class ContactUsActivity extends BaseActivity {
    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_contactus);
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

    @OnClick(R.id.layout_contactus_to_phone)
    public void doActionCall(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:02156435766"));
        startActivity(intent);
    }
}
