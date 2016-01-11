package com.example.picasso;

import com.example.picasso.R;
import com.squareup.picasso.Picasso;

import android.content.Context;
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
        .into(imageview);
        return view;
    }
    
}
