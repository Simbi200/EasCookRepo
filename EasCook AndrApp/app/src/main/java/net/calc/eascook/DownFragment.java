package net.calc.eascook;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import static net.calc.eascook.MainActivity.fragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownFragment extends Fragment {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootRef = firebaseDatabase.getReference();
    private DatabaseReference mChildFeedback = mRootRef.child("feedback");
    private DatabaseReference mDoneChild = mRootRef.child("session");


    public TextView feedBack;


    public DownFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_down, container, false);

        feedBack = view.findViewById(R.id.feedBack_id);
        return view;
    }


    @Override
    public void onStart() {

        super.onStart();

        mChildFeedback.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String message = dataSnapshot.getValue(String.class);
                feedBack.setText(message);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}

