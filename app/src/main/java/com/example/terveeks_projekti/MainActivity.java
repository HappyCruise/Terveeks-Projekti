package com.example.terveeks_projekti;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**Called when the user clicks the Add User button*/
    public void createUser(View view){
        Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);
    }

    /**Called when the user clicks the Add Meal button*/
    public void addMeal(View view){
        Intent intent = new Intent(this, AddMealActivity.class);
        startActivity(intent);
    }

    /**Called when the user clicks the add Exercise button */
    public void addExercise(View view){
        Intent intent = new Intent(this, AddExerciseActivity.class);
        startActivity(intent);
    }
}