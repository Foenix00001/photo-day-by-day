package pro.foenix.photodaybyday.utils;

import java.io.File;

import pro.foenix.photodaybyday.AppPhotoDayByDay;

public class FileManaging {
	private static final String NAMESPACE = "PhotoDayByDay";
	private static final String PICTURES_DIR = "Pictures";
	private static final String SOUNDS_DIR = "Sounds";
	private static File baseDir = null;
	private static File directory = null;
	private static String sdState;
	


	/**
	 * Returns a path for an application picture directory in an external
	 * storage (SD-card)
	 * 
	 * @return
	 */
	public static File getImgDir() {
		sdState = android.os.Environment.getExternalStorageState();
		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			baseDir = android.os.Environment.getExternalStorageDirectory();
			directory = new File(baseDir.getAbsolutePath(), NAMESPACE + "/" + PICTURES_DIR + "/");
			directory.mkdirs();
		} else {
			//directory = getAppDir();
			directory =  new File(getAppDir()+ "/" + PICTURES_DIR + "/");
		}
		return directory;
	}

	/**
	 * Returns a path for an application sounds directory in an external storage
	 * (SD-card)
	 * 
	 * @return
	 */
	public static File getSoundDir() {
		sdState = android.os.Environment.getExternalStorageState();
		if (sdState.equals(android.os.Environment.MEDIA_MOUNTED)) {
			baseDir = android.os.Environment.getExternalStorageDirectory();
			directory = new File(baseDir.getAbsolutePath(), NAMESPACE + "/" + SOUNDS_DIR + "/");
			directory.mkdirs();
		} else {
			directory =  new File(getAppDir()+ "/" + SOUNDS_DIR + "/");
		}
		return directory;
	}

	/**
	 * Returns a path for an application root directory in a device memory
	 * 
	 * @return
	 */
	public static File getAppDir() {
		// baseDir = android.os.Environment.getDataDirectory();
		// directory = new File(baseDir, NAMESPACE);
		directory = new File(AppPhotoDayByDay.getInstance().getFilesDir().getParent());
		directory.mkdirs();

		return directory;
	}
}
