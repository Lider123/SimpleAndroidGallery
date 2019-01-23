package com.example.babaets.simplegallery.network;

import android.util.Log;

import com.example.babaets.simplegallery.models.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkManager {
    private static final String TAG = "NetworkManager";
    private static final String url = "https://jsonplaceholder.typicode.com/photos";

    private static OkHttpClient client;
    private static JSONArray jsonData = null;

    public static Gson imageGson;

    public static void init() {
        client = new OkHttpClient();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Image.class, new ImageJsonDeserializer());
        imageGson = gsonBuilder.create();
    }

    public static void getDataFromNetwork() throws IOException {
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            String jsonString = response.body().string();
            jsonData = new JSONArray(jsonString);
        }
        catch (JSONException e) {
            Log.e(TAG, "Unable to parse JSON from result.");
        }
    }

    public static int getNetworkDataLength() {
        return jsonData == null ? 0 : jsonData.length();
    }

    public static boolean isNetworkDataNull() {
        return jsonData == null;
    }

    public static JSONObject getNetworkObject(int i) throws JSONException {
        return jsonData.getJSONObject(i);
    }
}
