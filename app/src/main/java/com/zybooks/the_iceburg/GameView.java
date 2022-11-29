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


@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    public int currentLevel = 1;

    public int progress;
    public int progress_portrait;
    public int progress_landscape;

    public int orientation = 1;

    private int isDrawn = 0;

    // -------- Threading and environment and player ----------
    private Thread thread;
    private Thread thread_secondary;
    private boolean isPlaying;
    private final float SCREEN_RATIO_X, SCREEN_RATIO_Y;
    private Paint paint;
    private GameBackground background1, background2;
    private int dir, lastDir = 1;
    private Drawable player;
    private boolean overlay;

    public int screenX, screenY;
    public int costumeNum = 0;
    public int levelEnd;

    // -------- Collision ----------
    private boolean isJump = false;
    private int yDir = 0;
    private int acceleration = 0;
    private LevelEnvironment envi;
    private int currentObst = 1;
    private int closestFloorHeight;

    public int floorColBelow = 1;
    public int obstNear = 0;
    public boolean underplat;

    private int nextBarrier;
    // -------- Gravity ----------
    public float deltaT = 0;
    public float gravity;
    public boolean onPlatform = false;
    public boolean grounded = true;

    // -------- Interacts ----------
    private int yn = 0;

    public Drawable currInt;
    public int interact_num = 0;
    public boolean int_draw = false;
    public boolean prompt = false;
    public boolean answered = false;

    public boolean dead = false;

    public boolean jump, invoke_interaction;
    public Context contx;

    // -------- Timers ----------
    private float deathTimer = 0;
    private float transitionTimer = 0;
    private float interactTransitionTimer = 20;

    // -------- Classes ----------
    public Interacables intables;
    public LevelEnvironment env;
    public Player playClass;
    public Collectibles collectibles;

    public GameView(Context context, int screenX, int screenY, int costume) {
        super(context);
        contx = context;

        costumeNum = costume;

        this.screenX = screenX;
        this.screenY = screenY;
        SCREEN_RATIO_X = 1920 / screenX;
        SCREEN_RATIO_Y = 1080 /screenY;

        background1 = new GameBackground(currentLevel,screenX ,screenY, getResources());
        background2 = new GameBackground(currentLevel,screenX ,screenY, getResources());

        background2.x = screenX;

        intables = new Interacables(contx, screenX, screenY);
        env = new LevelEnvironment(contx, screenX, screenY);
        playClass = new Player(contx, costumeNum);
        collectibles = new Collectibles(contx,screenX, screenY);
        paint = new Paint();
    }

    @Override
    public void run() {
      while (isPlaying) {

          Log.e("CurrentLeve", String.valueOf(currentLevel));
          levelEnd = env.levelEnds[currentLevel - 1];

          progress_landscape = progress + 370;
          progress_portrait = progress - 370;

          if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
              orientation = 1;
          }
          else {
              orientation = 2;
          }

          draw();
          ResultUpdater();
          Barriers();

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

          if(gravity > 700 && progress < levelEnd) {
              dead = true;
              gravity = 0;
          }
          else if(gravity > 700 && progress > levelEnd) {
              TransitionLevel();
          }

          //Log.e("CurrentLeve", String.valueOf(progress));
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
          if(progress < 0) {
              progress = 0;
          }
          if(answered) {
              interactTransitionTimer -= 0.1f;
          }
          else {
              interactTransitionTimer = 5;
          }
        }
    }

    private void Barriers() {
        if(collectibles.layout[0] == 0) {
            nextBarrier = env.barrierLocation[1];
        }
        else {
            nextBarrier = env.barrierLocation[0];
        }
    }

    private void TransitionLevel() {
        if(transitionTimer <= 5) {
            transitionTimer += 0.1f;
        }
        else {
            currentLevel = currentLevel + 1;
            transitionTimer = 0;
            background1 = new GameBackground(currentLevel,screenX ,screenY, getResources());
            background2 = new GameBackground(currentLevel,screenX ,screenY, getResources());

            background2.x = screenX;

            intables = new Interacables(contx, screenX, screenY);
            env = new LevelEnvironment(contx, screenX, screenY);
            playClass = new Player(contx, costumeNum);
            collectibles = new Collectibles(contx,screenX, screenY);
            paint = new Paint();
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
        isDrawn = 0;
        currInt = null;
        int_draw = false;
        interact_num = 0;
        prompt = false;
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
        int factor;
        if(orientation == 1) {
            factor = progress_portrait;
        }
        else {
            factor = progress;
        }
        if(factor < nextBarrier) {
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
                if(currentLevel == 1) {
                    Drawable boat = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.titanic_2, null);
                    boat.setBounds(50 - progress, screenY - 600, 900 - progress, screenY - 200);
                    boat.draw(canvas);
                }
                //----------------------------------------- Scenery Loader --------------------------------------------------
                envi = env;
                env.progress = progress;

                if(overlay) {
                    drawOverlay(canvas);
                }

                //---------------------------------- Barriers -------------------------------------
                if(nextBarrier != 37700) {
                    Drawable b;
                    b = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.barrier, null);
                    b.setBounds(1100 + nextBarrier - 150 - progress, 0, 1100 + nextBarrier - progress, screenY);
                    b.draw(canvas);
                }
                //---------------------------------- Obstacles -------------------------------------
                drawObstacles(env,canvas);
                //---------------------------------- Floor Loader ----------------------------------
                drawFloor(env, canvas);
                //---------------------------------- Interactables ----------------------------------
                for(int i = 0; i < intables.layout.length; i++) {
                    Drawable itr;
                    switch (intables.layout[i]) {
                        case 1:
                            itr = intables.interacts[1];
                            break;
                        case 2:
                            itr = intables.interacts[2];
                            break;
                        case 3:
                            itr = intables.interacts[3];
                            break;
                        case 4:
                            itr = intables.interacts[4];
                            break;
                        case 5:
                            itr = intables.interacts[5];
                            break;
                        case 6:
                            itr = intables.interacts[6];
                            break;
                        default:
                            itr = intables.interacts[0];
                            break;
                    }

                    switch (intables.layout[i]) {
                        case 1:
                        case 6:
                            itr.setBounds(intables.offsetX[i] - progress, screenY - intables.offsetY[i] - 110, intables.offsetX[i] + 200 - progress, screenY - intables.offsetY[i] + 200);
                            break;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            itr.setBounds(intables.offsetX[i] - progress, screenY - intables.offsetY[i] - 80, intables.offsetX[i] + 200 - progress, screenY - intables.offsetY[i] + 200);
                            break;
                        default:
                            itr.setBounds(intables.offsetX[i] - progress, screenY - intables.offsetY[i], intables.offsetX[i] + 200 - progress, screenY - intables.offsetY[i] + 200);
                            break;
                    }
                    if(itr.getBounds().left < screenX / 2 && itr.getBounds().right > screenX / 2) {
                        if(intables.response[i] != 0) {
                            interact_num = intables.response[i];
                            currInt = itr;
                            if (invoke_interaction) {
                                if(intables.isPrompt[i] == 1) {
                                    prompt = true;
                                }
                                int_draw = true;
                            }
                        }
                        else {
                            interact_num = 0;
                            currInt = itr;
                            if (invoke_interaction) {
                                if(intables.layout[i] == 4) {
                                    boolean one,two,three;
                                    if (intables.layout[i - 1] == 3) {
                                        three = true;
                                    }
                                    else {
                                        three = false;
                                    }
                                    if (intables.layout[i - 2] == 3) {
                                        two = true;
                                    }
                                    else {
                                        two = false;
                                    }
                                    if (intables.layout[i - 3] == 3) {
                                        one = true;
                                    }
                                    else {
                                        one = false;
                                    }

                                    boolean success = intables.leverPuzzle(currentLevel,one,two,three);
                                    if(success) {
                                        //applies just for level 1, set later ones with a statement
                                        env.layout[7] = 4;
                                    }
                                    else {
                                        dead = true;
                                    }
                                }
                                intables.flip(i);
                                int_draw = true;
                            }
                        }
                    }
                    else {
                        currInt = null;
                    }
                    if (itr.getBounds().left < screenX && itr.getBounds().right > 0) {
                        if(intables.layout[i] == 2 || intables.layout[i] == 3) {
                            Drawable arrow;
                            if(intables.solution1[i] == 1) {
                                arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.arrow_down, null);
                            }
                            else {
                                arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.arrow_up, null);
                            }
                            arrow.setBounds(itr.getBounds().left,itr.getBounds().top - 1000, itr.getBounds().right, itr.getBounds().bottom - 1100);
                            arrow.draw(canvas);
                        }
                        itr.draw(canvas);
                    }
                }

                //---------------------------------- Collectibles ----------------------------------
                drawCollectibles(canvas);
                drawCollectibleReactors(canvas);
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

                if(!prompt) {
                    drawUI(env, canvas);
                }
                else {
                    drawPrompt(env,canvas);
                }

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

    private void drawOverlay(Canvas canvas) {
        Drawable d = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.textureless, null);
        d.setBounds(0,0,screenX,screenY);
        d.draw(canvas);
    }


    //======================================== DRAW METHOD START ==================================================

    private void drawFloor(LevelEnvironment env, Canvas canvas) {
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

    private void drawBridgeFront(LevelEnvironment env, Canvas canvas) {
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

    private void drawObstacles(LevelEnvironment env, Canvas canvas) {
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
    }

    private void drawPlayer(LevelEnvironment env, Canvas canvas) {

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

    private void drawIntResponse(LevelEnvironment env, Canvas canvas, Interacables intables) {
        Drawable d = intables.int_response[interact_num];
        d.setBounds((screenX / 2) - 400, (screenY / 2) - 400, (screenX / 2) + 400, (screenY / 2) + 400);
        d.draw(canvas);
    }

    private void drawCollectibles(Canvas canvas) {
        for(int i = 0; i < collectibles.layout.length; i++) {
            Drawable colec;
            switch (collectibles.layout[i]) {
                case 1:
                    colec = collectibles.collectibles[1];
                    break;
                default:
                    colec = collectibles.collectibles[0];
                    break;
            }

            colec.setBounds(collectibles.offsetX[i] - progress, screenY - collectibles.offsetY[i], collectibles.offsetX[i] + 200 - progress, screenY - collectibles.offsetY[i] + 200);

            if(colec.getBounds().left < screenX / 2 && colec.getBounds().right > screenX / 2
                    && colec.getBounds().bottom > gravity + screenY - 230 && colec.getBounds().top < gravity + screenY - 230) {
                collectibles.layout[i] = 0;
            }
            colec.draw(canvas);
        }
    }

    private void drawCollectibleReactors(Canvas canvas) {
    }


    private void collision(LevelEnvironment env, Canvas canvas) {

        if(obstNear != 0 && -(gravity - 230) <= closestFloorHeight) {
            underplat = true;
        }
        else {
            underplat = false;
        }

        //============================== Sets where to land ==============================

        if(floorColBelow != 0 || obstNear !=0) {
            if(obstNear == 0 && floorColBelow !=0){
                closestFloorHeight = 230;
            }
            else if(obstNear != 0 && !underplat){
                closestFloorHeight = env.obstBuffer_vert[currentObst];
            }
            else if(floorColBelow == 0 && obstNear !=0 && underplat && acceleration == 0) {
                closestFloorHeight = 0;
            }
        }
        else if(floorColBelow == 0 && obstNear == 0 && acceleration == 0) {
            closestFloorHeight = 0;
        }
        else if(floorColBelow == 0 && underplat) {
            closestFloorHeight = 0;
        }
        //================================= True collision =====================================
        if(floorColBelow == 0 && closestFloorHeight == 0) {
            grounded = false;
        }
        else if(floorColBelow !=0 || obstNear != 0) {
           /*Log.e("FIRST STAEMENT", String.valueOf(-(gravity - 230)));
           Log.e("SECOND STAEMENT", String.valueOf(closestFloorHeight));
           Log.e("AAAAAAAAAAAAAAAAAA", String.valueOf(underplat));
           Log.e("FLOOOOOR!!!", String.valueOf(floorColBelow));*/

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


    private void drawUI(LevelEnvironment env, Canvas canvas) {
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
    }


    private void drawPrompt(LevelEnvironment env, Canvas canvas) {
        Drawable yes = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.yes_button, null);
        yes.setBounds(30, (screenY) - 230, 230, (screenY) - 30);
        yes.draw(canvas);

        Drawable no;
        no = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.no_button, null);
        no.setBounds((screenX) - 230, (screenY) - 230, (screenX) - 30, (screenY) - 30);
        no.draw(canvas);
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

    public void interactResult(boolean correct) {
        if(currentLevel == 1) {
            if (correct) {
                yn = 2;
                answered = true;
                overlay = true;
                intables.response[3] = 6;
            }
            else {
                yn = 1;
                overlay = true;
                answered = true;
                intables.response[3] = 5;
            }
        }
    }

    public void ResultUpdater () {
        if(yn == 1) {
            if(interactTransitionTimer < 0) {
                prompt = false;
                overlay = false;
                answered = false;
                intables.isPrompt[3] = 0;
                interactTransitionTimer = 20;
                yn = 0;
                return;
            }
        }
        else if (yn == 2){
            if(interactTransitionTimer < 0) {
                intables.response[3] = 5;
                overlay = false;
                answered = false;
                prompt = false;
                interactTransitionTimer = 20;
                yn = 0;
                dead = true;
            }
        }
        else {
            interactTransitionTimer = 20;
            yn = 0;
        }
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

