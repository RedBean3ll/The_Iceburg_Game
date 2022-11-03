package com.zybooks.the_iceburg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.provider.Settings;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onGameStartClick (View view) {
        Intent start = new Intent(this, GameActivity.class);
        startActivity(start);
    }

    public void onSettingsClick (View view) {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

    public void onSoundClick (View view) {
        Intent sound = new Intent(this, SoundActivityTEST.class);
        startActivity(sound);
    }
}