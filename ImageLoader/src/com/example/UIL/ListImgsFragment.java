package com.example.UIL;



import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.UI.ImageActivity;
import com.example.utils.LoadTime;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;


public class ListImgsFragment extends Fragment
{
	

	private GridView mGridView;
	private String[] mUrlStrs=null;
	private ImageLoader mImageLoader=ImageLoader.getInstance();
	DisplayImageOptions options;
	//ImageLoader imageLoader;
	
	public ListImgsFragment(){
		
	}
	

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mUrlStrs=getArguments().getStringArray("KEY");
		//mImageLoader = ImageLoader.getInstance();
		
		
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.bitmapConfig(Bitmap.Config.RGB_565)	 //设置图片的解码类型
		.build();
		
		
		
		
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
				String URI=(String) view.getTag();
				
				upImagePagerActivity(URI,view);
			}
		});
		//在猛的滑动时不加载图片
		mGridView.setOnScrollListener(new PauseOnScrollListener(mImageLoader, false, true));
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
//			for(int i=0;i<mUrlStrs.length;i++)
//	 			System.out.println("test"+mUrlStrs[i]);
			
			
			mGridView.setAdapter(new ListImgItemAdaper(getActivity(), 0,
					mUrlStrs));
		} else
		{
			//System.out.println("null");
			mGridView.setAdapter(null);
		}

	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mImageLoader.clearMemoryCache();
		mImageLoader.clearDiscCache();
	}


	private class ListImgItemAdaper extends ArrayAdapter<String>
	{

		public ListImgItemAdaper(Context context, int resource, String[] datas)
		{
			super(getActivity(), 0, datas);
			Log.e("TAG", "ListImgItemAdaper");
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.item_fragment_list_imgs, parent, false);
			}
			ImageView imageview = (ImageView) convertView
					.findViewById(R.id.id_img);
			imageview.setImageResource(R.drawable.pictures_no);
			
			
			//mImageLoader.loadImage(getItem(position), imageview, true);
			
			//mImageLoader..getInstance().displayImage(picUrl, imageView001,new ImageLoaderPicture(this).getOptions(),new SimpleImageLoadingListener());
			mImageLoader.displayImage(mUrlStrs[position], imageview, options,new ImageLoadingListener(){

				long start_time,finish_time;
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					// TODO Auto-generated method stub
					start_time=System.currentTimeMillis();
					Log.e("start_time",imageUri+":"+start_time);
					
				}

				@Override
				public void onLoadingFailed(String imageUri, View view,
						FailReason failReason) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					// TODO Auto-generated method stub
					finish_time=System.currentTimeMillis();
					if(LoadTime.map.get(imageUri) == null){
						LoadTime.map.put(imageUri, finish_time-start_time);
						int loadtime=(int) (finish_time-start_time);
						Log.e("loadtime",imageUri+":"+loadtime+"-"+start_time+"-"+finish_time);
					}
					

				}

				@Override
				public void onLoadingCancelled(String imageUri, View view) {
					// TODO Auto-generated method stub
					
				}
				
			});
			convertView.setTag(getItem(position));
			Log.i("URI", getItem(position));
			return convertView;
		}

	}

}
