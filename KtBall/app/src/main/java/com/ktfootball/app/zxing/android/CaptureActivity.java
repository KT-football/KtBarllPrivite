package com.ktfootball.app.zxing.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.frame.app.base.activity.BaseToolBarActivity2;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.ktfootball.app.Constants;
import com.ktfootball.app.R;
import com.ktfootball.app.zxing.camera.CameraManager;
import com.ktfootball.app.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 这个activity打开相机，在后台线程做常规的扫描；它绘制了一个结果view来帮助正确地显示条形码，在扫描的时候显示反馈信息，
 * 然后在扫描成功的时候覆盖扫描结果
 */
public final class CaptureActivity extends BaseToolBarActivity2 implements
        SurfaceHolder.Callback {

    // 相机控制
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private IntentSource source;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    // 电量控制
    private InactivityTimer inactivityTimer;
    // 声音、震动控制
    private BeepManager beepManager;
    private int imageViewCode;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    @Override
    public void onResume() {
        super.onResume();

        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        handler = null;

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；
            // surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 重置callback，等待surfaceCreated()来初始化camera
            surfaceHolder.addCallback(this);
        }

        beepManager.updatePrefs();
        inactivityTimer.onResume();

        source = IntentSource.NONE;
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    public void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        beepManager.close();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    protected void initHandler(Message msg) {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        imageViewCode = getIntent().getIntExtra(Constants.IMAGEVIEW_CODE, 0);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        // 保持Activity处于唤醒状态
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        addContentView(R.layout.capture);

        hasSurface = false;

        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    /**
     * 扫描成功，处理反馈信息
     *
     * @param rawResult
     * @param barcode
     * @param scaleFactor
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();

        boolean fromLiveScan = barcode != null;
        //这里处理解码完成后的结果，此处将参数回传到Activity处理
        if (fromLiveScan) {
            beepManager.playBeepSoundAndVibrate();

//            Intent intent = getIntent();
//            intent.putExtra(Constants.DECODED_CONTENT_KEY, rawResult.getText());
//            intent.putExtra("codedBitmap", barcode);
//            setResult(Constants.CAPTUREACTIVITY_RESULT_OK, intent);


            Intent intentUser = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.DECODED_CONTENT_KEY, rawResult.getText());
            bundle.putInt(Constants.TWO_IMAGEVIEW_CODE, imageViewCode);
            intentUser.putExtra("bundle", bundle);
            setResult(Constants.CAPTUREACTIVITY_RESULT_OK, intentUser);
            finish();
        }

//        inactivityTimer.onActivity();
//        playBeepSoundAndVibrate();
//        String recode = result.toString();
//        switch (code) {
//            case 1:
//                doBagsScan(recode);
//                break;
//            case 2:
//                doUserScan(recode);
//                break;
//            case 3:
//                doAddClassScanUser(recode);
//        }

    }

//    private void doAddClassScanUser(String result) {
//        Intent intentAddClassUser = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString(BUNDLE_SCAN_RESULT, result);
//        intentAddClassUser.putExtra("bundle", bundle);
//        intentAddClassUser.setClass(CaptureActivity.this, ListActivity.class);
//        setResult(3, intentAddClassUser);
//        finish();
//    }
//
//    private void doUserScan(String rawResult) {
//        imageViewCode = intent.getIntExtra(KtActivity.IMAGEVIEW_CODE, 0);
//        Intent intentUser = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString(USER_SCAN_RESULT, rawResult);
//        bundle.putInt(TWO_IMAGEVIEW_CODE, imageViewCode);
//        intentUser.putExtra("bundle", bundle);
//        LogUtils.e(bundle.toString());
//        intentUser.setClass(CaptureActivity.this, KtActivity.class);
//        setResult(2, intentUser);
//        finish();
//    }

//    private void doBagsScan(String rawResult) {

//        List<com.ktfootball.www.dao.Bags> bagsList = BagsDaoHelper.getInstance().getAllData();
//        ArrayList<String> list = new ArrayList<>();
//        for (com.ktfootball.www.dao.Bags bags : bagsList) {
//            String code = bags.getCode();
//            list.add(code);
//        }
//        Intent intentBag = new Intent();
//        intentBag.setClass(CaptureActivity.this, BagsScannerActivity.class);
//        if (list.contains(rawResult)) {
//            PreferenceManager.getDefaultSharedPreferences(CaptureActivity.this)
//                    .edit()
//                    .putString(PRE_CURRENT_TWO_DIMENSION_CODE, rawResult)
//                    .commit();
//            intentBag.putExtra(BAGS_SCAN_RESULT, "ok");
//        } else {
//            intentBag.putExtra(BAGS_SCAN_RESULT, "no");
//        }
//        setResult(1, intentBag);
//        finish();
//    }

    /**
     * 初始化Camera
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats,
                        decodeHints, characterSet, cameraManager);
            }
        } catch (IOException ioe) {
            Log.w(getTAG(), ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            Log.w(getTAG(), "Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }
    }

    /**
     * 显示底层错误信息并退出应用
     */
    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(R.string.button_ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    @Override
    protected void initToolBar() {
        setToolBarTitle("二维码扫描");
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }
}
