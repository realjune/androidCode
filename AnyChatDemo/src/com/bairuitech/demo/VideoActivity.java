package com.bairuitech.demo;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;

@SuppressWarnings("deprecation")
public class VideoActivity extends Activity implements AnyChatBaseEvent,
		SensorEventListener,OnClickListener {
	private LinearLayout fullLayout;
	// private LinearLayout mainLayout;

	private SurfaceView otherView;
	ProgressBar OtherProgressBar;
	ProgressBar MyProgressBar;
	private ImageView mCameraSwitchImage;		// ǰ������ͷ�л���ť

	private SurfaceView myView;
 
	private ConfigEntity configEntity;

	public AnyChatCoreSDK anychat;
	int userID;

	private final int blankInterval = 5; 		// ��Ƶ�߿�հ�������С���
	private final int volumeHeight = 5; 		// �������߶�

	private int VideoWidth;
	private int VideoHeight;

	private int iNormalVideoWidth; 				// ������Ƶ���
	private int iNormalVideoHeight; 			// ������Ƶ�߶�

	private int FullScreenWidth; 				// ȫ����Ƶ���
	private int FullScreenHeight; 				// ȫ����Ƶ�߶�

	private boolean bSelfVideoOpened = false; 	// ������Ƶ�Ƿ��Ѵ�
	private boolean bOtherVideoOpened = false; 	// �Է���Ƶ�Ƿ��Ѵ�

	private AbsoluteLayout absoluteLayout;

	private Timer mTimer = new Timer(true);
	private TimerTask mTimerTask;
	private Handler handler;

	private boolean bCameraNeedFocus = false; 	// ������Ƿ���Ҫ�Խ�
	private Date LastSportTime = new Date(); 	// �ϴ��˶�ʱ��
	private float LastXSpead = 0;
	private float LastYSpead = 0;
	private float LastZSpead = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		configEntity = ConfigService.LoadConfig(this);
		Intent intent = getIntent();
		userID = Integer.parseInt(intent.getStringExtra("UserID"));

		VideoWidth = ScreenInfo.HEIGHT;
		VideoHeight = ScreenInfo.WIDTH - 60;

		InitialSDK();
		InitialLayout();

		mTimerTask = new TimerTask() {
			public void run() {
				Message mesasge = new Message();
				handler.sendMessage(mesasge);
			}
		};

		mTimer.schedule(mTimerTask, 1000, 100);

		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				CheckVideoStatus();
				SetVolum();
				super.handleMessage(msg);
			}
		};

		// ��ȡ�������������
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		// ��ȡ���ٶȴ�����
		Sensor mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		// ��ȡ���ٶȴ���������Ϣ��SensorManager. SENSOR_DELAY_NORMAL��ʾ��ȡ���ڣ�ȡ��������
		sm.registerListener(this, mAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	private void SetVolum() {
		OtherProgressBar.setProgress(anychat.GetUserSpeakVolume(userID));
		MyProgressBar.setProgress(anychat.GetUserSpeakVolume(-1));
	}

	// �ж���Ƶ�Ƿ��Ѵ�
	private void CheckVideoStatus() {
		if (!bOtherVideoOpened) {
			if (anychat.GetCameraState(userID) == 2
					&& anychat.GetUserVideoWidth(userID) != 0) {
				SurfaceHolder holder = otherView.getHolder();
				holder.setFormat(PixelFormat.RGB_565);
				holder.setFixedSize(anychat.GetUserVideoWidth(userID),
						anychat.GetUserVideoHeight(userID));
				Surface s = holder.getSurface();
				anychat.SetVideoPos(userID, s, 0, 0, 0, 0);
				bOtherVideoOpened = true;
			}
		}
		if (!bSelfVideoOpened) {
			if (anychat.GetCameraState(-1) == 2
					&& anychat.GetUserVideoWidth(-1) != 0) {
				SurfaceHolder holder = myView.getHolder();
				holder.setFormat(PixelFormat.RGB_565);
				holder.setFixedSize(anychat.GetUserVideoWidth(-1),
						anychat.GetUserVideoHeight(-1));
				Surface s = holder.getSurface();
				anychat.SetVideoPos(-1, s, 0, 0, 0, 0);
				bSelfVideoOpened = true;
			}
		}

	}

	private void InitialSDK() {
		anychat = new AnyChatCoreSDK();
		anychat.SetBaseEvent(this);
	}

	private void InitialLayout() {
		this.setTitle("��" + anychat.GetUserName(userID) + "��Ƶͨ��");
		fullLayout = new LinearLayout(this);
		fullLayout.setBackgroundResource(R.drawable.chat_bk);
		// fullLayout.setBackgroundColor(Color.WHITE);
		fullLayout.setOrientation(LinearLayout.VERTICAL);

		absoluteLayout = new AbsoluteLayout(this);
		absoluteLayout.setBackgroundColor(Color.TRANSPARENT);

		if (((VideoWidth - blankInterval * 4) / 2) * 3 >= 4 * VideoHeight) // ��ĻҪ��һЩ
		{
			Log.e("*************", "here");
			// iNormalVideoHeight = (ScreenInfo.HEIGHT - blankInterval*2);
			iNormalVideoHeight = VideoHeight - blankInterval * 2;
			iNormalVideoWidth = iNormalVideoHeight * 4 / 3;
		} else {

			// iNormalVideoWidth = ScreenInfo.WIDTH/2 - blankInterval*2;
			iNormalVideoWidth = (VideoWidth - blankInterval * 4) / 2;
			iNormalVideoHeight = iNormalVideoWidth * 3 / 4;

		}

		if (((VideoWidth - blankInterval * 2)) * 3 >= 4 * VideoHeight) // ��ĻҪ��һЩ
		{
			// iNormalVideoHeight = (ScreenInfo.HEIGHT - blankInterval*2);
			FullScreenHeight = VideoHeight - blankInterval * 2;
			FullScreenWidth = FullScreenHeight * 4 / 3;
		} else {

			// iNormalVideoWidth = ScreenInfo.WIDTH/2 - blankInterval*2;
			FullScreenWidth = (VideoWidth - blankInterval * 2);
			FullScreenHeight = FullScreenWidth * 3 / 4;
		}
		// ���л��Ĵ�С��ȫ����Ƶ�Ĵ�С�ɱ���
		// iSubVideoWidth = iFullScreenWidth/3;
		// iSubVideoHeight = iFullScreenHeight/3;

		otherView = new SurfaceView(this);
		SurfaceHolder holder = otherView.getHolder();
		holder.setKeepScreenOn(true);

		anychat.UserCameraControl(userID, 1);
		anychat.UserSpeakControl(userID, 1);

		AbsoluteLayout.LayoutParams vLP = new AbsoluteLayout.LayoutParams(
				iNormalVideoWidth, iNormalVideoHeight,
				(VideoWidth - iNormalVideoWidth * 2) / 4,
				(VideoHeight - iNormalVideoHeight) / 2);
		absoluteLayout.addView(otherView, vLP);
		// mainLayout.addView(otherView,new
		// LayoutParams(ScreenInfo.WIDTH,ScreenInfo.WIDTH*3/4));

		myView = new SurfaceView(this);
		if (configEntity.videoOverlay != 0) {
			myView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		// myView.setBackgroundResource(R.drawable.room);

		anychat.UserCameraControl(-1, 1);
		anychat.UserSpeakControl(-1, 1);

		// ���ǰ������ͷ�л���ť
		String strVideoCaptures[]=anychat.EnumVideoCapture();
		FrameLayout myViewLayout=new FrameLayout(this);
		mCameraSwitchImage=new ImageView(this);
		mCameraSwitchImage.setImageDrawable(getResources().getDrawable(R.drawable.switchvideo));
		mCameraSwitchImage.setBackgroundResource(R.drawable.btn_switchvideo_background);
		mCameraSwitchImage.requestFocus();
		mCameraSwitchImage.setOnClickListener(this);
		if(strVideoCaptures.length<=1)
			mCameraSwitchImage.setVisibility(View.GONE);
		FrameLayout.LayoutParams myParams1 = new FrameLayout.LayoutParams(
				iNormalVideoWidth, iNormalVideoHeight);
		FrameLayout.LayoutParams myParams2 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		myParams2.gravity=Gravity.RIGHT;
		myViewLayout.addView(myView, myParams1);
		myViewLayout.addView(mCameraSwitchImage, myParams2);
		
		AbsoluteLayout.LayoutParams vLP2 = new AbsoluteLayout.LayoutParams(
				iNormalVideoWidth, iNormalVideoHeight, iNormalVideoWidth
						+ (VideoWidth - iNormalVideoWidth * 2) / 4 * 3,
				(VideoHeight - iNormalVideoHeight) / 2);
		absoluteLayout.addView(myViewLayout, vLP2);

		
		OtherProgressBar=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
		OtherProgressBar.setMax(100);
		OtherProgressBar.setIndeterminate(false);

		AbsoluteLayout.LayoutParams vLP3 = new AbsoluteLayout.LayoutParams(
				iNormalVideoWidth, volumeHeight,
				(VideoWidth - iNormalVideoWidth * 2) / 4, VideoHeight
						- (VideoHeight - iNormalVideoHeight) / 2);
		absoluteLayout.addView(OtherProgressBar, vLP3);

		MyProgressBar=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
		MyProgressBar.setMax(100);
		MyProgressBar.setIndeterminate(false);

		AbsoluteLayout.LayoutParams vLP4 = new AbsoluteLayout.LayoutParams(
				iNormalVideoWidth, volumeHeight, iNormalVideoWidth
						+ (VideoWidth - iNormalVideoWidth * 2) / 4 * 3,
				VideoHeight - (VideoHeight - iNormalVideoHeight) / 2);
		absoluteLayout.addView(MyProgressBar, vLP4);

		// fullLayout.addView(absoluteLayout,new
		// LayoutParams(ScreenInfo.WIDTH,ScreenInfo.HEIGHT));
		fullLayout.addView(absoluteLayout, new LayoutParams(VideoWidth,
				VideoHeight + 25));

		this.setContentView(fullLayout);
	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {

		}
	};
	private void refreshAV()
	{
		anychat.UserCameraControl(userID, 1);
		anychat.UserSpeakControl(userID, 1);
		anychat.UserCameraControl(-1, 1);
		anychat.UserSpeakControl(-1, 1);
		bOtherVideoOpened = false;
		bSelfVideoOpened = false;
	}

	protected void onRestart() {
    	super.onRestart();
    	refreshAV();
	
    }

    protected void onResume() {
    	super.onResume();

    }

    protected void onPause() {
    	super.onPause();
    	anychat.UserCameraControl(userID, 0);
		anychat.UserSpeakControl(userID, 0);
		anychat.UserCameraControl(-1, 0);
		anychat.UserSpeakControl(-1, 0);
    }
	
	protected void onDestroy() {
		super.onDestroy();
    	mTimer.cancel();
		finish();
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		// TODO Auto-generated method stub
		Log.e("********VideoActivity*********", "OnAnyChatEnterRoomMessage");

	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		// TODO Auto-generated method stub
		Log.e("********VideoActivity*********", "OnAnyChatOnlineUserMessage   "
				+ dwUserNum);
		refreshAV();
	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		// TODO Auto-generated method stub
		Log.e("********VideoActivity*********", "OnAnyChatUserAtRoomMessage"
				+ dwUserId);
		if(dwUserId==userID)
		{
			if(!bEnter)
			{
				anychat.UserCameraControl(dwUserId, 0);
				anychat.UserSpeakControl(dwUserId, 0);
				bOtherVideoOpened=false;
			}
			else
			{
				anychat.UserCameraControl(dwUserId, 1);
				anychat.UserSpeakControl(dwUserId, 1);
			}
		}
		
			

	}

	public void onSensorChanged(android.hardware.SensorEvent event) {
		float X = event.values[0]; // ˮƽx������ٶ� �����徲ֹʱ�ڣ�0--1֮�䣩
		float Y = event.values[1]; // ˮƽY������ٶ� �����徲ֹʱ�ڣ�0--1֮�䣩
		float Z = event.values[1]; // ��ֱZ������ٶ� �����徲ֹʱ�ڣ�9.5--10֮�䣩

		if ((Math.abs(X - LastXSpead) <= 0.5)
				&& (Math.abs(Y - LastYSpead) <= 0.5)
				&& (Math.abs(Z - LastZSpead) <= 0.5)) // ��ֹ״̬
		{
			Date now = new Date();
			long interval = now.getTime() - LastSportTime.getTime();
			if (bCameraNeedFocus && interval > 1000) {
				bCameraNeedFocus = false;
				anychat.CameraAutoFocus();
			}
		} else {
			bCameraNeedFocus = true;
			LastSportTime.setTime(System.currentTimeMillis());
		}
		LastXSpead = X;
		LastYSpead = Y;
		LastZSpead = Z;
	}

	public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==mCameraSwitchImage)
		{
			String strVideoCaptures[]=anychat.EnumVideoCapture();;
			String temp=anychat.GetCurVideoCapture();
			for(int i=0;i<strVideoCaptures.length;i++)
			{
				if(!temp.equals(strVideoCaptures[i]))
				{
					anychat.UserCameraControl(-1, 0);
					bSelfVideoOpened=false;
					anychat.SelectVideoCapture(strVideoCaptures[i]);
					anychat.UserCameraControl(-1, 1);
					break;
				}
			}
			
		}
	}
}
