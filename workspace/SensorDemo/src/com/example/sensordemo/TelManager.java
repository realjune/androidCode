package com.example.sensordemo;

import java.util.List;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
public class TelManager{
	private static TelephonyManager tm;
	public TelManager(Context context){
		if(tm==null){
			tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
	}
	
	public String getInfo(){
		StringBuilder sb=new StringBuilder();
		
		/*
		 * �绰״̬��
		 * 1.tm.CALL_STATE_IDLE=0            �޻
		 * 2.tm.CALL_STATE_RINGING=1     ����
		 * 3.tm.CALL_STATE_OFFHOOK=2  ժ��
		 */ 
//  tm.getCallState(); //int
		switch(tm.getCallState()){
		case TelephonyManager.CALL_STATE_IDLE:
			sb.append("�޻");
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			sb.append("����");
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			sb.append("ժ��");
			break;
		}  
		sb.append('\n');
		
		/*
		 * �ֻ����ͣ�
		 * ���磺 PHONE_TYPE_NONE  ���ź�
		 * PHONE_TYPE_GSM   GSM�ź�
		 * PHONE_TYPE_CDMA  CDMA�ź�
		 */ 
		int phoneType=tm.getPhoneType(); //int 
		if(phoneType==TelephonyManager.PHONE_TYPE_NONE){
			sb.append('\n');
			sb.append("���ź�");
		}
		
		
		/*
		 * �绰��λ��
		 */ 
		CellLocation mCellLocation=tm.getCellLocation(); //CellLocation 
		sb.append(getLastKnownLocation(phoneType,mCellLocation));
		sb.append('\n');
		
		/*
		 * Ψһ���豸ID��
		 * GSM�ֻ��� IMEI �� CDMA�ֻ��� MEID. 
		 * Return null if device ID is not available.
		 */ 
		String deviceId=tm.getDeviceId(); //String 
		sb.append("deviceId: "+deviceId);
		sb.append('\n');
		
		
		/*
		 * �豸������汾�ţ�
		 * ���磺the IMEI/SV(software version) for GSM phones.
		 * Return null if the software version is not available. 
		 */ 
		String deviceSoftwareVersion=tm.getDeviceSoftwareVersion(); //String 
		sb.append("deviceId: "+deviceSoftwareVersion);
		sb.append('\n');
		
		/*
		 * �ֻ��ţ�
		 * GSM�ֻ��� MSISDN.
		 * Return null if it is unavailable. 
		 */ 
		String line1Number=tm.getLine1Number(); //String 
		sb.append("line1Number: "+line1Number);
		sb.append('\n');
		
		/*
		 * �����ĵ绰����Ϣ:
		 * ���ͣ�List<NeighboringCellInfo> 
		 * ��ҪȨ�ޣ�android.Manifest.permission#ACCESS_COARSE_UPDATES
		 */
		List<NeighboringCellInfo> mNeighboringCellInfos=tm.getNeighboringCellInfo(); //List<NeighboringCellInfo> 
		for(NeighboringCellInfo mNeighboringCellInfo:mNeighboringCellInfos){
			sb.append("�����绰����Ϣ��");
			sb.append(mNeighboringCellInfo.toString());
			sb.append('\n');
		}
		
		
		/*
		 * ��ȡISO��׼�Ĺ����룬�����ʳ�;���š�
		 * ע�⣺�����û���������ע�����Ч��
		 *       ��CDMA�����н��Ҳ���ɿ���
		 */ 
		String mNetworkCountryIso=tm.getNetworkCountryIso(); //String 
		sb.append("CountryIso: "+mNetworkCountryIso);
		
		/*
		 * MCC+MNC(mobile country code + mobile network code)
		 * ע�⣺�����û���������ע��ʱ��Ч��
		 *    ��CDMA�����н��Ҳ���ɿ���
		 */ 
		String mNetworkOperator=tm.getNetworkOperator(); //String 
		sb.append("MCC+MNC: "+mNetworkOperator);
		
		/*
		 * ������ĸ�����current registered operator(��ǰ��ע����û�)������
		 * ע�⣺�����û���������ע��ʱ��Ч��
		 *    ��CDMA�����н��Ҳ���ɿ���
		 */ 
		String mNetworkOperatorName=tm.getNetworkOperatorName(); //String
		sb.append("etworkOperatorName: "+mNetworkOperatorName);
		
		/*
		 * ��ǰʹ�õ��������ͣ�
		 * ���磺 NETWORK_TYPE_UNKNOWN  ��������δ֪  0
		 * NETWORK_TYPE_GPRS     GPRS����  1
		 * NETWORK_TYPE_EDGE     EDGE����  2
		 * NETWORK_TYPE_UMTS     UMTS����  3
		 * NETWORK_TYPE_HSDPA    HSDPA����  8 
		 * NETWORK_TYPE_HSUPA    HSUPA����  9
		 * NETWORK_TYPE_HSPA     HSPA����  10
		 * NETWORK_TYPE_CDMA     CDMA����,IS95A �� IS95B.  4
		 * NETWORK_TYPE_EVDO_0   EVDO����, revision 0.  5
		 * NETWORK_TYPE_EVDO_A   EVDO����, revision A.  6
		 * NETWORK_TYPE_1xRTT    1xRTT����  7
		 */ 
		int mNetworkType=tm.getNetworkType(); //int
		sb.append("��������:");
		switch(mNetworkType){
		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			sb.append("��������δ֪ ");
			break;
		case TelephonyManager.NETWORK_TYPE_GPRS:
			sb.append("GPRS���� ");
			break;
		case TelephonyManager.NETWORK_TYPE_EDGE:
			sb.append("EDGE����");
			break;
		case TelephonyManager.NETWORK_TYPE_UMTS:
			sb.append("UMTS����");
			break;
		case TelephonyManager.NETWORK_TYPE_HSDPA:
			sb.append("HSDPA����");
			break;
		case TelephonyManager.NETWORK_TYPE_HSUPA:
			sb.append("HSUPA����");
			break;
		case TelephonyManager.NETWORK_TYPE_HSPA:
			sb.append("HSPA����");
			break;
		case TelephonyManager.NETWORK_TYPE_CDMA:
			sb.append("CDMA����,IS95A �� IS95B.");
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			sb.append("EVDO����, revision 0");
			break;
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			sb.append("EVDO����, revision A");
			break;
		case TelephonyManager.NETWORK_TYPE_1xRTT:
			sb.append("1xRTT����");
			break;
		}
		sb.append('\n');
		
		/*
		 * Returns the ISO country code equivalent for the SIM provider's country code.
		 * ��ȡISO�����룬�൱���ṩSIM���Ĺ����롣
		 */ 
		String mSimCountryIso=tm.getSimCountryIso(); //String 
		sb.append("SIM���Ĺ�����: "+mSimCountryIso);
		sb.append('\n');
		
		/*
		 * Returns the MCC+MNC (mobile country code + mobile network code) of the provider of the SIM. 5 or 6 decimal digits.
		 * ��ȡSIM���ṩ���ƶ���������ƶ�������.5��6λ��ʮ��������.
		 * SIM����״̬������ SIM_STATE_READY(ʹ��getSimState()�ж�).
		 */ 
		String mSimOperator=tm.getSimOperator(); //String 
		sb.append("mSimOperator: "+mSimOperator);
		sb.append('\n');
		
		/*
		 * ���������ƣ�
		 * ���磺�й��ƶ�����ͨ
		 * SIM����״̬������ SIM_STATE_READY(ʹ��getSimState()�ж�).
		 */ 
		String mSimOperatorName=tm.getSimOperatorName(); //String 
		sb.append("SimOperatorName: "+mSimOperatorName);
		sb.append('\n');
		
		/*
		 * SIM�������кţ�
		 * ��ҪȨ�ޣ�READ_PHONE_STATE
		 */ 
		String mSimSerialNumber=tm.getSimSerialNumber(); //String 
		sb.append("SimSerialNumber: "+mSimSerialNumber);
		sb.append('\n');
		
		/*
		 * SIM��״̬��Ϣ��
		 *  SIM_STATE_UNKNOWN             δ֪״̬ 0
		 *  SIM_STATE_ABSENT                 û�忨 1
		 *  SIM_STATE_PIN_REQUIRED     ����״̬����Ҫ�û���PIN����� 2
		 *  SIM_STATE_PUK_REQUIRED    ����״̬����Ҫ�û���PUK����� 3
		 *  SIM_STATE_NETWORK_LOCKED   ����״̬����Ҫ�����PIN����� 4
		 *  SIM_STATE_READY            ����״̬ 5
		 */ 
		int mSimState=tm.getSimState(); //int 
		sb.append("SIM��״̬:");
		switch(mSimState){
		case TelephonyManager.SIM_STATE_UNKNOWN:
			sb.append("δ֪״̬");
			break;
		case TelephonyManager.SIM_STATE_ABSENT:
			sb.append("û�忨");
			break;
		case TelephonyManager.SIM_STATE_PIN_REQUIRED:
			sb.append("����״̬����Ҫ�û���PIN�����");
			break;
		case TelephonyManager.SIM_STATE_PUK_REQUIRED:
			sb.append("����״̬����Ҫ�û���PUK�����");
			break;
		case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			sb.append("����״̬����Ҫ�����PIN�����");
			break;
		case TelephonyManager.SIM_STATE_READY:
			sb.append("����״̬");
			break;
			
		}
		sb.append('\n');
		
		/*
		 * Ψһ���û�ID��
		 * ���磺IMSI(�����ƶ��û�ʶ����) for a GSM phone.
		 * ��ҪȨ�ޣ�READ_PHONE_STATE
		 */ 
		String mSubscriberId=tm.getSubscriberId(); //String 
		sb.append("IMSI(�����ƶ��û�ʶ����): "+mSubscriberId);
		sb.append('\n');
		
		
		/*
		 * ȡ�ú������ʼ���صı�ǩ����Ϊʶ���
		 * ��ҪȨ�ޣ�READ_PHONE_STATE
		 */ 
		String mVoiceMailAlphaTag=tm.getVoiceMailAlphaTag(); //String
		sb.append("ʶ���: "+mVoiceMailAlphaTag);
		sb.append('\n'); 
		
		/*
		 * ��ȡ�����ʼ����룺
		 * ��ҪȨ�ޣ�READ_PHONE_STATE
		 */ 
		String mVoiceMailNumber=tm.getVoiceMailNumber(); //String 
		sb.append("�����ʼ�����: "+mVoiceMailNumber);
		sb.append('\n'); 
		
		/*
		 * ICC���Ƿ����
		 */ 
		boolean mIccCard=tm.hasIccCard(); //boolean 
		sb.append("ICC���Ƿ����: "+mIccCard);
		sb.append('\n');  
		
		/*
		 * �Ƿ�����:
		 * (��GSM��;��)
		 */ 
		boolean isNetworkRoaming=tm.isNetworkRoaming(); // boolean
		sb.append("isNetworkRoaming: "+isNetworkRoaming);
		sb.append('\n');  
		
		return sb.toString();
	}
	public String getLastKnownLocation(int mPhoneType,CellLocation mLocation) {  
		
		if (mLocation == null) return "{}";  
		
		String rtn ="";  
		
		rtn += "\"operator\":\"" + tm.getNetworkOperator() +"\"";  
		
		rtn += ",\"operatorName\":\"" + tm.getNetworkOperatorName() +"\"";  
		
		if (mPhoneType == TelephonyManager.PHONE_TYPE_GSM) {  
			
			GsmCellLocation gsm = (GsmCellLocation) mLocation;  
			
			rtn +=",\"type\":\"GSM\"";  
			
			rtn += ",\"cid\":" + gsm.getCid();  
			
			rtn += ",\"lac\":" + gsm.getLac();  
			
		} else if (mPhoneType == TelephonyManager.PHONE_TYPE_CDMA) {  
			
			CdmaCellLocation cdma = (CdmaCellLocation) mLocation;  
			
			rtn +=",\"type\":\"CDMA\"";  
			
			rtn += ",\"baseStationId\":" + cdma.getBaseStationId();  
			
			rtn += ",\"baseStationLatitude\":" + cdma.getBaseStationLatitude();  
			
			rtn += ",\"baseStationLongitude\":" + cdma.getBaseStationLongitude();  
			
			rtn += ",\"networkId\":" + cdma.getNetworkId();  
			
			rtn += ",\"systemId\":" + cdma.getSystemId();  
			
		}  
		
		return "{" + rtn +"}";  
		
	}  
	
}

