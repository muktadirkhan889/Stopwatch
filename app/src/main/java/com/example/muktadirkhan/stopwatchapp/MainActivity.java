package com.example.muktadirkhan.stopwatchapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Chronometer chronometer;
    ImageView imageView;
    Button resetButton, shareButton;
    private boolean running = false, resetStatus = false;
    private long pauseOffset;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkBackground));

        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.darkBackground));

        //        ActionBar actionBar = getSupportActionBar();
        //        actionBar.hide();



        chronometer = findViewById(R.id.chronometer);
        resetButton = findViewById(R.id.reset);
        shareButton = findViewById(R.id.share);

        imageView = findViewById(R.id.play_pause);

        imageView.setSelected(true);
        resetButton.setEnabled(false);
        resetButton.setVisibility(View.INVISIBLE);

        shareButton.setEnabled(false);
        shareButton.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                    resetButton.setEnabled(true);
                    resetButton.setVisibility(View.VISIBLE);
                    shareButton.setEnabled(false);
                    shareButton.setVisibility(View.INVISIBLE);
                    v.setSelected(false);
                } else if (running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                    resetButton.setEnabled(true);
                    resetButton.setVisibility(View.VISIBLE);
                    shareButton.setEnabled(true);
                    shareButton.setVisibility(View.VISIBLE);
                    v.setSelected(true);
                }

            }
        });

//        play_pause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                chronometer.stop();
                running = false;
                imageView.setSelected(true);
                resetButton.setEnabled(false);
                resetButton.setVisibility(View.INVISIBLE);
                shareButton.setEnabled(false);
                shareButton.setVisibility(View.INVISIBLE);
//                                              Log.i("time", String.valueOf(chronometer.getText()));
//
//                                               final int i = Integer.parseInt(String.valueOf(chronometer.getText().charAt(3))+String.valueOf(chronometer.getText().charAt(4)));
//                                               Log.i("some",String.valueOf(i));
//                                               new Timer().scheduleAtFixedRate(new TimerTask() {
//
//                                                   int j = i;
//                                                   @Override
//                                                   public void run() {
//                                                       if (String.valueOf(j).equals("-1")) {
////                                                           Toast.makeText(MainActivity.this, "ok", Toast.LENGTH_SHORT).show();
//                                                           chronometer.setText(String.valueOf("00:00"));
//                                                           return;
//                                                       }
//                                                       if(chronometer.getText().toString().startsWith("00:0") && chronometer.getText().toString().length()<=4)
//                                                       chronometer.setText(String.valueOf("00:0")+String.valueOf(j));
//                                                       else if(chronometer.getText().toString().startsWith("00:") && chronometer.getText().toString().length()<=5)
//                                                           chronometer.setText(String.valueOf("00:0")+String.valueOf(j));
//                                                       j--;
//                                                       Log.i("time",String.valueOf(j));
//
//                                                   }
//
//                                               }, 50, 50);


            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "My time is " + String.valueOf(chronometer.getText()));
                intent.setType("text/plain");

                startActivity(Intent.createChooser(intent, "Where to share?"));

            }
        });

        chronometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!running) {
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    running = true;
                    resetButton.setEnabled(true);
                    resetButton.setVisibility(View.VISIBLE);
                    shareButton.setEnabled(false);
                    shareButton.setVisibility(View.INVISIBLE);
                    imageView.setSelected(false);
                } else if (running) {
                    chronometer.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = false;
                    resetButton.setEnabled(true);
                    resetButton.setVisibility(View.VISIBLE);
                    shareButton.setEnabled(true);
                    shareButton.setVisibility(View.VISIBLE);
                    imageView.setSelected(true);
                }
            }
        });

        chronometer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                pauseOffset = 0;
                chronometer.stop();
                running = false;
                imageView.setSelected(true);
                resetButton.setEnabled(false);
                resetButton.setVisibility(View.INVISIBLE);
                shareButton.setEnabled(false);
                shareButton.setVisibility(View.INVISIBLE);
                return true;
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this,CreditsActivity.class);
        startActivity(intent);
    }
}