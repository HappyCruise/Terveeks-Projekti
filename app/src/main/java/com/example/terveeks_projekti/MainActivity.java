package com.example.terveeks_projekti;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        updateCaloriesShown();
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

    /**Called when the user clicks the Show Exercises button */
    public void showExercises(View view){
        Intent intent = new Intent(this, ShowExercisesActivity.class);
        startActivity(intent);
    }

    public int getCaloriesToday(){
        int calorieCount =0;

        //Get the data and map it.
        SharedPreferences prefGet = getSharedPreferences("getExercises", Activity.MODE_PRIVATE);
        Map<String, ?> keys = prefGet.getAll();


        //Get current date
        String currDate = new SimpleDateFormat("dd / MM / yyyy", Locale.getDefault()).format(new Date());

        //Go through the mapped data
        for(Map.Entry<String,?> entry: keys.entrySet()){
            //Split the key with "-"
            String strKey = String.valueOf(entry.getKey());
            String[] arrOfStrKey = strKey.split("-",2);

            //Split the value with "-"
            String strValue = String.valueOf(entry.getValue());
            String[] arrOfStrValue = strValue.split("-",2);

            //If key Starts with "calories" add its value to the current calorie count
            if(arrOfStrKey[0].equals("CALORIES")){
                if(arrOfStrValue[0].equals(currDate)){
                    calorieCount += Integer.parseInt(arrOfStrValue[1]);
                }
            }
        }
        return calorieCount;
    }

    public void updateCaloriesShown(){
        TextView tvCalories = findViewById(R.id.tvBurnedCaloriesToday);
        tvCalories.setText("Tänään poltetut kalorit: "+ getCaloriesToday());
    }
}