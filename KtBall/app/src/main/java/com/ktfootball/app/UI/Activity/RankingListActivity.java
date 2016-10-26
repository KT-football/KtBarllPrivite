package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.adapter.RankingAdapter;
import com.kt.ktball.entity.RankingData;
import com.kt.ktball.entity.Users;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RankingListActivity extends BaseActivity {

    ListView listView;
    RankingAdapter rankingAdapter;
    long userId;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ranking_list);
        listView = (ListView) findViewById(R.id.listView5);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID,0);
        initView();
    }

    private void initView() {
        final String url = Constants.HOST +"users/range1v1_power_top100?user_id=" +
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
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                            String response = jsonObject1.getString("response");
                            if (response.equals("success")){
                                RankingData rankingData = new Gson().fromJson(jsonObject.toString(),
                                        new TypeToken<RankingData>(){}.getType());
                                Log.d("=========",rankingData.toString());
                                ArrayList<Users> data  = new ArrayList<>();
                                data = rankingData.users;
                                rankingAdapter = new RankingAdapter(RankingListActivity.this,data);
                                listView.setAdapter(rankingAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent = new Intent(RankingListActivity.this,UserProfiles.class);
                                        Users users = rankingAdapter.getItem(position);
                                        intent.putExtra(UserProfiles.USERID,users.user_id);
                                        startActivity(intent);
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
                Log.d("=========",volleyError.toString());
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    public void doBack(View view) {//退出
        finish();
    }
}
