package com.zybooks.the_iceburg;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;

public class SoundMixer {

    private MediaPlayer levelMusic;
    Context soundContext;

    private float volumeLevel;
    private boolean musicLock;

    public SoundMixer(Context context) {
        soundContext = context;

    }

    protected void setPlayerAndStart(int currentLevel, float volume) {
        volumeLevel = volume;
        musicLock = false;

        /*if(levelMusic != null) {
            levelMusic.stop();
            levelMusic.release();
            levelMusic = null;
        }*/
        if(levelMusic == null) {
            switch (currentLevel) {
                case 2:
                    levelMusic = MediaPlayer.create(soundContext, R.raw.tracktwo);
                    break;
                case 3:
                    levelMusic = MediaPlayer.create(soundContext, R.raw.trackthree);
                    break;
                default:
                    levelMusic = MediaPlayer.create(soundContext, R.raw.trackone);
            }
            levelMusic.setVolume(volumeLevel, volumeLevel);
            levelMusic.setLooping(true);
            levelMusic.start();
        }
    }

    protected void play() {
        if(levelMusic != null) {
            if(!levelMusic.isPlaying()) {
                levelMusic.start();
            }
        }
    }

    protected void stop() {
        if(levelMusic != null) {
            if(!levelMusic.isPlaying()) levelMusic.stop();
            levelMusic.release();
        }
    }

    protected void changeTrack(int level) {
        if(levelMusic != null) {
            if(levelMusic.isPlaying()) levelMusic.stop();
            levelMusic.release();
            levelMusic = null;
        }
        trackPicker(level);
    }

    protected void trackPicker(int currentLevel) {
        if(levelMusic == null) {
            switch (currentLevel) {
                case 2:
                    levelMusic = MediaPlayer.create(soundContext, R.raw.tracktwo);
                    break;
                case 3:
                    levelMusic = MediaPlayer.create(soundContext, R.raw.trackthree);
                    break;
                default:
                    levelMusic = MediaPlayer.create(soundContext, R.raw.trackone);
            }
            levelMusic.setLooping(true);
            levelMusic.setVolume(volumeLevel, volumeLevel);
        }
    }

    protected void secretEnding(Context context) {

        if(!musicLock) {
            if(levelMusic != null) {
                levelMusic.stop();
                levelMusic.release();
                levelMusic = null;
            }
            levelMusic = MediaPlayer.create(context, R.raw.secret);
            levelMusic.setVolume(volumeLevel, volumeLevel);
            levelMusic.start();
            musicLock = true;
        }
    }

    protected void onPause() {
        if(levelMusic != null) {
            levelMusic.pause();
        }
    }

    protected void onResume() {
        if(levelMusic != null) {
            levelMusic.start();
        }
    }

}
