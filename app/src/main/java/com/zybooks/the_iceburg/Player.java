package com.zybooks.the_iceburg;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.SurfaceView;

import androidx.core.content.res.ResourcesCompat;

public class Player extends SurfaceView {

    public int costumeID = 1;
    public Drawable[] costume = new Drawable[12];

    public Player(Context context, int cosNum) {
        super(context);
        costumeID = cosNum;
        switch (costumeID){
            case 1:
                CostumeTwo();
                break;
            case 2:
                CostumeThree();
                break;
            case 3:
                CostumeFour();
                break;
            case 4:
                CostumeFive();
                break;
            case 5:
                CostumeSix();
                break;
            default:
                CostumeOne();
                break;
        }

    }

    public void CostumeOne () {
        // Idle
        costume[0] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_idle, null);
        costume[1] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_idle_m, null);
        // Walk right
        costume[2] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_walk, null);
        costume[3] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_walk_1, null);
        costume[4] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_walk_2, null);
        costume[5] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_walk, null);
        // Walk left
        costume[6] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_walk_m, null);
        costume[7] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_walk_1_m, null);
        costume[8] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_walk_2_m, null);
        costume[9] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_walk_m, null);
        // Jump
        costume[10] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_jump, null);
        costume[11] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.defaulto_jump_m, null);
    }

    public void CostumeTwo () {
        // Idle
        costume[0] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_idle, null);
        costume[1] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_idle_m, null);
        // Walk right
        costume[2] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_walk, null);
        costume[3] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_walk1, null);
        costume[4] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_walk2, null);
        costume[5] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_walk1, null);
        // Walk left
        costume[6] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_walk_m, null);
        costume[7] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_walk1_m, null);
        costume[8] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_walk2_m, null);
        costume[9] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_walk1_m, null);
        //Jump
        costume[10] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_jump, null);
        costume[11] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.animal_jump_m, null);
    }

    public void CostumeThree () {
        // Idle
        costume[0] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai, null);
        costume[1] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_m, null);
        // Walk right
        costume[2] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t, null);
        costume[3] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t, null);
        costume[4] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t, null);
        costume[5] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t, null);
        // Walk left
        costume[6] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t_m, null);
        costume[7] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t_m, null);
        costume[8] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t_m, null);
        costume[9] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_t_m, null);
        //Jump
        costume[10] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai, null);
        costume[11] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.moyai_m, null);
    }

    public void CostumeFour () {
        // Idle
        costume[0] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_idle, null);
        costume[1] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_idle, null);
        // Walk right
        costume[2] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_walk, null);
        costume[3] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_walk_1, null);
        costume[4] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_walk_2, null);
        costume[5] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_walk_1, null);
        // Walk left
        costume[6] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_walk_m, null);
        costume[7] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_walk_1_m, null);
        costume[8] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_walk_2_m, null);
        costume[9] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_walk_1_m, null);
        //Jump
        costume[10] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_jump, null);
        costume[11] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.harambe_jump_m, null);
    }

    public void CostumeFive () {
        // Idle
        costume[0] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_idle, null);
        costume[1] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_idle_m, null);
        // Walk right
        costume[2] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_walk, null);
        costume[3] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_walk_1, null);
        costume[4] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_walk_2, null);
        costume[5] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_walk_1, null);
        // Walk left
        costume[6] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_walk_m, null);
        costume[7] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_walk_1_m, null);
        costume[8] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_walk_2_m, null);
        costume[9] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_walk_1_m, null);
        //Jump
        costume[10] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_jump, null);
        costume[11] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.mattmug_jump_m, null);
    }

    public void CostumeSix () {
        // Idle
        costume[0] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_idle, null);
        costume[1] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_idle, null);
        // Walk right
        costume[2] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_walk, null);
        costume[3] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_idle, null);
        costume[4] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_walk_2, null);
        costume[5] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_idle, null);
        // Walk left
        costume[6] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_walk, null);
        costume[7] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_idle, null);
        costume[8] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_walk_2, null);
        costume[9] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_idle, null);
        //Jump
        costume[10] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_jump, null);
        costume[11] =  ResourcesCompat.getDrawable(getContext().getResources(), R.drawable.crab_jump, null);
    }
}
