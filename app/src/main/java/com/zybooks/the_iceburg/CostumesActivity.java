package com.zybooks.the_iceburg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
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
        int costumeId = 0;
        if (view.getId() == R.id.costume_defaulto) {
            costumeId = 0;
        }
        if (view.getId() == R.id.costume_animal) {
            costumeId = 1;
        }
        else if (view.getId() == R.id.costume_moyai) {
            costumeId = 2;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_COSTUME, costumeId);
        setResult(RESULT_OK, intent);
        finish();
    }
}