package com.zybooks.the_iceburg;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

public class Sound implements Runnable {

    Thread thread;
    SoundPool funnysoundmachine;
    int soundId;
    Context curr;

    public Sound(Context context) {
        curr = context;

        AudioAttributes att = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        funnysoundmachine = new SoundPool.Builder()
                .setAudioAttributes(att)
                .build();
        soundId = funnysoundmachine.load(curr, R.raw.startbmg, 1);

    }

    @Override
    public void run() {
        funnysoundmachine.play(soundId,5,5,2,0,1);
    }

    private void sleep () {
        try {
            Thread.sleep(17); // Speed at which elements are drawn !!! IMPORTANT !!! (larger is slower)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume () {
        thread = new Thread(this);
        thread.start();
    }

    public void pause () {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
