package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    Button startButton;

    CountDownTimer countDownTimer;

    @SuppressLint("SetTextI18n")
    public void resetTimer() {
        timerTextView.setText("00:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        startButton.setText("Start");
        counterIsActive = false;
    }
    @SuppressLint("SetTextI18n")
    public void startButtonClicked(View view) {
        if(counterIsActive) {
            resetTimer();
        } else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            startButton.setText("Stop");
            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000L + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }
    @SuppressLint("SetTextI18n")
    public void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString = Integer.toString(seconds);
        String firstString = Integer.toString(minutes);

        if(seconds < 10) {
            secondString = "0" + secondString;
        }

        if(minutes < 10) {
            firstString = "0" + firstString;
        }

        timerTextView.setText(firstString + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}