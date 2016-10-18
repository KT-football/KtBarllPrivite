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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.frame.app.base.activity.BaseActivity;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.FileUtil;
import com.frame.app.utils.GsonTools;
import com.frame.app.utils.LogUtils;
import com.frame.app.utils.PhoneUtils;
import com.frame.app.utils.ZipUtils;
import com.frame.app.view.Dialog.SingleDownDialog;
import com.kt.ktball.App;
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
import com.ktfootball.app.Views.CircleProgressView;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by jy on 16/6/14.
 */
public class TrainDetailsActivity extends BaseActivity {
    @Bind(R.id.progress)
    CircleProgressView mProgressView;
    @Bind(R.id.level_name)
    TextView mName;
    @Bind(R.id.tv_chuji)
    TextView tv_chuji;
    @Bind(R.id.tv_zhongji)
    TextView tv_zhongji;
    @Bind(R.id.tv_gaoji)
    TextView tv_gaoji;
    @Bind(R.id.tv_tishi)
    TextView tv_tishi;
    @Bind(R.id.tv_manhua_download)
    TextView mTv_manhua_download;
    @Bind(R.id.tv_zhenren_download)
    TextView mTv_zheren_download;
    @Bind(R.id.tv_mKT_download)
    TextView mTv_kt_download;
    @Bind(R.id.image_kt)
    ImageView mImage_kt;
    @Bind(R.id.image_manhua)
    ImageView mImage_manhua;
    @Bind(R.id.kt_progress)
    ProgressBar mProgress_kt;
    @Bind(R.id.manhua_progress)
    ProgressBar mProgress_manhua;
    @Bind(R.id.layout_traindetails_start)
    Button layout_traindetails_start;
    @Bind(R.id.xunlian_progress)
    ProgressBar mProgressXunlian;
    @Bind(R.id.tv_title)
    TextView tv_title;

    private TextView yxl;
    private TextView ywc;
    private String app_cartoon_id;
    private String sub_name;
    private StartTrainDialog startTrainDiialog;
    public static final int STARTTRAIN_1 = 1001;
    public static final int STARTTRAIN_2 = 1002;
    public static final int STARTTRAIN_3 = 1003;
    public static final int MANHUA_1 = 1004;
    public static final int MANHUA_2 = 1005;
    private SingleDownDialog singleDownDialog;
    private DownListDialog listDownDialog;
    /**
     * 下载请求.
     */
    private DownloadRequest mDownloadRequest;
    private int x = 0;
    private SharedDialog dialog;
    private AppCartoon appCartoon;

    private final int DOWNLOAD_GUSHI = 2;
    private final int DOWNLOAD_MANHUA = 1;
    private final int DOWNLOAD_XUNLIAN = 3;
    private int tab = 1;


    private void showSharedDialog() {
        if (dialog == null) {
            dialog = new SharedDialog(this, R.style.transparentFrameWindowStyle);
            dialog.setTitleUrl("http://ktfootball.com/app_share/cartoon?user_id=" + App.getUserLogin().user_id + "&app_cartoon_id=" + appCartoon.id);
            dialog.setTitle("我在看KT足球漫画足球教程" + appCartoon.name + "，跟我一起来练习吧");
            dialog.setText("KT足球漫画教程");
        }
        dialog.show();
        dimActivity(dialog, 0.6f);


    }


    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {
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
        setContentView(R.layout.layout_traindetails);
        yxl = (TextView) findViewById(R.id.layout_traindetails_yxl_tv);
        ywc = (TextView) findViewById(R.id.layout_traindetails_ywc_tv);
    }

    @OnClick(R.id.layout_traindetails_start)
    public void startTrain(View v) {
        switch (tab){
            /**
             * 初級
             */
            case 1:
                showDownDialog(TrainDetailsActivity.STARTTRAIN_1);
                break;
            /**
             * 中級
             */
            case 2:
                showDownDialog(TrainDetailsActivity.STARTTRAIN_2);
                break;
            /**
             * 高級
             */
            case 3:
                showDownDialog(TrainDetailsActivity.STARTTRAIN_3);
                break;
        }
//        showDialog(); 多下载
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

    /**
     * 獲取數據
     */
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

    /**
     * 初始化UI
     * @param userAppCartoons
     */
    private void init(AppCartoon userAppCartoons) {
        appCartoon = userAppCartoons;
        tv_title.setText(userAppCartoons.name);
        yxl.setText(userAppCartoons.finished_minutes + "min");
        ywc.setText(userAppCartoons.finished_times + "次");
        mProgressView.setTimerProgress((int) Float.parseFloat(userAppCartoons.now_level_progress));
        int[] colcr = CommonUtils.getTrainColor(userAppCartoons.now_level_color);
        mName.setText(userAppCartoons.now_level_name);
        mName.setTextColor(colcr[0]);
        mTv_manhua_download.setText("下载 " + checkSize(Integer.valueOf(userAppCartoons.lessons.get(1).zip_size)));
        mTv_kt_download.setText("下载 " + checkSize(Integer.valueOf(userAppCartoons.lessons.get(0).zip_size)));
        if (FileUtil.fileExists(FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/" + userAppCartoons.lessons.get(1).name)) {
            mTv_manhua_download.setVisibility(View.GONE);
        } else {
            mTv_manhua_download.setVisibility(View.VISIBLE);
            mImage_manhua.setVisibility(View.GONE);
        }

        if (FileUtil.fileExists(FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/" + userAppCartoons.lessons.get(0).name)) {
            mTv_kt_download.setVisibility(View.GONE);
        } else {
            mTv_kt_download.setVisibility(View.VISIBLE);
            mImage_kt.setVisibility(View.GONE);
        }
        AppCartoon.Videos videos1 = selectVideo("0");
        if (videos1 != null) {
            String path = FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/";
            String name = getFileName(videos1.download_video_url).replace(".zip", "");
            if (FileUtil.fileExists(path + name)) {
                layout_traindetails_start.setText("开始训练");
            } else {
                layout_traindetails_start.setText("下载训练(" + checkSize(Integer.parseInt(videos1.video_size)) + ")");
            }
        }
    }


    /**
     *換算M
     * @param size
     * @return
     */
    private String checkSize(int size) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format((double) size / 1024) + "M";

    }

    /**
     * 單步下載
     * @param url
     * @param name
     */
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

        mDownloadRequest = NoHttp.createDownloadRequest(url, FileUtil.getDownloadDir(getThis()), fileName, true, true);
        mDownloadRequest.addHeader("Accept", "application/json");
        mDownloadRequest.setContentType("application/octet-stream; charset=UTF-8");
        // what 区分下载。
        // downloadRequest 下载请求对象。
        // downloadListener 下载监听。
        if (name.contains("故事")) {
            CallServer.getDownloadInstance().add(DOWNLOAD_GUSHI, mDownloadRequest, downloadListener);
        } else if (name.contains("教学")) {
            CallServer.getDownloadInstance().add(DOWNLOAD_MANHUA, mDownloadRequest, downloadListener);
        } else if (name.contains("训练")) {
            CallServer.getDownloadInstance().add(DOWNLOAD_XUNLIAN, mDownloadRequest, downloadListener);
        } else {
            showsingleDownDialog(name);
            CallServer.getDownloadInstance().add(0, mDownloadRequest, downloadListener);
        }
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

    /**
     * ]
     * 下载视频
     *
     * @param str
     */
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
            doSingleDown(videos.download_video_url, str);
//            selectDialog(str, videos.video_size, videos.download_video_url, str);
        }
    }

    /**
     * ]
     * 下载漫画
     *
     * @param str
     * @param lessons
     * @param code
     */
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
            doSingleDown(lessons.download_images_url, str);
//            selectDialog(str, lessons.zip_size, lessons.download_images_url, str);
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
                Log.d("onFinish", filePath);
                Log.d("onFinish", FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "...");
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
            switch (what) {
                case DOWNLOAD_MANHUA:
                    mProgress_manhua.setVisibility(View.VISIBLE);
                    mTv_manhua_download.setVisibility(View.GONE);
                    mProgress_manhua.setProgress(progress);
                    break;
                case DOWNLOAD_GUSHI:
                    mProgress_kt.setVisibility(View.VISIBLE);
                    mTv_kt_download.setVisibility(View.GONE);
                    mProgress_kt.setProgress(progress);
                    break;
                case DOWNLOAD_XUNLIAN:
                    mProgressXunlian.setVisibility(View.VISIBLE);
                    mProgressXunlian.setProgress(progress);
                    layout_traindetails_start.setVisibility(View.GONE);
                    break;
                default:
                    updateProgress(progress);
                    break;
            }

        }

        @Override
        public void onFinish(final int what, String filePath) {
            if (singleDownDialog != null)
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
                    switch (what) {
                        case DOWNLOAD_MANHUA:
                            mProgress_manhua.setVisibility(View.GONE);
                            mImage_manhua.setVisibility(View.VISIBLE);
                            break;
                        case DOWNLOAD_GUSHI:
                            mProgress_kt.setVisibility(View.GONE);
                            mImage_kt.setVisibility(View.VISIBLE);
                            break;
                        case DOWNLOAD_XUNLIAN:
                            mProgressXunlian.setVisibility(View.GONE);
                            layout_traindetails_start.setVisibility(View.VISIBLE);
                            layout_traindetails_start.setText("开始训练");
                            break;
                        default:
                            singleDownDialog.dialogDismiss();
                            break;
                    }
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
            CallServer.getDownloadInstance().cancelAll();
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

    @OnClick(R.id.tv_chuji)
    public void checkChuji() {
        tv_tishi.setText("适合足球基础差,零基础的初学者");
        tv_chuji.setTextColor(0xffffffff);
        tv_chuji.setBackgroundColor(getResourcesColor(R.color.gold));
        tv_zhongji.setBackgroundColor(0xffffffff);
        tv_zhongji.setTextColor(getResourcesColor(R.color.gold));
        tv_gaoji.setBackgroundColor(0xffffffff);
        tv_gaoji.setTextColor(getResourcesColor(R.color.gold));
        tab = 1;
        AppCartoon.Videos videos1 = selectVideo("0");
        if (videos1 != null) {
            String path = FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/";
            String name = getFileName(videos1.download_video_url).replace(".zip", "");
            if (FileUtil.fileExists(path + name)) {
                layout_traindetails_start.setText("开始训练");
            } else {
                layout_traindetails_start.setText("下载训练(" + checkSize(Integer.parseInt(videos1.video_size)) + ")");
            }
        }
    }

    @OnClick(R.id.tv_zhongji)
    public void checkZhongji() {
        tv_tishi.setText("适合身体尚可,有一定的基础的训练者");
        tv_zhongji.setTextColor(0xffffffff);
        tv_zhongji.setBackgroundColor(getResourcesColor(R.color.gold));
        tv_chuji.setBackgroundColor(0xffffffff);
        tv_chuji.setTextColor(getResourcesColor(R.color.gold));
        tv_gaoji.setBackgroundColor(0xffffffff);
        tv_gaoji.setTextColor(getResourcesColor(R.color.gold));
        tab = 2;
        AppCartoon.Videos videos1 = selectVideo("1");
        if (videos1 != null) {
            String path = FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/";
            String name = getFileName(videos1.download_video_url).replace(".zip", "");
            if (FileUtil.fileExists(path + name)) {
                layout_traindetails_start.setText("开始训练");
            } else {
                layout_traindetails_start.setText("下载训练(" + checkSize(Integer.parseInt(videos1.video_size)) + ")");
            }
        }
    }

    @OnClick(R.id.tv_gaoji)
    public void checkGaoji() {
        tv_tishi.setText("适合身体素质强大,有一定基础的训练者");
        tv_gaoji.setTextColor(0xffffffff);
        tv_gaoji.setBackgroundColor(getResourcesColor(R.color.gold));
        tv_zhongji.setBackgroundColor(0xffffffff);
        tv_zhongji.setTextColor(getResourcesColor(R.color.gold));
        tv_chuji.setBackgroundColor(0xffffffff);
        tv_chuji.setTextColor(getResourcesColor(R.color.gold));
        tab = 3;
        AppCartoon.Videos videos1 = selectVideo("2");
        if (videos1 != null) {
            String path = FileUtil.getDecompressionDir(getThis()) + appCartoon.name + "/";
            String name = getFileName(videos1.download_video_url).replace(".zip", "");
            if (FileUtil.fileExists(path + name)) {
                layout_traindetails_start.setText("开始训练");
            } else {
                layout_traindetails_start.setText("下载训练(" + checkSize(Integer.parseInt(videos1.video_size)) + ")");
            }
        }

    }

    @OnClick(R.id.linear_zhenren)
    public void download_zhenren() {
        Intent intent = new Intent(getThis(), ZhenRenVodeoActivity.class);
        intent.putStringArrayListExtra("info", (ArrayList<String>) appCartoon.youku_videos);
        startActivity(intent);
    }

    @OnClick(R.id.image_help)
    public void help() {
        Intent intent = new Intent(getThis(), BigClassHelp.class);
        intent.putExtra("info", appCartoon);
        startActivity(intent);
    }

    @OnClick(R.id.image_back)
    public void back() {
        finish();
    }

}
