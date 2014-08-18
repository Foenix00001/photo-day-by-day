package pro.foenix.photodaybyday.ui.activities;

import pro.foenix.photodaybyday.ui.fragments.DayCameraFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

public class DayCameraActivity extends OneFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new DayCameraFragment();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
	}
}
