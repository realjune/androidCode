package w.a.utils;

import java.util.List;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**android���ж����������Ƿ����
 * @author Administrator
 *
 */
public class NetWorkUtils {
	/**�ж����������Ƿ����
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {   
		ConnectivityManager cm = (ConnectivityManager) context   
				.getSystemService(Context.CONNECTIVITY_SERVICE);   
		if (cm == null) {   
		} else {
			//��������������ж���������
			//�����ʹ�� cm.getActiveNetworkInfo().isAvailable();  
			NetworkInfo[] info = cm.getAllNetworkInfo();   
			if (info != null) {   
				for (int i = 0; i < info.length; i++) {   
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
						return true;   
					}   
				}   
			}   
		}   
		return false;   
	}
	
	/**�ж�GPS�Ƿ��
	 * @param context
	 * @return
	 */
	public static boolean isGpsEnabled(Context context) {   
		LocationManager lm = ((LocationManager) context   
				.getSystemService(Context.LOCATION_SERVICE));   
		List<String> accessibleProviders = lm.getProviders(true);   
		return accessibleProviders != null && accessibleProviders.size() > 0;   
	}
	
	/**�ж�WIFI�Ƿ��
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context) {   
		ConnectivityManager mgrConn = (ConnectivityManager) context   
				.getSystemService(Context.CONNECTIVITY_SERVICE);   
		TelephonyManager mgrTel = (TelephonyManager) context   
				.getSystemService(Context.TELEPHONY_SERVICE);   
		return ((mgrConn.getActiveNetworkInfo() != null && mgrConn   
				.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel   
				.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);   
	}
	
	/**�ж��Ƿ���3G����
	 * @param context
	 * @return
	 */
	public static boolean is3rd(Context context) {   
		ConnectivityManager cm = (ConnectivityManager) context   
				.getSystemService(Context.CONNECTIVITY_SERVICE);   
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();   
		if (networkINfo != null   
				&& networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {   
			return true;   
		}   
		return false;   
	}  
	/**�ж���wifi����3g����,�û����������������ˣ�wifi�Ϳ��Խ������ػ������߲��š�
	 * @param context
	 * @return
	 */
	public static boolean isWifi(Context context) {   
		ConnectivityManager cm = (ConnectivityManager) context   
				.getSystemService(Context.CONNECTIVITY_SERVICE);   
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();   
		if (networkINfo != null   
				&& networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {   
			return true;   
		}   
		return false;   
	}
}
