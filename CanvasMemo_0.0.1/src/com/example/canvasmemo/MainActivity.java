package com.example.canvasmemo;

import android.app.Activity;
import android.content.Intent;
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.action_undo:
			
			break;

		case R.id.action_start_overlay:
			OverlayManager manager = new OverlayManager(this);
			
			manager.takeCaptureThenAddToOverlay(view);
			byte[] stream = manager.createOverlayBuffer();
			
			Intent intent = new Intent(this, LayerService.class);
			intent.putExtra("OverlayView", stream);
			startService(intent);
			
			break;
		
		case R.id.action_end_overlay:
			stopService(new Intent(MainActivity.this, LayerService.class));
			break;

		default:
			break;
		}
		
		
		return super.onOptionsItemSelected(item);
	}

}
