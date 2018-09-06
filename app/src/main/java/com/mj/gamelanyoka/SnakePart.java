package com.mj.gamelanyoka;

import android.graphics.Canvas;
import android.graphics.RectF;

/**
 * Created by Frank on 06-Sep-18.
 */

public class SnakePart extends RectF {

    public static float size = 36f;


    public void doDraw(Canvas canvas) {
        canvas.drawRect(this, DopePaints.getOne(2));
    }

    public void moveTo(SnakePart snakePart) {
        this.pos(new Position(snakePart.top, snakePart.left));
    }

    public void pos(Position position) {
        this.top = position.getTop();
        this.left = position.getLeft();

        this.bottom = this.top + size;
        this.right = this.left + size;
    }
}
