package com.zybooks.the_iceburg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.os.Bundle;

public class CostumesActivity extends AppCompatActivity{
    public static String EXTRA_COSTUME = "costume id";

    ImageButton defaulto;
    ImageButton moyai;
    ImageButton animal;
    ImageButton mug;
    ImageButton harambe;
    ImageButton crab;
    ImageButton pill;
    ImageButton space;

    SharedPreferences gameStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.costumes_menu);

        defaulto = findViewById(R.id.costume_defaulto);
        moyai = findViewById(R.id.costume_moyai);
        animal = findViewById(R.id.costume_animal);
        mug = findViewById(R.id.costume_mug);
        harambe = findViewById(R.id.costume_harambe);
        crab = findViewById(R.id.costume_crab);
        pill = findViewById(R.id.costume_pill);
        space = findViewById(R.id.costume_space);

        initial();
    }

    public void initial() {
        gameStorage = getApplicationContext().getSharedPreferences(getString(R.string.shared_storage_name), MODE_PRIVATE);

        unlockCostumePanel(0);
        if(gameStorage.getBoolean(getString(R.string.flag_costume_1), false)) { unlockCostumePanel(1); }
        if(gameStorage.getBoolean(getString(R.string.flag_costume_2), false)) { unlockCostumePanel(2); }
        if(gameStorage.getBoolean(getString(R.string.flag_costume_3), false)) { unlockCostumePanel(3); }
        if(gameStorage.getBoolean(getString(R.string.flag_costume_4), false)) { unlockCostumePanel(4); }
        if(gameStorage.getBoolean(getString(R.string.flag_costume_5), false)) { unlockCostumePanel(5); }
        if(gameStorage.getBoolean(getString(R.string.flag_costume_6), false)) { unlockCostumePanel(6); }
        if(gameStorage.getBoolean(getString(R.string.flag_costume_7), false)) { unlockCostumePanel(7); }
    }

    public void unlockCostumePanel(int costumeNumber) {

        switch(costumeNumber) {
            case 1:
                animal.setOnClickListener(viewC -> setCostume(1));
                animal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.animal_idle));
                animal.setBackgroundTintList(null);
                break;
            case 2:
                moyai.setOnClickListener(viewC -> setCostume(2));
                moyai.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moyai));
                moyai.setBackgroundTintList(null);
                break;
            case 3:
                harambe.setOnClickListener(viewC -> setCostume(3));
                harambe.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.harambe_idle));
                harambe.setBackgroundTintList(null);
                break;
            case 4:
                mug.setOnClickListener(viewC -> setCostume(4));
                mug.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.mattmug_idle));
                mug.setBackgroundTintList(null);
                break;
            case 5:
                crab.setOnClickListener(viewC -> setCostume(5));
                crab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.crab_idle));
                crab.setBackgroundTintList(null);
                break;
            case 6:
                pill.setOnClickListener(viewC -> setCostume(6));
                pill.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pill_no));
                pill.setBackgroundTintList(null);
                break;
            case 7:
                space.setOnClickListener(viewC -> setCostume(7));
                space.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.spaceman_idle));
                space.setBackgroundTintList(null);
            default:
                defaulto.setOnClickListener(viewC -> setCostume(0));
                defaulto.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.defaulto_idle));
                defaulto.setBackgroundTintList(null);
        }
    }

    private void setCostume(int costumeNumber) {
        SharedPreferences.Editor editor = gameStorage.edit();
        editor.putInt(getString(R.string.current_costume), costumeNumber);
        editor.apply();
        finish();
    }
}