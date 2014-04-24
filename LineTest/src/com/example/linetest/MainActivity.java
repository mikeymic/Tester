package com.example.linetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	CanvasView view;
	
	

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		view = (CanvasView) findViewById(R.id.canvas);
		
		Button add = (Button) findViewById(R.id.button_add_path);
		Button divide = (Button) findViewById(R.id.button_divide);
		Button crop = (Button) findViewById(R.id.button_crop);
		Button zoom = (Button) findViewById(R.id.button_zoom);
		
		add.setOnClickListener(onClickButton);
		divide.setOnClickListener(onClickButton);
		crop.setOnClickListener(onClickButton);
		zoom.setOnClickListener(onClickButton);

	}
	
	OnClickListener onClickButton = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.button_add_path:
				view.mode = CanvasView.MODE_ADD;
				break;
			case R.id.button_divide:
				view.mode = CanvasView.MODE_DIVIDE;
				break;
			case R.id.button_crop:
				view.mode = CanvasView.MODE_CROP;
				break;
			case R.id.button_zoom:
				view.mode = CanvasView.MODE_ZOOM;
				break;
			}
			view.invalidate();
		}
	};


}
