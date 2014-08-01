package pro.foenix.photodaybyday.ui.activities;

import java.util.Calendar;

import pro.foenix.photodaybyday.R;
import pro.foenix.photodaybyday.adapters.YearAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

public class YearActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        TextView tvYear = (TextView) findViewById(R.id.tvYear);
        tvYear.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        gridview.setAdapter(new YearAdapter(this)); 
    }
}
