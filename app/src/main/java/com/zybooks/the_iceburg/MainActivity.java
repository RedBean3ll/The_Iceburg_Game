package com.zybooks.the_iceburg;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int costumeNum;
    private int[] unlockedCostumes = {1,0,0,0,0,0,0,0};
    private int[] unlockedAchievements = {0,0,0,0,0,0,0,0};
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
        costumeLauncher.launch(costumes);
    }

    private final ActivityResultLauncher<Intent> costumeLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            // Create the "on" button color from the chosen color ID from ColorActivity
                            costumeNum = data.getIntExtra(CostumesActivity.EXTRA_COSTUME, 0);
                        }
                    }
                }
            });

    public void onSettingsClick() {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

}