package com.example.picloader;

import java.util.List;

import com.example.utils.LoadTime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

public abstract class AbsSingleFragmentActivity extends FragmentActivity
{
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

	}
	
	


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		List loadTime=LoadTime.allTime;
		long sum=(long)0;
		for(int i=0;i<loadTime.size();i++){
			long time=(Long) loadTime.get(i);
			sum+=time;
		}
		float aver=(float) (sum*1.0/loadTime.size());
		Log.e("average time:",loadTime.size()+":"+aver+"");
	}




	protected abstract Fragment createFragment();

	protected abstract int getLayoutId();

}
