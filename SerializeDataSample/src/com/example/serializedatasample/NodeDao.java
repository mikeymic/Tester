package com.example.serializedatasample;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class NodeDao {
	

	private static final String TABLE_NAME = DatabaseHelper.TABLE_NAME;
	private static final String ID = DatabaseHelper.CLM_ID;
//	private static final String  COORDINATE_X = DatabaseHelper.CLM_X;
//	private static final String  COORDINATE_Y = DatabaseHelper.CLM_Y;
	private static final String NODE = DatabaseHelper.CLM_NODE;

	private SQLiteDatabase db;

//	private static final String[] COLUMNS = {ID, COORDINATE_X, COORDINATE_Y};
	private static final String[] COLUMNS = {ID, NODE};
	
	FileManager fiManager = new FileManager(); 
	
	public NodeDao (SQLiteDatabase db) {
		this.db = db;
	}

	public long insert(byte[] stream) {
		
		ContentValues values = new ContentValues();
		
//		values.put(COORDINATE_X, node.getX());
//		values.put(COORDINATE_Y, node.getY());
//		Log.d("TEST", "COORDINATE X = " + String.valueOf(node.getX()));
//		Log.d("TEST", "COORDINATE X = " + String.valueOf(node.getY()));
		
		values.put(NODE, stream);
		return db.insert(TABLE_NAME, null, values);
	}

	public ArrayList<Node> findAll() {
		ArrayList<Node> nodeList = new ArrayList<Node>();
		Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null);
		while (cursor.moveToNext() != false) {
			nodeList.add( (cursor.getInt(cursor.getColumnIndex(ID))-1), fiManager.loadNode(cursor.getBlob(cursor.getColumnIndex(NODE))));

		}
		return nodeList;
	}







}
