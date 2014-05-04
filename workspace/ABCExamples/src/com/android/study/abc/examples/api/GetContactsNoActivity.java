package com.android.study.abc.examples.api;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.study.abc.examples.R;


/**
 * ��android����Ŀ����У�����Ҫʵ�ֵ�һ�������ǵ���ϵͳ�Դ�����ϵ�˽��棬����ѡ����֮��ȡ����Ӧ�����ƺͺ��롣��android2.0
 * ǰ��ʵ�ַ�ʽ��Ҳ��һЩ�����Ҫ��2.0�汾����ϵ��API�����˱仯����ԭ����Contacts�����ContactsContract��
 * 
 * @author junxu.wang
 * 
 */
public class GetContactsNoActivity extends Activity implements OnClickListener {
	private static final String TAG="GetContactsNoActivity";
	EditText et;

    Toast mToast;
	ResultDisplayer mPendingResult;

    class ResultDisplayer implements OnClickListener {
        String mMsg;
        String mMimeType;
        
        ResultDisplayer(String msg, String mimeType) {
            mMsg = msg;
            mMimeType = mimeType;
        }
        
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType(mMimeType);
            mPendingResult = this;
            startActivityForResult(intent, 1);
        }
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.api_getcontacts_no);
		et = (EditText) findViewById(R.id.editText1);
		((Button)findViewById(R.id.pick_phone)).setOnClickListener(
                new ResultDisplayer("Selected phone",
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE));
		// Watch for button clicks.
        ((Button)findViewById(R.id.pick_contact)).setOnClickListener(
                new ResultDisplayer("Selected contact",
                        ContactsContract.Contacts.CONTENT_ITEM_TYPE));
        ((Button)findViewById(R.id.pick_person)).setOnClickListener(
                new ResultDisplayer("Selected person",
                        "vnd.android.cursor.item/person"));
        ((Button)findViewById(R.id.pick_phone)).setOnClickListener(
                new ResultDisplayer("Selected phone",
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE));
        ((Button)findViewById(R.id.pick_address)).setOnClickListener(
                new ResultDisplayer("Selected address",
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE));
	}

	public static final int PICK_CONTACT=1;
	
	@Override
	public void onClick(View v) {
//		2.0֮ǰȡ��ϵ���б�ķ�ʽ��
//		������ͼ��ʽһ
//		Intent intent = new Intent();
//		intent.setAction(Intent.ACTION_PICK);
//		intent.setData(Contacts.People.CONTENT_URI);
//		startActivityForResult(intent,  PICK_CONTACT);
//		������ͼ��ʽ��
//		Intent intent  = new Intent(Intent.ACTION_PICK);
//		intent.setType("Contacts.People.CONTENT_TYPE");//vnd.android.cursor.dir/person
//		startActivityForResult(intent,  PICK_CONTACT);
//		2.0֮��ȡ��ϵ���б�ķ�ʽ��
//		������ͼ��ʽһ

//		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//		startActivityForResult(intent, PICK_CONTACT);
//		������ͼ��ʽ��
//		Intent intent = new Intent(Intent.ACTION_PICK);
//		intent.setType(ContactsContract.Contacts.CONTENT_TYPE);//vnd.android.cursor.dir/contact
//		startActivityForResult(intent, PICK_CONTACT);


	}
//	2.0ǰ ʵ��onActivityResult
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        
	        if (data != null) {
	            Uri uri = data.getData();
	            if (uri != null) {
	                Cursor c = null;
	                try {
	                    c = getContentResolver().query(uri, new String[] { BaseColumns._ID ,ContactsContract.CommonDataKinds.Phone.NUMBER},
	                            null, null, null);
	                    if (c != null && c.moveToFirst()) {
	                        int id = c.getInt(0);
	                        if (mToast != null) {
	                            mToast.cancel();
	                        }
	                        int index=c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
	                        String phoneNumber = c.getString(index);
	                        String txt = mPendingResult.mMsg + ":\n" + uri + "\nid: " + id;
	                        mToast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
	                        mToast.show();
	                        et.setText(phoneNumber);
	                    }
	                } finally {
	                    if (c != null) {
	                        c.close();
	                    }
	                }
	            }
	        }
	    } 
//	2.0�� ʵ��onActivityResult
//	@Override
//	public void onActivityResult(int reqCode, int resultCode, Intent data) {
//	  super.onActivityResult(reqCode, resultCode, data);
//
//
//	  switch (reqCode) {
//	    case (PICK_CONTACT) :
//	      if (resultCode == Activity.RESULT_OK) {
//	        Uri contactData = data.getData();
//	        Cursor c =  managedQuery(contactData, null, null, null, null);
//	        if (c.moveToFirst()) {
//	          String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//	          String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//	          String phoneNumber = null;
//	          if ( hasPhone.equalsIgnoreCase("1"))
//	              hasPhone = "true";
//	          else
//	              hasPhone = "false" ;
//
//
//	          if (Boolean.parseBoolean(hasPhone)) 
//	          {
//	   Cursor phones=
//	           getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,null, null);
//	           while (phones.moveToNext()) 
//	           {
//	             phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//	           }
//	           phones.close();
//	          }
//	        }
//	      }
//	      break;
//	  }
//	}

}
