package pro.foenix.photodaybyday.adapters;

import java.util.Calendar;
import java.util.Date;

import pro.foenix.photodaybyday.ui.fragments.DayFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;

public class DayFragmenPagerAdapter extends FragmentStatePagerAdapter {

	private static final String TAG = "DayFragmenPagerAdapter";
	public static final int MAX_PAGES=10000;
	private int mInitalYear, mInitalMonth, mInitalDay;
	private int pos = 0;

	public DayFragmenPagerAdapter(FragmentManager fm, int year, int month, int day) {
		super(fm);
		this.mInitalDay = day;
		this.mInitalMonth = month;
		this.mInitalYear = year;

	}

	@Override
	public int getCount() {
		return MAX_PAGES;
	}

	/*@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return false;
	}*/
 
	@Override
	public Fragment getItem(int position) {
		Calendar c = Calendar.getInstance();
		c.set(mInitalYear, mInitalMonth-1, mInitalDay);
		//Log.d(TAG, "Год - "+ mInitalYear+ " Месяц - "+mInitalYear+ " День - "+c.get(Calendar.DAY_OF_MONTH));
		int n = position-MAX_PAGES/2;
		c.add(Calendar.DATE, n);
		Log.d(TAG, "position - "+ n);
		Bundle args = new Bundle();
		args.putInt(DayFragment.EXTRA_YEAR, c.get(Calendar.YEAR));
		args.putInt(DayFragment.EXTRA_MONTH, c.get(Calendar.MONTH)+1);
		args.putInt(DayFragment.EXTRA_DAY, c.get(Calendar.DAY_OF_MONTH));
		Log.d(TAG, "Год - "+ c.get(Calendar.YEAR)+ " Месяц - "+c.get(Calendar.MONTH)+ " День - "+c.get(Calendar.DAY_OF_MONTH));
		DayFragment fragment = new DayFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return FragmentStatePagerAdapter.POSITION_NONE; 
	} 
}
