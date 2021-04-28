package com.example.terveeks_projekti;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class AddExerciseActivity extends AppCompatActivity {
    private final String[] sports = new String[]{"Kävely", "Juoksu", "Pyöräily", "Uinti"};
    private int selectedSport = 0;

    private EditText editDuration;
    private EditText editDistance;
    private EditText editWeight;

    private TextView tvAvgSpeed;
    private TextView tvBurnedCalories;


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
                Log.d("SELECTEDSPORT", String.valueOf(selectedSport));
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
     * Saves the values to shared preferences.
     * @param view
     */
    public void saveExercise(View view){
        int burnedCalories = calculateBurnedCalories();
        int duration = (int) getDuration();
        float distance = getDistance();
        Intent intent = new Intent(this, MainActivity.class);

        SharedPreferences prefPut = getSharedPreferences("getExercises", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();

        prefEditor.putInt("calories", burnedCalories);
        prefEditor.putInt("duration", duration);
        prefEditor.putFloat("distance", distance);
        prefEditor.apply();

        startActivity(intent);
    }



    /**
     * Calculates the burned calories with given weight, sport and duration.
     * @return
     */

    private int calculateBurnedCalories(){
        float burnedCalories;
        float met;
        float weight = getWeight();
        float avgSpeedValue = getAvgSpeed();
        float durationHours = getDuration() / 60;

        switch(selectedSport){
            case 0: met = 2.5f;
                break;
            default: met = 0;
        }
        if(selectedSport == 1){
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
            met = 7;
        }

        burnedCalories = weight * met * durationHours;
        return (int) burnedCalories;
    }

    /**
     * Updates the burned calories counter value
     */
    private void updateCalories(){
        tvBurnedCalories.setText(String.valueOf(calculateBurnedCalories()));
    }

    /**
     * Updates the average speed counter value
     */
    private void updateAvgSpeed(){
        tvAvgSpeed.setText(String.format("%.2f", getAvgSpeed()) + " km/h");
    }
}