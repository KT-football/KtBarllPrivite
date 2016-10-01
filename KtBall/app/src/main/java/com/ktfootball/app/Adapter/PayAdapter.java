package com.ktfootball.app.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frame.app.base.Adapter.BaseRecyclerViewAdapter;
import com.frame.app.base.Adapter.BaseViewHolder;
import com.ktfootball.app.Entity.UserBcOrders;
import com.ktfootball.app.R;

import java.util.List;

/**
 * Created by jy on 16/6/28.
 */
public class PayAdapter extends BaseRecyclerViewAdapter<UserBcOrders.BcOrders, PayAdapter.PayViewHolder> {

    public PayAdapter(RecyclerView recyclerView, List<UserBcOrders.BcOrders> bcOrderses) {
        super(recyclerView, bcOrderses);
    }

    @Override
    protected void bindView(PayViewHolder holder, int position, UserBcOrders.BcOrders model) {
        holder.dizhi.setText(model.address);
        holder.time.setText("下单时间：" + model.pay_time);
        holder.pay.setText("￥ " + model.amount);
        holder.state.setText(model.status);
    }

    @Override
    public PayViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PayViewHolder payViewHolder = new PayViewHolder(getRecyclerView(), getLayoutInflater(getContext(), R.layout.layout_pay, parent, false), this);
        return payViewHolder;
    }

    public class PayViewHolder extends BaseViewHolder {

        public TextView dizhi;
        public TextView pay;
        public TextView state;
        public TextView time;

        public PayViewHolder(RecyclerView recyclerView, View rootView, BaseRecyclerViewAdapter mBaseRecyclerViewAdapter) {
            super(recyclerView, rootView, mBaseRecyclerViewAdapter);
            dizhi = (TextView) rootView.findViewById(R.id.layout_pay_dizhi);
            pay = (TextView) rootView.findViewById(R.id.layout_pay_pay);
            state = (TextView) rootView.findViewById(R.id.layout_pay_state);
            time = (TextView) rootView.findViewById(R.id.layout_pay_time);
        }
    }
}
