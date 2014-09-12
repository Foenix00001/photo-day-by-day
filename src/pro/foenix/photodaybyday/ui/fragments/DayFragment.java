package pro.foenix.photodaybyday.ui.fragments;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pro.foenix.photodaybyday.AppPhotoDayByDay;
import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.database.IDBModel;
import pro.foenix.photodaybyday.database.IPictures;
import pro.foenix.photodaybyday.entities.ThumbnailEntity;
import pro.foenix.photodaybyday.ui.activities.DayPhotoActivity;
import pro.foenix.photodaybyday.utils.FileManaging;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DayFragment extends Fragment {
	public static final String EXTRA_YEAR = "pro.foenix.photodaybyday.dayintent.year";
	public static final String EXTRA_MONTH = "pro.foenix.photodaybyday.dayintent.month";
	public static final String EXTRA_DAY = "pro.foenix.photodaybyday.dayintent.day";
	public static final String EXTRA_DAY_ID = "pro.foenix.photodaybyday.dayintent.dayid";
	private static final int ACTION_TAKE_PHOTO_B = 1;
	//private static final String PATH_FILE_PREFIX = "FILE://";
	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";
	private static final String TAG = "DayFragment";

	private static final String KEY_YEAR = "year";
	private static final String KEY_MONTH = "month";
	private static final String KEY_DAY = "day";
	private static final String KEY_DAYID = "dayid";
	private static final String KEY_PHOTO_PATH = "photopath";
	private int mYear;
	private int mMonth;
	private int mDay;
	private int mDayId;
	private ImageButton mIbRecord;
	private String mCurrentPhotoPath = null;
	private IDBModel mModel;
	protected ActionMode mActionMode;
	private int mSelectedCount = 0;
	//private ThumbnailEntity tagThumbnail;

	private TextView mTvNote, mTvCaption;
	private String[] idOfThumbs = { "iv_1", "iv_2", "iv_3" };
	private String[] idOfEmptyText = { "l_emptytext1", "l_emptytext2", "l_emptytext3" };
	private String[] idOfInclude = { "thumbnail1", "thumbnail2", "thumbnail3" };

	ArrayList<ImageView> array_images = new ArrayList<ImageView>();
	ArrayList<ImageView> array_chks = new ArrayList<ImageView>();
	ArrayList<LinearLayout> array_emptytext = new ArrayList<LinearLayout>();

	/*	private Animator mCurrentAnimator;
		private int mShortAnimationDuration;
		ImageView mExpandedImageView;
		FrameLayout mContainer;*/

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//if (savedInstanceState == null){
		mYear = getArguments().getInt(EXTRA_YEAR);
		mMonth = getArguments().getInt(EXTRA_MONTH);
		mDay = getArguments().getInt(EXTRA_DAY);
		mDayId = getArguments().getInt(EXTRA_DAY_ID);
		//mCurrentPhotoPath = null;

		mModel = AppPhotoDayByDay.getBaseModel(this.getActivity());
		setHasOptionsMenu(true);
	}

	public static DayFragment newInstance(int year, int month, int day, int day_id) {
		Bundle args = new Bundle();
		args.putInt(EXTRA_YEAR, year);
		args.putInt(EXTRA_MONTH, month);
		args.putInt(EXTRA_DAY, day);
		args.putInt(EXTRA_DAY_ID, day_id);
		DayFragment fragment = new DayFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(KEY_YEAR, mYear);
		outState.putInt(KEY_MONTH, mMonth);
		outState.putInt(KEY_DAY, mDay);
		outState.putInt(KEY_DAYID, mDayId);
		outState.putString(KEY_PHOTO_PATH, mCurrentPhotoPath);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.f_day, container, false);
		if (savedInstanceState != null) {
			mYear = savedInstanceState.getInt(KEY_YEAR, 0);
			mMonth = savedInstanceState.getInt(KEY_MONTH, 0);
			mDay = savedInstanceState.getInt(KEY_DAY, 0);
			mDayId = savedInstanceState.getInt(KEY_DAYID, 0);
			mCurrentPhotoPath = savedInstanceState.getString(KEY_PHOTO_PATH);
		}

		for (int position = 0; position < idOfInclude.length; position++) {
			int incId = getActivity().getBaseContext().getResources()
					.getIdentifier(idOfInclude[position], "id", (getActivity()).getBaseContext().getPackageName());
			View inc = (View) v.findViewById(incId);

			/*int imgId = getActivity().getBaseContext().getResources()
					.getIdentifier(idOfThumbs[position], "id", (getActivity()).getBaseContext().getPackageName());*/

			ImageView img = (ImageView) inc.findViewById(R.id.iv_thumbnail);
			ImageView iv_chk = (ImageView) inc.findViewById(R.id.iv_check);
			iv_chk.setVisibility(View.INVISIBLE);
			array_chks.add(iv_chk);
			iv_chk.setBackgroundColor(getActivity().getResources().getIntArray(R.array.year_colors_tran)[mMonth - 1]);
			array_images.add(img);
			img.setOnClickListener(this.onClickThumbnail);
			img.setBackgroundColor(getActivity().getResources().getIntArray(R.array.year_colors)[mMonth - 1]);
			img.setOnLongClickListener(this.onLongClickThumbnail);
			/*img.setOnLongClickListener(new View.OnLongClickListener() {
				// called when the user long-clicks on someView
				public boolean onLongClick(View view) {
					if (mActionMode != null) {
						return false;
					}
					// start the CAB using the ActionMode.Callback defined above
					mActionMode = getActivity().startActionMode(mActionModeCallback);
					view.setSelected(true);
					iv_chk.setVisibility(View.VISIBLE);
					return true;
				}
			});*/
			/*int llId = getActivity().getBaseContext().getResources()
					.getIdentifier(idOfEmptyText[position], "id", (getActivity()).getBaseContext().getPackageName());*/
			//LinearLayout emptytext = (LinearLayout) v.findViewById(llId);
			array_emptytext.add((LinearLayout) inc.findViewById(R.id.l_emptytext));
		}

		mIbRecord = (ImageButton) v.findViewById(R.id.ib_record);
		mTvNote = (TextView) v.findViewById(R.id.tv_note);
		mTvCaption = (TextView) v.findViewById(R.id.tv_caption);
		/*		mPhoto = (ImageView) v.findViewById(R.id.iv_photo);
				mPhoto.setBackgroundColor(getActivity().getResources().getIntArray(R.array.year_colors)[mMonth - 1]);
				mPhoto.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View view) {
		                zoomImageFromThumb(mPhoto, (String) mPhoto.getTag());
		            }
		        });
				mExpandedImageView = (ImageView) v.findViewById(R.id.expanded_image);
				mContainer = (FrameLayout) v.findViewById(R.id.container);
				mShortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);*/
		//mAttacher = new PhotoViewAttacher(mPhoto);
		//Log.d(TAG, "Год - " + mYear + " Месяц - " + mMonth + " День - " + mDay);
		mTvCaption.setText(String.valueOf(mDay)
				+ " "
				+ getActivity().getBaseContext().getResources().getStringArray(R.array.month_names_short)[mMonth - 1]
						.toUpperCase());
		updateImages(mYear, mMonth, mDay);
		updateInfo(mYear, mMonth, mDay);

		return v;
	}

	public OnClickListener onClickThumbnail = new OnClickListener() {
		public void onClick(final View view) {
			ThumbnailEntity tagThumbnail = (ThumbnailEntity) view.getTag();
			if (mActionMode != null) {
				if (tagThumbnail != null && tagThumbnail.isEmpty() == false) {
					if (tagThumbnail.isChecked()) {
						array_chks.get(tagThumbnail.getOrder()).setVisibility(View.INVISIBLE);
						tagThumbnail.setChecked(false);
						view.setSelected(false);
						mSelectedCount--;
					} else {
						array_chks.get(tagThumbnail.getOrder()).setVisibility(View.VISIBLE);
						tagThumbnail.setChecked(true);
						view.setSelected(true);
						mSelectedCount++;
					}
					view.setTag(tagThumbnail);
					if (mSelectedCount == 0) {
						mActionMode.finish();
					} else {
						mActionMode.setTitle(String.valueOf(mSelectedCount) + " images selected");
					}
				}
			} else {
				Intent intent = new Intent(getActivity(), DayPhotoActivity.class);
				intent.putExtra(DayPhotoFragment.EXTRA_URL, tagThumbnail.getUrl());
				startActivity(intent);
			}
		}
	};
	public OnLongClickListener onLongClickThumbnail = new OnLongClickListener() {
		public boolean onLongClick(View view) {
			if (mActionMode != null) {
				return false;
			}
			// start the CAB using the ActionMode.Callback defined above
			mActionMode = getActivity().startActionMode(mActionModeCallback);
			view.setSelected(true);
			ThumbnailEntity tagThumbnail = (ThumbnailEntity) view.getTag();
			array_chks.get(tagThumbnail.getOrder()).setVisibility(View.VISIBLE);
			tagThumbnail.setChecked(true);
			mSelectedCount++;
			view.setTag(tagThumbnail);
			mActionMode.setTitle(String.valueOf(mSelectedCount) + " images selected");
			return true;
		}
	};

	private void takePicture() {
		if (mModel.getNumberOfPictures(mDayId) < 3) {
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File f = null;
			try {
				f = createImageFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				//Log.d(TAG, "mCurrentPhotoPath **  =" + mCurrentPhotoPath);
				//Log.d(TAG, "Uri.fromFile(f) **  =" + Uri.fromFile(f));

				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
				startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO_B);
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCurrentPhotoPath = null;
			}
		} else {
			Toast.makeText(getActivity(), R.string.cant_take_pictures, 3000).show();
		}
	};

	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + JPEG_FILE_SUFFIX;
		File albumF = FileManaging.getImgDir();
		//File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		File imageF = new File(albumF, imageFileName);
		return imageF;
	}

	private void updateImages(int year, int month, int day) {
		Cursor cursor = mModel.getPicturesInDay(year, month, day);
		if (!(cursor == null)) {
			int order;
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				//boolean fl_star = mCursor.getInt(mCursor.getColumnIndex(IPictures.ROW_FL_STAR)) == 1;
				//array_stars.get(position).setChecked(fl_star);

				//String url = PATH_FILE_PREFIX + cursor.getString(cursor.getColumnIndex(IPictures.ROW_URL));
				String url = cursor.getString(cursor.getColumnIndex(IPictures.ROW_URL));
				order = cursor.getInt(cursor.getColumnIndex(IPictures.ROW_ORDER));
				if (url != null) {
					//ImageLoader.getInstance().displayImage(url, array_images.get(order));
					File f = new File(url);
					Picasso.with(getActivity()).load(f).fit().centerCrop().noFade().into(array_images.get(order));
					ThumbnailEntity tagThumbnail = new ThumbnailEntity(order, url);
					array_images.get(order).setTag(tagThumbnail);
					array_emptytext.get(order).setVisibility(View.GONE);
				}
			}
			cursor.close();
		}
	}

	private void updateInfo(int year, int month, int day) {
		mTvNote.setText(mModel.getDayNote(year, month, day));
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
			//Intent i = new Intent(getActivity(), DayCameraActivity.class);
			//i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
			//startActivityForResult(i, REQUEST_PHOTO);
			takePicture();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Log.d(TAG, "requestCode " + requestCode);
		switch (requestCode) {
		case ACTION_TAKE_PHOTO_B: {
			if (resultCode == getActivity().RESULT_OK) {
				Log.d(TAG, "RESULT_OK ");
				handleCameraPhoto();
			}
			break;
		} // ACTION_TAKE_PHOTO_B

		} // switch
	}

	private void handleCameraPhoto() {
		//Log.d(TAG, "mCurrentPhotoPath  =" + mCurrentPhotoPath);
		if (mCurrentPhotoPath != null) {
			int id = mModel.addPicture(mDayId, mCurrentPhotoPath, mYear, mMonth, mDay);
			if (id > 0)
				mDayId = id;
			else {
				if (id == -1) {
					Toast.makeText(getActivity(), R.string.cant_take_pictures, 3000).show();
				}
			}
			mCurrentPhotoPath = null;
			updateImages(mYear, mMonth, mDay);
		}

	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		// Called when the action mode is created; startActionMode() was called
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			// inflate a menu resource providing context menu items
			MenuInflater inflater = mode.getMenuInflater();
			mode.setTitle("бла-бла-бла");
			// assumes that you have "contexual.xml" menu resources
			inflater.inflate(R.menu.context_menu_day, menu);
			return true;
		}

		// called each time the action mode is shown. Always called after
		// onCreateActionMode, but
		// may be called multiple times if the mode is invalidated.
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false; // Return false if nothing is done
		}

		// called when the user selects a contextual menu item
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.delete:
				deleteSelectedPhoto();
				Toast.makeText(getActivity(), "Selected menu", Toast.LENGTH_LONG).show();
				mode.finish(); // Action picked, so close the CAB
				return true;
			default:
				return false;
			}
		}

		// called when the user exits the action mode
		public void onDestroyActionMode(ActionMode mode) {
			mActionMode = null;
		}
	};

	private void deleteSelectedPhoto() {
		for (int i = 0; i < array_images.size(); i++) {
			ThumbnailEntity thumb = (ThumbnailEntity) array_images.get(i).getTag();
			if (thumb.isChecked()){
				
			}
		}

	}
}
