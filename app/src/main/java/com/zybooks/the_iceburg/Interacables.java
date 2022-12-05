package com.zybooks.the_iceburg;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.SurfaceView;
import androidx.core.content.res.ResourcesCompat;

@SuppressLint("ViewConstructor")
public class Interacables extends SurfaceView {

    public int currentLevel;
    public Drawable[] interacts = new Drawable[11];
    public Drawable[] int_response = new Drawable[18];

    // ---------------------------------- Level 1 -----------------------------------------
    public int[] isPrompt = {0,0,0,1,0,0,0,0,0};
    public int[] layout = {0,0,1,6,2,2,2,4,0};
    public int[] response = {1,2,3,4,0,0,0,0,7};
    public int[] offsetX = {1100,1800,2500,7200,8200,8500,8800,9300,14200};
    public int[] offsetY = {400,400,400,400,400,400,400,400,400};

    // ---------------------------------- Level 2 -----------------------------------------
    public int[] isPrompt_2 = {1,0,0,0,0,0,0,0,0};
    public int[] layout_2 = {7,0,0,0,2,2,2,4,0};
    public int[] response_2 = {8,9,10,11,0,0,0,0,12};
    public int[] offsetX_2 = {4800,7000,12000,13700,18000,18300,18600,19100,30000};
    public int[] offsetY_2 = {400,400,400,400,400,400,400,400,400};
    // ---------------------------------- Level 3 -----------------------------------------
    public int[] isPrompt_3 = {0,0,1,0,0};
    public int[] layout_3 = {8,9,10,4,0};
    public int[] response_3 = {13,14,15,0,17};
    public int[] offsetX_3 = {2500,7500,16000,18000,23000};
    public int[] offsetY_3 = {400,400,400,1600,400};


    public Drawable sign_1,bob,lever_up,lever_down,button,button_pressed,skyman,nerd,incognito,squid,bob_corrupt;
    public Drawable sign_response_1,rotation_tutorial,bob_resp_1,sky_response,end_1,nerd_response,sign_response_2,sign_response_3,sign_response_4,sign_response_5,incog_resp, squid_resp,bob_resp_2,denial,ending;
    public Drawable yes,no;
    public Context contx;

    public int[] solution = {0,0,0,0,1,2,1,0,0};
    public int[] solution2 = {0,0,0,0,2,2,1,0};
    public int[] solution3 = {0,0,0,0,2,2,1,0,0};

    public Interacables(Context context, int currentLevel) {
        super(context);

        this.currentLevel = currentLevel;
        contx = context;

        sign_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign, null);
        bob = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bob, null);
        lever_up = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.lever_unpulled, null);
        lever_down = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.lever_pulled, null);
        button = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_unpressed, null);
        button_pressed = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.button_pressed, null);
        skyman = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sky_lover, null);
        nerd = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.cave_nerd, null);
        incognito = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.incognito_man, null);
        squid = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.squid_game, null);
        bob_corrupt = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bob_corrupt, null);

        sign_response_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_tutorial, null);
        rotation_tutorial = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.rotation_advice, null);
        bob_resp_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bob_speak_1, null);
        sky_response = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sky_question, null);
        yes = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.correct, null);
        no = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.wrong, null);
        end_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_end_level_one, null);
        nerd_response = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.nerd_puzzle, null);
        sign_response_2 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_resp_2, null);
        sign_response_3 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_resp_3, null);
        sign_response_4 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_resp_4, null);
        sign_response_5 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_resp_5, null);
        incog_resp = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.incog_resp, null);
        squid_resp = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.squid_resp, null);
        bob_resp_2 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.blueberry_puzzle, null);
        denial = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.blueberry_correct, null);
        ending = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.the_end, null);


        interacts[0] = sign_1;
        interacts[1] = bob;
        interacts[2] = lever_up;
        interacts[3] = lever_down;
        interacts[4] = button;
        interacts[5] = button_pressed;
        interacts[6] = skyman;
        interacts[7] = nerd;
        interacts[8] = incognito;
        interacts[9] = squid;
        interacts[10] = bob_corrupt;

        int_response[0] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
        int_response[1] = sign_response_1;
        int_response[2] = rotation_tutorial;
        int_response[3] = bob_resp_1;
        int_response[4] = sky_response;
        int_response[5] = yes;
        int_response[6] = no;
        int_response[7] = end_1;
        int_response[8] = nerd_response;
        int_response[9] = sign_response_2;
        int_response[10] = sign_response_3;
        int_response[11] = sign_response_4;
        int_response[12] = sign_response_5;
        int_response[13] = incog_resp;
        int_response[14] = squid_resp;
        int_response[15] = bob_resp_2;
        int_response[16] = denial;
        int_response[17] = ending;
    }

    public boolean leverPuzzle (int bridgeNum, boolean lever1, boolean lever2, boolean lever3) {
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

    public void flip (int index) {
        switch (layout[index]) {
            case 2:
                layout[index] = 3;
                return;
            case 3:
                layout[index] = 2;
                return;
            case 4:
                layout[index] = 5;
        }
    }

    public void NewLevel (int level) {
        switch (level) {
            case 2:
                isPrompt = isPrompt_2;
                layout = layout_2;
                response = response_2;
                offsetX = offsetX_2;
                offsetY = offsetY_2;
                solution = solution2;
                break;
            case 3:
                isPrompt = isPrompt_3;
                layout = layout_3;
                response = response_3;
                offsetX = offsetX_3;
                offsetY = offsetY_3;
                solution = solution3;
                break;
        }
    }
}
