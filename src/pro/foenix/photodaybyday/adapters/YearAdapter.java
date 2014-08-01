package pro.foenix.photodaybyday.adapters;

import pro.foenix.photodaybyday.R;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class YearAdapter extends BaseAdapter {
	private Context mContext;

	public YearAdapter(Context context) {
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 12;
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
		/*ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
		} else {
			imageView = (ImageView) convertView;
		}
		Picasso.with(mContext).load(R.raw.picture_empty)
				.placeholder(R.raw.hourglass).error(R.raw.picture_empty)
				.noFade().into(imageView);
		//.noFade().resize(150, 150).centerCrop().into(imageView);
		// Picasso.with(mContext).load("http://i.imgur.com/DvpvklR.png").into(imageView);
		return imageView;*/
		View cellView;
		if (convertView == null) {
			cellView = new View(mContext);
			LayoutInflater inflater = (LayoutInflater)mContext.getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			//LayoutInflater inflater = mContext.getLayoutInflater();
			cellView=inflater.inflate(R.layout.gridview_year_item, parent, false);
		} else {
			cellView = (View) convertView;
		}
		ImageView imageView = (ImageView) cellView.findViewById(R.id.iv_picture);
		Picasso.with(mContext).load(R.raw.picture_empty)
		.placeholder(R.raw.hourglass).error(R.raw.picture_empty).fit()
		.noFade().into(imageView);
//.noFade().resize(150, 150).centerCrop().into(imageView);
		TextView tvMonth = (TextView) cellView.findViewById(R.id.tv_month);
		tvMonth.setText(mContext.getResources().getStringArray(R.array.Month_names)[position]);

		return cellView;
	}

}
