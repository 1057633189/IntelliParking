package com.example.IntelliParkingParkingLot;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Response;

public class InformationManagement extends AppCompatActivity {

    private String parkingLotName = "Hanguang Department Store";

    private TextView locationTV;
    private TextView priceTV;
    private TextView totalSpaceTV;
    private TextView availableSpaceTV;
    private TextView utilizationRateTV;

    private Button changePrice;

    private ImageView space1;
    private ImageView space2;
    private ImageView space3;
    private ImageView space4;
    private ImageView space5;
    private ImageView space6;

    private int totalSpace;
    private int availableSpace;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_management);

        locationTV = (TextView) findViewById(R.id.im_textview_location);
        priceTV = (TextView) findViewById(R.id.im_textview_price);
        totalSpaceTV = (TextView) findViewById(R.id.im_textview_total_space);
        availableSpaceTV = (TextView) findViewById(R.id.im_textview_available_space);
        utilizationRateTV = (TextView) findViewById(R.id.im_textview_utilization_rate);

        changePrice = (Button) findViewById(R.id.im_button);
        changePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(InformationManagement.this);
                AlertDialog.Builder b = new AlertDialog.Builder(InformationManagement.this)
                        .setIcon(R.drawable.change_price)
                        .setTitle("Change Price")
                        .setView(input)
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String price = input.getText().toString();
                                HttpUtil.changePrice(parkingLotName, price, new okhttp3.Callback() {
                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                        String responseData = Objects.requireNonNull(response.body()).string();
                                        Log.d("IM", responseData);
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(InformationManagement.this, "Change price successful", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }

                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                                dialogInterface.dismiss();
                            }
                        });
                b.show();
            }
        });

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

        space6 = (ImageView) findViewById(R.id.im_6);
        space6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParkingLotReservationInfo(parkingLotName, "6");
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
        HttpUtil.parkingLotInfo(parkingLotName, new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                availableSpace = 0;
                String responseData = Objects.requireNonNull(response.body()).string();
                Log.d("IM", responseData);
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
                    final String s_6 = spaceJSON.get("6").toString();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (s_1.equalsIgnoreCase("using")) {
                                space1.setImageResource(R.drawable.using1);
                            } else if (s_1.equalsIgnoreCase("empty")) {
                                availableSpace += 1;
                                space1.setImageResource(R.drawable.empty1);
                            }

                            if (s_2.equalsIgnoreCase("using")) {
                                space2.setImageResource(R.drawable.using2);
                            } else if (s_2.equalsIgnoreCase("empty")) {
                                availableSpace += 1;
                                space2.setImageResource(R.drawable.empty2);
                            }

                            if (s_3.equalsIgnoreCase("using")) {
                                space3.setImageResource(R.drawable.using3);
                            } else if (s_3.equalsIgnoreCase("empty")) {
                                availableSpace += 1;
                                space3.setImageResource(R.drawable.empty3);
                            }

                            if (s_4.equalsIgnoreCase("using")) {
                                space4.setImageResource(R.drawable.using4);
                            } else if (s_4.equalsIgnoreCase("empty")) {
                                availableSpace += 1;
                                space4.setImageResource(R.drawable.empty4);
                            }

                            if (s_5.equalsIgnoreCase("using")) {
                                space5.setImageResource(R.drawable.using5);
                            } else if (s_5.equalsIgnoreCase("empty")) {
                                availableSpace += 1;
                                space5.setImageResource(R.drawable.empty5);
                            }

                            if (s_6.equalsIgnoreCase("using")) {
                                space6.setImageResource(R.drawable.using6);
                            } else if (s_6.equalsIgnoreCase("empty")) {
                                availableSpace += 1;
                                space6.setImageResource(R.drawable.empty6);
                            }

                            locationTV.setText(Html.fromHtml("Location: <b>" + location + "</b>"));
                            priceTV.setText(Html.fromHtml("Price: <b>" + price + "</b>"));
                            totalSpaceTV.setText(Html.fromHtml("Total parking space: <b>" + totalSpace + "</b>"));
                            availableSpaceTV.setText(Html.fromHtml("Available parking space: <b>" + availableSpace + "</b>"));
                            double utilizationRate = (double) (totalSpace - availableSpace) / totalSpace;
                            BigDecimal bd = new BigDecimal(utilizationRate);
                            String ur = String.valueOf(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                            if (ur.equalsIgnoreCase("1.0")) {
                                utilizationRateTV.setText(Html.fromHtml("Parking space utilization rate: <b>100%</b>"));
                            } else if (ur.equalsIgnoreCase("0.0")) {
                                utilizationRateTV.setText(Html.fromHtml("Parking space utilization rate: <b>0%</b>"));
                            } else {
                                utilizationRateTV.setText(Html.fromHtml("Parking space utilization rate: <b>" + ur + "%</b>"));
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

    private void getParkingLotReservationInfo(String parkingLotName, final String parkingSpace) {
        HttpUtil.parkingLotReservationInfo(parkingLotName, parkingSpace, new okhttp3.Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String responseData = Objects.requireNonNull(response.body()).string();
                Log.d("IM", responseData);
                if (responseData.isEmpty()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(InformationManagement.this, "No reservation for parking space " + parkingSpace, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseData.contains(";")) {
                                String[] items = responseData.split(";");
                                for (int i = 0; i < items.length; i++) {
                                    items[i] = (i + 1) + ". " + items[i].split("--")[0] + " -- " + items[i].split("--")[1];
                                }
                                AlertDialog dialog = new AlertDialog.Builder(InformationManagement.this)
                                        .setIcon(R.drawable.reservation_info)
                                        .setTitle("Reservation Information\nfor parking space " + parkingSpace)
                                        .setItems(items, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.show();
                            } else {
                                String s = responseData.split("--")[0] + " -- " + responseData.split("--")[1];
                                AlertDialog dialog = new AlertDialog.Builder(InformationManagement.this)
                                        .setIcon(R.drawable.reservation_info)
                                        .setTitle("Reservation Information\nfor parking space " + parkingSpace)
                                        .setItems(new String[]{s}, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create();
                                dialog.setCanceledOnTouchOutside(true);
                                dialog.show();
                            }
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