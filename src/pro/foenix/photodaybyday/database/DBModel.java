package pro.foenix.photodaybyday.database;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.entities.MonthEntity;
import pro.foenix.photodaybyday.entities.YearEntity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class DBModel implements IDBModel {

	private static final String TAG = "DBModel";
	private Context mContext;

	public DBModel(Context context) {
		super();
		this.mContext = context;
	}
	
	public Cursor execRawQuery(String selection, String[] selectionArgs ) {
		// Log.d(TAG,"rawSQl = "+strQ);
		return mContext.getContentResolver().query(IPictures.CONTENT_URI_RAW, null, selection, selectionArgs, null);
	}

	@Override
	public ArrayList<YearEntity> getMonthsInYear(int year) {
		/*String[] projection = { IDays.KEY_ROWID_FULL, IPictures.ROW_URL, IDays.ROW_MONTH };

		String selection = IDays.ROW_YEAR + " = ?";
		String[] selectionArgs = { Integer.toString(year) };

		Cursor mCursor = mContext.getContentResolver().query(IPictures.PICTURES_DAYS, projection, selection,
				selectionArgs, IPictures.ROW_ORDER + " LIMIT 1");*/
		String selection = "SELECT t1.year as year, t1.month as month, t2.url as url "
				+ " FROM ( "
				+ " SELECT tblDays._id, tblDays.year, tblDays.month"
				+ " FROM tblDays"
				+ " INNER JOIN ( "
				+ " SELECT min( tblDays.day ) AS min_day, tblDays.month, tblDays.year"
				+ " FROM tblDays"
                + " INNER JOIN tblPictures"
                + " ON tblDays._id = tblPictures.id_day"
                + " GROUP BY tblDays.year, tblDays.month) minDays "
                + " ON tblDays.year = minDays.year AND tblDays.month = minDays.month AND tblDays.day = minDays.min_day) t1"
                + " INNER JOIN ( "
                + " SELECT tblPictures.url, tblPictures.id_day"
                + " FROM tblPictures"
                + " INNER JOIN ("
                + " SELECT id_day, min( _order ) AS min_order"
                + " FROM tblPictures GROUP BY id_day) minOrders"
                + " ON tblPictures.id_day = minOrders.id_day  AND tblPictures._order = minOrders.min_order) t2"
                + " ON t1._id = t2.id_day "
                + " WHERE t1.year = ?;";
		String[] selectionArgs = { Integer.toString(year) };
		Cursor mCursor = execRawQuery(selection,selectionArgs );
		
		ArrayList<YearEntity> YearArray = new ArrayList<YearEntity>();

		for (int j = 1; j <= 12; j++) {
			YearArray.add(new YearEntity(0, year, j, null,
					mContext.getResources().getStringArray(R.array.month_names)[j - 1], 0));
			//Log.d(TAG, mArrayList.get(j - 1).toString());
		}
		if (mCursor != null) {
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
				int month = mCursor.getInt(mCursor.getColumnIndex(IDays.ROW_MONTH));
				String url = mCursor.getString(mCursor.getColumnIndex(IPictures.ROW_URL));
				//int num = 0; 
				//mCursor.getInt(mCursor.getColumnIndex("cnt"));
				//updateYearMonthArray(mCursor.getInt(mCursor.getColumnIndex(IYearMonth.ROW_ID_MONTH)),
				//mCursor.getString(mCursor.getColumnIndex(IYearMonth.ROW_URL)));
				//Log.d(TAG, mArrayList.get(i-1).toString());
				for (YearEntity c : YearArray) {
					if (c.getIdMonth() == (month)) {
						c.setUrl(url);
						//		c.setNumOfPhoto(num);
					}
				}
			}
			mCursor.close();
		}
		return YearArray;
	}

	@Override
	public ArrayList<MonthEntity> getDaysInMonth(int year, int month) {
		String[] projection = { IPictures.KEY_ROWID_FULL, IDays.ROW_DAY, IPictures.ROW_URL };

		String selection = IDays.ROW_YEAR + " = ? and " + IDays.ROW_MONTH + " = ? and "+IPictures.ROW_ORDER + " = 0";
		Log.d(TAG, "selection = " + selection);
		String[] selectionArgs = { Integer.toString(year), Integer.toString(month) };

		Cursor mCursor = mContext.getContentResolver().query(IPictures.PICTURES_DAYS, projection, selection,
				selectionArgs, null);
		/*String selection ="select tblPictures._id, tblDays.day, tblPictures.url"
				+" from tblPictures"
				+" left outer join tblDays"
				+" on tblPictures.id_day = tblDays._id"
				+" where"
				+" tblDays.year = ? and tblDays.month = ? and tblPictures._order =0";
		String[] selectionArgs = { Integer.toString(year), Integer.toString(month) };
		Cursor mCursor = execRawQuery(selection,selectionArgs );*/
		
		
		ArrayList<MonthEntity> mArrayList = new ArrayList<MonthEntity>();
		Calendar cal = new GregorianCalendar(year, month - 1, 1);
		int mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int j = 1; j <= mNumDaysInMonth; j++) {
			mArrayList.add(new MonthEntity(year, month, j, null));
			//Log.d(TAG, mArrayList.get(j - 1).toString());
		}

		if (!(mCursor == null)) {
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
				int day = mCursor.getInt(mCursor.getColumnIndex(IDays.ROW_DAY));
				String url = mCursor.getString(mCursor.getColumnIndex(IPictures.ROW_URL));
				//updateYearMonthArray(mCursor.getInt(mCursor.getColumnIndex(IYearMonth.ROW_ID_MONTH)),
				//	mCursor.getString(mCursor.getColumnIndex(IYearMonth.ROW_URL)));
				//Log.d(TAG, mArrayList.get(i-1).toString());

				for (MonthEntity c : mArrayList) {
					if (c.getDay() == (day)) {
						c.setUrl(url);
						Log.d(TAG, c.toString());
					}
				}
			}
			mCursor.close();
		}
		return mArrayList;
	}

	@Override
	public int addPicture(int id_day, String url, int year, int month, int day) {
		//Log.d(TAG, "id_day = "+ id_day + " url = "+ url);
		//Log.d(TAG, " year = "+ year+" month = "+ month+" day = "+ day);
		if (id_day > 0) {
			for (int i = 0; i < 3; i++) {
				String[] projection = { IPictures.KEY_ROWID };
				String selection = IPictures.ROW_ID_DAY + " = ? and " + IPictures.ROW_ORDER + " = ? ";
				String[] selectionArgs = { Integer.toString(id_day), Integer.toString(i) };
				Cursor mCursor = mContext.getContentResolver().query(IPictures.CONTENT_URI, projection, selection,
						selectionArgs, null);
				if (mCursor != null) {
					if (mCursor.getCount() == 0) {
						ContentValues cv = new ContentValues();
						cv.put(IPictures.ROW_ORDER, i);
						cv.put(IPictures.ROW_ID_DAY, id_day);
						cv.put(IPictures.ROW_URL, url);

						Uri uri = mContext.getContentResolver().insert(IPictures.CONTENT_URI, cv);
						return id_day;//Integer.parseInt(uri.getLastPathSegment());

					}
				}
			}
			return -1;//Фото уже три, вставка невозможна
		} else {
			ContentValues cv = new ContentValues();
			cv.put(IDays.ROW_YEAR, year);
			cv.put(IDays.ROW_MONTH, month);
			cv.put(IDays.ROW_DAY, day);
			//cv.put(IDays.ROW_DAY, day);

			Uri uri = mContext.getContentResolver().insert(IDays.CONTENT_URI, cv);
			id_day = Integer.parseInt(uri.getLastPathSegment());
			if (id_day > 0) {
				cv.clear();
				cv.put(IPictures.ROW_ORDER, 0);
				cv.put(IPictures.ROW_ID_DAY, id_day);
				cv.put(IPictures.ROW_URL, url);

				mContext.getContentResolver().insert(IPictures.CONTENT_URI, cv);
				return id_day;
			}else{
				return 0;
			}
		}
	}

	@Override
	public int getNumberOfPictures(int id_day) {
		String[] projection = { "COUNT (" + IPictures.KEY_ROWID + ") as cnt" };

		String selection = IPictures.ROW_ID_DAY + " = ? ";
		String[] selectionArgs = { Integer.toString(id_day) };
		int cnt = 0;
		Cursor mCursor = mContext.getContentResolver().query(IPictures.CONTENT_URI, projection, selection,
				selectionArgs, null);
		if (!(mCursor == null)) {
			mCursor.moveToFirst();
			cnt = mCursor.getInt(mCursor.getColumnIndex("cnt"));
			mCursor.close();
		}
		return cnt;
	}

	@Override
	public Cursor getPicturesInDay(int year, int month, int day) {
		String[] projection = { IPictures.ROW_URL, IPictures.ROW_ORDER };
		String selection = IDays.ROW_YEAR + " = ? and " + IDays.ROW_MONTH + " = ? and " + IDays.ROW_DAY + " =? ";
		String[] selectionArgs = { Integer.toString(year), Integer.toString(month), Integer.toString(day) };
		return mContext.getContentResolver().query(IPictures.PICTURES_DAYS, projection, selection, selectionArgs,
				IPictures.ROW_ORDER);
	}

	@Override
	public String getDayNote(int year, int month, int day) { //может, по id?
		String note = null;
		String[] projection = { IDays.ROW_NOTE };
		String selection = IDays.ROW_YEAR + " = ? and " + IDays.ROW_MONTH + " = ? and " + IDays.ROW_DAY + " =? ";
		String[] selectionArgs = { Integer.toString(year), Integer.toString(month), Integer.toString(day) };
		Cursor mCursor = mContext.getContentResolver().query(IDays.CONTENT_URI, projection, selection,
				selectionArgs, null);
		if (!(mCursor == null)) {
			if (mCursor.moveToFirst()){
				note = mCursor.getString(mCursor.getColumnIndex(IDays.ROW_NOTE ));
			}
			mCursor.close();
		}
		return note;
	}

	@Override
	public int getDayId(int year, int month, int day) {
		int day_id = 0;
		String[] projection = { IDays.KEY_ROWID_FULL + " as id " };
		String selection = IDays.ROW_YEAR + " = ? and " + IDays.ROW_MONTH + " = ? and " + IDays.ROW_DAY + " =? ";
		String[] selectionArgs = { Integer.toString(year), Integer.toString(month), Integer.toString(day) };
		Cursor mCursor = mContext.getContentResolver().query(IPictures.PICTURES_DAYS, projection, selection,
				selectionArgs, null);
		if (!(mCursor == null)) {
			if (mCursor.moveToFirst()){
				day_id = mCursor.getInt(mCursor.getColumnIndex("id"));
			}
			mCursor.close();

		}
		return day_id;
	}

	@Override
	public void deletePicture(int id_picture) {
		
		
	}
	
	
}
