package com.example.IntelliParkingDriver.parkinglot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.IntelliParkingDriver.R;
import com.example.IntelliParkingDriver.util.HttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class HanguangDepartmentStore extends AppCompatActivity {

    private ImageView hds_1;
    private ImageView hds_2;
    private ImageView hds_3;
    private ImageView hds_4;
    private ImageView hds_5;

    private Handler handler;

    private int totalSpace = 0;
    private int currentSpace = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanguang_department_store);

        hds_1 = (ImageView) findViewById(R.id.hds_1);
        hds_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HanguangDepartmentStore.this, Reservation.class);
                intent.putExtra("extra_data", "Hanguang Department Store-1");
                startActivity(intent);
                finish();
            }
        });

        hds_2 = (ImageView) findViewById(R.id.hds_2);
        hds_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HanguangDepartmentStore.this, Reservation.class);
                intent.putExtra("extra_data", "Hanguang Department Store-2");
                startActivity(intent);
                finish();
            }
        });

        hds_3 = (ImageView) findViewById(R.id.hds_3);
        hds_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HanguangDepartmentStore.this, Reservation.class);
                intent.putExtra("extra_data", "Hanguang Department Store-3");
                startActivity(intent);
                finish();
            }
        });

        hds_4 = (ImageView) findViewById(R.id.hds_4);
        hds_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HanguangDepartmentStore.this, Reservation.class);
                intent.putExtra("extra_data", "Hanguang Department Store-4");
                startActivity(intent);
                finish();
            }
        });

        hds_5 = (ImageView) findViewById(R.id.hds_5);
        hds_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HanguangDepartmentStore.this, Reservation.class);
                intent.putExtra("extra_data", "Hanguang Department Store-5");
                startActivity(intent);
                finish();
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
                    final String distance = infoJSON.getString("distance");
                    final String location = infoJSON.getString("location");
                    final String price = infoJSON.getString("price");
                    totalSpace = Integer.parseInt(infoJSON.getString("total space"));
                    JSONObject spaceJSON = new JSONObject(responseJSON.get("space").toString());
                    final String hds_1_s = spaceJSON.get("1").toString();
                    final String hds_2_s = spaceJSON.get("2").toString();
                    final String hds_3_s = spaceJSON.get("3").toString();
                    final String hds_4_s = spaceJSON.get("4").toString();
                    final String hds_5_s = spaceJSON.get("5").toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (hds_1_s.equalsIgnoreCase("using")) {
                                hds_1.setImageResource(R.drawable.using1);
                            } else if (hds_1_s.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                hds_1.setImageResource(R.drawable.empty1);
                            }
                            if (hds_2_s.equalsIgnoreCase("using")) {
                                hds_2.setImageResource(R.drawable.using2);
                            } else if (hds_2_s.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                hds_2.setImageResource(R.drawable.empty2);
                            }
                            if (hds_3_s.equalsIgnoreCase("using")) {
                                hds_3.setImageResource(R.drawable.using3);
                            } else if (hds_3_s.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                hds_3.setImageResource(R.drawable.empty3);
                            }
                            if (hds_4_s.equalsIgnoreCase("using")) {
                                hds_4.setImageResource(R.drawable.using4);
                            } else if (hds_4_s.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                hds_4.setImageResource(R.drawable.empty4);
                            }
                            if (hds_5_s.equalsIgnoreCase("using")) {
                                hds_5.setImageResource(R.drawable.using5);
                            } else if (hds_5_s.equalsIgnoreCase("empty")) {
                                currentSpace += 1;
                                hds_5.setImageResource(R.drawable.empty5);
                            }
                            ((TextView) findViewById(R.id.hds_textView1)).setText("Location: " + location);
                            ((TextView) findViewById(R.id.hds_textView2)).setText("Distance: " + distance);
                            ((TextView) findViewById(R.id.hds_textView3)).setText("Price: " + price);
                            ((TextView) findViewById(R.id.hds_textView4)).setText("Total parking space: " + totalSpace);
                            ((TextView) findViewById(R.id.hds_textView5)).setText("Available parking space: " + currentSpace);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}