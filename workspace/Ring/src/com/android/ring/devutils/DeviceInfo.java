package com.android.ring.devutils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;

import com.android.log.CLog;

public class DeviceInfo {
	CLog clog=new CLog(DeviceInfo.class.getSimpleName());
	private Context context;
	private TelephonyManager tm;
	public DeviceInfo(Context context){
		this.context=context;
		tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
	}
	
	public String getPhoneNumber(){
		return tm.getLine1Number();
	}
	
	public String getIMEI(){
		return tm.getDeviceId();
	}
	
	public static String getPath(){
		String path=null;		 
		  //�ж��ֻ����Ƿ���SDCard�����ҿ��Խ��ж�д����  
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			path=Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		if(path==null){
			 File udiskFile1=new File("/udisk");  
			 if(udiskFile1.exists()){
				 path=udiskFile1.getAbsolutePath();
			 }else{
				 File udiskFile2=new File("/sdcard2");  
				 if(udiskFile2.exists()){
					 path=udiskFile2.getAbsolutePath();
				 }
			 }

		}
		if(path==null){
			 File udiskFile1=new File("/");  
			 if(udiskFile1.exists()){
				 File[]files=udiskFile1.listFiles();
				 for(File f:files){
					 if(f.isDirectory()&&f.exists()&&f.canWrite()){
						 path=f.getAbsolutePath();
						 break;
					 }
				 }
			 }
		}
		
		return path;
	}
	
//	CLog.print("getExternalStorageState", Environment.getExternalStorageState());
//	CLog.print("getDataDirectory����Ŀ¼", Environment.getDataDirectory().getAbsolutePath());
//	CLog.print("getDownloadCacheDirectory����/��������Ŀ¼", Environment.getDownloadCacheDirectory().getAbsolutePath());
//	CLog.print("getExternalStorageDirectorySDCard", Environment.getExternalStorageDirectory().getAbsolutePath());
//	CLog.print("getExternalStoragePublicDirectory���õ��ⲿ�洢��Ŀ¼", Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
//	CLog.print("getRootDirectory��Ŀ¼", Environment.getRootDirectory().getAbsolutePath());
	
	public void getInfo(Context context){     
		
		/*   
		 * �绰״̬��   
		 * 1.tm.CALL_STATE_IDLE=0          �޻   
		 * 2.tm.CALL_STATE_RINGING=1  ����   
		 * 3.tm.CALL_STATE_OFFHOOK=2  ժ��   
		 */    
		tm.getCallState();//int     
		
		/*   
		 * �绰��λ��   
		 *    
		 */    
		tm.getCellLocation();//CellLocation     
		
		/*   
		 * Ψһ���豸ID��   
		 * GSM�ֻ��� IMEI �� CDMA�ֻ��� MEID.    
		 * Return null if device ID is not available.   
		 */    
		tm.getDeviceId();//String     
		clog.println("imei:"+tm.getDeviceId());
		clog.println("imei:"+tm.getLine1Number());
		
		/*   
		 * �豸������汾�ţ�   
		 * ���磺the IMEI/SV(software version) for GSM phones.   
		 * Return null if the software version is not available.    
		 */    
		tm.getDeviceSoftwareVersion();//String     
		
		/*   
		 * �ֻ��ţ�   
		 * GSM�ֻ��� MSISDN.   
		 * Return null if it is unavailable.    
		 */    
		tm.getLine1Number();//String     
		
		/*   
		 * �����ĵ绰����Ϣ:   
		 * ���ͣ�List<NeighboringCellInfo>    
		 * ��ҪȨ�ޣ�android.Manifest.permission#ACCESS_COARSE_UPDATES   
		 */    
		tm.getNeighboringCellInfo();//List<NeighboringCellInfo>     
		
		/*   
		 * ��ȡISO��׼�Ĺ����룬�����ʳ�;���š�   
		 * ע�⣺�����û���������ע�����Ч��   
		 *       ��CDMA�����н��Ҳ���ɿ���   
		 */    
		tm.getNetworkCountryIso();//String     
		
		/*   
		 * MCC+MNC(mobile country code + mobile network code)   
		 * ע�⣺�����û���������ע��ʱ��Ч��   
		 *    ��CDMA�����н��Ҳ���ɿ���   
		 */    
		tm.getNetworkOperator();//String     
		
		/*   
		 * ������ĸ�����current registered operator(��ǰ��ע����û�)������   
		 * ע�⣺�����û���������ע��ʱ��Ч��   
		 *    ��CDMA�����н��Ҳ���ɿ���   
		 */    
		tm.getNetworkOperatorName();//String     
		
		/*   
		 * ��ǰʹ�õ��������ͣ�   
		 * ���磺 NETWORK_TYPE_UNKNOWN  ��������δ֪  0   
	      NETWORK_TYPE_GPRS     GPRS����  1   
	      NETWORK_TYPE_EDGE     EDGE����  2   
	      NETWORK_TYPE_UMTS     UMTS����  3   
	      NETWORK_TYPE_HSDPA    HSDPA����  8    
	      NETWORK_TYPE_HSUPA    HSUPA����  9   
	      NETWORK_TYPE_HSPA     HSPA����  10   
	      NETWORK_TYPE_CDMA     CDMA����,IS95A �� IS95B.  4   
	      NETWORK_TYPE_EVDO_0   EVDO����, revision 0.  5   
	      NETWORK_TYPE_EVDO_A   EVDO����, revision A.  6   
	      NETWORK_TYPE_1xRTT    1xRTT����  7   
		 */    
		tm.getNetworkType();//int     
		
		/*   
		 * �ֻ����ͣ�   
		 * ���磺 PHONE_TYPE_NONE  ���ź�   
	      PHONE_TYPE_GSM   GSM�ź�   
	      PHONE_TYPE_CDMA  CDMA�ź�   
		 */    
		tm.getPhoneType();//int     
		
		/*   
		 * Returns the ISO country code equivalent for the SIM provider's country code.   
		 * ��ȡISO�����룬�൱���ṩSIM���Ĺ����롣   
		 *    
		 */    
		tm.getSimCountryIso();//String     
		
		/*   
		 * Returns the MCC+MNC (mobile country code + mobile network code) of the provider of the SIM. 5 or 6 decimal digits.   
		 * ��ȡSIM���ṩ���ƶ���������ƶ�������.5��6λ��ʮ��������.   
		 * SIM����״̬������ SIM_STATE_READY(ʹ��getSimState()�ж�).   
		 */    
		tm.getSimOperator();//String     
		
		/*   
		 * ���������ƣ�   
		 * ���磺�й��ƶ�����ͨ   
		 * SIM����״̬������ SIM_STATE_READY(ʹ��getSimState()�ж�).   
		 */    
		tm.getSimOperatorName();//String     
		
		/*   
		 * SIM�������кţ�   
		 * ��ҪȨ�ޣ�READ_PHONE_STATE   
		 */    
		tm.getSimSerialNumber();//String     
		
		/*   
		 * SIM��״̬��Ϣ��   
		 *  SIM_STATE_UNKNOWN          δ֪״̬ 0   
	    SIM_STATE_ABSENT           û�忨 1   
	    SIM_STATE_PIN_REQUIRED     ����״̬����Ҫ�û���PIN����� 2   
	    SIM_STATE_PUK_REQUIRED     ����״̬����Ҫ�û���PUK����� 3   
	    SIM_STATE_NETWORK_LOCKED   ����״̬����Ҫ�����PIN����� 4   
	    SIM_STATE_READY            ����״̬ 5   
		 */    
		tm.getSimState();//int     
		
		/*   
		 * Ψһ���û�ID��   
		 * ���磺IMSI(�����ƶ��û�ʶ����) for a GSM phone.   
		 * ��ҪȨ�ޣ�READ_PHONE_STATE   
		 */    
		tm.getSubscriberId();//String     
		
		/*   
		 * ȡ�ú������ʼ���صı�ǩ����Ϊʶ���   
		 * ��ҪȨ�ޣ�READ_PHONE_STATE   
		 */    
		tm.getVoiceMailAlphaTag();//String     
		
		/*   
		 * ��ȡ�����ʼ����룺   
		 * ��ҪȨ�ޣ�READ_PHONE_STATE   
		 */    
		tm.getVoiceMailNumber();//String     
		
		/*   
		 * ICC���Ƿ����   
		 */    
		tm.hasIccCard();//boolean     
		
		/*   
		 * �Ƿ�����:   
		 * (��GSM��;��)   
		 */    
		tm.isNetworkRoaming();//     
		
		
		
	}     
	
	
} 

