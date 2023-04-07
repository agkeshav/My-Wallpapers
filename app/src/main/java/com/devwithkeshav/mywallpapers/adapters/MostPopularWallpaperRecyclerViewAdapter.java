package com.devwithkeshav.mywallpapers.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.devwithkeshav.mywallpapers.Modals.MostPopularWallpaperModal;
import com.devwithkeshav.mywallpapers.Modals.RandomWallpaperModal;
import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.activities.WallpaperSettingActivity;

import java.util.ArrayList;

public class MostPopularWallpaperRecyclerViewAdapter extends RecyclerView.Adapter<MostPopularWallpaperRecyclerViewAdapter.ViewHolder>{
    RequestOptions requestOptions;
    Context context;
    ArrayList<MostPopularWallpaperModal> mostPopularWallpaperArrayList;
    ImageView mostPopularWallpaperRecyclerViewImageView;
    RecyclerView mostPopularWallpaperRecyclerView;

    public MostPopularWallpaperRecyclerViewAdapter(Context context, ArrayList<MostPopularWallpaperModal> mostPopularWallpaperArrayList) {
        this.context = context;
        this.mostPopularWallpaperArrayList = mostPopularWallpaperArrayList;
        requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.progress_animation)
                .dontAnimate()
                .error(R.drawable.ic_baseline_loop_24);
    }
    @Override
    public MostPopularWallpaperRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.most_popular_wallpaper_recycler_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MostPopularWallpaperRecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(context).asDrawable().load(mostPopularWallpaperArrayList.get(position).url).thumbnail(0.5f).apply(requestOptions).into(mostPopularWallpaperRecyclerViewImageView);
        mostPopularWallpaperRecyclerViewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WallpaperSettingActivity.class);

                intent.putExtra("imgUrl",mostPopularWallpaperArrayList.get(position).url);
                intent.putExtra("user",mostPopularWallpaperArrayList.get(position).user);
                intent.putExtra("download",mostPopularWallpaperArrayList.get(position).download);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mostPopularWallpaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             mostPopularWallpaperRecyclerView= itemView.findViewById(R.id.mostPopularWallpaperRecyclerView);
            mostPopularWallpaperRecyclerViewImageView = itemView.findViewById(R.id.mostPopularWallpaperRecyclerViewImageView);


        }
    }
}
