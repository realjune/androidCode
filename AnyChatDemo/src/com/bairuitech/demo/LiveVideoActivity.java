package com.bairuitech.demo;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

@SuppressWarnings("deprecation")
public class LiveVideoActivity extends Activity implements AnyChatBaseEvent, SensorEventListener{
	private LinearLayout fullLayout;
	private SurfaceView videoView;
	ProgressBar volumeProgressBar;
	private ImageView mCameraSwitchImage;		// ǰ������ͷ�л���ť

	public AnyChatCoreSDK anychat;
	int userID = -1;
	private ConfigEntity configEntity;
	
	private boolean bVideoOpened = false;		// ��Ƶ�Ƿ��Ѵ�
	private boolean bSuccessEnterRoom = false;	// �Ƿ��ѳɹ����뷿��
	
	private final int WORK_MODE_DOWNSTREAM = 1;	// ���ع���ģʽ
	private final int WORK_MODE_UPSTREAM = 2;	// �ϴ�����ģʽ
	private int iWorkMode;
	
	private final int volumeHeight = 5;			// �������߶�
	
	private int VideoWidth;	
	private int VideoHeight;			
	
	private AbsoluteLayout absoluteLayout;
	
	private Timer  mTimer = new Timer(true); 
	private TimerTask mTimerTask;
	private Handler handler;
	
	private boolean bCameraNeedFocus = false;		// ������Ƿ���Ҫ�Խ�
	private Date LastSportTime = new Date();		// �ϴ��˶�ʱ�� 
	private float LastXSpead = 0;
	private float LastYSpead = 0;
	private float LastZSpead = 0;

	private Button quitBtn;
	private boolean bShowQuitBtn = true;			// �Ƿ�����ʾ�˳���ť
	private Date mLastShowQuitBtnTime = new Date();	// ��ʼ��ʾ�˳���ť��ʱ��

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configEntity = ConfigService.LoadConfig(this);
        iWorkMode = getIntent().getIntExtra("mode",0);
        if(iWorkMode == WORK_MODE_UPSTREAM)		// ������ϴ�ģʽ��ֻ���Լ�����Ƶ
        {
        	userID = -1;
        	
        	// ��ȡ�������������
        	SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        	// ��ȡ���ٶȴ�����
        	Sensor mAccelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        	//��ȡ���ٶȴ���������Ϣ��SensorManager.  SENSOR_DELAY_NORMAL��ʾ��ȡ���ڣ�ȡ��������
            sm.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL );
        }
        
        VideoWidth= ScreenInfo.HEIGHT;
        VideoHeight = ScreenInfo.WIDTH-50;
        
        InitialSDK();
        InitialLayout();
        
        mTimerTask = new TimerTask() 
        {
          public void run()
          {
              Message mesasge = new Message();  
              handler.sendMessage(mesasge);  
          }         
         };
         mTimer.schedule(mTimerTask, 1000,100);
         handler = new Handler()  
         {  
             @Override  
             public void handleMessage(Message msg)  
             {
            	 CheckVideoStatus();
            	 // ˢ��������
            	 if(LiveVideoActivity.this.bSuccessEnterRoom)
            		 LiveVideoActivity.this.volumeProgressBar.setProgress(LiveVideoActivity.this.anychat.GetUserSpeakVolume(userID));
            	 // �ж��Ƿ���Ҫ�����˳���ť
            	 Date now = new Date();
            	 long interval = now.getTime() - LiveVideoActivity.this.mLastShowQuitBtnTime.getTime();
            	 if(LiveVideoActivity.this.bShowQuitBtn && (interval > 2000))
            	 {
            		 LiveVideoActivity.this.bShowQuitBtn = false;
            		 quitBtn.setVisibility(View.GONE);
            	 }
            	 super.handleMessage(msg);  
             }
         };
    }
    // �ж���Ƶ��״̬�����û�д򿪣�����һ���ʺϵ��û�
    private void CheckVideoStatus()
    {
    	if(bVideoOpened || !bSuccessEnterRoom)
    		return;
    	if(iWorkMode == WORK_MODE_DOWNSTREAM)			// �������˵���Ƶ
    	{
	     	int[] userarray = anychat.GetOnlineUser();
	   		for(int i=0; i<userarray.length; i++)
	   		{
	   			int uid = userarray[i];
	   			if(anychat.GetCameraState(uid) == 2 && anychat.GetUserVideoWidth(uid) != 0)
	   			{
	   				userID = uid;
	   				SurfaceHolder holder=videoView.getHolder();
	   				holder.setFormat(PixelFormat.RGB_565);
	   				holder.setFixedSize(anychat.GetUserVideoWidth(userID), anychat.GetUserVideoHeight(userID));
	   				Surface s=holder.getSurface();
	   				anychat.SetVideoPos(userID, s, 0, 0, 0, 0);
	   				anychat.UserCameraControl(userID, 1);
	   				anychat.UserSpeakControl(userID, 1);
	   				bVideoOpened = true;
	   				break;
	   			}
	   		}   		
    	}
    	else		// �򿪱��ص���Ƶ�����뷿��ɹ��Ѿ������˴򿪲��������ֻ��Ҫ������ʾ
    	{
    		if(anychat.GetCameraState(userID) == 2 && anychat.GetUserVideoWidth(userID) != 0)
    		{
    			SurfaceHolder holder=videoView.getHolder();
    			holder.setFormat(PixelFormat.RGB_565);
	    		holder.setFixedSize(anychat.GetUserVideoWidth(userID), anychat.GetUserVideoHeight(userID));
				Surface s=holder.getSurface();
				anychat.SetVideoPos(userID, s, 0, 0, 0, 0);  
				bVideoOpened = true;
    		}
    	}

    }
    
    private void InitialSDK()
    {
        anychat = new AnyChatCoreSDK();
        anychat.SetBaseEvent(this);
        
        int RoomID = getIntent().getIntExtra("RoomID",0);
        anychat.EnterRoom(RoomID, "");
    }
    
	private void InitialLayout()
    {
		if(iWorkMode == WORK_MODE_UPSTREAM)
			this.setTitle("������Ƶ�ϴ�");
		else
			this.setTitle("ʵʱ��ý��ֱ��");
		
    	fullLayout =  new LinearLayout(this);
    	fullLayout.setBackgroundResource(R.drawable.chat_bk);
    	//fullLayout.setBackgroundColor(Color.WHITE);
    	fullLayout.setOrientation(LinearLayout.VERTICAL);
    	//fullLayout.setOnTouchListener(touchListener);
	    
	    absoluteLayout = new AbsoluteLayout(this);
	    absoluteLayout.setBackgroundColor(Color.TRANSPARENT);
	

	    videoView = new SurfaceView(this);
	    videoView.setOnClickListener(listener);
		SurfaceHolder holder=videoView.getHolder();
		holder.setKeepScreenOn(true);
		if(iWorkMode == WORK_MODE_UPSTREAM && configEntity.videoOverlay==1)
			holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
	    volumeProgressBar=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
	    volumeProgressBar.setMax(100);
	    volumeProgressBar.setIndeterminate(false);  
	    
	    quitBtn = new Button(this);
    	quitBtn.setText("����");
    	quitBtn.setVisibility(View.GONE);
    	quitBtn.setOnClickListener(listener);
    	
    	String strVideoCaptures[]=anychat.EnumVideoCapture();
    	mCameraSwitchImage=new ImageView(this);
		mCameraSwitchImage.setImageDrawable(getResources().getDrawable(R.drawable.switchvideo));
		mCameraSwitchImage.setBackgroundResource(R.drawable.btn_switchvideo_background);
		mCameraSwitchImage.requestFocus();
		mCameraSwitchImage.setOnClickListener(listener);
		if((strVideoCaptures!=null&&strVideoCaptures.length<=1) || iWorkMode == WORK_MODE_DOWNSTREAM)
			mCameraSwitchImage.setVisibility(View.GONE);
	   
		FrameLayout myViewLayout=new FrameLayout(this);
		FrameLayout.LayoutParams myParams1 = new FrameLayout.LayoutParams(
				VideoWidth, VideoHeight);
		FrameLayout.LayoutParams myParams2 = new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
		FrameLayout.LayoutParams myParams3= new FrameLayout.LayoutParams(
				ScreenInfo.WIDTH/2, FrameLayout.LayoutParams.WRAP_CONTENT);
		myParams2.gravity=Gravity.RIGHT;
		myViewLayout.addView(videoView, myParams1);
		myViewLayout.addView(mCameraSwitchImage, myParams2);
		myViewLayout.addView(quitBtn, myParams3);
		
		AbsoluteLayout.LayoutParams vLP1 = new AbsoluteLayout.LayoutParams
				(VideoWidth, VideoHeight,0, 0);
		absoluteLayout.addView(myViewLayout, vLP1);
	   
    	
		AbsoluteLayout.LayoutParams vLP2 =   new AbsoluteLayout.LayoutParams(VideoWidth, volumeHeight,0, VideoHeight);
    	//fullLayout.addView(absoluteLayout,new LayoutParams(ScreenInfo.WIDTH,ScreenInfo.HEIGHT));
    	fullLayout.addView(absoluteLayout,new LayoutParams(VideoWidth,LayoutParams.WRAP_CONTENT));
    	fullLayout.addView(volumeProgressBar, vLP2);
    	requestWindowFeature(Window.FEATURE_NO_TITLE);		// ���ر�����
    	this.setContentView(fullLayout);
    }
	
	OnClickListener listener = new OnClickListener()
    {       
		public void onClick(View v) 
		{
			if(v == quitBtn)
			{
				mTimer.cancel();
				if(bVideoOpened)
				{
					anychat.UserCameraControl(userID, 0);
					anychat.UserSpeakControl(userID, 0);
					bVideoOpened = false;
				}
				finish();
			}
			else if(v == videoView)
			{
				bShowQuitBtn = true;
				mLastShowQuitBtnTime = new Date();
				quitBtn.setVisibility(View.VISIBLE);
			}
			else if(v==mCameraSwitchImage)		// ǰ������ͷ�л��¼�
			{
				String strVideoCaptures[]=anychat.EnumVideoCapture();
				if(strVideoCaptures==null)
					return;
				String temp=anychat.GetCurVideoCapture();
				for(int i=0;i<strVideoCaptures.length;i++)
				{
					if(!temp.equals(strVideoCaptures[i]))
					{
						anychat.UserCameraControl(-1, 0);
						bVideoOpened=false;
						anychat.SelectVideoCapture(strVideoCaptures[i]);
						anychat.UserCameraControl(-1, 1);
						break;
					}
				}
			}
		}
    };
    
	protected void onRestart() {
    	super.onRestart();
    	anychat.UserCameraControl(userID, 1);
		anychat.UserSpeakControl(userID, 1);
	
    }

    protected void onResume() {
    	super.onResume();

    }

    protected void onPause() {
    	super.onPause();
		if(bVideoOpened)
		{
			anychat.UserCameraControl(userID, 0);
			anychat.UserSpeakControl(userID, 0);
			bVideoOpened = false;
			
		}
    }
    
    protected void onDestroy(){
    	mTimer.cancel();
		anychat.LeaveRoom(-1);
		super.onDestroy();
    	finish();
    }
 


	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		if(dwErrorCode == 0)
		{
			bSuccessEnterRoom = true;
			if(iWorkMode == WORK_MODE_UPSTREAM)
			{
				anychat.UserCameraControl(userID, 1);
				anychat.UserSpeakControl(userID, 1); 
			}
		}
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {

	}

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {

	}
	
   public void onSensorChanged(android.hardware.SensorEvent event)
   {
       float X=event.values[0] ;	// ˮƽx������ٶ� �����徲ֹʱ�ڣ�0--1֮�䣩
       float Y=event.values[1] ;	// ˮƽY������ٶ� �����徲ֹʱ�ڣ�0--1֮�䣩
       float Z=event.values[1] ;	// ��ֱZ������ٶ� �����徲ֹʱ�ڣ�9.5--10֮�䣩
       
       if((Math.abs(X-LastXSpead)<= 0.5) && 
    	(Math.abs(Y-LastYSpead)<=0.5) && 
    	(Math.abs(Z-LastZSpead)<=0.5))	 // ��ֹ״̬
       {
    	   Date now = new Date();
    	   long interval = now.getTime() - LastSportTime.getTime();
    	   if(bCameraNeedFocus && interval>1000)
    	   {
    		   bCameraNeedFocus = false;
    		   anychat.CameraAutoFocus();
    	   }
       }
       else
       {
    	   bCameraNeedFocus = true;
    	   LastSportTime.setTime(System.currentTimeMillis());
       }
       LastXSpead = X;
       LastYSpead = Y;
       LastZSpead = Z;
   }
   
   public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy)
   {

   }
}
