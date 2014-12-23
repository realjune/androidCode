package w.a.media;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * @author junxu.wang
 *
 */
public class Record{
	boolean isRecording;
	int BUF_SIZE=1024;
	int   recBufSize;
	AudioRecord  mAudioRecord;
	int sampleRateInHz;
	short mChannelConfig;
	short mAudioFormat;
	/**
	 * Android各种设备的采样频率不同，输入的声道数也不同，如果采用固定的采样频率和声道数，那么得到的AudioRecorder不一定能够正常初始化。
为了正常使用，需要尝试各种不同的参数，得到在此设备上可以用的AudioRecorder实例。
即使你得到了有效的AudioRecorder实例，在audioRecord.startRecording()的时候还会报ERROR_BAD_VALUE错误。
这有可能是你使用了AudioManager而没有释放导致的。
其他错误都可以在网络上找到答案。
	 */
	private void createAudioRecord() {  
        for (int sampleRate : new int[]{44100, 8000, 11025, 16000, 22050, 32000,  
         47250, 48000}) {  
     for (short audioFormat : new short[]{  
             AudioFormat.ENCODING_PCM_16BIT,  
             AudioFormat.ENCODING_PCM_8BIT}) {  
         for (short channelConfig : new short[]{  
                 AudioFormat.CHANNEL_IN_MONO,  
                 AudioFormat.CHANNEL_IN_STEREO}) {  

             // Try to initialize  
             try {  
                  recBufSize = AudioRecord.getMinBufferSize(sampleRate,  
                         channelConfig, audioFormat);  

                 if (recBufSize < 0) {  
                     continue;  
                 }  

                 mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,  
                         sampleRate, channelConfig, audioFormat,  
                         recBufSize * 2);  

                 if (mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED) {  
                	 sampleRateInHz=sampleRate;
                	 mAudioFormat=audioFormat;
                	 mChannelConfig=channelConfig;
                     return;  
                 }  

                 mAudioRecord.release();  
                 mAudioRecord = null;  
             } catch (Exception e) {  
                 e.printStackTrace();
             }  
         }  
     }  
 }  

 throw new IllegalStateException(  
         "getInstance() failed : no suitable audio configurations on this device.");  
}
	
	public void startAudioRecord(){
		//测试此函数的取得的最小buffer
//		sampleRateInHz=44100;
//		mChannelConfig= AudioFormat.CHANNEL_IN_DEFAULT;
//		mAudioFormat=AudioFormat.ENCODING_PCM_16BIT;
//		int suggestBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, mChannelConfig, mAudioFormat);
//		mAudioRecord = new AudioRecord(AudioSource.MIC, sampleRateInHz, mChannelConfig, mAudioFormat, suggestBufferSize);
		mAudioRecord.startRecording();
		byte[] inByteBuf = new byte[BUF_SIZE];
		while (isRecording) {
			int readSize = mAudioRecord.read(inByteBuf, 0, inByteBuf.length);
		}
		mAudioRecord.stop();
		mAudioRecord.release();
	}
	
	private AudioRecord.OnRecordPositionUpdateListener updateListener = new AudioRecord.OnRecordPositionUpdateListener()  
	{  
		private int x = 0;
		public void onPeriodicNotification(AudioRecord recorder)  
		{          	
			recorder.read(buffer, 0, buffer.length); // Fill buffer  
			try  
			{  
				//randomAccessWriter.write(buffer); // Write buffer to file  
				//payloadSize += buffer.length;  
				if (bSamples == 16)  
				{  
					for (int i=0; i<buffer.length/2; i++)  
					{ // 16bit sample size  
						short curSample = ExtAudioRecorder.this.getShort(buffer[2*i], buffer[2*i+1]);  
						if (curSample > cAmplitude)  
						{ // Check amplitude  
							cAmplitude = curSample;  
						}  
					}  
				}  
				else     
				{ // 8bit sample size  
					for (int i=0; i<buffer.length; ++i)  
					{  
						if (buffer[i] > cAmplitude)  
						{ // Check amplitude  
							cAmplitude = buffer[i];  
						}  
					}  
				}  
			}  
			catch (Exception e)  
			{  
				Log.e(ExtAudioRecorder.class.getName(), "Error occured in updateListener, recording is aborted");  
				//stop();  
			}  
			if (x%4 == 0)
			{
				mDrawingView.getEditingLayer().addSquare(x/4, 19 - (int)((cAmplitude/65535.0f)*19));
				mDrawingView.invalidate();
			}
			++x;
		}
		@Override
		public void onMarkerReached(AudioRecord recorder) {
			// TODO Auto-generated method stub
			
		}
	};
	class AudioRecordThread implements Runnable 
    { 
        public void run() 
        { 
        	while (isRecording) 
        	{ 
        		audioRecorder.read(buffer, 0, buffer.length); 
        		cAmplitude = 0;
	    		if (bSamples == 16)  
	            {  
	                for (int i=0; i<buffer.length/2; i++)  
	                { 
                        short curSample = ExtAudioRecorder.this.getShort(buffer[2*i], buffer[2*i+1]);  
                        if (curSample > cAmplitude)  
                        { 
                            cAmplitude = curSample;  
                        }  
                    }  
                }  
                else     
                { 
                    for (int i=0; i<buffer.length; ++i)  
                    {  
                        if (buffer[i] > cAmplitude)  
                        { 
                            cAmplitude = buffer[i];  
                        }  
                    }  
                }  

                Message message = Message.obtain();
                message.obj = (Object)(new Integer(19 - (int)((cAmplitude/65535.0f)*19)));
                messageHandler.sendMessage(message);
        	}  
        } 
    } 
}