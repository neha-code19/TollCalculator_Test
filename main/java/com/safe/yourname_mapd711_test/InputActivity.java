package com.safe.yourname_mapd711_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class    InputActivity extends AppCompatActivity {

    Spinner spinnerDaytime;
    EditText editTextDistance;
    ArrayAdapter<String> arrayAdapter;
    RadioGroup radioGroup, radioEast;
    RadioButton radiolight, radioHeavy, radiomulti, radioeast, radiowest;
    Button calculate;
    double tc;
    Switch aSwitch;
    String selected_val;
    CheckBox checkBox;
    private Calendar fromTime;
    private Calendar toTime;
    private Calendar currentTime;
    double toll = 1.00;
    double c = 0.0;
    double totalToll;
    boolean checked;
    String transport = "No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDistance = findViewById(R.id.distance);
        calculate = findViewById(R.id.calculate);
        radioGroup = findViewById(R.id.radioControl);
        radioEast = findViewById(R.id.radioEast);
        radiolight = findViewById(R.id.radioButtonlight);
        radioHeavy = findViewById(R.id.radioButtonHeavy);
        radiomulti = findViewById(R.id.radioButtonHeavyMulti);
        radioeast = findViewById(R.id.radioEastBound);
        radiowest = findViewById(R.id.radioButtonWest);
        checkBox = findViewById(R.id.checkbox);
        InputFilterMinMax filter = new InputFilterMinMax("0", "24") {};
        editTextDistance.setFilters(new InputFilter[]{filter});
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String[] weekdays = new String[]{"Saturday  " + currentTime, "Sunday " + currentTime, "Monday "+ currentTime, "Tuesday "+ currentTime, "Wednesday "+ currentTime, "Thursday "+ currentTime, "Friday "+ currentTime, "holiday "+ currentTime};
        //Get the day of the week for perticular device
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        // Get the array index from day of the week.
        int defaultPosition = Arrays.asList(weekdays).indexOf(dayOfTheWeek);
        aSwitch = findViewById(R.id.transport);
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();

        // Apply index to spinner.

        spinnerDaytime = findViewById(R.id.daytime);
        arrayAdapter = new ArrayAdapter<>(InputActivity.this, android.R.layout.simple_spinner_dropdown_item, weekdays);
        spinnerDaytime.setAdapter(arrayAdapter);
        spinnerDaytime.setSelection(defaultPosition);
        //textdatetime = spinnerDaytime.getSelectedItem().toString();
        spinnerDaytime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {

                selected_val=spinnerDaytime.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String distance = editTextDistance.getText().toString();

                if (TextUtils.isEmpty(distance)){
                    editTextDistance.setError("Enter the distance between 0.0-24.0 Float points allowed.");
                }
                else if(radioGroup.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "Please select the vertical size!!!!", Toast.LENGTH_SHORT).show();
                }
                else if(radioEast.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "Please select the direction!!!!", Toast.LENGTH_SHORT).show();
                }

                else if(!(checkBox.isChecked())){
                    checked = true;
                    myEdit.putBoolean("checked", checked);
                    myEdit.apply();
                    Toast.makeText(getApplicationContext(), "Please click on the checkbox to to display the webview", Toast.LENGTH_SHORT).show();
                }
                else if(radioGroup.getCheckedRadioButtonId() == R.id.radioButtonlight){
                    String light = radiolight.getText().toString();
                    if(checkTime("12:00-6:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 25.29;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 25.29;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("6:00-7:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 42.04;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "6:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 44.86;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("7:00-9:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 47.83;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-9:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 54.93;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("9:30-10:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 42.04;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "9:30-10:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 46.58;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "9:30-10:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("10:30-14:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 38.47;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "10:30-14:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 39.07;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "10:30-14:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("14:30-15:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 43.62;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "14:30-15:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 48.61;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "14:30-15:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("15:30-18:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 49.56;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "15:30-18:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 58.48;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "15:30-18:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("18:00-19:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 46.81;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "18:00-19:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 43.62;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "18:00-19:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("19:00-12:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 25.29;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "19:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 25.29;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "19:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("12:00-11:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 25.29;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-11:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 25.29;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-11:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("11:00-7:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 34.63;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "11:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 34.63;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "11:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("7:00-12:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 25.29;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 25.29;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                }





                else if(radioGroup.getCheckedRadioButtonId() == R.id.radioButtonHeavy){
                    String light = radioHeavy.getText().toString();
                    if(checkTime("12:00-6:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 50.58;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 50.58;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("6:00-7:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 84.08;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "6:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 89.72;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "6:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("7:00-9:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 95.66;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-9:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 109.86;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-9:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("9:30-10:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 84.08;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "9:30-10:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 93.16;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "9:30-10:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("10:30-14:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 76.94;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "10:30-14:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 78.14;
                        }
                    }
                    else if(checkTime("14:30-15:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 97.22;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "14:30-15:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 87.24;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "14:30-15:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("15:30-18:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 116.96;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "15:30-18:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 99.12;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "15:30-18:000");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("18:00-19:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 93.62;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "18:00-19:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 87.24;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "18:00-19:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("19:00-12:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 50.58;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "19:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 50.58;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "19:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("12:00-11:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 50.58;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-11:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 50.58;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-11:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("11:00-7:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 69.26;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "11:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 69.26;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "11:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("7:00-12:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 50.58;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 50.58;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                }




                else if(radioGroup.getCheckedRadioButtonId() == R.id.radioButtonHeavyMulti){
                    String light = radiomulti.getText().toString();
                    if(checkTime("12:00-6:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 75.87;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 75.87;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("6:00-7:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 126.12;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "6:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 134.58;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "6:00-7:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("7:00-9:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 143.49;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-9:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 164.79;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-9:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("9:30-10:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 126.12;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "9:30-10:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 139.74;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "9:30-10:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("10:30-14:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 115.41;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 117.21;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "10:30-14:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("14:30-15:30")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 145.83;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "14:30-15:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 130.86;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "14:30-15:30");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("15:30-18:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 175.44;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "15:30-18:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 148.68;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "15:30-18:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("18:00-19:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 93.62;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "18:00-19:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 130.86;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "18:00-19:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if(checkTime("19:00-12:00")){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 140.43;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "19:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 75.87;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "19:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("12:00-11:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 75.87;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-11:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 75.87;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("11:00-7:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 103.89;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 103.89;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "12:00-6:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                    else if((checkTime("7:00-12:00")) && (selected_val.equals("Saturday " + currentTime) || selected_val.equals("Sunday " + currentTime) || selected_val.equals("holiday "+ currentTime))){
                        if(radioEast.getCheckedRadioButtonId() == R.id.radioEastBound){
                            tc = 75.87;
                            String east = radioeast.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                        else if(radioEast.getCheckedRadioButtonId() == R.id.radioButtonWest){
                            tc = 75.87;
                            String east = radiowest.getText().toString();
                            if (aSwitch.isChecked()){
                                totalToll = Float.parseFloat(distance) * tc + toll;
                                transport = "Yes";
                            }
                            else{
                                totalToll = Float.parseFloat(distance) * tc + toll + c;
                            }
                            myEdit.putString("light", light);
                            myEdit.putString("distance", distance);
                            myEdit.putString("day", selected_val + "7:00-12:00");
                            myEdit.putString("direction", east);
                            myEdit.putString("transport", transport);
                            myEdit.putFloat("toll", (float) totalToll);
                            myEdit.apply();
                            Intent intent = new Intent(InputActivity.this, ResultActivity.class);
                            startActivity(intent);
                        }
                    }
                }

            }
        });

    }
        public static class InputFilterMinMax implements InputFilter {

            private final float min;
            private final float max;

            public InputFilterMinMax(float min, float max) {
                this.min = min;
                this.max = max;
            }

            public InputFilterMinMax(String min, String max) {
                this.min = Float.parseFloat(min);
                this.max = Float.parseFloat(max);
            }

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                try {
                    float input = Float.parseFloat(dest.toString() + source.toString());
                    if (isInRange(min, max, input))
                        return null;
                } catch (NumberFormatException nfe) { }
                return "";
            }

            private boolean isInRange(float a, float b, float c) {
                return b > a ? c >= a && c <= b : c >= b && c <= a;
            }
        }
    public boolean checkTime(String time) {
        try {
            String[] times = time.split("-");
            String[] from = times[0].split(":");
            String[] until = times[1].split(":");

            fromTime = Calendar.getInstance();
            fromTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(from[0]));
            fromTime.set(Calendar.MINUTE, Integer.valueOf(from[1]));

            toTime = Calendar.getInstance();
            toTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(until[0]));
            toTime.set(Calendar.MINUTE, Integer.valueOf(until[1]));

            currentTime = Calendar.getInstance();
            currentTime.set(Calendar.HOUR_OF_DAY, Calendar.HOUR_OF_DAY);
            currentTime.set(Calendar.MINUTE, Calendar.MINUTE);
            if(currentTime.after(fromTime) && currentTime.before(toTime)){
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}