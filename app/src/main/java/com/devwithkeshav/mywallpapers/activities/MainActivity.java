package com.devwithkeshav.mywallpapers.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.devwithkeshav.mywallpapers.R;
import com.devwithkeshav.mywallpapers.adapters.ViewPagerAdapter;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "My Channel";
    private static final int NOTIFICATION_ID = 200;
    private static final int REQ_CODE = 100;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    MaterialToolbar toolbar;
    TabLayout tab;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toggle.syncState();
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id==R.id.privacyPolicy){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://agkeshav.github.io/")));
                }
                else if(id==R.id.rateus){
                    try{
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id="+getPackageName())));
                    }
                    catch (ActivityNotFoundException e){
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                    }
                }
                else{
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Wallpapers ");
                        String shareMessage= "Hey check out this app at:\n";
                        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getPackageName() ;
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "Share via"));
                    } catch(Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                        //e.toString();
                    }
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


//        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.app_icon, null);
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//        Bitmap largeIcon = bitmapDrawable.getBitmap();
//        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        Notification notification;
//        Intent iNotify = new Intent(getApplicationContext(), MainActivity.class);
//        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pi = PendingIntent.getActivity(this, REQ_CODE, iNotify, PendingIntent.FLAG_UPDATE_CURRENT);
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            notification = new Notification.Builder(this).setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.app_icon)
//                    .setContentText("Check Out our new Wallpapers")
//                    .setContentTitle("My Wallpapers")
//                    .setChannelId(CHANNEL_ID)
//                    .setContentIntent(pi)
//                    .build();
//            nm.createNotificationChannel(new NotificationChannel(CHANNEL_ID,"New Channel",NotificationManager.IMPORTANCE_HIGH));
//        }
//        else {
//            notification = new Notification.Builder(this).setLargeIcon(largeIcon)
//                    .setSmallIcon(R.drawable.app_icon)
//                    .setContentText("Check Out our new Wallpapers")
//                    .setContentTitle("My Wallpapers")
//                    .build();
//
//        }
//        nm.notify(NOTIFICATION_ID,notification);

    }
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }
}