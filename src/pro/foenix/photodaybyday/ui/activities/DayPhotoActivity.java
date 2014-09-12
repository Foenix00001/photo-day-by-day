package pro.foenix.photodaybyday.ui.activities;

import pro.foenix.photodaybyday.ui.fragments.DayPhotoFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

public class DayPhotoActivity extends OneFragmentActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
	}

	@Override
	protected Fragment createFragment() {
		String url = getIntent().getStringExtra(DayPhotoFragment.EXTRA_URL);
		return DayPhotoFragment.newInstance(url);
	}

}
