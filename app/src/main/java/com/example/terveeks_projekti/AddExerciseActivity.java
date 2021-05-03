package com.example.terveeks_projekti;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;


public class AddExerciseActivity extends AppCompatActivity {
    private final String[] sports = new String[]{"Kävely", "Juoksu", "Pyöräily", "Uinti"};
    private int selectedSport = 0;

    private EditText editDuration;
    private EditText editDistance;
    private EditText editWeight;

    private TextView tvAvgSpeed;
    private TextView tvBurnedCalories;

    /**
     * Adds eventListeners to the Spinner and the text fields.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        editDuration  = (EditText) findViewById(R.id.editDuration);
        editDistance  = (EditText) findViewById(R.id.editDistance);
        editWeight = (EditText) findViewById(R.id.editWeight);


        tvAvgSpeed = findViewById(R.id.tvAvgSpeed);
        tvBurnedCalories = findViewById(R.id.tvBurnedCalories);

        Spinner sportsList = findViewById(R.id.spSportsList);
        ArrayAdapter<String> sportsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sports);
        sportsList.setAdapter(sportsAdapter);

        sportsList.setOnItemSelectedListener(new OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSport = (int) id;
                updateCalories();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        /*
          Update the shown values every time a value changes.
         */
        editDuration.addTextChangedListener(new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                updateAvgSpeed();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });
        editDistance.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                updateAvgSpeed();

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });
        editWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                updateCalories();
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }


        });
    }

    /**
     * Returns the duration input
     * @return
     */
    private float getDuration(){

         if(String.valueOf(editDuration.getText()).equals("") || editDuration.getText() == null){
             return 0;
         }else{
             return Float.parseFloat(String.valueOf(editDuration.getText()));
         }
    }

    /**
     * Returns the Distance input
     * @return
     */
    private float getDistance(){
        if(String.valueOf(editDistance.getText()).equals("") || editDistance.getText() == null){
            return 0;
        }else{
            return Float.parseFloat(String.valueOf(editDistance.getText()));
        }

    }

    /**
     * Returns the weight input
     * @return
     */
    private float getWeight(){
        if(String.valueOf(editWeight.getText()).equals("") || editWeight.getText() == null){
            return 0;
        }else{
            return Float.parseFloat(String.valueOf(editWeight.getText()));
        }
    }

    /**
     * Calculates the average speed using the inputted distance and duration.
     * @return
     */
    private float getAvgSpeed(){
        return getDistance() / (getDuration() /60);
    }



    /**
     * Calculates the burned calories with given weight, sport and duration.
     * @param
     */
    private int calculateBurnedCalories(){
        float burnedCalories;
        float met = 0;
        float weight = getWeight();
        float avgSpeedValue = getAvgSpeed();
        float durationHours = getDuration() / 60;

        //Check the selected sport to set the met value according to speed.
        if (selectedSport == 0) {
            met = 2.5f;
        } 
        else if(selectedSport == 1){
            if(avgSpeedValue <= 8){
                met = 7;
            }else if(avgSpeedValue > 8 && avgSpeedValue <= 14.5){
                met = 11;
            }
            else if(avgSpeedValue > 14.5){
                met = 17;
            }
        }
        else if(selectedSport == 2){
            if(avgSpeedValue <= 19){
                met = 6;
            }
            else if(avgSpeedValue > 19 && avgSpeedValue <= 26){
                met = 9;
            }
            else if(avgSpeedValue > 26){
                met = 12;
            }
        }
        else if(selectedSport == 3){
            met = 7.2f;
        }

        burnedCalories = weight * met * durationHours;

        //Round out calorie count and return it
        return (int) burnedCalories;
    }

    /**
     * Updates the burned calories counter value
     */
    private void updateCalories(){
        int calories = calculateBurnedCalories();
        if (calories > 0){
            tvBurnedCalories.setText(calories + "kcal. Hyvää työtä!");
        }else{
            tvBurnedCalories.setText(String.valueOf(calories));
        }

    }

    /**
     * Updates the average speed counter value down to the 2nd decimal point.
     */
    private void updateAvgSpeed(){
        tvAvgSpeed.setText(String.format("%.2f", getAvgSpeed()) + " km/h");
    }

    /**
     * Saves the exercise with the input values and returns to MainActivity.
     * @param view
     */
    public void saveExercise(View view){
        Intent intent = new Intent(this, MainActivity.class);
        //Get the values
        int burnedCalories = calculateBurnedCalories();
        int duration = (int) getDuration();
        float distance = getDistance();

        //Get the current date.
        String currDate = new SimpleDateFormat("dd / MM / yyyy", Locale.getDefault()).format(new Date());

        String sport;
        //Set the value of ´sport´ by checking the selected sport.
        switch (selectedSport){
            case 0: sport = "Kävely";
                break;
            case 1: sport = "Juoksu";
                break;
            case 2: sport = "Pyöräily";
                break;
            case 3: sport = "Uinti";
                break;
            default: sport = "Kävely";
        }

        //Create the data set. Use System time as tag.
        long idTime = System.currentTimeMillis();
        String dataString = "Päivämäärä: " + currDate + " Laji: " + sport + ". Kesto: " + duration + " Matka: " + distance + " Poltetut kalorit: " + burnedCalories;

        //initialize the editor
        SharedPreferences prefPut = getSharedPreferences("getExercises", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();

        //all data
        prefEditor.putString(String.valueOf(idTime), dataString);
        //calories and date only
        prefEditor.putString("CALORIES-"+ idTime,currDate + "-" + burnedCalories);
        prefEditor.apply();

        //Return to main activity
        startActivity(intent);
    }

}