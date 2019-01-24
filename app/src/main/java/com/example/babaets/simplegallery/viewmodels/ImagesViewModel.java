package com.example.babaets.simplegallery.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.babaets.simplegallery.models.Image;
import com.example.babaets.simplegallery.network.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImagesViewModel extends ViewModel {
    private static final String TAG = "ImagesViewModel";
    private MutableLiveData<List<Image>> imageList;

    public LiveData<List<Image>> getImages() {
        if (imageList == null) {
            List<Image> actualData = new ArrayList<>();
            imageList = new MutableLiveData<>();
            imageList.postValue(actualData);
            loadImages();
        }
        return imageList;
    }

    public void loadImages() {
        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (NetworkManager.isNetworkDataNull()) {
                        NetworkManager.init();
                        NetworkManager.getDataFromNetwork();
                    }

                    final List<Image> actualData;
                    if (imageList.getValue() == null)
                        actualData = new ArrayList<>();
                    else
                        actualData = imageList.getValue();
                    final int oldSize = actualData.size();
                    int batchSize = 8;
                    while (actualData.size() < oldSize + batchSize && actualData.size() < NetworkManager.getNetworkDataLength()) {
                        JSONObject newObject = NetworkManager.getNetworkObject(actualData.size());
                        actualData.add(NetworkManager.imageGson.fromJson(newObject.toString(), Image.class));
                    }
                    Log.d(TAG, String.format("Added %d objects to list", actualData.size() - oldSize));
                    imageList.postValue(actualData);
                }
                catch (JSONException e) {
                    Log.e(TAG, "Unable to handle JSON");
                    e.printStackTrace();
                }
                catch (IOException e) {
                    Log.e(TAG, "Unable to get data from network");
                    e.printStackTrace();
                }
            }
        });
        networkThread.start();
    }
}
