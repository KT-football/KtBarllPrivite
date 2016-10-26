package com.ktfootball.app.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.bumptech.glide.Glide;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.PhoneUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kt.ktball.App;
import com.kt.ktball.entity.CimmentsData;
import com.kt.ktball.entity.Comments;
import com.kt.ktball.entity.LikeUsers;
import com.kt.ktball.entity.VideoDetailsData;
import com.kt.ktball.myclass.GlideCircleTransform;
import com.kt.ktball.myclass.MyAlertDialog;
import com.kt.ktball.myclass.MyDialog;
import com.kt.ktball.myclass.VolleyUtil;
import com.ktfootball.app.Adapter.MyExpandableListViewAdapter;
import com.ktfootball.app.Base.BaseEntity;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.StarComment;
import com.ktfootball.app.Entity.SubComment;
import com.ktfootball.app.Entity.Video;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.BaseEntityRequest;
import com.ktfootball.app.Request.VideoRequest;
import com.ktfootball.app.Views.CustomExpandableListView;
import com.ktfootball.app.Views.SharedDialog;
import com.yolanda.nohttp.RequestMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

public class VideoDetailsActivity extends BaseToolBarActivity2 {

    @Bind(R.id.view)
    ImageView imageViewPlay;//播放
    @Bind(R.id.relativeLayout30)
    RelativeLayout relativeLayout1;//用户布局1
    @Bind(R.id.relativeLayout31)
    RelativeLayout relativeLayout2;//用户布局2
    @Bind(R.id.relativeLayout32)
    RelativeLayout relativeLayout3;//用户布局3

    @Bind(R.id.relativeLayout33)
    RelativeLayout relativeLayout4;//用户布局3
    @Bind(R.id.relativeLayout333)
    RelativeLayout relativeLayout5;//用户布局3
    @Bind(R.id.relativeLayout3333)
    RelativeLayout relativeLayout6;//用户布局3

    @Bind(R.id.relativeLayout29)
    RelativeLayout send_rl;//用户布局3

    @Bind(R.id.textView66)
    TextView textViewLeftBiFen;//左边比分
    @Bind(R.id.textView00009)
    TextView textViewName;//赛事名称
    @Bind(R.id.textView00008)
    TextView textViewTime;//赛事时间
    @Bind(R.id.textView00007)
    TextView textViewAddress;//赛事地点
    @Bind(R.id.imageView60)
    ImageView imageViewLove;//喜欢图标
    @Bind(R.id.textView79)
    TextView textViewLove;//喜欢数量
    @Bind(R.id.textView81)
    TextView textViewPingLun;//评论数量
    @Bind(R.id.imageView62)
    ImageView imageViewFenXiang;//分享图标
    @Bind(R.id.listView10)
    CustomExpandableListView listView;//评论列表
    @Bind(R.id.editText9)
    EditText editTextAdd;//添加评论
    @Bind(R.id.textView65)
    TextView textViewSend;//发送添加评论

    @Bind(R.id.imageView57)
    ImageView imageViewA;
    @Bind(R.id.imageView58)
    ImageView imageViewB;
    @Bind(R.id.imageView577)
    ImageView imageViewC;
    @Bind(R.id.imageView588)
    ImageView imageViewD;
    @Bind(R.id.imageView5777)
    ImageView imageViewE;
    @Bind(R.id.imageView5888)
    ImageView imageViewF;

    @Bind(R.id.textView71)
    TextView textViewNameA;
    @Bind(R.id.textView77)
    TextView textViewNameB;
    @Bind(R.id.textView711)
    TextView textViewNameC;
    @Bind(R.id.textView777)
    TextView textViewNameD;
    @Bind(R.id.textView7111)
    TextView textViewNameE;
    @Bind(R.id.textView7777)
    TextView textViewNameF;

    @Bind(R.id.textView72)
    TextView textViewZhanA;
    @Bind(R.id.textView78)
    TextView textViewZhanB;
    @Bind(R.id.textView722)
    TextView textViewZhanC;
    @Bind(R.id.textView788)
    TextView textViewZhanD;
    @Bind(R.id.textView7222)
    TextView textViewZhanE;
    @Bind(R.id.textView7888)
    TextView textViewZhanF;

    private SharedDialog sharedDialog;
    private int conmmentCode = 0;

    long userId;
    int video;
    MyExpandableListViewAdapter commentsAdapter;
    MyDialog dialog;
    String score;
    private VideoDetailsData videoDetailsData;
    private Video videoBean;
    String video_comment_id;
    private List<StarComment> group_list;
    private List<List<SubComment>> item_list;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.activity_video_details);
    }

    @Override
    protected void initToolBar() {
        setToolBarTitle("对战详情");
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void setListener() {
        getToolbar().setOnMenuItemClickListener(onMenuItemClick);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShareSDK.initSDK(this);
        userId = App.getUserLogin().user_id;
        Intent intent = getIntent();
        video = intent.getIntExtra(Constants.EXTRA_VIDEOS_ID, 0);
        score = intent.getStringExtra(Constants.EXTRA_SCORES);
        initView();
        //随便一堆测试数据
        initStarV();
//        doClubBcOrders("89");
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_shared:
                    showDialog();
                    break;
            }
            return true;
        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shared, menu);
        return true;
    }

//    private void doClubBcOrders(String club_id) {
//        ClubBcOrdersRequest request = new ClubBcOrdersRequest(Constants.CLUB_BC_ORDERS, RequestMethod.GET);
//        request.add("club_id", club_id);
////        getThis().showDialogToast(club_id);
//        showLoadingDiaglog();
//        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<ClubBcOrders>() {
//            @Override
//            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<ClubBcOrders> response) {
//                closeLoadingDialog();
//                item_list = new ArrayList<List<ClubBcOrders.Orders>>();
//                item_list.add(response.get().bc_orders.get("2"));
//                item_list.add(response.get().bc_orders.get("3"));
//                item_list.add(response.get().bc_orders.get("4"));
//                listView.setAdapter(new MyExpandableListViewAdapter(getThis(), group_list, item_list));
//                setListViewHeight(listView);
//            }
//
//            @Override
//            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
//                closeLoadingDialog();
//            }
//        }, false, false);
//    }

    private void setListViewHeight(ExpandableListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        int count = listAdapter.getCount();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void initV() {//评论详情
        String url = Constants.HOST + "videos/comments?user_id="
                + userId + "&game_video_id=" + video +
                "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        Log.d("]]]]]]]]]]]]", url);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        CimmentsData cimmentsData = new Gson().fromJson(jsonObject.toString(),
                                new TypeToken<CimmentsData>() {
                                }.getType());
                        ArrayList<Comments> data = cimmentsData.comments;
                        Log.d("]]]]]]]]]]]]", data.toString());
                        initList(cimmentsData);

                        item_list = new ArrayList<>();
                        if (videoBean != null) {
                            for (int x = 0; x < group_list.size(); x++) {
                                if (x >= videoBean.star_comments.size()) {
                                    item_list.add(new ArrayList<SubComment>());
                                } else {
                                    item_list.add(videoBean.star_comments.get(x).sub_comments);
                                }
                            }
                        }
                        textViewPingLun.setText("" + group_list.size());
                        commentsAdapter = new MyExpandableListViewAdapter((VideoDetailsActivity) getThis(), group_list, item_list);
                        listView.setAdapter(commentsAdapter);
                        setListViewHeight(listView);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    private void initList(CimmentsData cimmentsData) {
        group_list = new ArrayList<>();
        if (videoBean != null) {
            group_list.addAll(videoBean.star_comments);
        }
        if (cimmentsData != null) {
            for (int x = 0; x < cimmentsData.comments.size(); x++) {
                Comments comments = cimmentsData.comments.get(x);
                StarComment starComment = new StarComment();
                starComment.avatar = comments.avatar;
                starComment.content = comments.content;
                starComment.id = comments.game_video_comment_id + "";
                starComment.nickname = comments.nickname;
                group_list.add(starComment);
            }
        }
    }

    private void initStarV() {
        VideoRequest request = new VideoRequest(Constants.VIDEO, RequestMethod.GET);
        request.add("game_video_id", video + "");
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<Video>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<Video> response) {
                if (response.get().response != null && response.get().response.equals("success")) {
                    videoBean = response.get();
                    initV();
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, false, false);
    }

    private void initView() {
        String url = Constants.HOST + "videos/show?game_video_id="
                + video + "&authenticity_token=K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8";
        Log.d("=========", url);
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("=========", jsonObject.toString());
                        videoDetailsData = new Gson().fromJson(jsonObject.toString(),
                                new TypeToken<VideoDetailsData>() {
                                }.getType());
                        init(videoDetailsData);//开始布局
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                MyAlertDialog myAlertDialog = new MyAlertDialog(VideoDetailsActivity.this);
                myAlertDialog.doAlertDialog("无法找到视频");
            }
        }
        );
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    private void init(final VideoDetailsData videoDetailsData) {//获取视频详情
        if (score != null) {
            textViewLeftBiFen.setText(score);
        }

        switch (videoDetailsData.game_video_type) {
            case 0://1v1
                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout4.setVisibility(View.VISIBLE);
                String uri1 = Constants.HEAD_HOST + videoDetailsData.users.get(0).avatar;
                String uri2 = Constants.HEAD_HOST + videoDetailsData.users.get(1).avatar;
                Glide.with(this).load(uri1).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewA);
                Glide.with(this).load(uri2).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewB);
                textViewNameA.setText(videoDetailsData.users.get(0).nickname);
                textViewNameB.setText(videoDetailsData.users.get(1).nickname);
                textViewZhanA.setText("战斗力" + videoDetailsData.users.get(0).power);
                textViewZhanB.setText("战斗力" + videoDetailsData.users.get(1).power);
                imageViewA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(0).user_id);
                        startActivity(intent);
                    }
                });
                imageViewB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(1).user_id);
                        startActivity(intent);
                    }
                });
                break;
            case 1://2v2
                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout4.setVisibility(View.VISIBLE);
                relativeLayout2.setVisibility(View.VISIBLE);
                relativeLayout5.setVisibility(View.VISIBLE);
                String uri11 = Constants.HEAD_HOST + videoDetailsData.users.get(0).avatar;
                String uri21 = Constants.HEAD_HOST + videoDetailsData.users.get(1).avatar;
                String uri31 = Constants.HEAD_HOST + videoDetailsData.users.get(2).avatar;
                String uri41 = Constants.HEAD_HOST + videoDetailsData.users.get(3).avatar;
                Glide.with(this).load(uri11).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewA);
                Glide.with(this).load(uri21).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewC);
                Glide.with(this).load(uri31).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewB);
                Glide.with(this).load(uri41).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewD);
                textViewNameA.setText(videoDetailsData.users.get(0).nickname);
                textViewNameB.setText(videoDetailsData.users.get(2).nickname);
                textViewNameC.setText(videoDetailsData.users.get(1).nickname);
                textViewNameD.setText(videoDetailsData.users.get(3).nickname);
                textViewZhanA.setText("战斗力" + videoDetailsData.users.get(0).power);
                textViewZhanC.setText("战斗力" + videoDetailsData.users.get(1).power);
                textViewZhanB.setText("战斗力" + videoDetailsData.users.get(2).power);
                textViewZhanD.setText("战斗力" + videoDetailsData.users.get(3).power);
                imageViewA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(0).user_id);
                        startActivity(intent);
                    }
                });
                imageViewC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(1).user_id);
                        startActivity(intent);
                    }
                });
                imageViewB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(2).user_id);
                        startActivity(intent);
                    }
                });
                imageViewD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(3).user_id);
                        startActivity(intent);
                    }
                });
                break;
            case 2://3v3
                relativeLayout1.setVisibility(View.VISIBLE);
                relativeLayout2.setVisibility(View.VISIBLE);
                relativeLayout3.setVisibility(View.VISIBLE);
                relativeLayout4.setVisibility(View.VISIBLE);
                relativeLayout6.setVisibility(View.VISIBLE);
                relativeLayout5.setVisibility(View.VISIBLE);
                String uri111 = Constants.HEAD_HOST + videoDetailsData.users.get(0).avatar;
                String uri222 = Constants.HEAD_HOST + videoDetailsData.users.get(1).avatar;
                String uri333 = Constants.HEAD_HOST + videoDetailsData.users.get(2).avatar;
                String uri444 = Constants.HEAD_HOST + videoDetailsData.users.get(3).avatar;
                String uri555 = Constants.HEAD_HOST + videoDetailsData.users.get(3).avatar;
                String uri666 = Constants.HEAD_HOST + videoDetailsData.users.get(3).avatar;
                Glide.with(this).load(uri111).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewA);
                Glide.with(this).load(uri222).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewC);
                Glide.with(this).load(uri333).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewE);
                Glide.with(this).load(uri444).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewB);
                Glide.with(this).load(uri555).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewD);
                Glide.with(this).load(uri666).transform(new GlideCircleTransform(this)).error(R.drawable.result_btnkt).into(imageViewF);
                textViewNameA.setText(videoDetailsData.users.get(0).nickname);
                textViewNameC.setText(videoDetailsData.users.get(1).nickname);
                textViewNameE.setText(videoDetailsData.users.get(2).nickname);
                textViewNameB.setText(videoDetailsData.users.get(3).nickname);
                textViewNameD.setText(videoDetailsData.users.get(4).nickname);
                textViewNameF.setText(videoDetailsData.users.get(5).nickname);
                textViewZhanA.setText("战斗力" + videoDetailsData.users.get(0).power);
                textViewZhanC.setText("战斗力" + videoDetailsData.users.get(1).power);
                textViewZhanE.setText("战斗力" + videoDetailsData.users.get(2).power);
                textViewZhanB.setText("战斗力" + videoDetailsData.users.get(3).power);
                textViewZhanD.setText("战斗力" + videoDetailsData.users.get(4).power);
                textViewZhanF.setText("战斗力" + videoDetailsData.users.get(5).power);
                imageViewA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(0).user_id);
                        startActivity(intent);
                    }
                });
                imageViewC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(1).user_id);
                        startActivity(intent);
                    }
                });
                imageViewE.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(2).user_id);
                        startActivity(intent);
                    }
                });
                imageViewB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(3).user_id);
                        startActivity(intent);
                    }
                });
                imageViewD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(4).user_id);
                        startActivity(intent);
                    }
                });
                imageViewF.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getThis(), UserProfiles.class);
                        intent.putExtra(UserProfiles.USERID, videoDetailsData.users.get(5).user_id);
                        startActivity(intent);
                    }
                });
                break;
        }
        textViewName.setText(videoDetailsData.name);
        textViewTime.setText(videoDetailsData.time);
        textViewAddress.setText(videoDetailsData.local);
        ArrayList<LikeUsers> likeUserses = videoDetailsData.like_users;
        ArrayList<Long> list = new ArrayList<>();
        for (int i = 0; i < likeUserses.size(); i++) {
            list.add(likeUserses.get(i).user_id);
        }
        if (list.contains(userId)) imageViewLove.setImageResource(R.mipmap.dianzan_zan);
        textViewLove.setText("" + videoDetailsData.likes);


    }

    public void doBack(View view) {//退出
        finish();
    }

    public void doLove(View view) {//喜欢影片
        String url = Constants.HOST + "videos/like";
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("game_video_id", video);
            jsonObject1.put("user_id", userId);
            jsonObject1.put("authenticity_token", "K9MpaPMdj0jij2m149sL1a7TcYrWXmg5GLrAJDCNBx8");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject1,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("+++++++++++++", jsonObject.toString());
                        try {
                            JSONObject jsonObject11 = new JSONObject(jsonObject.toString());
                            String response = jsonObject11.getString("response");
                            if (response.equals("success")) {
                                int likes = jsonObject11.getInt("likes");
                                imageViewLove.setImageResource(R.mipmap.dianzan_zan);
                                textViewLove.setText("" + likes);
                            } else if (response.equals("error")) {
                                String msg = jsonObject11.getString("msg");
                                MyAlertDialog myAlertDialog = new MyAlertDialog(VideoDetailsActivity.this);
                                myAlertDialog.doAlertDialog(msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        VolleyUtil.getInstance(this).addRequest(jsonRequest);
    }

    public void doPlay(View view) {//播放视频
        if (videoDetailsData != null) {
            Intent i = new Intent(getThis(), PlayerActivity.class);
            String id = getVideoId(videoDetailsData.uri);
            LogUtils.e(id);
            i.putExtra("vid", id);
            startActivity(i);
        }
    }

    public void doSendMessage(View view) {
        doComment(view, null);
    }

    public void doComment(View view, String video_comment_id) {
        if (video_comment_id == null || "".equals(video_comment_id)) {
            if (App.getUserLogin().is_star) {
                conmmentCode = 1;
            } else {
                conmmentCode = 0;
            }
        } else {
            conmmentCode = 2;
            this.video_comment_id = video_comment_id;
        }
        send_rl.setVisibility(View.VISIBLE);
        PhoneUtils.showInputMethod(getThis(), editTextAdd);
    }

    private void hide() {
        send_rl.setVisibility(View.GONE);
        PhoneUtils.closeInputMethod(getThis(), editTextAdd);
    }

    private void showDialog() {
        if (sharedDialog == null) {
            sharedDialog = new SharedDialog(this, R.style.transparentFrameWindowStyle);
            sharedDialog.setTitleUrl(Constants.HOST + "app_share/game_video?game_video_id=" + video);
            String title = getSharedString() + " @" + videoDetailsData.local + " @" + score + " ," + "来自KT足球";
            sharedDialog.setTitle(title);
            sharedDialog.setText("KT足球比赛视频精选");
        }
        sharedDialog.show();
        dimActivity(sharedDialog, 0.6f);
    }

    private String getSharedString() {
        String sharedString = "";
        switch (videoDetailsData.game_video_type) {
            case 0://1v1
                sharedString = videoDetailsData.users.get(0).nickname + "vs" + videoDetailsData.users.get(1).nickname;
                break;
            case 1://2v2
                sharedString = videoDetailsData.users.get(0).nickname + " " + videoDetailsData.users.get(2).nickname + "vs" + videoDetailsData.users.get(1).nickname + " " + videoDetailsData.users.get(3).nickname;
                break;
            case 2://3v3
                sharedString = videoDetailsData.users.get(0).nickname + " " + videoDetailsData.users.get(2).nickname + " " + videoDetailsData.users.get(4).nickname + "vs" + videoDetailsData.users.get(1).nickname + " " + videoDetailsData.users.get(3).nickname + " " + videoDetailsData.users.get(5).nickname;

                break;
        }
        return sharedString;
    }

    private String getVideoId(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1);
    }

    public void doSend(View view) {//发送添加评论
        String input = editTextAdd.getText().toString();
        if (!TextUtils.isEmpty(input)) {
            showLoadingDiaglog();
            switch (conmmentCode) {
                case 0:
                    doAddComment(input);
                    break;
                case 1:
                    doVideoComment(input);
                    break;
                case 2:
                    doVideoCommentReply(video_comment_id, input);
                    break;
            }
        }
    }

    public void doVideoComment(String text) {
        BaseEntityRequest request = new BaseEntityRequest(Constants.VIDEO_COMMENT, RequestMethod.POST);
        request.add("game_video_id", video + "");
        request.add("user_id", userId + "");
        request.add("content", text);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<BaseEntity>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<BaseEntity> response) {
                closeLoadingDialog();
                if (response.get().response.equals("success")) {
                    editTextAdd.setText("");
                    hide();
                    initStarV();
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    public void doAddComment(String text) {
        BaseEntityRequest request = new BaseEntityRequest(Constants.ADD_COMMENT, RequestMethod.POST);
        request.add("game_video_id", video + "");
        request.add("user_id", userId + "");
        request.add("content", text);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<BaseEntity>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<BaseEntity> response) {
                closeLoadingDialog();
                if (response.get().response.equals("success")) {
                    editTextAdd.setText("");
                    hide();
                    initStarV();
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    public void doVideoCommentReply(String video_comment_id, String text) {
        BaseEntityRequest request = new BaseEntityRequest(Constants.VIDEO_COMMENT_REPLY, RequestMethod.POST);
        request.add("game_video_id", video + "");
        request.add("user_id", userId + "");
        request.add("video_comment_id", video_comment_id);
        request.add("content", text);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<BaseEntity>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<BaseEntity> response) {
                closeLoadingDialog();
                if (response.get().response.equals("success")) {
                    editTextAdd.setText("");
                    hide();
                    initStarV();
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    @OnClick(R.id.duizhan_btn)
    public void btn() {
        if (videoDetailsData != null) {
            Intent intent = new Intent(getThis(), HengHuadActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", videoDetailsData);
            bundle.putString("url", Constants.HOST + "app_share/game_video?game_video_id=" + video);
            bundle.putString("title", getSharedString() + " @" + videoDetailsData.local + " @" + score + " ," + "来自KT足球");
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }

}
