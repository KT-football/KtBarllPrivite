package com.ktfootball.app.Utils;

import android.graphics.Color;

/**
 * Created by jy on 16/6/16.
 */
public class CommonUtils {

    public static int[] getTrainColor(String colorStr){
        int[] colcr = null;
        if (colorStr.equals("绿")) {
            colcr = new int[]{
                    Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN};
        } else if (colorStr.equals("白")) {
            colcr = new int[]{
                    Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
        } else if (colorStr.equals("紫")) {
            colcr = new int[]{
                    Color.parseColor("#a63bd8"), Color.parseColor("#a63bd8"), Color.parseColor("#a63bd8"), Color.parseColor("#a63bd8")};
        }else if (colorStr.equals("蓝")) {
            colcr = new int[]{
                    Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE};
        }else if (colorStr.equals("红")) {
            colcr = new int[]{
                    Color.RED, Color.RED, Color.RED, Color.RED};
        }else if (colorStr.equals("黄")) {
            colcr = new int[]{
                    Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW};
        }
        return colcr;
    }
}
