android/frameworks/base/wifi/java/android/net/wifi/WifiManager.java

�޸ķ������£�
line 424 

	private boolean checkUpdateSSID(WifiConfiguration config , String strKey) // �ж��Ƿ�AEMM��Profile
    {
    	if(config.SSID.contains(strKey))
    	{
    		return true ; 
    	}
    	return false ; 
    }
line 451
		if( checkUpdateSSID(config,"aemm") ) // �����AEMM��Profile��ֱ�ӷ���ʧ��
        	return false ;
line 472 
	private boolean checkSSID(int netId , String strKey) // �ж��Ƿ�AEMM��Profile
    {
        List<WifiConfiguration> configs = this.getConfiguredNetworks();
        if (configs != null) {
            for (WifiConfiguration config : configs) 
            {
            	if( netId == config.networkId )
            	{
            		if(config.SSID.contains(strKey))
            		{
            			return true ; 
            		}
            	}
            }
        }
        return false ; 
    }
line 500
			if( !checkSSID(netId , "aemm") )
        		return mService.removeNetwork(netId);
        	else
        		return false ;  

���˼·�� 
        Wifi���漰�����õĵط���WifiSettings.java(forget�������õ�WifiManager.java�е�removeNetwork()������updateNetwork()����)
        WifiManager�Ѿ�����ϵͳ���ĵ��á�������������Ϊ��
        WifiSettings->WifiManager->WifiService->WifiStateTracker->WifiNative->android_net_wifi_Wifi.cpp
        ��WifiManager����������Ѿ����ԴﵽĿ�꣬��Ȼ�����²������Ҳ���ԣ����Ǵ��������Ͳ���WifiManager��Ĵ�����
