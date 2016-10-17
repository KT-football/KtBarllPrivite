package com.ktfootball.app.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by leo on 16/10/14.
 */

public class Util {
    public static int voll(int i, int b) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        String result = numberFormat.format((float) i / (float) b * 100);
        return Integer.parseInt(result);
    }
}
