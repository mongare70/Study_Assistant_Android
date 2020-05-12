package com.example.studyassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextInstitution, editTextPassword;
    private RadioGroup radioSex;
    private RadioButton radioButton;
    private Button buttonRegister;
    private DatabaseReference mDatabaseUsers;
    private ProgressDialog mProgress;
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextInstitution = findViewById(R.id.editTextInstitution);
        radioSex = findViewById(R.id.radioSex);
        editTextPassword = findViewById(R.id.editTextPassword);
        mProgress = new ProgressDialog(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startSignUp();
            }
        });
    }

    private void startSignUp(){
        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String institution = editTextInstitution.getText().toString();

        final String sex;
        int selectedId = radioSex.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        sex = radioButton.getText().toString();

        String password = editTextPassword.getText().toString();

        if (name.isEmpty()){
            editTextName.setError("Please enter your name");
            editTextName.requestFocus();
            return;
        }

        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter your Email");
            editTextEmail.requestFocus();
            return;
        }

        else if (institution.isEmpty()){
            editTextInstitution.setError("Please enter the name of your institution");
            editTextInstitution.requestFocus();
            return;
        }

        else if (password.isEmpty()) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }

        else if (password.length() < 6) {
            editTextPassword.setError("Your password must contain at least 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        else if (!(name.isEmpty() && email.isEmpty() && institution.isEmpty() && password.isEmpty())) {
            mProgress.setMessage("Signing up...");
            mProgress.show();

            mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "SignUp Unsuccessful, Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String user_id = mFirebaseAuth.getCurrentUser().getUid();
                        DatabaseReference current_user_db = mDatabaseUsers.child(user_id);
                        current_user_db.child("name").setValue(name);
                        current_user_db.child("email").setValue(email);
                        current_user_db.child("institution").setValue(institution);
                        current_user_db.child("sex").setValue(sex);

                        mProgress.dismiss();
                        Toast.makeText(RegisterActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                }
            });
        }

        else {
            Toast.makeText(RegisterActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    //Opens Main Activity
    public void showMainActivity(View v) {
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
    }

}
