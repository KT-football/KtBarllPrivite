package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.UI.Activity.Main.SuperStarActivity;
import com.ktfootball.app.UI.Activity.train.TrainActivity;
import com.kt.ktball.views.IRoundBallViewScrollListener;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Fragment.GameMatchFragment;
import com.ktfootball.app.UI.Fragment.StatrRankFragment;
import com.ktfootball.app.UI.Fragment.TrainFragment;
import com.ktfootball.app.UI.Fragment.UserProfilesFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends BaseActivity implements IRoundBallViewScrollListener {

    @Bind(R.id.main_viewpager)
    ViewPager mViewPager;
    List<Fragment> mList = new ArrayList<>();
    @Bind(R.id.image_tab1)
    ImageView mImage_tab1;
    @Bind(R.id.image_tab2)
    ImageView mImage_tab2;
    @Bind(R.id.image_tab3)
    ImageView mImage_tab3;
    @Bind(R.id.image_tab4)
    ImageView mImage_tab4;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_ball);
        ShareSDK.initSDK(this);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mList.add(new UserProfilesFragment());
        mList.add(new GameMatchFragment());
        mList.add(new TrainFragment());
        mList.add(new StatrRankFragment());
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
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                checkTabs(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(3);
    }

    /**
     * 切换tab
     *
     * @param position
     */
    private void checkTabs(int position) {
        switch (position) {
            case 0:
                mImage_tab1.setImageResource(R.mipmap.bottom_1);
                mImage_tab2.setImageResource(R.mipmap.bottom_no_2);
                mImage_tab3.setImageResource(R.mipmap.bottom_no_3);
                mImage_tab4.setImageResource(R.mipmap.bottom_no_4);
                break;
            case 1:
                mImage_tab2.setImageResource(R.mipmap.bottom_2);
                mImage_tab1.setImageResource(R.mipmap.bottom_no_1);
                mImage_tab3.setImageResource(R.mipmap.bottom_no_3);
                mImage_tab4.setImageResource(R.mipmap.bottom_no_4);
                break;
            case 2:
                mImage_tab3.setImageResource(R.mipmap.bottom_3);
                mImage_tab1.setImageResource(R.mipmap.bottom_no_1);
                mImage_tab2.setImageResource(R.mipmap.bottom_no_2);
                mImage_tab4.setImageResource(R.mipmap.bottom_no_4);
                break;
            case 3:
                mImage_tab4.setImageResource(R.mipmap.bottom_4);
                mImage_tab1.setImageResource(R.mipmap.bottom_no_1);
                mImage_tab3.setImageResource(R.mipmap.bottom_no_3);
                mImage_tab2.setImageResource(R.mipmap.bottom_no_2);
                break;
        }
    }

    @Override
    public void startActivity(int code) {
        Intent intent = null;
        switch (code) {
            case 0:
                intent = new Intent(this, GameMatchActivity.class);
                break;
            case 1:
                intent = new Intent(this, SuperStarActivity.class);
                break;
            case 2:
                long userId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
                intent = new Intent(this, UserProfiles.class);
                intent.putExtra(UserProfiles.USERID, userId);
                break;
            case 3:
                intent = new Intent(getThis(), TrainActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    @OnClick(R.id.linear_mineKt)
    public void tabFirst() {
        mViewPager.setCurrentItem(0);
    }

    @OnClick(R.id.linear_bisai)
    public void tabSecond() {
        mViewPager.setCurrentItem(1);
    }

    @OnClick(R.id.linear_xunlian)
    public void tabThird() {
        mViewPager.setCurrentItem(2);
    }

    @OnClick(R.id.linear_start)
    public void tabFour() {
        mViewPager.setCurrentItem(3);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }
    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "确定要退出程序嘛~", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
