package com.ktfootball.app.UI.Activity.train;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.utils.LogUtils;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.PlayerActivity;
import com.ktfootball.app.Adapter.PersonVideoAdapter;
import com.ktfootball.app.Base.BaseRecyclerView;
import com.ktfootball.app.Entity.AppCartoon;

/**
 * Created by jy on 16/6/22.
 */
public class PersonVedioActivity extends BaseRecyclerView {

    private AppCartoon appCartoon;
    private PersonVideoAdapter adapter;

    @Override
    protected void initToolBar() {
        setToolBarTitle(getString(R.string.really_video));
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
                Intent i = new Intent(getThis(),PlayerActivity.class);
                String id = getVideoId(appCartoon.youku_videos.get(position));
                LogUtils.e(id);
                i.putExtra("vid", id);
                startActivity(i);
            }
        });
    }

    private String getVideoId(String s) {
        int startindex = s.lastIndexOf("/");
        int endindex = s.lastIndexOf(",");
        return s.substring(startindex+1,endindex);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        appCartoon = (AppCartoon) getIntent().getSerializableExtra("data");
        adapter = new PersonVideoAdapter(getRecyclerView(), appCartoon.youku_videos);
        setAdapter(adapter);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getThis());
        setLayoutManager(mLayoutManager);
    }
}
