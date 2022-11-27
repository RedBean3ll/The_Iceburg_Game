package com.zybooks.the_iceburg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    private int priorRotation;

    private final String CURRENT_PROGRESS = "currentProgress";
    private final String CURRENT_GRAVITY = "currentGravity";
    private final String LAST_ROTATION = "lastOrientation";
    private final String SAVED_FLOOR_COLLIDER = "savedFloorCol";
    private final String SAVED_GROUNDED_STATE = "savedGrounded";
    private final String SAVED_OBSTACLE_NEARBY = "savedObstacleNear";

    private final String SAVED_INTERACTS = "savedInteracts";
    private final String SAVED_ENVIRONMENT= "savedEnvironment";
    private final String SAVED_INT_REPLY = "savedIntProgress";
    private final String SAVED_PROMPTS = "savedPrompts";

    @Override
    protected void onCreate(Bundle saveInstanceState) {

        super.onCreate(saveInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        int costumeId;
        Intent transfer = getIntent();
        costumeId = transfer.getIntExtra(CostumesActivity.EXTRA_COSTUME, 0);
        Log.e("CRA", String.valueOf(costumeId));
        gameView = new GameView(this, point.x, point.y, costumeId);
        if(saveInstanceState == null) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT && priorRotation == 2) {
                priorRotation = 1;
            } else {
                priorRotation = 2;
            }
        }
        if(saveInstanceState != null && gameView != null) {
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
        }
        setContentView(gameView);
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
        outState.putBoolean(SAVED_GROUNDED_STATE,gameView.grounded);
        outState.putFloat(CURRENT_GRAVITY, gameView.gravity);
        outState.putInt(LAST_ROTATION, priorRotation);
        outState.putIntArray(SAVED_INTERACTS,gameView.intables.layout);
        outState.putIntArray(SAVED_ENVIRONMENT,gameView.env.layout);
        outState.putInt(SAVED_FLOOR_COLLIDER, gameView.floorColBelow);
        outState.putInt(SAVED_OBSTACLE_NEARBY, gameView.obstNear);
        outState.putIntArray(SAVED_PROMPTS, gameView.intables.response);
        outState.putIntArray(SAVED_INT_REPLY, gameView.intables.isPrompt);
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
        String action = "";

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
                            (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30) && gameView.answered == false) {
                        gameView.interactResult(true);
                    }
                    if (((x < (gameView.screenX - 30) && x > (gameView.screenX - 230)) &&
                            (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) && gameView.answered == false) {
                        gameView.interactResult(false);
                    }
                }
                action = "ACTION_DOWN";
                break;
            case MotionEvent.ACTION_MOVE:
                action = "ACTION_MOVE";
                break;
            case MotionEvent.ACTION_UP:
                action = "ACTION_UP";
                gameView.backgroundMovement(0);
                gameView.setJump(false);
                gameView.setInteract(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                action = "ACTION_CANCEL";
                break;
        }

       /* Log.d("Touched Location", action + " x = " + event.getX() +
                " y = " + event.getY());*/
        return true;
    }
}