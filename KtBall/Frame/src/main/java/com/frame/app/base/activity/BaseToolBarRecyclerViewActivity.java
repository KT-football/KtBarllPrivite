package com.frame.app.base.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chongwuzhiwu.frame.R;
import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;

public abstract class BaseToolBarRecyclerViewActivity extends BaseToolBarActivity {

	public RecyclerView mRecyclerView;
	public SwipeRefreshLayout refresh;

	@Override
	protected void initView(Bundle savedInstanceState) {
		setContentView(R.layout.layout_recyclerview_nopull);
		mRecyclerView = (RecyclerView) findViewById(R.id.layout_recyclerview_rv);
		refresh = (SwipeRefreshLayout) findViewById(R.id.layout_recyclerview_sr);
		LinearLayoutManager mLayoutManager = new LinearLayoutManager(getThis());
		mRecyclerView.setLayoutManager(mLayoutManager);
	}

	/**
	 * 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
	 */
	protected void setHasFixedSize(boolean isFixed) {
		mRecyclerView.setHasFixedSize(isFixed);
	}

	protected void addItemDecoration(RecyclerView.ItemDecoration mItemDecoration) {
		mRecyclerView.addItemDecoration(mItemDecoration);
	}

	protected void setItemAnimator(RecyclerView.ItemAnimator mItemAnimator){
		mRecyclerView.setItemAnimator(mItemAnimator);
	}

	protected void initRecyclerView(BaseRecyclerViewAdapter adapter) {
		mRecyclerView.setAdapter(adapter);
	}

	protected RecyclerView getRecyclerView() {
		return mRecyclerView;
	}
	protected SwipeRefreshLayout getSwipeRefreshLayout() {
		return refresh;
	}

	protected void Refreshing(boolean isRefreshing){
		refresh.setRefreshing(isRefreshing);
	}
	protected void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener){
		refresh.setOnRefreshListener(listener);
	}

	protected void addOnScrollListener(RecyclerView.OnScrollListener listener){
		mRecyclerView.addOnScrollListener(listener);
	}
}
