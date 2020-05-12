package com.example.studyassistant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mDatabaseUsers;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference().child("StudyAssistant");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseUsers.keepSynced(true);
        mDatabase.keepSynced(true);
    }

    private void checkUserExists(){
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.hasChild(user_id)) {
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
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
                Intent openDashboard = new Intent(MainActivity.this, MainActivity.class);
                startActivity(openDashboard);
                return true;
            case R.id.menuItemSchedules:
                Intent createSchedule = new Intent(MainActivity.this, CreateScheduleActivity.class);
                startActivity(createSchedule);
                return true;
            case R.id.menuItemSession:
                Toast.makeText(this, "Session selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemReports:
                Toast.makeText(this, "Reports selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuItemAccount:
                Intent openAccount = new Intent(MainActivity.this, AccountActivity.class);
                startActivity(openAccount);
                return true;
            case R.id.menuItemLogOut:
                Intent logOut = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(logOut);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
