package net.calc.eascook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Button btn_time;
    TextView time_picker;
    int hour, minute;
    int hour_x, minute_x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_time = findViewById(R.id.readyTime);
        time_picker = findViewById(R.id.time1);



        Spinner riceAmountSpin = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.riceAmount));

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        riceAmountSpin.setAdapter(spinnerAdapter);



        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR);
                minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, MainActivity.this, hour, minute, true);
                timePickerDialog.show();

            }

        });




        };




    @Override
    public void onTimeSet(TimePicker view, int i, int i1) {
        hour_x = i;
        minute_x = i1;
        time_picker.setText("Rice to be ready at\n"+hour_x+":"+minute_x);

    }



}



