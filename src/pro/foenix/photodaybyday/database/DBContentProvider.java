package pro.foenix.photodaybyday.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class DBContentProvider extends ContentProvider implements IPictures, IDays {
	private static final String TAG = "DBContentProvider";
	private DatabaseHelper mOpenHelper;
	private static final UriMatcher sUriMatcher = buildUriMatcher();
	static final String AUTHORITY = "pro.foenix.photodaybyday.PhotoContentProvider";

	private static final String DBNAME = "Photo.db";

	private static final int PICTURES = 10;
	private static final int PICTURES_ID = 11;
	private static final int PICTURES_DAYS = 12;

	private static final int DAYS = 20;
	private static final int DAYS_ID = 21;

	private static final int RAW_QUERY = 100;

	@Override
	public boolean onCreate() {
		mOpenHelper = new DatabaseHelper(getContext());
		return true;
	}

	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String authority = AUTHORITY;

		matcher.addURI(authority, "pictures", PICTURES);
		matcher.addURI(authority, "pictures/#", PICTURES_ID);

		matcher.addURI(authority, "pictures/days", PICTURES_DAYS);

		matcher.addURI(authority, "days", DAYS);
		matcher.addURI(authority, "days/#", DAYS_ID);

		matcher.addURI(authority, "raw", RAW_QUERY);
		return matcher;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = mOpenHelper.getReadableDatabase();
		Cursor cursor = null;
		int id;
		switch (sUriMatcher.match(uri)) {
		// PICTURES
		case PICTURES_ID:
			id = Integer.parseInt(uri.getLastPathSegment());
			if (TextUtils.isEmpty(selection)) {
				selection = IPictures.KEY_ROWID + " = " + id;
			} else {
				selection = selection + " AND " + IPictures.KEY_ROWID + " = " + id;
			}
			cursor = db.query(IPictures.TABLENAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case PICTURES:
			cursor = db.query(IPictures.TABLENAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case PICTURES_DAYS:
			//Log.d(TAG, IPictures.TABLENAME + " LEFT OUTER JOIN " + IDays.TABLENAME + " ON "
				//	+ IPictures.ROW_ID_DAY_FULL + " = " + IDays.KEY_ROWID_FULL);
			cursor = db.query(IPictures.TABLENAME + " LEFT OUTER JOIN " + IDays.TABLENAME + " ON "
					+ IPictures.ROW_ID_DAY_FULL + " = " + IDays.KEY_ROWID_FULL, projection, selection, selectionArgs, null, null,
					sortOrder);
			break;
		// DAYS
		case DAYS_ID:
			id = Integer.parseInt(uri.getLastPathSegment());
			if (TextUtils.isEmpty(selection)) {
				selection = IDays.KEY_ROWID + " = " + id;
			} else {
				selection = selection + " AND " + IDays.KEY_ROWID + " = " + id;
			}
			cursor = db.query(IDays.TABLENAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;
		case DAYS:
			cursor = db.query(IDays.TABLENAME, projection, selection, selectionArgs, null, null, sortOrder);
			break;

		case RAW_QUERY:
			cursor = db.rawQuery(selection, selectionArgs);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		long rowId = 0;

		switch (sUriMatcher.match(uri)) {
		//PICTURES
		case PICTURES:
			rowId = db.insert(IPictures.TABLENAME, null, values);
			break;

		//DAYS
		case DAYS:
			rowId = db.insert(IDays.TABLENAME, null, values);
			break;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		if (rowId > 0) {
			Uri resUri = ContentUris.withAppendedId(uri, rowId);
			getContext().getContentResolver().notifyChange(resUri, null);
			return resUri;
		}
		throw new SQLException("Failed to insert row into " + uri);

	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}
}
