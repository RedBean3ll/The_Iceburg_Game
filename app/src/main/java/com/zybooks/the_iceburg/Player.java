package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class Player extends SurfaceView {

    public int costumeID = 0;
    public Drawable[] costume = new Drawable[5];

    public Player(Context context, int cosNum) {
        super(context);
        costumeID = cosNum;
        switch (costumeID){
            default:
                CostumeOne();
        }

    }

    public void CostumeOne () {
        costume[0] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai, null);
        costume[1] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t, null);
        costume[2] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t, null);
        costume[3] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t, null);
        costume[4] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai, null);
    }
}
