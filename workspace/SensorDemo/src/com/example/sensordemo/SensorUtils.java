package com.example.sensordemo;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

/**
 * @author junxu.wang
 * <pre>
 *Android�г����İ��ִ�������
       ���ٶȴ�����(accelerometer)
       �����Ǵ�����(gyroscope)
       �������մ�����(light)
       ����������(magnetic field)
       ���򴫸���(orientation)
       ѹ��������(pressure)
       ���봫����(proximity)
       �¶ȴ�����(temperature)

Android�Ĵ󲿷��ֻ��ж��д������������������з��򡢼��ٶ�(����)�����ߡ��ų�������(�ٽ���)���¶ȵȡ�

  ���򴫸�����   Sensor.TYPE_ORIENTATION

  ���ٶ�(����)��������Sensor.TYPE_ACCELEROMETER

  ���ߴ�����:    Sensor.TYPE_LIGHT

  �ų���������   Sensor.TYPE_MAGNETIC_FIELD

  ����(�ٽ���)��������Sensor.TYPE_PROXIMITY

  �¶ȴ�������   Sensor.TYPE_TEMPERATURE

//��ȡĳ�����͵ĸ�Ӧ��

Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

//ע���������ȡ�������仯ֵ

sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);

�������������Ϊ�����ʣ���졢��Ϸ����ͨ���û����档��Ӧ�ó��������ض��Ĳ�����ʱ����ʵֻ�ǶԴ�������ϵͳ��һ�����飬����֤�ض��Ĳ����ʿ��á�

��죺 SensorManager.SENSOR_DELAY_FASTEST

����ӳ٣�һ�㲻���ر����еĴ����Ƽ�ʹ�ã�����ģʽ��������ֻ������������ģ����ڴ��ݵ�Ϊԭʼ���ݣ��㷨������ý���Ӱ����Ϸ�߼���UI�����ܡ�

��Ϸ�� SensorManager.SENSOR_DELAY_GAME

��Ϸ�ӳ٣�һ����������ʵʱ�Խϸߵ���Ϸ��ʹ�øü���

��ͨ�� SensorManager.SENSOR_DELAY_NORMAL 

��׼�ӳ٣�����һ����������EASY�������Ϸ����ʹ�ã������͵Ĳ����ʿ��ܶ�һЩ��������Ϸ����֡����

�û����棺 SensorManager.SENSOR_DELAY_UI

 

//��ȡ��Ӧ��������     

SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);    
<p>//��ȡ���򴫸���               
Sensor orientationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);              
sensorManager.registerListener(sensorEventListener, orientationSensor, SensorManager.SENSOR_DELAY_NORMAL);</p><p>//��ȡ���ٶȴ�����               
Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);              
sensorManager.registerListener(sensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL); </p>   

 */
public class SensorUtils {
	Context context;
	Sensor sensor ;       
	Handler handler;
    private float x, y, z;
    
    public SensorUtils(Context context){
    	this.context=context;
    	//��ϵͳ�����л�ô�����������       
        SensorManager sm = (SensorManager)context. getSystemService(Context.SENSOR_SERVICE);       
        // ע��listener�������������Ǽ��ľ�ȷ��       
        
        //��title����ʾ�����������ı仯       
        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }
    public StringBuilder getAllSensor(Context context){

    	StringBuilder sb=new StringBuilder();
        //��ϵͳ�����л�ô�����������       
        SensorManager sm = (SensorManager)context. getSystemService(Context.SENSOR_SERVICE);       
       
        //�Ӵ������������л��ȫ���Ĵ������б�       
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);       
       
        //��ʾ�ж��ٸ�������       
        sb.append("�������ֻ���" + allSensors.size() + "�������������Ƿֱ��ǣ�\n");       
       
        //��ʾÿ���������ľ�����Ϣ       
        for (Sensor s : allSensors) {       
       
        	String tempString=( "\n" + "  �豸���ƣ�" + s.getName() + "\n" + "  �豸�汾��" + s.getVersion() + "\n" + "  ��Ӧ�̣�"       
                    + s.getVendor() + "\n");       
       
            switch (s.getType()) {       
            case Sensor.TYPE_ACCELEROMETER:       
            	sb.append( s.getType() + " ���ٶȴ�����accelerometer" + tempString);       
                break;       
            case Sensor.TYPE_GRAVITY:       
            	sb.append( s.getType() + " ����������gravity API 9" + tempString);       
                break;       
            case Sensor.TYPE_GYROSCOPE:       
            	sb.append( s.getType() + " �����Ǵ�����gyroscope" + tempString);       
                break;       
            case Sensor.TYPE_LIGHT:       
            	sb.append( s.getType() + " �������ߴ�����light" + tempString);       
                break;       
            case Sensor.TYPE_LINEAR_ACCELERATION:       
            	sb.append( s.getType() + " ���Լ�����LINEAR_ACCELERATION API 9" + tempString);       
                break;       
            case Sensor.TYPE_MAGNETIC_FIELD:       
            	sb.append( s.getType() + " ��ų�������magnetic field" + tempString);       
                break;       
            case Sensor.TYPE_ORIENTATION:       
            	sb.append( s.getType() + " ���򴫸���orientation" + tempString);       
                break;       
            case Sensor.TYPE_PRESSURE:       
            	sb.append( s.getType() + " ѹ��������pressure" + tempString);       
                break;       
            case Sensor.TYPE_PROXIMITY:       
            	sb.append( s.getType() + " ���봫����proximity" + tempString);       
                break;       
            case Sensor.TYPE_ROTATION_VECTOR:       
            	sb.append( s.getType() + " ��ת����ROTATION" + tempString);       
                break;       
            case Sensor.TYPE_TEMPERATURE:       
            	sb.append( s.getType() + " �¶ȴ�����temperature" + tempString);       
                break;       
            default:       
            	sb.append( s.getType() + " δ֪������" + tempString);       
                break;       
            }       
        }  
        return sb;
    }
    
    public void startListener(Context context, Handler handler) {
    	this.handler=handler;
    	//��ϵͳ�����л�ô�����������       
        SensorManager sm = (SensorManager)context. getSystemService(Context.SENSOR_SERVICE);       
        // ע��listener�������������Ǽ��ľ�ȷ��       
        
    	sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);       
       
    } 
    
    public void startListener(Context context,SensorEventListener lsn) {
    	//��ϵͳ�����л�ô�����������       
        SensorManager sm = (SensorManager)context. getSystemService(Context.SENSOR_SERVICE);       
        // ע��listener�������������Ǽ��ľ�ȷ��       
        
    	sm.registerListener(lsn, sensor, SensorManager.SENSOR_DELAY_GAME);       
       
    } 
    public void stopListener(Context context){

    	//��ϵͳ�����л�ô�����������       
        SensorManager sm = (SensorManager)context. getSystemService(Context.SENSOR_SERVICE); 
        sm.unregisterListener(lsn);
    }
    
    
    SensorEventListener lsn = new SensorEventListener() {       
       public void onSensorChanged(SensorEvent e) {  
           x = e.values[SensorManager.DATA_X];       
           y = e.values[SensorManager.DATA_Y];       
           z = e.values[SensorManager.DATA_Z];
           Message msg=handler.obtainMessage(0, e.sensor.getName()+"  "+"x=" + x + "," + "y=" +  y + "," + "z="+  z);
           handler.sendMessage(msg);
       }       
  
       public void onAccuracyChanged(Sensor s, int accuracy) {
           Message msg=handler.obtainMessage(0, "onAccuracyChanged " + s.getName()+ "," + "getPower=" + s.getPower() + "," + "accuracy="+ accuracy);
           handler.sendMessage(msg);
       }
   };
}
