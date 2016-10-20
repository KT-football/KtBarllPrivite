package com.ktfootball.app.UI.Activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.Adapter.SystemBattleMsgItem;
import com.ktfootball.app.Adapter.SystemTeamMsgItem;
import com.ktfootball.app.Entity.MyMsgBattleBean;
import com.ktfootball.app.Entity.MyMsgTeamBean;
import com.ktfootball.app.Event.SystemBattleMsgEvent;
import com.ktfootball.app.Event.SystemMsgEvent;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.MyBGARefreshViewHolder;

import butterknife.Bind;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import de.greenrobot.event.EventBus;

/**
 * Created by leo on 16/10/20.
 */

public class SystemBallteMsgActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    MyMsgBattleBean messagBean;
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView layout_recyclerview_rv;
    @Bind(R.id.empty)
    View mEmpty;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_battle_msg);
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
        BGARefreshViewHolder refreshViewHolder = new MyBGARefreshViewHolder(getThis(), true);
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }


    @Override
    protected void setListener() {

    }

    @OnClick(R.id.imageView45)
    public void toBack() {
        finish();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        messagBean = (MyMsgBattleBean) getIntent().getSerializableExtra("info");
        if (messagBean == null || messagBean.getLeague_invitations().size() == 0) {
            mEmpty.setVisibility(View.VISIBLE);
            layout_recyclerview_rv.setVisibility(View.GONE);
        }else{
            layout_recyclerview_rv.setAdapter(new SystemBattleMsgItem(getThis(),messagBean.getLeague_invitations()));
        }


    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        EventBus.getDefault().post(new SystemBattleMsgEvent());
        refreshLayout.endRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
