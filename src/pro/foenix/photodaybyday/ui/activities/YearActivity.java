package pro.foenix.photodaybyday.ui.activities;

import java.util.ArrayList;
import java.util.Calendar;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.adapters.YearAdapter;
import pro.foenix.photodaybyday.database.IPictures;
import pro.foenix.photodaybyday.entities.YearEntity;
import pro.foenix.photodaybyday.ui.fragments.MonthFragment;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

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
		mYearGrigView = (GridView) findViewById(R.id.gv_year);
		mYearTextView = (TextView) findViewById(R.id.tv_caption);
		if (savedInstanceState != null) {
			mYear = savedInstanceState.getInt(KEY_YEAR, 0);
		} else {
			mYear = Calendar.getInstance().get(Calendar.YEAR);
		}
		updateYear();

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
		 
		
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
			
			.build();
		//DisplayImageOptions options = new DisplayImageOptions.Builder()
			//.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build();
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true) 
			.cacheOnDisk(true) 
			//.showImageOnLoading(R.drawable.picture_empty_january)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build(); 
		ImageLoader.getInstance().init(config);
		mYearGrigView.setOnItemClickListener(new OnItemClickListener() 
		{
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		    {
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
		String[] projection = { IPictures.KEY_ROWID, IPictures.ROW_MONTH, IPictures.ROW_URL };
		String[] selectionArgs = { Integer.toString(year), "1" };
		String selection = IPictures.ROW_YEAR + " = ? and "+ IPictures.ROW_FL_STAR + " = ?";

		Cursor mCursor = getBaseContext().getContentResolver().query(IPictures.CONTENT_URI, projection, selection,
				selectionArgs, null);
		ArrayList<YearEntity> mArrayList = new ArrayList<YearEntity>();

		for (int j = 1; j <= 12; j++) {
			mArrayList.add(new YearEntity(0, mYear, j, null, getBaseContext().getResources().getStringArray(
					R.array.month_names)[j - 1], 0));
			Log.d(TAG, mArrayList.get(j - 1).toString());
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
					if (c.getIdMonth() == (month)){
						c.setUrl(url);
				//		c.setNumOfPhoto(num);
					}
				}
			}
			mCursor.close();
		}
		return mArrayList;
	}

}
