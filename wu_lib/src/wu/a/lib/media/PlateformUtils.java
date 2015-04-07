package wu.a.lib.media;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;

public class PlateformUtils {

	/**
	 * <pre>
	 *
	 * @param context
	 * @param keyEvent KeyEvent.KEYCODE_MEDIA_PREVIOUS,KeyEvent.KEYCODE_MEDIA_NEXT,KeyEvent.KEYCODE_HEADSETHOOK,KeyEvent.KEYCODE_MEDIA_STOP,KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
	 * </pre>
	 */
	public static void sendActionMediaButton(Context context, int keyEvent) {
		Intent i;
		KeyEvent ke;

		// i = new Intent();
		// i.setAction(Intent.ACTION_MEDIA_BUTTON);
		// ke = new KeyEvent(KeyEvent.ACTION_DOWN,
		// KeyEvent.KEYCODE_MEDIA_PREVIOUS);
		// i.putExtra(Intent.EXTRA_KEY_EVENT, ke);
		// sendBroadcast(i);
		i = new Intent();
		i.setAction(Intent.ACTION_MEDIA_BUTTON);
		ke = new KeyEvent(System.currentTimeMillis() - 100,
				System.currentTimeMillis(), KeyEvent.ACTION_UP, keyEvent, 1);
		// ke = new KeyEvent(KeyEvent.ACTION_UP,
		// KeyEvent.KEYCODE_MEDIA_PREVIOUS);
		i.putExtra(Intent.EXTRA_KEY_EVENT, ke);
		context.sendBroadcast(i);
	}

}
