package pro.foenix.photodaybyday.ui.activities;

import pro.foenix.photodaybyday.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;



public abstract class OneFragmentActivity extends FragmentActivity {

	protected abstract Fragment createFragment();
	protected int getLayoutResId() {
		return R.layout.a_onefragment;
		}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutResId());
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment == null) {
			fragment = createFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
}
