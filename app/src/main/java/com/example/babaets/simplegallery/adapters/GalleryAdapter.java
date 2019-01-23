package com.example.babaets.simplegallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.babaets.simplegallery.R;
import com.example.babaets.simplegallery.activities.ImageActivity;
import com.example.babaets.simplegallery.models.Image;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    private Context mContext;
    private ArrayList<Image> dataset;

    public GalleryAdapter(Context context, ArrayList<Image> dataset) {
        this.mContext = context;
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder galleryViewHolder, int i) {
        final Image image = dataset.get(i);
        Picasso.with(mContext).load(image.getUrl().toString()).fit().centerCrop().into(galleryViewHolder.mImageView);
        galleryViewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageActivity.class);
                intent.putExtra(GalleryViewHolder.KEY_IMAGE_URL, image.getUrl().toString());
                intent.putExtra(GalleryViewHolder.KEY_IMAGE_TITLE, image.getTitle());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        private static final String KEY_IMAGE_URL = "url";
        private static final String KEY_IMAGE_TITLE = "title";

        private ImageView mImageView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_image_view);
        }
    }
}
