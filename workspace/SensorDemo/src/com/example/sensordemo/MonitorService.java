package com.example.sensordemo;

	 
	import java.io.File;
import java.io.RandomAccessFile;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;  //֪ͨ������ʾ����Ļ���Ͻǵ�Сͼ��  
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.IBinder;
import android.util.Log;
	 
	public class MonitorService extends Service {  
		SensorUtils mSensorUtils;
	  @Override 
	  public IBinder onBind(Intent intent) {  
	    return null;  
	  }  
	 
	  @Override 
	  public void onStart(Intent intent, int startId) {  
	    // �����ص������¹㲥�Ĺ�����,ֻ���ܴ���ACTION_BATTERRY_CHANGED�¼���Intent  
	    IntentFilter batteryChangedReceiverFilter = new IntentFilter();  
	    batteryChangedReceiverFilter.addAction(Intent.ACTION_BATTERY_CHANGED);  
	      
	    // ��ϵͳע��batteryChangedReceiver������������������ʵ�ּ������ֶδ�  
	    registerReceiver(batteryChangedReceiver, batteryChangedReceiverFilter); 
		mSensorUtils=new SensorUtils(this);
		
		 Timer timer= new Timer() ;
		 TimerTask timerTask = new TimerTask(){

	          public void run(){

	        	  mSensorUtils.startListener(MonitorService.this,lsn); 
	               //Doing something

	           }

	  };
	  timer.schedule(timerTask, 0) ;


	 
	    // ʵ����Notification֪ͨ�Ĺ����������ֶ�notification manager  
	    notifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);  
	 
	    // ���ڳ�ʼ��������ʱϵͳ����û�з���ACTION_BATTERY_CHANGED�㲥����ô�ղ�ע����Ǹ��������������ڱ���������ʱ�������������¾��޷���ʾ��ǰ������������������һ�������㲥��������  
//	    new BroadcastReceiver() {  
//	      @Override 
//	      public void onReceive(Context context, Intent intent) {  
//	        int level = intent.getIntExtra("level", 0);  //��ص����ȼ�  
//	        int scale = intent.getIntExtra("scale", 100);  //�����ʱ�ٷֱ�  
//	        int status = intent.getIntExtra("status", 0);  //���״̬  
//	 
//	        // �����ڳ��  
//	        if (status == BatteryManager.BATTERY_STATUS_CHARGING)  
//	          notification = getNotification(getChargingIcon(level * 100 / scale),  
//	              "��ؼ��", System.currentTimeMillis(), "��ص���", "���ڳ��");  
//	        else 
//	          notification = getNotification(getStateIcon(level * 100 / scale),  
//	              "��ؼ��", System.currentTimeMillis(), "��ص���", "�뼰ʱ���");  
//	      
//	    //��ϵͳ���ʹ�֪ͨ��Ϊ���������֪ͨID����Ϊ0  
//	        notifyManager.notify(0, notification);  
//	      }  
//	    };  
	  }  
	 
	  @Override 
	  public void onDestroy() {  
	    //����������ֹʱ��ͨ��֪ͨIDע�����Ͻǵ�֪ͨ  
//	    notifyManager.cancel(0);  
	  }  
	 
	  // --------------------------------------˽�з���------------------------------  
	  // ���һ��notification  
//	  private Notification getNotification(int notificationIcon, String tickerText,  
//	      long when, String contentTitle, String contentText) {  
//	    // �����notification����չ�ı���intent  
//	    Notification notification = new Notification(notificationIcon, tickerText,  
//	        when);  
//	    Context context = getApplicationContext();  
//	    Intent notificationIntent = new Intent(context, BatteryInfo.class);  
//	    PendingIntent contentIntent = PendingIntent.getActivity(this, 0,  
//	        notificationIntent, 0);  
//	    notification.setLatestEventInfo(context, contentTitle, contentText,  
//	        contentIntent);  
//	 
//	    // ���ñ�״̬�����ڡ����ڽ��С����ǡ�֪ͨ����Ŀ���������û����״̬ͼ��  
//	    notification.flags |= Notification.FLAG_ONGOING_EVENT;  
//	    return notification;  
//	  }  
//	 
//	  // ��ö�Ӧ�ĵ��״̬ͼ��  
//	  private int getStateIcon(int batteryHealth) {  
//	    if (batteryHealth >= 0 || batteryHealth <= 100)  
//	      return batteryStateIcons[batteryHealth]; //������Ϊ�˷��������Ӳ�������Դ���飬ֱ�Ӵ�R.java�ļ�����ȡ��ʮ��������ԴID  
//	    return R.drawable.stat_sys_battery_unknown;  
//	  }  
//	 
//	  // ��ö�Ӧ�ĳ��״̬ͼ��  
//	  private int getChargingIcon(int batteryHealth) {  
//	    if (batteryHealth >= 0 && batteryHealth < 5)  
//	      return R.drawable.stat_sys_battery_charge_anim0;  
//	    if (batteryHealth >= 5 && batteryHealth < 10)  
//	      return R.drawable.stat_sys_battery_charge_anim01;  
//	    if (batteryHealth >= 10 && batteryHealth < 15)  
//	      return R.drawable.stat_sys_battery_charge_anim02;  
//	    if (batteryHealth >= 15 && batteryHealth < 20)  
//	      return R.drawable.stat_sys_battery_charge_anim03;  
//	    if (batteryHealth >= 20 && batteryHealth < 25)  
//	      return R.drawable.stat_sys_battery_charge_anim04;  
//	    if (batteryHealth >= 25 && batteryHealth < 30)  
//	      return R.drawable.stat_sys_battery_charge_anim05;  
//	    if (batteryHealth >= 30 && batteryHealth < 35)  
//	      return R.drawable.stat_sys_battery_charge_anim06;  
//	    if (batteryHealth >= 35 && batteryHealth < 40)  
//	      return R.drawable.stat_sys_battery_charge_anim07;  
//	    if (batteryHealth >= 40 && batteryHealth < 45)  
//	      return R.drawable.stat_sys_battery_charge_anim08;  
//	    if (batteryHealth >= 45 && batteryHealth < 50)  
//	      return R.drawable.stat_sys_battery_charge_anim09;  
//	    if (batteryHealth >= 50 && batteryHealth < 55)  
//	      return R.drawable.stat_sys_battery_charge_anim10;  
//	    if (batteryHealth >= 55 && batteryHealth < 60)  
//	      return R.drawable.stat_sys_battery_charge_anim11;  
//	    if (batteryHealth >= 60 && batteryHealth < 65)  
//	      return R.drawable.stat_sys_battery_charge_anim12;  
//	    if (batteryHealth >= 65 && batteryHealth < 70)  
//	      return R.drawable.stat_sys_battery_charge_anim13;  
//	    if (batteryHealth >= 70 && batteryHealth < 75)  
//	      return R.drawable.stat_sys_battery_charge_anim14;  
//	    if (batteryHealth >= 75 && batteryHealth < 80)  
//	      return R.drawable.stat_sys_battery_charge_anim15;  
//	    if (batteryHealth >= 80 && batteryHealth < 85)  
//	      return R.drawable.stat_sys_battery_charge_anim16;  
//	    if (batteryHealth >= 85 && batteryHealth < 90)  
//	      return R.drawable.stat_sys_battery_charge_anim17;  
//	    if (batteryHealth >= 90 && batteryHealth < 95)  
//	      return R.drawable.stat_sys_battery_charge_anim18;  
//	    if (batteryHealth >= 95 && batteryHealth < 100)  
//	      return R.drawable.stat_sys_battery_charge_anim19;  
//	    if (batteryHealth == 100)  
//	      return R.drawable.stat_sys_battery_charge_animfull;  
//	    return R.drawable.stat_sys_battery_unknown;  
//	  }  
	 
	  // -------------------------------˽���ֶ�--------------------------------------  
	  private NotificationManager notifyManager = null;  
	  private Notification notification = null;  
	 
	  //������Ϊ�˷��������Ӳ�����icon��Դ���飬ֱ�Ӵ�R.java�ļ�����ȡ��ʮ��������ԴID���������0��Ԫ��0x7f020002��ʾ��Դstat_sys_battery_0.png�������������Ժ��ۼӡ���ͬ���õ�ID���ܲ�ͬ������������޸ġ�  
	  private int batteryStateIcons[] = { 0x7f020002, 0x7f020003, 0x7f020004,  
	      0x7f020005, 0x7f020006, 0x7f020007, 0x7f020008, 0x7f020009, 0x7f02000a,  
	      0x7f02000b, 0x7f02000c, 0x7f02000d, 0x7f02000e, 0x7f02000f, 0x7f020010,  
	      0x7f020011, 0x7f020012, 0x7f020013, 0x7f020014, 0x7f020015, 0x7f020016,  
	      0x7f020017, 0x7f020018, 0x7f020019, 0x7f02001a, 0x7f02001b, 0x7f02001c,  
	      0x7f02001d, 0x7f02001e, 0x7f02001f, 0x7f020020, 0x7f020021, 0x7f020022,  
	      0x7f020023, 0x7f020024, 0x7f020025, 0x7f020026, 0x7f020027, 0x7f020028,  
	      0x7f020029, 0x7f02002a, 0x7f02002b, 0x7f02002c, 0x7f02002d, 0x7f02002e,  
	      0x7f02002f, 0x7f020030, 0x7f020031, 0x7f020032, 0x7f020033, 0x7f020034,  
	      0x7f020035, 0x7f020036, 0x7f020037, 0x7f020038, 0x7f020039, 0x7f02003a,  
	      0x7f02003b, 0x7f02003c, 0x7f02003d, 0x7f02003e, 0x7f02003f, 0x7f020040,  
	      0x7f020041, 0x7f020042, 0x7f020043, 0x7f020044, 0x7f020045, 0x7f020046,  
	      0x7f020047, 0x7f020048, 0x7f020049, 0x7f02004a, 0x7f02004b, 0x7f02004c,  
	      0x7f02004d, 0x7f02004e, 0x7f02004f, 0x7f020050, 0x7f020051, 0x7f020052,  
	      0x7f020053, 0x7f020054, 0x7f020055, 0x7f020056, 0x7f020057, 0x7f020058,  
	      0x7f020059, 0x7f02005a, 0x7f02005b, 0x7f02005c, 0x7f02005d, 0x7f02005e,  
	      0x7f02005f, 0x7f020060, 0x7f020061, 0x7f020062, 0x7f020063, 0x7f020064,  
	      0x7f020065, 0x7f02007b };  
	 
	  // ���ܵ����Ϣ���µĹ㲥  
	  private BroadcastReceiver batteryChangedReceiver = new BroadcastReceiver() { 
		  int lastLevel=0;
	    public void onReceive(Context context, Intent intent) {  
	      int level = intent.getIntExtra("level", 0);  
	      int scale = intent.getIntExtra("scale", 100);  
	      int status = intent.getIntExtra("status", 0);  
	      if(level==lastLevel){
	    	  return;
	      }
	      lastLevel=level;
	      WriteTxtFile("bettery: "+level+ "    "+level * 100 / scale);
	      Log.d("ddd","bettery: "+level);
	      // �����ڳ��  
	      if (status == BatteryManager.BATTERY_STATUS_CHARGING){
//	        notification = getNotification(getChargingIcon(level * 100 / scale),  
//	            "Battery Monitor", System.currentTimeMillis(), "��ص���", "���ڳ��");  
	      }else {
//	        notification = getNotification(getStateIcon(level * 100 / scale),  
//	            "Battery Monitor", System.currentTimeMillis(), "��ص���", "�뼰ʱ���");  
//	      notifyManager.notify(0, notification); 
	      } 
	    }  
	  }; 
	  

	    SensorEventListener lsn = new SensorEventListener() {  
	        private float x, y, z;     
	       public void onSensorChanged(SensorEvent e) {       
	           x = e.values[SensorManager.DATA_X];       
	           y = e.values[SensorManager.DATA_Y];       
	           z = e.values[SensorManager.DATA_Z];
	           WriteTxtFile("x=" + x + "," + "y=" +  y + "," + "z="+  z);
	           Log.d("ddd","x=" + x + "," + "y=" +  y + "," + "z="+  z);
	       }       
	  
	       public void onAccuracyChanged(Sensor s, int accuracy) {      
	    	   WriteTxtFile("onAccuracyChanged " + s.getName()+ "," + "getPower=" + s.getPower() + "," + "accuracy="+ accuracy);
	    	   Log.d("ddd","onAccuracyChanged " + s.getName()+ "," + "getPower=" + s.getPower() + "," + "accuracy="+ accuracy);
	       }       
	   }; 
	   DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	  public void WriteTxtFile(String strcontent)
	     {
	       //ÿ��д��ʱ��������д
	      String strContent=format2.format(System.currentTimeMillis())+" "+strcontent+"\n";
	       try {
	            File file = new File("/sdcard/bettery_log.txt");
	            if (!file.exists()) {
	             Log.d("TestFile", "Create the file:/sdcard/bettery_log.txt");
	             file.createNewFile();
	            }
	            RandomAccessFile raf = new RandomAccessFile(file, "rw");
	            raf.seek(file.length());
	            raf.write(strContent.getBytes());
	            raf.close();
	       } catch (Exception e) {
	            Log.e("TestFile", "Error on write File.");
	           }
	     }
	} 
