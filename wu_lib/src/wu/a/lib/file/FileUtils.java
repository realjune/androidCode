package wu.a.lib.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * @author junxu.wang
 * 
 *         <pre>
 * android.os.Environment 提供访问环境变量 
 * 
 * 1.从 resource 中的 raw 文件夹中获取文件并读取数据（资源文件只能读不能写）
 * 2. 从 asset 中获取文件并读取数据（资源文件只能读不能写） 
 * 3. 从 sdcard 中去读文件，首先要把文件通过 \android-sdk-windows\tools\adb.exe  * 
 * 把本地计算 机上的文件 copy 到 sdcard 上去， adb.exe push e:/Y.txt /sdcard/, 
 * 不可以用 adb.exe push e:\Y.txt \sdcard\ 同样： 把仿真器上的文件 copy 到本地计算机上用： 
 * adb pull ./data/data/com.tt/files/Test.txt e:/
 * 4. 写文件， 一般写在 \data\data\com.test\files\ 里面，
 * 打开 DDMS 查看 file explorer 是可以看 到仿真器文件存放目录的结构的 
 * 5. 写， 读 data/data/ 目录 ( 相当 AP 工作目录 ) 上的文件，用 openFileOutput
 * 6. 写， 读 sdcard 目录上的文件，要用 FileOutputStream ， 不能用 openFileOutput
 */
public class FileUtils {
	private static final String DATA_DIR = "mnt/sdcard/sctc/";
	private String IMGS_DIR = DATA_DIR + "imgs/";
	int flag;

	/**
	 * 获取可写sdcard目录
	 * 
	 * @return
	 */
	public static File getSdcardForWrite() {
		String mExternalStorageState = Environment.getExternalStorageState();
		// 拥有可读写权限
		if (Environment.MEDIA_MOUNTED.equals(mExternalStorageState)) {
			// 获取扩展存储设备的文件目录
			return android.os.Environment.getExternalStorageDirectory();
			// 打开文件
			// Stirng sdcardPath=SDFile.getAbsolutePath();
		} else if (mExternalStorageState
				.endsWith(Environment.MEDIA_MOUNTED_READ_ONLY)) {// 拥有只读权限

		}
		return null;
	}

	/**
	 * 获取可读sdcard目录
	 * 
	 * @return
	 */
	public static File getSdcardForRead() {
		String mExternalStorageState = Environment.getExternalStorageState();
		// 拥有可读写权限// 拥有只读权限
		if (Environment.MEDIA_MOUNTED.equals(mExternalStorageState)
				|| mExternalStorageState
						.endsWith(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			// 获取扩展存储设备的文件目录
			return android.os.Environment.getExternalStorageDirectory();
		}
		return null;
	}

	/**
	 * sdcard free size
	 * 
	 * @return
	 */
	public static long getSdcardFreeSize() {
		// 取得 SDCard 当前的状态
		String sDcString = android.os.Environment.getExternalStorageState();
		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			// 取得 sdcard 文件路径
			File pathFile = android.os.Environment
					.getExternalStorageDirectory();
			android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
			// 获取剩下的所有 Block 的数量 ( 包括预留的一般程序无法 使用的块 )
			long nFreeBlock = statfs.getFreeBlocks();
			// 获取 SDCard 上每个 block 的 SIZE
			long nBlocSize = statfs.getBlockSize();
			return nFreeBlock * nBlocSize;
		}
		return 0;
	}

	/**
	 * sdcard total size
	 * 
	 * @return
	 */
	public static long getSdcardSize() {
		// 取得 SDCard 当前的状态
		String sDcString = android.os.Environment.getExternalStorageState();
		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			// 取得 sdcard 文件路径
			File pathFile = android.os.Environment
					.getExternalStorageDirectory();
			android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
			// 获取 SDCard 上 BLOCK 总数
			long nTotalBlocks = statfs.getBlockCount();
			// 获取 SDCard 上每个 block 的 SIZE
			long nBlocSize = statfs.getBlockSize();
			return nTotalBlocks * nBlocSize;
		}
		return 0;
	}

	/**
	 * sdcard available size
	 * 
	 * @return
	 */
	public static long getSdcardAvailableSize() {
		// 取得 SDCard 当前的状态
		String sDcString = android.os.Environment.getExternalStorageState();
		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {
			// 取得 sdcard 文件路径
			File pathFile = android.os.Environment
					.getExternalStorageDirectory();
			android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
			// 获取可供程序使用的 Block 的数量
			long nAvailaBlock = statfs.getAvailableBlocks();
			// 获取 SDCard 上每个 block 的 SIZE
			long nBlocSize = statfs.getBlockSize();
			return nAvailaBlock * nBlocSize;
		}
		return 0;
	}

	/**
	 * 创建目录
	 * 
	 * @param context
	 */
	public static File makeDir(Context context, String dir) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageDirectory())) {
			File imgDirFile = new File(dir);
			if (imgDirFile.exists() || imgDirFile.mkdirs()) {
				return imgDirFile;
			}
		}
		return null;
	}

	/**
	 * from bis write to bos
	 * 
	 * @param bis
	 * @param bos
	 * @throws IOException
	 */
	public static void write(BufferedInputStream bis, BufferedOutputStream bos)
			throws IOException {
		int length = bis.available();// is.available();
		byte[] buffer = new byte[length];
		int len = bis.read(buffer);
		while (len != -1) {
			bos.write(buffer);
			len = bis.read(buffer);
		}
	}

	/**
	 * write raw source to sdcard
	 * 
	 * @param context
	 * @param file
	 * @param id
	 */
	public static void writeRawToSdcard(Context context, File file, int id) {
		InputStream is = null;
		OutputStream os = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			is = context.getResources().openRawResource(id);
			int length = is.available();
			byte[] buffer = new byte[length];
			os = new FileOutputStream(file);
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(os);
			int len = bis.read(buffer);
			while (len != -1) {
				bos.write(buffer);
				len = bis.read(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * write asswts to sdcard
	 * 
	 * @param context
	 * @param file
	 * @param name
	 * @throws IOException
	 */
	public static void writeAssetsToSdcard(Context context, File file,
			String name) throws IOException {
		Log.d(FileUtils.class.getSimpleName(),
				name + " -> " + file.getAbsolutePath());
		InputStream is = null;
		OutputStream os = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.createNewFile();
		}
		Log.d(FileUtils.class.getSimpleName(), file.getAbsolutePath());
		is = context.getAssets().open(name);
		os = new FileOutputStream(file);
		bis = new BufferedInputStream(is);
		bos = new BufferedOutputStream(os);
		write(bis, bos);
		if (bis != null) {
			bis.close();
		}
		if (bos != null) {
			bos.close();
		}
	}

	/**
	 * get assets list
	 * 
	 * @param context
	 * @param path
	 * @throws IOException
	 */
	public static void getAssetsList(Context context, String path,
			String destPath) throws IOException {
		String[] list = context.getAssets().list(path);
		for (int i = 0; i < list.length; i++) {
			String name = list[i];
			if (name.indexOf('.') >= 0) {
				writeAssetsToSdcard(context, new File(destPath + "/" + path
						+ "/" + name), path + "/" + name);
				Log.d(FileUtils.class.getSimpleName(), path + "/" + name);
			} else if (path.length() == 0) {
				getAssetsList(context, name, destPath);
			} else {
				getAssetsList(context, path + "/" + name, destPath);
			}
		}
	}

	// public static void getFileList(File file){
	// if(file.isDirectory()){
	// File[]files=file.listFiles();
	// for(int i=0;i<files.length;i++){
	// File cf=files[i];
	// if(cf.isDirectory()){
	// getFileList(cf);
	// }else{
	// Log.d(Sdcard.class.getSimpleName(),cf.getAbsolutePath());
	// }
	// }
	// }else{
	// Log.d(Sdcard.class.getSimpleName(),file.getAbsolutePath());
	// }
	// }

	/**
	 * delete file or directory
	 * 
	 * @param file
	 * @return
	 */
	public static boolean deleteFile(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File cf : files) {
					if (cf.exists()) {
						if (cf.isDirectory()) {
							deleteFile(cf);
						} else {
							cf.delete();
						}
					}
				}
			}
			return file.delete();
		}
		return false;
	}

	/**
	 * write str to sdcard\file
	 * 
	 * @param context
	 * @param fileName
	 * @param message
	 */
	public static void writeSdcardFile(Context context, String fileName,
			String message) {
		try {
			FileOutputStream fout = new FileOutputStream(new File(fileName));
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * read file from sdcard
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static String readSdcard(String fileName) throws IOException {
		String content;
		FileInputStream fis = new FileInputStream(new File(fileName));
		int available = fis.available();
		byte[] buffer = new byte[available];
		fis.read(buffer);
		content = EncodingUtils.getString(buffer, "UTF-8");
		fis.close();
		return content;
	}

	/**
	 * write str to data\data\pkg path\file
	 * 
	 * @param context
	 * @param fileName
	 * @param message
	 */
	public static void writeDataFile(Context context, String fileName,
			String message) {
		try {
			FileOutputStream fout = context.openFileOutput(fileName,
					Context.MODE_PRIVATE);
			byte[] bytes = message.getBytes();
			fout.write(bytes);
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * read from data\data\pkg path\file
	 * 
	 * @param context
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String readDataFile(Context context, String file)
			throws IOException {
		String msg;
		InputStream is = context.openFileInput(file);
		int available = is.available();
		byte[] buffer = new byte[available];
		is.read(buffer);
		msg = EncodingUtils.getString(buffer, "UTF-8");
		return msg;
	}

}
