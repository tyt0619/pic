package com.example.volley;

import java.util.Iterator;
import java.util.Map;

import com.example.utils.LoadTime;
import com.example.volley.R;
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
		long sum=0;
		long sum2=0;
		Iterator iter = LoadTime.map.entrySet().iterator();
		Iterator iter2 = LoadTime.map2.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			Long val = (Long) entry.getValue();
			sum+=val;
		}
		while (iter2.hasNext()) {
			Map.Entry entry = (Map.Entry) iter2.next();
			String key = (String) entry.getKey();
			Long val = (Long) entry.getValue();
			sum2+=val;
		}
		Log.e("average1:",LoadTime.map.size()+":"+sum*1.0/LoadTime.map.size()+"");
		Log.e("average2:",LoadTime.map2.size()+":"+sum2*1.0/LoadTime.map2.size()+"");
	}
	


	protected abstract Fragment createFragment();

	protected abstract int getLayoutId();

}
