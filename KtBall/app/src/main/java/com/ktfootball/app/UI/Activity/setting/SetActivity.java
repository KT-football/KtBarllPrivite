package com.ktfootball.app.UI.Activity.setting;

import android.content.Intent;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.App;
import com.ktfootball.app.Constants;
import com.kt.ktball.entity.UserLogin;
import com.ktfootball.app.Manager.BitmapManager;
import com.kt.ktball.myclass.MyCircleImageView;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.MyQRCodeActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class SetActivity extends BaseActivity {

    @Bind(R.id.layout_set_header)
    MyCircleImageView header;

    @Bind(R.id.layout_set_name)
    TextView name;

    @Bind(R.id.layout_set_accounts)
    TextView account;

    @Bind(R.id.layout_erweima)
    ImageView erweima;

    public static final int NICKNAME_START = 1111;
    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_set);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        UserLogin userLogin = App.getUserLogin();
        name.setText(userLogin.nickname);
        account.setText(userLogin.email);
        BitmapManager.getInstance().displayUserLogo(header, Constants.HEAD_HOST + userLogin.avatar);

    }

    public void doBack(View view) {
        finish();
    }

    @OnClick(R.id.layout_set_question)
    public void toQurestionActivity(View v) {
        startActivity(new Intent(getThis(), QuestionActivity.class));
    }

    @OnClick(R.id.layout_set_about)
    public void toAboutActivity(View v) {
        startActivity(new Intent(getThis(), AboutActivity.class));
    }

    @OnClick(R.id.layout_set_userinfo)
    public void userinfo(View v) {
        Intent intent = new Intent(getThis(), UserinfoChangeActivity.class);
        intent.putExtra(UserinfoChangeActivity.USER_ID,App.getUserLogin().user_id+"");
        startActivity(intent);
    }

//
//    @OnClick(R.id.layout_userinfochange_rl_qrcode)
//    public void qrcode(View v) {
//        Intent intent = new Intent(getThis(), MyQRCodeActivity.class);
//        startActivityForResult(intent, NICKNAME_START);
//    }

    @OnClick(R.id.layout_userinfochange_changepassword)
    public void changepassword(View v) {
        Intent intent = new Intent(getThis(), ChangePassWordActivity.class);
        startActivityForResult(intent, NICKNAME_START);
    }

    @OnClick(R.id.layout_userinfochange_bingphone)
    public void bingphone(View v) {
        Intent intent = new Intent(getThis(), BindPhoneActivity.class);
        startActivityForResult(intent, NICKNAME_START);

    }
}
