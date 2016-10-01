package com.ktfootball.app.UI.Activity.setting;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.UI.Activity.MyDVActivity;
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.ktfootball.app.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class FootBallRecommendActivity extends BaseActivity {

    @Bind(R.id.layout_footballrecommend_sp)
    TextView lookVedio;

    @Bind(R.id.layout_footballrecommend_fa)
    TextView fangan;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_footballrecommend);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        lookVedio.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        lookVedio.getPaint().setAntiAlias(true);//抗锯齿

        fangan.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        fangan.getPaint().setAntiAlias(true);//抗锯齿
    }

    public void doBack(View view) {
        finish();
    }

    @OnClick(R.id.layout_footballrecommend_sp)
    public void lookVedio(View view){
        Intent intent = new Intent(this, MyDVActivity.class);
        long id = 14449;
        intent.putExtra(UserProfiles.EXTRA_ME_OR_HE_USER_ID, id);
        startActivity(intent);
    }

    @OnClick(R.id.layout_footballrecommend_fa)
    public void fangan(View view){
        Intent intent = new Intent(this, HowToJoinKTFootBallAvtivity.class);
        startActivity(intent);
    }
}
