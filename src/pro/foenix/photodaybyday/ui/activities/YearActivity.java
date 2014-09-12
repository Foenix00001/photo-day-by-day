package pro.foenix.photodaybyday.ui.activities;

import java.util.ArrayList;
import java.util.Calendar;

import pro.foenix.photodaybyday.AppPhotoDayByDay;
import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.adapters.YearAdapter;
import pro.foenix.photodaybyday.database.IPictures;
import pro.foenix.photodaybyday.entities.YearEntity;
import pro.foenix.photodaybyday.ui.fragments.MonthFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class YearActivity extends Activity implements IPictures {
	private static final String TAG = "YearActivity";
	private static final String KEY_YEAR = "year";
	private int mYear;
	private TextView mYearTextView;
	private ImageButton mNextButton, mPrevButton;
	private GridView mYearGrigView;
	private ArrayList<YearEntity> mYearArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.a_year);
		/*Log.d(TAG, "DIRECTORY_PICTURES =  "+ 
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString());
		Log.d(TAG, "DIRECTORY_DCIM =  "+ 
				Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
		Log.d(TAG, "  DIRECTORY_MUSIC =  "+ 
				Environment.getExternalStoragePublicDirectory(Environment.  DIRECTORY_MUSIC).toString());
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File (sdCard.getAbsolutePath() + "/lalala/lalala2");
		dir.mkdirs();
		 */
		//File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		mYearGrigView = (GridView) findViewById(R.id.gv_year);
		mYearTextView = (TextView) findViewById(R.id.tv_caption);
		if (savedInstanceState != null) {
			mYear = savedInstanceState.getInt(KEY_YEAR, 0);
		} else {
			mYear = Calendar.getInstance().get(Calendar.YEAR);
		}
		//updateYear();

		mNextButton = (ImageButton) findViewById(R.id.ib_next);
		mPrevButton = (ImageButton) findViewById(R.id.ib_prev);
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

		/*ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).build();
		ImageLoader.getInstance().init(config);*/
		
		mYearGrigView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent intent = new Intent(getBaseContext(), MonthActivity.class);
				YearEntity ent = mYearArray.get(position);
				//intent.putExtra(MonthFragment.EXTRA_ID, ent.getId());
				intent.putExtra(MonthFragment.EXTRA_YEAR, ent.getYear());
				intent.putExtra(MonthFragment.EXTRA_MONTH, ent.getMonth());
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		updateYear();
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_YEAR, mYear);
	}

	private void updateYear() {
		mYearArray = getYearData(mYear);
		//запрос в бд по году плюс обновление картинок
		mYearTextView.setText(String.valueOf(mYear));
		mYearGrigView.setAdapter(new YearAdapter(this, mYearArray));
	}

	private ArrayList<YearEntity> getYearData(int year) {
		//String selection = IYearMonth.ROW_YEAR + " = ?";
		/*String[] projection = { IPictures.KEY_ROWID, IPictures.ROW_MONTH, IPictures.ROW_URL };
		String[] selectionArgs = { Integer.toString(year), "1" };
		String selection = IPictures.ROW_YEAR + " = ? and " + IPictures.ROW_FL_STAR + " = ?";

		Cursor mCursor = getBaseContext().getContentResolver().query(IPictures.CONTENT_URI, projection, selection,
				selectionArgs, null);
		ArrayList<YearEntity> mArrayList = new ArrayList<YearEntity>();

		for (int j = 1; j <= 12; j++) {
			mArrayList.add(new YearEntity(0, mYear, j, null, getBaseContext().getResources().getStringArray(
					R.array.month_names)[j - 1], 0));
			//Log.d(TAG, mArrayList.get(j - 1).toString());
		}

		if (!(mCursor == null)) {
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
				int month = mCursor.getInt(mCursor.getColumnIndex(IPictures.ROW_MONTH));
				String url = mCursor.getString(mCursor.getColumnIndex(IPictures.ROW_URL));
				//int num = 0; 
				//mCursor.getInt(mCursor.getColumnIndex("cnt"));
				//updateYearMonthArray(mCursor.getInt(mCursor.getColumnIndex(IYearMonth.ROW_ID_MONTH)),
				//mCursor.getString(mCursor.getColumnIndex(IYearMonth.ROW_URL)));
				//Log.d(TAG, mArrayList.get(i-1).toString());
				for (YearEntity c : mArrayList) {
					if (c.getIdMonth() == (month)) {
						c.setUrl(url);
						//		c.setNumOfPhoto(num);
					}
				}
			}
			mCursor.close();
		}*/

		ArrayList<YearEntity> mArrayList = AppPhotoDayByDay.getBaseModel(this).getMonthsInYear(year);
		return mArrayList;
	}

}
