package com.devwithkeshav.mywallpapers.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.devwithkeshav.mywallpapers.Modals.MostPopularWallpaperModal;
import com.devwithkeshav.mywallpapers.Modals.RandomWallpaperModal;
import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.adapters.MostPopularWallpaperRecyclerViewAdapter;
import com.devwithkeshav.mywallpapers.adapters.RandomWallpaperRecyclerViewAdapter;
import com.devwithkeshav.mywallpapers.constants;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MostPopularWallpaperFragment extends Fragment {

    AdView mostPopularBannerAd;
    RequestQueue requestQueue;
    JSONArray jsonArray;
    RecyclerView mostPopularWallpaperRecyclerView;
    ArrayList<MostPopularWallpaperModal> mostPopularWallpaperArrayList = new ArrayList<>();
    MostPopularWallpaperRecyclerViewAdapter adapter;

    public MostPopularWallpaperFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_most_popular_wallpaper, container, false);
        mostPopularBannerAd = view.findViewById(R.id.mostPopularBannerAd);

        // Step 1
        MobileAds.initialize(view.getContext());
        // Step2
        AdRequest adRequest = new AdRequest.Builder().build();
        // Step3
        mostPopularBannerAd.loadAd(adRequest);


        mostPopularWallpaperRecyclerView = view.findViewById(R.id.mostPopularWallpaperRecyclerView);
        mostPopularWallpaperRecyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        mostPopularWallpaperRecyclerView.setItemViewCacheSize(50);
        mostPopularWallpaperArrayList.clear();
        Random random = new Random();
        int a = 1+random.nextInt(1);
        if(a==0){
            jsonCallpixabay(view);
            jsonCallUnsplash(view);
        }
        else{
            jsonCallUnsplash(view);
            jsonCallpixabay(view);
        }
        adapter = new MostPopularWallpaperRecyclerViewAdapter(view.getContext(), mostPopularWallpaperArrayList);
        mostPopularWallpaperRecyclerView.setAdapter(adapter);

        return view;
    }

    public void jsonCallUnsplash(View view) {

        String url = "https://api.unsplash.com/photos?client_id=" + constants.unSplashApi + "&page=1&per_page=30&orientation=landscape&query=fantasy+fairy+mostpopular+wallpaper&order_by=latest";
        JsonArrayRequest postRequest = new JsonArrayRequest(Request.Method.GET, url, null,

                new Response.Listener<JSONArray>() {

                    public void onResponse(JSONArray jsonArray) {
                        try {


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
                                mostPopularWallpaperArrayList.add(new MostPopularWallpaperModal(imgUrl,name,download));
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

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(postRequest);
    }
    public void jsonCallpixabay(View view) {

        String url = "https://pixabay.com/api/?key=" + constants.pixabayApi + "&image_type=photo&safesearch=true&per_page=200&query=fantasy+fairy&page=1&order=popular";
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
                                mostPopularWallpaperArrayList.add(new MostPopularWallpaperModal(imgUrl,user,download));
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


        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(postRequest);
    }
}