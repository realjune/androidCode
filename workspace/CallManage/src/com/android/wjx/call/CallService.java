package com.android.wjx.call;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.IBinder;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.log.CLog;
import com.android.wx.math.DateFormater;

public class CallService extends Service {
	CLog clog=new CLog(CallService.class.getSimpleName());
	TelephonyManager telephonyManager;
	PhoneListener mPhoneListener;
	ContentResolver  mContentResolver;
	ContentObserver observer;
	private AudioManagerUtils mAudioManagerUtils;
	IntentUtils mIntentUtils;
	
	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();

		clog.println("onCreate");
		mAudioManagerUtils= new AudioManagerUtils(this);   
		
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		mPhoneListener=new PhoneListener();
		telephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);  //ע������� �����绰״̬
		
		mContentResolver=getContentResolver();
		observer=new phoneContentObserver(handler);
		mContentResolver.registerContentObserver(CallLog.Calls.CONTENT_URI, true, observer);
		mIntentUtils=new IntentUtils(this);
		
	}
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		clog.println("onStart");
		super.onStart(intent, startId);
	}
	@Override
	public void onDestroy() {
		clog.println("onDestroy");
		telephonyManager.listen(mPhoneListener, PhoneStateListener.LISTEN_NONE);  //ע������� �����绰״̬
		mContentResolver.unregisterContentObserver(observer);
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	/**
	 * <pre>
	 * ע�� {@link  PhoneStateListener.LISTEN_CALL_STATE}
	 * ע��{@link  PhoneStateListener.LISTEN_NONE}
	 * ע���ǻᴥ��һ��{@link TelephonyManager.CALL_STATE_IDLE} 
	 * ���Ż����{@link TelephonyManager.CALL_STATE_OFFHOOK}
	 * ��������{@link TelephonyManager.CALL_STATE_RINGING}
	 * �绰�Ҷ�{@link TelephonyManager.CALL_STATE_IDLE}
	 * incomingNumber ֻ������״̬ʱ��ֵ
	 * @author junxu.wang
	 *
	 */
	private final class PhoneListener extends PhoneStateListener
	{
//		private String incomeNumber;   //�������
		@Override
		public void onCallStateChanged(int state, String incomingNumber)
		{
				switch(state)
				{
				case TelephonyManager.CALL_STATE_RINGING:   //���� 
//					this.incomeNumber = incomingNumber;
					clog.println(DateFormater.format(System.currentTimeMillis(), DateFormater.defaultFomate)+" ���磨RINGING�� "+incomingNumber);
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:   //��ͨ�绰�򲦳��绰
					mAudioManagerUtils.silent();
					mIntentUtils.start();
					clog.println(DateFormater.format(System.currentTimeMillis(), DateFormater.defaultFomate)+" ��ͨ������OFFHOOK�� "+incomingNumber);
					break;
					
				case TelephonyManager.CALL_STATE_IDLE:  //�ҵ��绰��ע��
					mAudioManagerUtils.reset();
					if(mIntentUtils!=null){
						mIntentUtils.stop();
					}
					clog.println(DateFormater.format(System.currentTimeMillis(), DateFormater.defaultFomate)+" �ҵ绰��IDLE�� "+incomingNumber);
					break;
				}
		}
		
	}
	class phoneContentObserver extends ContentObserver{

		public phoneContentObserver(Handler handler) {
			super(handler);
			// TODO Auto-generated constructor stub
		}
		@Override
		public void onChange(boolean selfChange) {
			clog.println("onChange"+selfChange);
			/* �����漰�������ṩ�ߵ�֪ʶ����ʵ������ֱ���ڲ��� Android �����ݿ⣬ʮ��ʹ�� */
			Cursor cursor = mContentResolver.query(CallLog.Calls.CONTENT_URI,
					new String[]{CallLog.Calls._ID,
					CallLog.Calls.DATE,
					CallLog.Calls.NUMBER,
					CallLog.Calls.TYPE,
					CallLog.Calls.DURATION,
					CallLog.Calls.CACHED_NAME}
			, null/*"number=? and (type=1 or type=3)"*/, 
					null/*new String[]{"15101689022"}*/,
"_id desc limit 3");
			String callType[]=new String[]{"����","����","δ��"};
			if(cursor.moveToFirst()) {
				StringBuffer sb=new StringBuffer();
				int clumns=cursor.getColumnCount();
				for(int i=0;i<clumns;i++){
					sb.append(cursor.getColumnName(i));
					sb.append(" | ");
				}
				sb.append('\n');
				do{
					sb.append(cursor.getString(0));
					sb.append(" | ");
					sb.append(DateFormater.format(cursor.getLong(1), DateFormater.defaultFomate));
					sb.append(" | ");
					sb.append(cursor.getString(2));
					sb.append(" | ");
					int type=cursor.getInt(3);
					String typeStr;
					switch(type){
					case CallLog.Calls.INCOMING_TYPE:
						typeStr=callType[0];
						break;
					case CallLog.Calls.OUTGOING_TYPE:
						typeStr=callType[1];
						break;
					case CallLog.Calls.MISSED_TYPE:
						typeStr=callType[2];
						break;
						default:
							typeStr="δ֪����";
							break;
					}
					sb.append(typeStr);
					sb.append(" | ");
					long duration=cursor.getLong(4);
					sb.append(duration/60+"��"+duration%60+"��");
					sb.append(" | ");
					sb.append(cursor.getString(5));
					sb.append('\n');
				}while(cursor.moveToNext());
				clog.println(sb.toString());
//				int id = cursor.getInt(0);
//				mContentResolver.delete(CallLog.Calls.CONTENT_URI, "_id=?", new String[] {id + ""});
			}

			
			cursor = mContentResolver.query(CallLog.Calls.CONTENT_URI,
					new String[]{CallLog.Calls._ID,
					CallLog.Calls.DATE,
					CallLog.Calls.NUMBER,
					CallLog.Calls.TYPE,
					CallLog.Calls.DURATION,
					CallLog.Calls.CACHED_NAME}
			, "number=? and type=?", 
					new String[]{"13121581070",CallLog.Calls.OUTGOING_TYPE+""},
"_id desc limit 3");
			if(cursor.moveToFirst()){
				StringBuffer sb=new StringBuffer();
				sb.append(cursor.getString(0));
				sb.append(" | ");
				sb.append(DateFormater.format(cursor.getLong(1), DateFormater.defaultFomate));
				sb.append(" | ");
				sb.append(cursor.getString(2));
				sb.append(" | ");
				int type=cursor.getInt(3);
				String typeStr;
				switch(type){
				case CallLog.Calls.INCOMING_TYPE:
					typeStr=callType[0];
					break;
				case CallLog.Calls.OUTGOING_TYPE:
					typeStr=callType[1];
					break;
				case CallLog.Calls.MISSED_TYPE:
					typeStr=callType[2];
					break;
					default:
						typeStr="δ֪����";
						break;
				}
				sb.append(typeStr);
				sb.append(" | ");
				long duration=cursor.getLong(4);
				sb.append(duration/60+"��"+duration%60+"��");
				sb.append(" | ");
				sb.append(cursor.getString(5));
				sb.append('\n');
				
				clog.println("delt����> "+sb.toString());
				int id = cursor.getInt(0);
				
				mContentResolver.delete(CallLog.Calls.CONTENT_URI, "_id=?", new String[] {id + ""});
			}
			super.onChange(selfChange);
		}
		
	}
	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			clog.println("handleMessage"+msg.what);
		};
	};
	
	public static StringBuffer toStringBuffer(Cursor cursor){
		StringBuffer sb=new StringBuffer();
		if(cursor.moveToFirst()) {
			int clumns=cursor.getColumnCount();
			for(int i=0;i<clumns;i++){
				sb.append(cursor.getColumnName(i));
				sb.append(" | ");
			}
			sb.append('\n');
			do{
				for(int i=0;i<clumns;i++){
					sb.append(cursor.getString(i));
					sb.append(" | ");
				}
				sb.append('\n');
			}while(cursor.moveToNext());
		}
		return sb;
	}
	
}
