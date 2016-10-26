package com.ktfootball.app.UI.Activity.train;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.AppCartoon;
import com.ktfootball.app.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by huangbo on 2016/10/5.
 */
public class BigClassHelp extends BaseToolBarActivity2 {

    private AppCartoon mBigRoomIdiviBean;
    @Bind(R.id.imageView)
    ImageView mImageView;
    @Bind(R.id.tv_yaol)
    TextView mTv_yaol;
    @Bind(R.id.tv_shuom)
    TextView mTv_shuom;
    @Bind(R.id.tv_title)
    TextView mTv_title;

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_big_class);
    }

    @Override
    protected void initToolBar() {

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
        mBigRoomIdiviBean = (AppCartoon) getIntent().getSerializableExtra("info");
        getToolbar().setVisibility(View.GONE);
        mTv_title.setText(mBigRoomIdiviBean.name);
        mTv_shuom.setText(mBigRoomIdiviBean.description);
        mTv_yaol.setText(mBigRoomIdiviBean.intro);
        Glide.with(getThis()).load(Constants.HEAD_HOST + mBigRoomIdiviBean.avatar).into(mImageView);

    }

    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }
}
