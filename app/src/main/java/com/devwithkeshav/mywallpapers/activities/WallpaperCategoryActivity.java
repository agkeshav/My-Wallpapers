package com.devwithkeshav.mywallpapers.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.devwithkeshav.mywallpapers.Modals.CategoryActivityWallpaperModal;
import com.devwithkeshav.mywallpapers.Modals.RandomWallpaperModal;
import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.adapters.CategoryActivityRecyclerViewAdapter;
import com.devwithkeshav.mywallpapers.adapters.RandomWallpaperRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.devwithkeshav.mywallpapers.constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WallpaperCategoryActivity extends AppCompatActivity {
    AdView categoryActivityBannerAd;
    RequestQueue requestQueue;
    JSONArray jsonArray;
    Toolbar categoryToolbar;
    RecyclerView categoryActivityRecyclerView;
    CategoryActivityRecyclerViewAdapter adapter;
    ArrayList<CategoryActivityWallpaperModal> categoryActivityWallpaperArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_category);

        categoryActivityBannerAd = findViewById(R.id.categoryActivityBannerAd);
        // Step 1
        MobileAds.initialize(this);
        // Step2
        AdRequest adRequest = new AdRequest.Builder().build();
        // Step3
        categoryActivityBannerAd.loadAd(adRequest);


        categoryActivityRecyclerView = findViewById(R.id.categoryActivityRecyclerView);
        categoryToolbar = findViewById(R.id.categoryToolbar);
        String category = getIntent().getStringExtra("CategoryName");
        categoryToolbar.setTitle(category);
        categoryActivityRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        categoryActivityRecyclerView.setItemViewCacheSize(50);
        categoryActivityWallpaperArrayList.clear();
        Random random = new Random();
        int a = 1+random.nextInt(1);
        if(a==0){
            jsonCallpixabay(category);
            jsonCallUnsplash(category);
        }
        else{
            jsonCallUnsplash(category);
            jsonCallpixabay(category);
        }
        adapter = new CategoryActivityRecyclerViewAdapter(this, categoryActivityWallpaperArrayList);
        categoryActivityRecyclerView.setAdapter(adapter);
    }

    public void jsonCallUnsplash(String category) {

        String url = "https://api.unsplash.com/search/photos?client_id=" + constants.unSplashApi + "&page=1&per_page=30&orientation=landscape&query="+category;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject jsonObject) {
                        try {

                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String download = obj.getString("likes");
                                JSONObject user = obj.getJSONObject("user");
                                String name = user.getString("name");
                                Log.i("Download",download);
                                Log.i("Name",name);
                                JSONObject url = obj.getJSONObject("urls");
                                String imgUrl = url.getString("regular");
                                Log.i("URL", imgUrl);
                                categoryActivityWallpaperArrayList.add(new CategoryActivityWallpaperModal(imgUrl,name,download));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        error.printStackTrace();
                        Log.d("Error.Response", error.toString());
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                headerMap.put("Content-Type", "application/json");
                headerMap.put("Authorization", constants.unSplashApi);
                return headerMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
    public void jsonCallpixabay(String category) {

        String url = "https://pixabay.com/api/?key=" + constants.pixabayApi + "&image_type=photo&safesearch=true&per_page=200&q="+category;
        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {

                    public void onResponse(JSONObject jsonObject) {
                        try {

                            JSONArray jsonArray = jsonObject.getJSONArray("hits");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String imgUrl = obj.getString("webformatURL");
                                Log.i("URL", imgUrl);
                                String download = obj.getString("downloads");
                                Log.i("download", download);
                                String user = obj.getString("user");
                                Log.i("user", user);
                                categoryActivityWallpaperArrayList.add(new CategoryActivityWallpaperModal(imgUrl,user,download));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        error.printStackTrace();
                        Log.d("Error.Response", error.toString());
                    }
                }
        );


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(postRequest);
    }
}
