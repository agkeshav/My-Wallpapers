package com.devwithkeshav.mywallpapers.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.devwithkeshav.mywallpapers.Fragments.RandomWallpaperFragment;
import com.devwithkeshav.mywallpapers.Modals.RandomWallpaperModal;
import com.devwithkeshav.mywallpapers.Modals.WallpaperCategoryModal;
import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.activities.WallpaperSettingActivity;

import java.util.ArrayList;

public class RandomWallpaperRecyclerViewAdapter extends RecyclerView.Adapter<RandomWallpaperRecyclerViewAdapter.ViewHolder> {

    RequestOptions requestOptions;
    Context context;
    ArrayList<RandomWallpaperModal> randomWallpaperArrayList;
    ImageView randomWallpaperRecyclerViewImageView;
    RecyclerView randomWallpaperRecyclerView;

    public RandomWallpaperRecyclerViewAdapter(Context context, ArrayList<RandomWallpaperModal> randomWallpaperArrayList) {
        this.context = context;
        this.randomWallpaperArrayList = randomWallpaperArrayList;
        requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.progress_animation)
                .dontAnimate()
                .error(R.drawable.ic_baseline_loop_24);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.random_wallpaper_recycler_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            Glide.with(context).asDrawable().load(randomWallpaperArrayList.get(position).url).thumbnail(0.5f).apply(requestOptions).into(randomWallpaperRecyclerViewImageView);

            randomWallpaperRecyclerViewImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WallpaperSettingActivity.class);
                    intent.putExtra("imgUrl",randomWallpaperArrayList.get(position).url);
                    intent.putExtra("user",randomWallpaperArrayList.get(position).user);
                    intent.putExtra("download",randomWallpaperArrayList.get(position).download);
                    context.startActivity(intent);
                }
            });



    }

    @Override
    public int getItemCount() {
        return randomWallpaperArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            randomWallpaperRecyclerView = itemView.findViewById(R.id.randomWallpaperRecyclerView);
            randomWallpaperRecyclerViewImageView = itemView.findViewById(R.id.randomWallpaperRecyclerViewImageView);


        }
    }
}
