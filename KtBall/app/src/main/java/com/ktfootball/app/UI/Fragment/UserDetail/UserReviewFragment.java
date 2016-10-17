package com.ktfootball.app.UI.Fragment.UserDetail;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Adapter.BattleChildAdapter;
import com.ktfootball.app.Entity.BattleBean;
import com.ktfootball.app.Event.BattleEvent;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.ktfootball.app.Utils.MyBGARefreshViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import de.greenrobot.event.EventBus;

/**
 * Created by leo on 16/10/17.
 */

public class UserReviewFragment  extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    List<BattleBean.VideosBean> mList = new ArrayList<>();
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView layout_recyclerview_rv;
    private BattleChildAdapter mBattleChildAdapter;
    private Long userId;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_battle_child);
        userId = ((UserProfiles) getActivity()).userId;
        initRefreshLayout();
        initRecycler();
        mRefreshLayout.beginRefreshing();
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


    public void getVideoList() {
        String url = "http://www.ktfootball.com/apiv2/videos/my_videos?user_id=" + userId
                + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        Gson gson = new Gson();
                        BattleBean battleBean = gson.fromJson(jsonObject.toString(), BattleBean.class);
                        refreshList(battleBean.getVideos());

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

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    /**
     * 刷新数据
     */
    public void refreshList(List<BattleBean.VideosBean> list) {
        mList = list;
        mBattleChildAdapter.setData(list);
        mRefreshLayout.endRefreshing();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getVideoList();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}