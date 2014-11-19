package com.android.accenture.aemm.dome;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

/**
 * @author junxu.wang
 *
 */
public class ApkReceiver extends BroadcastReceiver {
	ApkManager mApkManager;
	public ApkReceiver(ApkManager apkManager){
		mApkManager=apkManager;
	}
	@Override
	public void onReceive(Context context, Intent intent) {
		// ���չ㲥��ϵͳ������ɺ����г���
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Intent newIntent = new Intent(context, ApkHall.class);
			newIntent.setAction("android.intent.action.MAIN");
			newIntent.addCategory("android.intent.category.LAUNCHER");
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(newIntent);
		}
		// ���չ㲥���豸���°�װ��һ��Ӧ�ó�������Զ������°�װӦ�ó���
		if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
			String packageName = intent.getDataString().substring(8);
			Log.v("VV","receve PACKAGE_ADDED "+packageName);
			Intent newIntent = new Intent();
			newIntent.setClassName(packageName, packageName + ".MainActivity");
			newIntent.setAction("android.intent.action.MAIN");
			newIntent.addCategory("android.intent.category.LAUNCHER");
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(newIntent);
			Log.v("V","uri:"+intent.getData().toString()+"\n"+packageName+"\n"+intent.toString());
			if(mApkManager==null){
				Intent i=new Intent(context,ApkHall.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
				mApkManager=ApkHall.apkHall.mApkManager;
			}
			Message message = new Message();  
            message.what =ApkManager.INSTALL;
            message.obj=packageName;
            mApkManager.handler.sendMessage(message);
		}
		// ���չ㲥���豸��ɾ����һ��Ӧ�ó������
		if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
			Log.v("VV","receve package_removed "+intent.getDataString().substring(8));
			if(mApkManager==null){
				Intent i=new Intent(context,ApkHall.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
				mApkManager=ApkHall.apkHall.mApkManager;
			}
			Message message = new Message();  
            message.what =ApkManager.UNINSTALL;
            message.obj=intent.getDataString().substring(8);
            mApkManager.handler.sendMessage(message);
//			DatabaseHelper dbhelper = new DatabaseHelper();
//			dbhelper.executeSql("delete from users");
            Log.v("V",intent.getDataString().substring(8)+" uri:"+intent.getData().toString()+"\n"+intent.toString());
		}

	}
}
