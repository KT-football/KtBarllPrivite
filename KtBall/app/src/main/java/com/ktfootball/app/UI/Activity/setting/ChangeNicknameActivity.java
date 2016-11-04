package com.ktfootball.app.UI.Activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class ChangeNicknameActivity extends BaseActivity {

    @Bind(R.id.layout_changenickname_et)
    public EditText et;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_changenickname);
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

    @OnClick(R.id.layout_changenickname)
    public void userinfoSubmit(View v) {
       String nickname = et.getText().toString();
        if("".equals(nickname)){
            showToast(getString(R.string.write_nickname));
            return;
        }
        Intent Intent= new Intent();
        Intent.putExtra(UserinfoChangeActivity.NICKNAME,nickname);
        setResult(UserinfoChangeActivity.NICKNAME_RESULT,Intent);
        finish();
    }
}
