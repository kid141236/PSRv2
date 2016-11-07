package com.example.designarama.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Howtoplay extends AppCompatActivity {

    private SoundPool soundPool;
    private int Clickbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtoplay);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 5);
        Clickbutton = soundPool.load(this, R.raw.score2, 1);

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(Howtoplay.this,"Enjoy",Toast.LENGTH_SHORT).show();
                soundPool.play(Clickbutton, 1.0F, 1.0F, 0, 0, 1.0F);
            }
        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        Music.play(this, R.raw.pixelbackground);
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
