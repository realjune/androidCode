package com.example.deviceinfo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	private DisplayMetrics dm;//��Ļ�ֱ�������
	private TextView info_tv;
	String str="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		info_tv=(TextView) findViewById(R.id.info_tv);

		dm = new DisplayMetrics();

		this.getWindowManager().getDefaultDisplay().getMetrics(dm);

		int width = dm.widthPixels;

		int height = dm.heightPixels;
		
		str+="pw:"+width+" , "+"ph:"+height;

		        Display display = getWindowManager().getDefaultDisplay(); 


		        width = display.getWidth();

		 

		        height = display.getHeight(); 

				str+="dw:"+width+" , "+"dh:"+height;
				
				str+="\nMemoryclass:"+getMemoryclass()+"MB";
				info_tv.setText(str);

	}
	
	/**每个 android 平台内存限制不一样，从最开始的 16M 到 24M，以及后来的 32M，64M，或许以后会更大。
	那如何获取单个 app 内存限制大小呢？
	class : ActivityManager*/
	public int getMemoryclass(){
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		return activityManager.getMemoryClass(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
