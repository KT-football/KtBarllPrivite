package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.editText3)
    EditText editTextPhone;
    @Bind(R.id.editText5)
    EditText editTextCode;
    @Bind(R.id.editText4)
    EditText editTextName;
    @Bind(R.id.editText6)
    EditText editTextPassword;
    @Bind(R.id.editText7)
    EditText editTextPasswordAgain;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.textView50)
    TextView buttomRegister;
    @Bind(R.id.button2)
    Button buttonGetCode;
    @Bind(R.id.tv_isregister)
    TextView mTv_register;

    MyAlertDialog myAlertDialog;
    Timer timer;//定时器
    TimerTask timerTask;//定时器任务
    int time = 60;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        myAlertDialog = new MyAlertDialog(this);
        mTv_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public void doFinsh(View view) {//退出
        finish();
    }

    public void doRegisterNotice(View view) {//注册须知
        startActivity(new Intent(this, RegisterNoticeActivity.class));
    }

    public void doGetCode(View view) {//获取验证码
        String phone = editTextPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            myAlertDialog.doAlertDialog(getString(R.string.check_phone));
        } else if (phone.length() == 11) {
            doButtonChange();
            time = 60;
            String url = Constants.HOST +"users/send_mobile_captcha?phone="
                    + phone + "&authenticity_token="+ MD5.getToken(Constants.HOST +"users/send_mobile_captcha");
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
            myAlertDialog.doAlertDialog(getString(R.string.phone_error));
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
                            buttonGetCode.setText(time + getString(R.string.restate_send));
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonGetCode.setEnabled(true);
                            buttonGetCode.setText(getString(R.string.restart_yanzhen));
                        }
                    });
                }
                time--;
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    public void doRegister(View view) {//注册
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String code = editTextCode.getText().toString();
        String password = editTextPassword.getText().toString();
        String passwordAgain = editTextPasswordAgain.getText().toString();
        boolean flag = checkBox.isChecked();
        if (TextUtils.isEmpty(phone)) {
            myAlertDialog.doAlertDialog(getString(R.string.check_phone));
        } else if (TextUtils.isEmpty(code)) {
            myAlertDialog.doAlertDialog(getString(R.string.send_yanzhen));
        } else if (TextUtils.isEmpty(name)) {
            myAlertDialog.doAlertDialog(getString(R.string.send_user));
        } else if (TextUtils.isEmpty(password)) {
            myAlertDialog.doAlertDialog(getString(R.string.send_pwd));
        } else if (!password.equals(passwordAgain)) {
            myAlertDialog.doAlertDialog(getString(R.string.again_pwd_error));
        } else if (!flag) {
            myAlertDialog.doAlertDialog(getString(R.string.aggre_register));
        } else {
            String url = Constants.HOST +"users/mregister";
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("nickname", name);
                jsonObject1.put("phone", phone);
                jsonObject1.put("password", password);
                jsonObject1.put("password_confirmation", passwordAgain);
                jsonObject1.put("captcha", code);
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
                                    myAlertDialog.doAlertDialog(getString(R.string.register_ok));
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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
            VolleyUtil.getInstance(this).addRequest(jsonRequest);
        }
    }

    @OnClick(R.id.tv_isregister)
    public void isreister() {
        finish();
    }
}
