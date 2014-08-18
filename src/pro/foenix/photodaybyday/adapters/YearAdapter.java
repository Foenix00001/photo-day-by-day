package pro.foenix.photodaybyday.adapters;

import java.util.ArrayList;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.entities.YearEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class YearAdapter extends BaseAdapter {
	private static final String TAG = "YearAdapter";
	private Context mContext;
	private ArrayList<YearEntity> mYearArray;

	public YearAdapter(Context context, ArrayList<YearEntity> entity) {
		mContext = context;
		mYearArray = entity;
	}

	@Override
	public int getCount() {
		return mYearArray.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View cellView;
		ViewHolderItem viewHolder;
		if (convertView == null) {
			cellView = new View(mContext);
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			cellView = inflater.inflate(R.layout.i_gridview_year, parent, false);
			viewHolder = new ViewHolderItem();
			viewHolder.imageView = (ImageView) cellView.findViewById(R.id.iv_picture);
			viewHolder.tvMonth = (TextView) cellView.findViewById(R.id.tv_month);
			//viewHolder.tvNumber = (TextView) cellView.findViewById(R.id.tv_number);
			cellView.setTag(viewHolder);

		} else {
			cellView = (View) convertView;
			viewHolder = (ViewHolderItem) convertView.getTag();
		}
		/*if (imageHeight==0){
			viewHolder.imageView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			imageWidth = viewHolder.imageView.getMeasuredWidth();
			imageHeight = viewHolder.imageView.getMeasuredHeight();
		}*/
		/*	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2).build(); */
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.picture_empty_january).imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.build();
		if (mYearArray.get(position).getUrl() != null) {
			//viewHolder.imageView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			//int widht = viewHolder.imageView.getWidth();
			//int height = viewHolder.imageView.getHeight();
			//File f = new File(mYearMonthArray.get(position).getUrl());
			//Picasso.with(mContext).load(f).placeholder(R.raw.hourglass).error(R.raw.picture_empty)
			//	.fit().centerCrop().noFade().into(viewHolder.imageView);
			//.into(viewHolder.imageView);
			//	.centerCrop().resize(widht, height).noFade().into(viewHolder.imageView);
			//.noFade().resize(150, 150).centerCrop().into(imageView);

			ImageLoader.getInstance().displayImage(mYearArray.get(position).getUrl(), viewHolder.imageView, options);

			//Log.d(TAG, mYearArray.get(position).getUrl());
			viewHolder.tvMonth.setText(mYearArray.get(position).getMonthName());
			//viewHolder.tvNumber.setText(mYearArray.get(position).getNumOfPhoto()+"/"+mYearArray.get(position).getNumDaysInMonth());
		} else {
			//Picasso.with(mContext).load(R.raw.picture_empty_january).placeholder(R.raw.hourglass).error(R.raw.picture_empty)
			//	.fit().centerCrop().noFade().into(viewHolder.imageView);
			ImageLoader.getInstance().displayImage("drawable://" + R.drawable.picture_empty_january,
					viewHolder.imageView, options);
			viewHolder.tvMonth.setText(mYearArray.get(position).getMonthName());

			//viewHolder.tvNumber.setText("0/"+mYearArray.get(position).getNumDaysInMonth());
		}
		return cellView;
	}

	static class ViewHolderItem {
		ImageView imageView;
		TextView tvMonth;
		//TextView tvNumber;

	}

}
