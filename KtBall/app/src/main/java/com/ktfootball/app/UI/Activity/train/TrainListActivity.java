package com.ktfootball.app.UI.Activity.train;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.OnRecyclerViewItemClickListener;
import com.frame.app.base.activity.BaseActivity;
import com.kt.ktball.App;
import com.ktfootball.app.Manager.BitmapManager;
import com.kt.ktball.myclass.MyCircleImageView;
import com.kt.ktball.utils.CommonUtil;
import com.ktfootball.app.Adapter.TrainListAdapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.AppCartoons;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.AppCartoonsRequest;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import butterknife.OnClick;

/**
 * Created by jy on 16/6/17.
 */
public class TrainListActivity extends BaseActivity {


    private DrawerLayout drawer;
    public Toolbar toolbar;
    public TextView title;
    public TextView tv_right;
    public MyCircleImageView header;
    public SwipeRefreshLayout swipe;
    public RecyclerView rv;
    private String tag = "";
    private TrainListAdapter adapter;
    private AppCartoons userAppCartoons;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        getToolbar().setOnMenuItemClickListener(onMenuItemClick);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                doGetAllTrainList(tag);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        doGetAllTrainList(tag);
    }

    @OnClick(R.id.layout_trainlist_daiqiu)
    public void daiqiu(View view){
        tag = "带球";
        doGetAllTrainList(tag);
        drawer.closeDrawer(GravityCompat.END);
    }

    @OnClick(R.id.layout_trainlist_kongqiu)
    public void kongqiu(View view){
        tag = "控球";
        doGetAllTrainList(tag);
        drawer.closeDrawer(GravityCompat.END);
    }

    @OnClick(R.id.layout_trainlist_all)
    public void all(View view){
        tag = "";
        doGetAllTrainList(tag);
        drawer.closeDrawer(GravityCompat.END);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_trainlist);
        drawer = (DrawerLayout) findViewById(R.id.layout_trainlist_drawer_layout);
        toolbar = (Toolbar) findViewById(com.chongwuzhiwu.frame.R.id.toolbar);
        title = (TextView) findViewById(com.chongwuzhiwu.frame.R.id.layout_title_tv);
        tv_right = (TextView) findViewById(com.chongwuzhiwu.frame.R.id.layout_title_icon);
        header = (MyCircleImageView) findViewById(R.id.layout_trainlist_header);
        swipe = (SwipeRefreshLayout) findViewById(R.id.layout_trainlist_sr);
        swipe.setProgressViewOffset(false, 0, CommonUtil.dip2px(getThis(), 24));
        rv = (RecyclerView) findViewById(R.id.layout_trainlist_rv);
        GridLayoutManager gm = new GridLayoutManager(getThis(),2);
        rv.setLayoutManager(gm);
        BitmapManager.getInstance().displayUserLogo(header, Constants.HOST+App.getUserLogin().avatar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setNavigationIcon(com.chongwuzhiwu.frame.R.drawable.icon_back);
        initToolBar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnNavigationClick(v);
            }
        });
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    private void doGetAllTrainList(String type){
        swipe.setRefreshing(true);
        AppCartoonsRequest request = new AppCartoonsRequest(Constants.APP_CARTOONS, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id + "");
        request.add("tag", type);
        CallServer.getRequestInstance().add(this, 0, request, httpListener, false, false);
    }

    private HttpListener httpListener = new HttpListener<AppCartoons>() {
        @Override
        public void onSucceed(int what, Response<AppCartoons> response) {
            userAppCartoons = response.get();
            swipe.setRefreshing(false);
            if (userAppCartoons.response.equals("success")) {
                setAdapter(userAppCartoons);
            } else {
                showToast(R.string.request_failed);
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            swipe.setRefreshing(false);
        }
    };

    private void setAdapter(final AppCartoons userAppCartoons) {
        adapter = new TrainListAdapter(rv,userAppCartoons.games);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position) {
                Intent intent = new Intent(getThis(), TrainDetailsActivity.class);
                intent.putExtra(Constants.APP_CARTOON_ID,userAppCartoons.games.get(position).id);
                intent.putExtra(Constants.SUB_NAME,userAppCartoons.games.get(position).sub_name);
                startActivity(intent);
            }
        });
    }

    private void initToolBar() {
        setToolBarTitle("添加课程");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.trainlist_menu, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_trainlist_right:
                    drawerLayout();
                    break;
            }
            return true;
        }
    };

    private void drawerLayout(){
        if(drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }else{
            drawer.openDrawer(GravityCompat.END);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    protected void setToolBarTitle(String titleStr) {
        title.setText(titleStr);
        title.setBackground(null);
    }

    protected void setRightImage(int red){
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("");
        tv_right.setBackgroundResource(red);
    }

    protected void setNavigationIcon(int resId) {
        toolbar.setNavigationIcon(resId);
    }

    protected void setNavigationIcon(Drawable icon) {
        toolbar.setNavigationIcon(icon);
    }

    protected void OnNavigationClick(View v){
        finish();
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }
}