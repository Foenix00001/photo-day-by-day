package pro.foenix.photodaybyday.entities;


public class YearEntity {
	private long mId;
	private int mYear;
	private int mMonth;
	private String mUrl;
	private String mMonthName;
	private int mNumOfPhoto; 
	private int mNumDaysInMonth;

	public int getNumDaysInMonth() {
		return mNumDaysInMonth;
	}

	/*public YearMonthEntity() {

	}*/

	public YearEntity(long id, int year, int month, String url, String monthName, int num) {
		mId = id;
		mYear = year;
		mMonthName = monthName;
		mUrl = url;
		mMonth = month;
		mNumOfPhoto = num;

		//Calendar cal = new GregorianCalendar(mYear, mMonth-1, 1);
		//mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
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
		return mMonth;
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

	public int getMonth() {
		return mMonth;
	}
	
	public String getMonthName() {
		return mMonthName;
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
		return " Год: " + mYear + ", месяц: " + mMonthName + ", url = " + mUrl;
	}

}
