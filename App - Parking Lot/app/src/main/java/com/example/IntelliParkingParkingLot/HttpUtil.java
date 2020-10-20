package com.example.IntelliParkingParkingLot;

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

    public static void parkingLotReservationInfo(String parkingLotName, String parkingSpace, okhttp3.Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", parkingLotName);
            json.put("space", parkingSpace);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(json.toString(), HttpUtil.JSON);
        Request request = new Request.Builder()
                .url(HttpUtil.address + "parkingLotReservationInfo")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void changePrice(String parkingLotName, String price, okhttp3.Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("name", parkingLotName);
            json.put("price", price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(json.toString(), HttpUtil.JSON);
        Request request = new Request.Builder()
                .url(HttpUtil.address + "changePrice")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}
