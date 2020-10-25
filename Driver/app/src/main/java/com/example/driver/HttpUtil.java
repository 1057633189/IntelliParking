package com.example.driver;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String address = "http://10.0.2.2:8080/";

    public static void getReservationInfo(okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(HttpUtil.address + "getReservationInfo")
                .get()
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void reservation(String[] paras, okhttp3.Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("driver name", paras[0]);
            json.put("phone number", paras[1]);
            json.put("car number", paras[2]);
            json.put("booking time", paras[3]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(json.toString(), HttpUtil.JSON);
        Request request = new Request.Builder()
                .url(HttpUtil.address + "reservation")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}