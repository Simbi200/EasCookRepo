package net.calc.eascook;

import android.app.TimePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    public Toolbar toolbar;
    public int portion = 1;
    public int timeInSeconds;
    public Button timeButton;
    public TextView setTimeText;
    public RadioGroup radioGroup;
    public String timeString;
    String hr = " ";
    String h1 = " ";
    String mn = " ";
    String m1 = " ";


    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EasCook");
        toolbar.setLogo(R.drawable.logo);
        fragmentManager = getSupportFragmentManager();
        setTimeText = findViewById(R.id.selectedTimeRes_id);




        //add first fragment
        if(findViewById(R.id.frag_container_id_1)!=null){
            if(savedInstanceState!=null){
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            UpFragment upFragment = new UpFragment();
            fragmentTransaction.add(R.id.frag_container_id_1,upFragment,null);
            fragmentTransaction.commit();
        }

        //add second fragment
        if(findViewById(R.id.frag_container_id_2)!=null){
            if(savedInstanceState!=null){
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DownFragment downFragment = new DownFragment();
            fragmentTransaction.add(R.id.frag_container_id_2,downFragment,null);
            fragmentTransaction.commit();
        }

    }

    @Override
    //create action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    // onClick listeners for menu items
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.exit_id:
                finish();
                System.exit(0);
                return true;

            case R.id.help_id:
                Toast.makeText(getApplicationContext(),"this will show help", Toast.LENGTH_LONG).show();
        }
        return true;
    }


    // onClick methods for radio buttons
    public void onClicked(View view) {
        radioGroup = findViewById(R.id.radioGroup_id);
        int id = radioGroup.getCheckedRadioButtonId();

        switch (id){
            case R.id.radioButton1:
                portion = 1;
                Toast.makeText(this, portion+" portion", Toast.LENGTH_SHORT).show();
                break;

            case R.id.radioButton2:
                portion = 2;
                Toast.makeText(this, portion+" portions", Toast.LENGTH_SHORT).show();
                break;


            case R.id.radioButton3:
                portion = 3;
                Toast.makeText(this, portion+" portions", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void btClicked(View view) {
        timeButton = findViewById(R.id.timeButton_id);

        Calendar calendar = Calendar.getInstance();

        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                MainActivity.this, h, m, false);
        timePickerDialog.show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeInSeconds = ((hourOfDay*60)+minute);
        h1 = Integer.toString(hourOfDay);
        m1 = Integer.toString(minute);

        String fm = "Hrs";
        if (hourOfDay<10){
            hr = "0"+ h1;
        }
        else{
            hr = h1;
        }
        if (minute<10){

            mn = "0"+m1;
        }
        else{
            mn=m1;
        }
        timeString = hr+":"+mn+" "+fm;
        String portz = "portion"; if (portion>1){portz = portz+"s";}

        String confTxt = "you have selected "+portion+" "+portz+" of rice\nit'll be ready at "+timeString;

        Toast.makeText(this,confTxt, Toast.LENGTH_LONG).show();

        //setTimeText.setText(confTxt);

    }
}
