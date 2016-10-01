package com.kt.ktball.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.frame.app.utils.LogUtils;
import com.ktfootball.app.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jy on 16/6/4.
 */
public class RoundBallView extends ViewGroup {

    private int w;
    private int h;
    int roto = 180; //现在的角度
    int mAngleOffset = 0; //要偏移角度
    private ImageView iv;
    private final int RIGHT_ROTATION = 101;
    private final int LEFT_ROTATION = 102;
    private final int TIME_INTERVALS = 2;//动画的时间间隔
    private Timer timer;
    private TimerTask timerTask;
    private boolean isRotation = false; //是否在旋转
    private long downTime; //手指按下的时间
    private long upTime; //手指抬起的时间
    private IRoundBallViewScrollListener listener;
    int code = 2;

    int model = R.drawable.ball;

    public RoundBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundBallView(Context context) {
        super(context);
    }

    public RoundBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init1();
        View img = getChildAt(0);
        img.measure(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        int screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
        int screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        LogUtils.e("screenWidth = " + screenWidth + "...screenHeight = " + screenHeight);
        View img = getChildAt(0);
        int left = l - screenWidth;
        int top = (int) (screenHeight - (3 * (float) screenWidth / 2));
        int right = r + screenWidth;
        int bottom = (int) (screenHeight + (3 * (float) screenWidth / 2));
        LogUtils.e("left = " + left + "...top = " + top + "...right = " + right + "...bottom = " + bottom);
        img.layout(left, top, right, bottom);
    }

    public Handler mHandler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case RIGHT_ROTATION:
                    imageViewRotation(++roto);
                    break;
                case LEFT_ROTATION:
                    imageViewRotation(--roto);
                    break;
            }
            code = getCode(roto);
            LogUtils.e(code + "");
        }
    };

    private int getCode(int roto) {
        int x = roto / 90 % 4;
        LogUtils.e(x+"");
        if (x > 0) {
            x = Math.abs(x);
            switch (x) {
                case 1:
                    x =  3;
                    break;
                case 2:
                    x =   2;
                    break;
                case 3:
                    x =   1;
                    break;
                case 0:
                    x =   0;
                    break;
            }
        }else{
            x =  Math.abs(x);
        }
        x =  Math.abs(x);
        return x;
    }

    private void init1() {
        int size = 2000;
        setBackgroundResource(R.drawable.world_select02);
        Bitmap b = zoom(size);
        w = b.getWidth();
        h = b.getHeight();
        iv = (ImageView) getChildAt(0);
        iv.setImageBitmap(b);
        imageViewRotation(roto);
        iv.setOnTouchListener(new OnTouchListener() {

            private float distance;
            float x = 0;
            float xx = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downTime = System.currentTimeMillis();
                        x = event.getRawX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        xx = event.getRawX();
                        distance = xx - x;
                        LogUtils.e("distance =  " + distance);
                        upTime = System.currentTimeMillis();
                        if (!isRotation) {
                            mAngleOffset = 90;
                            isRotation = true;
                            if (distance < -10) {
                                leftRotation();
                                LogUtils.e("left");
                            } else if (distance > 10) {
                                LogUtils.e("right");
                                rightRotation();
                            } else {
                                if (listener != null) {
                                    listener.startActivity(code);
                                }
                                isRotation = false;
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void rightRotation() {
        ballRotation(RIGHT_ROTATION);
    }

    private void leftRotation() {
        ballRotation(LEFT_ROTATION);
    }

    private void ballRotation(final int rotation) {
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (mAngleOffset == 0) {
                        stopTiming();
                        LogUtils.e(iv.getRotation() + "");
                        isRotation = false;
                    } else {
                        mHandler.sendEmptyMessage(rotation);
                        --mAngleOffset;
                    }
                }
            };
        }
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(timerTask, TIME_INTERVALS, TIME_INTERVALS);
    }

    private void imageViewRotation(float x) {
        iv.setRotation(x);
    }

    private void stopTiming() {
        //被调用之后整个Timer的线程都会结束掉,不调用timer.cancel()的话timerTask线程会一直被执行,调用timer.cancel()的话，timerTask也会执行完当次之后才不会继续执行。
//        timer.cancel();
        //方法用于从这个计时器的任务队列中移除所有已取消的任务
//        timer.purge();

        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null) {
            timer.cancel(); // Cancel timer
            timer.purge();
            timer = null;
        }
    }

    private Bitmap zoom(int w) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;//只获取图片的属性
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), model, opts);
        //图片的宽高
        int width = opts.outWidth;
        int height = opts.outHeight;
        LogUtils.e("w = " + width + "...h = " + height);
//        double scale = 3;
        double scale = (double) width / w;
        LogUtils.e(scale + "");
        opts.inSampleSize = (int) scale;
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(getResources(), model, opts);
        LogUtils.e("w = " + bitmap.getWidth() + "...h = " + bitmap.getHeight());
        return bitmap;
    }

    public void setScrollListener(IRoundBallViewScrollListener listener) {
        this.listener = listener;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }
}
