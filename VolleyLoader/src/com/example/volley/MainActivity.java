package com.example.volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.volley.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends AbsSingleFragmentActivity
{
	private String[] mUrlStrs=null;
	private String serverIp;
	private String serverPort;
	//public static final int SHOW_RESPONSE = 0;
	@Override
	protected Fragment createFragment()
	{
		
		sendRequestWithHttpClient();
		ListImgsFragment ifrag=new ListImgsFragment();
		
		Bundle ibundle=new Bundle();
		ibundle.putStringArray("KEY", mUrlStrs);
		ifrag.setArguments(ibundle);
		return ifrag;
	}

	@Override
	protected int getLayoutId()
	{
		return R.layout.activity_single_fragment;
	}
	
	private void sendRequestWithHttpClient() {
		// TODO Auto-generated method stub
		serverIp=getIntent().getExtras().getString("ip");
		serverPort=getIntent().getExtras().getString("port");
		
		Thread th=new Thread(new Runnable() {
			@Override
			 public void run() {
				//用HttpClient发送请求，分为五步
				//第一步：创建HttpClient对象
				HttpClient httpCient = new DefaultHttpClient();
				//第二步：创建代表请求的对象,参数是访问的服务器地址
				HttpGet httpGet = new HttpGet("http://"+serverIp+":"+serverPort+"/test2/GetPicturePath");
				
				try {
					//第三步：执行请求，获取服务器发还的相应对象
					HttpResponse httpResponse = httpCient.execute(httpGet);
					//第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						String response=null;
						//第五步：从相应对象当中取出数据，放到entity当中
						HttpEntity entity = httpResponse.getEntity();
						response = EntityUtils.toString(entity,"utf-8");//将entity当中的数据转换为字符串
						String urls=response.toString();
						mUrlStrs=urls.split("\n");
//				 		for(int i=0;i<mUrlStrs.length;i++)
//				 			System.out.println("hello"+mUrlStrs[i]);
					}
					}catch (Exception e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
					}	
			}
		});
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
