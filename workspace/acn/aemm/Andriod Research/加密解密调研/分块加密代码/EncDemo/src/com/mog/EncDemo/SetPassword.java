package com.mog.EncDemo;

import com.mog.EncDemo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/*
 * set the password of the file to be encrypted 
 */
public class SetPassword extends Activity implements OnClickListener {
	private EditText edtInput;
	private EditText edtConfirm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setpass);
		Button buttonEnc = (Button) findViewById(R.id.setpassNext);
		buttonEnc.setOnClickListener(this);
		edtInput = (EditText) findViewById(R.id.setpassInput);
		edtConfirm = (EditText) findViewById(R.id.setpassConfirm);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.setpassNext:
			checkPassword();
			break;
		}
	}

	private void checkPassword() 
	{
		// check password 
		String strinput = edtInput.getText().toString();
		String strconfirm = edtConfirm.getText().toString(); 
		if( EncUtil.isEmpty(strinput))
		{
			EncUtil.showMessage(this,"����","���벻��Ϊ��","����"); 
			return ; 
		}
		if( strinput.compareTo(strconfirm) != 0 )
		{
			EncUtil.showMessage(this,"����","������������벻һ�� ","����") ; 
			return ; 
		}
		 // ��Bundle������Ҫ������
        Bundle bundle = new Bundle();
        bundle.putString("password", strinput);
        // ����һ���µ�Intent������Bundle����ȥ
        Intent it = new Intent();
        it.putExtras(bundle);
        it.setClass(SetPassword.this, EncResult.class);
        startActivity(it);
        finish();
		// use the pass to encrypt the data . 
	}
}