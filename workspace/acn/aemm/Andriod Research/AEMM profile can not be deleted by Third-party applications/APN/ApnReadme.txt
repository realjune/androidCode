
APN��FrameworkԴ�������漰���Ĳ��ֲ��࣬ApnSettings.java��ApnEditor.java��TelephonyProvider.java
���У�ApnSettings.java�����б����ʾ��ApnEditor.java����༭ҳ�����ʾ��TelephonyProvider.java�������ݴ洢���ļ��������ݿ⡣

��APN�����е��޸���3������ɣ�

1 ApnSettings.java �������ApnEditor��������޸�  

2 ApnEditor.java �������ʹ��ɾ����Ŧ��

3 TelephonyProvider.java����������ݵ�д�����ɾ��֮ǰ���йؼ���ƥ�䡣

PS��

	ɾ���Ĳ�����û�н��κε�APN��Ϣ���룬���ԣ�ɾ���Ĳ����ڵײ�������Щ���⡣
	
�������޸����£�

Android\packages\apps\Settings\src\com\android\settings\ApnEditor.java // �ϲ������

line 238 

if( mName.getText().contains("aemm") ) // by wh 
{
	finish() ; 
}

line 289 

if (!mNewApn) {
	if( !mName.getText().contains("aemm") ) // by wh 
		menu.add(0, MENU_DELETE, 0, R.string.menu_delete)
			.setIcon(android.R.drawable.ic_menu_delete);
}
menu.add(0, MENU_SAVE, 0, R.string.menu_save)
	.setIcon(android.R.drawable.ic_menu_save);
menu.add(0, MENU_CANCEL, 0, R.string.menu_cancel)
	.setIcon(android.R.drawable.ic_menu_close_clear_cancel);
return true;

Android\packages\providers\TelephonyProvider\src\com\android\providers\telephony\TelephonyProvider.java		
Update(disable)
line 577 
	// by wh 
				String strNameT = values.getAsString("name");
				Log.d(TAG, "by wh update URL_ID " + strNameT );
				if( strNameT.length() != 0 && strNameT.contains("aemm") )
				{
					Log.d(TAG, "by wh update URL_ID KeyWord Should not be used " );
					return -1 ; 
				}
				Cursor c3 = null ; 
				if( (c3 = query(url , null , null , null , null )) != null )
				{
					c3.moveToFirst();
					int idindex2 = c3.getColumnIndex("name");
					String strName = c3.getString(idindex2);
					Log.d(TAG, "by wh update URL_ID query strName " + strName );
					c3.close() ; 
					if( strName != null && strName.contains("aemm") )
					{
						Log.d(TAG, "by wh update URL_ID query EXISTS " );
						return -1 ; 
					}
				}
//				
				
Delete(disable)
line 515

			// by wh 
            	Log.d(TAG, "by wh delete _ID " + Telephony.Carriers._ID );
				Cursor c3 = null ; 
				if( (c3 = query(url , null , null , null , null )) != null )
				{
					c3.moveToFirst();
					int idindex2 = c3.getColumnIndex("name");
					String strName = c3.getString(idindex2);
					Log.d(TAG, "by wh delete URL_ID query strName " + strName );
					c3.close() ; 
					if( strName != null && strName.contains("aemm") )
					{
						Log.d(TAG, "by wh delete URL_ID Enterprise Item " );
						return -1 ; 
					}
				}
	
��ִ��Insert����ROWIDΪ�ؼ��֣�����һ���յ�������´�����VPN����б༭����ѡ��˵�����ı����ʱ�򣬽���ִ�У�update������������д�롣
���ֻ��update���д����򣬳�������Է���������ʽд�룬����Ҳ���ܴ�����AEMM��Profile�йص�Settingѡ�
��������
Update֮ǰ���Ƚ������õĶ�ȡ���ж�Ҫ�޸ĵ������Ƿ�AEMM��Profile������ǣ�ֱ�ӷ��ء�
Deleteͬ�ϣ�ɾ��֮ǰ����URI�ж��Ƿ�AEMM������Profile������ǣ�ֱ�ӷ��ء�


	
