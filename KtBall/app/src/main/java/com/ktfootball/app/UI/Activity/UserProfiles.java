package com.ktfootball.app.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.App;
import com.kt.ktball.entity.UserMsg;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.R;
import com.ktfootball.app.UI.Activity.setting.SetActivity;
import com.ktfootball.app.UI.Activity.setting.UserinfoChangeActivity;
import com.ktfootball.app.UI.Fragment.UserDetail.UserAbilityFragment;
import com.ktfootball.app.UI.Fragment.UserDetail.UserReviewFragment;
import com.ktfootball.app.UI.Fragment.UserDetail.UserTeamFragment;
import com.ktfootball.app.Utils.Util;
import com.ktfootball.app.Views.CircleProgressView;
import com.ktfootball.app.Views.SharedDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

public class UserProfiles extends BaseActivity {


    public static final String EXTRA_ME_OR_HE_USER_ID = "user_id";
    @Bind(R.id.view_avatar)
    ImageView avatarImage;//头像
    @Bind(R.id.textView2)
    TextView nameTextView;//姓名
    @Bind(R.id.textView3)
    TextView powerTextView;//TK学徒
    @Bind(R.id.textView5)
    TextView nengshouTextView;//Kt能手
    @Bind(R.id.tv_paim)
    TextView rankTextView;//全国排名
    @Bind(R.id.textView15)
    TextView LvTextView;//lv值
    @Bind(R.id.tv_folloewd)
    TextView textViewFollowed;//关注
    @Bind(R.id.imageView3)
    ImageView imageViewMenu;//菜单
    @Bind(R.id.progressBar)
    CircleProgressView mProgressView;
    private SharedDialog dialog;
    @Bind(R.id.image_head)
    ImageView mHead;
    @Bind(R.id.tv_tab1)
    TextView mTab1;
    @Bind(R.id.tv_tab2)
    TextView mTab2;
    @Bind(R.id.tv_tab3)
    TextView mTab3;
    @Bind(R.id.my_viewpager)
    ViewPager mViewpager;
    @Bind(R.id.tv_guanzhu)
    TextView mTv_guanzhu;
    @Bind(R.id.tv_fans)
    TextView mTv_fans;
    @Bind(R.id.tv_search)
    TextView tv_search;
    @Bind(R.id.image_setting)
    ImageView mSetting;
    long currentUserId;//当前用户id
    public long userId;
    int followed = 0;
    private List<Fragment> mList = new ArrayList<>();
    private UserMsg userMsg;

    public static final String USERID = "userId";

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_profiles);
        showLoadingDiaglog();

    }

    @Override
    protected void setListener() {
        mTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(0);

            }
        });
        mTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(1);

            }
        });
        mTab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewpager.setCurrentItem(2);

            }
        });
        textViewFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doFollowed();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShareSDK.initSDK(this);
        Intent intent = getIntent();
        userId = intent.getLongExtra(USERID, 0);
        currentUserId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        init();//判断个人页面还是他人页面
        getUserMsg();//网络获取个人信息
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void init() {//判断个人页面还是他人页面
        if (currentUserId == userId) {//个人页面
            textViewFollowed.setVisibility(View.GONE);
            imageViewMenu.setVisibility(View.VISIBLE);
        } else {//他人的个人页面
            textViewFollowed.setVisibility(View.VISIBLE);
            imageViewMenu.setVisibility(View.GONE);
            tv_search.setVisibility(View.GONE);
            mTab3.setText("他的战队");
            mSetting.setVisibility(View.GONE);
        }
    }


    private void getUserMsg() {
        String url = "http://www.ktfootball.com/apiv2/users/detail?" +
                "current_user_id=" + currentUserId + "&user_id=" + userId +
                "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        closeLoadingDialog();
                        userMsg = new Gson().fromJson(jsonObject.toString(), new TypeToken<UserMsg>() {
                        }.getType());
                        initView(userMsg);//初始化视图
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    //初始化视图
    private void initView(UserMsg userMsg) {
        UserAbilityFragment userAbilityFragment = new UserAbilityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",userMsg);
        userAbilityFragment.setArguments(bundle);
        mList.add(userAbilityFragment);
        mList.add(new UserReviewFragment());
        mList.add(new UserTeamFragment());
        String uri = "http://www.ktfootball.com" + userMsg.avatar;//加载头像
        Glide.with(getThis()).load(uri).into(avatarImage);
        Glide.with(getThis()).load(uri).transform(new GlideCircleTransform(getThis())).into(mHead);
        nameTextView.setText(userMsg.nickname);//姓名
        textViewFollowed.setText(userMsg.followed == 0 ? "关注" : "取消关注");
        followed = userMsg.followed;
        mTv_fans.setText("粉丝 " + userMsg.fans_count);
        mTv_guanzhu.setText("关注 "+userMsg.follow_count );
        String power = null;//KT学徒
        if (userMsg.power > 0 && userMsg.power <= 500) {
            power = "KT菜鸟";
        } else if (userMsg.power > 500 && userMsg.power <= 1000) {
            power = "KT学徒";
        } else if (userMsg.power > 1000 && userMsg.power <= 1500) {
            power = "KT达人";
        } else if (userMsg.power > 1500 && userMsg.power <= 2000) {
            power = "KT大师";
        } else if (userMsg.power > 2000) {
            power = "KT大神";
        }
        powerTextView.setText(power);

        rankTextView.setText("战力排名" + String.valueOf(userMsg.rank));//排名
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
        LvTextView.setText("LV" + lvi);//LV值


//        double kt = userMsg.kt / 4.45 * 2.5 * 100 + 1.00;
//        double win_rate = userMsg.win_rate / 41.13 * 2.5 + 1.00;
//        double scores = userMsg.scores / 6.48 * 2.5 + 1.00;
//        double goals = userMsg.goals / 2.67 * 2.5 + 1.00;
//        double pannas = userMsg.pannas / 0.67 * 2.5 + 1.00;

//        HashMap<Double, String> map = new HashMap<>();
//        map.put(kt, "KT能手");
//        map.put(win_rate, "穿裆王");
//        map.put(scores, "进球王");
//        map.put(goals, "赢球王");
//        map.put(pannas, "得分王");
//        double[] data = {kt, win_rate, scores, goals, pannas};
//        for (int i = 0; i < data.length; i++) {//冒泡排序
//            for (int j = i + 1; j < data.length; j++) {
//                if (data[i] < data[j]) {
//                    double k = data[i];
//                    data[i] = data[j];
//                    data[j] = k;
//                }
//            }
//        }
//        if (data[0] >= 3) {//能手
//            nengshouTextView.setText(map.get(data[0]));
//        } else {
//            nengshouTextView.setVisibility(View.GONE);
//        }
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
                checkTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //弹出菜单
    public void doPoP(View view) {
        showDialog();
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new SharedDialog(this, R.style.transparentFrameWindowStyle);
            dialog.setTitleUrl("http://ktfootball.com/app_share/user?user_id=" + userId);
            dialog.setTitle("我在KT足球已经排到了全国" + rankTextView.getText() + "名");
            dialog.setText("我的个人档案");
        }
        dialog.show();
        dimActivity(dialog, 0.6f);


    }

    public void doBack(View view) {//退出
        finish();
    }

    @OnClick(R.id.tv_fans)
    public void toFans(){
        if (currentUserId == userId){
            Intent intent = new Intent(getThis(),MyFansActivity.class);
            intent.putExtra("user_id",currentUserId);
            startActivity(intent);
        }
    }

    @OnClick(R.id.tv_guanzhu)
    public void toGuanZhu(){
        if (currentUserId == userId){
            Intent intent = new Intent(getThis(),MyFollowActivity.class);
            intent.putExtra("user_id",currentUserId);
            startActivity(intent);
        }
    }

    public void doFollowed() {//关注
        if (followed == 0) {//未关注，点击关注
            String url = "http://www.ktfootball.com/apiv2/users/follow";
            JSONObject jsonObject1 = new JSONObject();
            try {
                jsonObject1.put("user_id", currentUserId);
                jsonObject1.put("follow_user_id", userId);
                jsonObject1.put("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonObject1,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("----------", jsonObject.toString());
                            try {
                                JSONObject jsonObject11 = new JSONObject(jsonObject.toString());
                                String response = jsonObject11.getString("response");
                                if (response.equals("success")) {
                                    followed = 1;
                                    textViewFollowed.setText("取消关注");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                }
            }
            ) {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    return headers;
                }
            };
            VolleyUtil.getInstance(this).addRequest(jsonRequest);
        } else {//关注，点击取消关注
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("是否取消关注？");
            builder.setCancelable(false);
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String url = "http://www.ktfootball.com/apiv2/users/cancel_follow";
                    JSONObject jsonObject1 = new JSONObject();
                    try {
                        jsonObject1.put("user_id", currentUserId);
                        jsonObject1.put("follow_user_id", userId);
                        jsonObject1.put("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                            Request.Method.POST,
                            url,
                            jsonObject1,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject jsonObject) {
                                    Log.d("----------", jsonObject.toString());
                                    try {
                                        JSONObject jsonObject11 = new JSONObject(jsonObject.toString());
                                        String response = jsonObject11.getString("response");
                                        if (response.equals("success")) {
                                            followed = 0;
                                            textViewFollowed.setText("关注");
                                        } else if (response.equals("error")) {
                                            String msg = jsonObject11.getString("msg");
                                            MyAlertDialog myAlertDialog = new MyAlertDialog(UserProfiles.this);
                                            myAlertDialog.doAlertDialog(msg);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                        }
                    }
                    ) {
                        @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Accept", "application/json");
                            headers.put("Content-Type", "application/json; charset=UTF-8");
                            return headers;
                        }
                    };
                    VolleyUtil.getInstance(UserProfiles.this).addRequest(jsonRequest);
                }
            });
            builder.setNegativeButton("取消", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }


    private void checkTabs(int i) {
        switch (i) {
            case 0:
                mTab1.setBackgroundColor(getResources().getColor(R.color.gold));
                mTab1.setTextColor(0xffffffff);
                mTab2.setTextColor(0xff999999);
                mTab2.setBackgroundColor(0xffffffff);
                mTab3.setTextColor(0xff999999);
                mTab3.setBackgroundColor(0xffffffff);
                break;
            case 1:
                mTab2.setBackgroundColor(getResources().getColor(R.color.gold));
                mTab2.setTextColor(0xffffffff);
                mTab1.setTextColor(0xff999999);
                mTab1.setBackgroundColor(0xffffffff);
                mTab3.setTextColor(0xff999999);
                mTab3.setBackgroundColor(0xffffffff);
                break;
            case 2:
                mTab3.setBackgroundColor(getResources().getColor(R.color.gold));
                mTab3.setTextColor(0xffffffff);
                mTab1.setTextColor(0xff999999);
                mTab1.setBackgroundColor(0xffffffff);
                mTab2.setTextColor(0xff999999);
                mTab2.setBackgroundColor(0xffffffff);
                break;
        }

    }

    @OnClick(R.id.image_setting)
    public void toSetting(){
        Intent intent = new Intent(getThis(), UserinfoChangeActivity.class);
        intent.putExtra(UserinfoChangeActivity.USER_ID, App.getUserLogin().user_id+"");
        startActivity(intent);

    }

    @OnClick(R.id.tv_search)
    public void toSearch(){
        startActivity(MyFirendActivity.class);
    }
}
