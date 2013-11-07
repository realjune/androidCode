package com.android.wjx.customview.activity;

import com.android.wjx.customview.PathShapeView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class PathShapeActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		testPathShapeView(this);
	}
	

	private void testPathShapeView(Context context){
		PathShapeView psv;
		psv=new PathShapeView(context);
		android.widget.LinearLayout.LayoutParams lp=new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		psv.setLayoutParams(lp);
		setContentView(psv);
	}

}
