package com.ktfootball.app.UI.Fragment.Train;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.fragment.BaseFragment;
import com.kt.ktball.App;
import com.ktfootball.app.Adapter.AdapterItem;
import com.ktfootball.app.Adapter.BattleChildAdapter;
import com.ktfootball.app.Adapter.CommonRcvAdapter;
import com.ktfootball.app.Adapter.ItemAddClass;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.AppCartoons;
import com.ktfootball.app.Entity.BattleBean;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.AppCartoonsRequest;
import com.ktfootball.app.UI.Activity.train.TrainDetailsActivity;
import com.ktfootball.app.Utils.MyBGARefreshViewHolder;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by leo on 16/10/18.
 */

public class AddClassFragment extends BaseFragment  implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private AppCartoons userAppCartoons;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView layout_recyclerview_rv;
    private List<AppCartoons.Cartoons> mList = new ArrayList<>();
    private ItemAddClass mAdapter;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_add_class);
        initRefreshLayout();
        initRecycler();
    }


    private void initRecycler() {
        layout_recyclerview_rv = getViewById(R.id.layout_recyclerview_rv);
        layout_recyclerview_rv.setLayoutManager(new LinearLayoutManager(getThis()));

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
        mRefreshLayout.beginRefreshing();
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    private void doGetAllTrainList(){
        showLoadingDiaglog();
        AppCartoonsRequest request = new AppCartoonsRequest(Constants.APP_CARTOONS, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id + "");
        request.add("tag", getArguments().getString("tag"));
        CallServer.getRequestInstance().add((BaseActivity) getActivity(), 0, request, httpListener, false, false);
    }

    private HttpListener httpListener = new HttpListener<AppCartoons>() {
        @Override
        public void onSucceed(int what, Response<AppCartoons> response) {
            userAppCartoons = response.get();
            if (userAppCartoons.response.equals("success")) {
                mList = response.get().games;
                layout_recyclerview_rv.setAdapter(mAdapter = new ItemAddClass(getThis(),mList));
            } else {
                showToast(R.string.request_failed);
            }
            closeLoadingDialog();
            mRefreshLayout.endRefreshing();
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            closeLoadingDialog();
            mRefreshLayout.endRefreshing();
        }
    };

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        doGetAllTrainList();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

}
