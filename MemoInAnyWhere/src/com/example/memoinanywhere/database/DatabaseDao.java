package com.example.memoinanywhere.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.memoinanywhere.data.MemoPage;
import com.example.memoinanywhere.filemanager.FileManager;

public class DatabaseDao {


	private static final String TABLE_NAME = DatabaseHelper.TABLE_NAME;
	private static final String ID = DatabaseHelper.CLM_ID;
	public static final String NAME = DatabaseHelper.CLM_NAME;
	public static final String TEXTS = DatabaseHelper.CLM_TEXTS;
	public static final String IMAGES = DatabaseHelper.CLM_IMAGES;

	private SQLiteDatabase db;

	private static final String[] COLUMNS = {ID, NAME, TEXTS, IMAGES};

	private FileManager fiManager = new FileManager();

	public DatabaseDao (SQLiteDatabase db) {
		this.db = db;
	}


	public long insertTexts(byte[] textStream) {

		ContentValues values = new ContentValues();
		values.put(TEXTS, textStream);
		return db.insert(TABLE_NAME, null, values);
	}

	public long insertImages(byte[] imageStream) {

		ContentValues values = new ContentValues();
		values.put(IMAGES, imageStream);
		return db.insert(TABLE_NAME, null, values);
	}

	public long insertDataInPage(byte[] testStream, byte[] stream) {

		ContentValues values = new ContentValues();
		values.put(TEXTS, stream);
		values.put(IMAGES, stream);
		return db.insert(TABLE_NAME, null, values);
	}

	public long insertAllDataInPage(String pageName, byte[] testStream, byte[] stream) {

		ContentValues values = new ContentValues();
		values.put(NAME, pageName);
		values.put(TEXTS, stream);
		values.put(IMAGES, stream);
		return db.insert(TABLE_NAME, null, values);
	}

	public MemoPage getAllDataInPage() {
		MemoPage page = new MemoPage();
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
		while (cursor.moveToNext() != false) {
			page.setName(cursor.getString(cursor.getColumnIndex(NAME)));
			page.setTexts(fiManager.DeSerializeTexts(cursor.getBlob(cursor.getColumnIndex(TEXTS))));
			page.setImages( fiManager.DeSerializeImages(cursor.getBlob(cursor.getColumnIndex(IMAGES))));

		}
		return page;
	}







}
