package com.zybooks.the_iceburg;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GameBackground {

    int x, y;
    Bitmap background;

    GameBackground (int level,int screenX, int screenY, Resources res) {
        switch(level) {
            case 1:
                background = BitmapFactory.decodeResource(res,R.drawable.background_level_1);
                background = Bitmap.createScaledBitmap(background,screenX,screenY, true);
                break;
            case 3:
                background = BitmapFactory.decodeResource(res,R.drawable.background_level_5);
                background = Bitmap.createScaledBitmap(background,screenX,screenY, true);
                break;
            default:
                background = BitmapFactory.decodeResource(res,R.drawable.background_level_2);
                background = Bitmap.createScaledBitmap(background,screenX,screenY, true);
                break;
        }
    }
}
