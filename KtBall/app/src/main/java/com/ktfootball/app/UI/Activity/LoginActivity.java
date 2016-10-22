package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

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
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShareSDK.initSDK(this);
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
                            finish();
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


    /**
     * 第三方登陆
     *
     * @param userId
     * @param avatar   头像
     * @param nickname 名称
     * @param gender   性别
     * @param type
     */
    public void loginThird(String userId, String avatar, String nickname, String gender, int type) {
        String url = Constants.LOGIN_THIRD_PART;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nickname", nickname);
            jsonObject.put("openid", avatar);
            jsonObject.put("openid", userId);
            jsonObject.put("gender", gender);
            jsonObject.put("type", type);
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
                            myAlertDialog.doAlertDialog("网络错误");
                        } else {
                            App.setLocalUserInfo(response.toString());
                            PreferenceManager.getDefaultSharedPreferences(LoginActivity.this)
                                    .edit()
                                    .putLong(PRE_CURRENT_USER_ID, userMsg.user_id)
                                    .putInt(PRE_CURRENT_IS_JUSGE, userMsg.is_judge)
                                    .commit();
//                            SharedPreferencesUtils.saveString(getThis(), "username", user);
//                            SharedPreferencesUtils.saveString(getThis(), "password", password);
                            //把俱乐部的id用意图传递过去
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
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


    @OnClick(R.id.image_qq)
    public void goQQ() {
        myDialog.show();
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.SSOSetting(true);
        qq.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                PlatformDb platformDb = platform.getDb();
                loginThird(platformDb.getUserId(), platformDb.getUserIcon(), platformDb.getUserName(), platformDb.getUserGender(), 1);
                myDialog.dismiss();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                myDialog.dismiss();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                myDialog.dismiss();
            }
        });
        qq.authorize();
    }

    @OnClick(R.id.image_weibo)
    public void goWB() {
        myDialog.show();
        ShareSDK.initSDK(this);
        Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
        sina.SSOSetting(true);
        sina.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                PlatformDb platformDb = platform.getDb();
                loginThird(platformDb.getUserId(), platformDb.getUserIcon(), platformDb.getUserName(), platformDb.getUserGender(), 2);
                myDialog.dismiss();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                myDialog.dismiss();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                myDialog.dismiss();
            }
        });
        sina.authorize();
    }


    @OnClick(R.id.image_weixin)
    public void goWX() {
        myDialog.show();
        Platform platform_wb = ShareSDK.getPlatform(getThis(), Wechat.NAME);
        if (!platform_wb.isClientValid()) {
            showToast("微信未安装,请先安装微信");

        }
        platform_wb.SSOSetting(true);
        platform_wb.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                PlatformDb platformDb = platform.getDb();
                loginThird(platformDb.getUserId(), platformDb.getUserIcon(), platformDb.getUserName(), platformDb.getUserGender(), 0);
                myDialog.dismiss();
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                myDialog.dismiss();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                myDialog.dismiss();
            }
        });
        platform_wb.authorize();
    }
}
