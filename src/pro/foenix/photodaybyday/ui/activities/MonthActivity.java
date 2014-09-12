package pro.foenix.photodaybyday.ui.activities;

import pro.foenix.photodaybyday.ui.fragments.MonthFragment;
import android.support.v4.app.Fragment;



public class MonthActivity extends OneFragmentActivity {
	
	@Override
	protected Fragment createFragment() {
		//long id = getIntent().getIntExtra(MonthFragment.EXTRA_ID,0);
		int year = getIntent().getIntExtra(MonthFragment.EXTRA_YEAR,0);
		int month = getIntent().getIntExtra(MonthFragment.EXTRA_MONTH, 0);
		return MonthFragment.newInstance(year, month);
	}
	
}
