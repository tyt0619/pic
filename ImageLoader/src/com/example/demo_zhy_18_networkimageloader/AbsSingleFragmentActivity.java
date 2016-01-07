package com.example.demo_zhy_18_networkimageloader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.demo_zhy_18_networkimageloader.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public abstract class AbsSingleFragmentActivity extends FragmentActivity
{
	
	 private DisplayImageOptions options;
	 protected ImageLoader imageLoader;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());

		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.id_fragmentContainer);

		if (fragment == null)
		{
			fragment = createFragment();
			fm.beginTransaction().add(R.id.id_fragmentContainer, fragment)
					.commit();
		}
		
		
		 ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY - 2)
			        .denyCacheImageMultipleSizesInMemory()
			        .discCacheFileNameGenerator(new Md5FileNameGenerator())
			        .tasksProcessingOrder(QueueProcessingType.LIFO) 
			        .memoryCache(new WeakMemoryCache())                                 
			        .build();
			        ImageLoader.getInstance().init(config);
			         
			        options = new DisplayImageOptions.Builder()
			        .showStubImage(0)
			        .showImageForEmptyUri(0)
			        .showImageOnFail(0)
			        .cacheInMemory(true)
			        .cacheOnDisc(true)
			        .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
			        .bitmapConfig(android.graphics.Bitmap.Config.RGB_565)
			        .displayer(new SimpleBitmapDisplayer())
			        .build();
		  ImageLoader.getInstance().init(config);
			        

	}
	
	


	protected abstract Fragment createFragment();

	protected abstract int getLayoutId();

}
