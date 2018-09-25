package com.example.user.databasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewAllActivity extends AppCompatActivity  {

    private ListView studentListView;
    private ArrayList<StudentInfo> allStudent;
    private DatabaseReference databaseReference;
    private static final String TAG = ViewAllActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        studentListView = findViewById(R.id.listView);
        allStudent = new ArrayList<>();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        getAlldataFromDB();
    }

    private void getAlldataFromDB() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    StudentInfo value = data.getValue(StudentInfo.class);
                    allStudent.add(value);
                }
                ItemStudentAdapter studentAdapter = new ItemStudentAdapter(ViewAllActivity.this, allStudent);
                studentListView.setAdapter( studentAdapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

}