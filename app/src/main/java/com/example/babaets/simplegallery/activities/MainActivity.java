package com.example.babaets.simplegallery.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.babaets.simplegallery.adapters.GalleryAdapter;
import com.example.babaets.simplegallery.models.Image;
import com.example.babaets.simplegallery.R;
import com.example.babaets.simplegallery.viewmodels.ImagesViewModel;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.gallery_recycler_view);
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        final ImagesViewModel viewModel = ViewModelProviders.of(this).get(ImagesViewModel.class);
        viewModel.getImages().observe(this, new Observer<List<Image>>() {
            @Override
            public void onChanged(@Nullable List<Image> imageList) {
                if (mAdapter == null) {
                    mAdapter = new GalleryAdapter(MainActivity.this, imageList);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                    mAdapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    loadMore();
                }
            }

            private void loadMore() {
                viewModel.loadImages();
            }
        });
    }
}
