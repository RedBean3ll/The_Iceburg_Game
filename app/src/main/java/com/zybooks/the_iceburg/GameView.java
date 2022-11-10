package com.zybooks.the_iceburg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import androidx.core.content.res.ResourcesCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    private int isDrawn = 0;

    private Thread thread;
    private boolean isPlaying;
    private final float SCREEN_RATIO_X, SCREEN_RATIO_Y;
    private final Paint paint;
    private final GameBackground background1, background2;
    private int dir, lastDir = 1;
    private int progress = 0;
    private Drawable player;

    private boolean isJump = false;
    private int yDir = 0;
    private int acceleration = 0;
    private LevelOneEnvironment envi;
    private int colBelow = 1;
    public float deltaT = 0;
    public float delay = 0;
    public float gravity = 0;
    public boolean grounded = true;
    public int colBuffer;

    public boolean dead = false;

    public int costumeNum = 0;
    public int screenX, screenY;
    public boolean jump, invoke_interaction;
    public Context contx;
    public ArrayList<Integer> colliders = new ArrayList<>();

    public float deathTimer = 0;

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
          Log.d("Dead", String.valueOf(dead));
          draw();
          if(dead) {
              deathTimer += 0.1f;
              if (deathTimer >= 5){
                  Respawn();
              }
              Log.d("Dead", String.valueOf(deathTimer));
          }

          switch (dir) {
              case 1:
                  right();
                  sleep();
                  break;

              case -1:
                  left();
                  sleep();
                  break;
          }

          applyGravity(yDir);

          if(gravity > 2500) {
              dead = true;
              gravity = 0;
          }

          if (!grounded && delay < 20) {
              delay += 1;
          }
          if (jump && grounded) {
              isJump = true;
              if (grounded) {
                  acceleration = 15;
              }
              yDir = 1;
              deltaT = 3;
              grounded = false;
          }
          else if(grounded) {
              acceleration = 0;
              yDir = 0;
              isJump = false;
          }

          if(!jump && !grounded && delay > 0 && acceleration == 0) {
              yDir = 1;
              deltaT = 3;
          }
        }
    }

    private void Respawn() {
        deathTimer = 0;
        dead = false;
        progress = 0;
        gravity = 0;
        grounded = true;
        jump = false;
        isJump = false;

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
        progress+=21;
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
            progress -= 21;
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
            if(isDrawn == 1) {
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                isDrawn = 0;
            }
            if(isDrawn == 0) {
                canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
                canvas.drawBitmap(background2.background, background2.x, background2.y, paint);

                //----------------------------------------- Scenery Loader --------------------------------------------------
                LevelOneEnvironment env = new LevelOneEnvironment(contx, screenX, screenY);
                envi = env;
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
                    if (d.getBounds().left < screenX && d.getBounds().right > 0) {
                        d.draw(canvas);

                        //only applies to horizontal, make for both!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

                        if (screenX / 2 > d.getBounds().left + colBuffer) {
                            //only activates between the middle of the screen passing the right
                            //and then halfway in
                            colBelow = env.layout[i];
                            // Log.d("The bound", String.valueOf(colBuffer));
                            // Log.d("For", String.valueOf(env.layout[i]));
                        }
                    }
                }

                //---------------------------------- Interactables ----------------------------------

                Interacables intables = new Interacables(contx, screenX, screenY);
                Drawable itr = intables.interacts[0];

                itr.setBounds(2000 - progress, screenY - 400, 2200 - progress, screenY - 200);
                if (itr.getBounds().left < screenX && itr.getBounds().right > 0) {
                    itr.draw(canvas);
                }

                //Draw everything UI and player here, icons, player

                //---------------------------------- Player ----------------------------------
                Player playClass = new Player(contx, costumeNum);
                int frame = (progress % 4) + 1;

                switch (dir) {
                    case 1:
                        lastDir = 1;
                        switch (frame) {
                            case 1:
                                player = playClass.costume[3];
                                break;
                            case 2:
                                player = playClass.costume[4];
                                break;
                            default:
                                player = playClass.costume[2];
                        }
                        break;
                    case -1:
                        lastDir = -1;
                        switch (frame) {
                            case 1:
                                player = playClass.costume[7];
                                break;
                            case 2:
                                player = playClass.costume[8];
                                break;
                            default:
                                player = playClass.costume[6];
                        }
                        break;

                    default:
                        if (lastDir == 1) {
                            player = playClass.costume[0];
                        } else {
                            player = playClass.costume[1];
                        }
                        break;
                }

                if (!grounded) {
                    switch (lastDir) {
                        case 1:
                            player = playClass.costume[10];
                            break;
                        case -1:
                            player = playClass.costume[11];
                            break;
                    }
                }

                assert player != null;
                player.setBounds((screenX / 2) - 200, (int) (screenY - 550 + gravity), (screenX / 2) + 200, (int) (screenY - 150 + gravity));
                player.draw(canvas);


                //-------------------------------------------- COLLISION -------------------------------------------
                //Log.d("buffer", String.valueOf(colBuffer));
                //Log.d("colBelow", String.valueOf(colBelow));
                switch (colBelow) {
                    case 0:
                        colBuffer = 0;
                        grounded = false;
                        break;
                    case 1:
                        colBuffer = 0;
                        if (player.getBounds().bottom >= envi.levelElement[1].getBounds().top + 400 && delay > 10) {
                            applyGravity(0);
                            delay = 0;
                            grounded = true;
                        }
                        break;
                    case 2:
                        colBuffer = -700;
                        if (player.getBounds().bottom >= envi.levelElement[2].getBounds().top + 400 && player.getBounds().left + 200 > envi.levelElement[2].getBounds().right + colBuffer && delay > 10) {
                            applyGravity(0);
                            delay = 0;
                            grounded = true;
                        } else if (isJump && player.getBounds().bottom >= envi.levelElement[2].getBounds().top + 400 && delay > 10) {
                            applyGravity(0);
                            delay = 0;
                            grounded = true;
                        }
                }

                // ----- Handles interactions ------

                if ((player.getBounds().left <= itr.getBounds().right && player.getBounds().right >= itr.getBounds().right - 200) ||
                        (player.getBounds().right >= itr.getBounds().left && player.getBounds().left <= itr.getBounds().left + 200)) {
                    if (invoke_interaction) {
                        if (costumeNum < 4)
                            costumeNum++;
                        else
                            costumeNum = 0;
                        //intables.getInteraction(0);
                    }
                }

                //---------------------------------- Left Arrow ----------------------------------
                if (dir == 0 || dir == 1) {
                    Drawable left_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.left_arrow, null);
                    assert left_arrow != null;
                    left_arrow.setBounds(30, (screenY) - 230, 230, (screenY) - 30);
                    left_arrow.draw(canvas);
                } else if (dir == -1) {
                    Drawable left_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.left_arrow_pressed, null);
                    assert left_arrow != null;
                    left_arrow.setBounds(30, (screenY) - 230, 230, (screenY) - 30);
                    left_arrow.draw(canvas);
                }

                //---------------------------------- Right Arrow ----------------------------------
                if (dir == 0 || dir == -1) {
                    Drawable right_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.right_arrow, null);
                    assert right_arrow != null;
                    right_arrow.setBounds(260, (screenY) - 230, 460, (screenY) - 30);
                    right_arrow.draw(canvas);
                } else if (dir == 1) {
                    Drawable right_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.right_arrow_pressed, null);
                    assert right_arrow != null;
                    right_arrow.setBounds(260, (screenY) - 230, 460, (screenY) - 30);
                    right_arrow.draw(canvas);
                }

                //---------------------------------- Jump Arrow ----------------------------------
                Drawable jump_arrow;
                if (!jump) {
                    jump_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.jump_arrow, null);
                } else {
                    jump_arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.jump_arrow_pressed, null);
                }
                assert jump_arrow != null;
                jump_arrow.setBounds((screenX) - 230, (screenY) - 230, (screenX) - 30, (screenY) - 30);
                jump_arrow.draw(canvas);

                //---------------------------------- Interact Icon ----------------------------------
                Drawable interact;
                if (!invoke_interaction) {
                    interact = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.interact_button, null);
                } else {
                    interact = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.interact_button_pressed, null);
                }
                assert interact != null;
                interact.setBounds((screenX) - 460, (screenY) - 230, (screenX) - 260, (screenY) - 30);
                interact.draw(canvas);

                //---------------------------------- BSOD ----------------------------------

                if (dead) {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    Drawable bsod = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bsod, null);
                    bsod.setBounds(0, 0, screenX, screenY);
                    bsod.draw(canvas);
                }

                isDrawn = 1;
            }
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
        //handles icon
        jump = j;
    }

    public void setInteract(boolean b) {
        invoke_interaction = b;
    }

    public void applyGravity (int dir) {
        switch (dir) {
            case 1:
            case -1:
                acceleration -= 1;
                gravity -= acceleration * (deltaT * deltaT);
                break;
            default:
                gravity = 0;
                deltaT = 0;
                break;
        }
    }
}

