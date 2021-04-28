package com.example.terveeks_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

public class ViewExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

    }

    private void getValues(){
        SharedPreferences prefGet = getSharedPreferences("getExercises", Activity.MODE_PRIVATE);
        int burnedCalories = prefGet.getInt("calories", 0);
        int duration = prefGet.getInt("duration", 0);
        float distance = prefGet.getFloat("distance",0);
    }
}