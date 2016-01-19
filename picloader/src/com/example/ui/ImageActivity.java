package com.example.ui;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.example.picloader.R;
import com.example.utils.DownloadImgUtils;
import android.content.Context;
import android.graphics.Bitmap;
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
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.imagelayout);
		ImgURI=getIntent().getExtras().getString("KEY2");
		image=(ImageView) findViewById(R.id.imagesrc);
		//异步加载
		new ImageAsync().execute("");
	}

	public void downLoad(View view)
	{
		Log.i("click", "done");
		//download  uri= ImgURI
		//异步下载
		new DownLoadAsync().execute("");
//		
	}
	
	//下载图片
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
				//存在sd卡就获得sd卡中的pictures目录，没有就新建
				String pic_dir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
				File file=new File(pic_dir);
				if(!file.exists() && !file.isDirectory()){
					file.mkdir();
				}
				path=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()+"/"+System.currentTimeMillis()+".jpg";
				Log.i("SDcard", "prepared");
			}
			else{
				//没有sd卡。就存在/data/data/android/包名/file中
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
					Log.e("err", "IO ERR");
					e.printStackTrace();
				}
			//下载图片至newfile
			 return DownloadImgUtils.downloadImgByUrl(ImgURI,newFile);
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			//显示下载结果，成功就显示途径
			if(result)
			{
				Toast.makeText(ImageActivity.this, "下载成功\n路径:"+path, Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(ImageActivity.this, "下载失败,请重试", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	class ImageAsync extends AsyncTask<String,Void,Void>{

		Bitmap imap;
		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			//ImageSize imagesize=ImageSizeUtil.getImageViewSize(image);
			//加载放大的图片
			imap=DownloadImgUtils.downloadImgByUrl(ImgURI,2,4);
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			//将图片显示到界面上
			super.onPostExecute(result);
			image.setImageBitmap(imap);
		}
	}
	
}
