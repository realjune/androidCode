package com.android.ring.devutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.android.log.CLog;

public class TeleUtils {
	private Context context;
	private TelephonyManager telephonyManager ;
	private PhoneStateListener mTeleListener;
	public TeleUtils(Context context,PhoneStateListener mTeleListener){
		this.context=context;
		telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		this.mTeleListener=mTeleListener;//=new TeleListener();
	}
	
	public void onStart(){
		telephonyManager.listen(mTeleListener, PhoneStateListener.LISTEN_CALL_STATE);  
	}
	
	public void onStop(){
		telephonyManager.listen(mTeleListener, PhoneStateListener.LISTEN_NONE); 
	}
	
	public static void deleteCall(Context context,long date){
		/* ��������Ϊ�� Activity �У����Կ���ֱ�ӵ��� getContentResolver()���������ʵ������ Context �ж���ġ� */
		ContentResolver resolver = context.getContentResolver();
		/* �����漰�������ṩ�ߵ�֪ʶ����ʵ������ֱ���ڲ��� Android �����ݿ⣬ʮ��ʹ�� */
//		Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, new String[]{"_id"}, "number=? and (type=1 or type=3)",  new String[]{num},  "_id desc limit 1");
		Cursor cursor = resolver.query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.DATE,CallLog.Calls.NUMBER,CallLog.Calls.TYPE,"_id"}, CallLog.Calls.DATE+">"+date,  null,  "_id desc limit 1");
		if(cursor.moveToFirst()) {

	        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			do{

				CLog.print("calllog", sf.format(cursor.getLong(0))+" "+cursor.getString(1)+ " "+cursor.getString(2));
			}while(cursor.moveToNext());
			resolver.delete(CallLog.Calls.CONTENT_URI, CallLog.Calls.DATE+">"+date , null);
			CLog.print("dele call", sf.format(date));
		}

	}
	class TeleListener extends PhoneStateListener{
		CLog cLog=new CLog(TeleListener.class.getSimpleName());
		public TeleListener(){
		}
		
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			super.onCallStateChanged(state, incomingNumber);
			
			switch (state) {
			// �����ڴ���״̬��
			case TelephonyManager.CALL_STATE_IDLE: {
				cLog.println("IDLE");
				break;
			}
			// ���������ڲ��ų�ȥ����������ͨ����
			case TelephonyManager.CALL_STATE_OFFHOOK: {
				cLog.println("OFFHOOK");

//				 Intent intent=new Intent();
//				 intent.setComponent(cn);
//				 CLog.print("Ring", cn.getPackageName()+"---"+cn.getClassName());
//				 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				 context.startActivity(intent);
				

				ActivityManager am1 = (ActivityManager) context.getSystemService(Activity.ACTIVITY_SERVICE);
				ComponentName cn1 = am1.getRunningTasks(2).get(1).topActivity;
				 CLog.print("tele", cn1.getPackageName()+"---"+cn1.getClassName());

			}
			// ���沦��������û�нӲ���״̬��..
			case TelephonyManager.CALL_STATE_RINGING: {
				cLog.println("RINGING");
				break;
			}
			default:
				cLog.println("default");
				break;
			}
		}
	}
	
	public static  void dial(Context context ,String number) {
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony",
                    (Class[]) null);
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Object iTelephony;
            iTelephony = (Object) getITelephonyMethod.invoke(tManager,(Object[]) null);
            Method dial = iTelephony.getClass().getDeclaredMethod("dial", String.class);
            dial.invoke(iTelephony, number);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
	public static void call(Context context,String number) {
        Class<TelephonyManager> c = TelephonyManager.class;
        Method getITelephonyMethod = null;
        try {
            getITelephonyMethod = c.getDeclaredMethod("getITelephony",
                    (Class[]) null);
            getITelephonyMethod.setAccessible(true);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Object iTelephony;
            iTelephony = (Object) getITelephonyMethod.invoke(tManager,(Object[]) null);
            Method dial = iTelephony.getClass().getDeclaredMethod("call", String.class);
            dial.invoke(iTelephony, number);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	public static void endCall(Context context){
//		Method method = Class.forName("android.os.ServiceManager")
//				 
//				  .getMethod("getService",String.class);
//				 
//				IBinder binder = (IBinder)method.invoke(null, new Object[]{TELEPHONY_SERVICE});
//				 
//				ITelephony telephony = ITelephony.Stub.asInterface(binder);
//				 
//				telephony.endCall();

				
				
				
				TelephonyManager tManager = (TelephonyManager) 
						 context.getSystemService(Context.TELEPHONY_SERVICE);
						 //��ʼ��iTelephony
						 Class<TelephonyManager> c = TelephonyManager.class;
						 Method getITelephonyMethod = null;
						 try {
						 getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[])null);
						 getITelephonyMethod.setAccessible(true);
						 } catch (SecurityException e) {
						 // TODO Auto-generated catch block
						 e.printStackTrace();
						 } catch (NoSuchMethodException e) {
						 // TODO Auto-generated catch block
						 e.printStackTrace();
						 }
				ITelephony iTelephony;

			    try {

			     /*Method*/ getITelephonyMethod = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);

			     getITelephonyMethod.setAccessible(true);

			     iTelephony = (ITelephony) getITelephonyMethod.invoke(tManager, (Object[]) null);
//			     iTelephony = (ITelephony) getITelephonyMethod.invoke(tManager, (Object[])null);
			     iTelephony.endCall();

			      } catch (Exception e) {

			       e.printStackTrace();

			       System.out.println(e.getMessage());

			      }
	}
	
	



}
