package com.example.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class BitmapCache implements ImageCache{
    private LruCache<String, Bitmap> mCache;
    @SuppressLint("NewApi")
    public BitmapCache() {
        int maxSize = 10 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }
            
        };
    }
    
    @SuppressLint("NewApi")
    @Override
    public Bitmap getBitmap(String url) { // 存到本地
        return mCache.get(url);
    }

    @SuppressLint("NewApi")
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

}
