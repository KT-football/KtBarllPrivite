package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.App;
import com.ktfootball.app.Constants;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by jy on 16/6/8.
 */
public class TeamNameChangeActivity extends BaseActivity {

    @Bind(R.id.layout_teamnamechange_et)
    public EditText et;

    private String league_id;
    public  static final int NAME_CHANGE = 1001;
    public  static final int GOOUT_CHANGE = 1002;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_teamnamechange);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        league_id = getIntent().getStringExtra("league_id");
    }

    public void doBack(View v) {
        finish();
    }

    public void doUpdata(View v) {
        final String name = et.getText().toString();
        if (name == null || "".equals(name)) {
            showToast("请填写战队名称");
            return;
        }
        String url = Constants.CHANGE_LEAGUE_NAME;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("league_id", league_id);
            jsonObject.put("name", name);
            jsonObject.put("authenticity_token", MD5.getToken(url));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject1) {
                        Log.d("========", jsonObject1.toString());
                        showToast("修改成功");
                        Intent intent = new Intent();
                        intent.putExtra("name", name);
                        setResult(NAME_CHANGE,intent);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("========", error.toString());
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
        VolleyUtil.getInstance(getApplicationContext()).addRequest(jsonRequest);
    }

    public void doGoOut(View v) {
        String url = Constants.LEAVE_LEAGUE;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("league_id", league_id);
            jsonObject.put("user_id", App.getUserId());
            jsonObject.put("authenticity_token", MD5.getToken(url));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject1) {
                        Log.d("========", jsonObject1.toString());
                        showToast("退出成功");
                        setResult(GOOUT_CHANGE);
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("========", error.toString());
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
        VolleyUtil.getInstance(getApplicationContext()).addRequest(jsonRequest);
    }
}
