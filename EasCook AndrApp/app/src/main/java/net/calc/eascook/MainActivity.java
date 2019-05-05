package net.calc.eascook;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button select_time_button;
    TextView picked_time_text;
    int hour, minute;
    int hour_x, minute_x;
    Spinner riceAmountSpin;
    String o_h, o_m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        select_time_button = findViewById(R.id.selectTime_button_id);
        picked_time_text = findViewById(R.id.picked_time_txv_id);

        select_time_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, MainActivity.this, hour, minute, true);
                timePickerDialog.show();
            }
        });


        {
        riceAmountSpin = (Spinner) findViewById(R.id.spinner1_id);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.riceAmount));
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        riceAmountSpin.setAdapter(spinnerAdapter);
        riceAmountSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    } //spinner

        };



    public void openFeedBackActivity(View view) {
        startActivity(new Intent(this, FeedBackActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int min) {
        hour_x = hourOfDay;
        minute_x = min;

        if (hourOfDay<10){
            o_h = "0";
        }
        else {o_h = " ";
        }
        if (min<10){
            o_m = ":0";
        }
        else {
            o_m = ";";
        }

        picked_time_text.setText("Rice to be ready at\n"+o_h+hour_x+o_m+minute_x+" Hrs");

    }
}



