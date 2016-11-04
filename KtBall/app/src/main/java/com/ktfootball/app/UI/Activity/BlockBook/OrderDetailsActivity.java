package com.ktfootball.app.UI.Activity.BlockBook;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.LogUtils;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.BcOrderDetail;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.BcOrderDetailRequset;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

/**
 * Created by jy on 16/6/28.
 */
public class OrderDetailsActivity extends BaseToolBarActivity2 {

    private TextView status;
    private TextView ordernum;
    private TextView lianxiren;
    private TextView phonenum;
    private TextView peoplenum;
    private TextView dizhi;
    private TextView ftitle;
    private LinearLayout xingping;
    private TextView yuping;
    private String orderstatus;
    private String orderid;
    private TextView time;
    private LinearLayout yuping_ll;
    private LinearLayout xingping_ll;

    @Override
    protected void initToolBar() {
        setToolBarTitle(getString(R.string.order_detail));
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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        orderid = getIntent().getStringExtra("orderid");
        orderstatus = getIntent().getStringExtra("orderstatus");
        doBcOrderDetail(orderid);

        if(orderstatus.equals(getString(R.string.is_pay)) || orderstatus.equals(getString(R.string.wait_right)) || orderstatus.equals(getString(R.string.wait_ok))|| orderstatus.equals(getString(R.string.wait_pingjia))){
            yuping_ll.setVisibility(View.GONE);
            xingping_ll.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.bg_blockbook);
        addContentView(R.layout.layout_orderdetils);
        status = (TextView) findViewById(R.id.layout_orderdetils_order_status);
        time = (TextView) findViewById(R.id.layout_orderdetils_order_time);
        ordernum = (TextView) findViewById(R.id.layout_orderdetils_order_num);
        lianxiren = (TextView) findViewById(R.id.layout_orderdetils_lianxiren);
        phonenum = (TextView) findViewById(R.id.layout_orderdetils_phonenum);
        peoplenum = (TextView) findViewById(R.id.layout_orderdetils_peoplenum);
        dizhi = (TextView) findViewById(R.id.layout_orderdetils_dizhi);
        ftitle = (TextView) findViewById(R.id.layout_orderdetils_title);
        xingping = (LinearLayout) findViewById(R.id.layout_orderdetils_xingping);
        yuping = (TextView) findViewById(R.id.layout_orderdetils_yuping);
        yuping_ll = (LinearLayout) findViewById(R.id.layout_orderdetils_yuping_ll);
        xingping_ll = (LinearLayout) findViewById(R.id.layout_orderdetils_xingping_ll);
    }

    private void doBcOrderDetail(String orderid) {
        showLoadingDiaglog();
        BcOrderDetailRequset request = new BcOrderDetailRequset(Constants.BC_ORDER_DETAIL, RequestMethod.GET);
        request.add("bc_order_number", orderid);
        LogUtils.e(orderid);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<BcOrderDetail>() {
            @Override
            public void onSucceed(int what, Response<BcOrderDetail> response) {
                closeLoadingDialog();
                init(response.get());
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    private void init(BcOrderDetail bcOrderDetail){
        status.setText(getString(R.string.order_status)+"："+orderstatus);
        time.setText(bcOrderDetail.activity_time);
        ordernum.setText(getString(R.string.order_num)+"："+orderid);
        lianxiren.setText(bcOrderDetail.contact);
        phonenum.setText(bcOrderDetail.mobile);
        peoplenum.setText(bcOrderDetail.number_of_people);
        dizhi.setText(bcOrderDetail.address);
        String invoice_title = bcOrderDetail.invoice_title;
        if("".equals(invoice_title)){
            invoice_title = getString(R.string.none);
        }
        ftitle.setText(invoice_title);
        yuping.setText(bcOrderDetail.comment);
        if(!"".equals(bcOrderDetail.stars)){
            setStar(xingping,Integer.parseInt(bcOrderDetail.stars));
        }
    }

    void setStar(LinearLayout ll, int num) {
        for (int x = 0; x < 5; x++) {
            if (x < num) {
                ll.getChildAt(x).setBackgroundResource(
                        R.mipmap.rating_show);
            } else {
                ll.getChildAt(x).setBackgroundResource(R.mipmap.rating);
            }

        }
    }
}
