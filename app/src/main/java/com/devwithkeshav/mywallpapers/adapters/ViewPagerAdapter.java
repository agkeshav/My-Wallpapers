package com.devwithkeshav.mywallpapers.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.devwithkeshav.mywallpapers.Fragments.CategoryFragment;
import com.devwithkeshav.mywallpapers.Fragments.MostPopularWallpaperFragment;
import com.devwithkeshav.mywallpapers.Fragments.RandomWallpaperFragment;
import com.devwithkeshav.mywallpapers.Fragments.RecentWallpaperFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new CategoryFragment();
        } else if (position == 1) {
            return new RecentWallpaperFragment();
        } else if (position == 2) {
            return new RandomWallpaperFragment();
        } else {
            return new MostPopularWallpaperFragment();
        }


    }

    @Override
    public int getCount() {
        return 4;
    }

    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "CATEGORIES";
        } else if (position == 1) {
            return "RECENT";
        } else if (position == 2) {
            return "RANDOM";
        } else {
            return "MOST POPULAR";
        }
    }
}
