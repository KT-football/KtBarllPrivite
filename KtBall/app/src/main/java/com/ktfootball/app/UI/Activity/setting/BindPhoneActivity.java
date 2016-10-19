package com.ktfootball.app.UI.Activity.setting;

import android.os.Bundle;
import android.os.Message;
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
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/19.
 */
public class BindPhoneActivity extends BaseActivity {
    @Bind(R.id.button2)
    Button buttonGetCode;
    @Bind(R.id.edt1)
    EditText edt1;
    @Bind(R.id.edt2)
    EditText edt2;
    Timer timer;//定时器
    TimerTask timerTask;//定时器任务
    int time = 60;
    MyAlertDialog myAlertDialog;
    String phone;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bind_phone);

    }

    public void doBack(View view) {
        finish();
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        myAlertDialog = new MyAlertDialog(this);
    }

    @OnClick(R.id.tv_ok)
    public void changer() {
        if (edt2.getText().toString() != null) {
            showLoadingDiaglog();
            String url = "http://www.ktfootball.com/apiv2/users/binding_new_mobile?phone="
                    + phone + "&captcha=" + edt2.getText().toString() + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            closeLoadingDialog();
                            try {
                                if (jsonObject.getString("response").equals("success")) {
                                    showToast("绑定手机成功~");
                                    finish();
                                } else {
                                    showToast(jsonObject.getString("msg"));
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
            );
            VolleyUtil.getInstance(this).addRequest(jsonRequest);
        } else {
            showToast("验证码不能为空~");
        }
    }


    public void doGetCode(View view) {//获取验证码
        phone = edt1.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            myAlertDialog.doAlertDialog("请输入手机号");
        } else if (phone.length() == 11) {
            String url = "http://www.ktfootball.com/apiv2/users/send_mobile_captcha_for_binding_new_mobile?phone="
                    + phone + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                if (jsonObject.getString("response").equals("success")) {
                                    doButtonChange();
                                    time = 60;
                                } else {
                                    showToast(jsonObject.getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
}
