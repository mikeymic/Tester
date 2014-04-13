package com.example.customedittextsample004;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TextDropView layout = new TextDropView(this);
		setContentView(layout);


		EditTextUnit editTextUnit = new EditTextUnit(this);

		editTextUnit = new EditTextUnit(this);
		layout.addView(editTextUnit);




	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
