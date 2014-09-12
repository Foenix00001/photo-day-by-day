package pro.foenix.photodaybyday.adapters;

import java.io.File;
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

import com.squareup.picasso.Picasso;

public class MonthAdapter extends BaseAdapter {
	private static final String TAG = "MonthAdapter";
	private static final String PATH_FILE_PREFIX = "FILE://";
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
			viewHolder.tvEmpty = (TextView) cellView.findViewById(R.id.tv_empty);
			cellView.setTag(viewHolder);

		} else {
			cellView = (View) convertView;
			viewHolder = (ViewHolderItem) convertView.getTag();
		}
		viewHolder.ivPicture.setBackgroundColor(mContext.getResources().getIntArray(R.array.year_colors)[mMonthArray
				.get(position).getMonth() - 1]);
		viewHolder.tvCaption
				.setBackgroundColor(mContext.getResources().getIntArray(R.array.year_colors_tran)[mMonthArray.get(
						position).getMonth() - 1]);

		
		if (mMonthArray.get(position).getUrl() != null) {
			//ImageLoader.getInstance().displayImage(PATH_FILE_PREFIX + mMonthArray.get(position).getUrl(),viewHolder.ivPicture);
			File f = new File(mMonthArray.get(position).getUrl());
			Picasso.with(mContext).load(f).fit().centerCrop().noFade().into(viewHolder.ivPicture);
			viewHolder.tvEmpty.setText("");
			//Log.d(TAG,"getUrl() = "+ mMonthArray.get(position).getUrl());
			//Log.d(TAG,"getDay() = "+ mMonthArray.get(position).getDay());
		} else {
			viewHolder.tvEmpty.setText(R.string.photo_day_by_day);
			viewHolder.ivPicture.setImageResource(android.R.color.transparent);
			//Log.d(TAG,""+ mMonthArray.get(position).getDay());
			//viewHolder.tvCaption.setText(String.valueOf(mMonthArray.get(position).getDay()));
		}
		viewHolder.tvCaption.setText(Integer.toString(mMonthArray.get(position).getDay()));

		return cellView;
	}

	static class ViewHolderItem {
		ImageView ivPicture;
		TextView tvCaption;
		TextView tvEmpty;
	}

}
