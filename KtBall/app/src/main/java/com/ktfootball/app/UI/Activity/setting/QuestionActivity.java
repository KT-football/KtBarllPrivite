package com.ktfootball.app.UI.Activity.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.R;

import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class QuestionActivity extends BaseActivity {

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_question);
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

    @OnClick(R.id.layout_set_whatkt)
    public void whatKT(View v) {
        startActivity(new Intent(getThis(), FootBallRecommendActivity.class));
    }

    @OnClick(R.id.layout_set_howfenshu)
    public void howFenShu(View v) {
        startActivity(new Intent(getThis(), CalculateBattlePointsActivity.class));
    }

    @OnClick(R.id.layout_set_joinkt)
    public void joinkt(View v) {
        startActivity(new Intent(getThis(), HowToJoinKTFootBallAvtivity.class));
    }
}
