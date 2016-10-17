package com.ktfootball.app.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ktfootball.app.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by leo on 16/10/16.
 */

public class MyCirCleView extends View {


    private int mMaxProgress = 100;

    private int mProgress = 30;

    private int mProgeress1 = 20;

    private int mProgeress2 = 50;


    private final int mCircleLineStrokeWidth = 100;

    private final int mTxtStrokeWidth = 2;

    // 画圆所在的距形区域
    private final RectF mRectF;

    private final Paint mPaint;

    private final Context mContext;

    private String mTxtHint1;

    private String mTxtHint2;

    public MyCirCleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;
        mRectF = new RectF();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getWidth();
        int height = this.getHeight();

        if (width != height) {
            int min = Math.min(width, height);
            width = min;
            height = min;
        }

        // 设置画笔相关属性
        mPaint.setAntiAlias(true);
        mPaint.setColor(0x4cffffff);
        canvas.drawColor(Color.TRANSPARENT);
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        // 位置
        mRectF.left = mCircleLineStrokeWidth / 2; // 左上角x
        mRectF.top = mCircleLineStrokeWidth / 2; // 左上角y
        mRectF.right = width - mCircleLineStrokeWidth / 2; // 左下角x
        mRectF.bottom = height - mCircleLineStrokeWidth / 2; // 右下角y

        // 绘制圆圈，进度条背景
        canvas.drawArc(mRectF, -90, 360, false, mPaint);
        mPaint.setColor(0xffFFD669);
        canvas.drawArc(mRectF, -90, ((float) mProgress / mMaxProgress) * 360, false, mPaint);

        //===================================
//        mPaint.setStrokeWidth(mTxtStrokeWidth);
//        String text = "胜";
//        mPaint.setTextSize(40);
//        mPaint.setColor(0xff000000);
//        mPaint.setStyle(Paint.Style.FILL);
//        int textWidth = (int) mPaint.measureText(text, 0, text.length());
//        int textHeight = height / 4;
//        double  cos = Math.cos(((180 - (((float) mProgress / mMaxProgress)) * 360) /180f)*Math.PI);
//        double textX = ((float) mProgress / mMaxProgress) * 360-90<180?(width/2+(width/2)*cos):(width/2)*cos;
//        canvas.drawText(text, (float) textX-textWidth/2, ((float) mProgress / mMaxProgress * height ) + textHeight / 2, mPaint);
        //===================================

        // 进度1
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff37E3CC);
        canvas.drawArc(mRectF, (((float) mProgress / mMaxProgress) * 360) - 90, ((float) mProgeress1 / mMaxProgress) * 360, false, mPaint);


        //===================================
//        mPaint.setStrokeWidth(mTxtStrokeWidth);
//        text = "负";
//        mPaint.setTextSize(40);
//        mPaint.setColor(0xff000000);
//        mPaint.setStyle(Paint.Style.FILL);
//        double  cos1 = Math.cos(((180 - (((float) mProgeress1 / mMaxProgress)) * 360) /180f)*Math.PI);
//        double textX1 = ((float) mProgeress1 / mMaxProgress) * 360-90<180?(width/2+(width/2)*cos1):(width/2)*cos1;
//        canvas.drawText(text, (float) textX1-textWidth/2, ((float) (mProgeress1+mProgress) / mMaxProgress * height ) + textHeight / 2, mPaint);
        //===================================

        //进度二
        mPaint.setStrokeWidth(mCircleLineStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff67B7FF);
        canvas.drawArc(mRectF, (((float) (mProgress + mProgeress1) / mMaxProgress) * 360) - 90, ((float) mProgeress2 / mMaxProgress) * 360, false, mPaint);

        //===================================
//        mPaint.setStrokeWidth(mTxtStrokeWidth);
//        text = "平";
//        mPaint.setTextSize(40);
//        mPaint.setColor(0xff000000);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, mPaint);
        //===================================


//        // 绘制进度文案显示
//        mPaint.setStrokeWidth(mTxtStrokeWidth);
//        String text = mProgress + "%";
//        int textHeight = height / 4;
//        mPaint.setTextSize(textHeight);
//        int textWidth = (int) mPaint.measureText(text, 0, text.length());
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawText(text, width / 2 - textWidth / 2, height / 2 + textHeight / 2, mPaint);
//
//        if (!TextUtils.isEmpty(mTxtHint1)) {
//            mPaint.setStrokeWidth(mTxtStrokeWidth);
//            text = mTxtHint1;
//            textHeight = height / 8;
//            mPaint.setTextSize(textHeight);
//            mPaint.setColor(Color.rgb(0x99, 0x99, 0x99));
//            textWidth = (int) mPaint.measureText(text, 0, text.length());
//            mPaint.setStyle(Paint.Style.FILL);
//            canvas.drawText(text, width / 2 - textWidth / 2, height / 4 + textHeight / 2, mPaint);
//        }
//
//        if (!TextUtils.isEmpty(mTxtHint2)) {
//            mPaint.setStrokeWidth(mTxtStrokeWidth);
//            text = mTxtHint2;
//            textHeight = height / 8;
//            mPaint.setTextSize(textHeight);
//            textWidth = (int) mPaint.measureText(text, 0, text.length());
//            mPaint.setStyle(Paint.Style.FILL);
//            canvas.drawText(text, width / 2 - textWidth / 2, 3 * height / 4 + textHeight / 2, mPaint);
//        }
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setProgress(int progress,int progress1) {
        this.mProgress = progress;
        this.mProgeress1 = progress1;
        this.mProgeress2 = 100 - progress - progress1;
        this.invalidate();
    }

    public void setProgressNotInUiThread(int progress) {
        this.mProgress = progress;
        this.postInvalidate();
    }

    public String getmTxtHint1() {
        return mTxtHint1;
    }

    public void setmTxtHint1(String mTxtHint1) {
        this.mTxtHint1 = mTxtHint1;
    }

    public String getmTxtHint2() {
        return mTxtHint2;
    }

    public void setmTxtHint2(String mTxtHint2) {
        this.mTxtHint2 = mTxtHint2;
    }

    public void setTimerProgress(final int position) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = 0;

            @Override
            public void run() {
                i += 1;
                setProgressNotInUiThread(i);
                if (i == position) {
                    timer.cancel();
                }
            }
        }, 0, 50);

    }

    private void drayText() {

    }
}

