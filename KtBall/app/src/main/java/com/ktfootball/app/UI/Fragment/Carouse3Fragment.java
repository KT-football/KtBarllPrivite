package com.ktfootball.app.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;

import com.frame.app.base.fragment.BaseFragment;
import com.frame.app.utils.SharedPreferencesUtils;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.LoginActivity;
import com.ktfootball.app.Views.DiffuseView;

/**
 * Created by leo on 16/10/22.
 */

public class Carouse3Fragment extends BaseFragment {
    public DiffuseView mDiffuseView;
    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_catousel3);
        mDiffuseView = getViewById(R.id.diffuseView);
    }

    @Override
    protected void setListener() {
        mDiffuseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesUtils.saveBoolean(getThis(), Constants.IS_FIRST, true);
                Intent intent = new Intent();
                intent.setClass(getThis(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
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
}
