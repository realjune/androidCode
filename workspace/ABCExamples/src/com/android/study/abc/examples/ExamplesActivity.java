package com.android.study.abc.examples;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.study.abc.examples.exit.ExitApplication;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class ExamplesActivity extends Activity implements OnItemClickListener {
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.examples);
	        
//	        Notification n = new Notification(R.drawable.icon, "Service����", System.currentTimeMillis());   
//	        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, ExitApplication.class), 0);   
//	        n.setLatestEventInfo(this, "�������", "��������", contentIntent);   
//	        nManager.notify(NOTIFICATION_ID, n); // ����������
	        
	        Intent intent = getIntent();
	        String path = intent.getStringExtra("com.example.android.apis.Path");
	        
	        if (path == null) {
	            path = "com.android.study.abc";
	        }
	        
	        ListView lView=(ListView) findViewById(R.id.lv_intent);
	        lView.setOnItemClickListener(this);
//	        Log.v("[mylog]","path:"+path);
	        lView.setAdapter(new SimpleAdapter(this, getData(path),
	                android.R.layout.simple_list_item_1, new String[] { "title" },
	                new int[] { android.R.id.text1 }));
	        lView.setTextFilterEnabled(true);
	    }

	    /**����path��acitivtyIntent�б�.
	     * @param prefix path 
	     * @return Intent�б�
	     */
	    protected List getData(String prefix) {
	    	//Inent�б�
	        List<Map> myData = new ArrayList<Map>();

	        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
//	        mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);
	        mainIntent.addCategory("com.android.stuly.abc.examples");

	        PackageManager pm = getPackageManager();
	        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

	        if (null == list)
	            return myData;

	        String[] prefixPath;
	        
	        if (prefix.equals("")) {
	            prefixPath = null;
	        } else {
	            prefixPath = prefix.split("\\.");
	        }
	        //���ڴ��·�����Է��ظ�·������.(�統ǰĳ��·������Ӽ�Activity,��ôֻ��Ҫһ����Ŀ¼����)
	        Map<String, Boolean> entries = new HashMap<String, Boolean>();
	        //��������ResolveInfo
	        for(ResolveInfo info:list){
	        	String panStr=info.activityInfo.name;
	        	if(panStr==null){
	        		panStr="";
	        	}
	        	String activityPackage=panStr.substring(0, panStr.lastIndexOf("."));
	        	if(prefix.equals(activityPackage)){//�жϵ�ǰ��·������activityҳ��
//	        		Log.v("[mylog]","panStr:"+panStr);
	        		CharSequence labelSeq = info.loadLabel(pm);
		            String label = labelSeq != null? labelSeq.toString(): info.activityInfo.name;
		            addItem(myData, label, activityIntent(
                            info.activityInfo.applicationInfo.packageName,
                            info.activityInfo.name));
	        		
	        	}else if(prefix.length()==0||panStr.startsWith(prefix)){//����Ǹ�Ŀ¼���ǵ�ǰ�Ӽ�Ŀ¼
		        	String []panArrStr=panStr.split("\\.");
	        		
	        		String nextLabel=prefixPath==null?panArrStr[0]:panArrStr[prefixPath.length];
	        		 if (entries.get(nextLabel) == null) {
	        			//�����Ŀ¼��������������Ŀ¼��
	        			 addItem(myData, nextLabel, browseIntent(prefix.equals("") ? nextLabel : prefix + "." + nextLabel));
	                        entries.put(nextLabel, true);//��¼��Ŀ¼������
	                    }
	        	}
	        }
	        Collections.sort(myData, sDisplayNameComparator);
	        return myData;
	    }

	    /**
	     * �Ƚ���
	     */
	    private final static Comparator<Map> sDisplayNameComparator = new Comparator<Map>() {
	        private final Collator   collator = Collator.getInstance();
	        public int compare(Map map1, Map map2) {
	            return collator.compare(map1.get("title"), map2.get("title"));
	        }
	    };

	    /**����һ���µ�Activity
	     * @param pkg Application����
	     * @param componentName activityInfo����
	     * @return ActivityIntent
	     */
	    protected Intent activityIntent(String pkg, String componentName) {
	        Intent result = new Intent();
	        result.setClassName(pkg, componentName);
	        return result;
	    }
	    
	    /**����һ���µ�Activity���ApiDemosָ��pathĿ¼�µ�activity
	     * @param path ��·��
	     * @return һ����Acivitylist��initent
	     */
	    protected Intent browseIntent(String path) {
	        Intent result = new Intent();
	        result.setClass(this, ExamplesActivity.class);
	        result.putExtra("com.example.android.apis.Path", path);
	        return result;
	    }

	    /**����һ��intent����б�.
	     * ���������title--name,intent--Intent.
	     * @param data �������ļ���.
	     * @param name title.
	     * @param intent title��Ӧ��Intent.
	     */
	    protected void addItem(List<Map> data, String name, Intent intent) {
	        Map<String, Object> temp = new HashMap<String, Object>();
	        temp.put("title", name);
	        temp.put("intent", intent);
	        data.add(temp);
	    }

	    /* ListView��������¼�,������activity.
	     * (non-Javadoc)
	     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	     */
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Map map = (Map) arg0.getItemAtPosition(arg2);
	        Intent intent = (Intent) map.get("intent");
	        startActivity(intent);
		}
}