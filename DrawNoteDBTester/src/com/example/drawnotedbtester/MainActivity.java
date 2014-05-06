package com.example.drawnotedbtester;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText textNoteId;
	private EditText textNoteName;
	private EditText textNoteUpdateDate;
	private EditText textNoteCreateDate;
	private EditText textPageId;
	private EditText textPageName;
	private EditText textPageCreateDate;
	private EditText textPageUpdateDate;


	private String[] from = {
			C.DB.CLM_NOTES_ID,
			C.DB.CLM_NOTES_NAME,
			C.DB.CLM_NOTES_CREATE_DATE,
			C.DB.CLM_NOTES_UPDATE_DATE,
			C.DB.CLM_NOTES_PAGE_COUNT,
	};

	private int[] to = {
			R.id.row_note_id,
			R.id.row_note_name,
			R.id.row_note_create_date,
			R.id.row_note_update_date,
			R.id.row_note_page_count
	};

	private String[] from2 = {
			C.DB.CLM_PAGES_ID,
			C.DB.CLM_PAGES_NAME,
			C.DB.CLM_PAGES_CREATE_DATE,
			C.DB.CLM_PAGES_UPDATE_DATE,
			C.DB.CLM_PAGES_INDEX,
	};

	private int[] to2 = {
			R.id.row_note_id,
			R.id.row_note_name,
			R.id.row_note_create_date,
			R.id.row_note_update_date,
			R.id.row_note_page_count,
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textNoteId = (EditText) findViewById(R.id.text_note_id);
		textNoteName = (EditText) findViewById(R.id.text_note_name);
		textNoteCreateDate = (EditText) findViewById(R.id.text_note_create_date);
		textNoteUpdateDate = (EditText) findViewById(R.id.text_note_update_date);

		textPageId = (EditText) findViewById(R.id.text_page_id);
		textPageName = (EditText) findViewById(R.id.text_page_name);
		textPageCreateDate = (EditText) findViewById(R.id.text_page_create_date);
		textPageUpdateDate = (EditText) findViewById(R.id.text_page_update_date);


		Button noteEnter = (Button) findViewById(R.id.button_note_enter);
		Button pageEnter = (Button) findViewById(R.id.button_page_enter);
		noteEnter.setOnClickListener(onClickNoteEnter);
		pageEnter.setOnClickListener(onClickPageEnter);


		ListView noteListView = (ListView) findViewById(R.id.note_list_view);
		ListView pageListView = (ListView) findViewById(R.id.page_list_view);

		DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
		Cursor c = helper.getReadableDatabase().query(C.DB.TABLE_NAME_NOTES, from, null, null, null, null, null);
		cursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.row_note_list, c, from, to, 0);
		noteListView.setAdapter(cursorAdapter);

		Cursor c2 = helper.getReadableDatabase().query(C.DB.TABLE_NAME_PAGES, from2, null, null, null, null, null);
		cursorAdapter2 = new SimpleCursorAdapter(getApplicationContext(), R.layout.row_note_list, c2, from2, to2, 0);
		pageListView.setAdapter(cursorAdapter2);

	}

	private OnClickListener onClickNoteEnter = new OnClickListener() {

		@Override
		public void onClick(View v) {
			DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
			DatabaseDao dao = new DatabaseDao(helper.getWritableDatabase());

			int id = Integer.parseInt(textNoteId.getText().toString());
			String name = textNoteName.getText().toString();
			if (id == 0 ) {
				Toast.makeText(MainActivity.this, "id = " + String.valueOf(id) + " : " + "name = " + name, Toast.LENGTH_SHORT).show();
				dao.insertNewNote(name);
			} else {
				dao.updateNote(id, name);
			}

			cursorAdapter.notifyDataSetChanged();
			cursorAdapter.notifyDataSetInvalidated();
			cursorAdapter2.notifyDataSetChanged();
			cursorAdapter2.notifyDataSetInvalidated();


			helper.close();

		}
	};
	private OnClickListener onClickPageEnter = new OnClickListener() {

		@Override
		public void onClick(View v) {

		}
	};
	private SimpleCursorAdapter cursorAdapter;
	private SimpleCursorAdapter cursorAdapter2;

}
