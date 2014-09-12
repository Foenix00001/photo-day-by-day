package pro.foenix.photodaybyday.adapters;

import java.io.File;
import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.entities.YearEntity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class YearAdapter extends BaseAdapter {
	private static final String TAG = "YearAdapter";
	//private static final String PATH_FILE_PREFIX = "FILE://";
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
		return mYearArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mYearArray.get(position).getId();
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
			viewHolder.ivPicture = (ImageView) cellView.findViewById(R.id.iv_picture);
			viewHolder.tvMonth = (TextView) cellView.findViewById(R.id.tv_month);
			viewHolder.lEmptyText = (LinearLayout) cellView.findViewById(R.id.l_emptytext);
			cellView.setTag(viewHolder);
		} else {
			cellView = (View) convertView;
			viewHolder = (ViewHolderItem) convertView.getTag();
		}
		viewHolder.ivPicture.setBackgroundColor(mContext.getResources().getIntArray(R.array.year_colors)[position]);
		viewHolder.tvMonth.setBackgroundColor(mContext.getResources().getIntArray(R.array.year_colors_tran)[position]);
		if (mYearArray.get(position).getUrl() != null) {
			//ImageLoader.getInstance().displayImage(PATH_FILE_PREFIX + mYearArray.get(position).getUrl(),
			//	viewHolder.ivPicture);
			File f = new File(mYearArray.get(position).getUrl());
			Picasso.with(mContext).load(f).fit().centerCrop().noFade().into(viewHolder.ivPicture);
/*			 Glide.with(mContext)
		        .load(PATH_FILE_PREFIX +mYearArray.get(position).getUrl()) 
		        .centerCrop()
		        .crossFade()
		        .into(viewHolder.ivPicture);*/

			//.into(viewHolder.ivPicture);
			//.centerCrop().resize(widht, height).noFade().into(viewHolder.ivPicture);
			//.noFade().resize(150, 150).centerCrop().into(imageView);

			viewHolder.tvMonth.setText(mYearArray.get(position).getMonthName());
			viewHolder.lEmptyText.setVisibility(View.GONE);
		} else {
			viewHolder.tvMonth.setText(mYearArray.get(position).getMonthName());
			viewHolder.lEmptyText.setVisibility(View.VISIBLE);
			viewHolder.ivPicture.setImageResource(0);
		}
		return cellView;
	}

	static class ViewHolderItem {
		ImageView ivPicture;
		TextView tvMonth;
		LinearLayout lEmptyText;
	}

}
