package pro.foenix.photodaybyday.database;

import java.util.ArrayList;

import android.database.Cursor;
import pro.foenix.photodaybyday.entities.MonthEntity;
import pro.foenix.photodaybyday.entities.YearEntity;

/**
 * Provide a methods for work with database.
 * 
 * @author Foenix
 *
 */

public interface IDBModel {
	/**
	 * Returns ArrayList that contains list of month in particular year and url of its picture.
	 * 
	 * @return ArrayList<YearEntity>
	 */
	public ArrayList<YearEntity> getMonthsInYear(int year);
	
	/**
	 * Returns ArrayList that contains list of days in particular year and month and url of its picture.
	 * 
	 * @return ArrayList<MonthEntity>
	 */
	public ArrayList<MonthEntity> getDaysInMonth(int year, int month);
	
	/**
	 * Returns Cursor object that contains list of picture urls in particular year month day 
	 * 
	 * @return Cursor
	 */
	
	public Cursor getPicturesInDay(int year, int month, int day);
	
	/**
	 * Returns Cursor object that contains note and sound_url
	 * 
	 * @return Cursor
	 */
	public String getDayNote(int year, int month, int day);
	
	/**
	 * inserts picture uri into database
	 * 
	 * @returns int _id of inserted row
	 */
	public int addPicture(int id_day, String url, int year, int month, int day);
	
	/**
	 * Returns number of pictures taken in particular day in a year
	 * 
	 * @return int 0..2
	 */
	public int getNumberOfPictures(int id_day);
	
	/**
	 * Returns _id from Day-table
	 * 
	 * @return int 
	 */
	public int getDayId(int year, int month, int day);
	
	/**
	 * Deletes a row in Pictures table
	 * 
	 *  
	 */
	public void deletePicture(int id_picture);
	
	
}
