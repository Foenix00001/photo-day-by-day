package pro.foenix.photodaybyday;

import pro.foenix.photodaybyday.database.DBModel;
import pro.foenix.photodaybyday.database.IDBModel;
import android.app.Application;
import android.content.Context;

public class AppPhotoDayByDay extends Application {
	private static final String TAG = "AppPhotoDayByDay";
//	private static AppPhotoDayByDay instance = null;
	private static AppPhotoDayByDay singleton;


	public static IDBModel getBaseModel(Context context) {
		return new DBModel(context);
	}

	/*public static void initImageLoader(Context context) {
		Log.d(TAG, "initImageLoader************************");
		DisplayImageOptions optionsList = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).cacheInMemory(true).cacheOnDisk(true).build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.defaultDisplayImageOptions(optionsList).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.diskCacheSize(50 * 1024 * 1024) // 50 Mb
				.tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}*/

	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;

		//Log.d(TAG, "onCreate************************");
		//initImageLoader(getApplicationContext());
	}

	/*private AppPhotoDayByDay() {
		instance = this;
	}*/

	public static Context getInstance() {
		/*if (null == instance) { 
			instance = new AppPhotoDayByDay();
		}
		return instance;*/
		return singleton;

	}
}
