package pro.foenix.photodaybyday.ui.fragments;

import java.util.ArrayList;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.database.IPictures;
import pro.foenix.photodaybyday.ui.activities.DayCameraActivity;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class DayFragment extends Fragment {
	public static final String EXTRA_YEAR = "pro.foenix.photodaybyday.dayintent.year";
	public static final String EXTRA_MONTH = "pro.foenix.photodaybyday.dayintent.month";
	public static final String EXTRA_DAY = "pro.foenix.photodaybyday.dayintent.day";
	private static final String TAG = "DayFragment";
	private static final int REQUEST_PHOTO = 1;
	private int mYear;
	private int mMonth;
	private int mDay;
	//private ImageView mImage1, mImage2, mImage3;
	private ImageView mImage;
	private ImageButton mIbRecord;

	private TextView mTvNote, mTvCaption;
	private String[] idOfImages = { "iv_1", "iv_2", "iv_3" };
	private String[] idOfStars = { "cb_star1", "cb_star2", "cb_star3" };
	ArrayList<ImageView> array_images = new ArrayList<ImageView>();
	ArrayList<CheckBox> array_stars = new ArrayList<CheckBox>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mYear = getArguments().getInt(EXTRA_YEAR);
		mMonth = getArguments().getInt(EXTRA_MONTH);
		mDay = getArguments().getInt(EXTRA_DAY);
		setHasOptionsMenu(true);
	}

	public static DayFragment newInstance(int year, int month, int day) {
		Bundle args = new Bundle();
		args.putInt(EXTRA_YEAR, year);
		args.putInt(EXTRA_MONTH, month);
		args.putInt(EXTRA_DAY, day);
		DayFragment fragment = new DayFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_day, container, false);
		/*mImage1 = (ImageView) v.findViewById(R.id.iv_1);
		mImage3 = (ImageView) v.findViewById(R.id.iv_2);
		mImage2 = (ImageView) v.findViewById(R.id.iv_3);*/

		for (int position = 0; position < idOfImages.length; position++) {
			int imgId = getActivity().getBaseContext().getResources()
					.getIdentifier(idOfImages[position], "id", (getActivity()).getBaseContext().getPackageName());

			ImageView img = (ImageView) v.findViewById(imgId);
			array_images.add(img);
			//btn.setOnClickListener(this.onClickNum);
		}
		for (int position = 0; position < idOfStars.length; position++) {
			int starId = getActivity().getBaseContext().getResources()
					.getIdentifier(idOfStars[position], "id", (getActivity()).getBaseContext().getPackageName());

			CheckBox chk = (CheckBox) v.findViewById(starId);
			array_stars.add(chk);
			//btn.setOnClickListener(this.onClickNum);
		}
		mImage = (ImageView) v.findViewById(R.id.iv_photo);
		mIbRecord = (ImageButton) v.findViewById(R.id.ib_record);
		mTvNote = (TextView) v.findViewById(R.id.tv_note);
		mTvCaption = (TextView) v.findViewById(R.id.tv_caption);

		Log.d(TAG, "Год - " + mYear + " Месяц - " + mMonth + " День - " + mDay);
		mTvCaption.setText(String.valueOf(mDay)
				+ " "
				+ getActivity().getBaseContext().getResources().getStringArray(R.array.month_names_short)[mMonth - 1]
						.toUpperCase());
		updateImages(mYear, mMonth, mDay);
		return v;
	}

	private void updateImages(int year, int month, int day) {
		String[] projection = { IPictures.KEY_ROWID, IPictures.ROW_NOTE, IPictures.ROW_URL, IPictures.ROW_FL_STAR };
		String[] selectionArgs = { Integer.toString(year), Integer.toString(month), Integer.toString(month) };
		String selection = IPictures.ROW_YEAR + " = ? and " + IPictures.ROW_MONTH + " = ? and " + IPictures.ROW_DAY
				+ " = ?";
		Cursor mCursor = getActivity().getBaseContext().getContentResolver()
				.query(IPictures.CONTENT_URI, projection, selection, selectionArgs, IPictures.ROW_DATA);
		if (!(mCursor == null)) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.picture_empty_january).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
					.build();

			int position = 0;
			for (mCursor.moveToFirst(); !mCursor.isAfterLast(); mCursor.moveToNext()) {
				boolean fl_star = mCursor.getInt(mCursor.getColumnIndex(IPictures.ROW_FL_STAR)) == 1;
				array_stars.get(position).setChecked(fl_star);

				String url = mCursor.getString(mCursor.getColumnIndex(IPictures.ROW_URL));
				String note = mCursor.getString(mCursor.getColumnIndex(IPictures.ROW_NOTE));

				if (url != null) {
					ImageLoader.getInstance().displayImage(url, array_images.get(position), options);
					mTvNote.setText(note);
				} else {
					ImageLoader.getInstance().displayImage("drawable://" + R.drawable.picture_empty_january,
							array_images.get(position), options);
				}

				position++;
			}
			mCursor.close();
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu_day, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_shot:
			Intent i = new Intent(getActivity(), DayCameraActivity.class);
			//i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
			startActivityForResult(i, REQUEST_PHOTO);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK)
			return;
		 if (requestCode == REQUEST_PHOTO) {
			String filename = data.getStringExtra(DayCameraFragment.EXTRA_PHOTO_FILENAME);
			if (filename != null) {
				Log.i(TAG, "filename: " + filename);
			}
		 }
	}
}
