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

    public void onSettingsClick (View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}