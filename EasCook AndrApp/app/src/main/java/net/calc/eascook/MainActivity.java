package net.calc.eascook;

import android.app.TimePickerDialog;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    public Toolbar toolbar;
    public int portion;
    public int timeInSeconds;
    public Button timeButton;
    public TextView setTimeText, fbtxt, fbtxt2;
    public RadioGroup radioGroup;
    public String timeString, hr, h1, mn, m1, responce;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootRef = firebaseDatabase.getReference();
    private DatabaseReference mChildRef = mRootRef.child("FireBaseTimeValue");
    private DatabaseReference mChildRef2 = mRootRef.child("FireBasePortionValue");


    public MainActivity(){
        hr = " ";
        h1 = " ";
        mn = " ";
        m1 = " ";
        portion = 1;
        timeInSeconds = 0;
    }

    public static FragmentManager fragmentManager;

    @Override //on create
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbtxt = findViewById(R.id.fbtxt_id);
        fbtxt2 = findViewById(R.id.fbtxt2_id);
        toolbar = findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EasCook");
        toolbar.setLogo(R.drawable.logo);
        fragmentManager = getSupportFragmentManager();
        //setTimeText = findViewById(R.id.selectedTimeRes_id);


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
        {/*
        if(findViewById(R.id.frag_container_id_2)!=null){
            if(savedInstanceState!=null){
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            DownFragment downFragment = new DownFragment();
            fragmentTransaction.add(R.id.frag_container_id_2,downFragment,null);
            fragmentTransaction.commit();
        }*/
        }


    }

    private void sendAmountData() {

        mRootRef.child("myDb").child("awais@gmailcom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String mPortion = Integer.toString(portion);
                String t = Integer.toString(timeInSeconds);

                try {
                    mRootRef.child("FireBaseTimeValue").setValue(t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    mRootRef.child("FireBasePortionValue").setValue(mPortion);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
    }

    protected void onStart() {

        super.onStart();
        mChildRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String message = dataSnapshot.getValue(String.class);
                fbtxt.setText(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mChildRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String message = dataSnapshot.getValue(String.class);
                fbtxt2.setText(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    // onClick method for set time button
    public void btClicked(View view) {
        timeButton = findViewById(R.id.timeButton_id);

        Calendar calendar = Calendar.getInstance();

        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                MainActivity.this, h, m, false);
        timePickerDialog.show();

    }

    @Override // on set time method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTimeText = findViewById(R.id.selectedTimeRes_id);
        timeInSeconds = ((hourOfDay*60)+minute);
        h1 = Integer.toString(hourOfDay);
        m1 = Integer.toString(minute);
        String fm = "Hrs";
        String portz, confTxt;

        // format time
        {
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
        timeString = hr+":"+mn+" "+fm;}

        portz = "portion"; if (portion>1){portz = portz+"s";}
        confTxt = "you have selected "+portion+" "+portz+" of rice\nit'll be ready at "+timeString; // create feedback message
        //Toast.makeText(this,confTxt, Toast.LENGTH_LONG).show(); //toast message

        setTimeText.setText(confTxt);// display message on screen
        //sendAmountData();

    }

    public void startBtClicked(View view) {
        sendAmountData();
    }
}
