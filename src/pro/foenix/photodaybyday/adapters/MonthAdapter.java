package pro.foenix.photodaybyday.adapters;

import java.util.ArrayList;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.entities.MonthEntity;
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

public class MonthAdapter extends BaseAdapter {
	private static final String TAG = "MonthAdapter";
	private Context mContext;
	private ArrayList<MonthEntity> mMonthArray;

	public MonthAdapter(Context context, ArrayList<MonthEntity> entity) {
		mContext = context;
		mMonthArray = entity;
	}

	@Override
	public int getCount() {
		return mMonthArray.size();
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
			cellView = inflater.inflate(R.layout.i_gridview_month, parent, false);
			viewHolder = new ViewHolderItem();
			viewHolder.ivPicture = (ImageView) cellView.findViewById(R.id.iv_picture);
			viewHolder.tvCaption = (TextView) cellView.findViewById(R.id.tv_caption);
			cellView.setTag(viewHolder);
				
		} else {
			cellView = (View) convertView;
			viewHolder = (ViewHolderItem) convertView.getTag();
		} 
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.picture_empty_january)
			.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build(); 
		if (mMonthArray.get(position).getUrl() != null) { 
			ImageLoader.getInstance().displayImage(mMonthArray.get(position).getUrl(), viewHolder.ivPicture, options);
			 
			//Log.d(TAG, mMonthArray.get(position).getUrl());
			viewHolder.tvCaption.setText(mMonthArray.get(position).getDay());
		}else{
			ImageLoader.getInstance().displayImage("drawable://" + R.drawable.picture_empty_january, viewHolder.ivPicture, options);
			//Log.d(TAG,""+ mMonthArray.get(position).getDay());
			viewHolder.tvCaption.setText(String.valueOf(mMonthArray.get(position).getDay()));
		}
		return cellView;
	}

	static class ViewHolderItem {
		ImageView ivPicture;
		TextView tvCaption;
		
	}

}
