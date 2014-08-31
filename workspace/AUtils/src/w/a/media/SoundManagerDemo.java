package w.a.media;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManagerDemo {
	public void init(){
	   SoundPool soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);

       HashMap<Integer, Integer> soundPoolMap = new HashMap<Integer, Integer>();  

//       soundPoolMap.put(1, soundPool.load(this, R.raw.dingdong1, 1));        
//
//       soundPoolMap.put(2, soundPool.load(this, R.raw.dingdong2, 2));    
	}

       public void playSound(Context context,int sound, int loop) {

           AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);   

           float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);   

           float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);       

          float volume = streamVolumeCurrent/streamVolumeMax;   

//          soundPool.play(soundPoolMap.get(sound), volume, volume, 1, loop, 1f);

          //参数：1、Map中取值   2、当前音量     3、最大音量  4、优先级   5、重播次数   6、播放速度

} 

}
