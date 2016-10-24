package com.ktfootball.app.UI.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.frame.app.base.fragment.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.App;
import com.kt.ktball.entity.UserMsg;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Event.Rank1Event;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.JudgeSeleteActivity;
import com.ktfootball.app.UI.Activity.LoginActivity;
import com.ktfootball.app.UI.Activity.MyBaiDuFootPrintActivity;
import com.ktfootball.app.UI.Activity.MyMsgActivity;
import com.ktfootball.app.UI.Activity.MyQRCodeActivity;
import com.ktfootball.app.UI.Activity.UserProfiles;
import com.ktfootball.app.UI.Fragment.UserProFile.AbilityFragment;
import com.ktfootball.app.UI.Fragment.UserProFile.BattleFragment;
import com.ktfootball.app.UI.Fragment.UserProFile.CorpsFragment;
import com.ktfootball.app.UI.Fragment.UserProFile.ScreeningsFragment;
import com.ktfootball.app.Utils.Util;
import com.ktfootball.app.Views.CircleProgressView;

import org.json.JSONObject;

import de.greenrobot.event.EventBus;

/**
 * Created by leo on 16/10/13.
 */

public class UserProfilesFragment extends BaseFragment implements View.OnClickListener {

    TextView mTab1;
    TextView mTab2;
    TextView mTab3;
    TextView mTab4;
    CircleProgressView mProgressView;
    ImageView mHead;
    TextView mTv_LV;
    private BattleFragment mBattleFragment;
    private ScreeningsFragment mScreeningsFragment;
    private AbilityFragment mAbilityFragment;
    private CorpsFragment mCorpsFragment;
    private ImageView mErWweiMa, mMore;
    long currentUserId;//当前用户id
    long userId;


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.fragment_user_profi);
        userId = PreferenceManager.getDefaultSharedPreferences(getActivity()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        currentUserId = PreferenceManager.getDefaultSharedPreferences(getThis()).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        mTab1 = getViewById(R.id.tv_tab1);
        mTab2 = getViewById(R.id.tv_tab2);
        mTab3 = getViewById(R.id.tv_tab3);
        mTab4 = getViewById(R.id.tv_tab4);
        mMore = getViewById(R.id.image_more);
        mErWweiMa = getViewById(R.id.image_erweima);
        mProgressView = getViewById(R.id.progressBar);
        mHead = getViewById(R.id.image_head);
        mTv_LV = getViewById(R.id.tv_lv);
        mErWweiMa.setOnClickListener(this);
        mMore.setOnClickListener(this);
        mProgressView.setOnClickListener(this);
        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTab4.setOnClickListener(this);
        mHead.setOnClickListener(this);

    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getUserMsg();
    }

    /**
     * 获取用户信息
     */
    private void getUserMsg() {
        String url = "http://www.ktfootball.com/apiv2/users/detail?" +
                "current_user_id=" + currentUserId + "&user_id=" + userId +
                "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        Log.e("huangbo", currentUserId + " +  " + userId + url);
        showLoadingDiaglog();
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        UserMsg userMsg = new Gson().fromJson(jsonObject.toString(), new TypeToken<UserMsg>() {
                        }.getType());
                        initView(userMsg);//初始化视图
                        App.getInstance().setUserMSG(userMsg);
                        EventBus.getDefault().post(new Rank1Event());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(getActivity()).addRequest(jsonRequest);
    }


    //初始化视图
    private void initView(UserMsg userMsg) {
        String uri = "http://www.ktfootball.com" + userMsg.avatar;//加载头像
        Glide.with(getActivity()).load(uri).error(R.drawable.result_btnkt).transform(new GlideCircleTransform(getThis())).into(mHead);
        int lvi = 0;
        if (userMsg.exp <= 50) {
            lvi = 0;
            if (userMsg.exp > 0)
                mProgressView.setTimerProgress(Util.voll(userMsg.exp, 50));
            else
                mProgressView.setTimerProgress(0);
        } else if (userMsg.exp > 50 && userMsg.exp <= 250) {
            lvi = 1;
            mProgressView.setTimerProgress(Util.voll(userMsg.exp - 50, 200));
        } else if (userMsg.exp > 250 && userMsg.exp <= 750) {
            lvi = 2;
            mProgressView.setTimerProgress(Util.voll(userMsg.exp - 250, 500));
        } else if (userMsg.exp > 750 && userMsg.exp <= 1750) {
            lvi = 3;
            mProgressView.setTimerProgress(Util.voll(userMsg.exp - 750, 1000));
        } else if (userMsg.exp > 1750 && userMsg.exp <= 4000) {
            lvi = 4;
            mProgressView.setTimerProgress(Util.voll(userMsg.exp - 1750, 2250));
        } else if (userMsg.exp > 4000 && userMsg.exp <= 7500) {
            lvi = 5;
            mProgressView.setTimerProgress(Util.voll(userMsg.exp - 4000, 3500));
        } else if (userMsg.exp > 7500 && userMsg.exp <= 10000) {
            lvi = 6;
            mProgressView.setTimerProgress(Util.voll(userMsg.exp - 7500, 92500));
        } else if (userMsg.exp > 100000) {
            lvi = 7;
            mProgressView.setTimerProgress(100);
        }
        mTv_LV.setText("LV" + lvi);
        setSelect(0);

    }

    @Override
    protected void onUserVisible() {

    }


    private void setSelect(int i) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction trs = fm.beginTransaction();
        hideFragment(trs);

        switch (i) {
            case 0:
                if (mBattleFragment == null) {
                    mBattleFragment = new BattleFragment();
                    trs.add(R.id.framelayout, mBattleFragment);
                } else {
                    trs.show(mBattleFragment);
                }
                break;
            case 1:
                if (mScreeningsFragment == null) {
                    mScreeningsFragment = new ScreeningsFragment();
                    trs.add(R.id.framelayout, mScreeningsFragment);
                } else {
                    trs.show(mScreeningsFragment);
                }
                break;

            case 2:
                if (mAbilityFragment == null) {
                    mAbilityFragment = new AbilityFragment();
                    trs.add(R.id.framelayout, mAbilityFragment);
                } else {
                    trs.show(mAbilityFragment);
                }
                break;
            case 3:
                if (mCorpsFragment == null) {
                    mCorpsFragment = new CorpsFragment();
                    trs.add(R.id.framelayout, mCorpsFragment);
                } else {
                    trs.show(mCorpsFragment);
                }
                break;

        }
        trs.commit();
    }

    /*
     * 隐藏所有的Fragment
     */
    private void hideFragment(FragmentTransaction trs) {

        if (mBattleFragment != null) {
            trs.hide(mBattleFragment);
        }
        if (mScreeningsFragment != null) {
            trs.hide(mScreeningsFragment);
        }
        if (mAbilityFragment != null) {
            trs.hide(mAbilityFragment);
        }
        if (mCorpsFragment != null) {
            trs.hide(mCorpsFragment);
        }

    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.tv_tab1:
                mTab1.setBackgroundResource(R.drawable.shape_gold);
                mTab2.setBackgroundResource(R.drawable.shape_empty);
                mTab3.setBackgroundResource(R.drawable.shape_empty);
                mTab4.setBackgroundResource(R.drawable.shape_empty);
                mTab1.setTextColor(getResources().getColor(R.color.gold));
                mTab2.setTextColor(getResources().getColor(R.color.white));
                mTab3.setTextColor(getResources().getColor(R.color.white));
                mTab4.setTextColor(getResources().getColor(R.color.white));
                setSelect(0);
                break;
            case R.id.tv_tab2:
                mTab2.setBackgroundResource(R.drawable.shape_gold);
                mTab1.setBackgroundResource(R.drawable.shape_empty);
                mTab3.setBackgroundResource(R.drawable.shape_empty);
                mTab4.setBackgroundResource(R.drawable.shape_empty);
                mTab2.setTextColor(getResources().getColor(R.color.gold));
                mTab1.setTextColor(getResources().getColor(R.color.white));
                mTab3.setTextColor(getResources().getColor(R.color.white));
                mTab4.setTextColor(getResources().getColor(R.color.white));
                setSelect(1);
                break;
            case R.id.tv_tab3:
                mTab3.setBackgroundResource(R.drawable.shape_gold);
                mTab2.setBackgroundResource(R.drawable.shape_empty);
                mTab1.setBackgroundResource(R.drawable.shape_empty);
                mTab4.setBackgroundResource(R.drawable.shape_empty);
                mTab3.setTextColor(getResources().getColor(R.color.gold));
                mTab2.setTextColor(getResources().getColor(R.color.white));
                mTab1.setTextColor(getResources().getColor(R.color.white));
                mTab4.setTextColor(getResources().getColor(R.color.white));
                setSelect(2);
                break;
            case R.id.tv_tab4:
                mTab4.setBackgroundResource(R.drawable.shape_gold);
                mTab2.setBackgroundResource(R.drawable.shape_empty);
                mTab3.setBackgroundResource(R.drawable.shape_empty);
                mTab1.setBackgroundResource(R.drawable.shape_empty);
                mTab4.setTextColor(getResources().getColor(R.color.gold));
                mTab2.setTextColor(getResources().getColor(R.color.white));
                mTab3.setTextColor(getResources().getColor(R.color.white));
                mTab1.setTextColor(getResources().getColor(R.color.white));
                setSelect(3);
                break;
            case R.id.image_more:
                showPopupWindow(v);
                break;
            case R.id.image_erweima:
                Intent intent = new Intent(getThis(), MyQRCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.image_head:
                Intent intent1 = new Intent(getThis(), UserProfiles.class);
                intent1.putExtra(UserProfiles.USERID, userId);
                startActivity(intent1);
                break;
        }
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getThis()).inflate(
                R.layout.layout_more_menu, null);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        contentView.findViewById(R.id.linear_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getThis(), MyMsgActivity.class));
                popupWindow.dismiss();
            }
        });
        int isJudge = PreferenceManager.getDefaultSharedPreferences(getThis()).getInt(LoginActivity.PRE_CURRENT_IS_JUSGE, 0);
        if (isJudge == 0) {
            contentView.findViewById(R.id.linear_caipan).setVisibility(View.GONE);
        }
        contentView.findViewById(R.id.linear_zhuji).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), MyBaiDuFootPrintActivity.class);
                intent.putExtra("user_id", currentUserId);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        contentView.findViewById(R.id.linear_caipan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getThis(), JudgeSeleteActivity.class));
                popupWindow.dismiss();
            }
        });
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        final WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        popupWindow.setAnimationStyle(R.style.AnimationPreview);
        popupWindow.showAsDropDown(view);

    }


}
