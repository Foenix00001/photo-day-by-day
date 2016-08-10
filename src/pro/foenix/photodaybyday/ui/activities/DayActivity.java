package pro.foenix.photodaybyday.ui.activities;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.adapters.DayFragmenPagerAdapter;
import pro.foenix.photodaybyday.ui.fragments.DayFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class DayActivity extends FragmentActivity {
	private static final String TAG = "DayActivity";
	ViewPager mPager;
	PagerAdapter mPagerAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int year = getIntent().getIntExtra(DayFragment.EXTRA_YEAR, 0);
		int month = getIntent().getIntExtra(DayFragment.EXTRA_MONTH, 0);
		int day = getIntent().getIntExtra(DayFragment.EXTRA_DAY, 0);
		int day_id = getIntent().getIntExtra(DayFragment.EXTRA_DAY_ID, 0);
		Log.d(TAG, "Год - " + year + " Месяц -58470 " + month + " День - " + day);
		setContentView(R.layout.a_day);
		mPager = (ViewPager) findViewById(R.id.pager);

		mPager.setAdapter(new DayFragmenPagerAdapter(this, getSupportFragmentManager(), year, month, day, day_id));
		mPager.setOffscreenPageLimit(2);
		mPager.setCurrentItem(DayFragmenPagerAdapter.MAX_PAGES / 2, false);
	}

}
