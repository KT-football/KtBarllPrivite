package com.ktfootball.app.UI.Activity.SuperStar;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.ktfootball.app.Adapter.SuperStarDetailsAdapter;
import com.ktfootball.app.Base.BaseRecyclerView;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.CommentedVideos;
import com.ktfootball.app.Entity.StarUserProfile;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.CommentedRequest;
import com.ktfootball.app.Request.StarUserProFileRequest;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by jy on 16/7/21.
 */
public class SuperStarDetailsActivity extends BaseRecyclerView {

    public String userId;
    public StarUserProfile starUserProfile;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.bg);
        LinearLayoutManager ll = new LinearLayoutManager(getThis());
        setLayoutManager(ll);
        getRecyclerView().setBackgroundResource(R.drawable.bg_app_cartoon_item);
    }

    @Override
    protected void initToolBar() {
        setToolBarTitle("明星详情");
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
                doCommentedVideos();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setRefreshing(true);
        userId = getIntent().getStringExtra(Constants.SUPERSTAR_ID);
    }

    @Override
    protected void onStart() {
        super.onStart();
        doStarUserProfile();
    }

    private void doStarUserProfile() {
        StarUserProFileRequest request = new StarUserProFileRequest(Constants.STAR_USER_PROFILE, RequestMethod.GET);
        request.add("user_id", userId);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<StarUserProfile>() {
            @Override
            public void onSucceed(int what, Response<StarUserProfile> response) {
                if (isSuccess(response.get().response)) {
                    starUserProfile = response.get();
                    doCommentedVideos();
                } else {
                    showDialogToast(response.get().msg);
                    setRefreshing(false);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setRefreshing(false);
            }
        }, false, false);
    }

    private void doCommentedVideos() {
        CommentedRequest request = new CommentedRequest(Constants.COMMENTED_VIDEOS, RequestMethod.GET);
        request.add("user_id", userId);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<CommentedVideos>() {
            @Override
            public void onSucceed(int what, Response<CommentedVideos> response) {
                setRefreshing(false);
                if (isSuccess(response.get().response)) {
                    init(response.get());
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setRefreshing(false);
            }
        }, false, false);
    }

    private void init(CommentedVideos commentedVideos) {
        SuperStarDetailsAdapter superStarDetailsAdapter = new SuperStarDetailsAdapter(getThis(), commentedVideos.game_videos, starUserProfile);
        setAdapter(superStarDetailsAdapter);
    }
}
