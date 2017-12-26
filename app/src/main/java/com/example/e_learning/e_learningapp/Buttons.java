package com.example.e_learning.e_learningapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Buttons extends AppCompatActivity implements View.OnClickListener {

    Button btnVideo;
    Button btnNotes;
    private int topic_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);
        btnNotes = (Button) findViewById(R.id.btnNotes);
        btnVideo = (Button) findViewById(R.id.btnVideo);

        Intent next = getIntent();
        topic_id = next.getExtras().getInt("topic_id") ;
        Log.i("id",topic_id+"");

        btnNotes.setOnClickListener(Buttons.this);
        btnVideo.setOnClickListener(Buttons.this);
    }

    @Override
    public void onClick(View view) {
        Intent next = null;
        switch (view.getId()){
            case R.id.btnNotes:
                next = new Intent(Buttons.this,SuppList.class);
                next.putExtra("topic_id",topic_id);
                startActivity(next);
                break;
            case R.id.btnVideo:
                next = new Intent(Buttons.this,VideoList.class) ;
                next.putExtra("topic_id",topic_id);
                startActivity(next);
                break;
        }
    }
}
