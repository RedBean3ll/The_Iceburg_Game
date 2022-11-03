package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class Interacables extends SurfaceView {

    private int screen_x, screen_y;

    public int progress = 0;
    public Drawable[] interacts = new Drawable[1];
    public int[] layout = {0};
    public Drawable sign;
    public Context contx;

    public Interacables(Context context, int screenX, int screenY) {
        super(context);

        contx = context;
        screen_x = screenX;
        screen_y = screenY;

        sign = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.sign, null);

        interacts[0] = sign;
    }

    public void getInteraction(int i) {
        switch (i) {
            case 0:
                Log.e("YES", "YES INTERACT");
        }
    }
}
