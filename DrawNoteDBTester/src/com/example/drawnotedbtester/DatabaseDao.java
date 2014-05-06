package com.example.drawnotedbtester;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
public class DatabaseDao {

	private static final String[] COLUMNS_NOTES = {
		C.DB.CLM_NOTES_ID,
		C.DB.CLM_NOTES_NAME,
		C.DB.CLM_NOTES_CREATE_DATE,
		C.DB.CLM_NOTES_UPDATE_DATE,
		C.DB.CLM_NOTES_PAGE_COUNT,
		C.DB.CLM_NOTES_THUMBNAIL
		};

	private static final String[] COLUMNS_PAGES = {
		C.DB.CLM_PAGES_ID,
		C.DB.CLM_PAGES_NAME,
		C.DB.CLM_PAGES_CREATE_DATE,
		C.DB.CLM_PAGES_UPDATE_DATE,
		C.DB.CLM_PAGES_INDEX,
		C.DB.CLM_PAGES_IMAGE
	};

	private SQLiteDatabase db;

	public DatabaseDao (SQLiteDatabase db) {
		this.db = db;
	}

	/*--------------------<<<ページ操作> >> -------------------*/


	public void insertNewNote(String name) {
		db.execSQL("PRAGMA foreign_keys = true;");//外部キーを有効にする（毎回呼ぶこと）
		ContentValues values = new ContentValues();
		values.put(C.DB.CLM_NOTES_NAME, name);

		db.insert(C.DB.TABLE_NAME_NOTES, null, values);
	}

	public void updateNote(int id, String name) {
		db.execSQL("PRAGMA foreign_keys = true;");//外部キーを有効にする（毎回呼ぶこと）
		String where = C.DB.CLM_NOTES_ID + " = " + String.valueOf(id);
		ContentValues values = new ContentValues();
		values.put(C.DB.CLM_NOTES_NAME, name);
		db.update(C.DB.TABLE_NAME_NOTES, values, where, null);
	}







}
