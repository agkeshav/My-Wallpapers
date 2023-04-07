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
import com.bumptech.glide.signature.ObjectKey;
import com.devwithkeshav.mywallpapers.Modals.RandomWallpaperModal;
import com.devwithkeshav.mywallpapers.Modals.RecentWallpaperModal;
import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.activities.WallpaperSettingActivity;

import java.util.ArrayList;

public class RecentWallpaperRecyclerViewAdapter extends RecyclerView.Adapter<RecentWallpaperRecyclerViewAdapter.ViewHolder> {

    RequestOptions requestOptions;
    Context context;
    ArrayList<RecentWallpaperModal> recentWallpaperArrayList;
    ImageView recentWallpaperRecyclerViewImageView;
    RecyclerView recentWallpaperRecyclerView;


    public RecentWallpaperRecyclerViewAdapter(Context context, ArrayList<RecentWallpaperModal> recentWallpaperArrayList) {
        this.context = context;
        this.recentWallpaperArrayList = recentWallpaperArrayList;
        requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.progress_animation)
                .dontAnimate()
                .error(R.drawable.ic_baseline_loop_24);
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_wallpaper_recycler_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).asDrawable().load(recentWallpaperArrayList.get(position).url).thumbnail(0.5f).apply(requestOptions).into(recentWallpaperRecyclerViewImageView);
        recentWallpaperRecyclerViewImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WallpaperSettingActivity.class);
                intent.putExtra("imgUrl",recentWallpaperArrayList.get(position).url);
                intent.putExtra("user",recentWallpaperArrayList.get(position).user);
                intent.putExtra("download",recentWallpaperArrayList.get(position).download);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return recentWallpaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recentWallpaperRecyclerView = itemView.findViewById(R.id.recentWallpaperRecyclerView);
            recentWallpaperRecyclerViewImageView = itemView.findViewById(R.id.recentWallpaperRecyclerViewImageView);


        }
    }
}
