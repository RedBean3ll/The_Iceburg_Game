package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class Interacables extends SurfaceView {

    private int screen_x, screen_y;

    public int progress = 0;
    public Drawable[] interacts = new Drawable[8];
    public Drawable[] int_response = new Drawable[7];
    public int[] isPrompt = {0,0,0,1,0,0,0,0};
    public int[] layout = {0,0,1,6,2,2,2,4};
    public int[] response = {1,2,3,4,0,0,0,0};
    public int[] offsetX = {1100,1800,2500,8000,12200,12500,12800,13600};
    public int[] offsetY = {400,400,400,400,400,400,400,400};
    public Drawable sign_1,bob,lever_up,lever_down,button,button_pressed,skyman;
    public Drawable sign_response_1,rotation_tutorial,bob_resp_1,sky_response;
    public Drawable yes,no;
    public Context contx;

    public int solution1[] = {0,0,0,0,1,2,1,0};
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
        skyman = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sky_lover, null);


        sign_response_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_tutorial, null);
        rotation_tutorial = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.rotation_advice, null);
        bob_resp_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bob_speak_1, null);
        sky_response = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sky_question, null);
        yes = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.correct, null);
        no = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.wrong, null);

        interacts[0] = sign_1;
        interacts[1] = bob;
        interacts[2] = lever_up;
        interacts[3] = lever_down;
        interacts[4] = button;
        interacts[5] = button_pressed;
        interacts[6] = skyman;

        int_response[0] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
        int_response[1] = sign_response_1;
        int_response[2] = rotation_tutorial;
        int_response[3] = bob_resp_1;
        int_response[4] = sky_response;
        int_response[5] = yes;
        int_response[6] = no;
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
