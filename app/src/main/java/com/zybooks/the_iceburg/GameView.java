package com.zybooks.the_iceburg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;
import androidx.core.content.res.ResourcesCompat;


@SuppressLint("ViewConstructor")
public class GameView extends SurfaceView implements Runnable {

    public int gameEnd;

    public int jumpCount;

    public int currentLevel;
    public int[] costumesUnlocked;
    public int[] advancements;

    public int progress;
    public int progress_portrait;
    public int progress_landscape;

    public int orientation = 1;

    private int isDrawn = 0;

    // -------- Threading and environment and player ----------
    private Thread thread;
    private Thread thread_secondary;
    private boolean isPlaying;
    private Paint paint;
    private GameBackground background1, background2;
    private int dir, lastDir = 1;
    private boolean overlay;

    public int screenX, screenY;
    public int costumeNum;
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

    public int nextBarrier;
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

    public GameView(Context context, int screenX, int screenY, int costume, int currentLevel, int[] savedCost, int[] savedAdv) {
        super(context);
        contx = context;

        Log.e("CurrentLeve", String.valueOf(currentLevel));
        this.currentLevel = currentLevel;

        advancements = savedAdv;
        costumesUnlocked = savedCost;

        costumeNum = costume;

        this.screenX = screenX;
        this.screenY = screenY;

        background1 = new GameBackground(this.currentLevel,screenX ,screenY, getResources());
        background2 = new GameBackground(this.currentLevel,screenX ,screenY, getResources());

        background2.x = screenX;

        intables = new Interacables(contx, this.currentLevel);
        env = new LevelEnvironment(contx, this.currentLevel);
        playClass = new Player(contx, costumeNum);
        collectibles = new Collectibles(contx);
        paint = new Paint();

        if(currentLevel != 1) {
            collectibles.NewLevel(currentLevel);
            intables.NewLevel(currentLevel);
            env.NewLevel(currentLevel);
        }
    }

    @Override
    public void run() {
      while (isPlaying) {

          if(jumpCount > 300) {
              advancements[7] = 1;
          }

          levelEnd = env.levelEnds[currentLevel - 1];

          progress_landscape = progress + (int)(0.20345 * screenX);
          progress_portrait = progress - (int)(0.20345 * screenY);

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

         Log.e("JUMP???", String.valueOf(progress));
          if(currentLevel != 3) {
              if (gravity > 700 && progress < levelEnd) {
                  dead = true;
                  gravity = 0;
              } else if (gravity > 700 && progress > levelEnd) {
                  TransitionLevel();
              }
          }
          else {
              if (gravity > 700 && progress < levelEnd) {
                  dead = true;
                  gravity = 0;
              } else if (gravity > 700 && progress > 19000) {
                  BadEnding();
              }
              else if (gravity > 700 && progress > levelEnd) {
                  TrueEnding();
              }
          }

          //Log.e("CurrentLeve", String.valueOf(progress));
          if (grounded) {
              if (jump) {
                  jumpCount++;
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
        switch (currentLevel) {
            case 1:
                if (collectibles.layout[0] == 0) {
                    nextBarrier = env.barrierLocation[1];
                } else {
                    nextBarrier = env.barrierLocation[0];
                }
                break;
            case 2:
                if (collectibles.layout[0] == 0 && collectibles.layout.length > 3) {
                    if (collectibles.layout[2] == 0) {
                        nextBarrier = env.barrierLocation[2];
                    }
                    else {
                        nextBarrier = env.barrierLocation[1];
                    }
                }
                else {
                    nextBarrier = env.barrierLocation[0];
                }
                break;
            case 3:
                break;
        }
    }

    private void TransitionLevel() {
        if(transitionTimer <= 5) {
            transitionTimer += 0.1f;
        }
        else {
            currentLevel = 3;//currentlevel + 1;
            transitionTimer = 0;
            background1 = new GameBackground(currentLevel,screenX ,screenY, getResources());
            background2 = new GameBackground(currentLevel,screenX ,screenY, getResources());

            background2.x = screenX;

            intables = new Interacables(contx, currentLevel);
            env = new LevelEnvironment(contx, currentLevel);
            playClass = new Player(contx, costumeNum);
            collectibles = new Collectibles(contx);
            paint = new Paint();

            collectibles.NewLevel(currentLevel);
            intables.NewLevel(currentLevel);
            env.NewLevel(currentLevel);

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
        transitionTimer = 0;
    }

    public void backgroundMovement (int direction) {
        dir = direction;
        if (direction == 0) {
            //affects screen background movement!!!

            if(background1.x + background1.background.getWidth() <= 0) {
                    background1.x = screenX;
                }

                if(background2.x + background2.background.getWidth() <= 0) {
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
        if(factor < nextBarrier && transitionTimer ==0) {
            progress += 21;
            background1.x -= 5; //affects screen background movement!!!
            background2.x -= 5 ;

            if (background1.x + background1.background.getWidth() <= 0) {
                background1.x = screenX;
            }

            if (background2.x + background2.background.getWidth() <= 0) {
                background2.x = screenX;
            }
        }
    }

    private void left() {
        if(progress > 0 && transitionTimer ==0) {
            progress -= 21;
            background1.x += 5; //affects screen background movement!!!
            background2.x += 5;

            if (background1.x - background1.background.getWidth() >= 0) {
                background1.x = -screenX;
            }

            if (background2.x - background2.background.getWidth() >= 0) {
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
                    assert boat != null;
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
                    assert b != null;
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
                        case 7:
                            itr = intables.interacts[7];
                            break;
                        case 8:
                            itr = intables.interacts[8];
                            break;
                        case 9:
                            itr = intables.interacts[9];
                            break;
                        default:
                            itr = intables.interacts[0];
                            break;
                    }

                    switch (intables.layout[i]) {
                        case 1:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
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
                            boolean keepButtonPressed = false;
                            if (invoke_interaction) {
                                if(intables.layout[i] == 4) {
                                    boolean one,two,three;
                                    three = intables.layout[i - 1] == 3;
                                    two = intables.layout[i - 2] == 3;
                                    one = intables.layout[i - 3] == 3;

                                    boolean success = intables.leverPuzzle(currentLevel,one,two,three);
                                    if(success) {
                                        keepButtonPressed = true;
                                        //applies just for level 1, set later ones with a statement
                                        switch (currentLevel) {
                                            case 1:
                                                env.layout[5] = 4;
                                                break;
                                            case 2:
                                                env.layout[11] = 4;
                                                env.layout[12] = 4;
                                                break;
                                        }

                                    }
                                    else {
                                        keepButtonPressed = false;
                                        dead = true;
                                    }
                                }
                                if(keepButtonPressed || intables.layout[i] != 4) {
                                    intables.flip(i);
                                }
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
                            if(intables.solution[i] == 1) {
                                arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.arrow_down, null);
                            }
                            else {
                                arrow = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.arrow_up, null);
                            }
                            assert arrow != null;
                            arrow.setBounds(itr.getBounds().left,itr.getBounds().top - 1000, itr.getBounds().right, itr.getBounds().bottom - 1100);
                            arrow.draw(canvas);
                        }
                        itr.draw(canvas);
                    }
                }

                //---------------------------------- Collectibles ----------------------------------
                drawCollectibles(canvas);
                //Draw everything UI and player here, icons, player

                //---------------------------------- Player ----------------------------------
                drawPlayer(canvas);

                //------------------------------------ Draws split elements ----------------------------

                drawBridgeFront(env,canvas);
                //-------------------------------------------- COLLISION -------------------------------------------

                collision(env);

                // ----- Handles interactions ------
                if(int_draw) {
                    drawIntResponse(canvas,intables);
                }

                if(!prompt) {
                    drawUI(canvas);
                }
                else {
                    drawPrompt(canvas);
                }

                //---------------------------------- BSOD ----------------------------------

                if (dead) {
                    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    Drawable bsod = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bsod, null);
                    assert bsod != null;
                    bsod.setBounds(0, 0, screenX, screenY);
                    bsod.draw(canvas);
                }

                if(gameEnd != 0) {
                    switch (gameEnd) {
                        case 1:
                            Drawable bad = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bad_ending, null);
                            assert bad != null;
                            bad.setBounds(0, 0, screenX, screenY);
                            bad.draw(canvas);
                            break;
                        case 2:
                            Drawable good = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.good_ending, null);
                            assert good != null;
                            good.setBounds(0, 0, screenX, screenY);
                            good.draw(canvas);
                            break;
                        case 3:
                            Drawable real = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.trueending, null);
                            assert real != null;
                            real.setBounds(0, 0, screenX, screenY);
                            real.draw(canvas);
                            break;
                    }
                }

                isDrawn = 1;
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void drawOverlay(Canvas canvas) {
        Drawable d = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.textureless, null);
        assert d != null;
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
            if (env.layout[i] == 4) {
                drawIt = true;
                d = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bridge_front, null);
            } else {
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
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY);
                    break;
                case 2:
                    d = env.float_ledge;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i] + 140);
                    break;
                case 3:
                    d = env.move_box;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY);
                    break;
                case 4:
                    d = env.m1;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i]+ 400);
                    break;
                case 5:
                    d = env.m2;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i]+ 400);
                    break;
                case 6:
                    d = env.m3;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i] + 400);
                    break;
                case 7:
                    d = env.m4;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i] + 400);
                    break;
                case 8:
                    d = env.m5;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i]+ 400);
                    break;
                case 9:
                    d = env.m6;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i]+ 400);
                    break;
                case 10:
                    d = env.m7;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) + env.obstWidth[i] - progress, screenY - env.obstBuffer_vert[i]+ 400);
                    break;
                default:
                    d = env.empty;
                    d.setBounds((i + env.obstBuffer[i]) - progress, screenY - env.obstBuffer_vert[i], (i + env.obstBuffer[i]) - progress, screenY);
            }
            if (d.getBounds().left < screenX && d.getBounds().right > 0) {
                d.draw(canvas);
            }
        }
        for(int i = 0; i < env.obstacles.length; i++) {
            if (i + env.obstBuffer[i] - progress < screenX / 2 &&
                    (i + env.obstBuffer[i]) + env.obstWidth[i] - progress > screenX / 2) {
                obstNear = env.obstacles[i];
                currentObst = i;
                break;
            } else {
                currentObst = 0;
                obstNear = 0;
            }
        }
    }

    private void drawPlayer(Canvas canvas) {

        int frame = (progress % 4) + 1;

        Drawable player;
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

    private void drawIntResponse(Canvas canvas, Interacables intables) {
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
                case 2:
                    colec = collectibles.collectibles[2];
                    break;
                case 3:
                    colec = collectibles.collectibles[3];
                    break;
                case 4:
                    colec = collectibles.collectibles[4];
                    break;
                default:
                    colec = collectibles.collectibles[0];
                    break;
            }

            colec.setBounds(collectibles.offsetX[i] - progress, screenY - collectibles.offsetY[i], collectibles.offsetX[i] + 200 - progress, screenY - collectibles.offsetY[i] + 200);

            if(colec.getBounds().left < screenX / 2 && colec.getBounds().right > screenX / 2
                    && colec.getBounds().bottom > gravity + screenY - 230 && colec.getBounds().top < gravity + screenY - 230) {
                switch(collectibles.layout[i]) {
                    case 2:
                        collectibles.layout[i] = 0;
                        costumesUnlocked[1] = 1;
                        break;
                    case 3:
                        collectibles.layout[i] = 0;
                        costumesUnlocked[2] = 1;
                        break;
                    case 4:
                        collectibles.layout[i] = 0;
                        costumesUnlocked[3] = 1;
                        break;
                    case 5:
                        collectibles.layout[i] = 0;
                        costumesUnlocked[4] = 1;
                        break;
                    case 6:
                        collectibles.layout[i] = 0;
                        costumesUnlocked[5] = 1;
                        break;
                    case 7:
                        collectibles.layout[i] = 0;
                        costumesUnlocked[6] = 1;
                        break;
                    case 8:
                        collectibles.layout[i] = 0;
                        costumesUnlocked[7] = 1;
                        break;
                    default:
                        collectibles.layout[i] = 0;
                        break;
                }
            }
            colec.draw(canvas);
        }
    }

    private void collision(LevelEnvironment env) {

        underplat = obstNear != 0 && -(gravity - 230) <= closestFloorHeight;

        //============================== Sets where to land ==============================

        if(floorColBelow != 0 || obstNear !=0) {
            if(obstNear == 0){
                closestFloorHeight = 230;
            }
            else if(!underplat){
                closestFloorHeight = env.obstBuffer_vert[currentObst];
            }
        }
        else if(acceleration == 0) {
            closestFloorHeight = 0;
        }
        //================================= True collision =====================================
        if(floorColBelow == 0 && closestFloorHeight == 0) {
            grounded = false;
        }
        else if (currentObst !=0 && closestFloorHeight == 230 && floorColBelow == 0 && grounded) {
            grounded = false;
        }
        else if(floorColBelow !=0 || obstNear != 0) {
           /*Log.e("FIRST STAEMENT", String.valueOf(-(gravity - 230)));
           Log.e("SECOND STAEMENT", String.valueOf(closestFloorHeight));
           Log.e("AAAAAAAAAAAAAAAAAA", String.valueOf(underplat));
           Log.e("FLOOOOOR!!!", String.valueOf(floorColBelow));*/

           if(-(gravity - 230) <= closestFloorHeight) {
                if(acceleration < 0 && !grounded) {
                    if (obstNear != 0 && underplat && closestFloorHeight != 230) {
                        Land(true);
                    } else if(floorColBelow != 0){
                        Land(false);
                    }
                }
            }
        }
        else {
            grounded = false;
        }
    }

    private void Land (boolean platform) {
        grounded = true;
        if(!platform) {
            gravity = 0;
        }
        else {
            switch(obstNear) {
                case 1:
                    gravity = -envi.obstBuffer_vert[currentObst] + 230;
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


    private void drawUI(Canvas canvas) {
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


    private void drawPrompt(Canvas canvas) {
        Drawable yes = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.yes_button, null);
        assert yes != null;
        yes.setBounds(30, (screenY) - 230, 230, (screenY) - 30);
        yes.draw(canvas);

        Drawable no;
        no = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.no_button, null);
        assert no != null;
        no.setBounds((screenX) - 230, (screenY) - 230, (screenX) - 30, (screenY) - 30);
        no.draw(canvas);
    }

    //======================================== DRAW METHOD END ==================================================

    private void sleep () {
        try {
            Thread.sleep(17); // Speed at which elements are drawn !!! IMPORTANT !!! (larger is slower)
            Thread.sleep(17);
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
        if(currentLevel == 2) {
            if (correct) {
                yn = 2;
                answered = true;
                intables.response[0] = 5;
            }
            else {
                yn = 1;
                answered = true;
                intables.response[0] = 6;
            }
        }
    }

    public void ResultUpdater () {
        if(currentLevel == 1) {
            if (yn == 1) {
                if (interactTransitionTimer < 0) {
                    prompt = false;
                    overlay = false;
                    answered = false;
                    intables.isPrompt[3] = 0;
                    interactTransitionTimer = 20;
                    yn = 0;
                }
            } else if (yn == 2) {
                if (interactTransitionTimer < 0) {
                    intables.response[3] = 5;
                    overlay = false;
                    answered = false;
                    prompt = false;
                    interactTransitionTimer = 20;
                    yn = 0;
                    dead = true;
                }
            } else {
                interactTransitionTimer = 20;
                yn = 0;
            }
        }
        if(currentLevel == 2) {
            if (yn == 2) {
                if (interactTransitionTimer < 0) {
                    prompt = false;
                    answered = false;
                    intables.isPrompt[0] = 0;
                    interactTransitionTimer = 20;
                    yn = 0;
                }
            } else if (yn == 1) {
                if (interactTransitionTimer < 0) {
                    intables.response[0] = 8;
                    answered = false;
                    prompt = false;
                    interactTransitionTimer = 20;
                    yn = 0;
                    dead = true;
                }
            } else {
                interactTransitionTimer = 20;
                yn = 0;
            }
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

    public void BadEnding () {
        advancements[0] = 1;
        advancements[2] = 1;
        isPlaying = false;
    }

    public void TrueEnding () {
        advancements[3] = 1;
        isPlaying = false;
    }

    public void GoodEnding() {
        advancements[1] = 1;
        isPlaying = false;
    }
}

