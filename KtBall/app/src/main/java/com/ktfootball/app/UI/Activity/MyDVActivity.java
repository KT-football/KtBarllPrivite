package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
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
import com.kt.ktball.adapter.MyDVAdapter;
import com.kt.ktball.entity.StrataExternum;
import com.kt.ktball.entity.Videos;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class MyDVActivity extends BaseActivity {


    long userId;
    ListView listView;
    MyDVAdapter myDVAdapter;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_dv);
        listView = (ListView) findViewById(R.id.listView6);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        userId = intent.getLongExtra(UserProfiles.EXTRA_ME_OR_HE_USER_ID,0);
        String url = "http://www.ktfootball.com/apiv2/videos/my_videos?user_id=" +
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
                        Gson gson = new Gson();
                        Type type = new TypeToken<StrataExternum>() {
                        }.getType();
                        StrataExternum strataExternum = gson.fromJson(jsonObject.toString(), type);
                        Log.d("--------strataExternum",strataExternum.toString());
                        myDVAdapter = new MyDVAdapter(strataExternum,MyDVActivity.this);
                        listView.setAdapter(myDVAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Videos videos = myDVAdapter.getItem(position);
                                Intent intent = new Intent(MyDVActivity.this,VideoDetailsActivity.class);
                                intent.putExtra(Constants.EXTRA_VIDEOS_ID,videos.getGame_video_id());
                                intent.putExtra(Constants.EXTRA_SCORES,videos.getScores());
                                startActivity(intent);
                            }
                        });
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    public void doBack(View view) {
        finish();
    }
}
