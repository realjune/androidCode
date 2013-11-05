package com.act.sctc.db;

import java.util.HashMap;
import java.util.Map;

import android.net.Uri;

public class picutrue  extends DatabaseColumn {

	public static final String TABLE_NAME			= 	"picture";
	
	public static final String DATA="data"				;
	public static final String KEY_ID="phone_id"				;
	public static final String CATEGORY="category"			;
	
	public static final Uri		CONTENT_URI			=	Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);
	private static final Map<String,String> mColumnMap = new HashMap<String,String>();
	 static {
		 
		 mColumnMap.put(_ID, "integer primary key autoincrement");
		 mColumnMap.put(DATA, "string not null");
		 mColumnMap.put(KEY_ID, "int not null");
		 mColumnMap.put(CATEGORY, "int not null");
	 }
	 
	 /** selection all,default */
		public static final String SELECTION[] = { _ID, DATA,KEY_ID,CATEGORY };
	 
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
