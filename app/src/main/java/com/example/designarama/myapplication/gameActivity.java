package com.example.designarama.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class gameActivity extends AppCompatActivity {

    int wins;
    private TextView winsText;
    private SoundPool soundPool;

    private int winsound;
    private int tiesound;
    private int losesound;
    private int endsound;

    ImageView AIplayer;
    ImageView players;
    TextView timers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        winsound = soundPool.load(this, R.raw.score, 1);
        tiesound = soundPool.load(this, R.raw.draw, 1);
        losesound = soundPool.load(this, R.raw.wrong, 1);
        endsound = soundPool.load(this, R.raw.done, 1);

        AIplayer = (ImageView) findViewById(R.id.what);
        players = (ImageView) findViewById(R.id.player);
        winsText = (TextView) findViewById(R.id.score);

        final TextView status = (TextView) findViewById(R.id.Status);

        ImageButton scissors = (ImageButton) findViewById(R.id.scissors);
        ImageButton rock = (ImageButton) findViewById(R.id.Rock);
        ImageButton paper = (ImageButton) findViewById(R.id.Paper);

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timers = (TextView) findViewById(R.id.timer);
                timers.setText(" " + millisUntilFinished / 0000);
            }

            public void onFinish() {
                soundPool.play(endsound, 1.0F, 1.0F, 0, 0, 1.0F);
                Intent intent = new Intent(getApplicationContext(), endActivity.class);
                startActivity(intent);

                Toast.makeText(gameActivity.this,"Game End",Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPref = getSharedPreferences("ptdipwt5", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("highscore", wins);
                editor.commit();

                finish();
            }
        }.start();



        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                players.setImageResource(R.drawable.rock);

                int ram = (int) (Math.random() * 3) + 1;
                if (ram == 1) {
                    AIplayer.setImageResource(R.drawable.rock);
                    status.setText("TIE");
                    soundPool.play(tiesound, 1.0F, 1.0F, 0, 0, 1.0F);
                } else if (ram == 2) {
                    AIplayer.setImageResource(R.drawable.paper);
                    status.setText("YOU LOSE!!");
                    soundPool.play(losesound, 1.0F, 1.0F, 0, 0, 1.0F);
                } else {
                    AIplayer.setImageResource(R.drawable.scissors);
                    status.setText("YOU WIN!!");
                    wins += 10;
                    winsText.setText("" + wins);
                    soundPool.play(winsound, 1.0F, 1.0F, 0, 0, 1.0F);
                }
            }
        });

        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                players.setImageResource(R.drawable.paper);

                int ram = (int) (Math.random() * 3) + 1;
                if (ram == 1) {
                    AIplayer.setImageResource(R.drawable.rock);
                    status.setText("YOU WIN!!");
                    wins += 10;
                    winsText.setText("" + wins);
                    soundPool.play(winsound, 1.0F, 1.0F, 0, 0, 1.0F);
                } else if (ram == 2) {
                    AIplayer.setImageResource(R.drawable.paper);
                    status.setText("TIE");
                    soundPool.play(tiesound, 1.0F, 1.0F, 0, 0, 1.0F);
                } else {
                    AIplayer.setImageResource(R.drawable.scissors);
                    status.setText("YOU LOSE!!");
                    soundPool.play(losesound, 1.0F, 1.0F, 0, 0, 1.0F);
                }
            }
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                players.setImageResource(R.drawable.scissors);

                int ram = (int) (Math.random() * 3) + 1;
                if (ram == 1) {
                    AIplayer.setImageResource(R.drawable.rock);
                    status.setText("YOU LOSE!!");
                    soundPool.play(losesound, 1.0F, 1.0F, 0, 0, 1.0F);
                } else if (ram == 2) {
                    AIplayer.setImageResource(R.drawable.paper);
                    status.setText("YOU WIN!!");
                    wins += 10;
                    winsText.setText("" + wins);
                    soundPool.play(winsound, 1.0F, 1.0F, 0, 0, 1.0F);
                } else {
                    AIplayer.setImageResource(R.drawable.scissors);
                    status.setText("TIE");
                    soundPool.play(tiesound, 1.0F, 1.0F, 0, 0, 1.0F);
                }
            }
        });


        if (savedInstanceState != null) {
            //saved instance state data
            int exScore = savedInstanceState.getInt("score");
            winsText.setText("" + exScore);
        }

    }

}

