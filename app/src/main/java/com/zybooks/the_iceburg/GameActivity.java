package com.zybooks.the_iceburg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity {

    private GameView gameView;

    public boolean canJump = true;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);

        setContentView(gameView);
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
                        (y > (gameView.screenY - 230)) && y < (gameView.screenY - 30)) && canJump) {
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

        Log.d("deez", action + " x = " + event.getX() +
                " y = " + event.getY());
        return true;
    }
}