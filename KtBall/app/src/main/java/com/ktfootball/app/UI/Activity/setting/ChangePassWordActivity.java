package com.ktfootball.app.UI.Activity.setting;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;

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
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/6/9.
 */
public class ChangePassWordActivity extends BaseActivity {
    @Bind(R.id.edt1)
    EditText edt1;
    @Bind(R.id.edt2)
    EditText edt2;
    @Bind(R.id.edt3)
    EditText edt3;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_pwd);
    }

    @Override
    protected void setListener() {

    }

    public void doBack(View view) {
        finish();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_ok)
    public void changer() {
        if (edt2.getText().toString().trim().equals(edt3.getText().toString().trim())) {
            getData();
        } else {
            showToast("输入的密码不一致~");
        }
    }

    private void getData() {
        showLoadingDiaglog();
        String url = Constants.CHANGE_PWD + "?user_id="
                + App.getUserLogin().user_id + "&password=" + edt1.getText().toString() + "&new_password=" + edt2.getText().toString() + "&authenticity_token="+ MD5.getToken(Constants.CHANGE_PWD);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        try {
                            if (jsonObject.getString("response").equals("success")) {
                                showToast("修改密码成功~");
                                finish();
                            } else {
                                showToast(jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
