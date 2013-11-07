package com.wjx.study;

import java.io.File;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Contacts.People;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Audio.Media;
import android.webkit.URLUtil;

public class Intent_Examples {
    /**run cativityInfo.
     * @param activityInfo
     * @return
     */
    public static Intent startApplication(final ActivityInfo activityInfo){
    	if(activityInfo==null){
    		return null;
    	}
      Intent i = new Intent(); 
      ComponentName cn = new ComponentName(activityInfo.packageName, 
      		activityInfo.name); 
      i.setComponent(cn); 
      i.setAction("android.intent.action.MAIN"); 
//      startActivityForResult(i, RESULT_OK); 
      return i;
    }
    /**install Application
     * @param apkFile
     * @return
     */
    public static Intent installApplication(String apkFile){
    	Uri uri = Uri.parse(apkFile);        
    	Intent it = new Intent(Intent.ACTION_VIEW, uri);        
    	it.setData(uri);
    	it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);        
    	it.setClassName("com.android.packageinstaller",        
    	               "com.android.packageinstaller.PackageInstallerActivity");        
    	return it;
    	//make sure the url_of_apk_file is readable for all users   
    	/*Uri installUri = Uri.fromParts("package", "", null);
    	returnIt = new Intent(Intent.ACTION_PACKAGE_ADDED, installUri);
*/

    }
    /**uninstall application
     * @param activityInfo 
     * @return is the activityInfo is null.
     * true if the param activityInfo isn't null else return false.
     */
    public static Intent uninstallApplication(ActivityInfo activityInfo){
    	if(activityInfo==null){
    		return null;
    	}
    	Uri uri = Uri.fromParts("package", activityInfo.packageName, null);         
    	Intent it = new Intent(Intent.ACTION_DELETE, uri);         
    	return it;
    }
    
    /**openURl
     * @param url
     * @return
     */
    public static Intent openURl(String url){
    	if(url==null||!URLUtil.isNetworkUrl(url)){
    		return null;
    	}
    	Uri uri = Uri.parse(url);        
    	Intent it = new Intent(Intent.ACTION_VIEW, uri);        
    	return it;
    }
    /**�� ����̖��ʽ
     * @param telnum
     */
    public static Intent openDial(String telnum){
    	Uri uri = Uri.parse("tel:"+telnum);        
    	Intent it = new Intent(Intent.ACTION_DIAL, uri);        
    	return it;  
    }
    /**ֱ�Ӵ��Ԓ�� ȥ
     * @param telNum
     */
    public static Intent call(String telNum){
    	Uri uri = Uri.parse("tel:"+telNum);        
    	Intent it = new Intent(Intent.ACTION_CALL, uri);        
    	return it;
    	//���@����Ҫ �� AndroidManifest.xml �У�����        
    	//<uses-permission id="android.permission.CALL_PHONE" />   
    }
    
    /**���÷��Ͷ��ŵĳ���
     * @param message
     */
    public static Intent openSms(String message){
    	//�� д����SMS       
    	Intent it = new Intent(Intent.ACTION_VIEW);        
    	it.putExtra("sms_body", message);         
    	it.setType("vnd.android-dir/mms-sms");        
    	return it;
    }
    /**���� SMS 
     * <p>SMS ��һ�ִ洢��ת������
     * Ҳ����˵������Ϣ������ֱ�Ӵӷ����˷��͵������ˣ���ʼ��ͨ�� SMS ���Ľ���ת����
     * ��������˴���δ����״̬�����ܵ绰�ѹرգ�������Ϣ���ڽ������ٴ�����ʱ���͡�
     * @param num
     * @param message
     */
    public static Intent openSms(String num,String message){
    	//���� SMS        
    	Uri uri = Uri.parse("smsto:"+num);        
    	Intent it = new Intent(Intent.ACTION_SENDTO, uri);        
    	it.putExtra("sms_body", message);        
    	return it;
    }
    /**
     * ���Ͳ���,û�ɹ�
     * <p>mms��Ӣ����д����������Membership Management System����д��
     * ��������Ϊ��Ա����ϵͳ��Ҳ������Multimedia Messaging Service����д��
     * ������Ϊ��ý����ŷ���
     */
    public static Intent openMMS(){//��Uri����ʵ������޸ģ�external�����ⲿ�洢��sdcard
//    	Uri uri = Uri.parse("content://media/external/images/media/23");
    	Uri uri = Uri.parse("content://media/external/23.png");
    	Intent it = new Intent(Intent.ACTION_SEND);
    	it.putExtra("sms_body", "some text");
    	it.putExtra(Intent.EXTRA_STREAM, uri);
    	it.setType("image/png");
    	return it;
    }
    /**open the mail editor with accept address
     * @param mailto accept mail address
     * @param context
     */
    public static Intent email(String mailto,String context){
    	Uri uri = Uri.parse("mailto:"+mailto);        
    	Intent it = new Intent(Intent.ACTION_SENDTO, uri);    
    	it.putExtra(Intent.EXTRA_TEXT, context);
    	return it; 
    }
    /**��ѡ���ͷ�ʽ��sms��email.
     * @param mailto
     * @param context
     */
    public static Intent email2(String mailto,String context){
    	Intent it = new Intent(Intent.ACTION_SEND);        
    	it.putExtra(Intent.EXTRA_EMAIL, new String[]{mailto});
    	it.putExtra(Intent.EXTRA_TEXT, context);        
    	it.setType("text/plain");  
        
//    	startActivity(it); 
    	return Intent.createChooser(it, "ѡ���ͷ�ʽ"); 
    }
    /**
     * @param mail the accepter
     * @param mailto the carbon copied
     * @param context 
     * @param subject
     */
   public static Intent email(String mailto,String mailcarbon,String context,String subject){
    	Intent it=new Intent(Intent.ACTION_SEND);          
    	String[] tos={mailto};          
    	String[] ccs={mailcarbon};          
    	it.putExtra(Intent.EXTRA_EMAIL, tos);          
    	it.putExtra(Intent.EXTRA_CC, ccs);          
    	it.putExtra(Intent.EXTRA_TEXT, context);          
    	it.putExtra(Intent.EXTRA_SUBJECT, subject);
    	//ʹ��ʱûЧ��
//    	it.setType("message/rfc822"); //�������� 
    	it.setType("text/plain");  
    	return it;
//    	startActivity(Intent.createChooser(it, "Choose Email Client"));
    }
    /**
     * һ������
     */
    public static Intent mail(){
    	//���͸� ��        
    	Intent it = new Intent(Intent.ACTION_SEND);        
    	it.putExtra(Intent.EXTRA_SUBJECT, "The email subject text");        
    	it.putExtra(Intent.EXTRA_STREAM, "file:///sdcard/mysong.mp3");        
    	it.setType("audio/mp3");
    	return Intent.createChooser(it, "Choose Email Client");
    }
    
    /**
     * û�гɹ�
     */
    public static Intent openMap(){
    	Uri uri = Uri.parse("geo:38.899533,-77.036476");        
    	Intent it = new Intent(Intent.ACTION_VIEW, uri);         
    	return it;         
    	//�� �� geo URI ����        
    	//geo:latitude,longitude        
    	//geo:latitude,longitude?z=zoom        
    	//geo:0,0?q=my+street+address        
    	//geo:0,0?q=business+near+city        
    	//google.streetview:cbll=lat,lng&cbp=1,yaw,,pitch,zoom&mz=mapZoom      

    }
    
    public static Intent openMapPath(){
    	Uri uri = Uri.parse("http://maps.google.com/maps?f=d&saddr=startLat%20startLng&daddr=endLat%20endLng&hl=en");
    	Intent it = new Intent(Intent.ACTION_VIEW,uri);
    	return it;
    }
    
    /**ǰ̨����
     * @param fileName
     */
    public static Intent playMp3(String fileName){
    	Intent it = new Intent(Intent.ACTION_VIEW);        
    	Uri uri = Uri.parse("file://"+fileName);        
    	it.setDataAndType(uri, "audio/mp3");        
    	return it;
    	//����,ʧ��
//    	Uri uri2 = Uri.parse(fileName);
//    		Intent it2 = new Intent(Intent.ACTION_VIEW, uri2);
//    		 it2.addFlags(it2.FLAG_ACTIVITY_NEW_TASK);//�Ǳ���ѡ��
//    		it2.setDataAndType(uri2,"audio/mp3");
//    		startActivity(it2);
    }
    /**��������,��ϵͳ�ڲ���MediaProvider�����е��ò���
     * @param num
     */
   public static Intent playRing(String num){
    	Uri uri = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");        
    	Intent it = new Intent(Intent.ACTION_VIEW, uri);        
    	return it;
    }
    
    /**û�гɹ�
     * @param activityInfo
     */
    private void market(ActivityInfo activityInfo){
    	//Ѱ ��Ӧ��       
//    	Uri uri = Uri.parse("market://search?q=pname:"+activityInfo.packageName);
//    	Uri uri = Uri.parse("market://search?q=<"+activityInfo.packageName+">");
//    	Uri uri = Uri.parse("http://market.android.com/search?q=pname:<"+activityInfo.packageName+">");        
//    	Intent it = new Intent(Intent.ACTION_VIEW, uri);        
//    	startActivity(it);        
    	//where pkg_name is the full package path for an application       
    	//��ʾӦ����ϸ�� ��      
//    	Uri uri = Uri.parse("market://details?id=app_id");        
//    	Intent it = new Intent(Intent.ACTION_VIEW, uri);        
//    	startActivity(it);        
    	//where app_id is the application ID, find the ID         
    	//by clicking on your application on Market home         
    	//page, and notice the ID from the address bar 
    }
    /**
     * SIM card Contact
     */
    public static Intent openSIMContact(){
    	Intent importIntent = new Intent(Intent.ACTION_VIEW);
    	importIntent.setType("vnd.android.cursor.item/sim-contact");
    	importIntent.setClassName("com.android.phone", "com.android.phone.SimContacts");
    	return importIntent;
    }
    
    /**������ϵ�˽���
     * 
     */
   public static Intent openPeople(){
    	  Intent intent = new Intent();
    	  intent.setAction(Intent.ACTION_VIEW);
    	  intent.setData(People.CONTENT_URI);
    	  return intent;
    	
//    	18����ϵ���б� 
//        <1>           
//         Intent i = new Intent(); 
//         i.setAction(Intent.ACTION_GET_CONTENT); 
//         i.setType("vnd.android.cursor.item/phone"); 
//         startActivityForResult(i, 11); 

//        <2> 
//        Uri uri = Uri.parse("content://contacts/people"); 
//        Intent it = new Intent(Intent.ACTION_PICK, uri); 
//        startActivityForResult(it, 111); 
    }
    public static Intent openPeople(int ID){
    	 Uri personUri = ContentUris.withAppendedId(People.CONTENT_URI, ID);//����ID����Ϊ��ϵ��Provider�е����ݿ�BaseID������һ��
    	 Intent intent = new Intent();
    	 intent.setAction(Intent.ACTION_VIEW);
    	 intent.setData(personUri);
    	 return intent;
    }
    /**
     * ������ͼƬView
     * <p>use as:startActivityForResult(i, 11);
     */
    public static Intent selectPictrue(){
//    	 ѡ��һ��ͼƬ
//    	 Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
//    	 intent.addCategory(Intent.CATEGORY_OPENABLE);  
//    	 intent.setType("image/*");
//    	 startActivityForResult(intent, 0);
    	 Intent i = new Intent(); 
         i.setType("image/*"); 
         i.setAction(Intent.ACTION_GET_CONTENT); 
         return i;
    }
    /**<p>use as:startActivityForResult(i, 0);
     * @return
     */
    public static Intent camera(){
//    	 ����Android�豸������������������պ���λ��
    	 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment
    	.getExternalStorageDirectory().getAbsolutePath(), "android123" + ".jpg"))); //���λ��Ϊsdcard����cwj�ļ��У��ļ���Ϊandroid123.jpg��ʽ
    	return intent;
    }
    /**
     * use as:startActivityForResult(inttPhoto, 10);
     */
    public static Intent camera2(Context cotext){
    	long dateTaken = System.currentTimeMillis(); 
        String name = dateTaken + ".jpg"; 
       String fileName = "/sdcard/" + name; 
        ContentValues values = new ContentValues(); 
        values.put(Images.Media.TITLE, fileName); 
        values.put("_data", fileName); 
        values.put(Images.Media.PICASA_ID, fileName); 
        values.put(Images.Media.DISPLAY_NAME, fileName); 
        values.put(Images.Media.DESCRIPTION, fileName); 
        values.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, fileName); 
        Uri photoUri = cotext.getContentResolver().insert( 
                  MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values); 
          
        Intent inttPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
        inttPhoto.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); 
        return inttPhoto; 
    }
    /**��google�������� 
     * @param searchString
     */
    public static Intent searchGoogle(String searchString){
    	Intent intent = new Intent(); 
    	intent.setAction(Intent.ACTION_WEB_SEARCH); 
    	intent.putExtra(SearchManager.QUERY,searchString); 
    	return intent; 
    }
    /**
     * ��¼����
     */
   public static Intent openRecord(){
    	Intent mi = new Intent(Media.RECORD_SOUND_ACTION); 
    	return mi; 
    }
}
