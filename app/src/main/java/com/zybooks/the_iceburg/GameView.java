package com.zybooks.the_iceburg;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.graphics.Bitmap;
import android.os.Handler;

//public class GameView extends SurfaceView implements Runnable {

public class GameView extends View{

    private Thread thread;
    private boolean isPlaying;
    private int screenX, screenY, newX, newY;
    private float screenRatioX, screenRatioY;
    int sceneX = 0;
    private Paint paint;
    private GameBackground background1, background2;
    Bitmap scenery;
    Handler handler;
    Runnable runnable;
    final long UPDATE_MILLISECONDS = 1;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        scenery = BitmapFactory.decodeResource(getResources(),R.drawable.background_level_1);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenRatioX = size.x;
        screenRatioY = size.y;
        float ratio = screenX / screenY;
        newY = screenY;
        newX = (int) (ratio * screenY);

        scenery = Bitmap.createScaledBitmap(scenery, newY,newX, false);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };

        /*this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f /screenY;

        background1 = new GameBackground(screenX,screenY, getResources());
       // background2 = new GameBackground(screenX,screenY, getResources());

       // background2.x = screenX;

        paint = new Paint();*/
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        sceneX -= 1;
        if (sceneX < -newX) {
            sceneX = 0;
        }
        canvas.drawBitmap(scenery, sceneX, 0, null);
        if (sceneX < screenX - newX) {
            canvas.drawBitmap(scenery, sceneX + newX, 0, null);
        }
        handler.postDelayed(runnable,UPDATE_MILLISECONDS);
    }

/*
    @Override
    public void run() {
        while (isPlaying) {
            update ();
            if(background1.x < screenRatioX) {
                draw();
            }
            sleep();
        }
    }

    private void update () {
        background1.x -= 10 * screenRatioX; //affects screen background movement!!!
       // background2.x -= 10 * screenRatioY;

        if(background1.x + background1.background.getWidth() < screenRatioX) {
            background1.x = screenX;
        }

       /*if(background2.x + background2.background.getWidth() < 0) {
            background1.x = screenX;
        }
    }

    private void draw () {
        if(getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            //canvas.drawBitmap(background2.background, background2.x + background1.x, background2.y, null);

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void sleep () {
        try {
            Thread.sleep(1); // Speed at which elements are drawn !!! IMPORTANT !!! (larger is slower)
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
*/
}

