package com.ktfootball.app.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.App;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.CommentedVideos;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.Request.CommentedRequest;
import com.ktfootball.app.UI.Activity.SuperStar.StarRecommendActivity;
import com.ktfootball.app.UI.Activity.setting.SetActivity;
import com.kt.ktball.entity.UserMsg;
import com.ktfootball.app.Manager.BitmapManager;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.MyCircleImageView;
import com.kt.ktball.myclass.NewProgressBar;
import com.kt.ktball.myclass.SpiderProgressBar;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Views.SharedDialog;
import com.ktfootball.app.R;
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

public class UserProfiles extends BaseActivity {

//    57007

    public static final String EXTRA_ME_OR_HE_USER_ID = "user_id";
    @Bind(R.id.view_avatar)
    MyCircleImageView avatarImage;//头像
    @Bind(R.id.textView2)
    TextView nameTextView;//姓名
    @Bind(R.id.textView3)
    TextView powerTextView;//TK学徒
    @Bind(R.id.textView4)
    TextView sexTextView;//性别
    @Bind(R.id.textView5)
    TextView nengshouTextView;//Kt能手
    @Bind(R.id.textView8)
    TextView fightingTextView;//战斗力
    @Bind(R.id.textView9)
    TextView rankTextView;//全国排名
    @Bind(R.id.textView15)
    TextView LvTextView;//lv值
    @Bind(R.id.textView16)
    TextView expTextView;//经验
    @Bind(R.id.textView19)
    TextView winTextView;//胜
    @Bind(R.id.textView21)
    TextView drawTextView;//平
    @Bind(R.id.textView23)
    TextView loseTextView;//负
    @Bind(R.id.progressBar)
    ProgressBar progressBarExp;//经验进度条
    @Bind(R.id.textView1001)
    TextView textViewFollowed;//关注
    @Bind(R.id.imageView3)
    ImageView imageViewMenu;//菜单
    @Bind(R.id.linearLayout4)
    LinearLayout linearLayoutGeRenImage;//个人页面5个图片布局
    @Bind(R.id.linearLayout2)
    LinearLayout linearLayoutGeRenTextView;//个人页面5个文本框布局
    @Bind(R.id.relativeLayout987)
    RelativeLayout relativeLayoutImage;//他人个人页面5个图片布局
    @Bind(R.id.relativeLayout988)
    RelativeLayout relativeLayoutTextView;//他人个人页面5个文本框布局
    @Bind(R.id.imageView51)
    ImageView imageViewHeFootprint;//他的足迹
    @Bind(R.id.imageView12)
    ImageView imageViewHeVcr;//他的视频
    @Bind(R.id.imageView50)
    ImageView imageViewHeTeam;//他的团队
    @Bind(R.id.activity_user_profiles_diandiandian)
    TextView diandiandian;
    @Bind(R.id.activity_user_profiles_stardianp)
    ImageView stardianp;
    @Bind(R.id.spiderProgressBar)
    SpiderProgressBar spiderProgressBar;
    private SharedDialog dialog;

    long currentUserId;//当前用户id
    long userId;
    int followed = 0;

    @Bind(R.id.view2)
    NewProgressBar newProgressBar;

    public static final String USERID = "userId";

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_profiles);
    }

    @Override
    protected void setListener() {
        stardianp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), StarRecommendActivity.class);
                intent.putExtra(Constants.USER_ID, currentUserId+"");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShareSDK.initSDK(this);
    }

    @OnClick(R.id.view_avatar)
    public void avatarClick(View view) {
        Intent intent = new Intent(getThis(), MyQRCodeActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        userId = intent.getLongExtra(USERID, 0);
        currentUserId = PreferenceManager.getDefaultSharedPreferences(this).getLong(LoginActivity.PRE_CURRENT_USER_ID, 0);
        init();//判断个人页面还是他人页面
        getUserMsg();//网络获取个人信息
    }

    private void init() {//判断个人页面还是他人页面
        if (currentUserId == userId) {//个人页面
            textViewFollowed.setVisibility(View.GONE);
            imageViewMenu.setVisibility(View.VISIBLE);
            relativeLayoutImage.setVisibility(View.GONE);
            relativeLayoutTextView.setVisibility(View.GONE);
            linearLayoutGeRenImage.setVisibility(View.VISIBLE);
            linearLayoutGeRenTextView.setVisibility(View.VISIBLE);
            stardianp.setVisibility(View.VISIBLE);
            if (App.getUserLogin().is_star_v) {
                Drawable drawable = getResources().getDrawable(R.drawable.icon_vip);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                nameTextView.setCompoundDrawables(drawable, null, null, null);
                nameTextView.setCompoundDrawablePadding(dp2px(5));
            }else{
                stardianp.setVisibility(View.GONE);
            }
            doGetPushVideos();
        } else {//他人的个人页面
            textViewFollowed.setVisibility(View.VISIBLE);
            imageViewMenu.setVisibility(View.GONE);
            relativeLayoutImage.setVisibility(View.VISIBLE);
            relativeLayoutTextView.setVisibility(View.VISIBLE);
            linearLayoutGeRenImage.setVisibility(View.GONE);
            linearLayoutGeRenTextView.setVisibility(View.GONE);
            imageViewHeFootprint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//他的足迹
                    Intent intent = new Intent(UserProfiles.this, MyBaiDuFootPrintActivity.class);
                    intent.putExtra(EXTRA_ME_OR_HE_USER_ID, userId);
                    startActivity(intent);
                }
            });
            imageViewHeVcr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//他的视频
                    Intent intent = new Intent(UserProfiles.this, MyDVActivity.class);
                    intent.putExtra(EXTRA_ME_OR_HE_USER_ID, userId);
                    startActivity(intent);
                }
            });
            imageViewHeTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//他的团队
                    Intent intent = new Intent(UserProfiles.this, MyTeamActivity.class);
                    intent.putExtra(EXTRA_ME_OR_HE_USER_ID, userId);
                    startActivity(intent);
                }
            });
        }
    }

    public void doGetPushVideos() {
        CommentedRequest request = new CommentedRequest(Constants.GET_PUSH_VIDEOS, RequestMethod.GET);
        request.add("user_id", userId);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<CommentedVideos>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<CommentedVideos> response) {
                int x = 0;
                if (isSuccess(response.get().response)) {
                    if (response.get().game_videos.size() > 0) {
                        diandiandian.setVisibility(View.VISIBLE);
                        for (int y = 0; y < response.get().game_videos.size(); y++) {
                            if (response.get().game_videos.get(y).is_commented == 0) {
                                x++;
                            }
                        }
                        diandiandian.setText(x + "");
                    }
                    if(x == 0){
                        diandiandian.setVisibility(View.GONE);
                    }
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, false, false);
    }

    private void getUserMsg() {
        String url = "http://www.ktfootball.com/apiv2/users/detail?" +
                "current_user_id=" + currentUserId + "&user_id=" + userId +
                "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
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
                        Log.d("==============", userMsg.toString());
                        initView(userMsg);//初始化视图
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                closeLoadingDialog();
                Log.d("==============", volleyError.toString());
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    //初始化视图
    private void initView(UserMsg userMsg) {
        String uri = "http://www.ktfootball.com" + userMsg.avatar;//加载头像
        BitmapManager.getInstance().displayUserLogo(avatarImage, uri);
        nameTextView.setText(userMsg.nickname);//姓名
        textViewFollowed.setText(userMsg.followed == 0 ? "关注" : "已关注");
        followed = userMsg.followed;

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

        sexTextView.setText(userMsg.gender);//性别
        fightingTextView.setText(String.valueOf(userMsg.power));//战斗力
        rankTextView.setText(String.valueOf(userMsg.rank));//排名
        int lvi = 0;
        int experience = 0;
        if (userMsg.exp <= 50) {
            lvi = 0;
            experience = 50;
        } else if (userMsg.exp > 50 && userMsg.exp <= 250) {
            lvi = 1;
            experience = 250;
        } else if (userMsg.exp > 250 && userMsg.exp <= 750) {
            lvi = 2;
            experience = 750;
        } else if (userMsg.exp > 750 && userMsg.exp <= 1750) {
            lvi = 3;
            experience = 1750;
        } else if (userMsg.exp > 1750 && userMsg.exp <= 4000) {
            lvi = 4;
            experience = 4000;
        } else if (userMsg.exp > 4000 && userMsg.exp <= 7500) {
            lvi = 5;
            experience = 7500;
        } else if (userMsg.exp > 7500 && userMsg.exp <= 100000) {
            lvi = 6;
            experience = 100000;
        } else if (userMsg.exp > 100000) {
            lvi = 7;
            experience = 100000;
        }
        LvTextView.setText("LV" + lvi);//LV值
        expTextView.setText("经验: " + userMsg.exp + "/" + experience);//经验值
        winTextView.setText("胜" + userMsg.win + "场");//胜
        drawTextView.setText("平" + userMsg.draw + "场");//平
        loseTextView.setText("负" + userMsg.lose + "场");//负

        newProgressBar.setProgressS(userMsg.win);
        newProgressBar.setProgressP(userMsg.draw);
        newProgressBar.setProgressF(userMsg.lose);

        progressBarExp.setMax(experience);//经验进度条
        progressBarExp.setProgress(userMsg.exp);

        double kt = userMsg.kt / 4.45 * 2.5 * 100 + 1.00;
        double win_rate = userMsg.win_rate / 41.13 * 2.5 + 1.00;
        double scores = userMsg.scores / 6.48 * 2.5 + 1.00;
        double goals = userMsg.goals / 2.67 * 2.5 + 1.00;
        double pannas = userMsg.pannas / 0.67 * 2.5 + 1.00;

        spiderProgressBar.setWinning(win_rate > 6 ? 6.0 : win_rate);
        spiderProgressBar.setKt(kt > 6 ? 6.0 : kt);
        spiderProgressBar.setScore(scores > 6 ? 6.0 : scores);
        spiderProgressBar.setGoal(goals > 6 ? 6.0 : goals);
        spiderProgressBar.setTroughLegs(pannas > 6 ? 6.0 : pannas);

        HashMap<Double, String> map = new HashMap<>();
        map.put(kt, "KT能手");
        map.put(win_rate, "穿裆王");
        map.put(scores, "进球王");
        map.put(goals, "赢球王");
        map.put(pannas, "得分王");
        double[] data = {kt, win_rate, scores, goals, pannas};
        for (int i = 0; i < data.length; i++) {//冒泡排序
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] < data[j]) {
                    double k = data[i];
                    data[i] = data[j];
                    data[j] = k;
                }
            }
        }
        if (data[0] >= 3) {//能手
            nengshouTextView.setText(map.get(data[0]));
        } else {
            nengshouTextView.setVisibility(View.GONE);
        }

    }

    //弹出菜单
    public void doPoP(View view) {
        int isJudge = PreferenceManager.getDefaultSharedPreferences(this).getInt(LoginActivity.PRE_CURRENT_IS_JUSGE, 0);
        PopupMenu popupMenu = new PopupMenu(this, view);
        if (isJudge == 0) {
            popupMenu.inflate(R.menu.user_profiles_menu2);
        } else {
            popupMenu.inflate(R.menu.user_profiles_menu);
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_set://设置
                        startActivity(new Intent(UserProfiles.this, SetActivity.class));
                        break;
                    case R.id.action_share:
                        showDialog();
                        break;
                    case R.id.action_judge:
                        startActivity(new Intent(UserProfiles.this, JudgeSeleteActivity.class));
                        break;
//                    case R.id.action_my_msg:
//                        break;
                }
                return true;
            }
        });
        popupMenu.show();
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


//        ShareSDK.initSDK(this);
//        OnekeyShare oks = new OnekeyShare();
//        //关闭sso授权
//        oks.disableSSOWhenAuthorize();
//
//        // title标题：微信、QQ（新浪微博不需要标题）
//        oks.setTitle("我是分享标题");  //最多30个字符
//
//        // text是分享文本：所有平台都需要这个字段
//        oks.setText("我是分享文本，啦啦啦~http://uestcbmi.com/");  //最多40个字符
//
//        // imagePath是图片的本地路径：除Linked-In以外的平台都支持此参数
//        //oks.setImagePath(Environment.getExternalStorageDirectory() + "/meinv.jpg");//确保SDcard下面存在此张图片
//
//        //网络图片的url：所有平台
//        oks.setImageUrl("http://7sby7r.com1.z0.glb.clouddn.com/CYSJ_02.jpg");//网络图片rul
//
//        // url：仅在微信（包括好友和朋友圈）中使用
//        oks.setUrl("http://sharesdk.cn");   //网友点进链接后，可以看到分享的详情
//
//        // Url：仅在QQ空间使用
//        oks.setTitleUrl("http://www.baidu.com");  //网友点进链接后，可以看到分享的详情
//
//        // 启动分享GUI
//        oks.show(this);
    }

    public void doBack(View view) {//退出
        finish();
    }

    public void doFollowed(View view) {//关注
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
                                    textViewFollowed.setText("已关注");
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

    public void doMyDV(View view) {//我的视频
        Intent intent = new Intent(this, MyDVActivity.class);
        intent.putExtra(EXTRA_ME_OR_HE_USER_ID, currentUserId);
        startActivity(intent);
    }

    public void doMyRank(View view) {//我的排名
        Intent intent = new Intent(this, RankingListActivity.class);
        startActivity(intent);
    }

    public void doMyFirend(View view) {//我的好友
        Intent intent = new Intent(this, MyFirendActivity.class);
        startActivity(intent);
    }

    public void doMyTeam(View view) {//我的战队
        Intent intent = new Intent(this, MyTeamActivity.class);
        intent.putExtra(EXTRA_ME_OR_HE_USER_ID, currentUserId);
        startActivity(intent);
    }

    public void doMyFootPrint(View view) {//我的足迹
        Intent intent = new Intent(this, MyBaiDuFootPrintActivity.class);
        intent.putExtra(EXTRA_ME_OR_HE_USER_ID, currentUserId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }
}
