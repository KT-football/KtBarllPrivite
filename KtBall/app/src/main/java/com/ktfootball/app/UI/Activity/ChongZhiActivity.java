package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.kt.ktball.App;
import com.kt.ktball.entity.UserLogin;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.ChongZhiListBean;
import com.ktfootball.app.R;
import com.ktfootball.app.Utils.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/19.
 */

public class ChongZhiActivity extends BaseActivity {
    private UserLogin userLogin = App.getUserLogin();
    @Bind(R.id.tv_name)
    TextView mName;
    @Bind(R.id.tv_all_money)
    TextView bi;
    @Bind(R.id.tv_monty)
    TextView mMonty;
    @Bind(R.id.girdview)
    GridView mGridView;
    ChongZhiListBean.RechargeListBean mRechargeListBean;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_chongzhi);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getData();
        mName.setText(userLogin.nickname);
        bi.setText(userLogin.ktb + "");
    }

    public void doBack(View view) {
        finish();
    }

    @OnClick(R.id.btn1)
    public void toPay() {
        if (mRechargeListBean != null) {
            Intent intent = new Intent(getThis(), ChongZhiDetailActivity.class);
            intent.putExtra("info", mRechargeListBean);
            startActivity(intent);
        } else {
            showToast(getString(R.string.please_choose_money));
        }

    }

    private void getData() {
        showLoadingDiaglog();
        String url = Constants.RECHARGE_LIST + "?authenticity_token="+ MD5.getToken(Constants.RECHARGE_LIST);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        Gson gson = new Gson();
                        ChongZhiListBean chongZhiListBean = gson.fromJson(jsonObject.toString(), ChongZhiListBean.class);
                        if (chongZhiListBean.getResponse().equals("success")) {
                            mGridView.setAdapter(new ChongzhiAdapter(chongZhiListBean.getRecharge_list()));
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(getThis()).addRequest(jsonRequest);

    }

    public class ChongzhiAdapter extends BaseAdapter {
        List<ChongZhiListBean.RechargeListBean> mList;
        int isSelect = -1;

        public ChongzhiAdapter(List<ChongZhiListBean.RechargeListBean> recharge_list) {
            mList = recharge_list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHoder viewHoder;
            if (convertView == null) {
                viewHoder = new ViewHoder();
                convertView = LayoutInflater.from(getThis()).inflate(R.layout.item_chongzhi, null);
                viewHoder.mBg = (LinearLayout) convertView.findViewById(R.id.linear_bg);
                viewHoder.mBi = (TextView) convertView.findViewById(R.id.tv_cont);
                viewHoder.mMoney = (TextView) convertView.findViewById(R.id.tv_monry);
                convertView.setTag(viewHoder);
            } else {
                viewHoder = (ViewHoder) convertView.getTag();
            }
            viewHoder.mBi.setText(mList.get(position).getKtb() + getString(R.string.kt_monty));
            viewHoder.mMoney.setText("￥" + mList.get(position).getPrice());
            if (isSelect == position) {
                viewHoder.mBi.setTextColor(getResourcesColor(R.color.white));
                viewHoder.mMoney.setTextColor(getResourcesColor(R.color.white));
                viewHoder.mBg.setBackgroundResource(R.mipmap.chongzhi_selected);
            } else {
                viewHoder.mBi.setTextColor(getResourcesColor(R.color.gold));
                viewHoder.mMoney.setTextColor(getResourcesColor(R.color.gold));
                viewHoder.mBg.setBackgroundResource(R.mipmap.chongzhi_nomal);
            }
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMonty.setText(viewHoder.mBi.getText().toString());
                    mRechargeListBean = mList.get(position);
                    isSelect = position;
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHoder {
            TextView mBi, mMoney;
            LinearLayout mBg;
        }
    }
}
