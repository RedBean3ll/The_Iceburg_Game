package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private GameBackground background1, background2;
    private int dir;
    private int progress = 0;

    public int screenX, screenY;
    public Context contx;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        contx = context;
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920 / screenX;
        screenRatioY = 1080 /screenY;

        background1 = new GameBackground(screenX ,screenY, getResources());
        background2 = new GameBackground(screenX ,screenY, getResources());

        background2.x = screenX;

        paint = new Paint();
    }

    @Override
    public void run() {
      while (isPlaying) {
          draw ();
          if(dir == 1) {
              right();
              sleep();
          }
          if(dir == -1) {
              left();
              sleep();
          }
        }
    }

    public void backgroundMovement (int direction) {
        dir = direction;
        if (direction == 0) {
                background1.x -= 0 * screenRatioX; //affects screen background movement!!!
                background2.x -= 0 * screenRatioX;

                if(background1.x + background1.background.getWidth() < screenRatioX) {
                    background1.x = screenX;
                }

                if(background2.x + background2.background.getWidth() < 0) {
                    background2.x = screenX;
            }
        }
    }
    private void right () {
        progress+=15;
        Log.d("Progress", String.valueOf(progress));
        background1.x -= 10 * screenRatioX; //affects screen background movement!!!
        background2.x -= 10 * screenRatioX;

        if(background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if(background2.x + background2.background.getWidth() < screenRatioX) {
            background2.x = screenX;
        }
    }

    private void left() {
        if(progress > 0) {
            progress -= 15;
            Log.d("Progress", String.valueOf(progress));
            background1.x += 10 * screenRatioX; //affects screen background movement!!!
            background2.x += 10 * screenRatioX;

            if (background1.x - background1.background.getWidth() > 0) {
                background1.x = -screenX;
            }

            if (background2.x - background2.background.getWidth() > 0) {
                background2.x = -screenX;
            }
        }
    }

    private void draw () {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            /*Drawable testFloor = getResources().getDrawable(R.drawable.floor_1,null);
            testFloor.setBounds(2000 - progress,screenY - 600, 5000 - progress,screenY);
            testFloor.draw(canvas);*/

            LevelOneEnvironment env = new LevelOneEnvironment(contx, canvas, screenX, screenY);
            env.progress = progress;
            env.drawFloor();

            //initial floor
            Drawable ds = env.ice_floor;
            ds.setBounds(0 - progress, screenY - 600, 2000 - progress, screenY);
            ds.draw(canvas);

            //for loop that makes everything chain together WIP!!!
            for (int i = progress; i >= 200; i = 0) {
                Drawable d = env.ice_floor;
                d.setBounds(2000 - progress, screenY - 600, 4000 - progress, screenY);
                d.draw(canvas);
            }

            //Draw everything UI and player here, icons, player
            Drawable player = getResources().getDrawable(R.drawable.moyaifast, null);
            player.setBounds((screenX/2) -200,((screenY/2)-200) +200,(screenX/2)+200,((screenY/2)+200) +200);
            player.draw(canvas);

            Drawable left_arrow = getResources().getDrawable(R.drawable.left_arrow, null);
            left_arrow.setBounds(30,(screenY) -230, 230,(screenY) -30);
            left_arrow.draw(canvas);

            Drawable right_arrow = getResources().getDrawable(R.drawable.right_arrow, null);
            right_arrow.setBounds(260,(screenY) -230, 460,(screenY) -30);
            right_arrow.draw(canvas);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep () {
        try {
            Thread.sleep(17); // Speed at which elements are drawn !!! IMPORTANT !!! (larger is slower)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}

