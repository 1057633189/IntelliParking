package com.example.parkinglot;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static String address = "http://10.0.2.2:8080/";

    public static void postReservationInfo(String[] paras, okhttp3.Callback callback) {
        JSONObject json = new JSONObject();
        try {
            json.put("parking lot name", paras[0]);
            json.put("address", paras[1]);
            json.put("available parking spaces", paras[2]);
            json.put("price", paras[3]);
            json.put("available time", paras[4]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(json.toString(), HttpUtil.JSON);
        Request request = new Request.Builder()
                .url(HttpUtil.address + "postReservationInfo")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

}