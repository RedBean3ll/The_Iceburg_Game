package com.zybooks.the_iceburg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.SurfaceView;
import androidx.core.content.res.ResourcesCompat;


@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private final float SCREEN_RATIO_X, SCREEN_RATIO_Y;
    private final Paint paint;
    private final GameBackground background1, background2;
    private int dir;
    private int progress = 0;

    public int screenX, screenY;
    public boolean jump, invoke_interaction;
    public Context contx;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        contx = context;
        this.screenX = screenX;
        this.screenY = screenY;
        SCREEN_RATIO_X = 1920 / screenX;
        SCREEN_RATIO_Y = 1080 /screenY;

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
                background1.x -= 0 * SCREEN_RATIO_X; //affects screen background movement!!!
                background2.x -= 0 * SCREEN_RATIO_X;

                if(background1.x + background1.background.getWidth() < SCREEN_RATIO_X) {
                    background1.x = screenX;
                }

                if(background2.x + background2.background.getWidth() < 0) {
                    background2.x = screenX;
            }
        }
    }
    private void right () {
        progress+=15;
        background1.x -= 5 * SCREEN_RATIO_X; //affects screen background movement!!!
        background2.x -= 5 * SCREEN_RATIO_X;

        if(background1.x + background1.background.getWidth() < 0) {
            background1.x = screenX;
        }

        if(background2.x + background2.background.getWidth() < SCREEN_RATIO_X) {
            background2.x = screenX;
        }
    }

    private void left() {
        if(progress > 0) {
            progress -= 15;
            background1.x += 5 * SCREEN_RATIO_X; //affects screen background movement!!!
            background2.x += 5 * SCREEN_RATIO_X;

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

    //----------------------------------------- Scenery Loader --------------------------------------------------
            LevelOneEnvironment env = new LevelOneEnvironment(contx, screenX, screenY);
            env.progress = progress;

            //---------------------------------- Floor Loader ----------------------------------
            for (int i = 0; i < env.layout.length; i++) {
                Drawable d;
                switch (env.layout[i]) {
                    case 1:
                        d = env.ice_floor;
                        break;
                    case 2:
                        d = env.ice_cliff_left;
                        break;
                    case 3:
                        d = env.ice_cliff_right;
                        break;
                    default:
                        d = env.empty;
                }
                assert d != null;
                d.setBounds((i * 2000) - progress, screenY - 600, (i * 2000) + 2000 - progress, screenY);
                if(d.getBounds().left < screenX && d.getBounds().right > 0) {
                    d.draw(canvas);
                }
            }

            //Draw everything UI and player here, icons, player

            //---------------------------------- Player ----------------------------------
            Drawable player;
            if(dir == 1) {
                player = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t, null);
            }
            else {
                player = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai, null);
            }
            assert player != null;
            player.setBounds((screenX/2) -200,screenY -550,(screenX/2)+200,screenY -150);
            player.draw(canvas);

            //---------------------------------- Left Arrow ----------------------------------
            if(dir == 0 || dir == 1) {
                Drawable left_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.left_arrow, null);
                assert left_arrow != null;
                left_arrow.setBounds(30, (screenY) - 230, 230, (screenY) - 30);
                left_arrow.draw(canvas);
            }
            else if (dir == -1) {
                Drawable left_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.left_arrow_pressed, null);
                assert left_arrow != null;
                left_arrow.setBounds(30, (screenY) - 230, 230, (screenY) - 30);
                left_arrow.draw(canvas);
            }

            //---------------------------------- Right Arrow ----------------------------------
            if(dir == 0 || dir == -1) {
                Drawable right_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.right_arrow, null);
                assert right_arrow != null;
                right_arrow.setBounds(260, (screenY) - 230, 460, (screenY) - 30);
                right_arrow.draw(canvas);
            }
            else if (dir == 1) {
                Drawable right_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.right_arrow_pressed, null);
                assert right_arrow != null;
                right_arrow.setBounds(260, (screenY) - 230, 460, (screenY) - 30);
                right_arrow.draw(canvas);
            }

            //---------------------------------- Jump Arrow ----------------------------------
            Drawable jump_arrow;
            if(!jump) {
                jump_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.jump_arrow, null);
            }
            else {
                jump_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.jump_arrow_pressed, null);
            }
            assert jump_arrow != null;
            jump_arrow.setBounds((screenX) - 230,(screenY) -230, (screenX) - 30,(screenY) -30);
            jump_arrow.draw(canvas);

            //---------------------------------- Interact Icon ----------------------------------
            Drawable interact;
            if(!invoke_interaction) {
                interact = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.interact_button, null);
            }
            else {
                interact = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.interact_button_pressed, null);
            }
            assert interact != null;
            interact.setBounds((screenX) - 460,(screenY) -230, (screenX) - 260,(screenY) -30);
            interact.draw(canvas);

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

    public void setJump (boolean j) {
        jump = j;
    }


    public void setInteract(boolean b) {
        invoke_interaction = b;
    }
}

