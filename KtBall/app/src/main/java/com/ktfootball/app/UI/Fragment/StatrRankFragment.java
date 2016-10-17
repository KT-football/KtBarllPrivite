package com.ktfootball.app.UI.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.frame.app.base.fragment.BaseFragment;
import com.kt.ktball.App;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Event.RankEvent;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Fragment.Rank.StartFragment;
import com.ktfootball.app.UI.Fragment.Rank.SuperStarFragment;
import com.ktfootball.app.Views.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;

/**
 * Created by leo on 16/10/15.
 */
public class StatrRankFragment extends BaseFragment {
    private ImageView mHead;
    private TextView mMine;
    private TextView mTab1, mTab2;
    private NoScrollViewPager mViewpager;
    private List<Fragment> mList = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_start_rank);
        mHead = getViewById(R.id.image_head);
        mMine = getViewById(R.id.tv_mine);
        mTab1 = getViewById(R.id.tv_tab1);
        mTab2 = getViewById(R.id.tv_tab2);
        mViewpager = getViewById(R.id.viewpager);
        mViewpager.setNoScroll(true);
        mList.add(new StartFragment());
        mList.add(new SuperStarFragment());

    }

    @Override
    protected void setListener() {
        mTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(0);
                mTab1.setBackgroundColor(getResources().getColor(R.color.gold));
                mTab1.setTextColor(0xffffffff);
                mTab2.setTextColor(getResources().getColor(R.color.gold));
                mTab2.setBackgroundColor(0xffffffff);
            }
        });
        mTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(1);
                mTab2.setBackgroundColor(getResources().getColor(R.color.gold));
                mTab2.setTextColor(0xffffffff);
                mTab1.setTextColor(getResources().getColor(R.color.gold));
                mTab1.setBackgroundColor(0xffffffff);
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Glide.with(getThis()).load(Constants.HOST+App.getUserLogin().avatar).transform(new GlideCircleTransform(getThis())).error(R.drawable.result_btnkt).into(mHead);
        mViewpager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Subscribe
    public void setMine(RankEvent event){
        mMine.setText(event.getMine());
    }

}
