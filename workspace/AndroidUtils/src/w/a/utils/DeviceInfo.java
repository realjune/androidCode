package w.a.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class DeviceInfo {

	int mScreenWidth,mScreenHeight;
	public void getDisplay(Activity context){
		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		mScreenWidth = mDisplayMetrics.widthPixels;
		mScreenHeight = mDisplayMetrics.heightPixels;
	}
}
