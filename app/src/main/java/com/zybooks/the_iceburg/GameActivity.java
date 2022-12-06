package com.zybooks.the_iceburg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private GameView gameView;

    private SensorManager sensorManager;

    private int priorRotation;
    private int currentLevel = 1;

    public int[] unlockedCostumes = {1,0,0,0,0,0,0,0};
    public int[] achievements = {0,0,0,0,0,0,0,0};

    private final String UNLOCKED_COSTUMES = "unlockedCostumes";
    private final String ACHIEVEMENTS = "achievements";

    private final String CURRENT_PROGRESS = "currentProgress";
    private final String CURRENT_GRAVITY = "currentGravity";
    private final String LAST_ROTATION = "lastOrientation";
    private final String SAVED_FLOOR_COLLIDER = "savedFloorCol";
    private final String SAVED_GROUNDED_STATE = "savedGrounded";
    private final String SAVED_OBSTACLE_NEARBY = "savedObstacleNear";
    private final String SAVED_LEVEL = "savedLevel";
    private final String SAVED_INTERACTS = "savedInteracts";
    private final String SAVED_ENVIRONMENT= "savedEnvironment";
    private final String SAVED_INT_REPLY = "savedIntProgress";
    private final String SAVED_PROMPTS = "savedPrompts";
    private final String SAVED_CURRENT_INTERACT = "savedCurrentInteract";
    private final String SAVED_COLLECTIBLES = "savedCollectibles";
    private final String SAVED_BARRIERS = "savedBarriers";
    private final String SAVED_OBSTACLE_LAYOUT = "savedObstLayout";
    private final String SAVED_INITIAL_3 = "savedlevel3obstactles";
    private final String ENDING = "ending";

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        int costumeId;
        Intent transfer = getIntent();
        costumeId = getApplicationContext().getSharedPreferences(getString(R.string.shared_storage_name), MODE_PRIVATE).getInt(getString(R.string.current_costume), 0);
        if(saveInstanceState != null) {
            currentLevel = saveInstanceState.getInt(SAVED_LEVEL);
            unlockedCostumes = saveInstanceState.getIntArray(UNLOCKED_COSTUMES);
            achievements = saveInstanceState.getIntArray(ACHIEVEMENTS);
        }
        gameView = new GameView(this, point.x, point.y, costumeId,currentLevel, unlockedCostumes,achievements);
        if(saveInstanceState == null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && priorRotation == 2) {
                priorRotation = 1;
            } else {
                priorRotation = 2;
            }
        }
        if(saveInstanceState != null) {
            gameView.progress = saveInstanceState.getInt(CURRENT_PROGRESS);
            priorRotation = saveInstanceState.getInt(LAST_ROTATION);
            gameView.grounded = saveInstanceState.getBoolean(SAVED_GROUNDED_STATE);
            gameView.intables.layout = saveInstanceState.getIntArray(SAVED_INTERACTS);
            gameView.env.layout = saveInstanceState.getIntArray(SAVED_ENVIRONMENT);
            gameView.gravity = saveInstanceState.getFloat(CURRENT_GRAVITY);
            gameView.floorColBelow = saveInstanceState.getInt(SAVED_FLOOR_COLLIDER);
            gameView.obstNear = saveInstanceState.getInt(SAVED_OBSTACLE_NEARBY);
            gameView.intables.isPrompt = saveInstanceState.getIntArray(SAVED_PROMPTS);
            gameView.intables.response = saveInstanceState.getIntArray(SAVED_INT_REPLY);
            gameView.interact_num = saveInstanceState.getInt(SAVED_CURRENT_INTERACT);
            gameView.collectibles.layout = saveInstanceState.getIntArray(SAVED_COLLECTIBLES);
            gameView.nextBarrier = saveInstanceState.getInt(SAVED_BARRIERS);
            gameView.env.obstacles = saveInstanceState.getIntArray(SAVED_OBSTACLE_LAYOUT);
            gameView.env.initialLevel3Obst = saveInstanceState.getIntArray(SAVED_INITIAL_3);
            gameView.gameEnd = saveInstanceState.getInt(ENDING);
        }
        setContentView(gameView);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if(sensorManager !=null) {
            Sensor ss = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (ss != null) {
                sensorManager.registerListener(this, ss, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    @Override
    public void onSaveInstanceState (@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && priorRotation == 2) {
            outState.putInt(CURRENT_PROGRESS, gameView.progress_portrait);
        }
        else {
            outState.putInt(CURRENT_PROGRESS, gameView.progress_landscape);
        }
        outState.putIntArray(UNLOCKED_COSTUMES, gameView.costumesUnlocked);
        outState.putIntArray(ACHIEVEMENTS, gameView.advancements);
        unlockedCostumes = gameView.costumesUnlocked;
        achievements = gameView.advancements;

        currentLevel = gameView.currentLevel;
        outState.putInt(SAVED_LEVEL, currentLevel);
        outState.putBoolean(SAVED_GROUNDED_STATE,gameView.grounded);
        outState.putFloat(CURRENT_GRAVITY, gameView.gravity);
        outState.putInt(LAST_ROTATION, priorRotation);
        outState.putInt(SAVED_FLOOR_COLLIDER, gameView.floorColBelow);
        outState.putInt(SAVED_OBSTACLE_NEARBY, gameView.obstNear);
        outState.putInt(SAVED_CURRENT_INTERACT,gameView.interact_num);
        outState.putInt(SAVED_BARRIERS,gameView.nextBarrier);
        outState.putInt(ENDING,gameView.gameEnd);

        outState.putIntArray(SAVED_INTERACTS,gameView.intables.layout);
        outState.putIntArray(SAVED_ENVIRONMENT,gameView.env.layout);
        outState.putIntArray(SAVED_COLLECTIBLES,gameView.collectibles.layout);
        outState.putIntArray(SAVED_PROMPTS, gameView.intables.isPrompt);
        outState.putIntArray(SAVED_INT_REPLY, gameView.intables.response);
        outState.putIntArray(SAVED_OBSTACLE_LAYOUT,gameView.env.obstacles);
        outState.putIntArray(SAVED_INITIAL_3,gameView.env.initialLevel3Obst);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override

    public boolean onTouchEvent(MotionEvent event){

        int x = (int)event.getX();
        int y = (int)event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!gameView.prompt) {
                    if ((x > 30 && x < 230) &&
                            (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) {
                        gameView.backgroundMovement(-1);
                    }
                    if ((x > 260 && x < 460) &&
                            (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) {
                        gameView.backgroundMovement(1);
                    }

                    if (((x < (gameView.screenX - 30) && x > (gameView.screenX - 230)) &&
                            (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30))) {
                        gameView.setJump(true);
                    }

                    if ((x < (gameView.screenX - 260) && x > (gameView.screenX - 460)) &&
                            (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) {
                        gameView.setInteract(true);
                    }
                }
                else {
                    if ((x > 30 && x < 230) &&
                            (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30) && !gameView.answered) {
                        gameView.interactResult(true);
                    }
                    if (((x < (gameView.screenX - 30) && x > (gameView.screenX - 230)) &&
                            (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) && !gameView.answered) {
                        gameView.interactResult(false);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                gameView.backgroundMovement(0);
                gameView.setJump(false);
                gameView.setInteract(false);
                break;
        }
        return true;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER && gameView.currentLevel == 1) {
            if(event.values[1] < -8.7) {
                gameView.toSpace = true;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}