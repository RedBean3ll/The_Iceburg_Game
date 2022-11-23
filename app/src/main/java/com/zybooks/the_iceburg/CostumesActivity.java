package com.zybooks.the_iceburg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.content.Context;
import android.os.Bundle;

public class CostumesActivity extends AppCompatActivity{
    public static String EXTRA_COSTUME = "costume id";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.costumes_menu);
    }

    public void onCostumeSelected(View view) {
        int costumeId = R.drawable.defaulto_idle;
        if (view.getId() == R.id.costume_animal) {
            costumeId = R.drawable.animal_idle;
        }
        else if (view.getId() == R.id.costume_moyai) {
            costumeId = R.drawable.moyai;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_COSTUME, costumeId);
        setResult(RESULT_OK, intent);
        finish();
    }
}
