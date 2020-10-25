package com.example.parkinglot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class Reservation extends AppCompatActivity {

    private EditText parkingLotNameET;
    private EditText addressET;
    private EditText availableParkingSpacesET;
    private EditText priceET;
    private EditText availableTimeET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        parkingLotNameET = (EditText) findViewById(R.id.edittext1);
        addressET = (EditText) findViewById(R.id.edittext2);
        availableParkingSpacesET = (EditText) findViewById(R.id.edittext3);
        priceET = (EditText) findViewById(R.id.edittext4);
        availableTimeET = (EditText) findViewById(R.id.edittext5);

        Button post = (Button) findViewById(R.id.button2);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String parkingLotName = parkingLotNameET.getText().toString();
                String address = addressET.getText().toString();
                String availableParkingSpaces = availableParkingSpacesET.getText().toString();
                String price = priceET.getText().toString();
                String availableTime = availableTimeET.getText().toString();

                HttpUtil.postReservationInfo(new String[] {parkingLotName, address, availableParkingSpaces,
                        price, availableTime}, new okhttp3.Callback() {
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseData = Objects.requireNonNull(response.body()).string();
                        Log.d("POST", responseData);
                        if (responseData.equalsIgnoreCase("success")) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Reservation.this, "Post Successful", Toast.LENGTH_SHORT).show();
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

}