package com.zybooks.the_iceburg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int costumeNum;
    //private int[] unlockedCostumes = {1,0,0,0,0,0,0,0};
    //private int[] unlockedAchievements = {0,0,0,0,0,0,0,0};
    Button startButton;
    Button achievementsButton;
    Button costumesButton;
    Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        costumeNum = 0;
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.start_button);
        achievementsButton = findViewById(R.id.achievements_button);
        costumesButton = findViewById(R.id.costumes_button);
        settingsButton = findViewById(R.id.settings_button);
        initial();
    }

    private void initial() {
        SharedPreferences gameData = getApplicationContext().getSharedPreferences(getString(R.string.shared_storage_name), MODE_PRIVATE);

        if(!gameData.getBoolean(getString(R.string.flag_costume_0), false)) {
            SharedPreferences.Editor editor = gameData.edit();

            editor.putBoolean(getString(R.string.settings_sfx), false);
            editor.putInt(getString(R.string.current_stage), 1);

            editor.putInt(getString(R.string.current_costume), 0);

            editor.putBoolean(getString(R.string.flag_costume_0), true);
            editor.putBoolean(getString(R.string.flag_costume_1), false);
            editor.putBoolean(getString(R.string.flag_costume_2), false);
            editor.putBoolean(getString(R.string.flag_costume_3), false);
            editor.putBoolean(getString(R.string.flag_costume_4), false);
            editor.putBoolean(getString(R.string.flag_costume_5), false);
            editor.putBoolean(getString(R.string.flag_costume_6), false);
            editor.putBoolean(getString(R.string.flag_costume_7), false);

            editor.putBoolean(getString(R.string.flag_ending_1), false);
            editor.putBoolean(getString(R.string.flag_ending_2), false);
            editor.putBoolean(getString(R.string.flag_ending_3), false);
            editor.putBoolean(getString(R.string.flag_game_complete), false);
            editor.putBoolean(getString(R.string.flag_all_skins), false);
            editor.putBoolean(getString(R.string.flag_solve_sky_puzzle), false);
            editor.putBoolean(getString(R.string.flag_solve_nerd_puzzle), false);
            editor.putBoolean(getString(R.string.flag_take_the_blueberry), false);
            editor.apply();
        }

        startButton.setOnClickListener( view -> onGameStartClick());
        achievementsButton.setOnClickListener( view -> onAchievementsClick());
        costumesButton.setOnClickListener( view -> onCostumesClick());
        settingsButton.setOnClickListener( view -> onSettingsClick());
    }


    public void onGameStartClick() {
        Intent start = new Intent(this, GameActivity.class);
        start.putExtra(CostumesActivity.EXTRA_COSTUME, costumeNum);
        startActivity(start);
    }

    public void onAchievementsClick() {
        Intent achievements = new Intent(this, AchievementsActivity.class);
        startActivity(achievements);
    }

    public void onCostumesClick() {
        Intent costumes = new Intent(this, CostumesActivity.class);
        startActivity(costumes);
    }

    public void onSettingsClick() {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }
}