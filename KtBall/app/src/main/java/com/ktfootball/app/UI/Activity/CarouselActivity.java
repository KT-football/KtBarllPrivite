package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.SharedPreferencesUtils;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Fragment.Carouse1Fragment;
import com.ktfootball.app.UI.Fragment.Carouse2Fragment;
import com.ktfootball.app.UI.Fragment.Carouse3Fragment;
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
    private List<Fragment> mList = new ArrayList<>();
    private View mView;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_carouse);
    }

    @Override
    protected void setListener() {


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add(new Carouse1Fragment());
        mList.add(new Carouse2Fragment());
        Carouse3Fragment carouse3Fragment = new Carouse3Fragment();
        carouse3Fragment.mDiffuseView.start();
        mList.add(carouse3Fragment);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }


        });
        mViewPager.setOffscreenPageLimit(2);


    }
}
