package com.android.study.abc.examples.memory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

import a.w.utils.Logger;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;

import com.android.internal.util.MemInfoReader;

/**
 * <pre>
 *  @author Administrator
 *  清除缓存
 * 
 * �?�?在Android中Settings的源码中使用了PackageManager的deleteApplicationCacheFiles()方法清除缓存，但是作为第三方软件使用这个函数难度很大，
 * 我们在PackageManager中还发现freeStorageAndNotify()方法亦可实现清除缓存，�?�且第三方软件使用时难度相对较小。
 * 因此，我们使用freeStorageAndNotify()方法来实现缓存的清理�?
 * 
 * �?�?使用freeStorageAndNotify()时需要在AndroidManifest.xml中声明permission
 * 
 * <!-- 清除缓存时需要此权限 --><uses-permission android:name="android.permission.CLEAR_APP_CACHE" />�?�?该函数的具体使用可以参�?�源码MyFloatView.java中的clearCache()函数�?
 * 
 *  
 *  �?进程
 * 
 * �?�?在ActivityManager.java中为我们提供了killBackgroundProcesses(String packageName)函数来杀死进程具体如何杀死进程可以参考源码MyFloatView.java中的killBackgroundProcess()函数。杀进程�?要在AndroidManifest中声明如下权�?
 * 
 * <!-- �?死进程时�?要该权限 -->
 * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES" />
 * 
 * 统计RAM可用内存大小
 * 
 * �?�?A:Android中的MemInfoReader类�?�过读取/proc/meminfo实现了内存大小相关的函数，但第三方程序无法调用，我们将直接将MemInfoReader.java直接拷贝到项目中，作适当修改即可使用�?
 * 
 * �?�?B:通过MemoryInfo和ActivityManager获得可用Ram内存大小
 * ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
 *         MemoryInfo mi = new MemoryInfo();
 *         am.getMemoryInfo(mi); // mi.availMem; 当前系统的可用内�?
 *         Log.e("tag", "getMemoryInfo: " + mi.availMem);
 * </pre>
 */
@SuppressLint("HandlerLeak")
public class MemoryStateUpdate {

	protected static final String TAG = "MemoryStateUpdate";
	long SECONDARY_SERVER_MEM;
	MemInfoReader mMemInfoReader = new MemInfoReader();
	private long mMemavail;
	private long mMemtotal;
	private ActivityManager mAm = null;
	private PackageManager mPackageManager = null;
	private long totalCacheSize = 0l;

	private void initMemoryInfo(Context context) {
		if (mAm == null) {
			mAm = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
		}
		mMemInfoReader.readMemInfo();
		ActivityManager.MemoryInfo meminfo = new ActivityManager.MemoryInfo();
		mAm.getMemoryInfo(meminfo);
		SECONDARY_SERVER_MEM = meminfo.threshold;
		long availMem = mMemInfoReader.getFreeSize()
				+ mMemInfoReader.getCachedSize();
		if (availMem < 0) {
			availMem = 0;
		}
		long totalMem = mMemInfoReader.getTotalSize() - SECONDARY_SERVER_MEM;

		mMemavail = availMem;
		mMemtotal = totalMem;
	}

	private int mCurrentProgress = 0;

	public String getMemPercentage(Context context) {
		// 获得当前可用内存
		initMemoryInfo(context);
		double availMem = mMemInfoReader.getFreeSize()
				+ mMemInfoReader.getCachedSize();
		double per = availMem / mMemtotal * 100;
		return String.valueOf(100 - (int) per) + "%";
	}

	public int getMemPercentageInteger(Context context) {
		// 获得当前可用内存
		initMemoryInfo(context);
		double availMem = mMemInfoReader.getFreeSize()
				+ mMemInfoReader.getCachedSize();
		double per = availMem / mMemtotal * 100;
		mCurrentProgress = 100 - (int) per;
		return mCurrentProgress;
	}

	// public int getSubMemPrecentage(Context context){
	// new Thread(new Runnable() {
	//
	// @Override
	// public void run() {
	// Logger.i("SUB", "线程启动:"+mCurrentProgress);
	// for (int i = mCurrentProgress; i > 0; i--) {
	// Logger.i("SUB", mCurrentProgress+"--我特么的正在减少");
	// Message msg = mCleanAnimationHandler.obtainMessage();
	// CleanObj o = new CleanObj();
	// o.currentProgress = i;
	// o.percentage = i + "%";
	// msg.what = ON_CLEAN_PROGRESS_SUB;
	// msg.obj = o;
	// mCleanAnimationHandler.sendMessage(msg);
	// }
	//
	// }
	// }).start();
	// return 0;
	// }
	public int getAddMemPrecentage(Context context) {

		return 0;
	}

	public void updateMemoryInfo(Context context) {
		initMemoryInfo(context);
	}

	public void updateMemoryInfo() {

	}

	public long getAvailMemory() {
		return mMemavail;
	}

	public long getTotalMemory() {
		return mMemtotal;
	}

	public int getRecentTasks() {

		List<ActivityManager.RecentTaskInfo> recentTasks = mAm.getRecentTasks(
				21, ActivityManager.RECENT_WITH_EXCLUDED
						| ActivityManager.RECENT_IGNORE_UNAVAILABLE);

		return recentTasks.size();

	}

	private String mComName;
	private Context mContext;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case ON_CLEANING:
				Logger.i(TAG, "ON_CLEANING");
				CleanObj clean = (CleanObj) msg.obj;
				CleanEvent.getInstance().fireOnProgressChange(
						clean.currentProgress, clean.maxCount,
						clean.percentage, clean.processName, mComName);
				break;
			case ON_CLEAN_DONE:
				Logger.i(TAG, "ON_CLEAN_DONE");
				CleanObj cleanMb = (CleanObj) msg.obj;
				CleanEvent.getInstance().fireOnCleanDone(mComName, mContext,
						cleanMb.cleanMb, cleanMb.afterMb, cleanMb.beforeMb);
				break;
			case ON_CLEAN_START:
				Logger.i(TAG, "ON_CLEAN_START");
				CleanEvent.getInstance().fireOnStartClean(mComName);
				break;
			default:
				break;
			}
		}
	};

	// private Handler mCleanAnimationHandler = new Handler(){
	// @Override
	// public void handleMessage(Message msg) {
	//
	// switch (msg.what) {
	// case ON_CLEAN_PROGRESS_SUB:
	// CleanObj currentSub = (CleanObj) msg.obj;
	// Logger.i("SUB", "发�?�消息接�??:"+currentSub);
	// CleanEvent.getInstance().fireOnProgressSub(currentSub.currentProgress,currentSub.percentage);
	// break;
	// case ON_CLEAN_PROGRESS_ADD:
	// CleanObj currentAdd = (CleanObj) msg.obj;
	// CleanEvent.getInstance().fireOnProgressAdd(currentAdd.currentProgress,currentAdd.percentage);
	// break;
	// default:
	// break;
	// }
	// }
	// };
	public void killProcesses(final Context context, String comName) {
		Logger.i(TAG, "killProcesses");
		this.mComName = comName;
		this.mContext = context;
		updateMemoryInfo(context);
		beforeTMb = getAvailMemory();
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = mHandler.obtainMessage();
				msg.what = ON_CLEAN_START;
				mHandler.sendMessage(msg);
				killBackgroundProcessesToIcon(context);
			}
		}).start();
	}

	private final static int ON_CLEAN_START = 0;
	private final static int ON_CLEAN_DONE = 1;
	private final static int ON_CLEANING = 2;

	// current, max, percentage,processName
	private class CleanObj {
		public String percentage;
		public int maxCount;
		public int currentProgress;
		public String processName;
		public long afterMb;
		public long beforeMb;
		public String cleanMb;
	}

	// private long getMyAvailMemory(Context context) {// 获取android当前可用内存大小
	// ActivityManager am = (ActivityManager)
	// context.getSystemService(Context.ACTIVITY_SERVICE);
	// MemoryInfo mi = new MemoryInfo();
	// am.getMemoryInfo(mi);
	// return mi.availMem;
	// }
	@SuppressLint("NewApi")
	private void killBackgroundProcessesToIcon(Context context) {
		Logger.i(TAG, "killBackgroundProcessesToIcon");
		int processCount = 0;
		ActivityManager am = (ActivityManager) context.getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> processes = am
				.getRunningAppProcesses();
		int maxProcessCount = processes.size();
		for (ActivityManager.RunningAppProcessInfo info : processes) {
			if (info != null && info.processName != null
					&& info.processName.length() > 0) {
				String pkgName = info.processName;
				if (!("system".equals(pkgName)
						|| "android.process.media".equals(pkgName)
						|| "android.process.acore".equals(pkgName)
						|| pkgName.contains("mediatek")
						|| "com.android.systemui".equals(pkgName)
						|| "com.android.phone".equals(pkgName)
						|| "com.android.data".equals(pkgName) || pkgName
							.contains("noxus"))) {

					am.killBackgroundProcesses(pkgName);

					int currentProgress = processCount++;
					CleanObj clean = new CleanObj();
					clean.currentProgress = currentProgress;
					clean.maxCount = maxProcessCount;
					clean.processName = pkgName;
					clean.percentage = getMemPercentage(context);
					Message msg1 = mHandler.obtainMessage();
					msg1.obj = clean;
					msg1.what = ON_CLEANING;
					mHandler.sendMessage(msg1);
				}
			}
		}
		// 清理之后的内�??
		Message msg2 = mHandler.obtainMessage();
		CleanObj ob = new CleanObj();
		ob.beforeMb = beforeTMb;
		updateMemoryInfo(context);
		ob.afterMb = getAvailMemory();
		ob.cleanMb = Formatter.formatFileSize(context, getAvailMemory()
				- beforeTMb);
		if (ob.cleanMb.contains(" ")) {
			StringBuffer sb = new StringBuffer(ob.cleanMb);
			sb.deleteCharAt(sb.indexOf(" "));
			ob.cleanMb = sb.toString();
		}
		msg2.what = ON_CLEAN_DONE;
		msg2.obj = ob;
		mHandler.sendMessage(msg2);
	}

	@SuppressLint("NewApi")
	public int killBackgroundProcesses(Context context) {
		int processCount = 0;
		ActivityManager am = (ActivityManager) context.getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> processes = am
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo info : processes) {
			if (info != null && info.processName != null
					&& info.processName.length() > 0) {
				String pkgName = info.processName;
				if (!("system".equals(pkgName)
						|| "android.process.media".equals(pkgName)
						|| "android.process.acore".equals(pkgName)
						|| pkgName.contains("mediatek")
						|| "com.android.systemui".equals(pkgName)
						|| "com.android.phone".equals(pkgName)
						|| "com.android.data".equals(pkgName) || pkgName
							.contains("noxus"))) {

					am.killBackgroundProcesses(pkgName);
					processCount++;
				}
			}
		}
		return processCount;
	}

	public void clearCache(Context context) {
		queryToatalCache(context);
		try {
			if (mPackageManager == null) {
				mPackageManager = context.getPackageManager();
			}
			String methodName = "freeStorageAndNotify";
			Class<?> parameterType1 = Long.TYPE;
			Class<?> parameterType2 = IPackageDataObserver.class;
			Method freeStorageAndNotify = mPackageManager.getClass().getMethod(
					methodName, parameterType1, parameterType2);
			/*
			 * freeStorageSize (^_^) The number of bytes of storage to be freed
			 * by the system. Say if freeStorageSize is XX, and the current free
			 * storage is YY, if XX is less than YY, just return. if not free
			 * XX-YY number of bytes if possible.
			 */
			Long freeStorageSize = Long.valueOf(getDataDirectorySize());

			freeStorageAndNotify.invoke(mPackageManager, freeStorageSize,
					new IPackageDataObserver.Stub() {
						@Override
						public void onRemoveCompleted(String packageName,
								boolean succeeded) throws RemoteException {
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private long getDataDirectorySize() {
		File tmpFile = Environment.getDataDirectory();
		if (tmpFile == null) {
			return 0l;
		}
		String strDataDirectoryPath = tmpFile.getPath();
		StatFs localStatFs = new StatFs(strDataDirectoryPath);
		long size = localStatFs.getBlockSize() * localStatFs.getBlockCount();
		return size;
	}

	private void queryToatalCache(Context context) {
		if (mPackageManager == null) {
			mPackageManager = context.getPackageManager();
		}
		List<ApplicationInfo> apps = mPackageManager
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
						| PackageManager.GET_ACTIVITIES);
		String pkgName = "";
		for (ApplicationInfo info : apps) {
			pkgName = info.packageName;
			try {
				queryPkgCacheSize(context, pkgName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void queryPkgCacheSize(Context context, String pkgName)
			throws Exception {
		if (!TextUtils.isEmpty(pkgName)) {
			if (mPackageManager == null) {
				mPackageManager = context.getPackageManager();
			}
			try {
				// the requested method's name.
				String strGetPackageSizeInfo = "getPackageSizeInfo";
				Method getPackageSizeInfo = mPackageManager.getClass()
						.getDeclaredMethod(strGetPackageSizeInfo, String.class,
								IPackageStatsObserver.class);
				getPackageSizeInfo.invoke(mPackageManager, pkgName,
						mStatsObserver);

			} catch (Exception ex) {
				ex.printStackTrace();
				throw ex;
			}
		}
	}

	private IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {
		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			long tmp = totalCacheSize;
			totalCacheSize += pStats.cacheSize;
			if (tmp != totalCacheSize) {
				// mHandler.sendEmptyMessage(MSG_UPDATE_TOAST_TEXT);
			}
		}
	};
	private long beforeTMb;
}