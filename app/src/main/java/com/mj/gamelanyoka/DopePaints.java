package com.mj.gamelanyoka;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Frank on 12-Aug-18.
 */

public class DopePaints {

    private static Paint paints[];

    private static int[] colors = new int[] {
            Color.argb(200, 232, 190, 99),
            Color.argb(200, 112, 222, 229),
            Color.argb(220, 22, 22, 29),
            Color.argb(230, 212, 222, 229),
            Color.argb(230, 22, 22, 229),
            Color.RED,
            Color.GREEN,
            Color.YELLOW,
            Color.MAGENTA
    };


    private static final float DEFAULT_STROKE_WITH = 1f;

    public static final Paint lPaint;

    static {

        paints = new Paint[colors.length];

        for (int i = 0; i < colors.length; i++) {
            paints[i] = new Paint();
            paints[i].setStyle(Paint.Style.FILL_AND_STROKE);
            paints[i].setStrokeWidth(DEFAULT_STROKE_WITH);
            paints[i].setColor(colors[i]);

            paints[i].setAlpha(120);
            paints[i].setTextSize(22f);
        }

        lPaint = new Paint();
        lPaint.setStyle(Paint.Style.STROKE);
        lPaint.setStrokeWidth(2);
        lPaint.setColor(Color.BLACK);
        lPaint.setAlpha(160);
        lPaint.setTextSize(34f);
    }



    static Paint getOneRandom() {
        return paints[new Random().nextInt(paints.length -1 )];
    }
    static Paint getOne(int index) {
        return paints[index%paints.length];
    }

    public static int[] getColors() {
        return colors;
    }
}
