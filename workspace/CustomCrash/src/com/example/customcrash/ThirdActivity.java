package com.example.customcrash;

import wu.a.app.ExitAppUtils;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends Activity {
	TextView mTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ExitAppUtils.getInstance().addActivity(this);
		
		mTextView.setText("222222222");
	}


}
