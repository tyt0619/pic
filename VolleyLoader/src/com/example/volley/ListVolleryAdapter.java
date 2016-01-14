package com.example.volley;


import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.volley.R;
import com.example.utils.BitmapCache;
import com.example.utils.ImageSizeUtil;
import com.example.utils.LoadTime;
import com.example.utils.ImageSizeUtil.ImageSize;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ListVolleryAdapter extends BaseAdapter implements OnScrollListener{
    Context mContext = null;
    String[] mList = null;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    GridView mGridView;
    
    
    public ListVolleryAdapter(String[] list , Context context,GridView mGridView){
        mList = list;
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
        this.mGridView=mGridView;
        mGridView.setOnScrollListener(this);
        
        mImageLoader = new ImageLoader(mRequestQueue,  new BitmapCache());
    }
    @Override
    public int getCount() {
        return mList.length;
    }

    @Override
    public Object getItem(int arg0) {
        return mList[arg0];
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }
    
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ImageView imageview=null;
        System.out.println(position+":This is test");
        if (null == view){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_list_imgs,parent,false);
        }
        imageview = (ImageView) view.findViewById(R.id.id_img);
        imageview.setTag(mList[position]);
        return view;
    }
    
    private int mFirstVisibleItem; // 第一张可见图片的下标
    private int mVisibleItemCount; // 一屏有多少张图片可见
    private boolean isFirstEnter = true; // 记录是否刚打开程序，用于解决进入程序不滚动屏幕，不会下载图片的问题。
    private List<ImageContainer> icList = new ArrayList<ImageContainer>();
    

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	    mFirstVisibleItem = firstVisibleItem;
	    mVisibleItemCount = visibleItemCount;

	    if (isFirstEnter && visibleItemCount > 0) {
	      loadBitmaps(firstVisibleItem, visibleItemCount);
	      isFirstEnter = false;
	    }
	  }
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	    // 仅当GridView静止时才去下载图片，GridView滑动时取消所有正在下载的任务
	    if (scrollState == SCROLL_STATE_IDLE) {
	      loadBitmaps(mFirstVisibleItem, mVisibleItemCount);
	    } else {
	      for (ImageContainer ic : icList) {
	        ic.cancelRequest();
	        System.err.println(">>>>> cancel loading:" + ic.getRequestUrl());
	      }
	      icList.clear();
	    }
	  }
	
	private void loadBitmaps(int firstVisibleItem, int visibleItemCount) {
	    try {
	      for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++) {
	        final String imageUrl = mList[i];
	        final ImageView ivImage = (ImageView)mGridView .findViewWithTag(imageUrl);
	        final long start_time=System.currentTimeMillis();
	        ImageSize imageSize = ImageSizeUtil.getImageViewSize(ivImage);
	        
	        ImageContainer ic = mImageLoader.get(imageUrl, new ImageListener() {

	          @Override
	          public void onResponse(ImageContainer response, boolean isImmediate) {
	            String imageUrl = response.getRequestUrl();

	            if (ivImage != null) {
	              Bitmap tbm = response.getBitmap();
	              if (tbm != null) {
	                System.out.println("<<<<<loading finish:" + imageUrl);
	                ivImage.setImageBitmap(response.getBitmap());
	                long finish_time=System.currentTimeMillis();
	                long loadTime=finish_time-start_time;
	                if(LoadTime.map.get(imageUrl) == null){
						LoadTime.map.put(imageUrl, loadTime);
						Log.e("loadtime1",imageUrl+":"+loadTime+"-"+start_time+"-"+finish_time);
					}else if(LoadTime.map2.get(imageUrl)==null){
						LoadTime.map2.put(imageUrl, loadTime);
						Log.e("loadtime2",imageUrl+":"+loadTime+"-"+start_time+"-"+finish_time);
					}
	              } else {
	                ivImage.setImageResource(R.drawable.pictures_no);
	              }
	            }
	          }

	          @Override
	          public void onErrorResponse(VolleyError error) {
	            error.printStackTrace();
	            ivImage.setImageResource(R.drawable.pictures_no);
	          }

	        }, imageSize.width, imageSize.height);
	        System.out.println(">>>>><" + i + ">loading:" + imageUrl);
	        icList.add(ic);
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }

    

}
