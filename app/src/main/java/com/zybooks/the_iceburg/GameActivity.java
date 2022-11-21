package com.zybooks.the_iceburg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private int savedProgress;
    private float currentGravity;
    private int savedFloorCol;
    private boolean savedGrounded;
    private int savedObstacleNear;

    private final String CURRENT_PROGRESS = "currentProgress";
    private final String CURRENT_GRAVITY = "currentGravity";

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);
        if(saveInstanceState != null && gameView != null) {
            savedProgress = saveInstanceState.getInt(CURRENT_PROGRESS);
            currentGravity = saveInstanceState.getFloat(CURRENT_GRAVITY);
            gameView.gravity = currentGravity;
            gameView.progress = savedProgress;
        }
        setContentView(gameView);
    }

    @Override
    public void onSaveInstanceState (@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            outState.putInt(CURRENT_PROGRESS, gameView.progress_portrait);
        }
        else {
            outState.putInt(CURRENT_PROGRESS, gameView.progress_landscape);
        }
        outState.putFloat(CURRENT_GRAVITY, gameView.gravity);
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
                if((x > 30 && x < 230) &&
                (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) {
                    gameView.backgroundMovement(-1);
                }
                if((x > 260 && x < 460) &&
                        (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) {
                    gameView.backgroundMovement(1);
                }

                if(((x < (gameView.screenX - 30) && x > (gameView.screenX - 230)) &&
                        (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30))) {
                    gameView.setJump(true);
                }

                if((x < (gameView.screenX - 260) && x > (gameView.screenX - 460)) &&
                        (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) {
                    gameView.setInteract(true);
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