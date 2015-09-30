package com.qin.ammp;

import java.util.ArrayList;
import java.util.List;

import wu.a.lib.app.ActivityManagerUtils;
import wu.a.lib.app.PackageManagerUtils;
import wu.a.lib.app.RunningAppInfo;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class BrowseRunningAppActivity extends Activity {

	private static String TAG = "BrowseRunningAppActivity";

	private ListView listview = null;

	private List<RunningAppInfo> mlistAppInfo = null;
	private TextView tvInfo = null;

	private PackageManager pm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browse_app_list);

		listview = (ListView) findViewById(R.id.listviewApp);
		tvInfo = (TextView) findViewById(R.id.tvInfo);

		mlistAppInfo = new ArrayList<RunningAppInfo>();

		// ��ѯĳһ�ض����̵�����Ӧ�ó���
		Intent intent = getIntent();
		// �Ƿ��ѯĳһ�ض�pid��Ӧ�ó���
		int pid = intent.getIntExtra("EXTRA_PROCESS_ID", -1);

		if (pid != -1) {
			// ĳһ�ض������������������е�Ӧ�ó���
			mlistAppInfo = querySpecailPIDRunningAppInfo(intent, pid);
		} else {
			// ��ѯ�����������е�Ӧ�ó�����Ϣ�� �����������ڵĽ���id�ͽ�����
			tvInfo.setText("�����������е�Ӧ�ó�����-------");
			mlistAppInfo = ActivityManagerUtils.queryAllRunningAppInfo(PackageManagerUtils.getPackageManager(this),
					ActivityManagerUtils.getActivityManager(this));
		}
		BrowseRunningAppAdapter browseAppAdapter = new BrowseRunningAppAdapter(this, mlistAppInfo);
		listview.setAdapter(browseAppAdapter);
	}

	// ĳһ�ض������������������е�Ӧ�ó���
	private List<RunningAppInfo> querySpecailPIDRunningAppInfo(Intent intent, int pid) {

		String[] pkgNameList = intent.getStringArrayExtra("EXTRA_PKGNAMELIST");
		String processName = intent.getStringExtra("EXTRA_PROCESS_NAME");

		// update ui
		tvInfo.setText("����idΪ" + pid + " ���е�Ӧ�ó�����  :  " + pkgNameList.length);
		return ActivityManagerUtils.querySpecailPIDRunningAppInfo(pm, pkgNameList, processName, pid);
	}
}