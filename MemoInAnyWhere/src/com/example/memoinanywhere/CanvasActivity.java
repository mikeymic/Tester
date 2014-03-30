package com.example.memoinanywhere;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.memoinanywhere.view.MemoView;

public class CanvasActivity extends Activity {

	LinearLayout linear;
	ArrayList<EditText> editArray;
	private MemoView memoView;

	/* (非 Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		linear = new LinearLayout(this);
		linear.setOrientation(LinearLayout.VERTICAL);
		setContentView(linear);
		editArray = new ArrayList<EditText>();


		memoView = new MemoView(this);
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
		param.weight = 1;
		linear.addView(memoView, param);

		Button btn = new Button(this);
		btn.setText("REMOVE VIEW");
		param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		linear.addView(btn, param);


		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				memoView.removeViewAt(0);
			}
		});



	}






}
