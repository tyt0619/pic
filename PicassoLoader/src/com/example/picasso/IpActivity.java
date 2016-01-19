package com.example.picasso;


import com.example.picasso.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class IpActivity extends Activity{
	String serverIp;
	String serverPort;
	EditText serverIpView;
	EditText serverPortView;
	Button button;
	
	
//起始界面，输入服务器ip和端口号
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_activity);
		
		serverIpView=(EditText)findViewById(R.id.serverIp);
		serverPortView=(EditText)findViewById(R.id.port);
		button=(Button)findViewById(R.id.button1);

		
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				serverIp=serverIpView.getText().toString();
				serverPort=serverPortView.getText().toString();
				System.out.println(serverIp+":"+serverPort);
				Intent i=new Intent(IpActivity.this,MainActivity.class);
				i.putExtra("ip",serverIp);
				i.putExtra("port", serverPort);
				startActivity(i);
				
			}
        });

		
		
	}
	

}
