package com.ktfootball.app.UI.Fragment.UserDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.App;
import com.kt.ktball.entity.Leagues;
import com.kt.ktball.entity.LeaguesData;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Adapter.CorpsAdapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.BattleBean;
import com.ktfootball.app.Event.AddTeamEvent;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.NewTeamActivity;
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.ktfootball.app.Utils.MD5;
import com.ktfootball.app.Utils.MyBGARefreshViewHolder;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import de.greenrobot.event.Subscribe;

/**
 * Created by leo on 16/10/17.
 */

public class UserTeamFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    List<BattleBean.VideosBean> mList = new ArrayList<>();
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView layout_recyclerview_rv;
    private CorpsAdapter mAdapter;
    private Long userId;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_corps);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userId = ((UserProfiles) getActivity()).userId;
        initRefreshLayout();
        initRecycler();
        getData();
    }

    private void getData() {
        showLoadingDiaglog();
        String url = Constants.HOST +"users/leagues?user_id="
                + userId + "&authenticity_token="+ MD5.getToken(Constants.HOST +"users/leagues");
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("JSONObjectJSONObject", jsonObject.toString());
                        closeLoadingDialog();
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                            String response = jsonObject1.getString("response");
                            if (response.equals("success")) {
                                LeaguesData data = new Gson().fromJson(jsonObject.toString(),
                                        new TypeToken<LeaguesData>() {
                                        }.getType());
                                ArrayList<Leagues> leagues = data.leagues;
                                final ArrayList<Leagues> leagues1 = new ArrayList<>();
                                ArrayList<Leagues> leagues2 = new ArrayList<>();
                                ArrayList<Leagues> leagues3 = new ArrayList<>();
                                for (int i = 0; i < leagues.size(); i++) {
                                    if (leagues.get(i).game_type.equals("2")) {
                                        leagues.get(i).code = 2;
                                        leagues3.add(leagues.get(i));
                                    } else if (leagues.get(i).game_type.equals("1")) {
                                        leagues.get(i).code = 1;
                                        leagues2.add(leagues.get(i));
                                    } else {
                                        leagues.get(i).code = 1;
                                        leagues1.add(leagues.get(i));
                                    }
                                }
                                if (leagues3.size() == 0) {
                                    Leagues l = new Leagues();
                                    l.game_type = "2";
                                    l.code = 2;
                                    leagues3.add(l);
                                }
                                if (leagues2.size() == 0 && leagues1.size() == 0) {
                                    Leagues l = new Leagues();
                                    l.game_type = "1";
                                    l.code = 1;
                                    leagues2.add(l);
                                }
                                leagues1.addAll(leagues2);
                                leagues1.addAll(leagues3);
                                mAdapter.setData(leagues1);
                                mRefreshLayout.endRefreshing();
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
        VolleyUtil.getInstance(getThis()).addRequest(jsonRequest);

    }

    private void initRecycler() {
        layout_recyclerview_rv = getViewById(R.id.layout_recyclerview_rv);
        layout_recyclerview_rv.setLayoutManager(new LinearLayoutManager(getThis()));
        if (userId == App.getUserId()) {
            mAdapter = new CorpsAdapter(getThis());
        }else{
            mAdapter = new CorpsAdapter(getThis(),true);
        }
        layout_recyclerview_rv.setAdapter(mAdapter);
        StickyRecyclerHeadersDecoration headersDecor;
        layout_recyclerview_rv.addItemDecoration(headersDecor = new StickyRecyclerHeadersDecoration(mAdapter));
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(layout_recyclerview_rv, headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        if (userId == App.getUserId()) {
                            Intent intent = new Intent(getThis(), NewTeamActivity.class);
                            intent.putExtra("type", headerId + "");
                            startActivity(intent);
                        }
                    }
                });
        layout_recyclerview_rv.addOnItemTouchListener(touchListener);
    }

    private void initRefreshLayout() {
        mRefreshLayout = getViewById(R.id.rl_modulename_refresh);
        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new MyBGARefreshViewHolder(getActivity(), true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Subscribe
    public void addTeam(AddTeamEvent e) {
        mRefreshLayout.beginRefreshing();
    }
}