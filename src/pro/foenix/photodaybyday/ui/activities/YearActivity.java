package pro.foenix.photodaybyday.ui.activities;

import java.util.ArrayList;
import java.util.Calendar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.adapters.YearAdapter;
import pro.foenix.photodaybyday.database.IYearMonth;
import pro.foenix.photodaybyday.entities.YearMonthEntity;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class YearActivity extends Activity implements IYearMonth {
	private static final String TAG = "YearActivity";
	private static final String KEY_YEAR = "year";
	private int mYear;
	private TextView mYearTextView;
	private ImageButton mNextButton, mPrevButton;
	private GridView mYearGrigView;
	private ArrayList<YearMonthEntity> mYearMonthArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_year);
		mYearGrigView = (GridView) findViewById(R.id.gridview);
		mYearTextView = (TextView) findViewById(R.id.tvYear);
		if (savedInstanceState != null) {
			mYear = savedInstanceState.getInt(KEY_YEAR, 0);
		} else {
			mYear = Calendar.getInstance().get(Calendar.YEAR);
		}
		updateYear();

		mNextButton = (ImageButton) findViewById(R.id.ibNext);
		mPrevButton = (ImageButton) findViewById(R.id.ibPrev);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mYear++;
				updateYear();
			}
		});
		mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mYear--;
				updateYear();
			}
		});
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
		//DisplayImageOptions options = new DisplayImageOptions.Builder()
			//.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build(); 
		ImageLoader.getInstance().init(config);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_YEAR, mYear);
	}

	private void updateYear() {
		mYearMonthArray = getYearData(mYear);
		//запрос в бд по году плюс обновление картинок
		mYearTextView.setText(String.valueOf(mYear));
		mYearGrigView.setAdapter(new YearAdapter(this, mYearMonthArray));
	}

	private ArrayList<YearMonthEntity> getYearData(int year) {
		//String selection = IYearMonth.ROW_YEAR + " = ?";
		String[] selectionArgs = { Integer.toString(year) };
		String selection = "select tblYearMonth.*, ifnull(tblM.cnt,0) cnt from tblYearMonth "
				+ " left outer join (select id_yearmonth, count (*) cnt from tblMonthDay group by id_yearmonth ) as tblM "
				+ " on tblM.id_yearmonth = tblYearMonth._id	where tblYearMonth.year = ?";

		Cursor mCursor = getBaseContext().getContentResolver().query(IYearMonth.CONTENT_URI_RAW, null, selection,
				selectionArgs, null);
		ArrayList<YearMonthEntity> mArrayList = new ArrayList<YearMonthEntity>();

		for (int j = 1; j <= 12; j++) {
			mArrayList.add(new YearMonthEntity(0, mYear, j, null, getBaseContext().getResources().getStringArray(
					R.array.Month_names)[j - 1], 0));
			Log.d(TAG, mArrayList.get(j - 1).toString());
		}

		if (!(mCursor == null)) {
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
				int month = mCursor.getInt(mCursor.getColumnIndex(IYearMonth.ROW_ID_MONTH));
				String url = mCursor.getString(mCursor.getColumnIndex(IYearMonth.ROW_URL));
				int num = mCursor.getInt(mCursor.getColumnIndex("cnt"));
				//updateYearMonthArray(mCursor.getInt(mCursor.getColumnIndex(IYearMonth.ROW_ID_MONTH)),
				//	mCursor.getString(mCursor.getColumnIndex(IYearMonth.ROW_URL)));
				//Log.d(TAG, mArrayList.get(i-1).toString());

				for (YearMonthEntity c : mArrayList) {
					if (c.getIdMonth() == (month)){
						c.setUrl(url);
						c.setNumOfPhoto(num);
					}
				}
			}
			mCursor.close();
		}
		return mArrayList;

	}

}
