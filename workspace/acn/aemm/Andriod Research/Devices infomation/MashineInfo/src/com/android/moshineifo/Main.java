package com.android.moshineifo;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnClickListener;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

public class Main extends Activity {
	private TelephonyManager tm;
	private MyPhoneStateListener MyListener;
	private TextView battery_tv;
	private TextView signalStrength_tv;
	private TextView mashineInfo_tv;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		showInfo();
	}
	
	private void init(){
		battery_tv=(TextView) findViewById(R.id.battery_tv);
		signalStrength_tv=(TextView) findViewById(R.id.signalStrength_tv);
		mashineInfo_tv=(TextView) findViewById(R.id.mashineInfo_tv);
		tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		MyListener = new MyPhoneStateListener();
		tm.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
		registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
	}
	
	private void showInfo(){
		mashineInfo_tv.setText("MAC��"+getMac() +getBuildInfo() +"\n====\n" + getInfo() + "\n====\n"
				+ new SystemProperty().getInfo(this));
		Log.v("V", mashineInfo_tv.getText().toString());
	}

	@Override
	protected void onPause(){
		super.onPause();
		tm.listen(MyListener, PhoneStateListener.LISTEN_NONE);
	}

	/* Called when the application resumes */
	@Override
	protected void onResume(){
		super.onResume();
		tm.listen(MyListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
	}


	/** Start the PhoneState listener */
	private class MyPhoneStateListener extends PhoneStateListener{
		/*
		 * Get the Signal strength from the provider, each tiome there is an
		 * update
		 */
		@Override
		public void onSignalStrengthsChanged(SignalStrength signalStrength){

			super.onSignalStrengthsChanged(signalStrength);
			signalStrength_tv.setText("Go to Firstdroid!!! GSM Cinr = "
					+ String.valueOf(signalStrength.getGsmSignalStrength()));
			Log.v("V","Go to Firstdroid!!! GSM Cinr = "
					+ String.valueOf(signalStrength.getGsmSignalStrength()));
		}
	};/* End of private Class */
	
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
	    int intLevel = 0;
	    int intScale = 0;

	    public void onReceive(Context context, Intent intent) {
	        String action = intent.getAction();
	        /*
	        * �����׽����action��ACTION_BATTERY_CHANGED�� ������onBatteryInfoReceiver()
	        */
	        if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
	        	StringBuffer sb=new StringBuffer();
	        	
	            intLevel = intent.getIntExtra("level", 0);
	            intScale = intent.getIntExtra("scale", 100);
	            sb.append("���Level��"+intLevel).append("\nlevel ������"+intScale);

	            // ����¶�
	            sb.append("\nBattery��ǰ�¶�ΪT:" + intent.getIntExtra("temperature", 0));
	            // ��ط���
	            sb.append("\n��ǰ��ѹΪ:" + intent.getIntExtra("voltage", 0));
	           String BatteryStatus=null;
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
	            sb.append("\n״̬:" +BatteryStatus);
	            String BatteryStatus2=null;
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
	            sb.append("\n���:" +BatteryStatus2);
	            String BatteryTemp=null;
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
	            sb.append("\n��ȫ��:" +BatteryTemp);
	            battery_tv.setText(sb.toString());
	        }
	    }
	};
	
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

		sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());

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

		Log.v("DeviceInfo", sb.toString());
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
	
	protected void dialog(String info) {
		AlertDialog.Builder builder = new Builder(Main.this);
		builder.setMessage(info);

		builder.setTitle("��ʾ");

		builder.setPositiveButton("ȷ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

				Main.this.finish();
			}
		});

		builder.setNegativeButton("ȡ��", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
	
	private String getMac(){
		 //��ȡwifi����
	    WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	    //�ж�wifi�Ƿ���
	    if (!wifiManager.isWifiEnabled()) { 
	    	wifiManager.setWifiEnabled(true);   
	    }
	    if (!wifiManager.isWifiEnabled()) {
	    	return null;
	    }
	    WifiInfo wifiInfo = wifiManager.getConnectionInfo(); 
	    return wifiInfo.getMacAddress();
	}

}