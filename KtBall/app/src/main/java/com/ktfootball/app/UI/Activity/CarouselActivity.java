package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.SharedPreferencesUtils;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.Views.DiffuseView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by leo on 16/10/13.
 */
public class CarouselActivity extends BaseActivity {
    @Bind(R.id.viewpager_carousel)
    ViewPager mViewPager;
    private List<View> mList = new ArrayList<>();
    private View mView;
    private DiffuseView mDiffuseView;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_carouse);
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
                finish();
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add(getLayoutInflater().inflate(R.layout.layout_catousel1, null));
        mList.add(getLayoutInflater().inflate(R.layout.layout_catousel2, null));
        mView = getLayoutInflater().inflate(R.layout.layout_catousel3, null);
        mList.add(mView);
        mDiffuseView = (DiffuseView) mView.findViewById(R.id.diffuseView);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mList.get(position));
                return mList.get(position);
            }
        });
        mViewPager.setOffscreenPageLimit(2);


    }
}
