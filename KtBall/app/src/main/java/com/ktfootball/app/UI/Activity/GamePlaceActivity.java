package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.kt.ktball.App;
import com.ktfootball.app.Adapter.GamePlaceAdapter;
import com.ktfootball.app.Base.BaseRecyclerViewNoRefresh;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GamePlace;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.GameListRequest;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

public class GamePlaceActivity extends BaseRecyclerViewNoRefresh {

    private List<GamePlace.Games> list = null;
    private GamePlaceAdapter adapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.bg);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        setLayoutManager(ll);
    }

    @Override
    protected void initToolBar() {
        setToolBarTitle("比赛地点");
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                GamePlace.Games games = adapter.getDatas().get(position);
                Intent intent = new Intent(GamePlaceActivity.this, PlaceScanActivity.class);
                intent.putExtra(Constants.GAME,games);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        adapter = new GamePlaceAdapter(getRecyclerView(),list);
        setAdapter(adapter);
        doGameList();
    }

    private void doGameList() {
        showLoadingDiaglog();
        GameListRequest request = new GameListRequest(Constants.GAME_LIST, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id + "");
        request.add("lon", "121.564407,31.245315");
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<GamePlace>() {
            @Override
            public void onSucceed(int what, Response<GamePlace> response) {
                adapter.setDatas(response.get().games);
                closeLoadingDialog();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }
}
