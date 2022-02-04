package com.sunny.demo2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WakeUserActitvity extends AppCompatActivity {

    private boolean isUsedClickedok = false;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wake_user);

        okButton = findViewById(R.id.btn_ok_timeUp);

        MediaPlayer finishTimerTone = MediaPlayer.create(WakeUserActitvity.this,R.raw.hangouts_pleasant1);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isUsedClickedok) {
                    finishTimerTone.start();
                }
            }
        }).start();


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishTimerTone.stop();
                isUsedClickedok = true;
                Intent intent = new Intent(WakeUserActitvity.this,SelectTimerActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        });


    }
}