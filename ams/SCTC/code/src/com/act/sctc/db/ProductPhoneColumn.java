package com.act.sctc.db;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;


public class ProductPhoneColumn extends DatabaseColumn {

	public static final String TABLE_NAME			= 	"product_phone";
	
	public static final String NAME="name"				;
	public static final String ICON="icon"				;
	public static final String INTRODUCE="introduce"			;
	public static final String PRICE="price"				;
	public static final String AMOUNT="amount"				;
	public static final String CATEGORY="category";
	public static final String PRODUCER="producer";
	public static final String SCREEN_WITH="screenWith";
	public static final String SCREEN_HEIGHT="screenHeight";
	
	public static final Uri		CONTENT_URI			=	Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
	private static final Map<String,String> mColumnMap = new HashMap<String,String>();
	 static {
		 
		 mColumnMap.put(_ID, "integer primary key autoincrement");
		 mColumnMap.put(NAME, "text not null");
		 mColumnMap.put(ICON, "timestamp");
		 mColumnMap.put(INTRODUCE, "text");
		 mColumnMap.put(PRICE, "int not null");
		 mColumnMap.put(AMOUNT, "text");
		 mColumnMap.put(CATEGORY, "text not null");
		 mColumnMap.put(PRODUCER, "string not null");
		 mColumnMap.put(SCREEN_WITH, "int not null");
		 mColumnMap.put(SCREEN_HEIGHT, "int not null");
	 }
	 
	 /** selection all,default */
		public static final String SELECTION[] = { _ID, NAME,ICON,INTRODUCE, PRICE, AMOUNT, CATEGORY ,PRODUCER,SCREEN_WITH,SCREEN_HEIGHT};
	 
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

}
