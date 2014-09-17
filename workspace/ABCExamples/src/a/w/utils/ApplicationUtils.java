package a.w.utils;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

/**
 * @author Administrator ��Android�app��process��task��������ͬ�ĸ���.
 * 
 *         process��һ���̳���Linux�ĸ��һ��һ��app����һ��uid��һ�������ؿ�һ��process��
 * 
 *         ���ǣ�Ҳ���ж��app����һ��process����uid�ģ���������Լ�ָ����
 * 
 *         task��һ��activity��ջ������"����"�������Զ��App����һ����ͬһprocess�У��е�activity��
 * 
 *         ActivityManager����Ի�ȡ������Ϣ�����£�
 * 
 *         1.getRecentTasks() �������task��HOME�������ῴ����� 2.getRunningAppProcesses()
 *         �����е���Ϊapp������process 3.getRunningServices() �����еĺ�̨����
 *         4.getRunningTasks() �����е����� ���һ��Activity ��mainActivity��������Ϊtask��root
 *         activity�򿪵�
 *         �����Ǳ����Task��Activity���ã���ômainActivity��Ӧ��process�ǿ��ŵģ���ʱ�����Ƿ�Ҫ��ʾ���أ�
 * 
 *         ���⣬���һ��appֻ��service���Ŷ���Task�в��������app��Activity���㲻�㿪���أ�
 */
public class ApplicationUtils {
	/**
	 * �жϵ�ǰ�����Ƿ���ջ��
	 * 
	 * @param packageName
	 * @param context
	 * @return
	 */
	public static boolean isTopActivy(String packageName, Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		String currentPackageName = cn.getPackageName();

		return (currentPackageName != null && currentPackageName
				.equals(packageName));
	}

	/**
	 * �ж�AndroidӦ���Ƿ���ǰ̨
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAppOnForeground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getPackageName();
		List<RecentTaskInfo> appTask = activityManager.getRecentTasks(
				Integer.MAX_VALUE, 1);

		if (appTask == null) {
			return false;
		}

		if (appTask.get(0).baseIntent.toString().contains(packageName)) {
			return true;
		}
		return false;
	}

	/**
	 * ��ȡ������еĳ����б��������񣩣�����home����ʾЧ��
	 * 
	 * @param context
	 * @return
	 */
	public static String getTaskList(Context context) {
		String apps = "";
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = context.getPackageManager();
		try {
			List<RecentTaskInfo> list = am.getRecentTasks(Integer.MAX_VALUE, 0);
			for (RecentTaskInfo ti : list) {
				Intent intent = ti.baseIntent;
				ResolveInfo resolveInfo = pm.resolveActivity(intent, 0);
				if (resolveInfo != null) {
					// resolveInfo.loadIcon(pm);
				}
			}
			return apps;
		} catch (SecurityException se) {
			se.printStackTrace();
			return apps;
		}
	}

	/**
	 * ��ȡAndroid�ֻ��ڰ�װ����������
	 * 
	 * @param context
	 * @return
	 */
	private static List<String> getAllTheLauncher(Context context) {
		List<String> names = null;
		PackageManager pkgMgt = context.getPackageManager();
		Intent it = new Intent(Intent.ACTION_MAIN);
		it.addCategory(Intent.CATEGORY_HOME);
		List<ResolveInfo> ra = pkgMgt.queryIntentActivities(it, 0);
		if (ra.size() != 0) {
			names = new ArrayList<String>();
		}
		for (int i = 0; i < ra.size(); i++) {
			String packageName = ra.get(i).activityInfo.packageName;
			names.add(packageName);
		}
		return names;
	}

	/**
	 * Android �жϳ���ǰ��̨״̬
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isLauncherRunnig(Context context) {
		boolean result = false;
		List<String> names = getAllTheLauncher(context);
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appList = mActivityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo running : appList) {
			if (running.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				for (int i = 0; i < names.size(); i++) {
					if (names.get(i).equals(running.processName)) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}
	/**
	 * ��ȡ�������еĳ�����Ϣ
	 * 
	 * @param context
	 * @return
	 */
	// public static List<Programe> getRunningProcess(Context context){
	// PackagesInfo pi = PackagesInfo.getInstance(context);
	//
	// ActivityManager am = (ActivityManager)
	// context.getSystemService(Context.ACTIVITY_SERVICE);
	// List<RunningAppProcessInfo> run = am.getRunningAppProcesses();
	// PackageManager pm =context.getPackageManager();
	// List<Programe> list = new ArrayList<Programe>();
	// Programe pr = new Programe();
	// try {
	// for(RunningAppProcessInfo ra : run){
	// if(ra.processName.equals("system") ||
	// ra.processName.equals("com.Android.phone")){ //���Ը�����Ҫ���ε�һЩ����
	// continue;
	// }
	// pr = new Programe();
	// pr.setIcon(pi.getInfo(ra.processName).loadIcon(pm));
	// pr.setName(pi.getInfo(ra.processName).loadLabel(pm).toString());
	// Log.v("tag","icon = " + pr.getIcon() +"name=" + pr.getName());
	// list.add(pr);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return list;
	// }
}
