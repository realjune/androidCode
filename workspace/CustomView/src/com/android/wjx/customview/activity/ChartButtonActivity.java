package com.android.wjx.customview.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.android.wjx.customview.EditViewTextChanger;
import com.android.wjx.customview.ProportionBar;
import com.android.wjx.customview.R;
import com.android.wjx.customview.ShadeDrawable;

public class ChartButtonActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		testEditTextChanger(this);
		testProportionBar(this);
	}
	
	private void testEditTextChanger(Activity context){
		EditText amount_et=(EditText) context.findViewById(R.id.edit_text);
		new EditViewTextChanger(amount_et);
	}
	private void testProportionBar(Context context){
		ProportionBar pb=(ProportionBar) findViewById(R.id.pb);
		pb.setBackgroundResource(R.drawable.srect);
		pb.addItem("20%", 20, 0XFFFF0000);
		pb.addItem("30%", 40, 0XFF00FF00);
		pb.addItem("10%", 10, 0XFF0099FF);
		pb.setBackgroundDrawable(new ShadeDrawable(context));
		
		ProportionBar pb1=(ProportionBar) findViewById(R.id.pb2);
		pb1.setBackgroundResource(R.drawable.srect);
		pb1.addItem("70%", 70, 0XFFFF0000);
		pb1.addItemByRes("30%", 30, R.drawable.redpb);
	}
}