
package com.zybooks.the_iceburg;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private int costumeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        costumeNum = 0;
        setContentView(R.layout.activity_main);
    }

    public void onGameStartClick (View view) {
        Intent start = new Intent(this, GameActivity.class);
        start.putExtra(CostumesActivity.EXTRA_COSTUME, costumeNum);
        startActivity(start);
    }

    public void onAchievementsClick (View view) {
        Intent achievements = new Intent(this, AchievementsActivity.class);
        startActivity(achievements);
    }

    public void onChangeCostumeClick (View view) {
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

    public void onSettingsClick (View view) {
        Intent settings = new Intent(this, SettingsActivity.class);
        startActivity(settings);
    }

}