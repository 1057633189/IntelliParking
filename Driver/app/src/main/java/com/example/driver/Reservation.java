package com.example.driver;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class Reservation extends AppCompatActivity {

    private String parkingLotName;
    private int price;
    private int hour;

    private EditText driverNameET;
    private EditText phoneNumberET;
    private EditText carNumberET;
    private EditText bookingTimeET;

    private String driverName;
    private String phoneNumber;
    private String carNumber;
    private String bookingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        String extra = getIntent().getStringExtra("extra");
        parkingLotName = extra.split("-")[0];
        price = Integer.parseInt(extra.split("-")[1]);

        TextView parkingLotNameTV = (TextView) findViewById(R.id.rr1);
        parkingLotNameTV.setText(parkingLotName);

        driverNameET = (EditText) findViewById(R.id.rr2);
        phoneNumberET = (EditText) findViewById(R.id.rr3);
        carNumberET = (EditText) findViewById(R.id.rr4);
        bookingTimeET = (EditText) findViewById(R.id.rr5);

        Button button = (Button) findViewById(R.id.book);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driverName = driverNameET.getText().toString();
                phoneNumber = phoneNumberET.getText().toString();
                carNumber = carNumberET.getText().toString();
                bookingTime = bookingTimeET.getText().toString();
                int beginTime = Integer.parseInt(bookingTime.split(" ")[1].split("-")[0].split(":")[0]);
                int endTime = Integer.parseInt(bookingTime.split(" ")[1].split("-")[1].split(":")[0]);
                hour = endTime - beginTime;

                AlertDialog dialog = new AlertDialog.Builder(Reservation.this)
                        .setIcon(R.drawable.alipay)
                        .setTitle("ALIPAY")
                        .setMessage("Do you want to book?\n" +
                                "Price: " + price + " RMB/hour\n" +
                                "Hour: " + hour + "\n" +
                                "Total price: " + (price * hour) + " RMB")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(Reservation.this, "Cancel payment", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HttpUtil.reservation(new String[] {driverName, phoneNumber, carNumber, bookingTime}, new okhttp3.Callback() {
                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        String responseData = Objects.requireNonNull(response.body()).string();
                                        if (responseData.equalsIgnoreCase("success")) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(Reservation.this, "Payment successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(Reservation.this, Success.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            });
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                                dialog.dismiss();
                            }
                        }).create();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
        });
    }

}