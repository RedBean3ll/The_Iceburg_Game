package com.zybooks.the_iceburg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchSFX;
    private SeekBar volumeBar;
    private Button deleteSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_menu);

        switchSFX = findViewById(R.id.toggleButton);
        volumeBar = findViewById(R.id.seekBar);
        deleteSaveButton = findViewById(R.id.deleteSaveButton);
        initial();
    }

    private void initial() {
        SharedPreferences gameData = getApplicationContext().getSharedPreferences(getString(R.string.shared_storage_name), MODE_PRIVATE);
        SharedPreferences.Editor editor = gameData.edit();

        switchSFX.setChecked(gameData.getBoolean(getString(R.string.settings_sfx), false));
        volumeBar.setProgress((int) (gameData.getFloat(getString(R.string.settings_sound), 0) * 100));

        switchSFX.setOnClickListener( view -> {
            editor.putBoolean(getString(R.string.settings_sfx), switchSFX.isChecked());
            editor.apply();
        });

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float volume = (float) seekBar.getProgress();
                if(volume > 0) {
                    editor.putFloat(getString(R.string.settings_sound), volume/100);
                    editor.apply();
                    return;
                }
                editor.putFloat(getString(R.string.settings_sound), 0);
                editor.apply();
            }
        });

        deleteSaveButton.setOnClickListener(view -> {
            editor.putInt(getString(R.string.current_stage), 1);
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
        });
    }
}
