package com.example.designarama.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebView;
import android.widget.Button;

public class endActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "highscores.db";
    public static final String HIGH_SCORE_TABLE = "highscore";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_SCORE = "SCORE";
    public static final String COLUMN_NAME = "NAME";

    private SQLiteDatabase scoreDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        final EditText editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        final TextView textViewHighScore = (TextView) findViewById(R.id.textViewHighScore);
        final WebView webview = (WebView) findViewById(R.id.webview);

        //Read from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("ptdipwt5", Context.MODE_PRIVATE);
        int highScore = sharedPref.getInt("highscore", 0);
        textViewHighScore.setText("" + highScore);

        Button saveHighScore = (Button) findViewById(R.id.buttonSaveHighScore);
        saveHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // First get the values from the EditText
                String name = editTextUsername.getText().toString();
                int score = 0;

                try {
                    score = Integer
                            .parseInt(textViewHighScore.getText().toString());
                } catch (NumberFormatException e) {
                    return;
                }

                // Add the values
                ContentValues values = new ContentValues();
                values.put(COLUMN_NAME, name);
                values.put(COLUMN_SCORE, score);
                scoreDB.insert(HIGH_SCORE_TABLE, null, values);


                // Retrieve the new list of scores
                Cursor c = scoreDB.query(HIGH_SCORE_TABLE, new String[]{
                                COLUMN_NAME, COLUMN_SCORE}, null, null, null, null,
                        COLUMN_SCORE);

                StringBuilder builder = new StringBuilder();
                builder.append("<html><body><h1>High Scores</h1><table>");

                c.moveToLast();
                for (int i = c.getCount() - 1; i >= 0; i--) {

                    // Get the data
                    builder.append("<tr><td>");
                    builder.append(c.getString(0));
                    builder.append("</td><td>");
                    builder.append(c.getString(1));
                    builder.append("</td></tr>");

                    // Move the cursor
                    c.moveToPrevious();
                }

                builder.append("</table></html>");
                webview.loadData(builder.toString(), "text/html", "UTF-8");

                // Close the cursor
                c.close();

            }
        });

        ImageButton BacktoMain = (ImageButton) findViewById(R.id.back);
        BacktoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        scoreDB = openOrCreateDatabase(DATABASE_NAME,
                SQLiteDatabase.CREATE_IF_NECESSARY
                        | SQLiteDatabase.OPEN_READWRITE, null);
        scoreDB.execSQL("CREATE TABLE IF NOT EXISTS " + HIGH_SCORE_TABLE + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME
                + " VARCHAR, " + COLUMN_SCORE + " INT)");
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (scoreDB.isOpen()) {
            scoreDB.close();
        }

    }
}