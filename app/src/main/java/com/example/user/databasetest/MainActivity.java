package com.example.user.databasetest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private EditText name, email, pass, level, credit;
    private Button btn1, btn2, btn3,btn4;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String semail, spassword,sName,sLevel,sCredit;

    private DatabaseReference refDatabase;
    //private FirebaseAuth mAuth;
    private StudentInfo student;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        level = findViewById(R.id.level);
        credit = findViewById(R.id.credit);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        refDatabase= FirebaseDatabase.getInstance().getReference();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAllInputData();
                createStudent();
                createAccountAndSaveInfo();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewAllActivity.class);
                startActivity(intent);
            }
        });



    }

    void signIn() {
        semail = email.getText().toString().trim();
        spassword = pass.getText().toString().trim();
        progressDialog.setMessage("Please wait!!!");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "LogInSuccess", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Auth failed", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    void createAccount() {
        semail = email.getText().toString().trim();
        spassword = pass.getText().toString().trim();
        if (!semail.isEmpty() && !spassword.isEmpty()) {
            progressDialog.setMessage("Please wait!!!");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(semail, spassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), " Create Success ", Toast.LENGTH_SHORT).show();
                                refDatabase = FirebaseDatabase.getInstance().getReference();
                                refDatabase.child(user.getUid()).setValue(student);
                            } else {
                                Toast.makeText(MainActivity.this, "Failed to create Account", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
            }
        }

    void getAllInputData(){
        sName = name.getText().toString();
        sLevel = level.getText().toString();
        sCredit = credit.getText().toString();
        semail = email.getText().toString().trim();
        spassword = pass.getText().toString().trim();
        }

    void createStudent(){
        student = new StudentInfo(sName,sLevel,sCredit);
    }

    void createAccountAndSaveInfo() {
        progressDialog.setMessage("Please wait!!");
        progressDialog.show();
        semail = email.getText().toString().trim();
        spassword = pass.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(semail, spassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            refDatabase = FirebaseDatabase.getInstance().getReference();
                            refDatabase.child(user.getUid()).setValue(student);
                        } else {
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


}
