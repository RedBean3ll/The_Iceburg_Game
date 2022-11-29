package com.zybooks.the_iceburg;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;
import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

public class LevelEnvironment extends SurfaceView{

    //This number will go up and down depending on the location from left to right

    private int screen_x, screen_y;
    public Drawable short_ledge, float_ledge;

    private int currentLevel = 1;
    public int progress = 0;
    public Drawable[] levelElement = new Drawable[5];
    public Drawable[] levelObstacle = new Drawable[3];

    public int[] layout = {1,2,0,3,2,0,3,2,0};
    public int[] layout_2 = {1,1,1,2,0,3,2,0,3,2,1,1,1,1,1,1,2,0};

    public int[] obstBuffer = {1200,1900,3700,4400,5100};
    public int[] obstBuffer_2 = {1600,2400};

    public int[] obstWidth = {600,600,600,600,600};
    public int[] obstWidth_2 = {400,600};

    public int[] obstBuffer_vert = {600,800,400,600,400};
    public int[] obstBuffer_vert_2 = {400,900};

    public int[] obstacles = {1,2,2,2,2};
    public int[] obstacles_2 = {3,2};

    public int[] barrierLocation = {2500,22000};
    public int[] barrierLocation_2 = {15000,40000};

    public int[] levelEnds = {15000,37700};

    public Drawable empty,ice_floor, ice_cliff_left, ice_cliff_right, water, bridge, move_box;

    public LevelEnvironment(Context context, int screenX, int screenY, int currentLevel) {
        super(context);
        this.currentLevel = currentLevel;
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
                move_box = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.wood_crate, null);

                levelObstacle[0] = short_ledge;
                levelObstacle[1] = float_ledge;
                levelObstacle[2] = move_box;

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
                move_box = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.wood_crate, null);

                levelObstacle[0] = short_ledge;
                levelObstacle[1] = float_ledge;
                levelObstacle[2] = move_box;

                //-------------------------------------------- Water --------------------------------------------------
                water = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.water, null);
                break;
        }
    }

    public void NewLevel () {
        switch (currentLevel) {
            case 2:
                layout = layout_2;
                obstBuffer = obstBuffer_2;
                obstBuffer_vert = obstBuffer_vert_2;
                obstWidth = obstWidth_2;
                obstacles = obstacles_2;
                barrierLocation = barrierLocation_2;
                break;
            case 3:
                break;
        }
    }

}
