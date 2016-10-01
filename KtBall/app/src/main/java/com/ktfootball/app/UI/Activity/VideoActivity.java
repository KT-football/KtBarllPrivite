package com.ktfootball.app.UI.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.utils.LogUtils;
import com.frame.app.view.NumberProgressBar.NumberProgressBar;
import com.kt.ktball.App;
import com.kt.ktball.db.SideAandBDaoHelper;
import com.kt.ktball.db.VcrPathDaoHelper;
import com.ktfootball.app.Adapter.VideoAdapter;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.GetRole;
import com.ktfootball.app.Entity.UploadVideo;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.GetRoleRequest;
import com.ktfootball.app.Request.UploadVideoRequest;
import com.ktfootball.www.dao.SideAandB;
import com.ktfootball.www.dao.VcrPath;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;
import com.youku.uploader.IUploadResponseHandler;
import com.youku.uploader.YoukuUploader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

public class VideoActivity extends BaseActivity {

    @Bind(R.id.listView2)
    ListView listView;
    @Bind(R.id.textView70)
    TextView textViewCount;
    @Bind(R.id.activity_video_error_count)
    TextView errorCount;
    @Bind(R.id.buttonUp)
    Button button;

    ArrayList<String> list;
    ArrayList<String> copyList;
    int size;
    private VideoAdapter videoAdapter;

    private String access_token;
    private String CLIENT_ID = "5570b4b24049e570";
    private String CLIENT_SECRET = "c40bcc367334ef63e42ef4562b460e7f";
    private YoukuUploader uploader;
    private NumberProgressBar progressBar;

    private void doSaveOk(SideAandB sb) {//上传成功
        VcrPathDaoHelper.getInstance().addData(initVcrPath(sb, true));
    }

    private void doSaveFailure(SideAandB sb) {//上传失败
        VcrPathDaoHelper.getInstance().addData(initVcrPath(sb, false));
    }

    private VcrPath initVcrPath(SideAandB sb, boolean isSuccess) {
        VcrPath vcrPath = new VcrPath();
        vcrPath.setUsers(sb.getUsers());
        vcrPath.setAdd_scores(sb.getAdd_scores());
        vcrPath.setResult(sb.getResult());
        vcrPath.setGoals(sb.getGoals());
        vcrPath.setPannas(sb.getPannas());
        vcrPath.setFouls(sb.getFouls());
        vcrPath.setFlagrant_fouls(sb.getFlagrant_fouls());
        vcrPath.setPanna_ko(sb.getPanna_ko());
        vcrPath.setAbstained(sb.getAbstained());
        vcrPath.setUsers_b(sb.getUsers_b());
        vcrPath.setAdd_scores_b(sb.getAdd_scores_b());
        vcrPath.setResult_b(sb.getResult_b());
        vcrPath.setGoals_b(sb.getGoals_b());
        vcrPath.setPannas_b(sb.getPannas_b());
        vcrPath.setFouls_b(sb.getFouls_b());
        vcrPath.setFlagrant_fouls_b(sb.getFlagrant_fouls_b());
        vcrPath.setPanna_ko_b(sb.getPanna_ko_b());
        vcrPath.setAbstained_b(sb.getAbstained_b());
        vcrPath.setTime(sb.getTime());
        vcrPath.setPath(sb.getPath());
        vcrPath.setGame_type(sb.getGame_type());
        vcrPath.setUser_id(sb.getUser_id());
        vcrPath.setGame_id(sb.getGame_id());
        vcrPath.setUser1_id(sb.getUser1_id());
        vcrPath.setUser2_id(sb.getUser2_id());
        vcrPath.setLeague1_id(sb.getLeague1_id());
        vcrPath.setLeague2_id(sb.getLeague2_id());
        vcrPath.setPicture(sb.getPicture());
        vcrPath.setBattle_id(sb.getBattle_id());
        vcrPath.setIsSuccess(isSuccess);
        return vcrPath;
    }

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case 123:
                uploadFinishSuccess(msg);
                break;
            case 456:
                String s = (String) msg.obj;
                uploadFinishFailure(s);
                break;
        }
    }

    private void uploadFinishSuccess(Message msg) {
        queryByPath(((String[]) msg.obj)[0], ((String[]) msg.obj)[1]);
        videoAdapter.deleteItem(((String[]) msg.obj)[0]);//删除listview中的path
        List<SideAandB> sideAandBsList = SideAandBDaoHelper.getInstance().queryByPath(((String[]) msg.obj)[0]);
        for (SideAandB ab : sideAandBsList) {
            doSaveOk(ab);
            SideAandBDaoHelper.getInstance().deleteBySideAandB(ab);
        }
        vidoeUploadFinish();
    }

    private void uploadFinishFailure(String path) {
        videoAdapter.deleteItem(path);//删除listview中的path
        List<SideAandB> sideAandBsList = SideAandBDaoHelper.getInstance().queryByPath(path);
        for (SideAandB ab : sideAandBsList) {
            doSaveFailure(ab);
            SideAandBDaoHelper.getInstance().deleteBySideAandB(ab);
        }
        vidoeUploadFinish();
    }

    private void vidoeUploadFinish() {
        int i = list.size() + 1;
        textViewCount.setText(String.valueOf(--i));
        if (++current < copyList.size()) {
            doUp(copyList.get(current));
        } else {
            setUploadButtonClick();
        }
    }

    private void setUploadButtonClick() {
        button.setEnabled(true);
        button.setText("全部上传");
        current = 0;
        button.setOnClickListener(UploadVideoClick);
        getErrorCount();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_video);
    }

    @Override
    protected void setListener() {
        button.setOnClickListener(UploadVideoClick);
    }

    private View.OnClickListener UploadVideoClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doUploadVideo(v);
        }
    };

    private View.OnClickListener stop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStopUploadVideo(v);
        }
    };

    private View.OnClickListener goOn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doGoOnUploadVideo(v);
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {
        list = new ArrayList<>();
        copyList = new ArrayList<>();
        textViewCount.setText("0");
        getErrorCount();
    }

    private void initView() {
        list.clear();
        List<SideAandB> sideAandBsList = SideAandBDaoHelper.getInstance().getAllData();
        for (SideAandB ab : sideAandBsList) {
            String path = ab.getPath();
            File file = new File(path);
            if (file.exists()) {
                list.add(path);
            }
        }
        videoAdapter = new VideoAdapter(list, this);
        listView.setAdapter(videoAdapter);
    }

    private void initData() {
        uploader = YoukuUploader.getInstance(CLIENT_ID, CLIENT_SECRET, getApplicationContext());
    }

    public void doBack(View view) {
        finish();
    }

    private int current = 0;

    public void doStopUploadVideo(View view) {
        button.setText("继续上传");
        button.setOnClickListener(goOn);
        if (uploader != null) {
            uploader.cancel();
        }
    }

    public void doGoOnUploadVideo(View view) {
        button.setText("暂停上传");
        button.setOnClickListener(stop);
        startUpload();
    }

    public void doUploadVideo(View view) {
        showLoadingDiaglog();
        GetRoleRequest request = new GetRoleRequest(Constants.GET_ROLE, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<GetRole>() {
            @Override
            public void onSucceed(int what, Response<GetRole> response) {
                closeLoadingDialog();
                if (response.get().response.equals("success")) {
                    GetRole token = response.get();
                    access_token = token.youku_token;
                    startUpload();
                } else {
                    showDialogToast(response.get().msg);
                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
                showDialogToast("请重新再试");
            }
        }, false, false);
    }

    private void startUpload() {
        if (list.size() != 0) {
            textViewCount.setText("" + list.size());
            button.setText("暂停上传");
            button.setOnClickListener(stop);
            size = list.size();
            copyList = new ArrayList<>(list);
            doUp(list.get(current));
        }
    }

    public void doUp(final String s) {
        if (listView != null) {
            progressBar = (NumberProgressBar) listView.getChildAt(0).findViewById(R.id.progressBar20);
        } else {
            progressBar = new NumberProgressBar(getThis());
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        HashMap<String, String> uploadInfo = new HashMap<>();
        uploadInfo.put("title", s.substring(s.indexOf("球") + 2, s.length()));
        uploadInfo.put("tags", "优酷,EVA");
        uploadInfo.put("file_name", s);
        LogUtils.e(s + "");
        uploader = YoukuUploader.getInstance(CLIENT_ID, CLIENT_SECRET, getApplicationContext());
        uploader.upload(params, uploadInfo, new IUploadResponseHandler() {

            @Override
            public void onStart() {
                Log.v(getTAG(), "onStart");
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSuccess(JSONObject response) {
                Log.v(getTAG(), "onSuccess    " + response.toString());
                Message msg = Message.obtain();
                msg.what = 123;
                msg.obj = new String[]{s, response.toString()};
                getHandler().sendMessage(msg);
            }

            @Override
            public void onProgressUpdate(int counter) {
                Log.v(getTAG(), "onProgressUpdate    " + counter + "");
                progressBar.setProgress(counter);
            }

            @Override
            public void onFailure(JSONObject errorResponse) {
                Log.v(getTAG(), "onFailure    " + errorResponse.toString());
                Message msg = Message.obtain();
                msg.what = 456;
                msg.obj = s;
                getHandler().sendMessage(msg);
            }

            @Override
            public void onFinished() {
                Log.v(getTAG(), "onFinished");
                progressBar.setProgress(0);
            }
        });
    }

    private void queryByPath(String path, String youKuURI) {
        try {
            JSONObject jsonObject = new JSONObject(youKuURI);
            youKuURI = (String) jsonObject.get("video_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        List<SideAandB> sideAandBList = SideAandBDaoHelper.getInstance().queryByPath(path);
        for (SideAandB sb : sideAandBList) {
            doUploadVideo(sb.getUser_id(), sb.getGame_id(), sb.getGame_type() + "", sb.getUser1_id(), sb.getUser2_id(), sb.getLeague1_id(), sb.getLeague2_id(), youKuURI, "",
                    sb.getTime(), sb.getPicture(), sb.getBattle_id());
        }
    }

//    public String user_id;//: 用户ID(裁判的ID)
//    public String game_id;//: 赛事 ID
//    public String game_type;//: 0 或 1 (0: 1v1, 1: 2v2, 2: 3v3)
//    public String user1_id;//: 1v1的情况下为 用户1的用户id
//    public String user2_id;//: 1v1的情况下为 用户2的用户id
//    public String league1_id;//: 2v2的情况下为战队1的战队ID
//    public String league2_id;//: 2v2的情况下为战队2的战队ID
//    public String youku_uri;//: 优酷视频地址
//    public String tudou_uri;//: 土豆视频地址
//    public String time;//: 比赛时间 格式: 2014-12-20 17:50:00
//    public String picture;//: 比赛结果截图
//    public String battle_id;//: 对战ID

    public void doUploadVideo(String user_id, String game_id, String game_type, String user1_id, String user2_id, String league1_id, String league2_id, String youku_uri
            , String tudou_uri, String time, String picture, String battle_id) {
        UploadVideoRequest request = new UploadVideoRequest(Constants.UPLOAD_VIDEO, RequestMethod.POST);
        request.add("user_id", user_id);
        request.add("game_id", game_id);
        request.add("game_type", game_type);
        request.add("user1_id", user1_id);
        request.add("user2_id", user2_id);
        request.add("league1_id", league1_id);
        request.add("league2_id", league2_id);
        request.add("youku_uri", youku_uri);
        request.add("tudou_uri", tudou_uri);
        request.add("time", time);
        request.add("picture", picture);
        request.add("battle_id", battle_id);
        LogUtils.d("user_id = " +user_id +"...game_id=" +game_id +"...game_type=" +game_type +"...user1_id=" +user1_id +"...user2_id=" +user2_id +"...league1_id=" + league1_id+"...league2_id=" + league2_id+"...youku_uri=" +youku_uri +"...tudou_uri=" +tudou_uri +"...time=" + time+"...picture=" +picture +"...battle_id=" + battle_id);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<UploadVideo>() {
            @Override
            public void onSucceed(int what, Response<UploadVideo> response) {
                closeLoadingDialog();
//                if (response.get().response.equals("success")) {
//                    showDialogToast("success");
//                } else {
//                    showDialogToast(response.get().msg);
//                }
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if (uploader != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("退出将暂停上传视频");
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uploader.cancel();
                        finish();
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //上传记录
    public void doUploadHistory(View view) {
        startActivity(new Intent(this, UpHistoryActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (uploader != null) {
            uploader.cancel();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (access_token == null || "".equals(access_token)) {
            initData();//初始化TOKEN
        }
        getErrorCount();
        initView();
        setUploadButtonClick();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (uploader != null) {
            uploader.cancel();
        }
    }

    private void getErrorCount() {
        List<VcrPath> list = VcrPathDaoHelper.getInstance().queryBySuccess(false);
        if (list == null || list.size() == 0) {
            errorCount.setVisibility(View.GONE);
        } else {
            errorCount.setText(list.size() + "");
            errorCount.setVisibility(View.VISIBLE);
        }
    }
}
