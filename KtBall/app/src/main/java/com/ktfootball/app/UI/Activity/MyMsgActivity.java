package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.kt.ktball.App;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.MessageBean;
import com.ktfootball.app.Entity.MyMsgBattleBean;
import com.ktfootball.app.Entity.MyMsgTeamBean;
import com.ktfootball.app.Event.SystemBattleMsgEvent;
import com.ktfootball.app.Event.SystemMsgEvent;
import com.ktfootball.app.Event.SystemTeamMsgEvent;
import com.ktfootball.app.R;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;

public class MyMsgActivity extends BaseActivity {
    private int mCont = 0;
    private int mCont2 = 0;
    private int mCont3 =0;
    @Bind(R.id.tv_cont)
    TextView mTv_cont;
    MessageBean messagBean = new MessageBean();
    MyMsgTeamBean myMsgTeamBean = new MyMsgTeamBean();
    MyMsgBattleBean myMsgBattleBean = new MyMsgBattleBean();
    @Bind(R.id.tv_cont3)
    TextView mTv_cont3;
    @Bind(R.id.tv_cont2)
    TextView mTv_cont2;
    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_msg);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getData();
        getTeamData();
        getBattleData();
    }

    public void doFinsh(View view) {//退出
        finish();
    }

    /**
     * 获取消息
     */
    private void getData() {
        showLoadingDiaglog();
        String url = Constants.USER_MESSAGE + "?user_id="
                + App.getUserLogin().user_id + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mCont = 0;
                        closeLoadingDialog();
                        Gson gson = new Gson();
                        messagBean = gson.fromJson(jsonObject.toString(), MessageBean.class);
                        if (messagBean.getResponse().equals("success")) {
                            for (int i = 0; i < messagBean.getUser_messages().size(); i++) {
                                if (messagBean.getUser_messages().get(i).getIs_read() == 0) {
                                    mCont += 1;
                                }
                            }
                            if (mCont>0) {
                                mTv_cont.setVisibility(View.VISIBLE);
                                mTv_cont.setText(mCont + "");
                            }
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(getThis()).addRequest(jsonRequest);

    }
    /**
     * 获取组队消息
     */
    private void getTeamData() {
        showLoadingDiaglog();
        String url = Constants.USER_Team_MESSAGE + "?user_id="
                + App.getUserLogin().user_id + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mCont2 = 0;
                        closeLoadingDialog();
                        Gson gson = new Gson();
                        myMsgTeamBean = gson.fromJson(jsonObject.toString(), MyMsgTeamBean.class);
                        if (myMsgTeamBean.getResponse().equals("success")) {
                            for (int i = 0; i < myMsgTeamBean.getLeague_invitations().size(); i++) {
                                if (myMsgTeamBean.getLeague_invitations().get(i).getLeague_invitation_status() == -1) {
                                    mCont2 += 1;
                                }
                            }
                            if (mCont2>0) {
                                mTv_cont2.setVisibility(View.VISIBLE);
                                mTv_cont2.setText(mCont2 + "");
                            }
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(getThis()).addRequest(jsonRequest);

    }


    /**
     * 获取组队消息
     */
    private void getBattleData() {
        showLoadingDiaglog();
        String url = Constants.USER_BATTLE_MESSAGE + "?user_id="
                + App.getUserLogin().user_id + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        mCont3 = 0;
                        closeLoadingDialog();
                        Gson gson = new Gson();
                        myMsgBattleBean = gson.fromJson(jsonObject.toString(), MyMsgBattleBean.class);
                        if (myMsgBattleBean.getResponse().equals("success")) {
                            for (int i = 0; i < myMsgBattleBean.getLeague_invitations().size(); i++) {
//                                if (myMsgBattleBean.getLeague_invitations().get(i).get() == -1) {
//                                    mCont3 += 1;
//                                }
                            }
                            if (mCont3>0) {
                                mTv_cont3.setVisibility(View.VISIBLE);
                                mTv_cont3.setText(mCont3 + "");
                            }
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(getThis()).addRequest(jsonRequest);

    }

    @OnClick(R.id.image_message)
    public void toMessage() {
        Intent intent = new Intent(getThis(), SystemMagActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", messagBean);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    @OnClick(R.id.image_team_message)
    public void toTeamMessage() {
        Intent intent = new Intent(getThis(), SystemTeamMsgActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", myMsgTeamBean);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    @OnClick(R.id.image_battle_message)
    public void toBattleMessage() {
        Intent intent = new Intent(getThis(), SystemBallteMsgActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("info", myMsgBattleBean);
        intent.putExtras(bundle);
        startActivity(intent);

    }
    @Subscribe
    public void refreshMessageData(SystemMsgEvent systemMsgEvent){
        getData();
    }
    @Subscribe
    public void refreshTeamData(SystemTeamMsgEvent systemMsgEvent){
        getTeamData();
    }
    @Subscribe
    public void refreshBattleData(SystemBattleMsgEvent systemMsgEvent){
        getBattleData();
    }

}
