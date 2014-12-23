package com.android.wjx.customview.activity;

import wu.a.lib.view.CoverFlow;
import android.app.Activity;
import android.os.Bundle;
import ccom.custom.view.R;

public class SwitchButtonActivity extends Activity {

	/** Called when the activity is first created. */
	CoverFlow cf;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.switchbutton_layout);
	}

}
