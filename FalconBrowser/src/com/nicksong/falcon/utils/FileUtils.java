package com.nicksong.falcon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.net.URLEncoder;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import com.nicksong.falcon.ui.activities.SLAppication;

public class FileUtils {
	private String SDPATH = null;
	private Context mContext;

	public FileUtils() {
		// 初始化当前SD卡的路径
		SDPATH = Environment.getExternalStorageDirectory() + "/";
		mContext = SLAppication.getContext();
	}

	// 得到当前SD卡的路径
	public String getSDPATH() {
		return SDPATH;
	}

	public File getCacheFile() {
		File file = null;
		if (StorageUtils.isSDCardReady()) {
			file = new File(getSDPATH() + mContext.getPackageName() + "/cache/");
		} else {
			return SLAppication.getContext().getCacheDir();
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	// 获取下载Cache存储的路径
	public String getCachePath() {
		File file = null;
		if (StorageUtils.isSDCardReady()) {
			file = new File(getSDPATH() + mContext.getPackageName() + "/cache/");
		} else {
			return SLAppication.getContext().getCacheDir().getAbsolutePath();
		}
		/***/
		if (!file.exists()) {
			return file.getAbsolutePath() + "/";
		} else {
			file.mkdirs();
			return file.getAbsolutePath() + "/";
		}
	}

	// 获取下载apk存储的路径
	public String getApkPath() {
		File file = null;
		if (StorageUtils.isSDCardReady()) {
			file = new File(getSDPATH() + mContext.getPackageName() + "/apk/");
		} else {
			return SLAppication.getContext().getCacheDir().getAbsolutePath();
		}
		if (!file.exists()) {
			return file.getAbsolutePath() + "/";
		} else {
			file.mkdirs();
			return file.getAbsolutePath() + "/";
		}
	}

	// 获取sdcard剩余容量，以mb为单位
	public boolean isAvailableSize(String size) {
		boolean flag = true;

		try {
			File file = new File(SDPATH);
			StatFs statfs = new StatFs(file.getPath());
			long nBlocSize = statfs.getBlockSize();
			long nAvailaBlock = statfs.getAvailableBlocks();
			long leftSize = nBlocSize * nAvailaBlock / 1024 / 1024; // mb
			long apkSize = Long.parseLong(size.replace("MB", "").replace("mb", "").replace("Mb", "").replace("mB", "")) * 2;
			flag = leftSize - apkSize > 0;
		} catch (Exception e) {

		}

		return flag;
	}

	// 递归删除文件夹里面的东西
	public void deleteDirs(File file) {
		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				deleteDirs(f); // 递归删除每一个文件
				f.delete(); // 删除该文件夹
			}
		}
	}

	/**
	 * 文件序列化对象列表
	 * 
	 * @param obj
	 * @param fileName
	 * @param mode
	 */
	public static void writeObjsToFile(Object obj, String fileName, int mode) {

		if (obj == null || fileName == null) return;

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = SLAppication.getContext().openFileOutput(URLEncoder.encode(fileName, "UTF-8"), mode);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (oos != null) {
					oos.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 从序列化文件中读取对象列表
	 * 
	 * @param fileName
	 * @return
	 */
	public static Object readObjsFromFile(String fileName) {

		if (fileName == null) return null;

		Object obj = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			if (SLAppication.getContext().getFileStreamPath(fileName).exists()) {
				fis = SLAppication.getContext().openFileInput(fileName);
				ois = new ObjectInputStream(fis);
				obj = ois.readObject();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (ois != null) {
					ois.close();
				}
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return obj;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @return
	 */
	public static boolean fileIsExists(Context context, String fileName) {
		try {
			File f = new File(context.getFilesDir(), fileName);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
}
