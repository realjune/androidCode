package com.mog.EncDemo;

/*import java.io.IOException;*/

import com.mog.EncDemo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/*
 * display the encrypt result . 
 */
public class EncResult extends Activity implements OnClickListener {
	private String strPassword ; 
	TextView txtViewName ; 
	TextView txtViewResult ; 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dispencresult); 
		Bundle bundle = getIntent().getExtras(); 
		strPassword = bundle.getString("password");//�������� 
		Button temButton = (Button)findViewById(R.id.dispresultButton) ; 
		temButton.setOnClickListener(this) ;
		/*temButton = (Button)findViewById(R.id.dispresultStart) ;
		temButton.setOnClickListener(this) ;*/
		txtViewName = (TextView)findViewById(R.id.dispresultFname) ; 
		txtViewResult = (TextView)findViewById(R.id.dispresultFresult) ; 
		EncDemoLib edl = new EncDemoLib();
		String strpath = new String(EncUtil.getSDPath());
		strpath = strpath + "a.txt" ; 
		TimerRecord trecord = new TimerRecord() ; 
		trecord.start() ; 
		int nRet = edl.EncryptFile(strpath.getBytes(), /*("123")*/strPassword.getBytes()) ;
		long timeRecord = trecord.stop() ; 
		txtViewName.setText(strpath) ; 
		switch(nRet)
		{
		case 1:
		{
			EncUtil.showMessage(this, "���ܳɹ�", "����ɶ��ļ��ļ���", "ȷ��") ;
			txtViewResult.setText("���ܺ�ʱ��" + timeRecord + "����") ;
		}
			break;
		case 2:
			EncUtil.showMessage(this, "����", "���ļ�ʧ��", "����") ; 
			txtViewResult.setText("���ļ�ʧ��") ;
			break;
		case 3:
			EncUtil.showMessage(this, "����", "�ļ�Ϊ��", "����") ; 
			txtViewResult.setText("�ļ�Ϊ��") ;
			break;
		case 4:
			EncUtil.showMessage(this, "����", "�����ļ�ʧ��", "����") ;
			txtViewResult.setText("�����ļ�ʧ��") ;
			break;
		case 5:
			EncUtil.showMessage(this, "����", "�ļ���������", "����") ; 
			txtViewResult.setText("�ļ���������") ;
			break;
		case 6:
			EncUtil.showMessage(this, "����", "�������", "����") ;
			txtViewResult.setText("�������") ;
			break;
		}
	}

	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.dispresultButton:
			finish();
			break;
/*		case R.id.dispresultStart:
			try 
			{
				startEncProgress();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			break;*/
		}
	}
/*	private void startEncProgress() throws IOException 
    {
		EncDesPackBlock edspb = new EncDesPackBlock(); 
		edspb.GetSourceFile(("a.txt")) ; 
    }*/
}