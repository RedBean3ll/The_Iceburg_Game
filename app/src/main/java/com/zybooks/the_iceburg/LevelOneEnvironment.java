package com.zybooks.the_iceburg;

import android.graphics.Canvas;

public class LevelOneEnvironment {

    //This number will go up and down depending on the location from left to right
    public float progression = 0;
    public int[] levelElement = new int[2];
    public int[] layout = new int[100];
    public LevelOneEnvironment(Canvas canvas) {
        levelElement[0] = R.drawable.floor_1;
        levelElement[1] = R.drawable.floor_cliff_left;
        levelElement[2] = R.drawable.water;
    }

}
