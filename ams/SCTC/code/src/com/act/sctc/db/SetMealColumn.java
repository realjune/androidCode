package com.act.sctc.db;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.act.sctc.been.SetMeal;
import com.act.test.Utils;

public class SetMealColumn extends DatabaseColumn {
	public static final String TAG=SetMealColumn.class.getSimpleName();

	/**表名*/
	public static final String TABLE_NAME			= 	"set_meal";
	/**套餐名称*/
	public static final String NAME					=	"name";
	/**月租,单位：元*/
	public static final String MONTHLY_REN			=	"monthly_rent";
	/**国内流量,单位:MB*/
	public static final String DOMESTIC_TRAFFIC		=	"Domestic_traffic";
	/**国内语音*/
	public static final String FREE_INLAND_CALL		=	"free_inland_call";
	/**国内语音描述*/
	public static final  String FREE_INLAND_CALL_INTRODUCE="free_inland_call_introduce";
	/**WIFI时长,单位h*/
	public static final  String FREE_WIFI_H			="free_wifi_h";
	/**短信条数*/
	public static final  String FREE_SMS			="free_sms";
	/**彩信条数*/
	public static final  String FREE_MMS			="free_mms";
	/**超出语音*/
	public static final  String EXCEED_CALL			="exceed_call";
	/**超出流量*/
	public static final  String EXCEED_TRAFFIC		="exceed_traffic";
	/**免费接听范围*/
	public static final  String FREE_ANSWER_RANGE	="free_answer_range";
	/**赠送业务*/
	public static final  String FREE_SERVICES		="free_services";
	
	public static final Uri		CONTENT_URI			=	Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
	
	private static final Map<String,String> mColumnMap = new HashMap<String,String>();
	 static {
		 
		 mColumnMap.put(_ID, "integer primary key autoincrement");
		 mColumnMap.put(NAME, "text not null");
		 mColumnMap.put(MONTHLY_REN, "int not null");
		 mColumnMap.put(DOMESTIC_TRAFFIC, "int not null");
		 mColumnMap.put(FREE_INLAND_CALL, "int");
		 mColumnMap.put(FREE_INLAND_CALL_INTRODUCE, "text not null");
		 mColumnMap.put(FREE_WIFI_H, "int not null");
		 mColumnMap.put(FREE_SMS, "int not null");
		 mColumnMap.put(FREE_MMS, "int not null");
		 mColumnMap.put(EXCEED_CALL, "text not null");
		 mColumnMap.put(EXCEED_TRAFFIC, "text not null");
		 mColumnMap.put(FREE_ANSWER_RANGE, "text not null");
		 mColumnMap.put(FREE_SERVICES, "text not null");
	 }
	 
	 /** selection all,default */
	public static final String SELECTION[] = {NAME,MONTHLY_REN,DOMESTIC_TRAFFIC,FREE_INLAND_CALL,FREE_INLAND_CALL_INTRODUCE,FREE_WIFI_H,FREE_SMS,FREE_MMS,EXCEED_CALL,EXCEED_TRAFFIC,FREE_ANSWER_RANGE,FREE_SERVICES };
	 
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return TABLE_NAME;
	}

	@Override
	public Uri getTableContent() {
		// TODO Auto-generated method stub
		return CONTENT_URI;
	}

	@Override
	protected Map<String, String> getTableMap() {
		// TODO Auto-generated method stub
		return mColumnMap;
	}
	
	public static ContentValues toContentValue(SetMeal sm){
		// 实例化ContentValues
		ContentValues values = new ContentValues();
		
		/**套餐名称*/
		values.put(SetMealColumn.NAME,sm.getName());
		/**月租*/
		values.put(SetMealColumn.MONTHLY_REN,sm.getMonthlyRent());
		/**国内流量*/
		values.put(SetMealColumn.DOMESTIC_TRAFFIC,sm.getDomesticTraffic());
		/**国内语音*/
		values.put(SetMealColumn.FREE_INLAND_CALL,sm.getFreeInlandCall());
		/**国内语音描述*/
		values.put(SetMealColumn.FREE_INLAND_CALL_INTRODUCE,sm.getFreeInlandCallIntroduce());
		/**WIFI时长,单位h*/
		values.put(SetMealColumn.FREE_WIFI_H,sm.getFreeWifiHour());
		/**短信条数*/
		values.put(SetMealColumn.FREE_SMS,sm.getFreeSMS());
		/**彩信条数*/
		values.put(SetMealColumn.FREE_MMS,sm.getFreeMMS());
		/**超出语音*/
		values.put(SetMealColumn.EXCEED_CALL,sm.getExceedCall());
		/**超出流量*/
		values.put(SetMealColumn.EXCEED_TRAFFIC,sm.getExceedTraffic());
		/**免费接听范围*/
		values.put(SetMealColumn.FREE_ANSWER_RANGE,sm.getFreeAnswerRange());
		/**赠送业务*/
		values.put(SetMealColumn.FREE_SERVICES,sm.getFreeServices());
		return values;
	}
	public static class Test{
	
	public static void writeDB(Context content){
		SetMeal sm=new SetMeal();
		sm.setName("乐享3G上网版");
		sm.setMonthlyRent(89);
		sm.setDomesticTraffic(400);
		sm.setFreeInlandCall(240);
		sm.setFreeInlandCallIntroduce("市话、国内长话（含IP）、国内漫游240共分钟。");
		sm.setFreeWifiHour(30);
		sm.setFreeSMS(30);
		sm.setFreeMMS(6);
		sm.setExceedCall("市话、国内长话（含IP）、国内漫游主叫0.15元/分钟，其他按标准计费资费计收");
		sm.setExceedTraffic("国内上网0.30元/MB,20G自动断网");
		sm.setFreeAnswerRange("全国");
		sm.setFreeServices("来线、彩铃月功能和免费186邮箱");
		
		// 获得ContentResolver，并插入
		Log.d(TAG,"write "+content.getContentResolver().insert(SetMealColumn.CONTENT_URI, toContentValue(sm)));
	}
	
	public static void readDB(Context context){
		Uri uri = ContentUris.withAppendedId(SetMealColumn.CONTENT_URI, 0);
		Cursor cursor =
				//dBHelper.query(true, ProductPhoneColumn.TABLE_NAME, ProductPhoneColumn.SELECTION, null, null, null, null, null, null, null);
//				dBHelper.query(ProductPhoneColumn.TABLE_NAME, ProductPhoneColumn.SELECTION, null, null, null, null, null, "3 offset 3");
				context.getContentResolver().query(SetMealColumn.CONTENT_URI, SetMealColumn.SELECTION, /*"_id>3 and _id<10"*/null, null, null);
		if(cursor==null||!cursor.moveToFirst()){
			Log.d(TAG,"null from db");
			return ;
		}else{
			Log.d(TAG,Utils.toString(cursor));
		}
		cursor.close();
	}
	public static void update(Context context){
		SetMeal sm=new SetMeal();
		sm.setName("乐享5G上网版");
//		sm.setMonthlyRent(89);
//		sm.setDomesticTraffic(400);
//		sm.setFreeInlandCall(240);
//		sm.setFreeInlandCallIntroduce("市话、国内长话（含IP）、国内漫游240共分钟。");
//		sm.setFreeWifiHour(30);
//		sm.setFreeSMS(30);
//		sm.setFreeMMS(6);
//		sm.setExceedCall("市话、国内长话（含IP）、国内漫游主叫0.15元/分钟，其他按标准计费资费计收");
//		sm.setExceedTraffic("国内上网0.30元/MB,20G自动断网");
//		sm.setFreeAnswerRange("全国");
//		sm.setFreeServices("来线、彩铃月功能和免费186邮箱");
		ContentValues values = new ContentValues();//toContentValue(sm);
		
		/**套餐名称*/
		values.put(SetMealColumn.NAME,"乐享5G上网版");
		// 更新ID为1的记录
		Uri uri = ContentUris.withAppendedId(SetMealColumn.CONTENT_URI, 1);
		// 获得ContentResolver，并更新
		context.getContentResolver().update(uri, values, null, null);
	}
	public static void deleteDB(Context context){
		// 删除ID为1的记录
		Uri uri = ContentUris.withAppendedId(SetMealColumn.CONTENT_URI, 1);
		// 获得ContentResolver，并删除
		Log.d(TAG,"del "+context.getContentResolver().delete(SetMealColumn.CONTENT_URI, null, null));
	}
	}
}
