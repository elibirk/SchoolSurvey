package com.example.spec.cis410_hw5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

//basically just the two buttons to take us where we need to be
    public void TakeSurvey(View view){
        Intent intent = new Intent(this, TakeSurvey.class);
        startActivity(intent);
    }

    public void ViewResult(View view){
        Intent intent = new Intent(this, ViewResult.class);
        startActivity(intent);
    }

}
