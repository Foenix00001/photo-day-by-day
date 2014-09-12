package pro.foenix.photodaybyday.database;

import android.net.Uri;

public interface IDays {
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/days");
	public static final Uri CONTENT_URI_RAW = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/raw");

	public static final String TABLENAME = "tblDays";
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_ROWID_FULL =  TABLENAME + "." + KEY_ROWID;    
	public static final String ROW_DAY = "day";
	public static final String ROW_DAY_FULL =  TABLENAME + "." + ROW_DAY;    
	public static final String ROW_MONTH = "month";
	public static final String ROW_MONTH_FULL =  TABLENAME + "." + ROW_MONTH;    
	public static final String ROW_YEAR = "year";
	public static final String ROW_YEAR_FULL =  TABLENAME + "." + ROW_YEAR;    
	public static final String ROW_DATE = "date";
	public static final String ROW_DATE_FULL =  TABLENAME + "." + ROW_DATE;    
	
	public static final String ROW_URL_SOUND = "url_sound";
	public static final String ROW_URL_SOUND_FULL =  TABLENAME + "." + ROW_URL_SOUND;    
	public static final String ROW_NOTE = "note";
	public static final String ROW_NOTE_FULL =  TABLENAME + "." + ROW_NOTE;    
	
	public static final String ROW_FL_CHK = "fl_chk";
	public static final String ROW_FL_CHK_FULL =  TABLENAME + "." + ROW_FL_CHK;    
	

}
