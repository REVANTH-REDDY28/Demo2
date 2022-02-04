package com.sunny.demo2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SelectTimerActivity extends AppCompatActivity implements View.OnClickListener {
    int setTime = 45;

    TextView setTime_textView;
    ImageButton start_btn;
    ImageButton plus15_btn;
    ImageButton minus15_btn;
    ImageButton plus1_btn;
    ImageButton minus1_btn;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            });

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_timer);

        setTime_textView = findViewById(R.id.textView_setTime);
        start_btn = findViewById(R.id.btn_start);
        plus15_btn = findViewById(R.id.btn_plus_15);
        minus15_btn = findViewById(R.id.btn_minus_15);
        plus1_btn = findViewById(R.id.btn_plus_1);
        minus1_btn = findViewById(R.id.btn_minus_1);

        start_btn.setOnClickListener(this);
        plus15_btn.setOnClickListener(this);
        minus15_btn.setOnClickListener(this);
        plus1_btn.setOnClickListener(this);
        minus1_btn.setOnClickListener(this);

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("myChannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_plus_15:
                int checkTime = setTime+15;
                if(checkTime >= 240){
                    setTime = 240;
                }else{
                    setTime +=15;
                }
                setTime_textView.setText(String.valueOf(setTime));
                break;

            case R.id.btn_minus_15:
                checkTime = setTime-15;
                if(checkTime <= 1){
                    setTime = 1;
                }else{
                    setTime -= 15;
                }
                setTime_textView.setText(String.valueOf(setTime));
                break;

            case R.id.btn_plus_1:
                checkTime = setTime+1;
                if(checkTime >= 240){
                    setTime = 240;
                }else{
                    setTime +=1;
                }
                setTime_textView.setText(String.valueOf(setTime));
                break;

            case R.id.btn_minus_1:
                checkTime = setTime-1;
                if(checkTime <= 1){
                    setTime = 1;
                }else{
                    setTime -= 1;
                }
                setTime_textView.setText(String.valueOf(setTime));
                break;

            case R.id.btn_start:
                createNotificationChannel();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myChannel")
                        .setSmallIcon(R.drawable.ic_baseline_stop_24)
                        .setContentTitle("My notification")
                        .setContentText("Much longer text that cannot fit one line...")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Much longer text that cannot fit one line..."))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
                notificationManager.notify(1, builder.build());

                String time = String.valueOf(setTime_textView.getText()).trim();
                setTime = Integer.parseInt(time);
                startTimer(setTime);
                break;
        }
    }

    void startTimer(int timeInMin){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("time",setTime);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activityResultLauncher.launch(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}