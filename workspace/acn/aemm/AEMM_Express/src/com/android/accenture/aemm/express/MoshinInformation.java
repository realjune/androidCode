package com.android.accenture.aemm.express;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xmlpull.v1.XmlSerializer;

import com.android.accenture.aemm.express.updataservice.MessageNotification;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;


/**MoshinInformation m=new MoshinInformation(this);
		m.onStart();
		m.write();
		m.onDestroy();
 * @author junxu.wang
 *
 */
public class MoshinInformation {
	
	public static final String LOGCAT="moshinInformation";
		private TelephonyManager tm;
		private MyPhoneStateListener MyListener;
		private Context context;
		
		private int gsmSignalStrength;

	    int batteryLevel = 0;
	    int batteryScale = 0;
	    int batteryTemperature;
	    int batteryVoltage;
	    String BatteryStatus;
	    String BatteryStatus2;
        String BatteryTemp=null;
        
        private static MoshinInformation sInstance = null;
        
        protected MoshinInformation(Context context) {
			this.context=context;
        }
        
        public  static MoshinInformation getInstance(Context context) {
            if (sInstance == null) {
                sInstance = new MoshinInformation(context);
            }
            return sInstance;
        }

		public void onStart(){
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			MyListener = new MyPhoneStateListener();
			tm.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
			context.registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
			//tm.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		}

		public void onDestroy(){
			tm.listen(MyListener, PhoneStateListener.LISTEN_NONE);
		}


		/* Start the PhoneState listener */
		private class MyPhoneStateListener extends PhoneStateListener{
			/*
			 * Get the Signal strength from the provider, each tiome there is an
			 * update
			 */
			@Override
			public void onSignalStrengthsChanged(SignalStrength signalStrength){

				super.onSignalStrengthsChanged(signalStrength);
				gsmSignalStrength=signalStrength.getGsmSignalStrength();
				Log.v(LOGCAT,"gsmSignalStrength = "
						+ String.valueOf(signalStrength.getGsmSignalStrength()));
			}
		};/* End of private Class */
		
		private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {

		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        /*
		        * �����׽����action��ACTION_BATTERY_CHANGED�� ������onBatteryInfoReceiver()
		        */
		        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
		        	batteryLevel = intent.getIntExtra("level", 0);
		        	batteryScale = intent.getIntExtra("scale", 100);
		        	batteryTemperature=intent.getIntExtra("temperature", 0);
		        	batteryVoltage=intent.getIntExtra("voltage", 0);
		        	switch (intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN)) {
		        	case BatteryManager.BATTERY_STATUS_CHARGING:
		        		BatteryStatus = "���״̬";
		        		break;
		        	case BatteryManager.BATTERY_STATUS_DISCHARGING:
		        		BatteryStatus = "�ŵ�״̬";
		        		break;
		        	case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
		        		BatteryStatus = "δ���";
		        		break;
		        	case BatteryManager.BATTERY_STATUS_FULL:
		        		BatteryStatus = "������";
		        		break;
		        	case BatteryManager.BATTERY_STATUS_UNKNOWN:
		        		BatteryStatus = "δ֪��״̬";
		        		break;
		        	default:
		        		BatteryStatus="";
		        	}
		        	switch (intent.getIntExtra("plugged", BatteryManager.BATTERY_PLUGGED_AC)) {
		        	case BatteryManager.BATTERY_PLUGGED_AC:
		        		BatteryStatus2 = "AC���";
		        		break;
		        	case BatteryManager.BATTERY_PLUGGED_USB:
		        		BatteryStatus2 = "USB���";
		        		break;
		        	default:
		        		BatteryStatus2="";
		        	}
		        	
		        	switch (intent.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
		        	case BatteryManager.BATTERY_HEALTH_UNKNOWN:
		        		BatteryTemp = "δ֪����";
		        		break;
		        	case BatteryManager.BATTERY_HEALTH_GOOD:
		        		BatteryTemp = "״̬����";
		        		break;
		        	case BatteryManager.BATTERY_HEALTH_DEAD:
		        		BatteryTemp = "���û�е�";
		        		break;
		        	case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
		        		BatteryTemp = "��ص�ѹ����";
		        		break;
		        	case BatteryManager.BATTERY_HEALTH_OVERHEAT:
		        		BatteryTemp = "��ع���";
		        		break;
		        	default:
		        		BatteryTemp="";
		        	}
		        }
		    }
		};

		// Androidϵͳ��Ϣ����鿴����
		StringBuffer buffer = new StringBuffer();
		private String initProperty() {
			initProperty("java.vendor.url", "java.vendor.url");
			initProperty("java.class.path", "java.class.path");
			initProperty("user.home", "user.home");
			initProperty("java.class.version", "java.class.version");
			initProperty("os.version", "os.version");
			initProperty("java.vendor", "java.vendor");
			initProperty("user.dir", "user.dir");
			initProperty("user.timezone", "user.timezone");
			initProperty("path.separator", "path.separator");
			initProperty(" os.name", " os.name");
			initProperty("os.arch", "os.arch");
			initProperty("line.separator", "line.separator");
			initProperty("file.separator", "file.separator");
			initProperty("user.name", "user.name");
			initProperty("java.version", "java.version");

			initProperty("java.home", "java.home");
			return buffer.toString();
		}

		private void initProperty(String description, String propertyStr) {
			if (buffer == null) {
				buffer = new StringBuffer();
			}
			buffer.append("\n" + description).append("=:");
			buffer.append(System.getProperty(propertyStr)).append(" ");
		}
		
		

		// Androidϵͳ��Ϣ���ڴ�����鿴
		private void getMemoryInfo(Context context) {
			final ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
			activityManager.getMemoryInfo(outInfo);
			buffer.append(" ʣ���ڴ�:").append(outInfo.availMem >> 10).append("k");
			buffer.append(" ʣ���ڴ�:").append(outInfo.availMem >> 20).append("M");
			buffer.append(" �Ƿ��ڵ��ڴ�״̬:").append(outInfo.lowMemory);
		}
		
		
		public String getBatteryInfo(){
			StringBuffer sb=new StringBuffer();
            sb.append("���Level��"+batteryLevel).append("\nlevel ������"+batteryScale);
            // ����¶�
            sb.append("\nBattery��ǰ�¶�ΪT:" + batteryTemperature);
            // ��ط���
            sb.append("\n��ǰ��ѹΪ:" + batteryVoltage);
            
            sb.append("\n״̬:" +BatteryStatus);
            sb.append("\n���:" +BatteryStatus2);
            sb.append("\n��ȫ��:" +BatteryTemp);
            return sb.toString();
		}
		
		
		/**
		 * @return <pre>
		 * ��Android�����ϻ�����Ϣ���ֻ�״̬Status�� 
		 * Settings-&gt;About Phone-&gt;Status 
		 * ������Ŀ�Ĺ��ܽ������£� 
		 * Battery status����س��/δ���״̬ 
		 * Battery level�����ʣ����� 
		 * Phone number���ֻ����к� 
		 * Network���������ƶ����� 
		 * Signal strength���źŶ� 
		 * Network type��������ʽ 
		 * Service state�����ڷ����� 
		 * Roaming������/δ���� 
		 * Data access�������ʵ����ݴ�С 
		 * IMEI��IMEI�� 
		 * IMEI SV��IMEI��İ汾 
		 * IMSI�������ƶ��û�ʶ���� 
		 * Wi-Fi  Mac address��G1����Wi-Fi�����Mac��ַ�� 
		 * Bluetooth address��������ַ 
		 * Up time����������ʱ�� 
		 * Awake Time���ֻ�����ʱ��
		 * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
		 * </pre>
		 */
		public String getInfo() {
			StringBuilder sb = new StringBuilder();

			sb.append("\nDeviceId(IMEI) = " + MoshinInformation.getDeviceId(context));

			sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());

			sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());

			sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());

			sb.append("\nLine1Number = " + tm.getLine1Number());

			sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());

			sb.append("\nPhoneType = " + tm.getPhoneType());

			sb.append("\nSimSerialNumber = " /*+ tm.getSimSerialNumber()*/);
			if (tm.getSimSerialNumber() != null) {
				sb.append("@" + tm.getSimSerialNumber().toString());
			} else {
				sb.append("@�޷�ȡ��SIM����");
			}

			sb.append("\nSimOperator = " /*+ tm.getSimOperator()*/);
			if (tm.getSimOperator().equals("")) {
				sb.append("@�޷�ȡ�ù����̴���");
			} else {
				sb.append("@" + tm.getSimOperator().toString());
			}

			sb.append("\nSimOperatorName = " /*+ tm.getSimOperatorName()*/);
			if (tm.getSimOperatorName().equals("")) {
				sb.append("@�޷�ȡ�ù�����");
			} else {
				sb.append("@" + tm.getSimOperatorName().toString());
			}

			sb.append("\nSimCountryIso = " /*+ tm.getSimCountryIso()*/);
			if (tm.getSimCountryIso().equals("")) {
				sb.append("@�޷�ȡ�ù���");
			} else {
				sb.append("@" + tm.getSimCountryIso().toString());
			}

			sb.append("\nNetworkOperator = " /*+ tm.getNetworkOperator()*/);
			if (tm.getNetworkOperator().equals("")) {
				sb.append("@�޷�ȡ��������Ӫ��");
			} else {
				sb.append("@" + tm.getNetworkOperator());
			}

			sb.append("\nNetworkOperatorName = " /*+ tm.getNetworkOperatorName()*/);
			if (tm.getNetworkOperatorName().equals("")) {
				sb.append("@�޷�ȡ��������Ӫ������");
			} else {
				sb.append("@" + tm.getNetworkOperatorName());
			}

			 sb.append("\nNetworkType = "/* + tm.getNetworkType()*/);
			sb.append("@");
			switch (tm.getNetworkType()) {
			/** Network type is unknown */
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:// = 0;
				sb.append("�޷�ȡ����������");
				break;
			/** Current network is GPRS */
			case TelephonyManager.NETWORK_TYPE_GPRS:// 1;
				sb.append("GPRS");
				break;
			/** Current network is EDGE */
			case TelephonyManager.NETWORK_TYPE_EDGE:// 2;
				sb.append("EDGE");
				break;
			/** Current network is UMTS */
			case TelephonyManager.NETWORK_TYPE_UMTS:// 3;
				sb.append("UMTS");
				break;
			/** Current network is CDMA: Either IS95A or IS95B */
			case TelephonyManager.NETWORK_TYPE_CDMA:// 4;
				sb.append("CDMA");
				break;
			/** Current network is EVDO revision 0 */
			case TelephonyManager.NETWORK_TYPE_EVDO_0:// 5;
				sb.append("EVDO_0");
				break;
			/** Current network is EVDO revision A */
			case TelephonyManager.NETWORK_TYPE_EVDO_A:// 6;
				sb.append("EVDO_A");
				break;
			/** Current network is 1xRTT */
			case TelephonyManager.NETWORK_TYPE_1xRTT:// 7;
				sb.append("1xRTT");
				break;
			/** Current network is HSDPA */
			case TelephonyManager.NETWORK_TYPE_HSDPA:// 8;
				sb.append("HSDPA");
				break;
			/** Current network is HSUPA */
			case TelephonyManager.NETWORK_TYPE_HSUPA:// 9;
				sb.append("HSUPA");
				break;
			/** Current network is HSPA */
			case TelephonyManager.NETWORK_TYPE_HSPA:// 10;
				sb.append("HSPA");
				break;
			/** Current network is iDen */
			case TelephonyManager.NETWORK_TYPE_IDEN:// 11
				sb.append("IDEN");
				break;
			default:
				sb.append(tm.getNetworkType());
			}

			sb.append("\nSimState = (" + tm.getSimState() + ")");
			switch (tm.getSimState()) {
			case TelephonyManager.SIM_STATE_ABSENT:
				sb.append("�޿�");
				break;
			case TelephonyManager.SIM_STATE_UNKNOWN:
				sb.append("δ֪״̬");
				break;
			case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
				sb.append("��ҪNetworkPIN����");
				break;
			case TelephonyManager.SIM_STATE_PIN_REQUIRED:
				sb.append("��ҪPIN����");
				break;
			case TelephonyManager.SIM_STATE_PUK_REQUIRED:
				sb.append("��ҪPUK����");
				break;
			case TelephonyManager.SIM_STATE_READY:
				sb.append("����");
				break;
			}

			Log.v(LOGCAT, sb.toString());
			return sb.toString();
		}

		public String getBuildInfo(){
			StringBuffer sb=new StringBuffer();
			sb.append("\n�ײ�������:"+Build.BOARD);
			sb.append("\n��ϵͳ��������İ汾��:"+Build.BOOTLOADER);
			sb.append("\n��ϵͳ��������İ汾��:"+Build.BRAND);
			sb.append("\n��ָ��ı��ش��루CPU����+ ABI�Ĺ�Լ��������:"+Build.CPU_ABI);
			sb.append("\n�ù�ҵ�������:"+Build.DEVICE);
			sb.append("\nDisplay id:"+Build.DISPLAY);
			/**һ���ַ�������Ψһ��ʶ�˰汾����Ҫ��ͼ�������ֵ*/
			sb.append("\nFINGERPRINT id:"+Build.FINGERPRINT);
			sb.append("\nӲ�������ƣ����ں������л�/������:"+Build.HARDWARE);
			sb.append("\nHOST:"+Build.HOST);
			sb.append("\nID:"+Build.ID);
			sb.append("\n������/Ӳ��:"+Build.MANUFACTURER);
			sb.append("\n�ֻ��ͺ�:"+Build.MODEL);
			sb.append("\nThe name of the overall product:"+Build.PRODUCT);
			sb.append("\nThe radio firmware version number:"+Build.RADIO);
			sb.append("\nComma-separated tags describing the build, like \"unsigned,debug\":"+Build.TAGS);
			sb.append("\nTime:"+Build.TIME);
			sb.append("\nThe type of build, like \"user\" or \"eng\":"+Build.TYPE);
			sb.append("\nUSER:"+Build.USER);
			sb.append("\nCODENAME:"+Build.VERSION.CODENAME);
			sb.append("\nINCREMENTAL:"+Build.VERSION.INCREMENTAL);
			sb.append("\nandroidϵͳ�汾��:"+Build.VERSION.RELEASE);
			sb.append("\nSDK��:"+Build.VERSION.SDK);
			sb.append("\nSDK_INT(SDK version of the framework):"+Build.VERSION.SDK_INT);
			
			return sb.toString();
		}

		private void writeProperty(Map<String, String>map){
			map.put("java.vendor.url",System.getProperty("java.vendor.url"));
			map.put("java.class.path",System.getProperty("java.class.path"));
			map.put("user.home",System.getProperty("user.home"));
			map.put("java.class.version",System.getProperty("java.class.version"));
			map.put("os.version",System.getProperty("os.version"));
			map.put("java.vendor",System.getProperty("java.vendor"));
			map.put("user.dir",System.getProperty("user.dir"));
			map.put("user.timezone",System.getProperty("user.timezone"));
			map.put("path.separator",System.getProperty("path.separator"));
			map.put("os.name",System.getProperty("os.name"));
			map.put("os.arch",System.getProperty("os.arch"));
			map.put("line.separator",System.getProperty("line.separator"));
			map.put("file.separator",System.getProperty("file.separator"));
			map.put("user.name",System.getProperty("user.name"));
			map.put("java.version",System.getProperty("java.version"));
		}
		
		private void writeMemory(Map<String, String>map){
			final ActivityManager activityManager = (ActivityManager) context
			.getSystemService(Context.ACTIVITY_SERVICE);
			
			ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
			activityManager.getMemoryInfo(outInfo);
			
			map.put("FreeMemory",String.valueOf(outInfo.availMem >> 10)+"k");
			map.put("FreeMemory",String.valueOf(outInfo.availMem >> 20)+"M");
			map.put("isLowMemoryState",""+ String.valueOf(outInfo.lowMemory));
		}
		
		private void writeBatary(Map<String, String>map){
			map.put("batteryLevel" , ""+batteryLevel);
			map.put("batteryScale" ,""+batteryScale);
			map.put("batteryTemperature" ,""+batteryTemperature);
			map.put("batteryVoltage" , ""+batteryVoltage);
			map.put("BatteryStatus" ,BatteryStatus);
			map.put("BatteryStatus2" , BatteryStatus2);
			map.put("BatteryTemp" , BatteryTemp);
		}
		private void writeTM(Map<String, String>map){
			map.put("DeviceId" , getDeviceId(context));
			map.put("DeviceSoftwareVersion" , tm.getDeviceSoftwareVersion());

			map.put("SubscriberId" , tm.getSubscriberId());

			map.put("VoiceMailNumber" , tm.getVoiceMailNumber());

			map.put("Line1Number" , tm.getLine1Number());

			map.put("NetworkCountryIso" , tm.getNetworkCountryIso());

			map.put("PhoneType" , ""+ String.valueOf(tm.getPhoneType()));

			map.put("SimSerialNumber" , tm.getSimSerialNumber());
//			if (tm.getSimSerialNumber() != null) {
//				map.put("@" , tm.getSimSerialNumber().toString());
//			} else {
//				map.put("@�޷�ȡ��SIM����");
//			}

			map.put("SimOperator" , tm.getSimOperator());
//			if (tm.getSimOperator().equals("")) {
//				map.put("@�޷�ȡ�ù����̴���");
//			} else {
//				map.put("@" , tm.getSimOperator().toString());
//			}

			map.put("SimOperatorName" , tm.getSimOperatorName());
//			if (tm.getSimOperatorName().equals("")) {
//				map.put("@�޷�ȡ�ù�����");
//			} else {
//				map.put("@" , tm.getSimOperatorName().toString());
//			}

			map.put("SimCountryIso" , tm.getSimCountryIso());
//			if (tm.getSimCountryIso().equals("")) {
//				map.put("@�޷�ȡ�ù���");
//			} else {
//				map.put("@" , tm.getSimCountryIso().toString());
//			}

			map.put("NetworkOperator" , tm.getNetworkOperator());
//			if (tm.getNetworkOperator().equals("")) {
//				map.put("@�޷�ȡ��������Ӫ��");
//			} else {
//				map.put("@" , tm.getNetworkOperator());
//			}

			map.put("NetworkOperatorName" , tm.getNetworkOperatorName());
//			if (tm.getNetworkOperatorName().equals("")) {
//				map.put("@�޷�ȡ��������Ӫ������");
//			} else {
//				map.put("@" , tm.getNetworkOperatorName());
//			}

//			map.put("NetworkType = " ,""+ tm.getNetworkType());
//			map.put("@");
			switch (tm.getNetworkType()) {
			/** Network type is unknown */
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:// = 0;
				map.put("NetworkType","�޷�ȡ����������");
				break;
			/** Current network is GPRS */
			case TelephonyManager.NETWORK_TYPE_GPRS:// 1;
				map.put("NetworkType","GPRS");
				break;
			/** Current network is EDGE */
			case TelephonyManager.NETWORK_TYPE_EDGE:// 2;
				map.put("NetworkType","EDGE");
				break;
			/** Current network is UMTS */
			case TelephonyManager.NETWORK_TYPE_UMTS:// 3;
				map.put("NetworkType","UMTS");
				break;
			/** Current network is CDMA: Either IS95A or IS95B */
			case TelephonyManager.NETWORK_TYPE_CDMA:// 4;
				map.put("NetworkType","CDMA");
				break;
			/** Current network is EVDO revision 0 */
			case TelephonyManager.NETWORK_TYPE_EVDO_0:// 5;
				map.put("NetworkType","EVDO_0");
				break;
			/** Current network is EVDO revision A */
			case TelephonyManager.NETWORK_TYPE_EVDO_A:// 6;
				map.put("NetworkType","EVDO_A");
				break;
			/** Current network is 1xRTT */
			case TelephonyManager.NETWORK_TYPE_1xRTT:// 7;
				map.put("NetworkType","1xRTT");
				break;
			/** Current network is HSDPA */
			case TelephonyManager.NETWORK_TYPE_HSDPA:// 8;
				map.put("NetworkType","HSDPA");
				break;
			/** Current network is HSUPA */
			case TelephonyManager.NETWORK_TYPE_HSUPA:// 9;
				map.put("NetworkType","HSUPA");
				break;
			/** Current network is HSPA */
			case TelephonyManager.NETWORK_TYPE_HSPA:// 10;
				map.put("NetworkType","HSPA");
				break;
			/** Current network is iDen */
			case TelephonyManager.NETWORK_TYPE_IDEN:// 11
				map.put("NetworkType","IDEN");
				break;
			default:
				map.put("NetworkType",""+ String.valueOf(tm.getNetworkType()));
			}

			map.put("SimState" ,""+ tm.getSimState());
			switch (tm.getSimState()) {
			case TelephonyManager.SIM_STATE_ABSENT:
				map.put("SimState" ,"�޿�");
				break;
			case TelephonyManager.SIM_STATE_UNKNOWN:
				map.put("SimState" ,"δ֪״̬");
				break;
			case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
				map.put("SimState" ,"��ҪNetworkPIN����");
				break;
			case TelephonyManager.SIM_STATE_PIN_REQUIRED:
				map.put("SimState" ,"��ҪPIN����");
				break;
			case TelephonyManager.SIM_STATE_PUK_REQUIRED:
				map.put("SimState" ,"��ҪPUK����");
				break;
			case TelephonyManager.SIM_STATE_READY:
				map.put("SimState" ,"����");
				break;
			}
		}
		private void writeBuild(Map<String, String>map){
			
			map.put( "board",Build.BOARD);
			map.put( "bootloader",Build.BOOTLOADER);
			map.put( /*"��ϵͳ��������İ汾��:"*/"brand",Build.BRAND);
			map.put( /*"��ָ��ı��ش��루CPU����+ ABI�Ĺ�Լ��������:"*/"CPU_ABI",Build.CPU_ABI);
			map.put( /*"�ù�ҵ�������:"*/"DEVICE",Build.DEVICE);
			map.put( "DISPLAY",Build.DISPLAY);
			/**һ���ַ�������Ψһ��ʶ�˰汾����Ҫ��ͼ�������ֵ*/
			map.put( "FINGERPRINT",Build.FINGERPRINT);
			map.put( "HARDWARE",Build.HARDWARE);
			map.put( "HOST",Build.HOST);
			map.put( "ID",Build.ID);
			map.put( /*"������/Ӳ��:"*/"MANUFACTURER",Build.MANUFACTURER);
			map.put( /*"�ֻ��ͺ�:"*/"MODEL",Build.MODEL);
			map.put( "PRODUCT",Build.PRODUCT);
			map.put( "RADIO",Build.RADIO);
			map.put( "TAGS",Build.TAGS);
			map.put( "TIME",""+ String.valueOf(Build.TIME)); //
			map.put( "TYPE",Build.TYPE);
			map.put( "USER",Build.USER);
			map.put( "CODENAME",Build.VERSION.CODENAME);
			map.put( "INCREMENTAL",Build.VERSION.INCREMENTAL);
			map.put( "RELEASE",Build.VERSION.RELEASE);
			map.put( "SDK",String.valueOf(Build.VERSION.SDK));
			map.put( "SDK_INT",""+String.valueOf(Build.VERSION.SDK_INT));
		}
				
		private void writeMac(Map<String, String>map){
			 //��ȡwifi����
		    WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
		    //�ж�wifi�Ƿ���
		    if (!wifiManager.isWifiEnabled()) { 
		    wifiManager.setWifiEnabled(true);   
		    }
		    if (!wifiManager.isWifiEnabled()) {
		    	return ;
		    }
		    WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 
		    map.put("mac",wifiInfo.getMacAddress());
		    //return wifiInfo.getMacAddress();
		}

		public Map<String, String> write() {
			Log.v(LOGCAT,"write");
			Map<String, String> map=new HashMap<String,String>();

//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder;
//			Document doc=null;
//			try {
//				builder = factory.newDocumentBuilder();
//				doc = builder.newDocument();
//				Node root = doc.createElement("ROOT");
//				XmlUtils.addNode(doc, root, "mac", getMac());
//				Node build_node=XmlUtils.addNode(doc, root, "hard",null);
			    Log.v(LOGCAT,"writeBuild");
				writeBuild(map);
				
//				Node hard_node=XmlUtils.addNode(doc, root, "system",null);
				Log.v(LOGCAT,"writeTM");
				writeTM(map);
				
//				Node batary_node=XmlUtils.addNode(doc, root, "batary",null);
				Log.v(LOGCAT,"writeBatary");
				writeBatary(map);
				
//				Node memory_node=XmlUtils.addNode(doc, root, "memory",null);
				Log.v(LOGCAT,"writeMemory");
				writeMemory(map);
				
//				Node property_node=XmlUtils.addNode(doc, root, "property",null);
				Log.v(LOGCAT,"writeProperty");
				writeProperty(map);
				
				Log.v(LOGCAT,"writeMac");
				writeMac(map);
				
//				doc.appendChild(root);
//				XmlUtils.writeToSDcard(doc,new File(file));
//				Log.v(LOGCAT,"write file");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			return map;
		}
		
		public static String getDeviceId(Context context){
			String imsi = null;
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = tm.getDeviceId();
			return imsi;
			
		}
}
