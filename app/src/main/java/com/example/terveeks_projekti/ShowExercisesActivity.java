package com.example.terveeks_projekti;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ShowExercisesActivity extends AppCompatActivity {

    ArrayList<String> exercises = new ArrayList<String>();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercises);

        //ListView initialization
        lv = findViewById(R.id.lvExercises);

        getValues();


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1,
                exercises
        );

        lv.setAdapter(arrayAdapter);


    }

    /**
     * Gets the values from sharedPreferences.
     * @return void
     */
    private void getValues(){
        //Get the data and map it
        SharedPreferences prefGet = getSharedPreferences("getExercises", Activity.MODE_PRIVATE);
        Map<String,?> keys = prefGet.getAll();

        //Get current date
        String currDate = new SimpleDateFormat("dd / MM / yyyy", Locale.getDefault()).format(new Date());

        //Go through the mapped data
        for(Map.Entry<String,?> entry: keys.entrySet()){
            //Split the key from "-"
            String strKey = String.valueOf(entry.getKey());
            String[] arrOfKey = strKey.split("-",2);

            //If the key does NOT start with "CALORIES" add it to the display list <exercises>.
            if(!arrOfKey[0].equals("CALORIES")){
                String currString = entry.getValue().toString();
                exercises.add(currString);
            }
        }
    }
}