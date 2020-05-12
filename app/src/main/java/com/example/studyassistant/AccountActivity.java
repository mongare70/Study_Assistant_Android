package com.example.studyassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;

public class AccountActivity extends AppCompatActivity {
    private TextView textViewName, textViewEmail, textViewInstitution, textViewGender;
    private Button editProfile;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseUser;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        textViewName = findViewById(R.id.textViewName1);
        textViewEmail = findViewById(R.id.textViewEmail1);
        textViewInstitution = findViewById(R.id.textViewInstitution1);
        textViewGender = findViewById(R.id.textViewGender1);

        mFirebaseAuth = FirebaseAuth.getInstance();
        //get firebase user
        user = FirebaseAuth.getInstance().getCurrentUser();

        //get reference
        mDatabaseUser = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

       mDatabaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               final User user = dataSnapshot.getValue(User.class);
               textViewName.setText(user.getName());
               textViewEmail.setText(user.getEmail());
               textViewInstitution.setText(user.getInstitution());
               textViewGender.setText(user.getSex());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               System.out.println("The read failed: " + databaseError.getCode());
           }
       });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemDashboard:
                Intent openDashboard = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(openDashboard);
                return true;
            case R.id.menuItemSchedules:
                Intent createSchedule = new Intent(AccountActivity.this, CreateScheduleActivity.class);
                startActivity(createSchedule);
                return true;
            case R.id.menuItemSession:
                Toast.makeText(this, "Session selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemReports:
                Toast.makeText(this, "Reports selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemAccount:
                Intent openAccount = new Intent(AccountActivity.this, AccountActivity.class);
                startActivity(openAccount);
                return true;
            case R.id.menuItemLogOut:
                Intent logOut = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(logOut);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
