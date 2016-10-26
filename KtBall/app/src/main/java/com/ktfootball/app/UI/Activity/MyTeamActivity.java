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
import com.kt.ktball.adapter.MyTeamAdapter;
import com.kt.ktball.entity.Leagues;
import com.kt.ktball.entity.LeaguesData;
import com.kt.ktball.myclass.MyDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyTeamActivity extends BaseActivity {

    public static final String EXTRA_TEAM_ID = "team_id";
    public static final String EXTRA_GAME_TYPE = "game_type";
    MyDialog myDialog;
    long userId;
    ListView listView1;
    ListView listView2;
    MyTeamAdapter myTeamAdapter1;
    MyTeamAdapter myTeamAdapter2;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_team);
        listView1 = (ListView) findViewById(R.id.listView8);
        listView2 = (ListView) findViewById(R.id.listView88);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        userId = intent.getLongExtra(UserProfiles.EXTRA_ME_OR_HE_USER_ID,0);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        myDialog = new MyDialog(this,"正在更新");
        myDialog.show();
        String url = Constants.HOST +"users/leagues?user_id="
                + userId + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("JSONObjectJSONObject",jsonObject.toString());
                        myDialog.dismiss();
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                            String response = jsonObject1.getString("response");
                            if (response.equals("success")){
                                LeaguesData data = new Gson().fromJson(jsonObject.toString(),
                                        new TypeToken<LeaguesData>(){}.getType());
                                ArrayList<Leagues> leagues = data.leagues;
                                final ArrayList<Leagues> leagues1 = new ArrayList<>();
                                ArrayList<Leagues> leagues2 = new ArrayList<>();
                                for (int i = 0;i<leagues.size();i++){
                                    if (leagues.get(i).game_type.equals("2")){
                                        leagues2.add(leagues.get(i));
                                    } else {
                                        leagues1.add(leagues.get(i));
                                    }
                                }
                                myTeamAdapter1 = new MyTeamAdapter(leagues1,MyTeamActivity.this);
                                listView1.setAdapter(myTeamAdapter1);
                                listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Leagues leagues3 = myTeamAdapter1.getItem(position);
                                        Intent intent = new Intent(MyTeamActivity.this, TeamDetailsActivity.class);
                                        intent.putExtra(EXTRA_TEAM_ID, leagues3.league_id);
                                        intent.putExtra(EXTRA_GAME_TYPE,leagues3.game_type);
                                        startActivity(intent);
                                    }
                                });
                                myTeamAdapter2 = new MyTeamAdapter(leagues2,MyTeamActivity.this);
                                listView2.setAdapter(myTeamAdapter2);
                                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Leagues leagues4 = myTeamAdapter2.getItem(position);
                                        Intent intent = new Intent(MyTeamActivity.this, TeamDetailsActivity.class);
                                        intent.putExtra(EXTRA_TEAM_ID, leagues4.league_id);
                                        intent.putExtra(EXTRA_GAME_TYPE,leagues4.game_type);
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
                myDialog.dismiss();
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    public void doBack(View view) {
        finish();
    }

    public void doNewTeam(View view) {//创建战队
        startActivity(new Intent(this,NewTeamActivity.class));
    }
}
