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

          //������1��Map��ȡֵ   2����ǰ����     3���������  4�����ȼ�   5���ز�����   6�������ٶ�

} 

}
