package com.ktfootball.app.UI.Fragment.UserProFile;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private LinearLayout empty;
    private RelativeLayout relative_main;
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
        empty = getViewById(R.id.empty);
        relative_main =getViewById(R.id.relative_main);
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
            mTv_cont.setText(getString(R.string.all_game)+" : "+(userMsg.lose+userMsg.draw+userMsg.win));
            mTv_pai.setText(getString(R.string.contury_top)+" : "+userMsg.rank);
            tv_zhandouli.setText(userMsg.power+"");
            myCirCleView.setProgress(userMsg.win == 0?0:userMsg.win*100/(userMsg.lose+userMsg.draw+userMsg.win),userMsg.lose == 0?0:userMsg.lose*100/(userMsg.lose+userMsg.draw+userMsg.win));
            myoutCirCleView.setProgress(userMsg.win == 0?0:userMsg.win*100/(userMsg.lose+userMsg.draw+userMsg.win),userMsg.lose == 0?0:userMsg.lose*100/(userMsg.lose+userMsg.draw+userMsg.win));
            if (userMsg.win == 0&&userMsg.lose == 0&&userMsg.draw == 0){
                empty.setVisibility(View.VISIBLE);
                relative_main.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }
}
