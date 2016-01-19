package com.example.UI;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.example.UIL.R;
import com.example.utils.DownloadImgUtils;
import com.example.utils.ImageSizeUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.ImageSize;

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
	private ImageLoader mImageLoader=ImageLoader.getInstance();
	DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.imagelayout);
		ImgURI=getIntent().getExtras().getString("KEY2");
		image=(ImageView) findViewById(R.id.imagesrc);
		//配置显示选项
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.pictures_no)//默认图片
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.bitmapConfig(Bitmap.Config.RGB_565)	 //设置图片的解码类型
		.build();
		
	    //ImageSize mImageSize = new ImageSize(ImageSizeUtil.getImageViewSize(image).width,ImageSizeUtil.getImageViewSize(image).height);
		//开始加载
		mImageLoader.displayImage(ImgURI, image,options);
	    
	}

	public void downLoad(View view)
	{
		Log.i("click", "done");
		new  DownLoadAsync().execute("");
	}
	//异步下载图片
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
					Log.e("err", "IO ERR");
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
