package com.example.IntelliParkingDriver.parkinglot;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.IntelliParkingDriver.R;
import com.example.IntelliParkingDriver.util.HttpUtil;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class Reservation extends AppCompatActivity {

    private TextView title;
    private String parkingLotName;
    private String parkingSpace;

    private TextView beginDate;
    private TextView beginTime;
    private TextView endDate;
    private TextView endTime;

    private int beginYear;
    private int beginMonth;
    private int beginDay;
    private int beginHour;
    private int beginMinute;
    private int endYear;
    private int endMonth;
    private int endDay;
    private int endHour;
    private int endMinute;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        parkingLotName = getIntent().getStringExtra("extra_data").split("-")[0];
        parkingSpace = getIntent().getStringExtra("extra_data").split("-")[1];

        title = (TextView) findViewById(R.id.textview_reservation_title);
        title.setText("For parking space: " + parkingSpace);
        beginDate = (TextView) findViewById(R.id.textview_reservation_date_begin);
        beginTime = (TextView) findViewById(R.id.textview_reservation_time_begin);
        endDate = (TextView) findViewById(R.id.textview_reservation_date_end);
        endTime = (TextView) findViewById(R.id.textview_reservation_time_end);

        ((Button) findViewById(R.id.button_reservation_date_begin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Reservation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        beginYear = year;
                        beginMonth = month + 1;
                        beginDay = day;
                        beginDate.setText(Html.fromHtml("Begin Date: <b>" + beginYear + "-" + beginMonth + "-" + beginDay + "</b>"));
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ((Button) findViewById(R.id.button_reservation_time_begin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(Reservation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        beginHour = hour;
                        beginMinute = minute;
                        beginTime.setText(Html.fromHtml("Begin Time: <b>" + beginHour + ":" + beginMinute + "</b>"));
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true).show();
            }
        });

        ((Button) findViewById(R.id.button_reservation_date_end)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Reservation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        endYear = year;
                        endMonth = month + 1;
                        endDay = day;
                        endDate.setText(Html.fromHtml("End Date: <b>" + endYear + "-" + endMonth + "-" + endDay + "</b>"));
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ((Button) findViewById(R.id.button_reservation_time_end)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(Reservation.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        endHour = hour;
                        endMinute = minute;
                        endTime.setText(Html.fromHtml("End Time: <b>" + endHour + ":" + endMinute + "</b>"));
                    }
                }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true).show();
            }
        });

        Button reservation = (Button) findViewById(R.id.button_reservation);
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time = beginYear + "-" + beginMonth + "-" + beginDay + " " + beginHour + ":" + beginMinute
                        + "--" + endYear + "-" + endMonth + "-" + endDay + " " + endHour + ":" + endMinute;
                Log.d("RESERVATION", time);
                HttpUtil.parkingReservation(parkingLotName, parkingSpace, time, new okhttp3.Callback() {
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseData = Objects.requireNonNull(response.body()).string();
                        Log.d("RESERVATION", responseData);
                        if (responseData.equalsIgnoreCase("success")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Reservation.this, "Parking space " + parkingSpace + " reservation successful", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Reservation.this, HanguangDepartmentStore.class);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        } else if (responseData.equalsIgnoreCase("error")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Reservation.this, "Reservation datetime overlap", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
    
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Reservation.this, HanguangDepartmentStore.class);
        startActivity(intent);
        finish();
    }

}