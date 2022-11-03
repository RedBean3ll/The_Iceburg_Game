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
    private Drawable short_ledge;

    public int progress = 0;
    public Drawable[] levelElement = new Drawable[4];
    public Drawable[] levelObstacle = new Drawable[1];
    public int[] layout = {1,2,0,3,1,1,1};
    public int[] obstacles = {};

    public Drawable empty,ice_floor, ice_cliff_left, ice_cliff_right, water;

    public LevelOneEnvironment(Context context, int screenX, int screenY) {
        super(context);

        screen_x = screenX;
        screen_y = screenY;

        //-------------------------------------------- Main Floor Elements --------------------------------------------------
        empty = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
        ice_floor = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_1, null);
        ice_cliff_left = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_cliff_left, null);
        ice_cliff_right = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_cliff_right, null);

        levelElement[0] = empty;
        levelElement[1] = ice_floor;
        levelElement[2] = ice_cliff_left;
        levelElement[3] = ice_cliff_right;

        //-------------------------------------------- Obstacles --------------------------------------------------
        short_ledge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ice_platform, null);

        levelObstacle[0] = short_ledge;

        //-------------------------------------------- Water --------------------------------------------------
        water = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.water, null);

    }

}
