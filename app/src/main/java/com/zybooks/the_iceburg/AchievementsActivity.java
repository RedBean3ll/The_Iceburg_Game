package com.zybooks.the_iceburg;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AchievementsActivity extends AppCompatActivity{

    TextView achievement_one;
    TextView achievement_two;
    TextView achievement_three;
    TextView achievement_four;
    TextView achievement_five;
    TextView achievement_six;
    TextView achievement_seven;
    TextView achievement_eight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievements_menu);

        achievement_one = findViewById(R.id.achievement_1);
        achievement_two = findViewById(R.id.achievement_2);
        achievement_three = findViewById(R.id.achievement_3);
        achievement_four = findViewById(R.id.achievement_4);
        achievement_five = findViewById(R.id.achievement_5);
        achievement_six = findViewById(R.id.achievement_6);
        achievement_seven = findViewById(R.id.achievement_7);
        achievement_eight = findViewById(R.id.achievement_8);
        initial();
    }

    private void initial() {
        SharedPreferences gameStorage = getApplicationContext().getSharedPreferences(getString(R.string.shared_storage_name), MODE_PRIVATE);

        if(gameStorage.getBoolean(getString(R.string.flag_ending_1), false)) { setAchievementPanel(0); }
        if(gameStorage.getBoolean(getString(R.string.flag_ending_2), false)) { setAchievementPanel(1); }
        if(gameStorage.getBoolean(getString(R.string.flag_ending_3), false)) { setAchievementPanel(2); }
        if(gameStorage.getBoolean(getString(R.string.flag_game_complete), false)) { setAchievementPanel(3); }
        if(gameStorage.getBoolean(getString(R.string.flag_all_skins), false)) { setAchievementPanel(4); }
        if(gameStorage.getBoolean(getString(R.string.flag_solve_sky_puzzle), false)) { setAchievementPanel(5); }
        if(gameStorage.getBoolean(getString(R.string.flag_solve_nerd_puzzle), false)) { setAchievementPanel(6); }
        if(gameStorage.getBoolean(getString(R.string.flag_take_the_blueberry), false)) { setAchievementPanel(7); }

        Log.i("REACH", "REACH");
    }

    @SuppressLint("SetTextI18n")
    private void setAchievementPanel(int achievementNumber) {
        switch (achievementNumber) {
            case 0:
                achievement_one.setText(getString(R.string.good_ending_text));
                achievement_one.setTextColor(Color.WHITE);
                achievement_one.setBackgroundTintList(null);
                break;
            case 1:
                achievement_two.setText(getString(R.string.bad_ending_text));
                achievement_two.setTextColor(Color.WHITE);
                achievement_two.setBackgroundTintList(null);
                break;
            case 2:
                achievement_three.setText(getString(R.string.secret_ending_text));
                achievement_three.setTextColor(Color.WHITE);
                achievement_three.setBackgroundTintList(null);
                break;
            case 3:
                achievement_four.setText(getString(R.string.game_complete_text));
                achievement_four.setTextColor(Color.WHITE);
                achievement_four.setBackgroundTintList(null);
                break;
            case 4:
                achievement_five.setText(getString(R.string.ulocked_all_costume_text));
                achievement_five.setTextColor(Color.WHITE);
                achievement_five.setBackgroundTintList(null);
                break;
            case 5:
                achievement_six.setText(R.string.completed_sky_puzzle_text);
                achievement_six.setTextColor(Color.WHITE);
                achievement_six.setBackgroundTintList(null);
                break;
            case 6:
                achievement_seven.setText(R.string.completed_nerd_puzzle_text);
                achievement_seven.setTextColor(Color.WHITE);
                achievement_seven.setBackgroundTintList(null);
                break;
            case 7:
                achievement_eight.setText(R.string.accepted_blueberry_text);
                achievement_eight.setTextColor(Color.WHITE);
                achievement_eight.setBackgroundTintList(null);
                break;
        }
    }
}