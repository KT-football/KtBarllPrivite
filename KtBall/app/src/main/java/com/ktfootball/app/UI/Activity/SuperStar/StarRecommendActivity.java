package com.ktfootball.app.UI.Activity.SuperStar;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ktfootball.app.Adapter.SuperStarRecommendAdapter;
import com.ktfootball.app.Base.BaseRecyclerView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.CommentedVideos;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.CommentedRequest;
import com.yolanda.nohttp.RequestMethod;

/**
 * Created by jy on 16/7/26.
 */
public class StarRecommendActivity extends BaseRecyclerView {

    private String userId;

    @Override
    protected void initToolBar() {
        setToolBarTitle("明星推荐");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.bg);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        setLayoutManager(ll);
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
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doGetPushVideos();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userId = getIntent().getStringExtra(Constants.USER_ID);

    }

    @Override
    protected void onStart() {
        super.onStart();
        doGetPushVideos();
    }

    public void doGetPushVideos() {
        setRefreshing(true);
        CommentedRequest request = new CommentedRequest(Constants.GET_PUSH_VIDEOS, RequestMethod.GET);
        request.add("user_id", userId);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<CommentedVideos>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<CommentedVideos> response) {
                setRefreshing(false);
                if (response.get().response.equals("success")) {
                    SuperStarRecommendAdapter starRecommendActivity = new SuperStarRecommendAdapter(getThis(), response.get().game_videos);
                    setAdapter(starRecommendActivity);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setRefreshing(false);
            }
        }, false, false);
    }
}
