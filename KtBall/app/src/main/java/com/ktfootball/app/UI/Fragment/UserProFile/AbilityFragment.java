package com.ktfootball.app.UI.Fragment.UserProFile;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.App;
import com.kt.ktball.adapter.MyDVAdapter;
import com.kt.ktball.entity.StrataExternum;
import com.kt.ktball.entity.UserMsg;
import com.kt.ktball.entity.Videos;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.MyDVActivity;
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.ktfootball.app.UI.Activity.VideoDetailsActivity;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leo on 16/10/14.
 */

public class AbilityFragment extends BaseFragment {
    private TextView mTv_jinqiu, mTv_chuandang, mTv_Kt, mTv_All_jingqiu, mTv_all_chuangdang;
    private ImageView mImage_up_1, mImage_up_2, mImage_up_3, mImage_up_4, mImage_up_5, mImage_up_6, mImage_up_7, mImage_up_8, mImage_up_9, mImage_up_10;
    private ImageView mImage_down_1, mImage_down_2, mImage_down_3, mImage_down_4, mImage_down_5, mImage_down_6, mImage_down_7, mImage_down_8, mImage_down_9, mImage_down_10;
    private ImageView mImage_qiu1, mImage_qiu2, mImage_qiu3, mImage_qiu4, mImage_qiu5, mImage_qiu6, mImage_qiu7, mImage_qiu8, mImage_qiu9, mImage_qiu10;
    private TextView mTv_up1, mTv_up2, mTv_up3, mTv_up4, mTv_up5, mTv_up6, mTv_up7, mTv_up8, mTv_up9, mTv_up10;
    private TextView mTv_down1, mTv_down2, mTv_down3, mTv_down4, mTv_down5, mTv_down6, mTv_down7, mTv_down8, mTv_down9, mTv_down10;
    private List<TextView> mTvTopList = new ArrayList<>();
    private List<TextView> mTv_DownList = new ArrayList<>();
    private List<ImageView> mImageTop = new ArrayList<>();
    private List<ImageView> mImagedown = new ArrayList<>();
    private List<ImageView> mImageQiu = new ArrayList<>();
    private int cont = 30;
    private UserMsg mUserMsg;
    private LinearLayout mEmpty;
    private LinearLayout mMain;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_ability);
        mTv_all_chuangdang = getViewById(R.id.tv_all_chuangdang);
        mTv_All_jingqiu = getViewById(R.id.tv_zongjinqiu);
        mTv_Kt = getViewById(R.id.tv_kt);
        mTv_chuandang = getViewById(R.id.tv_chuandang);
        mTv_jinqiu = getViewById(R.id.tv_jinqiu);
        mImage_up_1 = getViewById(R.id.image_top_1);
        mImage_up_2 = getViewById(R.id.image_top_2);
        mImage_up_3 = getViewById(R.id.image_top_3);
        mImage_up_4 = getViewById(R.id.image_top_4);
        mImage_up_5 = getViewById(R.id.image_top_5);
        mImage_up_6 = getViewById(R.id.image_top_6);
        mImage_up_7 = getViewById(R.id.image_top_7);
        mImage_up_8 = getViewById(R.id.image_top_8);
        mImage_up_9 = getViewById(R.id.image_top_9);
        mImage_up_10 = getViewById(R.id.image_top_10);
        mImage_down_1 = getViewById(R.id.image_down_1);
        mImage_down_2 = getViewById(R.id.image_down_2);
        mImage_down_3 = getViewById(R.id.image_down_3);
        mImage_down_4 = getViewById(R.id.image_down_4);
        mImage_down_5 = getViewById(R.id.image_down_5);
        mImage_down_6 = getViewById(R.id.image_down_6);
        mImage_down_7 = getViewById(R.id.image_down_7);
        mImage_down_8 = getViewById(R.id.image_down_8);
        mImage_down_9 = getViewById(R.id.image_down_9);
        mImage_down_10 = getViewById(R.id.image_down_10);
        mImage_qiu1 = getViewById(R.id.image_qiu1);
        mImage_qiu2 = getViewById(R.id.image_qiu2);
        mImage_qiu3 = getViewById(R.id.image_qiu3);
        mImage_qiu4 = getViewById(R.id.image_qiu4);
        mImage_qiu5 = getViewById(R.id.image_qiu5);
        mImage_qiu6 = getViewById(R.id.image_qiu6);
        mImage_qiu7 = getViewById(R.id.image_qiu7);
        mImage_qiu8 = getViewById(R.id.image_qiu8);
        mImage_qiu9 = getViewById(R.id.image_qiu9);
        mImage_qiu10 = getViewById(R.id.image_qiu10);
        mTv_down1 = getViewById(R.id.tv_down_1);
        mTv_down2 = getViewById(R.id.tv_down_2);
        mTv_down3 = getViewById(R.id.tv_down_3);
        mTv_down4 = getViewById(R.id.tv_down_4);
        mTv_down5 = getViewById(R.id.tv_down_5);
        mTv_down6 = getViewById(R.id.tv_down_6);
        mTv_down7 = getViewById(R.id.tv_down_7);
        mTv_down8 = getViewById(R.id.tv_down_8);
        mTv_down9 = getViewById(R.id.tv_down_9);
        mTv_down10 = getViewById(R.id.tv_down_10);
        mTv_up1 = getViewById(R.id.tv_up_1);
        mTv_up2 = getViewById(R.id.tv_up_2);
        mTv_up3 = getViewById(R.id.tv_up_3);
        mTv_up4 = getViewById(R.id.tv_up_4);
        mTv_up5 = getViewById(R.id.tv_up_5);
        mTv_up6 = getViewById(R.id.tv_up_6);
        mTv_up7 = getViewById(R.id.tv_up_7);
        mTv_up8 = getViewById(R.id.tv_up_8);
        mTv_up9 = getViewById(R.id.tv_up_9);
        mTv_up10 = getViewById(R.id.tv_up_10);
        mTvTopList.add(mTv_up1);
        mTvTopList.add(mTv_up2);
        mTvTopList.add(mTv_up3);
        mTvTopList.add(mTv_up4);
        mTvTopList.add(mTv_up5);
        mTvTopList.add(mTv_up6);
        mTvTopList.add(mTv_up7);
        mTvTopList.add(mTv_up8);
        mTvTopList.add(mTv_up9);
        mTvTopList.add(mTv_up10);
        mTv_DownList.add(mTv_down1);
        mTv_DownList.add(mTv_down2);
        mTv_DownList.add(mTv_down3);
        mTv_DownList.add(mTv_down4);
        mTv_DownList.add(mTv_down5);
        mTv_DownList.add(mTv_down6);
        mTv_DownList.add(mTv_down7);
        mTv_DownList.add(mTv_down8);
        mTv_DownList.add(mTv_down9);
        mTv_DownList.add(mTv_down10);
        mImagedown.add(mImage_down_1);
        mImagedown.add(mImage_down_2);
        mImagedown.add(mImage_down_3);
        mImagedown.add(mImage_down_4);
        mImagedown.add(mImage_down_5);
        mImagedown.add(mImage_down_6);
        mImagedown.add(mImage_down_7);
        mImagedown.add(mImage_down_8);
        mImagedown.add(mImage_down_9);
        mImagedown.add(mImage_down_10);
        mImageTop.add(mImage_up_1);
        mImageTop.add(mImage_up_2);
        mImageTop.add(mImage_up_3);
        mImageTop.add(mImage_up_4);
        mImageTop.add(mImage_up_5);
        mImageTop.add(mImage_up_6);
        mImageTop.add(mImage_up_7);
        mImageTop.add(mImage_up_8);
        mImageTop.add(mImage_up_9);
        mImageTop.add(mImage_up_10);
        mImageQiu.add(mImage_qiu1);
        mImageQiu.add(mImage_qiu2);
        mImageQiu.add(mImage_qiu3);
        mImageQiu.add(mImage_qiu4);
        mImageQiu.add(mImage_qiu5);
        mImageQiu.add(mImage_qiu6);
        mImageQiu.add(mImage_qiu7);
        mImageQiu.add(mImage_qiu8);
        mImageQiu.add(mImage_qiu9);
        mImageQiu.add(mImage_qiu10);
        mEmpty = getViewById(R.id.empty);
        mMain = getViewById(R.id.linear_main);
        mUserMsg = App.getUserMsg();


    }

    @Override
    protected void setListener() {


    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (mUserMsg != null) {
            mTv_All_jingqiu.setText(mUserMsg.getTotal_goals() + "");
            mTv_all_chuangdang.setText(mUserMsg.getTotal_pannas() + "");
            mTv_Kt.setText(mUserMsg.getKt() + "");
            mTv_chuandang.setText(mUserMsg.getPannas() + "");
            mTv_jinqiu.setText(mUserMsg.getGoals() + "");
            checkView();
            if (mUserMsg.getLast10_goals_list() == null || mUserMsg.getLast10_goals_list().size() == 0) {
                mEmpty.setVisibility(View.VISIBLE);
                mMain.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void initHandler(Message msg) {


    }


    private void checkView() {

        if (mUserMsg.getLast10_goals_list().size()>0) {
            final Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                int i = 0;

                @Override
                public void run() {
                    getActivity().runOnUiThread(new TimerTask() {
                        @Override
                        public void run() {
                            mImageQiu.get(i).setVisibility(View.VISIBLE);
                            int user = mUserMsg.getLast10_goals_list().get(i);
                            int other = mUserMsg.getLast10_pannas_list().get(i);
                            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mImageTop.get(i).getLayoutParams();
                            linearParams.height = cont * user;
                            mImageTop.get(i).setLayoutParams(linearParams);
                            mImageTop.get(i).setVisibility(View.VISIBLE);
                            Animation myAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
                            mImageTop.get(i).startAnimation(myAnimation);
                            LinearLayout.LayoutParams linearParams1 = (LinearLayout.LayoutParams) mImagedown.get(i).getLayoutParams();
                            linearParams1.height = cont * other;
                            mImagedown.get(i).setLayoutParams(linearParams1);
                            mImagedown.get(i).setVisibility(View.VISIBLE);
                            Animation myAnimation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.scale1);
                            mImagedown.get(i).startAnimation(myAnimation1);
                            mTvTopList.get(i).setText(user + "");
                            mTv_DownList.get(i).setText(other + "");
                            mTvTopList.get(i).setVisibility(View.VISIBLE);
                            mTv_DownList.get(i).setVisibility(View.VISIBLE);
                            i += 1;
                            if (i == mUserMsg.getLast10_goals_list().size()) {
                                timer.cancel();
                            }
                        }
                    });
                }
            }, 500, 500);
        }

        ObjectAnimator animator = ObjectAnimator.ofFloat(mImage_up_1, "scaleY", 100f, 130f);
        animator.setDuration(1000);
        animator.start();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            mImageQiu.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI <= mUserMsg.getLast10_game_video_id_list().size() - 1) {
                        Intent intent = new Intent(getThis(), VideoDetailsActivity.class);
                        intent.putExtra(Constants.EXTRA_VIDEOS_ID, mUserMsg.getLast10_game_video_id_list().get(finalI));
                        intent.putExtra(Constants.EXTRA_SCORES, mUserMsg.getLast10_goals_list().get(finalI) + " : " + mUserMsg.getLast10_pannas_list().get(finalI));
                        startActivity(intent);
                    } else {
                        showToast(getString(R.string.game_no_video));
                    }
                }
            });
        }

    }

}
