package com.android.ring.devutils;

import java.lang.reflect.Method;

import com.android.log.CLog;

import android.content.Context;
import android.net.ConnectivityManager;

public class GprsUtils {
	CLog cLog=new CLog(GprsUtils.class.getSimpleName());
	private static final boolean ENABLE=true;
	private Context context;
	private ConnectivityManager mCM; 
	private boolean oldEnable;
	private boolean enable;
	
	public GprsUtils(Context context){
		this.context=context;
	}
	
	public void onCreate(){
		cLog.println("onCreate");
		mCM = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		oldEnable=gprsIsOpenMethod("getMobileDataEnabled");
		cLog.println("old gprs "+oldEnable);
		if(oldEnable!=ENABLE) {
			setGprsEnabled("setMobileDataEnabled", ENABLE); 
		}
		enable=gprsIsOpenMethod("getMobileDataEnabled");
		cLog.println("cur gprs "+enable);
	}
	
	public void onDestroy(){
		cLog.println("onDestroy");
		if(oldEnable!=ENABLE){
			setGprsEnabled("setMobileDataEnabled", oldEnable); 
		}
		enable=gprsIsOpenMethod("getMobileDataEnabled");
		cLog.println("cur gprs "+enable);
	}
	
	/*
	�������õ��������Զ���ķ���
	 gprsIsOpenMethod  �Ǵ�framework��ȡ��getMobileDataEnabled�������, ��Ҫ�������GPRS�Ƿ��
	 setGprsEnabled  ȡ��setMobileDataEnabled����, �����򿪻�ر�GPRS
	 ����������ֻҪ����grpsEnabled���ɡ�*/
	//�򿪻�ر�GPRS 
	private boolean gprsEnabled(boolean bEnable) 
	{ 
		Object[] argObjects = null; 
		
		boolean isOpen = gprsIsOpenMethod("getMobileDataEnabled"); 
		if(isOpen == !bEnable) 
		{ 
			setGprsEnabled("setMobileDataEnabled", bEnable); 
		} 
		
		return isOpen;   
	} 
	
	//���GPRS�Ƿ�� 
	private boolean gprsIsOpenMethod(String methodName) 
	{ 
		Class cmClass       = mCM.getClass(); 
		Class[] argClasses  = null; 
		Object[] argObject  = null; 
		
		Boolean isOpen = false; 
		try 
		{ 
			Method method = cmClass.getMethod(methodName, argClasses); 
			
			isOpen = (Boolean) method.invoke(mCM, argObject); 
		} catch (Exception e) 
		{ 
			e.printStackTrace(); 
		} 
		
		return isOpen; 
	} 
	
	//����/�ر�GPRS 
	private void setGprsEnabled(String methodName, boolean isEnable) 
	{ 
		Class cmClass       = mCM.getClass(); 
		Class[] argClasses  = new Class[1]; 
		argClasses[0]       = boolean.class; 
		
		try 
		{ 
			Method method = cmClass.getMethod(methodName, argClasses); 
			method.invoke(mCM, isEnable); 
		} catch (Exception e) 
		{ 
			e.printStackTrace(); 
		} 
	} 
//	 Class.getMethod   �Ǵ�framework����ָ���ķ�����  ���ص�Method�Ϳ���ʹ�ø÷���, �ڶ��������Ǹ÷����Ĳ������͡�
//	 Method.invoke  ʹ�ô�framework���������ķ���, �ڶ����ǲ�����
	
}
