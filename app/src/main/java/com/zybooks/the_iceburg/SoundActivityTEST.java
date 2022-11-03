package com.zybooks.the_iceburg;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SoundActivityTEST extends AppCompatActivity {

        private Button play;
        private Button pause;
        private Button stop;
        private Button change;
        private TextView currentlyPlaying;
        private MediaPlayer mediaPlayer;
        private int currentTrack;

        private int pos = 0;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.soundtest);

            play = findViewById(R.id.playsound);
            pause = findViewById(R.id.pausesound);
            stop = findViewById(R.id.stopsound);
            change = findViewById(R.id.changesound);

            currentlyPlaying = findViewById(R.id.currentTrack);

            mediaPlayer = MediaPlayer.create(this, R.raw.snowy);
            mediaPlayer.setVolume(1.2f, 1.2f);

            currentTrack = R.raw.snowy;

            displayTrack();

            play.setOnClickListener(this::pl);
            pause.setOnClickListener(this::pa);
            stop.setOnClickListener(this::st);
            change.setOnClickListener(this::ch);

        }

        private void pl(View view) {playMusic();}
        private void pa(View view) {pauseMusic();}
        private void st(View view) {stopMusic();}
        private void ch(View view) {changeMusic();}

        private void displayTrack() {
            String playStr = "Current Track: ";
            switch(currentTrack) {
                case R.raw.snowy:
                    currentlyPlaying.setText(playStr+"Snowy - ACNH");
                    break;
                case R.raw.iciclemountain:
                    currentlyPlaying.setText(playStr+"Icicle Mountain - SSBM");
                    break;
                default:
                    currentlyPlaying.setText(playStr+"NA");
            }
        }

        private void playMusic() {

            if(mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, currentTrack);
            }
            try {
                mediaPlayer.start();
            } catch(Exception e) {
                e.printStackTrace();
                Log.e("SoundActivityTEST", "Error has occured");
            }

        }

        private void pauseMusic() {
            if(mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }

        private void stopMusic() {
            if(mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }

            //currentlyPlaying.setText("Current Track: NA");

        }

        private void changeMusic() {
            stopMusic();

            switch(pos % 2) {
                case 0:
                    currentTrack = R.raw.snowy;
                    break;
                case 1:
                    currentTrack = R.raw.iciclemountain;
                    break;
            }
            displayTrack();
            pos++;

        }

    }

