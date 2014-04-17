package com.example.multifunctionaldrawer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private CanvasView canvas;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		canvas = (CanvasView) findViewById(R.id.canvas);

		Button undoBtn = (Button) findViewById(R.id.button_undo);
		Button redoBtn = (Button) findViewById(R.id.button_redo);
		Button clearBtn = (Button) findViewById(R.id.button_clear);
		Button drawBtn = (Button) findViewById(R.id.button_draw);

		undoBtn.setOnClickListener(onClickMenu);
		redoBtn.setOnClickListener(onClickMenu);
		clearBtn.setOnClickListener(onClickMenu);
		drawBtn.setOnClickListener(onClickMenu);

	}

	private OnClickListener onClickMenu = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.button_undo:
				canvas.setDrawMode(CanvasView.MODE_UNDO);
				break;
			case R.id.button_redo:
				canvas.setDrawMode(CanvasView.MODE_REDO);
				break;
			case R.id.button_clear:
				canvas.setDrawMode(CanvasView.MODE_CLEAR);
				break;
			case R.id.button_draw:
				canvas.setDrawMode(CanvasView.MODE_DRAW);
				break;
			}
		}
	};



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
