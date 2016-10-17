package com.ktfootball.app.UI.Fragment.UserProFile;

import android.os.Bundle;
import android.os.Message;
import android.widget.TextView;

import com.frame.app.base.fragment.BaseFragment;
import com.kt.ktball.App;
import com.kt.ktball.entity.UserMsg;
import com.ktfootball.app.R;
import com.ktfootball.app.Views.MyCirCleView;
import com.ktfootball.app.Views.MyOutCirCleView;

/**
 * Created by leo on 16/10/14.
 */

public class ScreeningsFragment extends BaseFragment{
    private TextView mTv_Lose,mTv_win,mTv_ping;
    private TextView mTv_cont,mTv_pai;
    private UserMsg userMsg = App.getInstance().getUserMsg();
    private TextView tv_zhandouli;
    private MyOutCirCleView myoutCirCleView;
    private MyCirCleView myCirCleView;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_screen);
        mTv_Lose = getViewById(R.id.tv_lose);
        mTv_ping = getViewById(R.id._tv_ping);
        mTv_win = getViewById(R.id.tv_win);
        mTv_cont = getViewById(R.id.tv_all_cont);
        mTv_pai = getViewById(R.id.tv_paim);
        tv_zhandouli = getViewById(R.id.tv_zhandouli);
        myCirCleView = getViewById(R.id.myCirCleView);
        myoutCirCleView = getViewById(R.id.myout);
    }

    @Override
    protected void setListener() {

    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        if (userMsg!=null){
            mTv_Lose.setText(userMsg.lose+"");
            mTv_ping.setText(userMsg.draw+"");
            mTv_win.setText(userMsg.win+"");
            mTv_cont.setText("总场次 : "+(userMsg.lose+userMsg.draw+userMsg.win));
            mTv_pai.setText("全国排行 : "+userMsg.rank);
            tv_zhandouli.setText(userMsg.power+"");
            myCirCleView.setProgress(userMsg.win*100/(userMsg.lose+userMsg.draw+userMsg.win),userMsg.lose*100/(userMsg.lose+userMsg.draw+userMsg.win));
            myoutCirCleView.setProgress(userMsg.win*100/(userMsg.lose+userMsg.draw+userMsg.win),userMsg.lose*100/(userMsg.lose+userMsg.draw+userMsg.win));
        }

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
