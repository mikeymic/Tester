package com.example.memoinanywhere;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class HomeWindowActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_window);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_window, menu);
		return true;
	}

}
