package pro.foenix.photodaybyday.adapters;

import java.io.File;
import java.util.ArrayList;

import pro.foenix.photodaybyday.R; 
import pro.foenix.photodaybyday.entities.YearMonthEntity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.squareup.picasso.Picasso;

public class YearAdapter extends BaseAdapter {
	private static final String TAG = "YearAdapter";
	private Context mContext;
	private ArrayList<YearMonthEntity> mYearMonthArray;
	private int imageWidth=0;
	private int imageHeight=0;

	public YearAdapter(Context context, ArrayList<YearMonthEntity> entity) {
		mContext = context;
		mYearMonthArray = entity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mYearMonthArray.size();
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
			cellView = inflater.inflate(R.layout.gridview_year_item, parent, false);
			viewHolder = new ViewHolderItem();
			viewHolder.imageView = (ImageView) cellView.findViewById(R.id.iv_picture);
			viewHolder.tvMonth = (TextView) cellView.findViewById(R.id.tv_month);
			viewHolder.tvNumber = (TextView) cellView.findViewById(R.id.tv_number);
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
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build(); 
		if (mYearMonthArray.get(position).getUrl() != null) { 
			//viewHolder.imageView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
			//int widht = viewHolder.imageView.getWidth();
			//int height = viewHolder.imageView.getHeight();
			//File f = new File(mYearMonthArray.get(position).getUrl());
			//Picasso.with(mContext).load(f).placeholder(R.raw.hourglass).error(R.raw.picture_empty)
					//.into(viewHolder.imageView);
				//	.centerCrop().resize(widht, height).noFade().into(viewHolder.imageView);
			//.noFade().resize(150, 150).centerCrop().into(imageView);
			ImageLoader.getInstance().displayImage(mYearMonthArray.get(position).getUrl(), viewHolder.imageView, options);
			Log.d(TAG, mYearMonthArray.get(position).getUrl());
			viewHolder.tvMonth.setText(mYearMonthArray.get(position).getMonth());
			viewHolder.tvNumber.setText(mYearMonthArray.get(position).getNumOfPhoto()+"/"+mYearMonthArray.get(position).getNumDaysInMonth());
		}else{
			//Picasso.with(mContext).load(R.raw.picture_empty).placeholder(R.raw.hourglass).error(R.raw.picture_empty)
				//	.noFade().into(viewHolder.imageView);
			ImageLoader.getInstance().displayImage("drawable://" + R.drawable.picture_empty, viewHolder.imageView, options);
			viewHolder.tvMonth.setText(mYearMonthArray.get(position).getMonth());
			
			viewHolder.tvNumber.setText("0/"+mYearMonthArray.get(position).getNumDaysInMonth());
		}
		return cellView;
	}

	static class ViewHolderItem {
		ImageView imageView;
		TextView tvMonth;
		TextView tvNumber;
		
	}

}
