package com.zybooks.the_iceburg;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.zybooks.the_iceburg.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameDataTool {

    private static GameDataTool instance = null;

    /**
     * A function for getting a singleton instance of the GameDataTool to avoid feverish generation and collection of GameDataTool. This Class contains functions for reading all flags and values from storage and limited writes for updating the database. All writes are written in a way to prevent updating of what doesn't exist.
     * @return GameDataTool Return instance of GameDataTool for performing read and write operations.
     */
    public static GameDataTool getInstance() {
        if(instance == null) {
            instance = new GameDataTool();
        }

        return instance;
    }

    /**
     * A function for getting an array list of all costume flags. This includes costume 0 (default character) at index 0.
     * @param context A context. Preferably applicationContext or activityContext.
     * @return ArrayList Returns an ArrayList of type Wrapper Boolean
     */
    public ArrayList<Boolean> getCostumeFlags(@NonNull Context context) {
        List<Integer> stringKeys = List.of(R.string.flag_costume_0,R.string.flag_costume_1, R.string.flag_costume_2, R.string.flag_costume_3, R.string.flag_costume_4, R.string.flag_costume_5, R.string.flag_costume_6, R.string.flag_costume_7);

        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);
        ArrayList<Boolean> returnArray = new ArrayList<>(stringKeys.size());

        try {
            for(int i = 0; i < stringKeys.size(); i++) {
                returnArray.add(gameData.getBoolean(context.getString(stringKeys.get(i)), false));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Collections.fill(returnArray, false);
        }
        return returnArray;
    }

    /**
     * A function for getting an array list of all achievement flags.
     * @param context A context. Preferably applicationContext or activityContext.
     * @return ArrayList Returns an ArrayList of type Wrapper Boolean
     */
    public ArrayList<Boolean> getAchievementFlags(@NonNull Context context) {
        List<Integer> stringKeys = List.of(R.string.flag_ending_1, R.string.flag_ending_2, R.string.flag_ending_3, R.string.flag_game_complete, R.string.flag_all_skins, R.string.flag_solve_sky_puzzle, R.string.flag_solve_nerd_puzzle, R.string.flag_take_the_blueberry);

        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);
        ArrayList<Boolean> returnArray = new ArrayList<>(stringKeys.size());

        try {
            for(int i = 0; i < stringKeys.size(); i++) {
                returnArray.add(gameData.getBoolean(context.getString(stringKeys.get(i)), false));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Collections.fill(returnArray, false);
        }
        return  returnArray;
    }

    /**
     * A function for getting an array list of all achievement flags.
     * @param context A context. Preferably applicationContext or activityContext.
     * @return ArrayList Returns an ArrayList of type Wrapper Boolean
     */
    public ArrayList<Boolean> getEndingFlags(@NonNull Context context) {
        List<Integer> stringKeys = List.of(R.string.flag_ending_1, R.string.flag_ending_2, R.string.flag_ending_3);

        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);
        ArrayList<Boolean> returnArray = new ArrayList<>(stringKeys.size());

        try {
            for(int i = 0; i < stringKeys.size(); i++) {
                returnArray.add(gameData.getBoolean(context.getString(stringKeys.get(i)), false));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            Collections.fill(returnArray, false);
        }
        return  returnArray;
    }

    /**
     * A function for getting the number of the current stage. Default value is 1.
     * @param context A context. Preferably applicationContext or activityContext.
     * @return int Returns a number of type int.
     */
    public int getCurrentStage(@NonNull Context context) {
        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);

        try {
            return  gameData.getInt(context.getString(R.string.current_stage), 1);
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * A function for getting current volume level. Default is 1f.
     * @param context A context. Preferably applicationContext or activityContext.
     * @return float Returns a number of type float.
     */
    public float getVolume(@NonNull Context context) {
        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);

        try {
            return  gameData.getFloat(context.getString(R.string.settings_sound), 1);
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * A function for getting whether sound effects should be on.
     * @param context A context. Preferably applicationContext or activityContext.
     * @return boolean Returns a raw boolean.
     */
    public boolean getIfSFX(@NonNull Context context) {
        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);

        try {
            return  gameData.getBoolean(context.getString(R.string.settings_sfx), true);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * A function for updating N costume flag to true. If N is already true then nothing will happen.
     * @param context A context. Preferably applicationContext or activityContext.
     * @param costumeNumber The number of costume being updated. 0 = default, 1 = animal, 2 = harambe, 4 = mug, 5 = crab, 6 = pull, 7 = spaceman
     */
    public void unlockCostume(@NonNull Context context, int costumeNumber) {
        List<Integer> stringKeys = List.of(R.string.flag_costume_0,R.string.flag_costume_1, R.string.flag_costume_2, R.string.flag_costume_3, R.string.flag_costume_4, R.string.flag_costume_5, R.string.flag_costume_6, R.string.flag_costume_7);
        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);

        if(costumeNumber < stringKeys.size() && !gameData.getBoolean(context.getString(stringKeys.get(costumeNumber)), false)) {
            SharedPreferences.Editor dataEditor = gameData.edit();
            dataEditor.putBoolean(context.getString(stringKeys.get(costumeNumber)), true);
            dataEditor.apply();
        }
    }

    /**
     * A function for updating N achievement flag to true. If N is already true then nothing will happen.
     * @param context A context. Preferably applicationContext or activityContext.
     * @param achievementNumber The number of the achievement being updated. 0 = completed stage 1, 1 = completed stage 2, 2 = completed stage 3, 3 = game completed, 4 = all skins unlocked, 5 = sky puzzle solved, 6 = nerd puzzle solved, 7 = took the blueberry
     */
    public void unlockAchievement(@NonNull Context context, int achievementNumber) {
        List<Integer> stringKeys = List.of(R.string.flag_ending_1, R.string.flag_ending_2, R.string.flag_ending_3, R.string.flag_game_complete, R.string.flag_all_skins, R.string.flag_solve_sky_puzzle, R.string.flag_solve_nerd_puzzle, R.string.flag_take_the_blueberry);
        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);

        if(achievementNumber < stringKeys.size() && !gameData.getBoolean(context.getString(stringKeys.get(achievementNumber)), false)) {
            SharedPreferences.Editor dataEditor = gameData.edit();
            dataEditor.putBoolean(context.getString(stringKeys.get(achievementNumber)), true);
            dataEditor.apply();
        }
    }

    /**
     * A function for updating N ending flag to true. If N is already true then nothing will happen.
     * @param context A context. Preferably applicationContext or activityContext.
     * @param endingNumber The number of the ending being updated. 0 = good, 1 = bad, 3 = secret
     */
    public void unlockEnding(@NonNull Context context, int endingNumber) {
        List<Integer> stringKeys = List.of(R.string.flag_ending_1, R.string.flag_ending_2, R.string.flag_ending_3);
        SharedPreferences gameData = context.getSharedPreferences(context.getString(R.string.shared_storage_name), Context.MODE_PRIVATE);

        if(endingNumber < stringKeys.size() && !gameData.getBoolean(context.getString(stringKeys.get(endingNumber)), false)) {
            SharedPreferences.Editor dataEditor = gameData.edit();
            dataEditor.putBoolean(context.getString(stringKeys.get(endingNumber)), true);
            dataEditor.apply();
        }
    }
}
