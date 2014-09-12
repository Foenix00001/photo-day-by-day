package pro.foenix.photodaybyday.database;

import android.net.Uri;

public interface IPictures {
	public static final Uri CONTENT_URI = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/pictures");
	public static final Uri CONTENT_URI_RAW = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/raw");
	public static final Uri PICTURES_DAYS = Uri.parse("content://" + DBContentProvider.AUTHORITY + "/pictures/days");

	public static final String TABLENAME = "tblPictures";
	
	public static final String KEY_ROWID = "_id";
	public static final String KEY_ROWID_FULL =  TABLENAME + "." + KEY_ROWID;  
	
	public static final String ROW_ID_DAY = "id_day";
	public static final String ROW_ID_DAY_FULL =  TABLENAME + "." + ROW_ID_DAY;
	
	public static final String ROW_URL = "url";
	public static final String ROW_URL_FULL =  TABLENAME + "." + ROW_URL;
	
	public static final String ROW_ORDER = "_order"; //1,2,3
	public static final String ROW_ORDER_FULL =  TABLENAME + "." + ROW_ORDER;
	
	public static final String ROW_FL_CHK = "fl_chk";
	public static final String ROW_FL_CHK_FULL =  TABLENAME + "." + ROW_FL_CHK;
	

}
