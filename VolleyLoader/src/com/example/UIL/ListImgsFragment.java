package com.example.UIL;



import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.UI.ImageActivity;


public class ListImgsFragment extends Fragment
{
	

	private GridView mGridView;
	private String[] mUrlStrs=null;
	 private ListVolleryAdapter adapterVollery;
	
	public ListImgsFragment(){
		
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mUrlStrs=getArguments().getStringArray("KEY");		
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_list_imgs, container,
				false);
		mGridView = (GridView) view.findViewById(R.id.id_gridview);
		setUpAdapter();
		//gridview监听事件
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				upImagePagerActivity(position);
				String URI=mUrlStrs[position];
				
				upImagePagerActivity(URI,view);
			}
		});
		//在猛的滑动时不加载图片
		//mGridView.setOnScrollListener(new PauseOnScrollListener(mImageLoader, false, true));
		return view;
	}
	
	//调用放大图片事件
	private void upImagePagerActivity(String URI,final View view) {
		// TODO Auto-generated method stub
		Intent i=new Intent(getActivity(),ImageActivity.class);
		i.putExtra("KEY2", URI);
		startActivity(i);
	}

	private void setUpAdapter()
	{
		if (getActivity() == null || mGridView == null)
			return;
		
		//System.out.println("This is Test");
		
		if (mUrlStrs != null)
		{	
			 adapterVollery = new ListVolleryAdapter(mUrlStrs,getActivity());
			 mGridView.setAdapter(adapterVollery);
		} else
		{
			mGridView.setAdapter(null);
		}

	}

}
