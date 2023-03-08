package com.devwithkeshav.mywallpapers.Modals;

public class RecentWallpaperModal {
    public String download;
    public String user;
    public String url;
    public RecentWallpaperModal(String url,String user,String download){
        this.url = url;
        this.user = user;
        this.download = download;
    }
}
