package com.ktfootball.app.UI.Activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Adapter.MyFansAdapter;
import com.ktfootball.app.Adapter.MyFollowAdapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.MyFasnBean;
import com.ktfootball.app.Entity.MyFollowBean;
import com.ktfootball.app.Event.FollowEvent;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.MyBGARefreshViewHolder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import de.greenrobot.event.Subscribe;

/**
 * Created by leo on 16/10/20.
 */
public class MyFollowActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView layout_recyclerview_rv;
    private boolean isEmpte = false;
    private Long userId;
    private List<MyFollowBean.FollowersBean> mList = new ArrayList<>();
    private MyFollowAdapter mAdapter;
    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_follow);
        userId = getIntent().getLongExtra("user_id", 0);
        initRefreshLayout();
        initRecycler();
    }

    @Override
    protected void setListener() {

    }

    private void initRecycler() {
        layout_recyclerview_rv = getViewById(R.id.layout_recyclerview_rv);
        layout_recyclerview_rv.setLayoutManager(new LinearLayoutManager(getThis()));
        layout_recyclerview_rv.setAdapter(mAdapter = new MyFollowAdapter(getThis(),mList));
    }

    private void initRefreshLayout() {
        mRefreshLayout = getViewById(R.id.rl_modulename_refresh);
        mRefreshLayout.setDelegate(this);
        BGARefreshViewHolder refreshViewHolder = new MyBGARefreshViewHolder(getThis(), true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mRefreshLayout.beginRefreshing();
    }


    private void getData() {
        String url = Constants.USER_FOLLOWERS+"?user_id=" + userId
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
                        MyFollowBean fasnBean = gson.fromJson(jsonObject.toString(), MyFollowBean.class);
                        if (fasnBean.getResponse().equals("success")) {
                            mList = fasnBean.getFollowers();
                        }
                        refreshList(mList);

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
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        getData();

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    /**
     * 刷新数据
     */
    public void refreshList(List<MyFollowBean.FollowersBean> list) {
        mAdapter.setData(list);
        if (list.size() == 0) {
            if (!isEmpte) {
                isEmpte = true;
                mRefreshLayout.removeView(layout_recyclerview_rv);
                mRefreshLayout.addView(LayoutInflater.from(getThis()).inflate(R.layout.empty_view, null));
            }

        } else {
            isEmpte = false;
            boolean isVisinle = false;
            for (int i = 0; i < mRefreshLayout.getChildCount(); i++) {
                if (mRefreshLayout.getChildAt(i) instanceof RecyclerView) {
                    isVisinle = true;
                }
                if (i == mRefreshLayout.getChildCount() - 1 && !isVisinle) {
                    mRefreshLayout.addView(layout_recyclerview_rv);
                }
            }
        }
        mRefreshLayout.endRefreshing();
    }

    @OnClick(R.id.imageView45)
    public void back(){
        finish();
    }

    @Subscribe
    public void cancle(FollowEvent followEvent){
        mRefreshLayout.beginRefreshing();
    }
}

