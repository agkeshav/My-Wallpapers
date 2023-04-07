package com.devwithkeshav.mywallpapers.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.Modals.WallpaperCategoryModal;
import com.devwithkeshav.mywallpapers.activities.WallpaperCategoryActivity;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    RequestOptions requestOptions ;
    Context context;
    ArrayList<WallpaperCategoryModal> wallpaperCategoryArrayList;


    public CategoryRecyclerViewAdapter(Context context, ArrayList<WallpaperCategoryModal> wallpaperCategoryArrayList) {
        this.context = context;
        this.wallpaperCategoryArrayList = wallpaperCategoryArrayList;
        requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_loop_24)
                .error(R.drawable.ic_baseline_loop_24);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_recycler_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryTextView.setText(wallpaperCategoryArrayList.get(position).text);
        Glide.with(context).asDrawable().load(wallpaperCategoryArrayList.get(position).url).apply(requestOptions).into(
                new CustomTarget<Drawable>() {

                    public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition<? super Drawable> transition) {
                        holder.categoryImageRelativeLayout.setBackground(drawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                }
        );
        holder.categoryImageRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WallpaperCategoryActivity.class);
                intent.putExtra("CategoryName",holder.categoryTextView.getText());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return wallpaperCategoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryTextView;
        RelativeLayout categoryImageRelativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImageRelativeLayout = itemView.findViewById(R.id.categoryImageRelativeLayout);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);

        }
    }


}

