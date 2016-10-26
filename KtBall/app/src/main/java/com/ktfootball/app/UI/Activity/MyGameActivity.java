package com.ktfootball.app.UI.Activity;

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
import com.kt.ktball.adapter.MyGameAdapter;
import com.kt.ktball.entity.GamePlace;
import com.kt.ktball.entity.Games;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyGameActivity extends BaseActivity {

    ListView listView;
    MyGameAdapter myGameAdapter;
    ArrayList<Games> data = new ArrayList<>();

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_game);
        listView = (ListView) findViewById(R.id.listView4);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {//初始化视图
        long userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID,0);
        int distance = 10000;
        String url = Constants.HOST +"games/club_games?user_id="
                + userId + "&distance=" + distance
                + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
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
                                GamePlace gamePlace = new Gson().fromJson(jsonObject.toString(),
                                        new TypeToken<GamePlace>(){}.getType());
                                data = gamePlace.games;
                                myGameAdapter = new MyGameAdapter(data,MyGameActivity.this);
                                listView.setAdapter(myGameAdapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                                        Intent intent = new Intent(MyGameActivity.this, )
                                    }
                                });
                            } else {
                                closeLoadingDialog();
                                String error = jsonObject1.getString("msg");
                                MyAlertDialog myAlertDialog = new MyAlertDialog(MyGameActivity.this);
                                myAlertDialog.doAlertDialog(error);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
                Log.d("onErrorResponse", volleyError.toString());
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    public void doBack(View view) {
        finish();
    }
}
