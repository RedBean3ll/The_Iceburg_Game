package com.zybooks.the_iceburg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
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

        setCostumeDetail(0);
        if(true) { setCostumeDetail(1); }
        if(true) { setCostumeDetail(2); }
        if(true) { setCostumeDetail(3); }
        if(true) { setCostumeDetail(4); }
        if(true) { setCostumeDetail(5); }
        if(true) { setCostumeDetail(6); }
        if(true) { setCostumeDetail(7); }
    }

    public void setCostumeDetail(int costumeNumber) {
        switch(costumeNumber) {
            case 1:
                animal.setOnClickListener(viewC -> {otnCostumeSelected(1);});
                animal.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.animal_idle));
                animal.setBackgroundTintList(null);
                break;
            case 2:
                moyai.setOnClickListener(viewC -> {otnCostumeSelected(2);});
                moyai.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.moyai));
                moyai.setBackgroundTintList(null);
                break;
            case 3:
                harambe.setOnClickListener(viewC -> {otnCostumeSelected(3);});
                harambe.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.harambe_idle));
                harambe.setBackgroundTintList(null);
                break;
            case 4:
                mug.setOnClickListener(viewC -> {otnCostumeSelected(4);});
                mug.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.mattmug_idle));
                mug.setBackgroundTintList(null);
                break;
            case 5:
                crab.setOnClickListener(viewC -> {otnCostumeSelected(5);});
                crab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.crab_idle));
                crab.setBackgroundTintList(null);
                break;
            case 6:
                pill.setOnClickListener(viewC -> {otnCostumeSelected(6);});
                pill.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pill_no));
                pill.setBackgroundTintList(null);
                break;
            case 7:
                space.setOnClickListener(viewC -> {otnCostumeSelected(7);});
                space.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.spaceman_idle));
                space.setBackgroundTintList(null);
                break;
            default:
                defaulto.setOnClickListener(viewC -> {otnCostumeSelected(0);});
                defaulto.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.defaulto_idle));
                defaulto.setBackgroundTintList(null);
        }
    }


    public void otnCostumeSelected(int costume) {
        int costumeId = costume;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_COSTUME, costumeId);
        setResult(RESULT_OK, intent);
        finish();
    }
}