package com.android.accenture.aemm.dome;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public final class Logger {
	private String LOGCAT="TAG";
	@SuppressWarnings("unchecked")
	private Class logClass;
	private FileWriter logfile;
	private static SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	private static Date date=new Date();


	Logger(Class<?> cla){
//		LOGCAT=cla.getName();
		Log.v("TAG", "className:"+cla.getName());
		logClass=cla;
		try {
			logfile=new FileWriter("/sdcard/data/aemm/socket.log",true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void d(String debug){
		Log.d(LOGCAT,"debug:"+debug);
		writeFile(" debug:"+debug);
	}
	public void d(String debug, IOException e) {
		Log.d(LOGCAT,"debug:"+debug+e.getMessage());
		writeFile(" debug:"+debug+e.getMessage());
	}
	public void w(String warn){
		Log.w(LOGCAT,"warn:"+warn);
		writeFile(" warn:"+warn);
	}
	public void e(String error,Exception e){
		Log.e(LOGCAT,"eror:"+error+e.getMessage());
		writeFile(" eror:"+error+"\n"+e.getMessage()+e.getMessage());
	}
	public void e(String error) {
		Log.e(LOGCAT,"eror:"+error);
		writeFile(" eror:"+error);
	}
	public void i(String info) {
		Log.i(LOGCAT,"info:"+info);
		writeFile(" info:"+info);
	}
	
	private void writeFile(String str){
		try {
			date.setTime(System.currentTimeMillis());
			String time=df.format(date);
			if(logfile!=null){
			logfile.write(time+" - "+LOGCAT+str+"\n");
			logfile.flush();
			}
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	public void close(){
		try {
			if(logfile!=null)
			logfile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
