package com.android.moshineifo;

/**���������Ҫ��Android�ֻ�����ϵͳ�в鿴�����ϵͳ��Ϣ�������ڴ��С��ϵͳ���ܵȵȡ�����Ĳ�������Ӧ������������?�������ҾͿ��Գ�ֵ�������һ�����������������Ƕ���һ�ֻ�ϵͳ���˽⡣
 ������˵˵��β鿴Androidϵͳ��Ϣ�е��ֻ����ԡ��ڴ�ʹ���������Ϣ����Щ��J2me���Ѿ����������ˣ���Android������?��ʵҲ�ܼ򵥣�ֱ�ӿ�����Ĵ����ok����
 */
import android.app.ActivityManager;
import android.content.Context;

public class SystemProperty {
	/**
	 * ��ʾ���ݴ��
	 */
	private StringBuffer buffer;

	public String getInfo(Context context) {
		initProperty();
		getMemoryInfo(context);
		return buffer.toString();
	}

	// Androidϵͳ��Ϣ����鿴����
	private void initProperty() {
		initProperty("java.vendor.url", "java.vendor.url");
		initProperty("java.class.path", "java.class.path");
		initProperty("user.home", "user.home");
		initProperty("java.class.version", "java.class.version");
		initProperty("os.version", "os.version");
		initProperty("java.vendor", "java.vendor");
		initProperty("user.dir", "user.dir");
		initProperty("user.timezone", "user.timezone");
		initProperty("path.separator", "path.separator");
		initProperty(" os.name", " os.name");
		initProperty("os.arch", "os.arch");
		initProperty("line.separator", "line.separator");
		initProperty("file.separator", "file.separator");
		initProperty("user.name", "user.name");
		initProperty("java.version", "java.version");

		initProperty("java.home", "java.home");
	}

	private void initProperty(String description, String propertyStr) {
		if (buffer == null) {
			buffer = new StringBuffer();
		}
		buffer.append("\n" + description).append("=:");
		buffer.append(System.getProperty(propertyStr)).append(" ");
	}

	// Androidϵͳ��Ϣ���ڴ�����鿴
	private void getMemoryInfo(Context context) {
		final ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo outInfo = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(outInfo);
		buffer.append(" ʣ���ڴ�:").append(outInfo.availMem >> 10).append("k");
		buffer.append(" ʣ���ڴ�:").append(outInfo.availMem >> 20).append("M");
		buffer.append(" �Ƿ��ڵ��ڴ�״̬:").append(outInfo.lowMemory);
	}
}
// ˳����ʾһ�£�AndroidϵͳҲ�ṩ�ˣ�Runtime�࣬���Ǹ����ȡ������ϢJava�������ʹ�������Ϣ�����Ͼ������Ƕ�Android
// ϵͳ��Ϣ�ľ���鿴�����̡̳�
