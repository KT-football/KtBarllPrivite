package com.kt.ktball.myclass;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.ktfootball.app.R;


/**
 * Created by Administrator on 2016/4/7.
 */
public class SpiderProgressBar extends View {

    private Paint paint;
    private int progressBarColor;
    private double winning = 1;
    private double troughLegs = 1;
    private double goal = 1;
    private double kt = 1;
    private double score = 1;
    private double max = 6;


    private int start;
    private float radius;

    public SpiderProgressBar(Context context) {
        this(context, null);
    }

    public SpiderProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiderProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint();
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.spiderProgressBar);

        progressBarColor = typedArray.getColor(R.styleable.spiderProgressBar_spiderProgressBarColor, Color.BLUE);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int center = width / 2;
        start = center / 4;
        radius = center - start;

        //画蜘蛛网
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        float endX;
        float endY;
        float smallX;
        float smallY;

        for (int i = 0; i < max - 1; ++i) {

            for (int j = 0; j < max - 1; ++j) {
                smallX = radius / (float) max * (j + 1) * (float) Math.cos(Math.PI / 180 * i * (360 / (max - 1)));
                smallY = radius / (float) max * (j + 1) * (float) Math.sin(Math.PI / 180 * i * (360 / (max - 1)));

                smallX += center;
                smallY += center;

                if (i == 4) {
                    canvas.drawLine(smallX, smallY,
                            radius / (float) max * (j + 1) * (float) Math.cos(Math.PI / 180 * 0 * (360 / (max - 1))) + center,
                            radius / (float) max * (j + 1) * (float) Math.sin(Math.PI / 180 * 0 * (360 / (max - 1))) + center,
                            paint);
                } else {
                    canvas.drawLine(smallX, smallY,
                            radius / (float) max * (j + 1) * (float) Math.cos(Math.PI / 180 * (i + 1) * (360 / (max - 1))) + center,
                            radius / (float) max * (j + 1) * (float) Math.sin(Math.PI / 180 * (i + 1) * (360 / (max - 1))) + center,
                            paint);
                }
            }

            endX = radius * (float) Math.cos(Math.PI / 180 * i * (360 / (max - 1)));
            endY = radius * (float) Math.sin(Math.PI / 180 * i * (360 / (max - 1)));

            endX += center;
            endY += center;

            if (i == 4) {
                canvas.drawLine(endX, endY,
                        radius * (float) Math.cos(Math.PI / 180 * 0 * (360 / (max - 1))) + center,
                        radius * (float) Math.sin(Math.PI / 180 * 0 * (360 / (max - 1))) + center,
                        paint);
            } else {
                canvas.drawLine(endX, endY,
                        radius * (float) Math.cos(Math.PI / 180 * (i + 1) * (360 / (max - 1))) + center,
                        radius * (float) Math.sin(Math.PI / 180 * (i + 1) * (360 / (max - 1))) + center,
                        paint);
            }
            canvas.drawLine(center, center, endX, endY, paint);
        }

        //画进度

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(progressBarColor);
        Path path = new Path();
        if (winning > 0 || troughLegs > 0 || goal > 0 || kt > 0 || score > 0) {
            path.moveTo(radius / (float) max * (float) winning * (float) Math.cos(Math.PI / 180 * 0 * (360 / (max - 1))) + center,
                    radius / (float) max * (float) winning * (float) Math.sin(Math.PI / 180 * 0 * (360 / (max - 1))) + center);//起点
            path.lineTo(radius / (float) max * (float) troughLegs * (float) Math.cos(Math.PI / 180 * 1 * (360 / (max - 1))) + center,
                    radius / (float) max * (float) troughLegs * (float) Math.sin(Math.PI / 180 * 1 * (360 / (max - 1))) + center);
            path.lineTo(radius / (float) max * (float) goal * (float) Math.cos(Math.PI / 180 * 2 * (360 / (max - 1))) + center,
                    radius / (float) max * (float) goal * (float) Math.sin(Math.PI / 180 * 2 * (360 / (max - 1))) + center);
            path.lineTo(radius / (float) max * (float) kt * (float) Math.cos(Math.PI / 180 * 3 * (360 / (max - 1))) + center,
                    radius / (float) max * (float) kt * (float) Math.sin(Math.PI / 180 * 3 * (360 / (max - 1))) + center);
            path.lineTo(radius / (float) max * (float) score * (float) Math.cos(Math.PI / 180 * 4 * (360 / (max - 1))) + center,
                    radius / (float) max * (float) score * (float) Math.sin(Math.PI / 180 * 4 * (360 / (max - 1))) + center);
            path.close();//封闭
            canvas.drawPath(path, paint);
        }

//        canvas.drawText(canvas,"进球",12+radius / (float) max * (float) goal * (float) Math.cos(Math.PI / 180 * 2 * (360 / (max - 1))) + center,
//                12+radius / (float) max * (float) goal * (float) Math.sin(Math.PI / 180 * 2 * (360 / (max - 1))) + center,paint,(float)360/5*2);

//        float textWidth = paint.measureText("进球");
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        paint.setTextSize(20);


        drawText(canvas,"胜率",radius * (float) Math.cos(Math.PI / 180 * 0 * (360 / (max - 1))) + center+6,
                radius * (float) Math.sin(Math.PI / 180 * 0 * (360 / (max - 1))) + center-16,
                paint,360/5+25);

        drawText(canvas,"穿裆",radius * (float) Math.cos(Math.PI / 180 * 1 * (360 / (max - 1))) + center+18,
                radius * (float) Math.sin(Math.PI / 180 * 1 * (360 / (max - 1))) + center,
                paint,360/5*2+25);

        drawText(canvas," 进球",radius * (float) Math.cos(Math.PI / 180 * 2 * (360 / (max - 1))) + center+6 ,
                radius * (float) Math.sin(Math.PI / 180 * 2 * (360 / (max - 1))) + center+24,
                paint,360/5*3+25);

        drawText(canvas,"KT",radius * (float) Math.cos(Math.PI / 180 * 3 * (360 / (max - 1))) + center-16,
                radius * (float) Math.sin(Math.PI / 180 * 3 * (360 / (max - 1))) + center+12,
                paint,360/5*4+20);

        drawText(canvas,"得分 ",radius * (float) Math.cos(Math.PI / 180 * 4 * (360 / (max - 1))) + center-16,
                radius * (float) Math.sin(Math.PI / 180 * 4 * (360 / (max - 1))) + center-12,
                paint,360+20);
    }

    void drawText(Canvas canvas ,String text , float x ,float y,Paint paint ,float angle){
        if(angle != 0){
            canvas.rotate(angle, x, y);
        }
        canvas.drawText(text, x, y, paint);
        if(angle != 0){
            canvas.rotate(-angle, x, y);
        }
    }
    public int getProgressBarColor() {
        return progressBarColor;
    }

    public void setProgressBarColor(int progressBarColor) {
        this.progressBarColor = progressBarColor;
        postInvalidate();
    }

    public double getWinning() {
        return winning;
    }

    public void setWinning(double winning) {
        this.winning = winning;
        postInvalidate();
    }

    public double getTroughLegs() {
        return troughLegs;
    }

    public void setTroughLegs(double troughLegs) {
        this.troughLegs = troughLegs;
        postInvalidate();
    }

    public double getGoal() {
        return goal;
    }

    public void setGoal(double goal) {
        this.goal = goal;
        postInvalidate();
    }

    public double getKt() {
        return kt;
    }

    public void setKt(double kt) {
        this.kt = kt;
        postInvalidate();
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
        postInvalidate();
    }

}
