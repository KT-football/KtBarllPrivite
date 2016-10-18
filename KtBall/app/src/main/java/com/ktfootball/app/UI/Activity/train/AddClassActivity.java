package com.ktfootball.app.UI.Activity.train;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.FileUtil;
import com.ktfootball.app.Entity.AppCartoon;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Fragment.Train.AddClassFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by leo on 16/10/18.
 */

public class AddClassActivity extends BaseActivity {
    @Bind(R.id.tv_chuji)
    TextView tv_chuji;
    @Bind(R.id.tv_zhongji)
    TextView tv_zhongji;
    @Bind(R.id.tv_gaoji)
    TextView tv_gaoji;
    @Bind(R.id.viewpager_add_class)
    ViewPager mViewpager;
    private List<Fragment> mList = new ArrayList<>();
    private AddClassFragment mAllAddClassFragment = new AddClassFragment();
    private AddClassFragment mDaiAddClassFragment = new AddClassFragment();
    private AddClassFragment mKongAddClassFragment = new AddClassFragment();


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_class);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = new Bundle();
        bundle.putString("tag","");
        mAllAddClassFragment.setArguments(bundle);
        Bundle bundle1 = new Bundle();
        bundle1.putString("tag","带球");
        mDaiAddClassFragment.setArguments(bundle1);
        Bundle bundle2 = new Bundle();
        bundle2.putString("tag","控球");
        mKongAddClassFragment.setArguments(bundle2);
        mList.add(mAllAddClassFragment);
        mList.add(mDaiAddClassFragment);
        mList.add(mKongAddClassFragment);
        mViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        mViewpager.setOffscreenPageLimit(2);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_chuji.setTextColor(0xffffffff);
                        tv_chuji.setBackgroundColor(getResourcesColor(R.color.gold));
                        tv_zhongji.setBackgroundColor(0xffffffff);
                        tv_zhongji.setTextColor(getResourcesColor(R.color.gold));
                        tv_gaoji.setBackgroundColor(0xffffffff);
                        tv_gaoji.setTextColor(getResourcesColor(R.color.gold));
                        break;
                    case 1:
                        tv_zhongji.setTextColor(0xffffffff);
                        tv_zhongji.setBackgroundColor(getResourcesColor(R.color.gold));
                        tv_chuji.setBackgroundColor(0xffffffff);
                        tv_chuji.setTextColor(getResourcesColor(R.color.gold));
                        tv_gaoji.setBackgroundColor(0xffffffff);
                        tv_gaoji.setTextColor(getResourcesColor(R.color.gold));
                        break;
                    case 2:
                        tv_gaoji.setTextColor(0xffffffff);
                        tv_gaoji.setBackgroundColor(getResourcesColor(R.color.gold));
                        tv_zhongji.setBackgroundColor(0xffffffff);
                        tv_zhongji.setTextColor(getResourcesColor(R.color.gold));
                        tv_chuji.setBackgroundColor(0xffffffff);
                        tv_chuji.setTextColor(getResourcesColor(R.color.gold));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick(R.id.tv_chuji)
    public void checkChuji() {
        mViewpager.setCurrentItem(0);
    }

    @OnClick(R.id.tv_zhongji)
    public void checkZhongji() {
        mViewpager.setCurrentItem(1);

    }

    @OnClick(R.id.tv_gaoji)
    public void checkGaoji() {
        mViewpager.setCurrentItem(2);


    }


    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }
}
