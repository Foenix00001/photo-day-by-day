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
//	@Override
//		protected Fragment createFragment() {
//			//long id = getIntent().getIntExtra(MonthFragment.EXTRA_ID,0);
//			int year = getIntent().getIntExtra(DayFragment.EXTRA_YEAR,0);
//			int month = getIntent().getIntExtra(DayFragment.EXTRA_MONTH, 0);
//			int day = getIntent().getIntExtra(DayFragment.EXTRA_DAY, 0);
//			return DayFragment.newInstance(year, month, day);
//		}
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        int year = getIntent().getIntExtra(DayFragment.EXTRA_YEAR,0);
			int month = getIntent().getIntExtra(DayFragment.EXTRA_MONTH, 0);
			int day = getIntent().getIntExtra(DayFragment.EXTRA_DAY, 0);
			Log.d(TAG, "Год - "+year+ " Месяц - "+month+ " День - "+day);
	        setContentView(R.layout.a_day);
	        mPager = (ViewPager) findViewById(R.id.pager);
	        
	       /* Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, year);
	        cal.set(Calendar.MONTH, month);
	        cal.set(Calendar.DAY_OF_MONTH, day);
	        Date date = cal.getTime();*/
	        mPager.setAdapter(new DayFragmenPagerAdapter(getSupportFragmentManager(), year, month, day));
	        mPager.setOffscreenPageLimit(2);
	        mPager.setCurrentItem(DayFragmenPagerAdapter.MAX_PAGES/2,false);
	    }

}
