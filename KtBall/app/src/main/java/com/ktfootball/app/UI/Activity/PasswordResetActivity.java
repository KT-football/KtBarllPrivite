package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;

public class PasswordResetActivity extends BaseActivity {

    @Bind(R.id.editText3)
    EditText editTextPhone;
    @Bind(R.id.editText5)
    EditText editTextCode;
    @Bind(R.id.editText6)
    EditText editTextPassword;
    @Bind(R.id.editText7)
    EditText editTextPasswordAgain;
    @Bind(R.id.button2)
    Button buttonGetCode;

    MyAlertDialog myAlertDialog;
    Timer timer;//定时器
    TimerTask timerTask;//定时器任务
    int time = 60;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_password_reset);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        myAlertDialog = new MyAlertDialog(this);
    }

    public void doFinsh(View view) {//退出
        finish();
    }

    public void doGetCode(View view) {//获取验证码
        String phone = editTextPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            myAlertDialog.doAlertDialog("请输入手机号");
        } else if (phone.length() == 11) {
            doButtonChange();
            time = 60;
            String url = Constants.HOST + "users/send_mobile_captcha_for_forget_password?phone="
                    + phone + "&authenticity_token=" + MD5.getToken(Constants.HOST + "users/send_mobile_captcha_for_forget_password");
            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            }
            );
            VolleyUtil.getInstance(this).addRequest(jsonRequest);
        } else {
            myAlertDialog.doAlertDialog("手机号格式错误");
        }
    }

    private void doButtonChange() {//点击发送验证码后buttom状态的改变
        if (timer == null) timer = new Timer();
        if (timerTask != null) timerTask.cancel();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (time > 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonGetCode.setEnabled(false);
                            buttonGetCode.setText(time + "秒后重新发送");
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonGetCode.setEnabled(true);
                            buttonGetCode.setText("重新发送验证码");
                        }
                    });
                }
                time--;
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public void doResetPassword(View view) {//密码重置
        final String phone = editTextPhone.getText().toString();
        String code = editTextCode.getText().toString();
        final String password = editTextPassword.getText().toString();
        final String password_confirmation = editTextPasswordAgain.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            myAlertDialog.doAlertDialog("请输入手机号");
        } else if (TextUtils.isEmpty(code)) {
            myAlertDialog.doAlertDialog("请输入验证码");
        } else if (TextUtils.isEmpty(password)) {
            myAlertDialog.doAlertDialog("请输入密码");
        } else {
            String url = Constants.HOST + "users/reset_password_check_captcha" +
                    "?phone=" + phone + "&captcha=" + code +
                    "&authenticity_token="+MD5.getToken(Constants.HOST + "users/reset_password_check_captcha");
            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("=============jsonObject", jsonObject.toString());
                            try {
                                JSONObject jsonObject11 = new JSONObject(jsonObject.toString());
                                String response11 = jsonObject11.getString("response");
                                if (response11.equals("error")) {
                                    String msg = jsonObject11.getString("msg");
                                    myAlertDialog.doAlertDialog(msg);
                                } else if (response11.equals("success")) {
                                    String url = Constants.HOST + "users/reset_password";
                                    JSONObject jsonObject1 = new JSONObject();
                                    try {
                                        jsonObject1.put("phone", phone);
                                        jsonObject1.put("password", password);
                                        jsonObject1.put("password_confirmation", password_confirmation);
                                        jsonObject1.put("authenticity_token", MD5.getToken(url));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                                            Request.Method.POST,
                                            url,
                                            jsonObject1,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject jsonObject) {
                                                    Log.d("==============", jsonObject.toString());
                                                    try {
                                                        JSONObject jsonObject2 = new JSONObject(jsonObject.toString());
                                                        String response = jsonObject2.getString("response");
                                                        if (response.equals("error")) {
                                                            String msg = jsonObject2.getString("msg");
                                                            myAlertDialog.doAlertDialog(msg);
                                                        } else if (response.equals("success")) {
                                                            myAlertDialog.doAlertDialog("注册成功，跳转登录页面");
                                                            Handler handler = new Handler();
                                                            handler.postDelayed(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    startActivity(new Intent(PasswordResetActivity.this, LoginActivity.class));
                                                                    finish();
                                                                }
                                                            }, 1500);
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Log.d("==============", volleyError.toString());
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
                                    VolleyUtil.getInstance(PasswordResetActivity.this).addRequest(jsonRequest);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("==============", volleyError.toString());
                }
            }
            );
            VolleyUtil.getInstance(PasswordResetActivity.this).addRequest(jsonRequest);
        }
    }
}
