package com.example.UIL;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.utils.BitmapCache;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListVolleryAdapter extends BaseAdapter{
    Context mContext = null;
    String[] mList = null;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;
    
    public ListVolleryAdapter(String[] list , Context context){
        mList = list;
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(mContext);
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
    
     ViewHolder mHolder = null;
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder mHolder = null;
        ImageView imageview=null;

        if (null == view){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_list_imgs,parent,false);
			imageview = (ImageView) view
					.findViewById(R.id.id_img);
            mHolder = new ViewHolder();
            mHolder.mImage = imageview;
            view.setTag(mHolder);
        }else{
            mHolder = (ViewHolder)view.getTag();
        }
        
        ImageListener listener  = ImageLoader.getImageListener(mHolder.mImage, R.drawable.pictures_no, R.drawable.pictures_no);
        mImageLoader.get(mList[position], listener);
        
        return view;
    }
    
    public class ViewHolder{
        public ImageView mImage = null;
    }
    

}
