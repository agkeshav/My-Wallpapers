package com.devwithkeshav.mywallpapers.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.devwithkeshav.mywallpapers.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.Set;

public class WallpaperSettingActivity extends AppCompatActivity {
    RequestOptions requestOptions;
    ImageView wallpaperSettingImageView;
    BottomNavigationView bottomNavigationView;
    AppCompatButton setHome,setLock,setBoth;
    AppCompatButton wallpaperCreditBtn;
    TextView userTextView, downloadTextView,siteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_setting);
        wallpaperCreditBtn = findViewById(R.id.wallpaperCreditBtn);
        requestOptions = new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.progress_animation)
                .dontAnimate().error(R.drawable.ic_baseline_loop_24);
        Intent intent = getIntent();
        wallpaperSettingImageView = findViewById(R.id.wallpaperSettingImageView);
        String imgUrl = intent.getStringExtra("imgUrl");
        String user = intent.getStringExtra("user");
        String download = intent.getStringExtra("download");
        Glide.with(WallpaperSettingActivity.this).asDrawable().load(imgUrl).apply(requestOptions).into(wallpaperSettingImageView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
               if (id == R.id.downloadBtn) {
                   try {
                       downloadWallpaper(WallpaperSettingActivity.this);
                       Toast.makeText(WallpaperSettingActivity.this, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
                   } catch (FileNotFoundException e) {
                       Toast.makeText(WallpaperSettingActivity.this, "Some Error occurred", Toast.LENGTH_SHORT).show();
                       throw new RuntimeException(e);
                   }
               } else if (id == R.id.setWallpaperBtn) {
                    setWallpaper();
                } else {
                  shareWallpaper();
                }

                return true;
            }
        });
        wallpaperCreditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog creditdialog = new Dialog(WallpaperSettingActivity.this);
                creditdialog.setContentView(R.layout.wallpaper_credit_dialog_box);
                userTextView = creditdialog.findViewById(R.id.userTextView);
                downloadTextView = creditdialog.findViewById(R.id.downloadTextView);
                siteTextView = creditdialog.findViewById(R.id.siteTextView);
                userTextView.setText(user);
                downloadTextView.setText(download);

                if(imgUrl.contains("pixabay")){
                    siteTextView.setText("https://pixabay.com/");
                }
                else if(imgUrl.contains("unsplash")){
                    siteTextView.setText("https://unsplash.com/");
                }
                creditdialog.show();
            }


        });

    }

    public void shareWallpaper() {

//write this code in your share button or function

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        BitmapDrawable bitmapDrawable = (BitmapDrawable) wallpaperSettingImageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();


        File f =  new File(getExternalCacheDir()+"/"+getResources().getString(R.string.app_name)+".jpg");
        Intent shareIntent;


        try {
            FileOutputStream outputStream = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

            outputStream.flush();
            outputStream.close();
            shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("*/*");
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
            shareIntent.putExtra(Intent.EXTRA_TEXT,"Hello! Download app from Play Store for many other amazing Wallpapers. visit:" + "https://play.google.com/store/apps/details?id=" +getPackageName());
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        }catch (Exception e){
            throw new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareIntent,"Share Picture"));
    }


    public void setWallpaper(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) wallpaperSettingImageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.set_wallpaper_dialog_box);
        setHome = dialog.findViewById(R.id.setHome);
        setLock = dialog.findViewById(R.id.setLock);
        setBoth = dialog.findViewById(R.id.setBoth);
        setHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wm.setBitmap(bitmap,null,true,WallpaperManager.FLAG_SYSTEM);//For Home screen
                        Toast.makeText(WallpaperSettingActivity.this, "Home Screen Wallpaper Set", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(WallpaperSettingActivity.this, "Home screen wallpaper not supported",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(WallpaperSettingActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        setLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wm.setBitmap(bitmap,null,true,WallpaperManager.FLAG_LOCK);//For Lock screen
                        Toast.makeText(WallpaperSettingActivity.this, "Lock Screen Wallpaper Set", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(WallpaperSettingActivity.this, "Lock screen wallpaper not supported",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(WallpaperSettingActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        setBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WallpaperManager wm = WallpaperManager.getInstance(getApplicationContext());
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wm.setBitmap(bitmap,null,true,WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK);//For Home screen
                        Toast.makeText(WallpaperSettingActivity.this, "Home and Lock Screen Wallpaper Set", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(WallpaperSettingActivity.this, "Home and Lock screen walpaper not supported",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(WallpaperSettingActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    public void downloadWallpaper(Context context) throws FileNotFoundException {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) wallpaperSettingImageView.getDrawable();
       Bitmap bitmap = bitmapDrawable.getBitmap();
//
//        FileOutputStream outputStream = null;
//        File file = Environment.getExternalStorageDirectory();
//        File dir = new File(file.getAbsolutePath() + "/MyWallpapers");
//        dir.mkdir();
//        String fileName = String.format("%d.png", System.currentTimeMillis());
//        File outFile = new File(dir, fileName);
//        try {
//            outputStream = new FileOutputStream(outFile);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, "Connection Failed", Toast.LENGTH_SHORT).show();
//        }
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
//        try {
//            outputStream.flush();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, "Connection Failed", Toast.LENGTH_SHORT).show();
//        }
//        try {
//            outputStream.close();
//            Toast.makeText(context, "Downloaded Successfully", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(context, "Connection Failed", Toast.LENGTH_SHORT).show();
//        }
//
//        scanFile(context, Uri.fromFile(outFile));


        String root = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        Random generator = new Random();

        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
            //     Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
// Tell the media scanner about the new file so that it is
// immediately available to the user.
        MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
    }



    public void scanFile(Context context, Uri imageUri) { //To refresh the gallery
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(imageUri);
        context.sendBroadcast(scanIntent);

    }


}

