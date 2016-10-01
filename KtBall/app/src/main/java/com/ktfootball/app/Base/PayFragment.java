package com.ktfootball.app.Base;

import android.os.Bundle;

import com.frame.app.base.fragment.BaseRecyclerViewFragment;
import com.ktfootball.app.UI.Activity.BlockBook.MyOrderActivity;

/**
 * Created by jy on 16/6/28.
 */
public abstract class PayFragment extends BaseRecyclerViewFragment {

    protected void doLoad(){
        ((MyOrderActivity)getThis()).doUserBcOrders();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }
}
