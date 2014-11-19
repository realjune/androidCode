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
import com.mog.EncDemo.SetPassword;
/*
 * this file is used to encrypt the input data , 
 * this page contains a inputtextarea ,
 *  which allows user to input data .
 *  a button is prepared as : go and encrypt data .  
 */
public class ContentEnc extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	 public static final String KEY_ENCTYPE =
	      "com.mog.EncDemo.fileenc";
	 
	private FileService fileService = new FileService();
    //������ͼ�е�filename��������
    private EditText fileNameText;
    //������ͼ�е�contentText��������
    private EditText contentText;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contentenc);

		Button buttonEnc = (Button) findViewById(R.id.contentencButton);
		buttonEnc.setOnClickListener(this);
		buttonEnc = (Button)findViewById(R.id.contomainButton) ; 
		buttonEnc.setOnClickListener(this) ; 
		contentText = (EditText)findViewById(R.id.edit_text) ; 
	}

	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
		case R.id.contentencButton:
			encInputText() ; 
			break;
		case R.id.contomainButton:
			finish();
			break;
		}
	}
	
	public void encInputText()
	{
		String fileName = "wokao" ; // fileNameText.getText().toString();
        String content = contentText.getText().toString();
/*   
        //���ļ���Ϊ�յ�ʱ����ʾ�û��ļ���Ϊ�գ�����¼��־��
        if(isEmpty(fileName)) 
        {
        	showMessage("����","������Ҫ������ļ���","����");
            return;
        }
*/
        //���ļ�����Ϊ�յ�ʱ����ʾ�û��ļ�����Ϊ�գ�����¼��־��
        if(EncUtil.isEmpty(content)) 
        {
        	EncUtil.showMessage(this , "����","������Ҫ���ܵ�����","����") ; 
            return;
        }
        Intent iStart = new Intent(ContentEnc.this , SetPassword.class ) ; 
    	startActivity(iStart);
/*    	
        //���ļ��������ݶ���Ϊ�յ�ʱ�򣬵���fileService��save����
        //���ɹ�ִ�е�ʱ����ʾ�û�����ɹ�������¼��־
        //�������쳣��ʱ����ʾ�û�����ʧ�ܣ�����¼��־
        try 
        {
            fileService.save(fileName, content);
            Log.i(fileService.TAG, "The file save successful");
        } 
        catch (Exception e) 
        {
        	showMessage("����","�ļ�����ʧ��","����");
            Log.e(fileService.TAG, "The file save failed");
        }
*/
	}
}