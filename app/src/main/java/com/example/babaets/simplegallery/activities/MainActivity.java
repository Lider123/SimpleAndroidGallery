package com.example.babaets.simplegallery.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.babaets.simplegallery.adapters.GalleryAdapter;
import com.example.babaets.simplegallery.models.Image;
import com.example.babaets.simplegallery.R;
import com.example.babaets.simplegallery.network.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private ArrayList<Image> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.gallery_recycler_view);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new GalleryAdapter(this, imageList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    loadMore();
                }
            }

            private void loadMore() {
                addImagesFromNetwork();
            }
        });

        addImagesFromNetwork();
    }

    private void addImagesFromNetwork() {
        Thread networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (NetworkManager.isNetworkDataNull()) {
                        NetworkManager.init();
                        NetworkManager.getDataFromNetwork();
                    }

                    final int oldSize = imageList.size();
                    int batchSize = 8;
                    while (imageList.size() < oldSize + batchSize && imageList.size() < NetworkManager.getNetworkDataLength()) {
                        JSONObject newObject = NetworkManager.getNetworkObject(imageList.size());
                        imageList.add(NetworkManager.imageGson.fromJson(newObject.toString(), Image.class));
                    }
                    Log.d(TAG, String.format("Added %d objects to list", imageList.size() - oldSize));

                    if (imageList.size() > oldSize) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyItemRangeInserted(oldSize, imageList.size() - oldSize);
                            }
                        });
                    }
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
