package com.cooksdev.photogallery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cooksdev.photogallery.R;
import com.cooksdev.photogallery.model.Photo;
import com.cooksdev.photogallery.model.Wall;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by roma on 17.08.16.
 */
public class WallPhotosAdapter extends RecyclerView.Adapter<WallPhotosAdapter.PhotoViewHolder> {

    private Context context;
    private List<Photo> data = new ArrayList<>();

    private Wall wall;

    public void setWall(Wall wall){
        this.wall = wall;
        this.notifyDataSetChanged();
    }

    public void updateWall(Wall newWall){
        this.data.addAll(newWall.getPhotos());
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_wall_photo_item, parent, false);
        PhotoViewHolder albumVH = new PhotoViewHolder(view);
        return albumVH;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo = data.get(position);
        holder.bindPhotoImage(photo.getUrl());
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo_item)
        AppCompatImageView ivPhotoItem;
        @BindView(R.id.pw_photo_item)
        ProgressWheel pwPhotoItem;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindPhotoImage(String iconUrl) {
            pwPhotoItem.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(iconUrl)
                    .centerCrop()
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            if (target.getRequest().isFailed()) {
                                target.getRequest().begin();
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pwPhotoItem.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivPhotoItem);
        }

    }
}
