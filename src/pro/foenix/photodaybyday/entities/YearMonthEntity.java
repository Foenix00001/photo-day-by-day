package pro.foenix.photodaybyday.entities;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class YearMonthEntity {
	private long mId;
	private int mYear;
	private int mIdMonth;
	private String mUrl;
	private String mMonth;
	private int mNumOfPhoto; 
	private int mNumDaysInMonth;

	public int getNumDaysInMonth() {
		return mNumDaysInMonth;
	}

	public YearMonthEntity() {

	}

	public YearMonthEntity(long id, int year, int idMonth, String url, String month, int num) {
		mId = id;
		mYear = year;
		mIdMonth = idMonth;
		mUrl = url;
		mMonth = month;
		mNumOfPhoto = num;

		Calendar cal = new GregorianCalendar(mYear, mIdMonth-1, 1);
		mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
	}

	public long getId() {
		return mId;
	}

/*	public void setId(long id) {
		mId = id;
	}*/

	public int getYear() {
		return mYear;
	}

/*	public void setYear(int year) {
		mYear = year;
	}*/

	public int getIdMonth() {
		return mIdMonth;
	}

/*	public void setIdMonth(int idMonth) {
		mIdMonth = idMonth;
		Calendar cal = new GregorianCalendar(mYear, mIdMonth-1, 1);
		mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
	}*/

	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public String getMonth() {
		return mMonth;
	}

	public void setMonth(String url) {
		mUrl = url;
	}
	
	public long getNumOfPhoto() {
		return mNumOfPhoto;
	}

	public void setNumOfPhoto(int num) {
		mNumOfPhoto = num;
	}

	@Override
	public String toString() {
		return " Год: " + mYear + ", месяц: " + mMonth + ", url = " + mUrl;
	}

}
