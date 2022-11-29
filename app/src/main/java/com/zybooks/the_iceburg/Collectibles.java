package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class Collectibles extends SurfaceView {


    private int screen_x, screen_y;

    public Drawable collectibles[] = new Drawable[2];

    //----------------------------------------- Level 1 -------------------------------------------
    public int[] layout = {1};
    public int[] offsetX = {2000};
    public int[] offsetY = {1400};
    public Context contx;
    // ---------------------------------- Level 2 -----------------------------------------
    public int[] layout_2 = {};
    public int[] offsetX_2 = {};
    public int[] offsetY_2 = {};
    // ---------------------------------- Level 3 -----------------------------------------
    // ---------------------------------- Level 4 -----------------------------------------
    // ---------------------------------- Level 5 -----------------------------------------

    public Collectibles(Context context, int screenX, int screenY) {
        super(context);
        contx = context;

        collectibles[0] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
        collectibles[1] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.key_basic, null);

    }

    public void NewLevel() {
        layout = layout_2;
        offsetX = offsetX_2;
        offsetY = offsetY_2;
    }
}