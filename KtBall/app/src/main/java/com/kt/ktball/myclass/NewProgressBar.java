package com.kt.ktball.myclass;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.ktfootball.app.R;

/**
 * Created by Administrator on 2016/4/6.
 */
public class NewProgressBar extends View {

    private Paint paint;
    private int buttonColor;
    private int outsideColor;
    private int centreColor;
    private int progressColorS;
    private int progressColorP;
    private int progressColorF;
    private int max;
    private float buttonWidth;
    private int progressS;
    private int progressP;
    private int progressF;
    private int start = 36;
    private int lenCentre = 2;
    private int lenOutside = 1;



    public NewProgressBar(Context context) {
        this(context, null);
    }

    public NewProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();


        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.newProgressBar);

        buttonColor = typedArray.getColor(R.styleable.newProgressBar_buttonColor, Color.BLUE);
        outsideColor = typedArray.getColor(R.styleable.newProgressBar_outsideColor, Color.BLUE);
        centreColor = typedArray.getColor(R.styleable.newProgressBar_centreColor, Color.BLUE);
        progressColorS = typedArray.getColor(R.styleable.newProgressBar_progressColorS, Color.BLUE);
        progressColorP = typedArray.getColor(R.styleable.newProgressBar_progressColorP, Color.BLUE);
        progressColorF = typedArray.getColor(R.styleable.newProgressBar_progressColorF, Color.BLUE);
        max = typedArray.getInt(R.styleable.newProgressBar_buttonMax, 100);
        buttonWidth = typedArray.getDimension(R.styleable.newProgressBar_buttonWidth, 36);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        //画最外层矩形
        paint.setStrokeWidth(0);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(outsideColor);
        canvas.drawRect(start -(lenOutside+ lenCentre), start - (lenOutside+lenCentre),
                width - start + (lenOutside+ lenCentre), start + buttonWidth + (lenOutside+ lenCentre), paint);

        //画黑色矩形
        paint.setStrokeWidth(lenCentre);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(centreColor);
        canvas.drawRect(start, start,
                width - start, start + buttonWidth, paint);

        //画矩形Button
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(buttonColor);
        canvas.drawRect(start, start,
                width - start, start + buttonWidth, paint);

        //文字和颜色


        //画胜利的进度
        paint.setColor(progressColorS);
        paint.setStrokeWidth(buttonWidth);
        paint.setStyle(Paint.Style.FILL);
        if (progressS + progressP + progressF != 0) {
            RectF ovals = new RectF(start, start,
                    (width - 2 * start) * progressS / (progressS + progressP + progressF) + start, start + buttonWidth);
            canvas.drawRect(ovals, paint);
        } else {

        }

        //画平局的进度
        paint.setColor(progressColorP);
        paint.setStrokeWidth(buttonWidth);
        paint.setStyle(Paint.Style.FILL);
        if (progressS + progressP + progressF != 0) {
            RectF ovalp = new RectF((width - 2 * start) * progressS / (progressS + progressP + progressF) + start, start,
                    (width - 2 * start) * (progressP + progressS) / (progressS + progressP + progressF) + start, start + buttonWidth);
            canvas.drawRect(ovalp, paint);
        } else {

        }

        //画输球的进度
        paint.setColor(progressColorF);
        paint.setStrokeWidth(buttonWidth);
        paint.setStyle(Paint.Style.FILL);
        if (progressS + progressP + progressF != 0) {
            RectF ovalf = new RectF((width - 2 * start) * (progressP + progressS) / (progressS + progressP + progressF) + start, start,
                    (width - 2 * start) + start, start + buttonWidth);
            canvas.drawRect(ovalf, paint);
        } else {

        }
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
    }

    public int getProgressColorS() {
        return progressColorS;
    }

    public void setProgressColorS(int progressColorS) {
        this.progressColorS = progressColorS;
    }

    public int getProgressColorP() {
        return progressColorP;
    }

    public void setProgressColorP(int progressColorP) {
        this.progressColorP = progressColorP;
    }

    public int getProgressColorF() {
        return progressColorF;
    }

    public void setProgressColorF(int progressColorF) {
        this.progressColorF = progressColorF;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public float getButtonWidth() {
        return buttonWidth;
    }

    public void setButtonWidth(float buttonWidth) {
        this.buttonWidth = buttonWidth;
    }

    public synchronized int getProgressS() {
        return progressS;
    }

    public synchronized void setProgressS(int progressS) {
        this.progressS = progressS;
        postInvalidate();
    }

    public synchronized int getProgressP() {
        return progressP;
    }

    public synchronized void setProgressP(int progressP) {
        this.progressP = progressP;
        postInvalidate();
    }

    public synchronized int getProgressF() {
        return progressF;
    }

    public synchronized void setProgressF(int progressF) {
        this.progressF = progressF;
        postInvalidate();
    }
}
