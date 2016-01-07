package com.example.UI;

import java.io.File;

import com.example.demo_zhy_18_networkimageloader.R;
import com.zhy.utils.DownloadImgUtils;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class ImageActivity extends FragmentActivity{

	String ImgURI="";
	ImageView image;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.imagelayout);
		ImgURI=getIntent().getExtras().getString("KEY2");
		image=(ImageView) findViewById(R.id.imagesrc);
		new ImageAsync().execute("");
	}

	public void downLoad(View view)
	{
		Log.i("click", "done");
		//download  uri= ImgURI
		DownloadImgUtils.downloadImgByUrl(ImgURI,new File(Environment.getExternalStorageDirectory().getPath()+"/image"));
	}
	
	class ImageAsync extends AsyncTask<String,Void,Void>{

		Bitmap imap;
		@Override
		protected Void doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			imap=DownloadImgUtils.downloadImgByUrl(ImgURI,2,4);
			Log.w("width", imap.getWidth()+"");
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			image.setImageBitmap(imap);
		}
	}
	
}
