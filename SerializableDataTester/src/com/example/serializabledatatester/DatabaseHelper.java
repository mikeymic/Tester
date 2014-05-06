package com.example.serializabledatatester;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;

	private static final String CREATE_DATABASE_TABLE_PAGES =
			"CREATE TABLE " + C.DB.TABLE_NAME_PAGES +" ( "
					+ C.DB.CLM_PAGES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ C.DB.CLM_PAGES_NAME + " TEXT, "
					+ C.DB.CLM_PAGES_CREATE_DATE + " INTEGER, "
					+ C.DB.CLM_PAGES_UPDATE_DATE + " INTEGER, "
					+ C.DB.CLM_PAGES_INDEX + " INTEGER, "
					+ C.DB.CLM_PAGES_IMAGE + " BLOB, "
					+ C.DB.CLM_PAGES_LINE + " BLOB "
					+	");";

	private static final String DROP_TABLE_PAGES =
			"DROP TABLE IF EXIST" + C.DB.TABLE_NAME_PAGES + ";";

	public DatabaseHelper(Context context) {
		super(context, C.DB.DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DATABASE_TABLE_PAGES);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE_PAGES);
	}

}
