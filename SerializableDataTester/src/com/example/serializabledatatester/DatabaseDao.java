package com.example.serializabledatatester;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class DatabaseDao {

	private static final String[] COLUMNS_PAGES = {
		C.DB.CLM_PAGES_ID,
		C.DB.CLM_PAGES_NAME,
		C.DB.CLM_PAGES_CREATE_DATE,
		C.DB.CLM_PAGES_UPDATE_DATE,
		C.DB.CLM_PAGES_INDEX,
		C.DB.CLM_PAGES_IMAGE,
		C.DB.CLM_PAGES_LINE
	};

	private SQLiteDatabase db;

	public DatabaseDao (SQLiteDatabase db) {
		this.db = db;
	}

	/*--------------------<<<ノート操作> >> -------------------*/








	/*--------------------<<<ページ操作> >> -------------------*/

	//ページ数取得
	public int getPageCount(String name){
		String where = C.DB.CLM_PAGES_NAME + " = " + "'" + name + "'";
		Cursor cursor = db.query(C.DB.TABLE_NAME_PAGES, COLUMNS_PAGES, where, null, null, null, null);
		return cursor.getCount();
	}

	//新規ページ作成
	public long insertPage(byte[] stream, String name, int index) {

		ContentValues values = new ContentValues();
		values.put(C.DB.CLM_PAGES_IMAGE, stream);
		values.put(C.DB.CLM_PAGES_NAME, name);
		values.put(C.DB.CLM_PAGES_INDEX, index);
		return db.insert(C.DB.TABLE_NAME_PAGES, null, values);
	}

	//ページ保存
	public long updatePage(byte[] stream, String name, int index) {

		String where =
				C.DB.CLM_PAGES_NAME + " = " + "'" + name + "'" + " and " +
				C.DB.CLM_PAGES_INDEX + " = " + String.valueOf(index) + " ;";
		ContentValues values = new ContentValues();
		values.put(C.DB.CLM_PAGES_LINE, stream);
//		values.put(C.DB.CLM_PAGES_NAME, name);
//		values.put(C.DB.CLM_PAGES_INDEX, index);
		return db.update(C.DB.TABLE_NAME_PAGES, values, where, null);
	}

	//ページ読み込み
	public byte[] getPage(String name, int index){
		String where =
				C.DB.CLM_PAGES_NAME + " = " + "'" + name + "'" + " and " +
				C.DB.CLM_PAGES_INDEX + " = " + String.valueOf(index) + " ;";
		Cursor cursor = db.query(C.DB.TABLE_NAME_PAGES, COLUMNS_PAGES, where, null, null, null, null);

		byte[] stream = null;
		while (cursor.moveToNext() != false) {
			stream = cursor.getBlob(cursor.getColumnIndex(C.DB.CLM_PAGES_LINE));
		}
		return stream;
	}

	//削除
	public void deletePage(int index) {
		String where = C.DB.CLM_PAGES_ID + " = " + String.valueOf(index) ;
		db.delete(C.DB.TABLE_NAME_PAGES, where, null);
	}






}
