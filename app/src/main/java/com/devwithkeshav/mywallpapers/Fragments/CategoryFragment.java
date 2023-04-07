package com.devwithkeshav.mywallpapers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devwithkeshav.mywallpapers.Modals.WallpaperCategoryModal;
import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.adapters.CategoryRecyclerViewAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CategoryFragment extends Fragment {


    RequestQueue requestQueue;
    JSONArray jsonArray;
    AdView categoryBannerAd;
    RecyclerView categoryRecyclerView;
    CategoryRecyclerViewAdapter adapter;
    ArrayList<WallpaperCategoryModal> wallpaperCategoryArrayList = new ArrayList<>();

    public CategoryFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        categoryBannerAd = view.findViewById(R.id.categoryBannerAd);
        categoryRecyclerView = view.findViewById(R.id.categoryRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        wallpaperCategoryArrayList.clear();
        wallpaperCategoryItemAdd();
        adapter = new CategoryRecyclerViewAdapter(view.getContext(), wallpaperCategoryArrayList);
        categoryRecyclerView.setAdapter(adapter);
        // Step 1
        MobileAds.initialize(view.getContext());
        // Step2
        AdRequest adRequest = new AdRequest.Builder().build();
        // Step3
        categoryBannerAd.loadAd(adRequest);
        return view;
    }



    public void wallpaperCategoryItemAdd(){
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/2156881/pexels-photo-2156881.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Abstract"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2019/02/18/16/59/animal-4004844_960_720.jpg", "Animals"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2018/02/04/09/09/brushes-3129361_960_720.jpg", "Artistic"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2011/12/15/11/37/galaxy-11188_960_720.jpg", "Astronomy"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2015/12/01/20/28/road-1072821_960_720.jpg", "Autumn"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2017/03/27/13/51/children-2178857_960_720.jpg", "Babies & Kids"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2012/06/19/10/32/owl-50267_960_720.jpg", "Birds"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2016/02/05/14/00/aurora-borealis-1181004_960_720.jpg", "Blue & Purple"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/240225/pexels-photo-240225.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Bokeh"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/177809/pexels-photo-177809.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Cats"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2017/01/18/16/46/hong-kong-1990268_960_720.jpg", "City & Buildings"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2016/11/29/09/32/auto-1868726_960_720.jpg", "Classic"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/1566909/pexels-photo-1566909.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Colorful"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2015/12/04/14/05/code-1076536_960_720.jpg", "Coding"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/333083/pexels-photo-333083.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Dogs"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/248159/pexels-photo-248159.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Dusk"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2018/08/14/13/23/ocean-3605547_960_720.jpg", "Fantasy"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2017/11/23/03/17/christmas-2971961_960_720.jpg", "Festivals"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/87452/flowers-background-butterflies-beautiful-87452.jpeg?auto=compress&cs=tinysrgb&w=600", "Flowers"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/370984/pexels-photo-370984.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Food & Drinks"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2017/10/05/06/46/asia-2818566_960_720.jpg", "Girly"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/409696/pexels-photo-409696.jpeg?auto=compress&cs=tinysrgb&w=600", "Green"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2018/09/24/13/54/mothers-day-3699995_960_720.jpg", "Love & Hearts"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2013/10/02/23/03/mountains-190055_960_720.jpg", "Mountains"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2015/05/07/11/02/guitar-756326_960_720.jpg", "Music"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2015/06/19/21/24/avenue-815297_960_720.jpg", "Nature"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/414144/pexels-photo-414144.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Night"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2016/10/18/21/22/beach-1751455_960_720.jpg", "Ocean"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2017/04/21/17/44/pink-2249403_960_720.jpg", "Pink"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2018/05/16/09/12/dreams-3405257_960_720.jpg", "Quotes"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2018/07/27/05/02/stones-3565221_960_720.jpg", "Randoms"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/247195/pexels-photo-247195.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Sadness"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/46164/field-of-rapeseeds-oilseed-rape-blutenmeer-yellow-46164.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Spring"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/1237119/pexels-photo-1237119.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Sunshine"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2018/01/31/12/16/architecture-3121009_960_720.jpg", "Travel"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2015/09/26/21/33/suspension-bridge-959853_960_720.jpg", "Trees & Leaves"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://images.pexels.com/photos/210019/pexels-photo-210019.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "Vehicles"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2018/02/24/20/39/clock-3179167_960_720.jpg", "Vintage"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2016/04/20/19/47/wolves-1341881_960_720.jpg", "Winter & Snow"));
        wallpaperCategoryArrayList.add(new WallpaperCategoryModal("https://cdn.pixabay.com/photo/2015/04/14/09/48/sky-721912_960_720.jpg", "Yellow & Orange"));

    }

}