package com.zybooks.the_iceburg;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;
import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

public class LevelOneEnvironment extends SurfaceView{

    //This number will go up and down depending on the location from left to right

    private int screen_x, screen_y;
    public Drawable short_ledge, float_ledge;

    private int currentLevel = 1;
    public int progress = 0;
    public Drawable[] levelElement = new Drawable[5];
    public Drawable[] levelObstacle = new Drawable[2];

    public int[] layout = {1,2,0,3,1,1,2,0,3,2,0,3,1,1,2,0,3,1,2,0};
    public int[] layout_2 = {1,1,1,2,0,3,2,0,3,2,1,1,1,1,1,1,1,1,2,0};

    public int[] obstBuffer = {1200,1900,3600,4300,5000,7777};
    public int[] obstWidth = {600,600,600,600,600,600};
    public int[] obstBuffer_vert = {600,800,400,600,400,400};

    public int[] obstacles = {1,2,2,2,2,2};

    public Drawable empty,ice_floor, ice_cliff_left, ice_cliff_right, water, bridge;

    public LevelOneEnvironment(Context context, int screenX, int screenY) {
        super(context);

        screen_x = screenX;
        screen_y = screenY;

        switch (currentLevel) {
            case 5:
            //-------------------------------------------- Main Floor Elements --------------------------------------------------
                empty = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
                ice_floor = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_5, null);
                ice_cliff_left = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_cliff_left, null);
                ice_cliff_right = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_cliff_right, null);
                bridge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bridge_back, null);

                levelElement[0] = empty;
                levelElement[1] = ice_floor;
                levelElement[2] = ice_cliff_left;
                levelElement[3] = ice_cliff_right;

                //-------------------------------------------- Obstacles --------------------------------------------------
                short_ledge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ice_platform, null);
                float_ledge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ice_platform_float, null);

                levelObstacle[0] = short_ledge;
                levelObstacle[1] = float_ledge;

                //-------------------------------------------- Water --------------------------------------------------
                water = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.water, null);
                break;
            default:
                //-------------------------------------------- Main Floor Elements --------------------------------------------------
                empty = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
                ice_floor = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_1, null);
                ice_cliff_left = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_cliff_left, null);
                ice_cliff_right = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_cliff_right, null);
                bridge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bridge_back, null);

                levelElement[0] = empty;
                levelElement[1] = ice_floor;
                levelElement[2] = ice_cliff_left;
                levelElement[3] = ice_cliff_right;

                //-------------------------------------------- Obstacles --------------------------------------------------
                short_ledge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ice_platform, null);
                float_ledge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ice_platform_float, null);

                levelObstacle[0] = short_ledge;
                levelObstacle[1] = float_ledge;

                //-------------------------------------------- Water --------------------------------------------------
                water = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.water, null);
                break;
        }
    }

}
