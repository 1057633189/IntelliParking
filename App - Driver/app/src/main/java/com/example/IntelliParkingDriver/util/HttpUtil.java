package com.example.IntelliParkingDriver.util;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String address = "http://10.0.2.2:8080/";

    public static void parkingLotInfo(String parkingLotName, okhttp3.Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", parkingLotName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(json.toString(), HttpUtil.JSON);
        Request request = new Request.Builder()
                .url(HttpUtil.address + "parkingLotInfo")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void parkingReservation(String parkingLotName, String parkingSpace, String time, okhttp3.Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", parkingLotName);
            json.put("space", parkingSpace);
            json.put("time", time);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(json.toString(), HttpUtil.JSON);
        Request request = new Request.Builder()
                .url(HttpUtil.address + "parkingReservation")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}