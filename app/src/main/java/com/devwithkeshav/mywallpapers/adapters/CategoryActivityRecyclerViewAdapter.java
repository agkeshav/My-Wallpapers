package com.devwithkeshav.mywallpapers.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.devwithkeshav.mywallpapers.Modals.CategoryActivityWallpaperModal;
import com.devwithkeshav.mywallpapers.Modals.RandomWallpaperModal;
import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.activities.WallpaperSettingActivity;

import java.util.ArrayList;

public class CategoryActivityRecyclerViewAdapter extends RecyclerView.Adapter<CategoryActivityRecyclerViewAdapter.ViewHolder> {

    RequestOptions requestOptions;
    Context context;
    ArrayList<CategoryActivityWallpaperModal> categoryActivityWallpaperArrayList;
    ImageView categoryActivityRecyclerViewImageView;
    RecyclerView categoryActivityRecyclerView;



    public CategoryActivityRecyclerViewAdapter(Context context, ArrayList<CategoryActivityWallpaperModal> categoryActivityWallpaperArrayList) {


        this.context = context;
        this.categoryActivityWallpaperArrayList = categoryActivityWallpaperArrayList;
        requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.progress_animation)
                .dontAnimate()
                .error(R.drawable.ic_baseline_loop_24);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.categoryactivityrecyclerviewlayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(categoryActivityWallpaperArrayList.get(position).url).thumbnail(0.5f).apply(requestOptions).into(categoryActivityRecyclerViewImageView);
        categoryActivityRecyclerViewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WallpaperSettingActivity.class);
                intent.putExtra("imgUrl",categoryActivityWallpaperArrayList.get(position).url);
                intent.putExtra("user",categoryActivityWallpaperArrayList.get(position).user);
                intent.putExtra("download",categoryActivityWallpaperArrayList.get(position).download);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryActivityWallpaperArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryActivityRecyclerView = itemView.findViewById(R.id.categoryActivityRecyclerView);
            categoryActivityRecyclerViewImageView = itemView.findViewById(R.id.categoryActivityRecyclerViewImageView);



        }
    }
}
