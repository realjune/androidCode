package wu.a.template.media;

import android.content.Context;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;

/**
 * Android AudioManager处理两个播放器同时有声音，停止其中一个播放的问题
 * 
 * 
 * 尽管某个时刻只有一个activity可以运行，Android却是一个多任务环境．这对使用音频的应用带来了特殊的挑战，
 * 因为只有一个音频输出而可能多个媒体都想用它．在Android2.2之前，没有内建的机制来处理这个问题，所以可能在某些情况下导致坏的用户体验．例如，
 * 当一个用户正在听音乐而另一个应用需要通知用户一些重要的事情时
 * ，用户可能由于音乐声音大而不能听的通知．从Android2.2开始，平台为应用提供了一个协商它们如何使用设备音频输出的途径
 * ，这个机制叫做音频焦点，AudioManager。
 * 当你的应用需要输出像乐音和通知之类的音频时，你应该总是请求音频焦点．一旦应用具有了焦点，它就可以自由的使用音频输出
 * ．但它总是应该监听焦点的变化．如果被通知丢失焦点，它应该立即杀死声音或降低到静音水平(有一个标志表明应选择哪一个)并且仅当重新获得焦点后才恢复大声播放。
 * 
 * 
 * 参数focusChange告诉你音频焦点如何发生了变化，它可以是以上几种值(它们都是定义在AudioManager中的常量)：
 * 
 * •AUDIOFOCUS_GAIN:你已获得了音频焦点．
 * 
 * •AUDIOFOCUS_LOSS:你已经丢失了音频焦点比较长的时间了．你必须停止所有的音频播放．因为预料到你可能很长时间也不能再获音频焦点，
 * 所以这里是清理你的资源的好地方．比如，你必须释放MediaPlayer．
 * 
 * •AUDIOFOCUS_LOSS_TRANSIENT:你临时性的丢掉了音频焦点，很快就会重新获得．你必须停止所有的音频播放，但是可以保留你的资源，
 * 因为你可能很快就能重新获得焦点．
 * 
 * •AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:你临时性的丢掉了音频焦点，但是你被允许继续以低音量播放，而不是完全停止．
 * 
 * 当第三方播放器也使用了这个机制的话，当你的应用获取声音焦点之后，第三方播放器失去焦点，做了暂停处理，即会停止播放。
 * 这样就不会出现两个播放器同时播放音乐的情况了。而如果第三方播放器没有经过处理，不管有没有焦点都一直播放，那就没有办法了
 * 
 * 
 * @author Administrator
 *
 */
public class AudioFocusChangeListener {
	private static final String TAG = "AudioFocusChangeListener";

	// * 首先，注册一个焦点监听器OnAudioFocusChangeListener 。
	private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = null;
	private AudioManager mAudioMgr;
	private boolean isNeedPlay;// 是否临时暂停

	// Build.VERSION.SDK_INT表示当前SDK的版本，Build.VERSION_CODES.ECLAIR_MR1为SDK 7版本 ，
	// 因为AudioManager.OnAudioFocusChangeListener在SDK8版本开始才有。
	private void createAudioFocusChangeListener() {
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
			mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
				@Override
				public void onAudioFocusChange(int focusChange) {

					Log.d(TAG, "other audioFocus: " + focusChange);
					switch (focusChange) {
					case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
						// break;
					case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
						// break;
					case AudioManager.AUDIOFOCUS_LOSS:
						// 失去焦点之后的操作
						Log.d(TAG, "lose audioFocus");
						// isNeedPlay = isPlaying();//暂存当前播放状态，待恢复
						if (isNeedPlay) {
							// playOrPause();//如果正在播放则停止
						}
						break;
					case AudioManager.AUDIOFOCUS_GAIN:
						// 获得焦点之后的操作
						Log.d(TAG, "get audioFocus");
						// if (isNeedPlay && !isPlaying()) {//判断是否需要回复
						// playOrPause();//恢复播放
						// }
						// isNeedPlay = isPlaying();//
						break;
					default:
						Log.d(TAG, "unknown audioFocus");
						break;
					}
				}
			};
		}
	}

	/**要请求音频焦点，你必须从AudioManager mAudioMgr调用requestAudioFocus()
	 * @param context
	 */
	private void requestAudioFocus(Context context) {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1) {
			return;
		}
		if (mAudioMgr == null)
			mAudioMgr = (AudioManager) context
					.getSystemService(Context.AUDIO_SERVICE);
		if (mAudioMgr != null) {
			Log.i(TAG, "Request audio focus");
			int ret = mAudioMgr.requestAudioFocus(mAudioFocusChangeListener,
					AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
			if (ret != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
				Log.i(TAG, "request audio focus fail. " + ret);
			}
		}

	}

	/**
	 * 放弃焦点
	 */
	private void abandonAudioFocus() {
		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR_MR1) {
			return;
		}
		if (mAudioMgr != null) {
			Log.i(TAG, "Abandon audio focus");
			mAudioMgr.abandonAudioFocus(mAudioFocusChangeListener);
			mAudioMgr = null;
		}
	}

}
