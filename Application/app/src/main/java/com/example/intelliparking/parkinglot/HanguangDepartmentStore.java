package com.example.intelliparking.parkinglot;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.intelliparking.R;
import com.example.intelliparking.util.HttpUtil;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hanguang_department_store);

        hds_1 = (ImageView) findViewById(R.id.hds_1);
        hds_2 = (ImageView) findViewById(R.id.hds_2);
        hds_3 = (ImageView) findViewById(R.id.hds_3);
        hds_4 = (ImageView) findViewById(R.id.hds_4);
        hds_5 = (ImageView) findViewById(R.id.hds_5);
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
                String responseData = Objects.requireNonNull(response.body()).string();
                Log.d("HDS", responseData);
                try {
                    JSONObject responseJSON = new JSONObject(responseData);
                    JSONObject infoJSON = new JSONObject(responseJSON.get("info").toString());
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
                                hds_1.setImageResource(R.drawable.empty1);
                            }
                            if (hds_2_s.equalsIgnoreCase("using")) {
                                hds_2.setImageResource(R.drawable.using2);
                            } else if (hds_2_s.equalsIgnoreCase("empty")) {
                                hds_2.setImageResource(R.drawable.empty2);
                            }
                            if (hds_3_s.equalsIgnoreCase("using")) {
                                hds_3.setImageResource(R.drawable.using3);
                            } else if (hds_3_s.equalsIgnoreCase("empty")) {
                                hds_3.setImageResource(R.drawable.empty3);
                            }
                            if (hds_4_s.equalsIgnoreCase("using")) {
                                hds_4.setImageResource(R.drawable.using4);
                            } else if (hds_4_s.equalsIgnoreCase("empty")) {
                                hds_4.setImageResource(R.drawable.empty4);
                            }
                            if (hds_5_s.equalsIgnoreCase("using")) {
                                hds_5.setImageResource(R.drawable.using5);
                            } else if (hds_5_s.equalsIgnoreCase("empty")) {
                                hds_5.setImageResource(R.drawable.empty5);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}