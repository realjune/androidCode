package com.android.test.wifi;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;

/**
 * @author abc
 * 
 * <pre>
 * ͨ�������鼮���ĵ�,��������, ��������һ����.
 * 
 * ��ʵ����WifiҲ�Ǻܼ򵥵ģ���Ҫʹ�����¼�������������
 * 
 * private WifiManager wifiManager;// �����������OpenWifi
 * private WifiInfo wifiInfo;// Wifi��Ϣ
 * private List&lt;ScanResult&gt; scanResultList; // ɨ����������������б�
 * private List&lt;WifiConfiguration&gt; wifiConfigList;// ���������б�
 * private WifiLock wifiLock;// Wifi��
 * 
 * 
 * ��Ȼ����Wifi������ģ�����н��У�����Ҫ�ŵ�����Wifi������Ͻ��У�
 * ���д���û�жԿ��ܴ��ڵĴ��������Ӧ�Ĳ����봦��ϣ���ο�������ע����һ�㣬
 * ����������ױ�ͻȻ�����Ĵ����󵼣�Ҳ�Ҳ������⣬���ڿ�����ʱ������������������⣡
 * ��˶Կ��ܴ��ڵ����⣬һ��Ҫ������Ӧ�Ĵ���
 * �����ǲ�����Щ����Ҫ��Ȩ�ޣ���Ȼ���ݲ��������ݲ�ͬ������Ȩ��Ҳ��ͬ��
 * �����Ȩ�޽����ο���
 * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
 * <uses-permission android:name="adnroid.permission.ACCESS_CHECKIN_PROPERTTES"></uses-permission>
 * <uses-permission android:name="android.permission.WAKE_LOCK"></uses-permission>
 * <uses-permission android:name="android.permission.INTERNET"></uses-permission>
 * <uses-permission android:name="adnroid.permission.CHANGE_WIFI_STATE"></uses-permission>
 * <uses-permission android:name="android.permission.MODIFY_PHONE_STATE"></uses-permission>
 * </pre>
 */
public class WifiManageWrap {
	// �����������
	WifiManager mWifiManager;
	// Wifi��Ϣ
	private WifiInfo mWifiInfo;
	// ɨ����������������б�
	private List<ScanResult> scanResultList;
	// ���������б�
	private List<WifiConfiguration> wifiConfigList;
	// Wifi��
	private WifiLock mWifiLock;
	
	public WifiManageWrap(Context context) {
		// ��ȡWifi����
		this.mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		// �õ�Wifi��Ϣ
		this.mWifiInfo = mWifiManager.getConnectionInfo();
	}

	/**WIfi״̬*/
	public boolean getWifiStatus() {
		return mWifiManager.isWifiEnabled();
	}

	/**��wifi*/
	public boolean openWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			return mWifiManager.setWifiEnabled(true);
		} else {
			return false;
		}
	}

	/**�ر� wifi*/
	public boolean closeWifi() {
		if (!mWifiManager.isWifiEnabled()) {
			return true;
		} else {
			return mWifiManager.setWifiEnabled(false);
		}
	}

	// ��ʵ����WiFI�����ж�wifi�Ƿ����ɹ���������ʹ�õ���held�����ֵ���˼acquire �õ���
	/**����wifi*/
	public void lockWifi() {
		mWifiLock.acquire();
	}
	
	/**����wifi*/
	public void unLockWifi() {
		if (!mWifiLock.isHeld()) {
			// �ͷ���Դ
			mWifiLock.release();
		}
	}

	/**Wifi ��*/
	public void createWifiLock() {
		// ����һ�����ı�־
		mWifiLock = mWifiManager.createWifiLock("flyfly");
	}

	/**ɨ������*/
	public void startScan() {
		mWifiManager.startScan();
		// ɨ�践�ؽ���б�
		scanResultList = mWifiManager.getScanResults();
		// ɨ�������б�
		wifiConfigList = mWifiManager.getConfiguredNetworks();
	}

	/**ScanResult List*/
	public List<ScanResult> getWifiList() {
		return scanResultList;
	}

	/**WifiConfiguration list*/
	public List<WifiConfiguration> getWifiConfigList() {
		return wifiConfigList;
	}

	/**��ȡɨ���б�*/
	public StringBuilder lookUpscan() {
		StringBuilder scanBuilder = new StringBuilder();
		for (int i = 0; i < scanResultList.size(); i++) {
			scanBuilder.append("��ţ�" + (i + 1));
			// ������Ϣ
			scanBuilder.append(scanResultList.get(i).toString());
			scanBuilder.append("\n");
		}
		return scanBuilder;
	}

	/**��ȡָ���źŵ�ǿ��*/
	public int getLevel(int NetId) {
		return scanResultList.get(NetId).level;
	}

	/**��ȡ����Mac��ַ*/
	public String getMac() {
		return (mWifiInfo == null) ? "" : mWifiInfo.getMacAddress();
	}

	/**��ȡBSSID*/
	public String getBSSID() {
		return (mWifiInfo == null) ? null : mWifiInfo.getBSSID();
	}

	public String getSSID() {
		return (mWifiInfo == null) ? null : mWifiInfo.getSSID();
	}

	/**���ص�ǰ���ӵ������ID*/
	public int getCurrentNetId() {
		return (mWifiInfo == null) ? null : mWifiInfo.getNetworkId();
	}

	/**����WifiInfo��Ϣ*/
	public String getwifiInfo() {
		return (mWifiInfo == null) ? null : mWifiInfo.toString();
	}

	/**��ȡIP��ַ*/
	public int getIP() {
		return (mWifiInfo == null) ? null : mWifiInfo.getIpAddress();
	}

	/**���һ������*/
	public boolean addNetWordLink(WifiConfiguration config) {
		Log.v("V","start add WifiConfiguration");
		int NetId = mWifiManager.addNetwork(config);
		Log.v("V","start save WifiConfiguration.."+NetId);
		return mWifiManager.saveConfiguration();
//		return wifiManager.enableNetwork(NetId, true);
	}

	/**����һ������*/
	public boolean disableNetWordLick(int NetId) {
		mWifiManager.disableNetwork(NetId);
		return mWifiManager.disconnect();
	}

	/**�Ƴ�һ������*/
	public boolean removeNetworkLink(int NetId) {
		return mWifiManager.removeNetwork(NetId);
	}

	/**����ʾSSID*/
	public void hiddenSSID(int NetId) {
		wifiConfigList.get(NetId).hiddenSSID = true;
	}

	/**��ʾSSID*/
	public void displaySSID(int NetId) {
		wifiConfigList.get(NetId).hiddenSSID = false;
	}
	
	/**wifiConfigList��Ϣ*/
	public String getWifiConfigListInfo(){
		wifiConfigList = mWifiManager.getConfiguredNetworks();
		String info="wifiConfigList��\n";
		int i=0;
		for(WifiConfiguration wifiConfig:wifiConfigList){
			info+=i+"_"+wifiConfig.toString()+"\n";
		}
		return info;
	}
	
	/**wifiConfigList ȥָ����WifiConfiguration*/
	public WifiConfiguration getWifiConfig(String ssid){
		WifiConfiguration reWifiConfiguration=null;
		wifiConfigList = mWifiManager.getConfiguredNetworks();
		if(wifiConfigList!=null){
			for(WifiConfiguration conf:wifiConfigList){
				if(conf.SSID.equals("\""+ssid+"\"")){
					reWifiConfiguration=conf;
					break;
				}
			}
		}
		return reWifiConfiguration;
	}
	
	/**WifiConfiguration����*/
	public static WifiConfiguration copyWifiConfiguration(WifiConfiguration srcWifiConfiguration){
		WifiConfiguration newWifiConfiguration=new WifiConfiguration();
		if(srcWifiConfiguration!=null){
			newWifiConfiguration.SSID="new"+srcWifiConfiguration.SSID;
			newWifiConfiguration.preSharedKey=srcWifiConfiguration.preSharedKey;
			newWifiConfiguration.hiddenSSID=srcWifiConfiguration.hiddenSSID;
			newWifiConfiguration.networkId=srcWifiConfiguration.networkId;
			newWifiConfiguration.priority=srcWifiConfiguration.priority;
			newWifiConfiguration.BSSID=srcWifiConfiguration.BSSID;
			newWifiConfiguration.status=srcWifiConfiguration.status;
			newWifiConfiguration.wepKeys=srcWifiConfiguration.wepKeys;
			newWifiConfiguration.wepTxKeyIndex=srcWifiConfiguration.wepTxKeyIndex;
			
			newWifiConfiguration.allowedAuthAlgorithms=srcWifiConfiguration.allowedAuthAlgorithms;
			newWifiConfiguration.allowedGroupCiphers=srcWifiConfiguration.allowedGroupCiphers;
			newWifiConfiguration.allowedKeyManagement=srcWifiConfiguration.allowedKeyManagement;
			newWifiConfiguration.allowedPairwiseCiphers=srcWifiConfiguration.allowedPairwiseCiphers;
			newWifiConfiguration.allowedProtocols=srcWifiConfiguration.allowedProtocols;
		}
		return newWifiConfiguration;
	}
}
