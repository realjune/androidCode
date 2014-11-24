package wu.a.lib.net;

import android.content.Context;
import android.telephony.TelephonyManager;

public class SimCard {

	private static TelephonyManager manager;

	public static boolean hasSimcard(Context context) {
		manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return manager.getSimState() == TelephonyManager.SIM_STATE_READY;
	}

}
