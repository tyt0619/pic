package com.example.UI;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.example.picasso.R;
import com.example.utils.DownloadImgUtils;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends FragmentActivity{

	String ImgURI="";
	ImageView image;
	String path="";
	Context context;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.imagelayout);
		ImgURI=getIntent().getExtras().getString("KEY2");
		image=(ImageView) findViewById(R.id.imagesrc);
		context=getApplicationContext();
        Picasso.with(context) 
        .load(ImgURI) 
        //.resize(imagesize.width, imagesize.height)
        //.centerCrop()
        .fit()
        .placeholder(R.drawable.pictures_no) 
        .error(R.drawable.ic_error)
        .tag(context)
        .config(Config.RGB_565)
        .into(image);
	}

	public void downLoad(View view)
	{
		Log.i("click", "done");
		new  DownLoadAsync().execute("");
	}
	
	class DownLoadAsync extends AsyncTask<String,Void,Boolean>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			Toast.makeText(ImageActivity.this, "开始下载", Toast.LENGTH_LONG).show();
			super.onPreExecute();
		}
		
		
	    //判断是否存在sd卡
        private boolean ExistSDCard() {
                      if (android.os.Environment.getExternalStorageState().equals(
                        android.os.Environment.MEDIA_MOUNTED)) {
                                       return true;
                                      } else
                       return false;
                     }

		
		@Override
		protected Boolean doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			//String path=null;
			if(ExistSDCard())
			{
				String pic_dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
				File file=new File(pic_dir);
				if(!file.exists() && !file.isDirectory()){
					file.mkdir();
				}
				path=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()+"/"+System.currentTimeMillis()+".jpg";
				Log.i("SDcard", "prepared");
			}
			else{
				try {
					String name=System.currentTimeMillis()+".jpg";
					OutputStream os=ImageActivity.this.openFileOutput(name, Context.MODE_WORLD_READABLE|Context.MODE_WORLD_WRITEABLE);
					os.close();
					path=ImageActivity.this.getFilesDir().getPath()+"/"+name;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("SDcard","disabled");
			}
			
			System.out.println("path:"+path);
			File newFile=new File(path);
			if(!newFile.exists())
				try {
					newFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.i("err", "IO ERR");
					e.printStackTrace();
				}
			 return DownloadImgUtils.downloadImgByUrl(ImgURI,newFile);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			if(result)
			{
				Toast.makeText(ImageActivity.this, "下载成功\n路径:"+path, Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(ImageActivity.this, "下载失败,请重试", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	
}
