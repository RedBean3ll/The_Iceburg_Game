package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class Interacables extends SurfaceView {

    private int screen_x, screen_y;

    public int progress = 0;
    public Drawable[] interacts = new Drawable[2];
    public Drawable[] int_response = new Drawable[2];
    public int[] layout = {0,1};
    public int[] response = {0,1};
    public int[] offsetX = {2000,2500};
    public int[] offsetY = {400,400};
    public Drawable sign_1,bob;
    public Drawable sign_response_1,bob_resp_1;
    public Context contx;

    public Interacables(Context context, int screenX, int screenY) {
        super(context);

        contx = context;
        screen_x = screenX;
        screen_y = screenY;

        sign_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign, null);
        bob = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bob, null);

        sign_response_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign_tutorial, null);
        bob_resp_1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bob_speak_1, null);

        interacts[0] = sign_1;
        interacts[1] = bob;

        int_response[0] = sign_response_1;
        int_response[1] = bob_resp_1;
    }

    public void getInteraction(int i) {
        switch (i) {
            case 0:
                Log.e("YES", "YES INTERACT");
        }
    }
}
