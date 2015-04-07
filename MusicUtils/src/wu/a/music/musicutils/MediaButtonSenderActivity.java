package wu.a.music.musicutils;

import wu.a.lib.media.PlateformUtils;
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
		case R.id.pre_btn:
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_MEDIA_PREVIOUS);
			break;
		case R.id.next_btn:
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_MEDIA_NEXT);
			break;
		case R.id.play_btn:
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_MEDIA_PLAY);
			break;
		case R.id.play_pause_btn:
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
			break;
		case R.id.headsethook_btn:
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_HEADSETHOOK);
			break;
		case R.id.pause_btn:
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_MEDIA_PAUSE);
			break;
		case R.id.stop_btn:
			PlateformUtils.sendActionMediaButton(this,KeyEvent.KEYCODE_MEDIA_STOP);
			break;
		}
	}
}
