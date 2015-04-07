package wu.a.template.media;

import wu.a.lib.media.PlateformUtils;
import wu.a.template.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class MediaButtonSenderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediabuttonsender_layout);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pre_btn:// act=com.netease.cloudmusic.TOGGLEPAUSE
							// flg=0x10000000
							// cmp=com.netease.cloudmusic/.service.PlayService
							// bnds=[489,1593][617,1679] (has extras) }}
			// Intent i=new Intent();
			// i.setAction("com.netease.cloudmusic.TOGGLEPAUSE");
			// i.setComponent(new
			// ComponentName("com.netease.cloudmusic","com.netease.cloudmusic.service.PlayService"));
			// startService(i);
//			Intent i;
//			KeyEvent ke;

//			i = new Intent();
//			i.setAction(Intent.ACTION_MEDIA_BUTTON);
//			ke = new KeyEvent(KeyEvent.ACTION_DOWN,
//					KeyEvent.KEYCODE_MEDIA_PREVIOUS);
//			i.putExtra(Intent.EXTRA_KEY_EVENT, ke);
//			sendBroadcast(i);
//			i = new Intent();
//			i.setAction(Intent.ACTION_MEDIA_BUTTON);
//			ke=new KeyEvent(System.currentTimeMillis()-100, System.currentTimeMillis(), KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS, 1);//, 0, 0, 0, 0, InputDevice.SOURCE_KEYBOARD);
//			ke = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
//			i.putExtra(Intent.EXTRA_KEY_EVENT, ke);
//			sendBroadcast(i);
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_HEADSETHOOK);
			break;
		case R.id.next_btn:
//			i = new Intent();
//			i.setAction(Intent.ACTION_MEDIA_BUTTON);
//			ke = new KeyEvent(KeyEvent.ACTION_DOWN,
//					KeyEvent.KEYCODE_MEDIA_NEXT);
//			i.putExtra(Intent.EXTRA_KEY_EVENT, ke);
//			sendBroadcast(i);
//			i = new Intent();
//			i.setAction(Intent.ACTION_MEDIA_BUTTON);
//			ke=new KeyEvent(System.currentTimeMillis()-100, System.currentTimeMillis(), KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT, 1);//, 0, 0, 0, 0, InputDevice.SOURCE_KEYBOARD);
//			ke = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_NEXT);
//			i.putExtra(Intent.EXTRA_KEY_EVENT, ke);
//			sendBroadcast(i);

			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_HEADSETHOOK);
			break;
		case R.id.play_btn:
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_HEADSETHOOK);
//			i = new Intent();
//			i.setAction(Intent.ACTION_MEDIA_BUTTON);
//			ke = new KeyEvent(KeyEvent.ACTION_DOWN,
//					KeyEvent.KEYCODE_HEADSETHOOK);
//			i.putExtra(Intent.EXTRA_KEY_EVENT, ke);
//			sendBroadcast(i);
//			i = new Intent();
//			i.setAction(Intent.ACTION_MEDIA_BUTTON);
//			ke=new KeyEvent(System.currentTimeMillis()-100, System.currentTimeMillis(), KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK, 1);//, 0, 0, 0, 0, InputDevice.SOURCE_KEYBOARD);
//			ke = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
//			i.putExtra(Intent.EXTRA_KEY_EVENT, ke);
//			sendBroadcast(i);
			break;
		}
	}
}
