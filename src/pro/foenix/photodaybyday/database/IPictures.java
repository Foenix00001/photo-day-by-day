package pro.foenix.photodaybyday.database;

import android.net.Uri;

public interface IPictures {
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/pictures");
	public static final Uri CONTENT_URI_RAW = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/raw");

	public static final String DATABASE_TABLE = "tblPictures";
	
	public static final String KEY_ROWID = "_id";
	public static final String ROW_DAY = "day";
	public static final String ROW_MONTH = "month";
	public static final String ROW_YEAR = "year";
	public static final String ROW_DATA = "data";
	public static final String ROW_URL = "url";
	public static final String ROW_URL_SOUND = "url_sound";
	public static final String ROW_NOTE = "note";
	public static final String ROW_FL_STAR = "fl_star";
	public static final String ROW_FL_CHK = "fl_chk";
	

}
