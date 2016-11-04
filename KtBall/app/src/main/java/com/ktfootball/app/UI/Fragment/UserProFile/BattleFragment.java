package com.ktfootball.app.UI.Fragment.UserProFile;

import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.BattleBean;
import com.ktfootball.app.Event.BattleEvent;
import com.ktfootball.app.Event.BattleEventLoad;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.LoginActivity;
import com.ktfootball.app.UI.Fragment.BattleChildFragment;
import com.ktfootball.app.Utils.MD5;
import com.ktfootball.app.Views.NoScrollViewPager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.Subscribe;

/**
 * Created by leo on 16/10/14.
 */

public class BattleFragment extends BaseFragment implements View.OnClickListener {
    private NoScrollViewPager noscroolview;
    private TextView mTab1, mTab2, mTab3;
    private List<Fragment> mList = new ArrayList<>();
    private long userId;
    private List<BattleBean.VideosBean> mFitstList = new ArrayList<>();
    private List<BattleBean.VideosBean> mScondList = new ArrayList<>();
    private List<BattleBean.VideosBean> mThreeList = new ArrayList<>();
    private BattleChildFragment mFirstFragment;
    private BattleChildFragment mScondFragment;
    private BattleChildFragment mThreeFragment;
    private int mPage = 1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_battle);
        userId = PreferenceManager.getDefaultSharedPreferences(getActivity()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        noscroolview = getViewById(R.id.noscroolview);
        mTab1 = getViewById(R.id.tv_battle_tab1);
        mTab2 = getViewById(R.id.tv_battle_tab2);
        mTab3 = getViewById(R.id.tv_battle_tab3);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        noscroolview.setNoScroll(true);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add(mFirstFragment = new BattleChildFragment());
        mList.add(mScondFragment = new BattleChildFragment());
        mList.add(mThreeFragment = new BattleChildFragment());
        noscroolview.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        });
        noscroolview.setOffscreenPageLimit(2);
        getVideoList(mPage);

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.tv_battle_tab1:
                mTab1.setTextColor(getResources().getColor(R.color.gold));
                mTab2.setTextColor(0xff333333);
                mTab3.setTextColor(0xff333333);
                noscroolview.setCurrentItem(0);
                break;
            case R.id.tv_battle_tab2:
                mTab2.setTextColor(getResources().getColor(R.color.gold));
                mTab1.setTextColor(0xff333333);
                mTab3.setTextColor(0xff333333);
                noscroolview.setCurrentItem(1);
                break;
            case R.id.tv_battle_tab3:
                mTab3.setTextColor(getResources().getColor(R.color.gold));
                mTab2.setTextColor(0xff333333);
                mTab1.setTextColor(0xff333333);
                noscroolview.setCurrentItem(2);
                break;
        }

    }

    public void getVideoList(int page) {
        String url = Constants.HOST + "videos/my_videos?user_id=" + userId + "&page=" + page
                + "&authenticity_token=" + MD5.getToken(Constants.HOST + "videos/my_videos");
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        Gson gson = new Gson();
                        BattleBean battleBean = gson.fromJson(jsonObject.toString(), BattleBean.class);
                        if (mPage == 1) {
                            mFitstList.clear();
                            mScondList.clear();
                            mThreeList.clear();
                        }
                        for (int i = 0; i < battleBean.getVideos().size(); i++) {
                            switch (battleBean.getVideos().get(i).getGame_video_type()) {
                                case 0:
                                    mFitstList.add(battleBean.getVideos().get(i));
                                    break;
                                case 1:
                                    mScondList.add(battleBean.getVideos().get(i));
                                    break;
                                case 2:
                                    mThreeList.add(battleBean.getVideos().get(i));
                                    break;
                            }
                        }
                        if (mPage == 1) {
                            mFirstFragment.refreshList(mFitstList);
                            mScondFragment.refreshList(mScondList);
                            mThreeFragment.refreshList(mThreeList);
                        } else {
                            mFirstFragment.loadList(mFitstList);
                            mScondFragment.loadList(mScondList);
                            mThreeFragment.loadList(mThreeList);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(getThis()).addRequest(jsonRequest);
    }

    @Subscribe
    public void refush(BattleEvent event) {
        mPage = 1;
        getVideoList(mPage);

    }

    @Subscribe
    public void load(BattleEventLoad event) {
        mPage += 1;
        getVideoList(mPage);

    }


}
