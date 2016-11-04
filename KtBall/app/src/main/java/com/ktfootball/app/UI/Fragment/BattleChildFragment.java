package com.ktfootball.app.UI.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.frame.app.base.fragment.BaseFragment;
import com.ktfootball.app.Adapter.BattleChildAdapter;
import com.ktfootball.app.Entity.BattleBean;
import com.ktfootball.app.Event.BattleEvent;
import com.ktfootball.app.Event.BattleEventLoad;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.MyBGARefreshViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import de.greenrobot.event.EventBus;

/**
 * Created by leo on 16/10/14.
 */

public class BattleChildFragment extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {
    List<BattleBean.VideosBean> mList = new ArrayList<>();
    private BGARefreshLayout mRefreshLayout;
    private RecyclerView layout_recyclerview_rv;
    private BattleChildAdapter mBattleChildAdapter;
    private boolean isEmpte = false;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_battle_child);
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


    /**
     * 加载数据
     *
     * @param list
     */
    public void loadList(List<BattleBean.VideosBean> list) {
        mList = list;
        mBattleChildAdapter.addData(list);
        mRefreshLayout.endLoadingMore();
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        EventBus.getDefault().post(new BattleEvent());

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        EventBus.getDefault().post(new BattleEventLoad());
        return true;
    }
}
