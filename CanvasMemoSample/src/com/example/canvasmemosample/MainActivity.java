package com.example.canvasmemosample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private CanvasView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new CanvasView(this);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// return super.onOptionsItemSelected(item);
		switch (item.getItemId()) {

		case R.id.action_undo:
			view.undoLine();
			break;

		case R.id.action_start_overlay:
			// Viewを画面上に重ね合わせする
			Intent intent = new Intent(MainActivity.this, LayerService.class);

//			view.setDrawingCacheEnabled(true);
//			Bitmap cache = view.getDrawingCache();
//			if (cache == null) {
//				break;
//			}
//
//			Bitmap bitmap = Bitmap.createBitmap(cache);
//			view.setDrawingCacheEnabled(false);
//
//			int actionBarHeight = getActionBar().getHeight();
//			int overlayHeight = view.getHeight() + actionBarHeight;
//			int overlayWidth = view.getWidth();
//
//			Bitmap overlayBmp = Bitmap.createBitmap(overlayWidth, overlayHeight, Config.ARGB_8888);
//			Canvas overlayCanvas = new Canvas(overlayBmp);
//			overlayCanvas.drawBitmap(bitmap, 0,actionBarHeight, null);
//
//			ByteArrayOutputStream bout = new ByteArrayOutputStream();
//			overlayBmp.compress(Bitmap.CompressFormat.PNG, 90, bout);
//			byte[] mBitmapArray = bout.toByteArray();

			OverlayManager manager = new OverlayManager(this);
			Bitmap cache = manager.takeViewCapture(view);
			manager.addBitmapToOverlay(cache);
			byte[] mBitmapArray = manager.createOverlayBuffer();

			intent.putExtra("OverlayView", mBitmapArray);
			startService(intent);

			break;
		case R.id.action_stop_overlay:
			stopService(new Intent(MainActivity.this, LayerService.class));
			break;
		}

		return true;
	}

}
