package pro.foenix.photodaybyday.adapters;

import java.util.Calendar;

import pro.foenix.photodaybyday.AppPhotoDayByDay;
import pro.foenix.photodaybyday.ui.fragments.DayFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class DayFragmenPagerAdapter extends FragmentStatePagerAdapter {

	private static final String TAG = "DayFragmenPagerAdapter";
	public static final int MAX_PAGES = 10000;
	private int mInitalYear, mInitalMonth, mInitalDay, mInitalDayId;
	private int mCurrYear, mCurrMonth, mCurrDay, mCurrDayId;
	Context mContext;

	public DayFragmenPagerAdapter(Context context, FragmentManager fm, int year, int month, int day, int day_id) {
		super(fm);
		this.mInitalDay = day;
		this.mInitalDayId = day_id;
		this.mInitalMonth = month;
		this.mInitalYear = year;
		this.mContext=context;
	}

	@Override
	public int getCount() {
		return MAX_PAGES;
	}

	@Override
	public Fragment getItem(int position) {
		Calendar c = Calendar.getInstance();
		c.set(mInitalYear, mInitalMonth - 1, mInitalDay);
		//Log.d(TAG, "Год - "+ mInitalYear+ " Месяц - "+mInitalYear+ " День - "+c.get(Calendar.DAY_OF_MONTH));
		int n = position - MAX_PAGES / 2;
		c.add(Calendar.DATE, n);
		//Log.d(TAG, "position - " + n);
		Bundle args = new Bundle();
		this.mCurrDay = c.get(Calendar.DAY_OF_MONTH);
		this.mCurrMonth =c.get(Calendar.MONTH) + 1;
		this.mCurrYear = c.get(Calendar.YEAR);
		
		this.mCurrDayId = AppPhotoDayByDay.getBaseModel(mContext).getDayId(mCurrYear, mCurrMonth, mCurrDay);
		args.putInt(DayFragment.EXTRA_YEAR, mCurrYear);
		args.putInt(DayFragment.EXTRA_MONTH, mCurrMonth);
		args.putInt(DayFragment.EXTRA_DAY, mCurrDay);
		args.putInt(DayFragment.EXTRA_DAY_ID, mCurrDayId);
		//Log.d(TAG,	"Год - " + c.get(Calendar.YEAR) + " Месяц - " + c.get(Calendar.MONTH) + " День - "		+ c.get(Calendar.DAY_OF_MONTH));
		DayFragment fragment = new DayFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public int getItemPosition(Object object) {
		return FragmentStatePagerAdapter.POSITION_NONE;
	}
}
