package pro.foenix.photodaybyday.entities;


public class MonthEntity {
	//private long mId;
	private int mYear;
	private int mMonth;
	private int mDay;
	private String mUrl;
	//private int mNumOfPhoto; 
	
	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	} 

	/*public int getNumOfPhoto() {
		return mNumOfPhoto;----
	}

	public void setNumOfPhoto(int numOfPhoto) {
		mNumOfPhoto = numOfPhoto;
	}*/

	/*public long getId() {
		return mId;
	}*/

	public int getYear() {
		return mYear;
	}

	public int getMonth() {
		return mMonth;
	}

	public int getDay() {
		return mDay;
	}

	
	
	public MonthEntity( int year, int month, int day, String url) {
		//mId = id;
		mYear = year;
		mMonth = month;
		mUrl = url;
		//mNumOfPhoto = num;
		mDay = day;
	}
	
	@Override
	public String toString() {
		return " Год: " + mYear + ", месяц: " + mMonth + ", url = " + mUrl;
	}
}
