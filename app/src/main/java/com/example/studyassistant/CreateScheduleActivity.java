package com.example.studyassistant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CreateScheduleActivity extends AppCompatActivity {
    private EditText editTextModule, editTextStart, editTextEnd;
    private SeekBar seekBarDifficulty;
    private Spinner spinnerWeekdays, spinnerWeekends;
    private Button buttonAdd, buttonCreateSchedule;
    private ListView listView;


    public CreateScheduleActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

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

        seekBarDifficulty = findViewById(R.id.seekBarDifficulty);
        int difficulty = seekBarDifficulty.getProgress();
        final String rating = String.valueOf(difficulty);

        String[] arrayHours = new String[] {
            "2", "4", "6", "8", "12"
        };

        spinnerWeekdays = findViewById(R.id.spinnerWeekdays);
        spinnerWeekends = findViewById(R.id.spinnerWeekends);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, arrayHours);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWeekdays.setAdapter(adapter);
        spinnerWeekends.setAdapter(adapter);

        listView = findViewById(R.id.listViewScheduleDetails);
        final ArrayList<String> listItems = new ArrayList<String>(  );
        final ArrayAdapter<String> adapter3 = new ArrayAdapter<String>
                (CreateScheduleActivity.this, android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter3);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems.add(editTextModule.getText().toString() + " " + rating);
                adapter3.notifyDataSetChanged();
            }
        });

        buttonCreateSchedule = findViewById(R.id.buttonCreateSchedule);
        buttonCreateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
