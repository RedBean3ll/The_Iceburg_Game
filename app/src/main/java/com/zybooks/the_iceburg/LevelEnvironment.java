package com.zybooks.the_iceburg;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.SurfaceView;
import android.content.Context;

import androidx.core.content.res.ResourcesCompat;

@SuppressLint("ViewConstructor")
public class LevelEnvironment extends SurfaceView{

    //This number will go up and down depending on the location from left to right

    public Drawable short_ledge, float_ledge;

    public int progress = 0;
    public Drawable[] levelElement = new Drawable[5];
    public Drawable[] levelObstacle = new Drawable[13];

    public int[] layout = {1,2,0,3,2,0,3,2,0};
    public int[] layout_2 = {1,1,1,2,0,3,2,0,3,2,1,2,0,0,3,1,2,0};
    public int[] layout_3 = {0,3,1,2,0,0,0,3,1,2,0,3,2,0};

    public int[] obstBuffer = {1200,1900,3700,4400,5100,13000,13600,14000};
    public int[] obstBuffer_2 = {1600,2400,3000,3600,4200,7600,8600,9100,9600,10200,10700,11000,11800,12100,12500,12800};
    public int[] obstBuffer_3 = {600};

    public int[] obstWidth = {600,600,600,600,600,300,300,700};
    public int[] obstWidth_2 = {400,600,600,600,600,800,400,200,300,200,200,500,200,200,200,500};
    public int[] obstWidth_3 = {500};

    public int[] obstBuffer_vert = {600,800,400,600,400,800,1200,1400};
    public int[] obstBuffer_vert_2 = {400,600,600,600,600,700,500,1300,300,600,600,300,600,600,600,300};
    public int[] obstBuffer_vert_3 = {190};

    public int[] obstacles = {1,2,2,2,2,2,2,2};
    public int[] obstacles_2 = {3,1,1,1,1,2,2,2,2,1,1,1,1,1,1,1};
    public int[] obstacles_3 = {2};

    public int[] barrierLocation = {2500,22000};
    public int[] barrierLocation_2 = {5000,20000,40000};
    public int[] barrierLocation_3 = {};

    public int[] levelEnds = {15000,37700,25000};

    public Drawable empty,ice_floor, ice_cliff_left, ice_cliff_right, water, bridge, move_box, m1,m2,m3,m4,m5,m6,m7;

    public LevelEnvironment(Context context, int currentLevel) {
        super(context);

        if (currentLevel == 3) {//-------------------------------------------- Main Floor Elements --------------------------------------------------
            empty = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.empty, null);
            ice_floor = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_5, null);
            ice_cliff_left = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_cliff_left_5, null);
            ice_cliff_right = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.floor_cliff_right_5, null);
            bridge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.bridge_back, null);

            levelElement[0] = empty;
            levelElement[1] = ice_floor;
            levelElement[2] = ice_cliff_left;
            levelElement[3] = ice_cliff_right;

            //-------------------------------------------- Obstacles --------------------------------------------------
            short_ledge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ice_platform_5, null);
            float_ledge = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ice_platform_float_5, null);
            m1 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.knee_surgery, null);
            m2 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.cowboy, null);
            m3 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.iphone, null);
            m4 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.twentyone, null);
            m5 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.industrialism, null);
            m6 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.ivomited, null);
            m7 = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.wowyeardoge, null);

            levelObstacle[0] = short_ledge;
            levelObstacle[1] = float_ledge;
            levelObstacle[2] = m1;
            levelObstacle[3] = m2;
            levelObstacle[4] = m3;
            levelObstacle[5] = m4;
            levelObstacle[6] = m5;
            levelObstacle[7] = m6;
            levelObstacle[8] = m7;

            //-------------------------------------------- Water --------------------------------------------------
            water = ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.water, null);
        } else {//-------------------------------------------- Main Floor Elements --------------------------------------------------
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
        }
    }

    public void NewLevel (int level) {
        switch (level) {
            case 2:
                layout = layout_2;
                obstBuffer = obstBuffer_2;
                obstBuffer_vert = obstBuffer_vert_2;
                obstWidth = obstWidth_2;
                obstacles = obstacles_2;
                barrierLocation = barrierLocation_2;
                break;
            case 3:
                layout = layout_3;
                obstBuffer = obstBuffer_3;
                obstBuffer_vert = obstBuffer_vert_3;
                obstWidth = obstWidth_3;
                obstacles = obstacles_3;
                barrierLocation = barrierLocation_2;
                break;
        }
    }

}
