package com.android.wjx.customview.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.wjx.customview.dialog.CustomDialog;
import com.android.wjx.customview.dialog.CustomDialogDemo;

public class CustomDialogActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		testCustomerDatePickerDialog(this);
	}
	
	private void testCustomerDatePickerDialog(final Context context){
		new CustomDialogDemo(context).show();
		CustomDialog.createDialog(context,"hello,here is message...");
		
		final CustomDialog cd = new CustomDialog(context);
		cd.setCancelable(false);
		cd.show();
		cd.setMessage("¹þ¹þ´ó¿§·È¿¨µÄ¿ª·¢");
		cd.addPositiveButton(android.R.string.ok, new View.OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
			}
		});
		cd.addNegativeButton(android.R.string.cancel, new View.OnClickListener() {
			public void onClick(View v) {
				cd.dismiss();
			}
		});
		
		cd.addNeutralButton("ÚÌÚÌ", new View.OnClickListener() {
			public void onClick(View v) {
				cd.setTitle("ßÏ ß×");
			}
		});
	}

}
