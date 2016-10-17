package com.ktfootball.app.UI.Fragment.UserDetail;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.kt.ktball.App;
import com.kt.ktball.entity.UserMsg;
import com.kt.ktball.myclass.SpiderProgressBar;
import com.ktfootball.app.R;

/**
 * Created by leo on 16/10/17.
 */

public class UserAbilityFragment extends BaseFragment {
    SpiderProgressBar spiderProgressBar;
    private UserMsg userMsg;
    private TextView mTv_jinqiu, mTv_chuandang, mTv_Kt;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_user_abilluty);
        spiderProgressBar = getViewById(R.id.spiderProgressBar);
        mTv_Kt = getViewById(R.id.tv_kt);
        mTv_chuandang = getViewById(R.id.tv_chuandang);
        mTv_jinqiu = getViewById(R.id.tv_jinqiu);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userMsg = App.getUserMsg();
        if (userMsg != null) {
            mTv_Kt.setText(userMsg.getKt() + "");
            mTv_chuandang.setText(userMsg.getPannas() + "");
            mTv_jinqiu.setText(userMsg.getGoals() + "");
            double kt = userMsg.kt / 4.45 * 2.5 * 100 + 1.00;
            double win_rate = userMsg.win_rate / 41.13 * 2.5 + 1.00;
            double scores = userMsg.scores / 6.48 * 2.5 + 1.00;
            double goals = userMsg.goals / 2.67 * 2.5 + 1.00;
            double pannas = userMsg.pannas / 0.67 * 2.5 + 1.00;
            spiderProgressBar.setWinning(win_rate > 6 ? 6.0 : win_rate);
            spiderProgressBar.setKt(kt > 6 ? 6.0 : kt);
            spiderProgressBar.setScore(scores > 6 ? 6.0 : scores);
            spiderProgressBar.setGoal(goals > 6 ? 6.0 : goals);
            spiderProgressBar.setTroughLegs(pannas > 6 ? 6.0 : pannas);
        }
        spiderProgressBar.setRotation(-19);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
