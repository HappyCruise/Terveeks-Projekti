package com.example.terveeks_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddMealActivity extends AppCompatActivity {
    private EditText MKaloriVastaus;
    private EditText MvitamiiniVastaus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        MKaloriVastaus = (EditText) findViewById(R.id.MkaloriVastaus);
        MvitamiiniVastaus = (EditText) findViewById(R.id.MvitamiiniVastaus);


    }

    /**
     * returns calories
     * @return
     */
    private float getCalories(){
        if(String.valueOf(MKaloriVastaus.getText()).equals("") || MKaloriVastaus.getText() == null){
            return 0;
        }else{
            return Float.parseFloat(String.valueOf(MKaloriVastaus.getText()));
        }
    }

    /**
     * returns vitamins
     * @return
     */
    private float getVitamins(){
        if(String.valueOf(MvitamiiniVastaus.getText()).equals("") || MvitamiiniVastaus.getText() == null){
            return 0;
        }else{
            return Float.parseFloat(String.valueOf(MvitamiiniVastaus.getText()));
        }
    }

    /**
     * returns calories
     * @return
     */
    private int calories() {
        return (int) getCalories();
    }

    /**
     * saves the meal
     * @param view
     */
    //nappula
    public void saveMeal(View view){
        Intent intent = new Intent(this, MainActivity.class);

        SharedPreferences prefPut = getSharedPreferences("getMeals", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = prefPut.edit();

        long idTime = System.currentTimeMillis();
        String currDate = new SimpleDateFormat("dd / MM / yyyy", Locale.getDefault()).format(new Date());
        prefEditor.putString("CONSUMED-"+ idTime,currDate + "-" + calories());
        prefEditor.apply();

        //Return to main activity
        startActivity(intent);
    }




}
