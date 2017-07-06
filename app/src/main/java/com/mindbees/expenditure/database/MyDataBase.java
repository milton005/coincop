package com.mindbees.expenditure.database;

import java.util.ArrayList;

import com.mindbees.expenditure.model.ReminderSet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBase extends SQLiteOpenHelper{
	
	
	private SQLiteDatabase sqLiteDatabase;
	
	public static final String MYDATABASE_NAME = "coincop";
	public static final int MYDATABASE_VERSION = 1;
	
	//table for reminder
	
	public static final String TABLE_REMINDER = "reminder";
	public static final String KEY_ID = "id";
	public static final String KEY_DESC = "description";
	public static final String KEY_DATE = "reminder_date";
	public static final String KEY_TIME = "reminder_time";
	
	// table for currency
	public static final String TABLE_CURRENCY = "currency";
	public static final String KEY_C_ID = "currency_id";
	public static final String KEY_C_NAME = "currency_name";
	public static final String KEY_C_COUNTRY = "currency_country";
	public static final String KEY_C_SYMBOL = "currency_symbol";
	

	public MyDataBase(Context context) {
		super(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	/*public MyDataBase(Context context, SQLiteDatabase sqLiteDatabase) {
		super(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		// TODO Auto-generated constructor stub
		this.sqLiteDatabase = sqLiteDatabase;
	}*/

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_CURRENCY);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	private static final String CREATE_TABLE_REMINDER = "create table "
			+ TABLE_REMINDER + " (" + KEY_ID
			+ " integer primary key autoincrement, " + KEY_DESC
			+ " text not null, " + KEY_DATE + " text not null, " + KEY_TIME + " text not null);";
	
	private static final String CREATE_TABLE_CURRENCY = "create table "
			+ TABLE_CURRENCY + " (" + KEY_C_ID
			+ " integer primary key autoincrement, " + KEY_C_NAME
			+ " text not null, " + KEY_C_COUNTRY + " text not null, "
			+ KEY_C_SYMBOL + " text not null);";

	
	
	public void openPermission(SQLiteDatabase sqLiteDatabase){
		this.sqLiteDatabase = sqLiteDatabase;
	}
	public void closePermission(){
		this.sqLiteDatabase.close();
	}
	
	public void addReminder(ReminderSet reminder){
		
		sqLiteDatabase = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(KEY_DESC, reminder.getDesc());
		cv.put(KEY_DATE, reminder.getDate());
		cv.put(KEY_TIME, reminder.getTime());
		
		sqLiteDatabase.insert(TABLE_REMINDER, null, cv);
		sqLiteDatabase.close();
	}
	
	
	public void addCurrency(CurrencySymbol cur){
		
//		sqLiteDatabase = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(KEY_C_NAME, cur.getCrncyName());
		cv.put(KEY_C_COUNTRY, cur.getCrncyCountry());
		cv.put(KEY_C_SYMBOL, cur.getCrncySymbol());
		
		sqLiteDatabase.insert(TABLE_CURRENCY, null, cv);
//		sqLiteDatabase.close();
	}
	
	
	public String getSymbol(String curencyName){
		sqLiteDatabase = this.getReadableDatabase();
		String[] column = new String[] { KEY_C_NAME, KEY_C_SYMBOL, KEY_C_COUNTRY };
		Cursor cursor = sqLiteDatabase.query(TABLE_CURRENCY, column, KEY_C_NAME
				+ " = '" + curencyName + "'", null, null, null, null);
		
		int keyitem = cursor.getColumnIndex(KEY_C_SYMBOL);	
		String curencySymbol = "";
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			curencySymbol = cursor.getString(keyitem);
		}
		
		cursor.close();
		sqLiteDatabase.close();
		
		return curencySymbol;
	}
	
	
public ArrayList<CurrencySymbol> getAllCurrency(String curencyName){
		
		sqLiteDatabase = this.getReadableDatabase();
		
		String[] column = new String[] { KEY_C_NAME, KEY_C_SYMBOL, KEY_C_COUNTRY};
		
		
		Cursor cursor = sqLiteDatabase.query(TABLE_CURRENCY, column, null, null, null, null, null);
		
		
		
		ArrayList<CurrencySymbol> allCurncy = new ArrayList<CurrencySymbol>();
		
		int name = cursor.getColumnIndex(KEY_C_NAME);
		int symbol = cursor.getColumnIndex(KEY_C_SYMBOL);
		int country = cursor.getColumnIndex(KEY_C_COUNTRY);
		
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			
			CurrencySymbol cur = new CurrencySymbol();
			cur.setCrncyName(cursor.getString(name));
			cur.setCrncyCountry(cursor.getString(country));
			cur.setCrncySymbol(cursor.getString(symbol));
			if (cursor.getString(name).contentEquals(curencyName)) {
				cur.setSelected(true);
			}else {
				cur.setSelected(false);
			}
			
			
			allCurncy.add(cur);
		}
		cursor.close();
		sqLiteDatabase.close();

		
		return allCurncy;
		
	}
	
	
	
	
	public ArrayList<ReminderSet> getReminders (String date , String time){
		
		sqLiteDatabase = this.getReadableDatabase();
		
		String[] column = new String[] { KEY_DESC, KEY_DATE, KEY_TIME };
		
		
		Cursor cursor = sqLiteDatabase.query(TABLE_REMINDER, column, KEY_DATE
				+ " = '" + date + "' AND " + KEY_TIME + " = '" + time + "'", null, null, null, null);
		
		
		
		ArrayList<ReminderSet> allReminders = new ArrayList<ReminderSet>();
		
		int desc = cursor.getColumnIndex(KEY_DESC);
		int dte = cursor.getColumnIndex(KEY_DATE);
		int tme = cursor.getColumnIndex(KEY_TIME);
		
		for (cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()) {
			
			ReminderSet rem = new ReminderSet();
			rem.setDesc(cursor.getString(desc));
			rem.setDate(cursor.getString(dte));
			rem.setTime(cursor.getString(tme));
			
			allReminders.add(rem);
		}
		cursor.close();
		sqLiteDatabase.close();

		
		return allReminders;
		
	}
	
	public void clearDb(){
		sqLiteDatabase = this.getWritableDatabase();
		sqLiteDatabase.execSQL("delete from "+ TABLE_REMINDER);
		sqLiteDatabase.close();
	}
	
}
