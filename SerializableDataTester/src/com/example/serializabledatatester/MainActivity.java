package com.example.serializabledatatester;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	String noteName = "testnote";
	int currentPageIndex = 1;

	private DrawingView drawingView;
	private ArrayList<Line> lines;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lines = new ArrayList<Line>();
		drawingView = (DrawingView) findViewById(R.id.drawingviwe);

		readPage(noteName, currentPageIndex);
		if (lines.size() == 0) {
			insertPage(null, noteName, currentPageIndex);
		}

		drawingView.setLines(lines);
		drawingView.invalidate();
	}

	/* (非 Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		byte[] stream;
		stream = getSerializeData();
		updatePage(stream, noteName, currentPageIndex);

	}

	/* (非 Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		byte[] stream;
		stream = getSerializeData();
		updatePage(stream, noteName, currentPageIndex);
	}

	private byte[] getSerializeData() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(stream);
			out.writeObject(lines);
			out.close();
		} catch (IOException e) {
		} catch (NullPointerException e) {
		}
		return stream.toByteArray();
	}

	private void insertPage(byte[] stream, String name, int index) {
		DatabaseHelper helper = new DatabaseHelper(this);
		DatabaseDao dao = new DatabaseDao(helper.getWritableDatabase());
		dao.insertPage(stream, name, index);
		helper.close();
	}

	private void updatePage(byte[] stream, String name, int index) {
		DatabaseHelper helper = new DatabaseHelper(this);
		DatabaseDao dao = new DatabaseDao(helper.getWritableDatabase());
		dao.updatePage(stream, name, index);
		helper.close();
	}

	private void readPage(String name, int index) {
		DatabaseHelper helper = new DatabaseHelper(this);
		DatabaseDao dao = new DatabaseDao(helper.getReadableDatabase());
		byte[] data = dao.getPage(name, index);
		helper.close();

		if (data != null) {
			try {
				ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
				lines = (ArrayList<Line>) in.readObject();
				in.close();
			} catch (ClassNotFoundException e) {
			} catch (StreamCorruptedException e) {
			} catch (IOException e) {
			}
		}
	}

}
