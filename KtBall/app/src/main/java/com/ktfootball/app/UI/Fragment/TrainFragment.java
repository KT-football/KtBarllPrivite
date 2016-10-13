package com.ktfootball.app.UI.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.fragment.BaseRecyclerViewFragment;
import com.kt.ktball.App;
import com.ktfootball.app.Adapter.TrainAdapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.UserAppCartoons;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.UserAppCartoonsRequest;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by leo on 16/10/13.
 */

public class TrainFragment extends BaseRecyclerViewFragment{
    public static final int TO_TRAINDETSILS = 1001;
    public static final String APP_CARTOON_ID = "app_cartoon_id";

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doUserAppCartoons();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        doUserAppCartoons();
    }

    @Override
    protected void onUserVisible() {
        
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        setLayoutManager(mLayoutManager);
    }


    private void doUserAppCartoons() {
        setRefreshing(true);
        UserAppCartoonsRequest request = new UserAppCartoonsRequest(Constants.USER_APP_CARTOONS, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id + "");
        CallServer.getRequestInstance().add((BaseActivity) getActivity(), 0, request, httpListener, false, false);
    }

    private HttpListener httpListener = new HttpListener<UserAppCartoons>() {
        @Override
        public void onSucceed(int what, Response<UserAppCartoons> response) {
            UserAppCartoons userAppCartoons = response.get();
            setRefreshing(false);
            if (userAppCartoons.response.equals("success")) {
                initRecyclerView(userAppCartoons);
            } else {
                showToast(R.string.request_failed);
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            setRefreshing(false);
        }
    };

    private void initRecyclerView(UserAppCartoons userAppCartoons) {
        TrainAdapter trainAdapter = new TrainAdapter(userAppCartoons, (BaseActivity) getActivity());
        setAdapter(trainAdapter);
    }
}
