package w.a.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

public class SimCard {

	private static TelephonyManager manager;

	public static boolean hasSimcard(Context context) {
		boolean result = false;
		StringBuilder sb = new StringBuilder();
		manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		sb.append("SIM��״̬");
		switch (manager.getSimState()) {
		case TelephonyManager.SIM_STATE_READY:
			sb.append("����");
			sb.append('\n');
			result = true;
			break;
		case TelephonyManager.SIM_STATE_ABSENT:
			sb.append("��SIM��");
			sb.append('\n');
			break;
		default:
			sb.append("SIM����������δ֪״̬");
			sb.append('\n');
			break;
		}
		Log.d("MainActivity", sb.toString());
		return result;
	}

}
