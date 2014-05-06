package com.android.ring.devutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.log.CLog;
/*
 * https://developer.android.com/guide/appendix/media-formats.html
Android��ý���ܰ�����ȡ�ͱ��������Ƶ��ʽ��֧�֣�������������ɵذ���Ƶ�ϲ������Ӧ���У�����豸֧�֣������ʹ��MediaRecorder APIs ����¼����
 
��������չʾ���дһ��Ӧ�ô��豸�ϵ�microphone��ȡ��Ƶ��Ȼ�󱣴沢�طţ�
 
ע��Androidģ����������¼����������������ʵ���豸һ�㶼���д˹��ܣ�
 
 
 
ִ����Ƶ��ȡ
 ���豸��ȡ��Ƶ�Ȼط���Ƶ����ƵҪ����һ�㣬����Ҳ����򵥣�
 

����һ��android.media.MediaRecorder����ʵ����
 ʹ��MediaRecorder.setAudioSource()������ƵԴ��һ��Ҫʹ��MediaRecorder.AudioSource.MIC.
 ʹ��MediaRecorder.setOutputFormat()��������ļ��ĸ�ʽ��
 ʹ��MediaRecorder.setOutputFile()��������ļ������֣�
 ʹ��MediaRecorder.setAudioEncoder()������Ƶ���룮
 ����MediaRecorder ʵ����MediaRecorder.prepare()��
 MediaRecorder.start()��ʼ��ȡ��Ƶ��
 ����MediaRecorder.stop().ֹͣ��
 ��������MediaRecorderʵ���󣬵���MediaRecorder.release()���ͻ������ͷ���Դ��

 * The application needs to have the permission to write to external storage

 * if the output file is written to the external storage, and also the

 * permission to record audio. These permissions must be set in the

 * application's AndroidManifest.xml file, with something like:

 *

 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

 * <uses-permission android:name="android.permission.RECORD_AUDIO" />

 *

 */
public class AudioRecordUtils {
	private static final String LOG_TAG = "AudioRecordTest";
	CLog cLog=new CLog(LOG_TAG);
	MediaRecorderUtils mediaRecorderUtils=new MediaRecorderUtils();
	MediaPlayerUtils mediaPlayerUtils=new MediaPlayerUtils();
	
	private List<String> mFileNames = new ArrayList<String>();
	private String path;
	private String suffix=".3gp";
	public AudioRecordUtils(){
		mediaRecorderUtils.onCreate();
		mediaPlayerUtils.onCreate();
		mediaPlayerUtils.setOnCompletionListener(onCompletionListener);


		path = Environment.getExternalStorageDirectory().getAbsolutePath();
		
		
		{
			mFileNames.add("/storage/sdcard0/1392540598987.3gp");
			mFileNames.add("/storage/sdcard0/1392540603987.3gp");
			mFileNames.add("/storage/sdcard0/1392540608987.3gp");
			mFileNames.add("/storage/sdcard0/1392540613988.3gp");
			mFileNames.add("/storage/sdcard0/1392540618988.3gp");
			mFileNames.add("/storage/sdcard0/1392540623988.3gp");
			mFileNames.add("/storage/sdcard0/1392540628989.3gp");
			mFileNames.add("/storage/sdcard0/1392540633990.3gp");
			mFileNames.add("/storage/sdcard0/1392540638990.3gp");
			mFileNames.add("/storage/sdcard0/1392540643990.3gp");
			mFileNames.add("/storage/sdcard0/1392540648991.3gp");
			mFileNames.add("/storage/sdcard0/1392540653991.3gp");

		}

	}
	
	
	
	class RecordButton extends Button {
		
		boolean mStartRecording = true;
		
		
		OnClickListener clicker = new OnClickListener() {
			
			public void onClick(View v) {
				
				if(mStartRecording){
					startTask();
//					mediaRecorderUtils.onStart(outFile);
					setText("Stop recording");
				}else{
//					mediaRecorderUtils.onStop();
					setText("Start recording");
				}
				
				mStartRecording = !mStartRecording;
				
			}
			
		};
		
		
		public RecordButton(Context ctx) {
			
			super(ctx);
			
			setText("Start recording");
			
			setOnClickListener(clicker);
			
		}
		
	}
	
	
	class PlayButton extends Button {
		
		boolean mStartPlaying = true;
		
		
		OnClickListener clicker = new OnClickListener() {
			
			public void onClick(View v) {
				
				if (mStartPlaying) {
					play();
					
//					mediaPlayerUtils.onStart(fileName);
					setText("Stop playing");
					
				} else {
					
//					mediaPlayerUtils.onStop();
					setText("Start playing");
					
				}
				
				mStartPlaying = !mStartPlaying;
				
			}
			
		};
		
		
		public PlayButton(Context ctx) {
			
			super(ctx);
			
			setText("Start playing");
			
			setOnClickListener(clicker);
			
		}
		
	}
	
	
	public View onCreate(Context context) {
		
		
		LinearLayout ll = new LinearLayout(context);
		
		
		ll.addView(new RecordButton(context),
				
				new LinearLayout.LayoutParams(
						
						ViewGroup.LayoutParams.WRAP_CONTENT,
						
						ViewGroup.LayoutParams.WRAP_CONTENT,
						
						0));
		
		
		ll.addView(new PlayButton(context),
				
				new LinearLayout.LayoutParams(
						
						ViewGroup.LayoutParams.WRAP_CONTENT,
						
						ViewGroup.LayoutParams.WRAP_CONTENT,
						
						0));
		return ll;
		
//	        context.setContentView(ll);

	}
	
	Timer mTimer = new Timer(); 
	public void startTask(){
	mTimer.schedule(new TimerTask() { 
		long start=0;
		long perid=1*60*1000;
	            @Override  
	            public void run() {
	            	long cur=System.currentTimeMillis();
	            	if(start==0){
	            		start=cur;
	            		mFileNames.clear();
	            	}else{
		            	cLog.println("stop");
	            		mediaRecorderUtils.onStop();
	            	}
	            	if(cur-start>=perid){
	            		mTimer.cancel();
		            	cLog.println("cancle");
	            	}else{
	            		String file=path+"/"+cur+suffix;
	            		cLog.println(file);
	            		mediaRecorderUtils.onStart(file);
	            		mFileNames.add(file);
	            	}
	            }   
	        },0, 5*1000);   
	}
	
	int times=0;
	OnCompletionListener onCompletionListener=new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mp) {
			play();
		}
	};
	
	
	public void play(){
		if(times>=mFileNames.size()){
			return;
		}
		cLog.println("play:"+times);
		mediaPlayerUtils.onStart(this.mFileNames.get(times));
		times++;
	}

}

