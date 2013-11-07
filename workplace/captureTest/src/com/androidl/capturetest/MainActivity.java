package com.androidl.capturetest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {
	public static final int CaptureRequestCode=1;
	
	public static final String DATA="data";
	
	EditText result_tv;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture_test);
		result_tv=(EditText) findViewById(R.id.editText1);
		((Button)findViewById(R.id.button1)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goToViewForResult(MainActivity.this, R.id.search,CaptureRequestCode);
			}
		});
	}
	 public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if(data!=null&&requestCode==CaptureRequestCode){
			 String scaString=data.getStringExtra(DATA);
			 result_tv.setText(scaString);
		 }
	 }
	 
	 
	 public static final String ViewArgu1 = "ViewArgu1";
		public static final String ViewArgu2 = "ViewArgu2";
		public static final String ViewArgu3 = "ViewArgu3";
		public static final String ViewArgu4 = "ViewArgu4";
		public static final String ViewArguBundle = "ViewArguBundle";

		public static final String formatString = "yyyy-MM-dd";
		public static final String formatString2 = "yyyy��MM��dd��";

		public static void goToView(Context cxt, Class<?> targetClass) {
			Intent intent = new Intent(cxt, targetClass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			cxt.startActivity(intent);
		}

		public static void goToViewAndNewTask(Context cxt, Class<?> targetClass) {
			Intent intent = new Intent(cxt, targetClass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			cxt.startActivity(intent);
		}

		public static void goToViewForResult(Activity acy, Class<?> targetClass,
				int code,int requestCode) {
			Intent intent = new Intent(acy, targetClass);
			intent.putExtra(ViewArgu1, String.valueOf(code));
			acy.startActivityForResult(intent, requestCode);
		}

		public static void goToViewForResult(Activity acy,
				int code,int requestCode) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addCategory("android.intent.category.capture");
			intent.putExtra(ViewArgu1, String.valueOf(code));
			acy.startActivityForResult(intent, requestCode);
		}

		public static void goToViewByArgu(Context cxt, Class<?> targetClass,
				String argu1) {
			Intent intent = new Intent(cxt, targetClass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(ViewArgu1, argu1);
			cxt.startActivity(intent);
		}

		public static void goToViewByArgu(Context cxt, Class<?> targetClass,
				String argu1, String argu2) {
			Intent intent = new Intent(cxt, targetClass);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra(ViewArgu1, argu1);
			intent.putExtra(ViewArgu2, argu2);
			cxt.startActivity(intent);
		}

		public static String Argu1(Intent intent) {
			if (null != intent) {
				return intent.getStringExtra(ViewArgu1);
			}
			return null;
		}

		public static String Argu2(Intent intent) {
			if (null != intent) {
				return intent.getStringExtra(ViewArgu2);
			}
			return null;
		}
		
		public static String Argu3(Intent intent) {
			if (null != intent) {
				return intent.getStringExtra(ViewArgu3);
			}
			return null;
		}
}
