package com.example.serializedatasample;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.databasesample.R;

public class NodeDataBaseActivity extends Activity {

	private TextView text;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FrameLayout frame = new FrameLayout(this);

		text = new TextView(this);
		text.setTextSize(22.0f);
		text.setTextColor(0xFFFF0000);
		displayDatabase();
		frame.addView(text);
		setContentView(frame);


	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			DatabaseHelper helper = new DatabaseHelper(NodeDataBaseActivity.this);
			// データベースを書き込みモードでopen
			SQLiteDatabase db = helper.getWritableDatabase();
			NodeDao dao = new NodeDao(db);

			Node node = new Node(0, event.getX(), event.getY());
			dao.insert(node);
			db.close();
			displayDatabase();

		}
		return true;
	}

	private void displayDatabase() {
		DatabaseHelper helper = new DatabaseHelper(this);
		SQLiteDatabase db = helper.getReadableDatabase();
		NodeDao dao = new NodeDao(db);

		ArrayList<Node> list = dao.findAll();
		db.close();

		StringBuffer buffer = new StringBuffer();
		for (Node node : list) {
			buffer.append(" id : ");
			buffer.append(node.getId());
			buffer.append(" | X : ");
			buffer.append(node.getX());
			buffer.append(" | Y : ");
			buffer.append(node.getY());
			buffer.append(System.getProperty("line.separator"));
		}
		text.setText(buffer.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule_data_base, menu);
		return true;
	}

}
