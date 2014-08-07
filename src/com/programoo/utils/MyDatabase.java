package com.programoo.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.programoo.models.Word;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDatabase extends SQLiteAssetHelper {

	private static final String DATABASE_NAME = "lexitron.db";
	private static final int DATABASE_VERSION = 1;
	private static final String TABLES_ENG2THAI = "eng2thai";

	public MyDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public List<Word> getAllBooks() {
		List<Word> books = new LinkedList<Word>();

		// 1. build the query
		// String query = "SELECT  * FROM " + TABLES_ENG2THAI;
		String query = "SELECT * FROM eng2thai where eentry like '%bazoo%' or tentry like '%okadf%';";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		Word book = null;
		if (cursor.moveToFirst()) {
			do {
				book = new Word();
				book.setId(Integer.parseInt(cursor.getString(0)));
				book.setEsearch(cursor.getString(1));
				book.setEentry(cursor.getString(2));
				book.setTentry(cursor.getString(3));
				book.setEcat(cursor.getString(4));
				book.setEthai(cursor.getString(5));
				book.setEsyn(cursor.getString(6));
				book.setEant(cursor.getString(7));
				// Add book to books
				books.add(book);
			} while (cursor.moveToNext());
		}

		Log.d("getAllBooks()", books.toString());
		return books;
	}

	public List<Word> searchByNearByKeyword(String keyword) {
		List<Word> books = new LinkedList<Word>();

		// 1. build the query
		// String query = "SELECT  * FROM " + TABLES_ENG2THAI;
		String query = "SELECT * FROM eng2thai where eentry like '%" + keyword
				+ "%' or tentry like '%" + keyword + "%';";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		Word book = null;
		if (cursor.moveToFirst()) {
			do {
				book = new Word();
				book.setId(Integer.parseInt(cursor.getString(0)));
				book.setEsearch(cursor.getString(1));
				book.setEentry(cursor.getString(2));
				book.setTentry(cursor.getString(3));
				book.setEcat(cursor.getString(4));
				book.setEthai(cursor.getString(5));
				book.setEsyn(cursor.getString(6));
				book.setEant(cursor.getString(7));
				// Add book to books
				books.add(book);
			} while (cursor.moveToNext());
		}

		Log.d("getAllBooks()", books.toString());
		return books;
	}

	public ArrayList<String> getEentry() {
		ArrayList<String> eentry = new ArrayList<String>();

		// 1. build the query
		// String query = "SELECT  * FROM " + TABLES_ENG2THAI;
		String query = "SELECT DISTINCT eentry FROM eng2thai;";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		Word book = null;
		if (cursor.moveToFirst()) {
			do {
				eentry.add(cursor.getString(0));
			} while (cursor.moveToNext());
		}

		// Log.d("getAllBooks()", books.toString());
		return eentry;
	}
	
	public ArrayList<String> searchNearByStartWith(String keyword,int maxHintLength) {
		ArrayList<String> eentry = new ArrayList<String>();

		// 1. build the query
		// String query = "SELECT  * FROM " + TABLES_ENG2THAI;
		String query = "SELECT DISTINCT eentry,tentry FROM eng2thai where eentry like '"+keyword+"%' or tentry like '"+keyword+"%';";

		// 2. get reference to writable DB
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		// 3. go over each row, build book and add it to list
		int counter=0;
		if (cursor.moveToFirst()) {
			do {
				eentry.add(cursor.getString(0)+" "+cursor.getString(1));
				counter++;
				
				if(counter > maxHintLength)
				{
					break;
				}
				
			} while (cursor.moveToNext());
		}

		// Log.d("getAllBooks()", books.toString());
		return eentry;
	}

	public List<Word> searchByExactKeyword(String keyword) {
		List<Word> books = new LinkedList<Word>();

		// 1. build the query
		// String query = "SELECT  * FROM " + TABLES_ENG2THAI;
		String query = "SELECT * FROM eng2thai where eentry='" + keyword
				+ "' COLLATE NOCASE;";

		// 2. get reference to writable DB
		Cursor cursor = null;
		try {
			SQLiteDatabase db = this.getWritableDatabase();
			cursor = db.rawQuery(query, null);

			// 3. go over each row, build book and add it to list
			Word book = null;
			if (cursor.moveToFirst()) {
				do {
					book = new Word();
					book.setId(Integer.parseInt(cursor.getString(0)));
					book.setEsearch(cursor.getString(1));
					book.setEentry(cursor.getString(2));
					book.setTentry(cursor.getString(3));
					book.setEcat(cursor.getString(4));
					book.setEthai(cursor.getString(5));
					book.setEsyn(cursor.getString(6));
					book.setEant(cursor.getString(7));
					// Add book to books
					books.add(book);
				} while (cursor.moveToNext());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return books;
	}
	
}
