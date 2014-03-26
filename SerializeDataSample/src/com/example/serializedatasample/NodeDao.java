package com.example.serializedatasample;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NodeDao {

	private static final String TABLE_NAME = DatabaseHelper.TABLE_NAME;
	private static final String ID = DatabaseHelper.CLM_ID;
	private static final String  COORDINATE_X = DatabaseHelper.CLM_X;
	private static final String  COORDINATE_Y = DatabaseHelper.CLM_Y;

	private SQLiteDatabase db;

	private static final String[] COLUMNS = {ID, COORDINATE_X, COORDINATE_Y};

	public NodeDao (SQLiteDatabase db) {
		this.db = db;
	}

	public long insert(Node node) {
		ContentValues values = new ContentValues();
		values.put(COORDINATE_X, node.getX());
		values.put(COORDINATE_Y, node.getY());
		Log.d("TEST", "COORDINATE X = " + String.valueOf(node.getX()));
		Log.d("TEST", "COORDINATE X = " + String.valueOf(node.getY()));
		return db.insert(TABLE_NAME, null, values);
	}

	public ArrayList<Node> findAll() {
		ArrayList<Node> nodeList = new ArrayList<Node>();
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
		while (cursor.moveToNext() != false) {
			Node node = new Node();

//			db作成時に使ったcolomnsのデータを使いまわす　
			node.setId(cursor.getInt(cursor.getColumnIndex(ID)));
			node.setX(cursor.getInt(cursor.getColumnIndex(COORDINATE_X)));
			node.setY(cursor.getInt(cursor.getColumnIndex(COORDINATE_Y)));
			nodeList.add(node);
		}
		return nodeList;
	}







}
