package com.zybooks.the_iceburg;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class GameBackground {

    int x, y;
    Bitmap background;

    GameBackground (int screenX, int screenY, Resources res) {
        background = BitmapFactory.decodeResource(res,R.drawable.background_level_1);
        background = Bitmap.createScaledBitmap(background,screenX,screenY, true);
    }
}
