package com.example.demo_zhy_18_networkimageloader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.zhy.utils.ImageLoader;
import com.zhy.utils.ImageLoader.Type;
import com.zhy.utils.Images;
/**
 * http://blog.csdn.net/lmj623565791/article/details/41874561
 * @author zhy
 *
 */
public class ListImgsFragment extends Fragment
{
	

	private GridView mGridView;
	//private String[] mUrlStrs = Images.imageThumbUrls;
	private String[] mUrlStrs=null;
	private ImageLoader mImageLoader;
	public ListImgsFragment(String[] mUrlStrs2) {
		// TODO Auto-generated constructor stub
		this.mUrlStrs=mUrlStrs2;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mImageLoader = ImageLoader.getInstance(3, Type.LIFO);
		
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
				upImagePagerActivity(position);
			}
		});
		return view;
	}
	
	//调用放大图片事件
	private void upImagePagerActivity(int position) {
		// TODO Auto-generated method stub
		System.out.println("you click pic"+position);
	}

	private void setUpAdapter()
	{
		if (getActivity() == null || mGridView == null)
			return;
		
		System.out.println("This is Test");
		
		if (mUrlStrs != null)
		{
			for(int i=0;i<mUrlStrs.length;i++)
	 			System.out.println("test"+mUrlStrs[i]);
			
			
			mGridView.setAdapter(new ListImgItemAdaper(getActivity(), 0,
					mUrlStrs));
		} else
		{
			System.out.println("null");
			mGridView.setAdapter(null);
		}

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
			mImageLoader.loadImage(getItem(position), imageview, true);
			return convertView;
		}

	}

}
