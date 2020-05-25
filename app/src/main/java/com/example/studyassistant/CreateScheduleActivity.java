package com.example.studyassistant;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Locale;

public class CreateScheduleActivity extends AppCompatActivity{
    private TextView textViewSeekBarDifficulty;
    private EditText editTextModule, editTextStart, editTextEnd;
    private SeekBar seekBarDifficulty;
    private Spinner spinnerWeekdays, spinnerWeekends;
    private Button buttonAdd, buttonCreateSchedule;
    private ListView listViewModule, listViewRating;
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

        textViewSeekBarDifficulty = findViewById(R.id.textViewSeekBarDifficulty);
        seekBarDifficulty = findViewById(R.id.seekBarDifficulty);
        // perform seek bar change listener event used for getting the progress value
        seekBarDifficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String rating = String.valueOf(progressChangedValue);
                textViewSeekBarDifficulty.setText(rating);
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


        listViewRating = findViewById(R.id.listViewScheduleRatingDetails);
        final ArrayList<Integer> listItems1 = new ArrayList<Integer>(  );
        final ArrayAdapter<Integer> adapter4 = new ArrayAdapter<Integer>
                (CreateScheduleActivity.this, android.R.layout.simple_list_item_1, listItems1);
        listViewRating.setAdapter(adapter4);


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
                if(listItems.size()<10 && listItems1.size()<10){
                    listItems.add(module);
                    listItems1.add(Integer.parseInt(textViewSeekBarDifficulty.getText().toString()));
                    adapter3.notifyDataSetChanged();
                    adapter4.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(CreateScheduleActivity.this, "You can only add 10 modules",Toast.LENGTH_SHORT).show();
                }
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

                else if(listViewModule== null) {
                    Toast.makeText(CreateScheduleActivity.this, "Please add your modules first",Toast.LENGTH_SHORT).show();
                }

                else if(listViewRating==null){
                    Toast.makeText(CreateScheduleActivity.this, "Please add your ratings first", Toast.LENGTH_SHORT).show();
                }

                else {
                    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    Schedule schedule = new Schedule(
                        user_id,start,end,weekday_hours,weekend_hours
                    );
                    mDatabaseSchedule.setValue(schedule);

                    int total_study_hours = 0;
                    try {
                        total_study_hours = totalStudyHourCalculator(weekday_hours, weekend_hours, start, end);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    int total_rating = totalRatingCalculator(
                           listItems1
                    );

                    String[][] modules1 = moduleCreator(listItems, listItems1, total_study_hours, total_rating);
                    /*try {
                        sessionCreator(weekday_hours, weekend_hours,start,end, modules1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                     */

                }
            }
        });

    }
    //Session Creator Function
    public void sessionCreator(int weekday_hours, int weekend_hours, String start, String end, String[][] modules1) throws ParseException {
        SimpleDateFormat format =  new SimpleDateFormat("dd/MM/yy", Locale.UK);
        Date start_date = format.parse(start);
        Date end_date = format.parse(end);

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(start_date);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(end_date);
        //Loops through all the days of the schedule, checks if day is weekday or weekend and allocates hours according to users study ability
       while(!startCal.after(endCal)){
            //If it's a Weekday
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                for (int x = 0; x < weekday_hours; x += 2) {
                    if (modules1 != null) {
                        // Generates a random key based on its weight
                        while (true) {
                            int random = (int) (Math.random() * 100 + 1);
                            for (int j = 0; j < modules1.length; j++) {
                                random -= Math.round(Double.valueOf(modules1[j][3]));

                                if (random <= 0) {
                                    break;
                                }

                                else if (random >modules1.length){
                                    break;
                                }

                                else if (modules1.length <= 1) {
                                    break;
                                }

                                else {
                                    Session session = new Session(
                                            modules1[random][0],
                                            format.format(startCal.getTime())
                                    );
                                    Log.d("DEBUG", "This is the session module & start date for weekday" +" "+ session.getModule()+ " "+session.getDate());
                                }
                            }
                        }
                    }
                }
            }
        startCal.add(Calendar.DATE, 1);
        }
    }

    //Module Creator Function
    public String[][] moduleCreator(ArrayList<String> modules, ArrayList<Integer> ratings, int total_study_hours, int total_rating){
        String[][] modules1 = new String[10][4];
        Log.d("DEBUG", "The total rating is: "+total_rating);

        for(int i=0; i<modules.size(); i++) {
            int a = ratings.get(i);
            Log.d("DEBUG", "The rating is: " + a);
            int average_rating = a/total_rating;
            Log.d("DEBUG", "The average rating is: " + average_rating);
        }
               /*
                int hours_per_module = (int) Math.round(total_study_hours*average_rating);
                //Module
                modules1[i][0] = modules.get(i);
                //Rating
                modules1[i][1] = String.valueOf(ratings.get(i));
                //Hours
                modules1[i][2] = String.valueOf(hours_per_module);
                //Weight
                modules1[i][3] = String.valueOf(average_rating*100);

            Module module = new Module(
                    modules1[i][0],
                    Integer.valueOf(modules1[i][1])
            );

                */

        return modules1;
    }
    //Total Rating Calculator Function
    public int totalRatingCalculator(ArrayList<Integer> ratings){
        int total_rating = 0;
        for(int i=0; i<ratings.size(); i++){
            total_rating += ratings.get(i);
        }
        return total_rating;
    }

    //Total Study Hour Calculator Function
    public int totalStudyHourCalculator(int weekday_hours, int weekend_hours, String start, String end) throws ParseException {
        SimpleDateFormat format =  new SimpleDateFormat("dd/MM/yy", Locale.UK);
        Date start_date = format.parse(start);
        Date end_date = format.parse(end);

        int no_week_days = getWeekDaysBetweenTwoDates(start_date,end_date);
        int no_weekend_days = getWeekendDaysBetweenTwoDates(start_date, end_date);

        int total_study_hours = (no_week_days * weekday_hours) + (no_weekend_days * weekend_hours);

        return total_study_hours;
    }

    public int getWeekendDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int weekendDays = 0;

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
            if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                ++weekendDays;
            }
        } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

        return weekendDays;
    }

    public int getWeekDaysBetweenTwoDates(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int weekDays = 0;

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
            if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                ++weekDays;
            }
        } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

        return weekDays;
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
