package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceView;


public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY;
    private float screenRatioX, screenRatioY;
    private Paint paint;
    private GameBackground background1, background2;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f /screenY;

        background1 = new GameBackground(screenX,screenY, getResources());
       // background2 = new GameBackground(screenX,screenY, getResources());

        //background2.x = screenX;

        paint = new Paint();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update ();
            draw ();
            sleep();
        }
    }

    private void update () {
        background1.x -= 10 * screenRatioX; //affects screen background movement!!!
        //background2.x -= 10 * screenRatioY;

        if(background1.x + background1.background.getWidth() < screenRatioX) {
            background1.x = 0;
        }

       /* if(background2.x + background2.background.getWidth() < 0) {
            background1.x = screenX;
        }*/
    }

    private void draw () {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x + 50, background1.y, paint);
           // canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

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
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

