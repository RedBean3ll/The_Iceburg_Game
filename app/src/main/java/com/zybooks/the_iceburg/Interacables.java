package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class Interacables extends SurfaceView {

    private int screen_x, screen_y;

    public int progress = 0;
    public Drawable[] interacts = new Drawable[6];
    public Drawable[] int_response = new Drawable[3];
    public int[] isPrompt = {0,0,0,0,0,0};
    public int[] layout = {0,1,2,2,2,4};
    public int[] response = {1,2,0,0,0,0};
    public int[] offsetX = {2000,2500,12200,12500,12800,13600};
    public int[] offsetY = {400,400,400,400,400,400};
    public Drawable sign_1,bob,lever_up,lever_down,button,button_pressed;
    public Drawable sign_response_1,bob_resp_1;
    public Context contx;

    public int solution1[] = {0,0,1,2,1,0};
    public Interacables(Context context, int screenX, int screenY) {
        super(context);

        contx = context;
        screen_x = screenX;
        screen_y = screenY;

        sign_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign, null);
        bob = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bob, null);
        lever_up = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.lever_unpulled, null);
        lever_down = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.lever_pulled, null);
        button = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_unpressed, null);
        button_pressed = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_pressed, null);

        sign_response_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_tutorial, null);
        bob_resp_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bob_speak_1, null);

        interacts[0] = sign_1;
        interacts[1] = bob;
        interacts[2] = lever_up;
        interacts[3] = lever_down;
        interacts[4] = button;
        interacts[5] = button_pressed;

        int_response[0] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
        int_response[1] = sign_response_1;
        int_response[2] = bob_resp_1;
    }

    public boolean leverPuzzle (int bridgeNum, boolean lever1, boolean lever2, boolean lever3) {
        boolean correct = false;
        switch(bridgeNum) {
            case 1 :
                if(lever1 && !lever2 && lever3) {
                    return true;
                }
                break;
            case 2:
                if(lever1 && lever2 && !lever3) {
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean flip (int index) {
        switch (layout[index]) {
            case 2:
                layout[index] = 3;
                return true;
            case 3:
                layout[index] = 2;
                return true;
            case 4:
                layout[index] = 5;
                return true;
        }
        return true;
    }
}
