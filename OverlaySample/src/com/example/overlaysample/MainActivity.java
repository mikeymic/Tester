package com.example.overlaysample;

import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private MyView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new MyView(this);
		setContentView(view);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_dialog, menu);
		return true;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_start_overlay:
			// Viewを画面上に重ね合わせする
			Intent intent = new Intent(MainActivity.this, LayerService.class);
			view.setDrawingCacheEnabled(true);
			Bitmap cache = view.getDrawingCache();
			if (cache == null) {
				break;
			}
			Bitmap bitmap = Bitmap.createBitmap(cache);
			view.setDrawingCacheEnabled(false);

		     ByteArrayOutputStream bout = new ByteArrayOutputStream();
		     bitmap.compress(Bitmap.CompressFormat.PNG, 90, bout);
		     byte[] mBitmapArray = bout.toByteArray();

			intent.putExtra("OverlayView",mBitmapArray);
			startService(intent);

			break;
		case R.id.action_end_overlay:
			// Viewを画面上に重ね合わせする

			stopService(new Intent(MainActivity.this, LayerService.class));
			break;
		}

		// return super.onOptionsItemSelected(item);
		return true;
	}

}
