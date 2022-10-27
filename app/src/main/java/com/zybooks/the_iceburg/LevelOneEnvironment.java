package com.zybooks.the_iceburg;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;
import android.content.Context;

public class LevelOneEnvironment extends SurfaceView{

    //This number will go up and down depending on the location from left to right

    private int screen_x, screen_y;

    public int progress = 0;
    public Drawable[] levelElement = new Drawable[3];
    public int[] layout = new int[100];
    public Drawable ice_floor, ice_cliff_left, water;

    public LevelOneEnvironment(Context context, Canvas canvas, int screenX, int screenY) {
        super(context);

        screen_x = screenX;
        screen_y = screenY;

        ice_floor = getResources().getDrawable(R.drawable.floor_1,null);
        ice_cliff_left = getResources().getDrawable(R.drawable.floor_cliff_left,null);
        water = getResources().getDrawable(R.drawable.water,null);

        levelElement[0] = ice_floor;
        levelElement[1] = ice_cliff_left;
        levelElement[2] = water;
    }

    public void drawFloor () {
        ice_floor.setBounds(2000 - progress,screen_y - 600, 5000 - progress,screen_y);
    }


}
