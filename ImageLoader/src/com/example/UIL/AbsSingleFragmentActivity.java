package com.example.UIL;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.utils.LoadTime;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

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
		
		//System.out.println(cacheDir.getAbsolutePath());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		 			.threadPriority(Thread.NORM_PRIORITY - 2)
			        .threadPoolSize(4)
				 	.denyCacheImageMultipleSizesInMemory()
				 	//.discCache(new UnlimitedDiscCache(cacheDir))
				 	//.discCacheFileCount(50)
				 	.discCacheSize(50*1024*1024)//硬盘缓存的大小
			        .discCacheFileNameGenerator(new Md5FileNameGenerator())//保存uri的名称用md5
			        //.discCacheFileNameGenerator(new HashCodeFileNameGenerator())//将保存时的URI用hashcode加密
			        .tasksProcessingOrder(QueueProcessingType.LIFO) 
			        .memoryCache(new LruMemoryCache((int) Runtime.getRuntime().maxMemory()/8))
			        .build();
			        ImageLoader.getInstance().init(config);
			        

	}
	
	


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		long sum=0;
		Iterator iter = LoadTime.map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			Long val = (Long) entry.getValue();
			sum+=val;
		}
		Log.e("average:",LoadTime.map.size()+":"+sum*1.0/LoadTime.map.size()+"");
		
	}




	protected abstract Fragment createFragment();

	protected abstract int getLayoutId();

}
