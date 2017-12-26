package com.example.e_learning.e_learningapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomePage extends AppCompatActivity {

    ImageView imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        imgSplash = (ImageView) findViewById(R.id.imgSplash);
        imgSplash.setImageResource(R.drawable.launcher);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(WelcomePage.this, SubjectList.class);
                startActivity(i);
                finish();
            }
        }).start();

    }
}
