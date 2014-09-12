package pro.foenix.photodaybyday.ui.fragments;

import java.util.ArrayList;

import pro.foenix.photodaybyday.AppPhotoDayByDay;
import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.adapters.MonthAdapter;
import pro.foenix.photodaybyday.entities.MonthEntity;
import pro.foenix.photodaybyday.ui.activities.DayActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

public class MonthFragment extends Fragment {
	//public static final String EXTRA_ID = "pro.foenix.photodaybyday.monthdayintent.id";
	public static final String EXTRA_YEAR = "pro.foenix.photodaybyday.monthintent.year";
	public static final String EXTRA_MONTH = "pro.foenix.photodaybyday.monthintent.month";
	private static final String KEY_YEAR = "year";
	private static final String KEY_MONTH = "month";
	private static final String TAG = "MonthFragment";
	private ArrayList<MonthEntity> mMonthArray;
	private GridView mMonthGridView;
	private TextView mCaptionTextView;
	//private int mId;
	private int mYear;
	private int mMonth;
	private ImageButton mNextButton, mPrevButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//
		mYear = getArguments().getInt(EXTRA_YEAR);
		mMonth = getArguments().getInt(EXTRA_MONTH);

		//getActivity().setTitle(R.string.crimes_title);
		//mMonthDayEntity = CrimeLab.get(getActivity()).getCrimes();

	}

	public static MonthFragment newInstance(int year, int month) {
		Bundle args = new Bundle();
		//args.putLong(EXTRA_ID, id);
		args.putInt(EXTRA_YEAR, year);
		args.putInt(EXTRA_MONTH, month);
		MonthFragment fragment = new MonthFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_YEAR, mYear);
		outState.putInt(KEY_MONTH, mMonth);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_month, container, false);
		if (savedInstanceState != null) {
			mYear = savedInstanceState.getInt(KEY_YEAR, 0);
			mMonth = savedInstanceState.getInt(KEY_MONTH, 0);
		}
		mMonthGridView = (GridView) v.findViewById(R.id.gv_month);
		mCaptionTextView = (TextView) v.findViewById(R.id.tv_caption);

		mNextButton = (ImageButton) v.findViewById(R.id.ib_next);
		mPrevButton = (ImageButton) v.findViewById(R.id.ib_prev);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMonth++;
				updateMonth();
			}
		});
		mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mMonth--;
				updateMonth();
			}
		});
		mMonthGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent intent = new Intent(getActivity().getBaseContext(), DayActivity.class);
				MonthEntity ent = mMonthArray.get(position);
				//intent.putExtra(MonthFragment.EXTRA_ID, ent.getId());
				intent.putExtra(DayFragment.EXTRA_YEAR, ent.getYear());
				intent.putExtra(DayFragment.EXTRA_MONTH, ent.getMonth());
				intent.putExtra(DayFragment.EXTRA_DAY, ent.getDay());
				// Log.d(TAG, "√од - "+ent.getYear()+ " ћес€ц - "+ent.getMonth()+ " ƒень - "+ent.getDay());
				startActivity(intent);
			}
		});
		//updateMonth();
		return v;
	}

	@Override
	public void onResume() {
		updateMonth();
		super.onResume();
	}

	private void updateMonth() {
		mMonthArray = getMonthData(mYear, mMonth);
		//запрос в бд по году плюс обновление картинок
		//Log.d(TAG, "mYear = "+ mYear + ", mMonth = "+ mMonth);
		//Log.d(TAG, "ћес€ц = "+ getActivity().getBaseContext().getResources().getStringArray(R.array.Month_names)[mMonth - 1]);
		mCaptionTextView
				.setText(getActivity().getBaseContext().getResources().getStringArray(R.array.month_names)[mMonth - 1]
						+ "` " + String.valueOf(mYear).substring(2));
		mMonthGridView.setAdapter(new MonthAdapter(this.getActivity(), mMonthArray));
		if (mMonth == 12) {
			mNextButton.setEnabled(false);
		} else {
			mNextButton.setEnabled(true);
		}
		if (mMonth == 1) {
			mPrevButton.setEnabled(false);
		} else {
			mPrevButton.setEnabled(true);
		}

	}

	private ArrayList<MonthEntity> getMonthData(int year, int month) {
		/*String[] projection = { IPictures.KEY_ROWID, IPictures.ROW_DAY, IPictures.ROW_URL };
		String[] selectionArgs = { Integer.toString(year), "1", Integer.toString(month)};
		String selection = IPictures.ROW_YEAR + " = ? and "+ IPictures.ROW_FL_STAR + " = ? and "+IPictures.ROW_MONTH + " = ?";

		Cursor mCursor = getActivity().getBaseContext().getContentResolver().query(IPictures.CONTENT_URI, projection, selection,
				selectionArgs, null);

		ArrayList<MonthEntity> mArrayList = new ArrayList<MonthEntity>();
		Calendar cal = new GregorianCalendar(mYear, mMonth-1, 1);
		int mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
		
		for (int j = 1; j <= mNumDaysInMonth; j++) {
			mArrayList.add(new MonthEntity(mYear, mMonth, j, null));
			//Log.d(TAG, mArrayList.get(j - 1).toString());
		}

		if (!(mCursor == null)) {
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
				int day = mCursor.getInt(mCursor.getColumnIndex(IPictures.ROW_DAY));
				String url = mCursor.getString(mCursor.getColumnIndex(IPictures.ROW_URL));
				//updateYearMonthArray(mCursor.getInt(mCursor.getColumnIndex(IYearMonth.ROW_ID_MONTH)),
				//	mCursor.getString(mCursor.getColumnIndex(IYearMonth.ROW_URL)));
				//Log.d(TAG, mArrayList.get(i-1).toString());

				for (MonthEntity c : mArrayList) {
					if (c.getDay() == (day)){
						c.setUrl(url);
					}
				}
			}
			mCursor.close();
		}*/
		ArrayList<MonthEntity> mArrayList = AppPhotoDayByDay.getBaseModel(this.getActivity()).getDaysInMonth(year,
				month);
		return mArrayList;

	}

}
