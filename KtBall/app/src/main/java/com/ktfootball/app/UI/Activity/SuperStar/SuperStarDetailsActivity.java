package com.ktfootball.app.UI.Activity.SuperStar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
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
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jy on 16/7/21.
 */
public class SuperStarDetailsActivity extends BaseActivity {

    public String userId;
    public StarUserProfile starUserProfile;
    @Bind(R.id.image_head)
    ImageView mHead;
    @Bind(R.id.tv_name)
    TextView mTv_name;
    @Bind(R.id.tv_tab1)
    TextView mTab1;
    @Bind(R.id.tv_tab2)
    TextView mTab2;
    @Bind(R.id.tv_content)
    TextView mTv_content;
    @Bind(R.id.recycler)
    RecyclerView mRecyclerView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_superstatr_detail);
    }


    @Override
    protected void initHandler(Message msg) {

    }


    public void doBack(View view) {
        finish();
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userId = getIntent().getStringExtra(Constants.SUPERSTAR_ID);
        doStarUserProfile();
    }


    private void doStarUserProfile() {
        final StarUserProFileRequest request = new StarUserProFileRequest(Constants.STAR_USER_PROFILE, RequestMethod.GET);
        request.add("user_id", userId);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<StarUserProfile>() {
            @Override
            public void onSucceed(int what, Response<StarUserProfile> response) {
                if (isSuccess(response.get().response)) {
                    starUserProfile = response.get();
                    mTv_name.setText(response.get().nickname);
                    Glide.with(getThis()).load(Constants.HEAD_HOST + response.get().avatar).error(R.drawable.result_btnkt).into(mHead);
                    mTv_content.setText(response.get().star_intro);
                    doCommentedVideos();
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, false, false);
    }

    private void doCommentedVideos() {
        CommentedRequest request = new CommentedRequest(Constants.COMMENTED_VIDEOS, RequestMethod.GET);
        request.add("user_id", userId);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<CommentedVideos>() {
            @Override
            public void onSucceed(int what, Response<CommentedVideos> response) {
                if (isSuccess(response.get().response)) {
                    init(response.get());
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, false, false);
    }

    private void init(CommentedVideos commentedVideos) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getThis()));
        SuperStarDetailsAdapter superStarDetailsAdapter = new SuperStarDetailsAdapter(getThis(), commentedVideos.game_videos, starUserProfile);
        mRecyclerView.setAdapter(superStarDetailsAdapter);
    }

    @OnClick(R.id.tv_main)
    public void goDetail() {
        Intent intent = new Intent(getThis(), UserProfiles.class);
        intent.putExtra(UserProfiles.USERID, Long.valueOf(userId));
        startActivity(intent);
    }

    @OnClick(R.id.tv_tab1)
    public void checkTab1() {
        mTab1.setBackgroundColor(getResources().getColor(R.color.gold));
        mTab1.setTextColor(0xffffffff);
        mTab2.setTextColor(getResources().getColor(R.color.gold));
        mTab2.setBackgroundColor(0xffffffff);
        mTv_content.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @OnClick(R.id.tv_tab2)
    public void checkTab2() {
        mTab2.setBackgroundColor(getResources().getColor(R.color.gold));
        mTab2.setTextColor(0xffffffff);
        mTab1.setTextColor(getResources().getColor(R.color.gold));
        mTab1.setBackgroundColor(0xffffffff);
        mRecyclerView.setVisibility(View.VISIBLE);
        mTv_content.setVisibility(View.GONE);

    }
}
