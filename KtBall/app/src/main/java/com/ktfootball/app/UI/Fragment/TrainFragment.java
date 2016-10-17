package com.ktfootball.app.UI.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.base.fragment.BaseRecyclerViewFragment;
import com.google.gson.Gson;
import com.kt.ktball.App;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Adapter.BattleChildAdapter;
import com.ktfootball.app.Adapter.TrainAdapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.BattleBean;
import com.ktfootball.app.Entity.UserAppCartoons;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.UserAppCartoonsRequest;
import com.ktfootball.app.Utils.MyBGARefreshViewHolder;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by leo on 16/10/13.
 */

public class TrainFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    List<BattleBean.VideosBean> mList = new ArrayList<>();
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView layout_recyclerview_rv;
    private BattleChildAdapter mBattleChildAdapter;
    public static final int TO_TRAINDETSILS = 1001;
    public static final String APP_CARTOON_ID = "app_cartoon_id";

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_train);
        initRefreshLayout();
        initRecycler();
    }


    private void initRecycler() {
        layout_recyclerview_rv = getViewById(R.id.layout_recyclerview_rv);
        layout_recyclerview_rv.setLayoutManager(new LinearLayoutManager(getThis()));
        layout_recyclerview_rv.setAdapter(mBattleChildAdapter = new BattleChildAdapter(getThis(), mList));
    }

    private void initRefreshLayout() {
        mRefreshLayout = getViewById(R.id.rl_modulename_refresh);
        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new MyBGARefreshViewHolder(getActivity(), true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }
    @Override
    protected void setListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        doUserAppCartoons();
    }

    @Override
    protected void onUserVisible() {

    }


    private void doUserAppCartoons() {
        UserAppCartoonsRequest request = new UserAppCartoonsRequest(Constants.USER_APP_CARTOONS, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id + "");
        CallServer.getRequestInstance().add((BaseActivity) getActivity(), 0, request, httpListener, false, false);
    }

    private HttpListener httpListener = new HttpListener<UserAppCartoons>() {
        @Override
        public void onSucceed(int what, Response<UserAppCartoons> response) {
            UserAppCartoons userAppCartoons = response.get();
            if (userAppCartoons.response.equals("success")) {
                initRecyclerView(userAppCartoons);
            } else {
                showToast(R.string.request_failed);
            }
            mRefreshLayout.endRefreshing();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            mRefreshLayout.endRefreshing();
        }
    };

    private void initRecyclerView(UserAppCartoons userAppCartoons) {
        TrainAdapter trainAdapter = new TrainAdapter(userAppCartoons, (BaseActivity) getActivity());
        layout_recyclerview_rv.setAdapter(trainAdapter);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
            doUserAppCartoons();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }



}
