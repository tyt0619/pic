package com.example.picasso;

import com.example.picasso.R;
import com.example.utils.LoadTime;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridViewAdapter extends BaseAdapter{
    Context mContext = null;
    String[] mList = null;
    GridView mGridView;
    
    
    public GridViewAdapter(String[] list , Context context,GridView mGridView){
        mList = list;
        mContext = context;
        this.mGridView=mGridView;
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
        long start_time=System.currentTimeMillis();
        if (null == view){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_list_imgs,parent,false);
        }
        imageview = (ImageView) view.findViewById(R.id.id_img);
      //  ImageSize imagesize=ImageSizeUtil.getImageViewSize(imageview);
        Picasso.with(mContext) 
        .load(mList[position]) 
       // .resize(imagesize.width, imagesize.height)
        //.centerCrop()
        .fit()
        .placeholder(R.drawable.pictures_no) 
        .error(R.drawable.ic_error)
        .tag(mContext)
        .config(Config.RGB_565)
        .transform(new CropSquareTransformation(start_time,mList[position]))
        .into(imageview);
        return view;
  
    }
    public class CropSquareTransformation implements Transformation {
    	long start_time;
    	String url;
    	public CropSquareTransformation(long start_time,String url) {
			// TODO Auto-generated constructor stub
    		this.start_time=start_time;
    		this.url=url;
    	}
		@Override 
    	public String key() { return "square()"; }
		@Override
		public Bitmap transform(Bitmap source) {
			// TODO Auto-generated method stub
			long finish_time=System.currentTimeMillis();
			long loadtime=finish_time-start_time;
			if(LoadTime.map.get(url) == null){
				LoadTime.map.put(url, finish_time-start_time);				
				Log.e("loadtime1",url+":"+loadtime+"-"+start_time+"-"+finish_time);
			}else if(LoadTime.map2.get(url)==null){
				LoadTime.map2.put(url, loadtime);
				Log.e("loadtime2",url+":"+loadtime+"-"+start_time+"-"+finish_time);
			}
    	    return source;
		}
    	}

    
}
