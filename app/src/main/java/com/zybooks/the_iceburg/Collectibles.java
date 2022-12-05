package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class Collectibles extends SurfaceView {

    public Drawable[] collectibles = new Drawable[7];

    //----------------------------------------- Level 1 -------------------------------------------
    public int[] layout = {1,2};
    public int[] offsetX = {2000,14200};
    public int[] offsetY = {1400,1600};
    public Context contx;
    // ---------------------------------- Level 2 -----------------------------------------
    public int[] layout_2 = {1,3,1,4};
    public int[] offsetX_2 = {4000,9500,16000,26000};
    public int[] offsetY_2 = {1600,1500,1600,1600};
    // ---------------------------------- Level 3 -----------------------------------------
    public int[] layout_3 = {5,6};
    public int[] offsetX_3 = {6000,12000};
    public int[] offsetY_3 = {1500,1000};

    public Collectibles(Context context) {
        super(context);
        contx = context;

        collectibles[0] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
        collectibles[1] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.key_basic, null);
        collectibles[2] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t_m, null);
        collectibles[3] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_idle_m, null);
        collectibles[4] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_idle, null);
        collectibles[5] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_idle, null);
        collectibles[6] = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crabcycle, null);
    }

    public void NewLevel(int level) {
        switch (level) {
            case 2:
                layout = layout_2;
                offsetX = offsetX_2;
                offsetY = offsetY_2;
                break;
            case 3:
                layout = layout_3;
                offsetX = offsetX_3;
                offsetY = offsetY_3;
                break;
        }
    }
}