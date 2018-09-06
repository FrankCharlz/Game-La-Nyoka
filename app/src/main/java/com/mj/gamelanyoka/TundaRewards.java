package com.mj.gamelanyoka;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

/**
 * Created by Frank on 06-Sep-18.
 */

class TundaRewards {

    private static RectF tunda;
    static Random random = new Random();

    static {
        tunda = makeTunda();
    }

    private static RectF makeTunda() {
        int top = random.nextInt(Dimensions.height);
        int left = random.nextInt(Dimensions.width);

        RectF rect = new RectF();
        rect.top = top;
        rect.bottom = (int) (top - SnakePart.size);

        rect.left = left;
        rect.right = (int) (left + SnakePart.size);
        Log.d("TUNDA", "makeTunda: " + rect.toShortString());
        return  rect;
    }


    public static boolean eaten(SnakePart headPart) {
        //OR: distance between centers is less than size;
        return headPart.contains(tunda.centerX(), tunda.centerY());
    }

    public static void draw(Canvas canvas) {
        canvas.drawCircle(tunda.centerX(), tunda.centerY(), (tunda.right - tunda.left)/2.27f, DopePaints.getOne(5));
    }

    public static void refreshTunda() {
        tunda = makeTunda();
    }
}
