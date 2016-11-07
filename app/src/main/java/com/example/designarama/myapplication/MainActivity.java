package com.example.designarama.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{

    public static boolean isPlay;
    private ImageView setting;
    private SoundPool soundPool;

    private int startsound;
    private int Clickbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting = (ImageView)findViewById(R.id.setting);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        startsound = soundPool.load(this, R.raw.start, 1);
        Clickbutton = soundPool.load(this, R.raw.score2, 1);

        ImageButton startgame = (ImageButton) findViewById(R.id.start);
        startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), gameActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"Game Start",Toast.LENGTH_SHORT).show();
                soundPool.play(startsound, 1.0F, 1.0F, 0, 0, 1.0F);

            }
        });

        ImageButton htp = (ImageButton) findViewById(R.id.htp);
        htp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Howtoplay.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,"Enjoy",Toast.LENGTH_SHORT).show();
                soundPool.play(Clickbutton, 1.0F, 1.0F, 0, 0, 1.0F);
            }
        });

    }

    public void setting(View v) {
        isPlay = !isPlay;
        setting.setImageResource(isPlay ? R.drawable.soundoff : R.drawable.sound);
//        isPlay?R.drawable.mute:R.drawable.sound;
        if (!isPlay) {
            Music.play(this, R.raw.pixelbackground);
            Toast.makeText(MainActivity.this,"Sound On",Toast.LENGTH_SHORT).show();
        } else {
            Music.stop(this);
            Toast.makeText(MainActivity.this,"Sound Off",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        Music.play(this, R.raw.pixelbackground);
        setting.setImageResource(isPlay ? R.drawable.soundoff : R.drawable.sound);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Music.stop(this);
    }

    @Override
    public void finish() {
        Music.stop(this);
        super.finish();
    }
}


