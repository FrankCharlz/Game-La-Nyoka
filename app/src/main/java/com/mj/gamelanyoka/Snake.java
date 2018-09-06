package com.mj.gamelanyoka;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Frank on 06-Sep-18.
 */

class Snake {

    private int score;
    private ScoreListener scoreListener;

    private SnakePart getTail() {
        return parts.get(0);
    }

    private SnakePart getHead() {
        return parts.get(parts.size() - 1);
    }

    public int getScore() {
        return score;
    }

    public void setScoreListener(ScoreListener scoreListener) {
        this.scoreListener = scoreListener;
    }

    public enum Direction {UP, DOWN, LEFT, RIGHT}
    private ArrayList<SnakePart> parts;
    private Direction direction;
    private float speed = SnakePart.size;


    public Snake() {

        parts = new ArrayList<>(5);


        float cx = Dimensions.width/2f;
        float cy = Dimensions.height/2f;

        float size = SnakePart.size;
        float x0 = cx - 2.5f * size;

        for (int i = 0; i < 5; i++) {
            SnakePart part = new SnakePart();
            part.left = x0;
            part.right = part.left + size;

            part.top = cy + size/2f;
            part.bottom = part.top - size;

            x0 += size;

            parts.add(part);
        }



    }

    public void draw(Canvas canvas) {

        Iterator iterator = parts.iterator();

        while (iterator.hasNext()) {
            ((SnakePart) iterator.next()).doDraw(canvas);
        }

        TundaRewards.draw(canvas);

        move();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private void move() {

       ArrayList<Position> positions = new ArrayList<>();

        for (int i = 0; i < parts.size(); i++) {
            positions.add(
                    new Position(
                            parts.get(i).top,
                            parts.get(i).left
                    )
            );
        }

        Position lastPart = positions.remove(0);
        Position headPosition;
        SnakePart headPart = getHead();

        switch (direction) {
            case UP:
                headPosition = new Position(headPart.top - speed,headPart.left);
                break;

            case DOWN:
                headPosition = new Position(headPart.top + speed, headPart.left);
                break;

            case LEFT:
                headPosition = new Position(headPart.top, headPart.left - speed);
                break;

            case RIGHT:
                headPosition = new Position(headPart.top, headPart.left + speed);
                break;

            default:
                headPosition = new Position(0,0);
                break;

        }

        positions.add(headPosition);

        for (int i = 0; i < parts.size(); i++) {
            parts.get(i).pos(positions.get(i));
        }

        if (TundaRewards.eaten(headPart)) {

            Log.d("NYOKA", "move: EATEN");
            TundaRewards.refreshTunda();

            grow(lastPart);
            score+=7;
            scoreListener.newScore(score);

        }

    }

    public void grow(Position position) {
        SnakePart s = new SnakePart();
        s.pos(position);
        parts.add(s);
    }

    public static interface ScoreListener {
        void newScore(int score);
    }
}







