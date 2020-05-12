package com.example.studyassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);


        mProgress = new ProgressDialog(this);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignIn();
            }
        });
    }

    private void startSignIn() {
        final String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError("Please enter your Email");
            editTextEmail.requestFocus();
            return;
        }

        else if (password.isEmpty()) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        else if (!(email.isEmpty() && password.isEmpty())) {
            mProgress.setMessage("Logging in...");
            mProgress.show();

            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        mProgress.dismiss();
                        Toast.makeText(LoginActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        checkUserExists();
                    }
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkUserExists(){
        final String user_id = mFirebaseAuth.getCurrentUser().getUid();
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)) {
                    mProgress.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
                else {
                    mProgress.dismiss();
                    Toast.makeText(LoginActivity.this, "You need to setup your account", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showRegisterActivity(View v) {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }
}
