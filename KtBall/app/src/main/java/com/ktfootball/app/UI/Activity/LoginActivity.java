package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.App;
import com.kt.ktball.entity.UserLogin;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.MyDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

public class LoginActivity extends BaseActivity {

    public static final String PRE_CURRENT_USER_ID = "user_id";
    public static final String PRE_CURRENT_IS_JUSGE = "is_jusge";
    @Bind(R.id.editText)
    EditText editTextUser;
    @Bind(R.id.editText2)
    EditText editTextPassword;

    String user;
    String password;
    MyDialog myDialog;
    MyAlertDialog myAlertDialog;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        myDialog = new MyDialog(this, "正在登录");
        myAlertDialog = new MyAlertDialog(this);
        //设置密码成暗码
        editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        editTextUser.setText(SharedPreferencesUtils.getString(getThis(), "username", ""));
        editTextPassword.setText(SharedPreferencesUtils.getString(getThis(), "password", ""));
    }

    //登录
    public void doLogin(View view) {
        user = editTextUser.getText().toString();
        password = editTextPassword.getText().toString();
//        user="15998206840";
//        password="123456";
//        user = "13482086618";
//        password = "aaa222";
//        user = "Leo@tempot.com";
//        password = "18601666098";
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password)) {
            myAlertDialog.doAlertDialog("请输入账号和密码");
        } else {
            myDialog.show();
            login();
        }
    }

    //向服务器发送json请求登录
    public void login() {
        String url = "http://www.ktfootball.com/apiv2/users/login";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", user);
            jsonObject.put("password", password);
            jsonObject.put("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtils.e(response.toString());
                        //关闭动画
                        myDialog.dismiss();
                        //解析从服务器上接收到的json数据
                        UserLogin userMsg = new Gson().fromJson(response.toString(), new TypeToken<UserLogin>() {
                        }.getType());
                        //返回的是错误
                        if (userMsg.response.equals("error")) {
                            myAlertDialog.doAlertDialog("用户名密码错误");
                        } else {
                            App.setLocalUserInfo(response.toString());
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit()
                                    .putLong(PRE_CURRENT_USER_ID, userMsg.user_id)
                                    .putInt(PRE_CURRENT_IS_JUSGE, userMsg.is_judge)
                                    .commit();
                            SharedPreferencesUtils.saveString(getThis(), "username", user);
                            SharedPreferencesUtils.saveString(getThis(), "password", password);
                            //把俱乐部的id用意图传递过去
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
//                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //关闭动画
                myDialog.dismiss();
                myAlertDialog.doAlertDialog("网络错误");
            }
        }) {

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


    public void doRegister(View view) {//注册
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void doForgetPassword(View view) {//忘记密码
        startActivity(new Intent(this, PasswordResetActivity.class));
    }
}
