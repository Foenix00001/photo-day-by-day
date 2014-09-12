package pro.foenix.photodaybyday.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper implements IPictures, IDays {
	public static final String DATABASE_NAME = "Photo.db";

	public static final int DATABASE_VERSION = 5;
	private static final String CREATE_TABLE_DAYS = "create table tblDays (" 
			+ IDays.KEY_ROWID + " integer primary key autoincrement, " 
			+ IDays.ROW_DAY + " integer, " 
			+ IDays.ROW_MONTH + " integer, "
			+ IDays.ROW_YEAR + " integer, " 
			+ IDays.ROW_DATE + " integer, " 
			+ IDays.ROW_URL_SOUND + " text, "
			+ IDays.ROW_NOTE + " text, " 
			+ IDays.ROW_FL_CHK + " int default 0);";

	private static final String CREATE_TABLE_PICTURES = "create table tblPictures (" 
			+ IPictures.KEY_ROWID + " integer primary key autoincrement, "
			+ IPictures.ROW_ID_DAY + " int, "
			+ IPictures.ROW_URL + " text, " 
			+ IPictures.ROW_ORDER + " int, "
			+ IPictures.ROW_FL_CHK + " int default 0);";
	private static final String TAG = "DatabaseHelper";
	private Context context = null;
	private static DatabaseHelper mInstance = null;

	public static DatabaseHelper getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new DatabaseHelper(context.getApplicationContext());
		}
		return mInstance;
	}

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_PICTURES);
		db.execSQL(CREATE_TABLE_DAYS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS tblDays;");
		db.execSQL("DROP TABLE IF EXISTS tblPictures;");

		db.execSQL(CREATE_TABLE_PICTURES);
		Log.d(TAG, CREATE_TABLE_PICTURES);
		db.execSQL(CREATE_TABLE_DAYS);
		Log.d(TAG, CREATE_TABLE_DAYS);

	}

}
