package com.example.IntelliParkingParkingLot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class InformationManagement extends AppCompatActivity {

    private String parkingLotName = "Hanguang Department Store";

    private TextView locationTV;
    private TextView priceTV;
    private TextView totalSpaceTV;
    private TextView availableSpaceTV;

    private Button changePrice;

    private ImageView space1;
    private ImageView space2;
    private ImageView space3;
    private ImageView space4;
    private ImageView space5;

    private int totalSpace;
    private int currentSpace;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_management);

        locationTV = (TextView) findViewById(R.id.im_textview_location);
        priceTV = (TextView) findViewById(R.id.im_textview_price);
        totalSpaceTV = (TextView) findViewById(R.id.im_textview_total_space);
        availableSpaceTV = (TextView) findViewById(R.id.im_textview_available_space);

        changePrice = (Button) findViewById(R.id.im_button);

        space1 = (ImageView) findViewById(R.id.im_1);
        space1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParkingLotReservationInfo(parkingLotName, "1");
            }
        });

        space2 = (ImageView) findViewById(R.id.im_2);
        space2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParkingLotReservationInfo(parkingLotName, "2");
            }
        });

        space3 = (ImageView) findViewById(R.id.im_3);
        space3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParkingLotReservationInfo(parkingLotName, "3");
            }
        });

        space4 = (ImageView) findViewById(R.id.im_4);
        space4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParkingLotReservationInfo(parkingLotName, "4");
            }
        });

        space5 = (ImageView) findViewById(R.id.im_5);
        space5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParkingLotReservationInfo(parkingLotName, "5");
            }
        });

        getParkingLotInfo();
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                getParkingLotInfo();
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    private void getParkingLotInfo() {
        HttpUtil.parkingLotInfo("Hanguang Department Store", new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                currentSpace = 0;
                String responseData = Objects.requireNonNull(response.body()).string();
                Log.d("HDS", responseData);
                try {
                    JSONObject responseJSON = new JSONObject(responseData);
                    JSONObject infoJSON = new JSONObject(responseJSON.get("info").toString());
                    final String location = infoJSON.getString("location");
                    final String price = infoJSON.getString("price");
                    totalSpace = Integer.parseInt(infoJSON.getString("total space"));
                    JSONObject spaceJSON = new JSONObject(responseJSON.get("space").toString());
                    final String s_1 = spaceJSON.get("1").toString();
                    final String s_2 = spaceJSON.get("2").toString();
                    final String s_3 = spaceJSON.get("3").toString();
                    final String s_4 = spaceJSON.get("4").toString();
                    final String s_5 = spaceJSON.get("5").toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (s_1.equalsIgnoreCase("using")) {
                                space1.setImageResource(R.drawable.using1);
                            } else if (s_1.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                space1.setImageResource(R.drawable.empty1);
                            }
                            if (s_2.equalsIgnoreCase("using")) {
                                space2.setImageResource(R.drawable.using2);
                            } else if (s_2.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                space2.setImageResource(R.drawable.empty2);
                            }
                            if (s_3.equalsIgnoreCase("using")) {
                                space3.setImageResource(R.drawable.using3);
                            } else if (s_3.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                space3.setImageResource(R.drawable.empty3);
                            }
                            if (s_4.equalsIgnoreCase("using")) {
                                space4.setImageResource(R.drawable.using4);
                            } else if (s_4.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                space4.setImageResource(R.drawable.empty4);
                            }
                            if (s_5.equalsIgnoreCase("using")) {
                                space5.setImageResource(R.drawable.using5);
                            } else if (s_5.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                space5.setImageResource(R.drawable.empty5);
                            }
                            locationTV.setText("Location: " + location);
                            priceTV.setText("Price: " + price);
                            totalSpaceTV.setText("Total parking space: " + totalSpace);
                            availableSpaceTV.setText("Available parking space: " + currentSpace);
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
    }

    private void getParkingLotReservationInfo(String parkingLotName, String parkingSpace) {
        HttpUtil.parkingLotReservationInfo(parkingLotName, parkingSpace, new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData = Objects.requireNonNull(response.body()).string();
                Log.d("RESERVATION", responseData);
                if (responseData.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InformationManagement.this, "No reservation", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InformationManagement.this, responseData, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}