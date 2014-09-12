package pro.foenix.photodaybyday.ui.fragments;

import pro.foenix.photodaybyday.utils.PictureUtils;
import uk.co.senab.photoview.PhotoViewAttacher;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DayPhotoFragment extends Fragment {
	private String mUrl;
	private ImageView mPhoto;
	private PhotoViewAttacher mAttacher;
	public static final String EXTRA_URL = "pro.foenix.photodaybyday.dayphotointent.url";
	private static final String TAG = "DayPhotoFragment";
	private boolean isAttacherDisposed = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUrl = getArguments().getString(EXTRA_URL);
	}

	public static DayPhotoFragment newInstance(String url) {
		Bundle args = new Bundle();
		args.putString(EXTRA_URL, url);
		DayPhotoFragment fragment = new DayPhotoFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		//View v = inflater.inflate(R.layout.f_photo, container, false);
		//mPhoto = (ImageView) v.findViewById(R.id.iv_photo);
		//mPhoto.setBackgroundColor(getActivity().getResources().getIntArray(R.array.year_colors)[0]);
		Log.d(TAG, "mUrl=" + mUrl);
		//TouchImageView mPhoto = new TouchImageView(getActivity());
		mPhoto = new ImageView(getActivity());

		BitmapDrawable bitmap = null;
		bitmap = PictureUtils.getScaledDrawable(getActivity(), mUrl);
		mPhoto.setImageDrawable(bitmap);

		mAttacher = new PhotoViewAttacher(mPhoto);
		return mPhoto;

		//setContentView(img);
		/*File f = new File(mUrl);
		 Display display = getActivity().getWindowManager().getDefaultDisplay();
		    Point size = new Point();
		    display.getSize(size);
		    int width = size.x;
		    int height = size.y;
		    
		     Callback imageLoadedCallback = new Callback() {
		    	 
		        @Override 
		        public void onSuccess() { 
		            if(mAttacher!=null){ 
		                mAttacher.update(); 
		            }else{ 
		                mAttacher = new PhotoViewAttacher(mPhoto);
		                //mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER); 
		            } 
		        	 
		            // root.findViewById(R.id.progress).setVisibility(View.GONE);
		        } 
		     
		        @Override 
		        public void onError() { 
		            // TODO Auto-generated method stub 
		     
		        } 
		    }; 
		     
		Picasso.with(getActivity()).load(f).fit().noFade().into(mPhoto, imageLoadedCallback);
		  isAttacherDisposed = false;
		mAttacher = new PhotoViewAttacher(mPhoto);
		//mAttacher.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		
		return v;*/
	}
}
