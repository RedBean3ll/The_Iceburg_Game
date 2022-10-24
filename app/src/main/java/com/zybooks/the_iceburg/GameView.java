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
    public int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private GameBackground background1, background2;
    private int dir;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

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
              update();
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
    private void update () {
        background1.x -= 10 * screenRatioX; //affects screen background movement!!!
        background2.x -= 10 * screenRatioX;

        if(background1.x + background1.background.getWidth() < screenRatioX) {
            background1.x = screenX;
        }

        if(background2.x + background2.background.getWidth() < 0) {
            background2.x = screenX;
        }
    }

    private void draw () {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

            //Draw everything UI and player here, icons, player
            Drawable player = getResources().getDrawable(R.drawable.moyaifast, null);
            player.setBounds((screenX/2) -200,((screenY/2)-200) +200,(screenX/2)+200,((screenY/2)+200) +200);
            player.draw(canvas);

            Drawable left_arrow = getResources().getDrawable(R.drawable.arrow_button, null);
            left_arrow.setBounds((screenX) - 230,(screenY) -230, (screenX) - 30,(screenY) -30);
            left_arrow.draw(canvas);

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

