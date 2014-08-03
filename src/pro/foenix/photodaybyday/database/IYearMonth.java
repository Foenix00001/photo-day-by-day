package pro.foenix.photodaybyday.database;

import android.net.Uri;

public interface IYearMonth {
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/yearmonth");
	public static final Uri CONTENT_URI_RAW = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/raw");

	public static final String KEY_ROWID = "_id";
	public static final String ROW_ID_MONTH = "id_month";
	public static final String ROW_YEAR = "year";
	public static final String ROW_URL = "url";
	public static final String DATABASE_TABLE = "tblYearMonth";

}
