package com.example.serializedatasample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "Node.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "Node";

	//コンテントプロバイダ　には　_idが必要
	public static final String CLM_ID = "_id";
	public static final String  CLM_X = "x";
	public static final String  CLM_Y = "y";

//	空白を忘れないこと
	private static final String CREATE_DATABASE_TABLE =
			"CREATE TABLE " + TABLE_NAME +" ("
	+ CLM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
	+ CLM_X + " REAL NOT NULL, "
	+ CLM_Y + " REAL NOT NULL"
	+	");";

	private static final String DROP_TABLE =
			"DROP TABLE IF EXIST" + TABLE_NAME + ";";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_DATABASE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE);
	}

}
