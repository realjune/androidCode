package wu.a.lib.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *
 */
public class NetUtil {
	
	/**是否联网
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo != null) {
			return networkInfo.isAvailable() && networkInfo.isConnected();
		} else {
			return false;
		}
	}
	public static int getAvailableNetWorkType(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if(networkInfo != null&&networkInfo.isAvailable() && networkInfo.isConnected()) {
			return networkInfo.getType();
		}
		return -1;
	}
	/**
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = mgrConn.getActiveNetworkInfo();
		return ((networkINfo != null && networkINfo.getState() == NetworkInfo.State.CONNECTED) && networkINfo
				.getType() == ConnectivityManager.TYPE_WIFI);
	}
	public static boolean isMobileNetWorkConnected(Context context) {
		ConnectivityManager mgrConn = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = mgrConn.getActiveNetworkInfo();
		return ((networkINfo != null && networkINfo.getState() == NetworkInfo.State.CONNECTED) && networkINfo
				.getType() == ConnectivityManager.TYPE_MOBILE);
	}
}
