package com.mog.EncDemo;

import com.mog.EncDemo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mog.EncDemo.FileService;
/*
 * this file is used to display the decrypted data 
 */
public class DispDecContent extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	public static final String KEY_ENCTYPE = "com.mog.EncDemo.DispDecContent";
	private String strinput;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dispdectext);
		Button buttonEnc = (Button) findViewById(R.id.disptomainButton);
		buttonEnc.setOnClickListener(this);
		Intent intent = getIntent();
		strinput = intent.getStringExtra("inputdata");
		EditText edtFname = (EditText) findViewById(R.id.dispdecFname) ; 
		EditText edtText = (EditText) findViewById(R.id.dispdecText);
		EncDemoLib edl = new EncDemoLib();
		String strpath = new String(EncUtil.getSDPath()) ; 
		strpath = strpath + "a.txt.mec" ; 
		TimerRecord tr = new TimerRecord();
		tr.start(); 
		int nRet = edl.DecryptFile(strpath.getBytes(), /*("456")*/strinput.getBytes()) ;
		long timeRecord = tr.stop() ; 
		switch(nRet)
		{
		case 1:
			EncUtil.showMessage(this, "���ܳɹ�", "���ܺ�ʱ:"+timeRecord + "����", "ȷ��") ;
			break;
		case 2:
			EncUtil.showMessage(this, "����", "���ļ�ʧ��", "����") ; 
			break;
		case 3:
			EncUtil.showMessage(this, "����", "�ļ�Ϊ��", "����") ; 
			break;
		case 4:
			EncUtil.showMessage(this, "����", "�����ļ�ʧ��", "����") ; 
			break;
		case 5:
			EncUtil.showMessage(this, "����", "�ļ���������", "����") ; 
			break;		
		case 6:
			EncUtil.showMessage(this, "����", "�������", "����") ;
			break;
		}
		/*
		String str = null;
		if( nRet == 1 )
		{
			try {
				strpath = strpath + ".txt" ; 
				str = new String(FileService.read(strpath));
			} catch (Exception e) {
				e.printStackTrace();
			}
			edtFname.setText(strpath) ; 
			edtText.setText(str);
		}
		else
		{
			edtFname.setText("�����ļ���") ; 
			edtText.setText("�ļ�����ʧ��") ; 
		}
		*/
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.disptomainButton:
			finish();
			break;
		}
	}
}