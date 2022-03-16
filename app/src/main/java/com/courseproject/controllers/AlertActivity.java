package com.courseproject.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.courseproject.R;
import com.courseproject.database.AlertNotificationDatabase;
import com.courseproject.model.AlertNotification;

import java.time.LocalDate;

public class AlertActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText type, date, title;
    private Button dateBTN, addBTN;
    private DatePickerDialog datePickerDialog;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        title = findViewById(R.id.alertTitle);
        type = findViewById(R.id.alertType);
        date = findViewById(R.id.alertDate);
        dateBTN = findViewById(R.id.alertDateBTN);
        addBTN = findViewById(R.id.addAlertBTN);

        Intent intent = getIntent();
        title.setText(intent.getStringExtra("title"));
        type.setText(intent.getStringExtra("type"));

        dateBTN.setOnClickListener(action -> datePickerDialog.show());

        addBTN.setOnClickListener(action -> {

            String titleD = title.getText().toString();
            String typeD = type.getText().toString();

            if (selectedDate == null) {
                Toast.makeText(getApplicationContext(), "Please selected date!",
                        Toast.LENGTH_LONG).show();
            } else {
                AlertNotification alertNotification = new AlertNotification(titleD,
                        typeD, selectedDate);
                (new AlertNotificationDatabase(this)).add(alertNotification);
                Toast.makeText(getApplicationContext(), "Alert added!",
                        Toast.LENGTH_LONG).show();
                finish();
            }

        });

        LocalDate now = LocalDate.now();
        datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT,
                this, now.getYear(), now.getMonthValue() - 1, now.getDayOfMonth());

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        selectedDate = LocalDate.of(year, month + 1, day);
        date.setText(selectedDate.toString());

    }

}