package com.ktfootball.app.UI.Fragment.Rank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.base.fragment.BaseRecyclerViewFragment;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.ktfootball.app.Adapter.StarViewAdapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.StarUsers;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.StarUsersRequest;
import com.ktfootball.app.UI.Activity.RankingListActivity;
import com.ktfootball.app.UI.Activity.SuperStar.SuperStarDetailsActivity;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by leo on 16/10/13.
 */

public class SuperStarFragment extends BaseRecyclerViewFragment {

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getThis(), 3);
        setLayoutManager(gridLayoutManager);
    }


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doStarUsers();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setRefreshing(true);
        doStarUsers();
    }

    @Override
    protected void onUserVisible() {

    }

    private void doStarUsers() {
        StarUsersRequest request = new StarUsersRequest(Constants.STAR_USERS, RequestMethod.GET);
        CallServer.getRequestInstance().add((BaseActivity) getActivity(), 0, request, new HttpListener<StarUsers>() {
            @Override
            public void onSucceed(int what, Response<StarUsers> response) {
                LogUtils.e(GsonTools.createGsonString(response.get()));
                setRefreshing(false);
                if (response.get().response.equals("success")) {
                    init(response.get());
                } else {
                    ((BaseActivity)getActivity()).showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                setRefreshing(false);
            }
        }, false, false);
    }

    private void init(final StarUsers starUsers) {
        StarViewAdapter starViewAdapter = new StarViewAdapter(getRecyclerView(), starUsers.users);
        setAdapter(starViewAdapter);
        starViewAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent intent = new Intent(getThis(), SuperStarDetailsActivity.class);
                intent.putExtra(Constants.SUPERSTAR_ID, starUsers.users.get(position).user_id);
                startActivity(intent);
            }
        });
    }
}
