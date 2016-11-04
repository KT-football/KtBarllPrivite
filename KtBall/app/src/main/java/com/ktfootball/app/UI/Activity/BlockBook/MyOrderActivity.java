package com.ktfootball.app.UI.Activity.BlockBook;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.LogUtils;
import com.kt.ktball.App;
import com.ktfootball.app.Adapter.Find_tab_Adapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GetCityPrice;
import com.ktfootball.app.Entity.UserBcOrders;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.UserBcOrdersRequest;
import com.ktfootball.app.UI.Fragment.BlockBook.CompletedFragment;
import com.ktfootball.app.UI.Fragment.BlockBook.EvaluatedFragment;
import com.ktfootball.app.UI.Fragment.BlockBook.PaymentFramgment;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.util.ArrayList;

/**
 * Created by jy on 16/6/24.
 */
public class MyOrderActivity extends BaseToolBarActivity2 {

    private TabLayout tab_FindFragment_title;
    private ViewPager vp_FindFragment_pager;
    private ArrayList<String> list_title;
    private ArrayList<BaseFragment> list_fragment;
    private Find_tab_Adapter fAdapter;
    private PaymentFramgment paymentFramgment;
    private EvaluatedFragment evaluatedFragment;
    private CompletedFragment completedFragment;

    @Override
    protected void initToolBar() {
        setToolBarTitle(getString(R.string.my_order));
        setRightText(getString(R.string.my_get_order));
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        getTv_right().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), WantToMakeActivity.class);
                startActivityForResult(intent, Constants.TO_WANTTOMAKE_AVTIVITY);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_myorder);
        setBackgroundResource(R.drawable.bg_blockbook);
        tab_FindFragment_title = (TabLayout) findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (ViewPager) findViewById(R.id.vp_FindFragment_pager);

        //初始化各fragment
        paymentFramgment = new PaymentFramgment();
        evaluatedFragment = new EvaluatedFragment();
        completedFragment = new CompletedFragment();

        //将fragment装进列表中
        list_fragment = new ArrayList<>();
        list_fragment.add(paymentFramgment);
        list_fragment.add(evaluatedFragment);
        list_fragment.add(completedFragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<>();
        list_title.add(getString(R.string.is_pay));
        list_title.add(getString(R.string.wait_pingjia));
        list_title.add(getString(R.string.is_over));

        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));

        fAdapter = new Find_tab_Adapter(getThis().getSupportFragmentManager(), list_fragment, list_title);

        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
        vp_FindFragment_pager.setOffscreenPageLimit(3);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
        //tab_FindFragment_title.set
    }

    public void doUserBcOrders() {
        UserBcOrdersRequest request = new UserBcOrdersRequest(Constants.USER_BC_ORDERS, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id+"");
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<UserBcOrders>() {
            @Override
            public void onSucceed(int what, Response<UserBcOrders> response) {
                paymentFramgment.refershListData(response.get().bc_orders);
                evaluatedFragment.refershListData(response.get().bc_orders);
                completedFragment.refershListData(response.get().bc_orders);
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, false, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.TO_WANTTOMAKE_AVTIVITY && resultCode == Constants.PAY_SUCCESS) {

        }
    }
}
