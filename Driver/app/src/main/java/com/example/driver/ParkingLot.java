package com.example.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class ParkingLot extends AppCompatActivity {

    private String place;
    private TextView textView;

    private TextView parkingLotNameTV;
    private TextView addressTV;
    private TextView availableParkingSpacesTV;
    private TextView priceTV;
    private TextView availableTime;

    private String parkingLotName;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_lot);

        place = getIntent().getStringExtra("place");
        textView = (TextView) findViewById(R.id.title);
        textView.setText("Available parking spaces for " + place);

        parkingLotNameTV = (TextView) findViewById(R.id.textView1);
        addressTV = (TextView) findViewById(R.id.textView2);
        availableParkingSpacesTV = (TextView) findViewById(R.id.textView3);
        priceTV = (TextView) findViewById(R.id.textView4);
        availableTime = (TextView) findViewById(R.id.textView5);

        HttpUtil.getReservationInfo(new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = Objects.requireNonNull(response.body()).string();
                try {
                    final JSONObject responseJson = new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                parkingLotName = responseJson.getString("parking lot name");
                                parkingLotNameTV.setText("Parking lot name: " + parkingLotName);
                                addressTV.setText("Address: " + responseJson.getString("address"));
                                availableParkingSpacesTV.setText("Available parking spaces: " + responseJson.getString("available parking spaces"));
                                price = responseJson.getString("price").split(" ")[0];
                                priceTV.setText("Price: " + responseJson.getString("price"));
                                availableTime.setText("Available Time: " + responseJson.getString("available time"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });

        Button button = (Button) findViewById(R.id.reservation);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParkingLot.this, Reservation.class);
                intent.putExtra("extra", parkingLotName + "-" + price);
                startActivity(intent);
                finish();
            }
        });
    }

}