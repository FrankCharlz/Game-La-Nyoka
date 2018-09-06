package com.mj.gamelanyoka;

import android.support.v7.app.AppCompatActivity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final long SLEEP_INTERVAL = 1200L;
    private SurfaceView surface;
    private LinearLayout inkContainer;
    private TextView tvPen, tvScore;
    private SurfaceHolder holder;
    private boolean nichore;
    private GameThread gameThread;
    private Snake snake;
    private boolean paused;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInks();


        surface = findViewById(R.id.surface);

        holder = surface.getHolder();
        holder.addCallback(this);


        //calculating screen width, then save it statically
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Dimensions.height = displayMetrics.heightPixels;
        Dimensions.width = displayMetrics.widthPixels;

        snake = new Snake();
        snake.setDirection(Snake.Direction.RIGHT);
        snake.setScoreListener(new Snake.ScoreListener() {
            @Override
            public void newScore(final int score) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvScore.setText(""+score);
                    }
                });
            }
        });

        gameThread = new GameThread();

    }

    private void initInks() {

        ViewGroup v = findViewById(R.id.direction_buttons);

        int bs = v.getChildCount();

        String[] directions = new String[] { "LEFT", "UP", "DOWN", "RIGHT"};


        for (int i = 0; i < bs; i++) {

            if (i == 0) {
                tvScore = (TextView) v.getChildAt(i);
                tvScore.setText("0");
                continue;
            }

            if (i == 1) {
                final TextView tv2 = (TextView) v.getChildAt(i);
                tv2.setText("PAUSE");
                tv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (paused) {
                            paused = false;
                            tv2.setText("PAUSE");
                        }  else  {
                            paused = true;
                            tv2.setText("PLAY");
                        }

                    }
                });
                continue;
            }


            TextView tv = (TextView) v.getChildAt(i);
            tv.setText(directions[i-2]);
            tv.setTag(directions[i-2]);
            tv.setOnClickListener(new DirectionClickListener());
        }
    }


    private class GameThread extends Thread {

        @Override

        public void run() {

            while (nichore) {

                if (paused) continue;

                if(holder.getSurface().isValid()) {

                    //todo: optimize
                    Canvas canvas = holder.lockCanvas();
                    doCanvas(canvas);
                    holder.unlockCanvasAndPost(canvas);

                    try {
                        Thread.sleep(SLEEP_INTERVAL);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        private void doCanvas(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            snake.draw(canvas);

        }
    }

    @Override
    protected void onPause() {
        nichore = false;
        try {
            gameThread.join(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        nichore = true;
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        nichore = false;
    }



    private class DirectionClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            String direction = view.getTag().toString();

            switch (direction) {
                case "UP": snake.setDirection(Snake.Direction.UP); break;
                case "RIGHT": snake.setDirection(Snake.Direction.RIGHT); break;
                case "LEFT": snake.setDirection(Snake.Direction.LEFT); break;
                case "DOWN": snake.setDirection(Snake.Direction.DOWN); break;
                default: break;
            }

        }
    }
}
