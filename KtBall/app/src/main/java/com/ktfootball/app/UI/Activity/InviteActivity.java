package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.adapter.InviteAdapter;
import com.kt.ktball.entity.FirendSearch;
import com.kt.ktball.entity.MyFirendData;
import com.kt.ktball.entity.Users;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InviteActivity extends BaseActivity {

    long userId;//登录的用户
    ListView listView;
    InviteAdapter adapter;
    EditText editText;
    RelativeLayout relativeLayout;
    TextView textView;
    ImageView imageView;
    TextView textViewName;
    TextView textViewP;
    TextView textViewAddFirend;
    long firendId;//好友用户
    public static String game_type;
    public static long league_id;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_invite);
        listView = (ListView) findViewById(R.id.listView7);
        editText = (EditText) findViewById(R.id.editText8);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout009);
        textView = (TextView) findViewById(R.id.textView57);
        imageView = (ImageView) findViewById(R.id.imageView53);
        textViewName = (TextView) findViewById(R.id.textView58);
        textViewP = (TextView) findViewById(R.id.textView59);
        textViewAddFirend = (TextView) findViewById(R.id.textView60);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        game_type = intent.getStringExtra(MyTeamActivity.EXTRA_GAME_TYPE);
        league_id = intent.getLongExtra(MyTeamActivity.EXTRA_TEAM_ID,0);
        initView();
    }

    private void initView() {
        userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID,0);
        String url = Constants.HOST +"users/followers?user_id=" +
                userId + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        Log.d("=========", jsonObject.toString());
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                            String response = jsonObject1.getString("response");
                            if (response.equals("success")){
                                MyFirendData myFirendData = new Gson().fromJson(jsonObject.toString(),
                                        new TypeToken<MyFirendData>(){}.getType());
                                ArrayList<Users> data = myFirendData.followers;
                                Log.d("=========", data.toString());
                                adapter = new InviteAdapter(data,InviteActivity.this);
                                listView.setAdapter(adapter);
                            } else if (response.equals("error")){
                                String msg = jsonObject1.getString("msg");
                                MyAlertDialog myAlertDialog = new MyAlertDialog(InviteActivity.this);
                                myAlertDialog.doAlertDialog(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
                Log.d("=========", volleyError.toString());
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    public void doBack(View view) {
        finish();
    }

    public void doSearch(View view) {//搜索
        String input = editText.getText().toString();
        try {
            input = URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(input)) {
            if (textView.getText().toString().equals("搜索")) {//搜索
                String url = Constants.HOST +"users/search_user?keyword="
                        + input + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
                showLoadingDiaglog();
                JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                closeLoadingDialog();
                                try {
                                    JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                                    String response = jsonObject1.getString("response");
                                    if (response.equals("success")){
                                        FirendSearch data = new Gson().fromJson(jsonObject.toString(),
                                                new TypeToken<FirendSearch>() {
                                                }.getType());
                                        firendId = data.user_id;
                                        listView.setVisibility(View.GONE);
                                        relativeLayout.setVisibility(View.VISIBLE);
                                        String url = Constants.HEAD_HOST + data.avatar;
                                        Glide.with(InviteActivity.this).load(url).transform(new GlideCircleTransform(InviteActivity.this)).into(imageView);
                                        textViewName.setText(data.nickname);
                                        textViewP.setText("战斗力：" + data.power);
                                        textView.setText("取消");
                                    } else if (response.equals("error")){
                                        String msg = jsonObject1.getString("msg");
                                        MyAlertDialog myAlertDialog = new MyAlertDialog(InviteActivity.this);
                                        myAlertDialog.doAlertDialog(msg);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        closeLoadingDialog();
                    }
                }
                ) {
                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Accept", "application/json");
                        headers.put("Content-Type", "application/json; charset=UTF-8");
                        return headers;
                    }
                };
                VolleyUtil.getInstance(this).addRequest(jsonRequest);
            } else {//取消
                relativeLayout.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                textView.setText("搜索");
            }
        }
    }

    public void doInviteFirend(View view) {//邀请搜索到的好友
        String url = "";
        if (game_type.equals("2")){
            url = Constants.HOST +"users/invite_league3v3";
        } else {
            url = Constants.HOST +"users/invite_league";
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", userId);
            jsonObject.put("to_user_id", firendId);
            jsonObject.put("league_id",InviteActivity.league_id);
            jsonObject.put("authenticity_token","K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST,url,jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject object) {
                        try {
                            MyAlertDialog myAlertDialog = new MyAlertDialog(InviteActivity.this);
                            JSONObject jsonObject1 = new JSONObject(object.toString());
                            String response = jsonObject1.getString("response");
                            if (response.equals("success")){
                                textViewAddFirend.setText("已邀请");
                                myAlertDialog.doAlertDialog("发送邀请成功");
                            } else if (response.equals("error")){
                                String msg = jsonObject1.getString("msg");
                                myAlertDialog.doAlertDialog(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
        {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        VolleyUtil.getInstance(InviteActivity.this).addRequest(jsonRequest);
    }
}
