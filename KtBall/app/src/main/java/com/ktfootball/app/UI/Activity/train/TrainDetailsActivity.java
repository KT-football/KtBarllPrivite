package com.ktfootball.app.UI.Activity.train;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.FileUtil;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.PhoneUtils;
import com.frame.app.utils.ZipUtils;
import com.frame.app.view.ColorArcProgressBar;
import com.frame.app.view.Dialog.SingleDownDialog;
import com.kt.ktball.App;
import com.ktfootball.app.Manager.BitmapManager;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.AddToUserAppCartoons;
import com.ktfootball.app.Entity.AppCartoon;
import com.ktfootball.app.Entity.LoadFile;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.AddToUserAppCartoonsRequest;
import com.ktfootball.app.Request.AppCartoonRequest;
import com.ktfootball.app.Utils.CommonUtils;
import com.ktfootball.app.Views.DownListDialog;
import com.ktfootball.app.Views.SharedDialog;
import com.ktfootball.app.Views.StartTrainDialog;
import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.download.DownloadListener;
import com.yolanda.nohttp.download.DownloadRequest;
import com.yolanda.nohttp.error.ArgumentError;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.ServerError;
import com.yolanda.nohttp.error.StorageReadWriteError;
import com.yolanda.nohttp.error.StorageSpaceNotEnoughError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.Response;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by jy on 16/6/14.
 */
public class TrainDetailsActivity extends BaseToolBarActivity2 {

    private TextView name;
    private TextView name_2;
    private TextView details;
    private TextView intro;
    private TextView intro_2;
    private TextView intro_3;
    private TextView yxl;
    private TextView ywc;
    private ImageView img;
    private ImageView img_2;
    private ImageView img_3;
    private ColorArcProgressBar bar;
    private String app_cartoon_id;
    private String sub_name;
    private StartTrainDialog startTrainDiialog;
    public static final int STARTTRAIN_1 = 1001;
    public static final int STARTTRAIN_2 = 1002;
    public static final int STARTTRAIN_3 = 1003;
    public static final int MANHUA_1 = 1004;
    public static final int MANHUA_2 = 1005;
    private AppCartoon appCartoon;
    private SingleDownDialog singleDownDialog;
    private DownListDialog listDownDialog;
    /**
     * 下载请求.
     */
    private DownloadRequest mDownloadRequest;
    private int x = 0;
    private SharedDialog dialog;

    @Override
    protected void initToolBar() {
        setToolBarTitle("");

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shared, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_shared:
                    showSharedDialog();
                    break;
            }
            return true;
        }
    };

    private void showSharedDialog() {
        if (dialog == null) {
            dialog = new SharedDialog(this, R.style.transparentFrameWindowStyle);
            dialog.setTitleUrl("http://ktfootball.com/app_share/cartoon?user_id=" + App.getUserLogin().user_id + "&app_cartoon_id=" + appCartoon.id);
            dialog.setTitle("我在看KT足球漫画足球教程" + appCartoon.name + "，跟我一起来练习吧");
            dialog.setText("KT足球漫画教程");
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

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
        bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getThis(), TrainingLevelActivity.class);
                startActivity(intent);
            }
        });
        getToolbar().setOnMenuItemClickListener(onMenuItemClick);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShareSDK.initSDK(this);
        app_cartoon_id = getIntent().getStringExtra(Constants.APP_CARTOON_ID);
        sub_name = getIntent().getStringExtra(Constants.SUB_NAME);
        doAppCartoons();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        addContentView(R.layout.layout_traindetails);
        setBackgroundResource(R.drawable.bg_train);
        name = (TextView) findViewById(R.id.layout_traindetails_name);
        name_2 = (TextView) findViewById(R.id.layout_traindetails_name_2);
        details = (TextView) findViewById(R.id.layout_traindetails_details);
        intro = (TextView) findViewById(R.id.layout_traindetails_intro);
        intro_2 = (TextView) findViewById(R.id.layout_traindetails_intro_2);
        intro_3 = (TextView) findViewById(R.id.layout_traindetails_intro_3);
        img = (ImageView) findViewById(R.id.layout_traindetails_img);
        img_2 = (ImageView) findViewById(R.id.layout_traindetails_img_2);
        img_3 = (ImageView) findViewById(R.id.layout_traindetails_img_3);
        yxl = (TextView) findViewById(R.id.layout_traindetails_yxl_tv);
        ywc = (TextView) findViewById(R.id.layout_traindetails_ywc_tv);
        bar = (ColorArcProgressBar) findViewById(R.id.layout_traindetails_bar);
    }

    @OnClick(R.id.layout_traindetails_start)
    public void startTrain(View v) {
        showDialog();
    }

    private void showDialog() {
        if (startTrainDiialog == null) {
            startTrainDiialog = new StartTrainDialog(this, R.style.transparentFrameWindowStyle);
            startTrainDiialog.setItenClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownDialog(TrainDetailsActivity.STARTTRAIN_1);
                    startTrainDiialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownDialog(TrainDetailsActivity.STARTTRAIN_2);
                    startTrainDiialog.dismiss();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDownDialog(TrainDetailsActivity.STARTTRAIN_3);
                    startTrainDiialog.dismiss();
                }
            });
        }
        startTrainDiialog.show();
        dimActivity(startTrainDiialog, 0.6f);
    }

    private void doAppCartoons() {
        AppCartoonRequest request = new AppCartoonRequest(Constants.APP_CARTOON, RequestMethod.GET);
        request.add("user_id", App.getUserLogin().user_id + "");
        request.add("app_cartoon_id", app_cartoon_id);
        CallServer.getRequestInstance().add(this, 0, request, httpListener, false, true);
    }

    private HttpListener httpListener = new HttpListener<AppCartoon>() {
        @Override
        public void onSucceed(int what, Response<AppCartoon> response) {
            LogUtils.e(GsonTools.createGsonString(response.get()));
            appCartoon = response.get();
            if (appCartoon.response.equals("success")) {
                init(appCartoon);
            } else {
                showToast(R.string.request_failed);
            }
        }

        @Override
        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
        }
    };

    private void init(AppCartoon userAppCartoons) {
        setToolBarTitle(userAppCartoons.name);
        name_2.setText(sub_name);
        BitmapManager.getInstance().display(img, Constants.HOST + userAppCartoons.avatar);
        BitmapManager.getInstance().display(img_2, Constants.HOST + userAppCartoons.lessons.get(0).avatar);
        BitmapManager.getInstance().display(img_3, Constants.HOST + userAppCartoons.lessons.get(1).avatar);
        intro_2.setText(getString(R.string.train_details_text));
        intro_3.setText(getString(R.string.train_details_text_2_1) + userAppCartoons.name + getString(R.string.train_details_text_2_2));
        name.setText("KTBall" + userAppCartoons.name + "练习");
        intro.setText(userAppCartoons.intro);
        details.setText(userAppCartoons.description);
        yxl.setText(userAppCartoons.finished_minutes);
        ywc.setText(userAppCartoons.finished_times);
        bar.setCurrentValues(Float.parseFloat(userAppCartoons.now_level_progress));
        int[] colcr = CommonUtils.getTrainColor(userAppCartoons.now_level_color);
        bar.setColors(colcr);
        bar.setHintString(userAppCartoons.now_level_name);
        bar.setHintPaintColor(colcr[0]);
    }

    private void doSingleDown(String url, String name) {
        String fileName = getFileName(url);
        LogUtils.e(fileName);
        // url 下载地址。
        // fileFolder 保存的文件夹。
        // fileName 文件名。
        // isRange 是否断点续传下载。
        // isDeleteOld 如果发现存在同名文件，是否删除后重新下载，如果不删除，则直接下载成功。
        LogUtils.e(url);
        url = getReleaUrl(url);
        showsingleDownDialog(name);
        mDownloadRequest = NoHttp.createDownloadRequest(url, FileUtil.getDownloadDir(getThis()), fileName, true, true);
        mDownloadRequest.addHeader("Accept", "application/json");
        mDownloadRequest.setContentType("application/octet-stream; charset=UTF-8");
        // what 区分下载。
        // downloadRequest 下载请求对象。
        // downloadListener 下载监听。
        CallServer.getDownloadInstance().add(0, mDownloadRequest, downloadListener);
    }

    public static String getReleaUrl(String url) {
        try {
            url = URLEncoder.encode(url, "utf-8");
            url = url.replaceAll("\\+", "%20");
            return url.replaceAll("%3A", ":").replaceAll("%2F", "/");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    private String getFileName(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1);
    }

    @OnClick(R.id.layout_traindetails_manhua_2)
    public void manhua2(View view) {
        showDownDialog(MANHUA_2);
    }

    @OnClick(R.id.layout_traindetails_manhua_1)
    public void manhua1(View view) {
        showDownDialog(MANHUA_1);
    }

    public void showDownDialog(int code) {
        switch (code) {
            case STARTTRAIN_1:
                AppCartoon.Videos videos1 = selectVideo("0");
                if (videos1 != null) {
                    String str = "下载初级训练课程";
                    downVideos(str, videos1);
                }
                break;
            case STARTTRAIN_2:
                AppCartoon.Videos videos2 = selectVideo("1");
                if (videos2 != null) {
                    String str = "下载中级训练课程";
                    downVideos(str, videos2);
                }
                break;
            case STARTTRAIN_3:
                AppCartoon.Videos videos3 = selectVideo("2");
                if (videos3 != null) {
                    String str = "下载高级训练课程";
                    downVideos(str, videos3);
                }
                break;
            case MANHUA_1:
                List<AppCartoon.Lessons> lit1 = appCartoon.lessons;
                AppCartoon.Lessons lessons1 = lit1.get(1);
                if (lessons1 != null) {
                    String str = "下载教学漫画";
                    downLessons(str, lessons1, 0);
                }
                break;
            case MANHUA_2:
                List<AppCartoon.Lessons> lit2 = appCartoon.lessons;
                AppCartoon.Lessons lessons2 = lit2.get(0);
                if (lessons2 != null) {
                    String str = "下载漫画故事";
                    downLessons(str, lessons2, 1);
                }
                break;
        }
    }

    private void downVideos(String str, AppCartoon.Videos videos) {
        String path = FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/";
        String name = getFileName(videos.download_video_url).replace(".zip", "");
        if (FileUtil.fileExists(path + name)) {
            Intent intent = new Intent(getThis(), VedioViewpagerActiviy.class);
            intent.putExtra("path", path);
            intent.putExtra("name", name);
            intent.putExtra("data", appCartoon);
            intent.putExtra("video", videos);
            startActivity(intent);
        } else {
            selectDialog(str, videos.video_size, videos.download_video_url, str);
        }
    }

    private void downLessons(String str, AppCartoon.Lessons lessons, int code) {
        String path = FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/" + lessons.name;
        if (FileUtil.fileExists(path)) {
            Intent intent = new Intent(getThis(), LessonsActivity.class);
            intent.putExtra("path", path);
            intent.putExtra("data", appCartoon);
            intent.putExtra("name", lessons.name);
            intent.putExtra("code", code);
            startActivity(intent);
        } else {
            selectDialog(str, lessons.zip_size, lessons.download_images_url, str);
        }
    }

    private AppCartoon.Videos selectVideo(String level) {
        if (appCartoon != null) {
            List<AppCartoon.Videos> list = appCartoon.videos;
            for (int x = 0; x < list.size(); x++) {
                AppCartoon.Videos videos = list.get(x);
                if (videos.video_level.equals(level)) {
                    return videos;
                }
            }
        }
        return null;
    }

    private void selectDialog(String str, String size, String url, final String name) {
        int model = PhoneUtils.isNetworkAvailable(getThis());
        if (model == 1) {//wifi
            showWiFiDialog(str, size, url, name);
        } else {
            showGDialog(size, url, name);
        }
    }

    private void showWiFiDialog(String str, String size, final String url, final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getThis());
        builder.setMessage("当前为Wi-Fi网络，建议下载本课全部内容（约" + size + "KB）");
        builder.setTitle("提示");
        builder.setPositiveButton("   " + str, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doSingleDown(url, name);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("下载本课全部内容", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                download();
                dialog.dismiss();
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void showGDialog(String size, final String url, final String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getThis());
        builder.setMessage("下载内容将会消耗流量（约" + size + "KB)确定继续下载吗");
        builder.setTitle("提示");
        builder.setPositiveButton("我是土豪我要继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doSingleDown(url, name);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * 文件列表。
     */
    private List<LoadFile> mFileList;

    /**
     * 下载任务列表。
     */
    private List<DownloadRequest> mDownloadRequests;

    /**
     * 开始下载全部。
     */
    private void download() {
        if (appCartoon == null) {
            return;
        }
        mDownloadRequests = new ArrayList<>();
        mFileList = new ArrayList<>();
        for (int x = 0; x < appCartoon.lessons.size(); x++) {
            AppCartoon.Lessons l = appCartoon.lessons.get(x);
            // 创建四个下载请求并且保存起来。
            DownloadRequest downloadRequest = NoHttp.createDownloadRequest(getReleaUrl(l.download_images_url), FileUtil.getDownloadDir(getThis()), getFileName(l.download_images_url), true, true);
            mDownloadRequests.add(downloadRequest);
            mFileList.add(new LoadFile(l.name, 0));
        }
        for (int y = 0; y < appCartoon.videos.size(); y++) {
            AppCartoon.Videos v = appCartoon.videos.get(y);
            // 创建四个下载请求并且保存起来。
            DownloadRequest downloadRequest = NoHttp.createDownloadRequest(getReleaUrl(v.download_video_url), FileUtil.getDownloadDir(getThis()), getFileName(v.download_video_url), true, true);
            mDownloadRequests.add(downloadRequest);
            String name = "";
            if (v.video_level.equals("0")) {
                name = "下载初级教程";
            }
            if (v.video_level.equals("1")) {
                name = "下载中级教程";
            }
            if (v.video_level.equals("2")) {
                name = "下载高级教程";
            }
            mFileList.add(new LoadFile(name, 0));
        }
        showListDownDialog();
        x = 0;
        for (int i = 0; i < mDownloadRequests.size(); i++) {
            CallServer.getDownloadInstance().add(i, mDownloadRequests.get(i), listDownloadListener);
        }
    }

    private void showListDownDialog() {
        listDownDialog = new DownListDialog(getThis(), R.style.transparentFrameWindowStyle, mFileList);
        listDownDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                for (DownloadRequest downloadRequest : mDownloadRequests) {
                    downloadRequest.cancel();
                }
            }
        });
        listDownDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        listDownDialog.show();
        dimActivity(listDownDialog, 0.6f);
    }

    /**
     * 下载状态监听。
     */
    /**
     * 下载监听
     */
    private DownloadListener listDownloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            updateProgress(what, 0);
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
            String message = getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = getString(R.string.download_error_url);
            } else if (exception instanceof ArgumentError) {
                messageContent = getString(R.string.download_error_argument);
            } else {
                messageContent = getString(R.string.download_error_un);
            }
            showDialogToast(messageContent);
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            updateProgress(what, progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
            Logger.d("Download finish");
            try {
                Log.d("onFinish",filePath);
                Log.d("onFinish",FileUtil.getDecompressionDir(getThis()) + appCartoon.name+"...");
                ZipUtils.UnZipFolder(filePath, FileUtil.getDecompressionDir(getThis()) + appCartoon.name);
                new File(filePath).delete();
            } catch (Exception e) {
                showDialogToast("文件解压错误");
            }
            downFinish();
        }

        @Override
        public void onCancel(int what) {
        }


        /**
         * 更新进度。
         * @param what 哪个item。
         * @param progress 进度值。
         */
        private void updateProgress(int what, int progress) {
            mFileList.get(what).setProgress(progress);
            listDownDialog.notifyItemChanged(what);
        }
    };

    private synchronized void downFinish() {
        ++x;
        if (x == mFileList.size() && listDownDialog != null) {
            listDownDialog.dialogDismiss();
            addToUserAppCartoons();
        }
    }

    /**
     * 下载监听
     */
    private DownloadListener downloadListener = new DownloadListener() {

        @Override
        public void onStart(int what, boolean isResume, long beforeLength, Headers headers, long allCount) {
            LogUtils.e("onStart" + isResume + "..." + beforeLength + "..." + allCount);
        }

        @Override
        public void onDownloadError(int what, Exception exception) {
            LogUtils.e("onDownloadError = " + exception.toString());
            String message = getString(R.string.download_error);
            String messageContent;
            if (exception instanceof ServerError) {
                messageContent = getString(R.string.download_error_server);
            } else if (exception instanceof NetworkError) {
                messageContent = getString(R.string.download_error_network);
            } else if (exception instanceof StorageReadWriteError) {
                messageContent = getString(R.string.download_error_storage);
            } else if (exception instanceof StorageSpaceNotEnoughError) {
                messageContent = getString(R.string.download_error_space);
            } else if (exception instanceof TimeoutError) {
                messageContent = getString(R.string.download_error_timeout);
            } else if (exception instanceof UnKnownHostError) {
                messageContent = getString(R.string.download_error_un_know_host);
            } else if (exception instanceof URLError) {
                messageContent = getString(R.string.download_error_url);
            } else if (exception instanceof ArgumentError) {
                messageContent = getString(R.string.download_error_argument);
            } else {
                messageContent = getString(R.string.download_error_un);
            }
            message = String.format(Locale.getDefault(), message, messageContent);
            showDialogToast(message);
        }

        @Override
        public void onProgress(int what, int progress, long fileCount) {
            LogUtils.e(progress + "..." + fileCount);
            updateProgress(progress);
        }

        @Override
        public void onFinish(int what, String filePath) {
            singleDownDialog.setProgress(100);
            LogUtils.e("onFinish");
            LogUtils.e("文件路径：" + filePath);
            LogUtils.e(FileUtil.getDecompressionDir(getThis()));
            try {
                ZipUtils.UnZipFolder(filePath, FileUtil.getDecompressionDir(getThis()) + appCartoon.name);
                new File(filePath).delete();
            } catch (Exception e) {
                showDialogToast("文件解压错误");
            }
            addToUserAppCartoons();
            getHandler().postAtTime(new Runnable() {
                @Override
                public void run() {
                    singleDownDialog.dialogDismiss();
                }
            }, 500);

        }

        @Override
        public void onCancel(int what) {
        }

        private void updateProgress(int progress) {
            if (singleDownDialog.isShowing()) {
                singleDownDialog.setProgress(progress);
            }
        }
    };

    private void addToUserAppCartoons() {
        com.yolanda.nohttp.rest.Request<AddToUserAppCartoons> request = new AddToUserAppCartoonsRequest(Constants.ADD_TO_USER_APP_CARTOONS, RequestMethod.POST);
        request.add("user_id", App.getUserLogin().user_id);
        request.add("app_cartoon_id", appCartoon.id);
        CallServer.getRequestInstance().add(getThis(), 0, request, new HttpListener<AddToUserAppCartoons>() {
            @Override
            public void onSucceed(int what, Response<AddToUserAppCartoons> response) {

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
            }
        }, false, false);
    }


    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        // 暂停下载
        if (mDownloadRequest != null) {
            mDownloadRequest.cancel();
        }
        super.onDestroy();
    }

    private void showsingleDownDialog(String name) {
        if (singleDownDialog == null) {
            singleDownDialog = new SingleDownDialog(this, R.style.transparentFrameWindowStyle, name);
            singleDownDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    mDownloadRequest.cancel();
                }
            });
        }
        singleDownDialog.setProgress(0);
        singleDownDialog.setName(name);
        singleDownDialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        singleDownDialog.show();
        dimActivity(singleDownDialog, 0.6f);
    }

    @Override
    protected void onStart() {
        super.onStart();
        closeLoadingDialog();
    }
}
