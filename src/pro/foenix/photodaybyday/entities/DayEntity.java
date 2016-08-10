package pro.foenix.photodaybyday.entities;

public class DayEntity {
	private long mId;
	private int mYear;
	private int mMonth;
	private int mDay;
	private String mUrl;

	public DayEntity(int id, int year, int month, int day, String url) {
		mId = id;
		mYear = year;
		mMonth = month;
		mUrl = url;
		mDay = day;
	}

	@Override
	public String toString() {
		return " Год:// " + mYear + ", месяц: " + mMonth + ", день: " + mDay + ", url = " + mUrl;
	}

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public long getId() {
		return mId;
	}

	public int getYear() {
		return mYear;
	}

	public int getMonth() {
		return mMonth;
	}

	public int getDay() {
		return mDay;
	}
}
