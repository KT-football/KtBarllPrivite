package com.ktfootball.app.UI.Fragment.BlockBook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.base.fragment.BaseRecyclerViewFragment;
import com.ktfootball.app.Adapter.PayAdapter;
import com.ktfootball.app.Base.PayFragment;
import com.ktfootball.app.Entity.UserBcOrders;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.BlockBook.OrderDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jy on 16/6/24.
 * 待评价
 */
public class EvaluatedFragment extends PayFragment {

    public List<UserBcOrders.BcOrders> list = new ArrayList<>();
    private PayAdapter trainAdapter;
    private List<UserBcOrders.BcOrders> currentList;

    @Override
    protected void setListener() {
        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doLoad();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initRecyclerView(list);
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getThis());
        setLayoutManager(mLayoutManager);

        getRecyclerView().setPadding(dp2px(15),dp2px(15),dp2px(15),0);
    }

    public void initRecyclerView(List<UserBcOrders.BcOrders> list) {
        trainAdapter = new PayAdapter(getRecyclerView(),list);
        setAdapter(trainAdapter);
        trainAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent intent = new Intent(getThis(),OrderDetailsActivity.class);
                intent.putExtra("orderid",currentList.get(position).bc_order_number);
                intent.putExtra("orderstatus",currentList.get(position).status);
                startActivity(intent);
            }
        });
    }

    public void refershListData(List<UserBcOrders.BcOrders> list){
        currentList = new ArrayList<>();
        for(UserBcOrders.BcOrders bcOrders : list){
            if(bcOrders.status.equals(getString(R.string.wait_pingjia))){
                currentList.add(bcOrders);
            }
        }
        if(trainAdapter != null){
            trainAdapter.setDatas(currentList);
        }
        setRefreshing(false);
    }
}
