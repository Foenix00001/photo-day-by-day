package pro.foenix.photodaybyday.ui.fragments;

import java.util.ArrayList;
import java.util.Calendar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.database.IPictures;
import pro.foenix.photodaybyday.entities.MonthEntity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DayFragment extends Fragment {
	public static final String EXTRA_YEAR = "pro.foenix.photodaybyday.dayintent.year";
	public static final String EXTRA_MONTH = "pro.foenix.photodaybyday.dayintent.month";
	public static final String EXTRA_DAY = "pro.foenix.photodaybyday.dayintent.day";
	private static final String TAG = "DayFragment";
	private int mYear;
	private int mMonth;
	private int mDay;
	private ImageView mImage1, mImage2, mImage3;
	private ImageView mImage;
	private ImageButton mIbRecord;
	private TextView mTvNote, mTvCaption;
	private String[] idOfImages = { "iv_1", "iv_2", "iv_3" };
	ArrayList<ImageView> array_images = new ArrayList<ImageView>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mYear = getArguments().getInt(EXTRA_YEAR);
		mMonth = getArguments().getInt(EXTRA_MONTH);
		mDay = getArguments().getInt(EXTRA_DAY);

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

		mImage = (ImageView) v.findViewById(R.id.iv_photo);
		mIbRecord = (ImageButton) v.findViewById(R.id.ib_record);
		mTvNote = (TextView) v.findViewById(R.id.tv_note);
		mTvCaption = (TextView) v.findViewById(R.id.tv_caption);
		Log.d(TAG, "��� - "+ mYear+ " ����� - "+mMonth+ " ���� - "+mDay);
		mTvCaption.setText(String.valueOf(mDay)+" "+ getActivity().getBaseContext().getResources().getStringArray(	R.array.month_names_short)[mMonth - 1].toUpperCase());
		//updateImages(mYear, mMonth, mDay);
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
}