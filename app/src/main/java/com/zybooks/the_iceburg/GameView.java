package com.zybooks.the_iceburg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import androidx.core.content.res.ResourcesCompat;

import java.util.Arrays;


@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    public int currentLevel = 1;

    public int progress;

    public int orientation = 1;

    private int isDrawn = 0;

    private Thread thread;
    private Thread thread_secondary;
    private boolean isPlaying;
    private final float SCREEN_RATIO_X, SCREEN_RATIO_Y;
    private final Paint paint;
    private final GameBackground background1, background2;
    private int dir, lastDir = 1;
    private Drawable player;

    private boolean isJump = false;
    private int yDir = 0;
    private int acceleration = 0;
    private LevelOneEnvironment envi;
    private int floorColBelow = 1;
    private int obstNear = 0;
    private int currentObst = 1;
    private int closestFloorHeight;
    public  boolean underplat;

    public float deltaT = 0;
    public float gravity = 0;
    public boolean onPlatform = false;
    public boolean grounded = true;

    public Drawable currInt;
    public int interact_num = 0;
    public boolean int_draw = false;

    public boolean dead = false;

    public int costumeNum = 0;
    public int screenX, screenY;
    public boolean jump, invoke_interaction;
    public Context contx;

    public float deathTimer = 0;
    public float transitionTimer = 0;

    public GameView(Context context, int screenX, int screenY) {
        super(context);
        contx = context;


        this.screenX = screenX;
        this.screenY = screenY;
        SCREEN_RATIO_X = 1920 / screenX;
        SCREEN_RATIO_Y = 1080 /screenY;

Log.e("CurrentLeve", String.valueOf(currentLevel));
        background1 = new GameBackground(currentLevel,screenX ,screenY, getResources());
        background2 = new GameBackground(currentLevel,screenX ,screenY, getResources());

        background2.x = screenX;

        paint = new Paint();
    }

    @Override
    public void run() {
      while (isPlaying) {
          if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
              orientation = 1;
          }
          else {
              orientation = 2;
          }

          draw();

          if(dead) {
              deathTimer += 0.1f;
              if (deathTimer >= 5){
                  Respawn();
              }
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

          if(!dead) {
              applyGravity(yDir);
          }

         // Log.e("JUMP???", String.valueOf(jump));

          if(gravity > 700 && progress < 37000) {
              dead = true;
              gravity = 0;
          }
          else if(gravity > 700 && progress > 37000) {
              TransitionLevel();
          }

          //Log.e("CurrentLeve", String.valueOf(progress));

          //Log.e("Grounded", String.valueOf(progress));
          if (grounded) {
              if (jump) {
                  isJump = true;
                  acceleration = 15;
                  onPlatform = false;
                  yDir = 1;
                  deltaT = 3;
                  grounded = false;
              }
              else {
                  acceleration = 0;
                  yDir = 0;
                  isJump = false;
              }
              if(floorColBelow !=0 && !isJump && obstNear == 0 && gravity < 0) {
                  onPlatform = false;
                  grounded = false;
                  yDir = 1;
                  deltaT = 3;
              }
              else if(floorColBelow !=0 && !isJump && obstNear == 0) {
                  gravity = 0;
              }
          }

          if(!grounded) {
              if(!jump && acceleration == 0) {
                  yDir = 1;
                  deltaT = 3;
              }
          }
        }
    }

    private void TransitionLevel() {
        if(transitionTimer <= 5) {
            transitionTimer += 0.1f;
        }
        else {
            currentLevel = currentLevel + 1;
            transitionTimer = 0;
            Respawn();
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
        dir = 0;
        background1.x = 0;
        background2.x = screenX;
        onPlatform = false;
        yDir = 0;
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
        if(progress < 37700) {
            progress += 21;
            background1.x -= 5 * SCREEN_RATIO_X; //affects screen background movement!!!
            background2.x -= 5 * SCREEN_RATIO_X;

            if (background1.x + background1.background.getWidth() < 0) {
                background1.x = screenX;
            }

            if (background2.x + background2.background.getWidth() < SCREEN_RATIO_X) {
                background2.x = screenX;
            }
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

                //---------------------------------- Obstacles -------------------------------------
                drawObstacles(env,canvas);
                //---------------------------------- Floor Loader ----------------------------------
                drawFloor(env, canvas);
                //---------------------------------- Interactables ----------------------------------

                Interacables intables = new Interacables(contx, screenX, screenY);
                for(int i = 0; i < intables.layout.length; i++) {
                    Drawable itr;
                    switch (intables.layout[i]) {
                        case 1:
                            itr = intables.interacts[1];
                            break;
                        default:
                            itr = intables.interacts[0];
                            break;
                    }

                    switch (intables.layout[i]) {
                        case 1:
                            itr.setBounds(intables.offsetX[i] - progress, screenY - intables.offsetY[i] - 110, intables.offsetX[i] + 200 - progress, screenY - intables.offsetY[i] + 200);
                            break;
                        default:
                            itr.setBounds(intables.offsetX[i] - progress, screenY - intables.offsetY[i], intables.offsetX[i] + 200 - progress, screenY - intables.offsetY[i] + 200);
                            break;
                    }
                    if (itr.getBounds().left < screenX && itr.getBounds().right > 0) {
                        itr.draw(canvas);
                    }
                    if(itr.getBounds().left < screenX / 2 && itr.getBounds().right > screenX / 2) {
                        interact_num = intables.response[i];
                        currInt = itr;
                        if (invoke_interaction) {
                            int_draw = true;
                        }
                    }
                    else {
                        currInt = null;
                    }
                }
                //Draw everything UI and player here, icons, player

                //---------------------------------- Player ----------------------------------
                drawPlayer(env,canvas);

                //------------------------------------ Draws split elements ----------------------------

                drawBridgeFront(env,canvas);
                //-------------------------------------------- COLLISION -------------------------------------------

                collision(env,canvas);

                // ----- Handles interactions ------
                if(int_draw) {
                    drawIntResponse(env,canvas,intables);
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
                    int_draw = false;
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



    //======================================== DRAW METHOD START ==================================================

    private void drawFloor(LevelOneEnvironment env, Canvas canvas) {
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
                case 4:
                    d = env.bridge;
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
                if (screenX / 2 > d.getBounds().left) {
                    floorColBelow = env.layout[i];
                }
            }
        }
    }

    private void drawBridgeFront(LevelOneEnvironment env, Canvas canvas) {
        for (int i = 0; i < env.layout.length; i++) {
            Drawable d;
            boolean drawIt;
            switch (env.layout[i]) {
                case 4:
                    drawIt = true;
                    d = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bridge_front, null);
                    break;
                default:
                    drawIt = false;
                    d = env.empty;
            }
            assert d != null;
            if(drawIt) {
                d.setBounds((i * 2000) - progress, screenY - 600, (i * 2000) + 2000 - progress, screenY);
                if (d.getBounds().left < screenX && d.getBounds().right > 0) {
                    d.draw(canvas);
                }
            }
        }
    }

    private void drawObstacles(LevelOneEnvironment env, Canvas canvas) {
        for(int i = 0; i < env.obstacles.length; i++) {
            Drawable d;
            switch(env.obstacles[i]) {
                case 1:
                    d = env.short_ledge;
                    d.setBounds((i + 1 * env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + 1* env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY);
                    break;
                case 2:
                    d = env.float_ledge;
                    d.setBounds((i + 1 * env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + 1* env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i] + 140);
                    break;
                default:
                    d = env.empty;
                    d.setBounds((i + 1 * env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + 1* env.obstBuffer[i]) + 0 - progress, screenY);
            }
            assert d != null;
            if (d.getBounds().left < screenX && d.getBounds().right > 0) {
                d.draw(canvas);
            }
        }
        for(int i = 0; i < env.obstacles.length; i++) {
            if (i + 1 * env.obstBuffer[i] - progress < screenX / 2 &&
                    (i + 1 * env.obstBuffer[i]) + env.obstWidth[i] - progress > screenX / 2) {
                obstNear = env.obstacles[i];
                currentObst = i;
                break;
            } else {
                currentObst = 0;
                obstNear = 0;
            }
        }
        // Log.e("Obst", String.valueOf(delay));
    }

    private void drawPlayer(LevelOneEnvironment env, Canvas canvas) {
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
    }

    private void drawIntResponse(LevelOneEnvironment env, Canvas canvas, Interacables intables) {
        Drawable d = intables.int_response[interact_num];
        d.setBounds((screenX / 2) - 400, (screenY / 2) - 400, (screenX / 2) + 400, (screenY / 2) + 400);
        d.draw(canvas);
    }

    private void collision(LevelOneEnvironment env, Canvas canvas) {

        if(obstNear != 0 && -(gravity - 230) <= closestFloorHeight) {
            underplat = true;
        }
        else {
            underplat = false;
        }

        //============================== Sets where to land ==============================

        if(floorColBelow != 0 || obstNear !=0) {
            if(obstNear == 0){
                closestFloorHeight = 230;
            }
            else if(obstNear != 0 && !underplat){
                closestFloorHeight = env.obstBuffer_vert[currentObst];
            }
        }
        else if(floorColBelow == 0 && obstNear == 0 && acceleration == 0) {
            closestFloorHeight = 0;
        }
        //================================= True collision =====================================
        if(floorColBelow !=0 || obstNear != 0) {
            Log.e("FIRST STAEMENT", String.valueOf(-(gravity - 230)));
            Log.e("SECOND STAEMENT", String.valueOf(closestFloorHeight));
            Log.e("AAAAAAAAAAAAAAAAAA", String.valueOf(underplat));


           if(-(gravity - 230) <= closestFloorHeight) {
                if(acceleration < 0 && !grounded) {
                    if (obstNear != 0 && underplat) {
                        Land(true);
                    } else {
                        Land(false);
                    }
                }
            }
        }
        else {
            grounded = false;
        }
        /*
        if(floorColBelow != 0) {
            if(obstNear == 0 || gravity == 0) {
                closestFloorHeight = 230;
            }
            else if (obstNear != 0 && gravity != 0) {
                closestFloorHeight = env.obstBuffer_vert[currentObst];
            }
        }
        else if(floorColBelow == 0 && obstNear == 0 && acceleration == 0) {
            closestFloorHeight = 0;
        }


        if(closestFloorHeight !=0) {
            if (!grounded && player.getBounds().bottom >= screenY - closestFloorHeight && acceleration < 0) {
                if(obstNear != 0) {
                    Land(true);
                }
                else {
                    Land(false);
                }
            }
        }
        else {
            grounded = false;
        }
        /*
        if(player.getBounds().bottom < screenY) {
            switch (obstNear) {
                case 1:
                    if (player.getBounds().bottom >= envi.levelObstacle[0].getBounds().top &&
                            envi.obstBuffer[currentObst] - progress < screenX / 2 &&
                            (envi.obstBuffer[currentObst] - progress) + envi.obstWidth[currentObst] > screenX / 2 &&
                            delay > 8) {
                        Log.e("TopCase    1","Yes");
                        yDir =0;
                        onPlatform = true;
                        gravity = -310;
                        delay = 0;
                        grounded = true;
                    } else if (isJump && player.getBounds().bottom >= envi.levelObstacle[0].getBounds().top && delay > 8) {
                        Log.e("BottomCase    1","Yes");
                        yDir =0;
                        onPlatform = true;
                        gravity = -310;
                        delay = 0;
                        grounded = true;
                    }
                    break;
                case 2:

                    if (player.getBounds().bottom <= screenY - envi.obstBuffer_vert[currentObst] + 180 &&
                            player.getBounds().bottom >= screenY - envi.obstBuffer_vert[currentObst] + 50 &&
                            envi.obstBuffer[currentObst] - progress < screenX / 2 &&
                            (envi.obstBuffer[currentObst] - progress) + envi.obstWidth[currentObst] > screenX / 2 &&
                            delay > 8) {
                        yDir =0;
                        onPlatform = true;
                        gravity = env.obstBuffer_vert[currentObst - 1] - 800;
                        delay = 0;
                        grounded = true;
                    } else if (isJump && player.getBounds().bottom >= screenY - envi.obstBuffer_vert[currentObst] + 50 && delay > 8) {
                        yDir =0;
                        onPlatform = true;
                        gravity = env.obstBuffer_vert[currentObst - 1] - 800;
                        delay = 0;
                        grounded = true;
                    }
                    break;
                default:
                    if (gravity != 0 && !isJump) {
                        grounded = false;
                    }
                    onPlatform = false;
                    break;
            }
        }
        if(!onPlatform) {
            switch (floorColBelow) {
                case 0:
                    grounded = false;
                    break;
                case 1:
                    if (!grounded && player.getBounds().bottom >= envi.levelElement[1].getBounds().top + 400 && delay > 1) {
                        yDir =0;
                        delay = 0;
                        grounded = true;
                    }
                    break;
                case 2:
                    if (!grounded && player.getBounds().bottom >= envi.levelElement[2].getBounds().top + 400 && screenX / 2 > envi.levelElement[2].getBounds().right && delay > 1) {
                        yDir =0;
                        delay = 0;
                        grounded = true;
                    } else if (!grounded && player.getBounds().bottom >= envi.levelElement[2].getBounds().top + 400 && delay > 1) {
                        yDir =0;;
                        delay = 0;
                        grounded = true;
                    }
                    break;
                case 3:
                    if (!grounded && player.getBounds().bottom >= envi.levelElement[3].getBounds().top + 400 && screenX / 2 > envi.levelElement[3].getBounds().right && delay > 1) {
                        yDir =0;
                        delay = 0;
                        grounded = true;
                    } else if (!grounded && player.getBounds().bottom >= envi.levelElement[3].getBounds().top + 400 && delay > 1) {
                        yDir =0;;
                        delay = 0;
                        grounded = true;
                    }
                    break;
            }
        }*/
    }

    private void Land (boolean platform) {
        grounded = true;
        if(!platform) {
            gravity = 0;
        }
        else {
            switch(obstNear) {
                case 1:
                    gravity = -envi.obstBuffer_vert[currentObst] + 300;
                    break;
                case 2:
                    gravity = -envi.obstBuffer_vert[currentObst] + 200;
                    break;
            }
        }
        yDir = 0;
        acceleration = 0;
        deltaT = 0;
    }
    //======================================== DRAW METHOD END ==================================================

    private void sleep () {
        try {
            thread.sleep(17); // Speed at which elements are drawn !!! IMPORTANT !!! (larger is slower)
            thread_secondary.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {
        isPlaying = true;
        thread = new Thread(this);
        thread_secondary = new Thread(this);
        thread.start();
        thread_secondary.start();
    }

    public void pause () {
        try {
            isPlaying = false;
            thread.join();
            thread_secondary.join();
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
                acceleration -= 1;
                gravity -= acceleration * (deltaT * deltaT);
                break;
            default:
                if (obstNear == 0) {
                    deltaT = 0;
                }
                else if(floorColBelow != 0) {
                    grounded = true;
                    deltaT = 0;
                }
                break;
        }
    }

}

