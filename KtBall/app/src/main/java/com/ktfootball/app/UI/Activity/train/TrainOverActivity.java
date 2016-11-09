package com.ktfootball.app.UI.Activity.train;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.ktfootball.app.Base.BaseEntity;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.AppCartoon;
import com.ktfootball.app.Entity.StudyFinished;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.BaseEntityRequest;
import com.ktfootball.app.Views.SharedDialog;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by jy on 16/6/24.
 */
public class TrainOverActivity extends BaseActivity {

    private StudyFinished studyFinished;
    private AppCartoon appCartoon;
    private RelativeLayout relativeLayout;
    private TextView tv_1;
    private TextView tv_2;
    int dj = 0;
    int gt = 0;
    int bg = 0;
    private LinearLayout zwdf;
    private LinearLayout wcqk;
    private LinearLayout jscz;
    private TextView tv_3;
    private TextView shared;
    private TextView submit_tv;
    private SharedDialog dialog;
    private AppCartoon.Videos videos;

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.layout_trainover);
        relativeLayout = (RelativeLayout) findViewById(R.id.layout_trainover_close);
        zwdf = (LinearLayout) findViewById(R.id.layout_trainover_zwdf);
        wcqk = (LinearLayout) findViewById(R.id.layout_trainover_wcqk);
        jscz = (LinearLayout) findViewById(R.id.layout_trainover_jscz);
        shared = (TextView) findViewById(R.id.layout_trainover_shared);
        submit_tv = (TextView) findViewById(R.id.layout_trainover_submit);
        tv_1 = (TextView) findViewById(R.id.layout_trainover_tv_1);
        tv_2 = (TextView) findViewById(R.id.layout_trainover_tv_2);
        tv_3 = (TextView) findViewById(R.id.layout_trainover_tv_3);
    }

    @Override
    protected void setListener() {
        submit_tv.setOnClickListener(submit);
        shared.setOnClickListener(sharedClick);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public View.OnClickListener submit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            doStudySelfScoreEvaluation();
        }
    };

    public View.OnClickListener sharedClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialog();
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShareSDK.initSDK(this);
        appCartoon = (AppCartoon) getIntent().getSerializableExtra("data");
        studyFinished = (StudyFinished) getIntent().getSerializableExtra("studyfinished");
        videos = (AppCartoon.Videos) getIntent().getSerializableExtra("vedio");

        tv_1.setText(getString(R.string.is_right)+studyFinished.finished_times+getString(R.string.cont_train));
        tv_3.setText(getString(R.string.jianc)+studyFinished.next_need_exp+getString(R.string.made)+appCartoon.name+getString(R.string.dasjo));
    }

    private void doStudySelfScoreEvaluation() {

        if(dj == 0){
            showDialogToast(getString(R.string.dafen_biaoxian));
            return;
        }

        if(gt == 0){
            showDialogToast(getString(R.string.dafen_qingk));
            return;
        }

        if(bg == 0){
            showDialogToast(getString(R.string.dafen_chenghang));
            return;
        }
        com.yolanda.nohttp.rest.Request<BaseEntity> request = new BaseEntityRequest(Constants.STUDY_SELF_SCORE_EVALUATION, RequestMethod.POST);
        request.add("app_cartoon_user_study_id", studyFinished.app_cartoon_user_study_id);
        request.add("self_stars", dj);
        request.add("finish_stars", gt);
        request.add("technique_stars", bg);

//        app_cartoon_user_study_id: 学习记录ID
//        self_stars: 自我打分
//        finish_stars: 完成情况
//        technique_stars: 技术成长
        showLoadingDiaglog();
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<BaseEntity>() {
            @Override
            public void onSucceed(int what, Response<BaseEntity> response) {
                closeLoadingDialog();
                showCloseDialog();
                showSharedBt();
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    private void showCloseDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getThis());
        dialog.setTitle(getString(R.string.prompt));
        dialog.setMessage(getString(R.string.commit_ok));
        dialog.setNegativeButton(getString(R.string.back_main), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        dialog.setPositiveButton(getString(R.string.here), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create().show();
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new SharedDialog(this, R.style.transparentFrameWindowStyle);
            dialog.setTitleUrl(Constants.SHARE_HOST+"app_share/study_finished?now_level_name="+studyFinished.now_level_name+"&total_finished_times="+videos.total_times+"&finished_times="+studyFinished.finished_times+"&next_need_exp="+studyFinished.next_need_exp+"&now_level_progress=" +studyFinished.now_level_progress);
            dialog.setTitle(getString(R.string.kt_ok) + appCartoon.name + getString(R.string.train_have)+(Float.parseFloat(studyFinished.next_need_exp) - Float.parseFloat(studyFinished.now_level_progress)));
            dialog.setText(getString(R.string.train_detail));
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

    private void showSharedBt(){
        submit_tv.setVisibility(View.GONE);
        shared.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 本次自我打分
            case R.id.layout_trainover_zwdf_xing_1:
                dj = 1;
                setStar(zwdf, dj);
                break;
            case R.id.layout_trainover_zwdf_xing_2:
                dj = 2;
                setStar(zwdf, dj);
                break;
            case R.id.layout_trainover_zwdf_xing_3:
                dj = 3;
                setStar(zwdf, dj);
                break;
            case R.id.layout_trainover_zwdf_xing_4:
                dj = 4;
                setStar(zwdf, dj);
                break;
            case R.id.layout_trainover_zwdf_xing_5:
                dj = 5;
                setStar(zwdf, dj);
                break;
            // 完成情况
            case R.id.layout_trainover_wcqk_xing_1:
                gt = 1;
                setStar(wcqk, gt);
                break;
            case R.id.layout_trainover_wcqk_xing_2:
                gt = 2;
                setStar(wcqk, gt);
                break;
            case R.id.layout_trainover_wcqk_xing_3:
                gt = 3;
                setStar(wcqk, gt);
                break;
            case R.id.layout_trainover_wcqk_xing_4:
                gt = 4;
                setStar(wcqk, gt);
                break;
            case R.id.layout_trainover_wcqk_xing_5:
                gt = 5;
                setStar(wcqk, gt);
                break;
            // 技术成长
            case R.id.layout_trainover_jscz_xing_1:
                bg = 1;
                setStar(jscz, bg);
                break;
            case R.id.layout_trainover_jscz_xing_2:
                bg = 2;
                setStar(jscz, bg);
                break;
            case R.id.layout_trainover_jscz_xing_3:
                bg = 3;
                setStar(jscz, bg);
                break;
            case R.id.layout_trainover_jscz_xing_4:
                bg = 4;
                setStar(jscz, bg);
                break;
            case R.id.layout_trainover_jscz_xing_5:
                bg = 5;
                setStar(jscz, bg);
                break;
        }
    }

    void setStar(LinearLayout ll, int num) {
        for (int x = 0; x < 5; x++) {
            if (x < num) {
                ll.getChildAt(x).setBackgroundResource(
                        R.mipmap.rating_show);
            } else {
                ll.getChildAt(x).setBackgroundResource(R.mipmap.rating);
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        closeLoadingDialog();
    }
}
