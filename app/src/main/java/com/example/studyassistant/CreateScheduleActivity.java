package com.example.studyassistant;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CreateScheduleActivity extends AppCompatActivity{
    private EditText editTextModule, editTextStart, editTextEnd;
    private Spinner spinnerWeekdays, spinnerWeekends;
    private Button buttonAdd, buttonCreateSchedule;
    private TextView textViewModuleTime;
    private ImageButton setModuleTime;
    private CheckBox alarmMe;
    private ListView listViewModule;
    private DatabaseReference mDatabaseSchedule, mDatabaseUsers;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        mDatabaseSchedule = FirebaseDatabase.getInstance().getReference("Schedules");

        editTextModule = findViewById(R.id.editTextModule);
        textViewModuleTime = findViewById(R.id.textViewModuleTime);
        setModuleTime = findViewById(R.id.setModuletime);
        alarmMe = findViewById(R.id.alarmMe);

        //Start DatePicker EditText
        final Calendar myCalender = Calendar.getInstance();
        editTextStart = findViewById(R.id.editTextStart);
        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR,year);
                myCalender.set(Calendar.MONTH,month);
                myCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel(myCalender);
            }
        };
        editTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateScheduleActivity.this, startDate, myCalender
                .get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //End Datepicker EditText
        final Calendar myCalender2 = Calendar.getInstance();
        editTextEnd = findViewById(R.id.editTextEnd);
        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender2.set(Calendar.YEAR,year);
                myCalender2.set(Calendar.MONTH,month);
                myCalender2.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                updateLabel2(myCalender2);
            }
        };
        editTextEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(CreateScheduleActivity.this, endDate, myCalender2
                        .get(Calendar.YEAR), myCalender2.get(Calendar.MONTH), myCalender2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        String[] arrayHours = new String[] {
            "2", "4", "6", "8", "12"
        };

        spinnerWeekdays = findViewById(R.id.spinnerWeekdays);
        spinnerWeekends = findViewById(R.id.spinnerWeekends);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayHours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeekdays.setAdapter(adapter);
        spinnerWeekends.setAdapter(adapter);

        listViewModule = findViewById(R.id.listViewScheduleModuleDetails);
        final ArrayList<String> listItems = new ArrayList<String>();
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (CreateScheduleActivity.this, android.R.layout.simple_list_item_1, listItems);
        listViewModule.setAdapter(adapter3);


        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String module = editTextModule.getText().toString();
                if(module.isEmpty()){
                    editTextModule.setError("Please enter your module name");
                    editTextModule.requestFocus();
                    return;
                }
                if(listItems.size()<10){
                    listItems.add(module);
                    adapter3.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(CreateScheduleActivity.this, "You can only add 10 modules",Toast.LENGTH_SHORT).show();
                }
            }
        });

        setModuleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), R.style.Theme_AppCompat_Dialog
                        , new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat hformat = new SimpleDateFormat("K:mm a", Locale.ENGLISH);
                        String ModuleTime = hformat.format(c.getTime());
                        textViewModuleTime.setText(ModuleTime);

                    }
                }, hours, minutes, false);
                timePickerDialog.show();
            }
        });

        buttonCreateSchedule = findViewById(R.id.buttonCreateSchedule);
        buttonCreateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = editTextStart.getText().toString();
                String end = editTextEnd.getText().toString();

                int weekday_hours = Integer.parseInt(spinnerWeekdays.getSelectedItem().toString());
                int weekend_hours = Integer.parseInt(spinnerWeekends.getSelectedItem().toString());

                if(start.isEmpty()) {
                    editTextStart.setError("Please Enter Your Start Date");
                    editTextStart.requestFocus();
                    return;
                }

                else if(end.isEmpty()) {
                    editTextEnd.setError("Please Enter Your End date");
                    editTextEnd.requestFocus();
                    return;
                }

                else if(listItems.size() == 0) {
                    Toast.makeText(CreateScheduleActivity.this, "Please add your module(s)",Toast.LENGTH_SHORT).show();
                }


                else {
                    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Schedule schedule = new Schedule(
                        user_id,start,end,weekday_hours,weekend_hours
                    );
                    mDatabaseSchedule.setValue(schedule);



                }
            }
        });

    }


    public int getDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int days = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
                ++days;
        } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

        return days;
    }


    public void updateLabel(Calendar calendar){
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        editTextStart.setText(sdf.format(calendar.getTime()));
    }

    public void updateLabel2(Calendar calendar){
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        editTextEnd.setText(sdf.format(calendar.getTime()));
    }
}
