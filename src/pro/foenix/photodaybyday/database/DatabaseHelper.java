package pro.foenix.photodaybyday.database;

import pro.foenix.photodaybyday.R;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String DATABASE_NAME = "Photo.db";

	public static final int DATABASE_VERSION = 1;
	private static final String CREATE_TABLE_MONTH = "create table tblMonth (_id integer primary key autoincrement, name text);";
	private static final String CREATE_TABLE_YEARMONTH = "create table tblYearMonth (_id integer primary key autoincrement, year integer, id_month integer, url text);";
	private static final String CREATE_TABLE_MONTHDAY = "create table tblMonthDay (_id integer primary key autoincrement, id_yearmonth integer, day integer, url text);";
	private static final String CREATE_TABLE_PICTURES = "create table tblPictures (_id integer primary key autoincrement, id_monthday integer, url text, url_sound text, note text);";
	
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
		db.execSQL(CREATE_TABLE_MONTH);
		db.execSQL(CREATE_TABLE_YEARMONTH);
		db.execSQL(CREATE_TABLE_MONTHDAY);
		db.execSQL(CREATE_TABLE_PICTURES);

		ContentValues cv = new ContentValues();
		for (int i = 1; i <= 12; i++) {
			cv.clear();
			cv.put("_id", i);
			cv.put("name", context.getResources().getStringArray(R.array.Month_names)[i-1]);
			db.insert("tblMonth", null, cv);
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS tblMonth;");
		db.execSQL(CREATE_TABLE_MONTH);
		db.execSQL("DROP TABLE IF EXISTS tblYearMonth;");
		db.execSQL(CREATE_TABLE_YEARMONTH);
		db.execSQL("DROP TABLE IF EXISTS tblMonthDay;");
		db.execSQL(CREATE_TABLE_MONTHDAY);
		db.execSQL("DROP TABLE IF EXISTS tblPictures;");
		db.execSQL(CREATE_TABLE_PICTURES);
	}

}
