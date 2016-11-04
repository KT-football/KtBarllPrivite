package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.kt.ktball.adapter.MyFirendAdapter;
import com.kt.ktball.entity.FirendSearch;
import com.kt.ktball.entity.MyFirendData;
import com.kt.ktball.entity.Users;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyFirendActivity extends BaseActivity {

    long userId;//登录的用户
    ListView listView;
    MyFirendAdapter myFirendAdapter;
    EditText editText;
    RelativeLayout relativeLayout;
    TextView textView;
    ImageView imageView;
    TextView textViewName;
    TextView textViewP;
    TextView textViewAddFirend;
    long firendId;//好友用户

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_firend);
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
        initView();
    }

    private void initView() {
        userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID,0);
        String url = Constants.HOST +"users/followers?user_id=" +
                userId + "&authenticity_token="+MD5.getToken(Constants.HOST +"users/followers");
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
                                myFirendAdapter = new MyFirendAdapter(data,MyFirendActivity.this);
                                listView.setAdapter(myFirendAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Users users = myFirendAdapter.getItem(position);
                                        Intent intent = new Intent(MyFirendActivity.this,UserProfiles.class);
                                        intent.putExtra(UserProfiles.USERID,users.user_id);
                                        startActivity(intent);
                                    }
                                });
                            } else if (response.equals("error")){
                                String msg = jsonObject1.getString("msg");
                                MyAlertDialog myAlertDialog = new MyAlertDialog(MyFirendActivity.this);
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

    public void doBack(View view) {//退出
        finish();
    }

    public void doSearch(View view) {//搜索
        String input = editText.getText().toString();
        try {
            input = URLEncoder.encode(input,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(input)) {
            if (textView.getText().toString().equals(getString(R.string.search))) {//搜索
                String url = Constants.HOST +"users/search_user?keyword="
                        + input + "&authenticity_token="+MD5.getToken(Constants.HOST +"users/search_user");
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
                                        Glide.with(MyFirendActivity.this).load(url).transform(new GlideCircleTransform(MyFirendActivity.this)).into(imageView);
                                        textViewName.setText(data.nickname);
                                        textViewP.setText(getString(R.string.fighting_capacity)+"：" + data.power);
                                        textView.setText(getString(R.string.cancle));
                                    } else if (response.equals("error")){
                                        String msg = jsonObject1.getString("msg");
                                        MyAlertDialog myAlertDialog = new MyAlertDialog(MyFirendActivity.this);
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
                textView.setText(getString(R.string.search));
            }
        }
    }

    public void doFirendDetails(View view) {//到搜索好友的个人用户
        Intent intent = new Intent(MyFirendActivity.this,UserProfiles.class);
        intent.putExtra(UserProfiles.USERID,firendId);
        startActivity(intent);
    }

    public void doAddFirend(View view) {//搜索到的用户加为好友
        String url = Constants.HOST +"users/follow";
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("user_id",userId);
            jsonObject1.put("follow_user_id",firendId);
            jsonObject1.put("authenticity_token", MD5.getToken(url));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        try {
                            JSONObject jsonObject11 = new JSONObject(jsonObject.toString());
                            String response = jsonObject11.getString("response");
                            if (response.equals("success")){
                                textViewAddFirend.setText(getString(R.string.is_add));
                            } else if (response.equals("error")){
                                String msg = jsonObject11.getString("msg");
                                MyAlertDialog myAlertDialog = new MyAlertDialog(MyFirendActivity.this);
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
        ){
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }
}
