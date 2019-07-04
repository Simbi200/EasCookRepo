package net.calc.eascook;

import android.app.TimePickerDialog;
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
    public int portion, pTimerInt;
    public int countDownTimeInSeconds, hr_current, min_current;
    public Button timeButton, startBTN;
    public TextView setTimeText, feedBack, fbtxt2;
    public RadioGroup radioGroup;
    public String timeString, hr, h1, mn, m1, fm, portz, confTxt, single_portion, resPOns;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootRef = firebaseDatabase.getReference();
    private DatabaseReference mChildFeedback = mRootRef.child("feedback");
    //private DatabaseReference mChildRef2 = mRootRef.child("num2");


    public MainActivity() {
        hr = " ";
        fm = "Hrs";
        h1 = " ";
        mn = " ";
        m1 = " ";
        portz = " ";
        confTxt = " ";
        portion = 1;
        pTimerInt = 0;
        resPOns = "";
        countDownTimeInSeconds = 0;
        single_portion = "single";
        fm = "Hrs";
    }

    public static FragmentManager fragmentManager;

    @Override //on create
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedBack = findViewById(R.id.feedBack_id);
        toolbar = findViewById(R.id.action_bar);
        startBTN = findViewById(R.id.start_id2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("EasCook");
        toolbar.setLogo(R.drawable.logo);
        fragmentManager = getSupportFragmentManager();


        //add first fragment
        if (findViewById(R.id.frag_container_id_1) != null) {
            if (savedInstanceState != null) {
                return;
            }

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            UpFragment upFragment = new UpFragment();
            fragmentTransaction.add(R.id.frag_container_id_1, upFragment, null);
            fragmentTransaction.commit();
        }
    }

    private void sendAmountData() {

        mRootRef.child("myDb").child("awais@gmailcom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    mRootRef.child("time").setValue(countDownTimeInSeconds);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    mRootRef.child("portion").setValue(portion);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    mRootRef.child("session").setValue(1);
                } catch (Exception e) {
                    e.printStackTrace();

                } try {
                    mRootRef.child("interapt").setValue("OK");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    mRootRef.child("feedback").setValue("Wait... \ncooking will in a moment");
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

    }

    @Override
    //create action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    // onClick listeners for menu items
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.exit_id:
                finish();
                System.exit(0);
                return true;

            case R.id.help_id:
                Toast.makeText(getApplicationContext(), "this will show help", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    // onClick methods for radio buttons
    public void onClicked(View view) {
        radioGroup = findViewById(R.id.radioGroup_id);
        int id = radioGroup.getCheckedRadioButtonId();
        setTimeText = findViewById(R.id.selectedTimeRes_id);
        String s;

        switch (id) {
            case R.id.radioButton1:
                portion = 1;
                Toast.makeText(this, portion + " portion", Toast.LENGTH_SHORT).show();
                s = "you've selected " + single_portion + " portion";
                setTimeText.setText(s);// display message on screen
                break;

            case R.id.radioButton2:
                portion = 2;
                Toast.makeText(this, portion + " portions", Toast.LENGTH_SHORT).show();
                portz = "portion";
                if (portion > 1) {
                    portz = portz + "s";
                }
                s = "you've selected " + portion + " " + portz;
                setTimeText.setText(s);// display message on screen
                break;

            case R.id.radioButton3:
                portion = 3;
                Toast.makeText(this, portion + " portions", Toast.LENGTH_SHORT).show();
                portz = "portion";
                if (portion > 1) {
                    portz = portz + "s";
                }
                s = "you've selected " + portion + " " + portz;
                setTimeText.setText(s);// display message on screen
                break;
        }
        //setTimeText.setText(confTxt);// display message on screen
    }

    // onClick method for set time button
    public void btClicked(View view) {
        timeButton = findViewById(R.id.timeButton_id);

        Calendar calendar = Calendar.getInstance();

        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);

        hr_current = calendar.get(Calendar.HOUR_OF_DAY);
        min_current = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                MainActivity.this, h, m, true);
        timePickerDialog.show();
    }

    @Override // on set time method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        setTimeText = findViewById(R.id.selectedTimeRes_id);
        int ht = hourOfDay;
        int mt = minute;
        int currentTinSec = ((hr_current * 3600) + min_current * 60);
        int selectedInSec = ((ht * 3600) + mt * 60);
        h1 = Integer.toString(hourOfDay);
        m1 = Integer.toString(minute);

        // calculate countdown seconds
        if (selectedInSec == currentTinSec) {
            countDownTimeInSeconds = 0;
        } else if (selectedInSec > currentTinSec) {
            countDownTimeInSeconds = selectedInSec;
        } else {
            countDownTimeInSeconds = (86400 - currentTinSec) + selectedInSec;
        }

        // format time for display
        if (hourOfDay < 10) {
            hr = "0" + h1;
        } else {
            hr = h1;
        }
        if (minute < 10) {

            mn = "0" + m1;
        } else {
            mn = m1;
        }
        timeString = hr + ":" + mn + " " + fm;
        if (countDownTimeInSeconds == 0) {
            confTxt = "you have selected " + portion + " " + portz + " of rice\nit'll be ready in 40 minutes"; // create feedback message

        } else {
            confTxt = "you have selected " + portion + " " + portz + " of rice\nit'll be ready at " + timeString; // create feedback message
        }
        setTimeText.setText(confTxt);// display message on screen
    }

    public void startBtClicked(View view) {

        sendAmountData();

        //add second fragment
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DownFragment downFragment = new DownFragment();
        fragmentTransaction.replace(R.id.frag_container_id_1, downFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void cancelButtonClicked(View view) {


        mRootRef.child("myDb").child("awais@gmailcom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    mRootRef.child("interapt").setValue("cancel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
        Toast.makeText(this,
                "Cooking Cancelled", Toast.LENGTH_LONG).show();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        UpFragment upFragment = new UpFragment();
        fragmentTransaction.replace(R.id.frag_container_id_1, upFragment, null);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
