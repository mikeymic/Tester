package com.example.customedittextsample004;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

/** ！！注意
 * backgroundに指定したdrawableの大きさが、Viewの最小の大きさになる
 *
 *
 * */

public class EditTextUnit extends FrameLayout {

	private int lineHeight;

	/**
	 * @return lineHeight
	 */
	public int getLineHeight() {
		return lineHeight;
	}

	public class ClearButtom extends ImageView {

		public ClearButtom(Context context) {
			super(context);
			createClearButton();
		}
		public ClearButtom(Context context, AttributeSet attrs) {
			super(context, attrs);
			createClearButton();
		}
		public ClearButtom(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			createClearButton();
		}
		private void createClearButton() {
			setBackgroundResource(android.R.drawable.ic_delete);
		}

	}

	public class CustomEditText extends EditText {

		public CustomEditText(Context context) {
			super(context);
			createEditText();
		}
		public CustomEditText(Context context, AttributeSet attrs) {
			super(context, attrs);
			createEditText();
		}
		public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			createEditText();
		}
		private void createEditText() {
			setTextSize(26.0f);
			setEms(5);
			setBackgroundResource(R.drawable.text_border);
//			setBackgroundColor(Color.GREEN);
		}
		/* (非 Javadoc)
		 * @see android.widget.TextView#onTextChanged(java.lang.CharSequence, int, int, int)
		 */
		@Override
		protected void onTextChanged(CharSequence text, int start,
				int lengthBefore, int lengthAfter) {
			super.onTextChanged(text, start, lengthBefore, lengthAfter);
			lineHeight = getLineHeight();
		}



	}

	private CustomEditText editText;
	private ClearButtom clearButtom;

	private int editHeight;
	private int editWidth;
	private int editHalfHeight;
	private int editHalfWidth;
	private int editLeft;
	private int editTop;
	private int editRight;
	private int editBottom;

	private int buttonHeight;
	private int buttonWidth;
	private int buttonHalfHeight;
	private int buttonHalfWidth;
	private int buttonLeft;
	private int buttonTop;
	private int buttonRight;
	private int buttonBottom;

	private int widthSpec;
	private int heightSpec;
	private int measuredWidth;
	private int measuredHeight;

 	public EditTextUnit(Context context) {
		super(context);
		setChildViews(context);
	}
	public EditTextUnit(Context context, AttributeSet attrs) {
		super(context, attrs);
		setChildViews(context);
	}
	public EditTextUnit(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setChildViews(context);
	}
	private void setChildViews(Context context) {
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

//		params.gravity = Gravity.RIGHT|Gravity.TOP;
		clearButtom = new ClearButtom(context);
		clearButtom.setLayoutParams(params);

//		params.gravity = Gravity.LEFT|Gravity.BOTTOM;
		editText = new CustomEditText(context);
		editText.setLayoutParams(params);

		addView(clearButtom, 0);
		addView(editText, 1);

		setBackgroundColor(Color.YELLOW);
	}

	private int i = 0;
	private int j =0;

	/* (非 Javadoc)
	 * @see android.widget.FrameLayout#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);



//		if (child instanceof ClearButtom) {

		int bl = editWidth - buttonHalfWidth;
		int bt = clearButtom.getTop();
		int br = editWidth + buttonHalfWidth;
		int bb = buttonHeight;

		j++;
		Log.d("TEST", "ClearButtom: [" + j +"] HEIGHT = " + String.valueOf(buttonHeight));
		Log.d("TEST", "ClearButtom: [" + j +"] WIDTH = " + String.valueOf(buttonWidth));
		Log.d("TEST", "ClearButtom: [" + j +"] LEFT = " + String.valueOf(bl));
		Log.d("TEST", "ClearButtom: [" + j +"] TOP = " + String.valueOf(bt));
		Log.d("TEST", "ClearButtom: [" + j +"] RIGHT = " + String.valueOf(br));
		Log.d("TEST", "ClearButtom: [" + j +"] BOTTOM = " + String.valueOf(bb));


		clearButtom.layout(bl, bt, br, bb);


//		}


//		View child = getChildAt(getChildCount()-1);
//
//		if (child instanceof CustomEditText) {

		int el = editText.getLeft();
		int et = buttonHalfHeight;
		int er = editText.getRight();
		int eb = editText.getBottom() + buttonHalfHeight;

		i ++;
		Log.d("TEST", "EditText: [" + i +"] HEIGHT = " + String.valueOf(editHeight));
		Log.d("TEST", "EditText: [" + i +"] WIDTH = " + String.valueOf(editWidth));
		Log.d("TEST", "EditText: [" + i +"] LEFT = " + String.valueOf(el));
		Log.d("TEST", "EditText: [" + i +"] TOP = " + String.valueOf(et));
		Log.d("TEST", "EditText: [" + i +"] RIGHT = " + String.valueOf(er));
		Log.d("TEST", "EditText: [" + i +"]  BOTTOM = " + String.valueOf(eb));

		editText.layout(el, et, er ,eb);


//	}


		clearButtom.bringToFront();


	}

	/* (非 Javadoc)
	 * @see android.widget.FrameLayout#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);//最後に呼び出すべきらしいが今回は大して使わないのでここで良い

		editHeight = editText.getMeasuredHeight();
		editWidth = editText.getMeasuredWidth();
		Log.d("TEST", "EditText: [0] HEIGHT = " + String.valueOf(editHeight));
		Log.d("TEST", "EditText: [0] WIDTH = " + String.valueOf(editWidth));

		buttonHeight = clearButtom.getMeasuredHeight();
		buttonWidth = clearButtom.getMeasuredWidth();
		Log.d("TEST", "ClearButtom: [0] HEIGHT = " + String.valueOf(buttonHeight));
		Log.d("TEST", "ClearButtom: [0] WIDTH = " + String.valueOf(buttonWidth));

		buttonHalfHeight = buttonHeight / 2;
		buttonHalfWidth = buttonWidth / 2;

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);


		measuredHeight = editHeight + buttonHalfHeight;
		measuredWidth = editWidth + buttonHalfWidth;

		Log.d("TEST", "measured: [0] HEIGHT = " + String.valueOf(measuredHeight));
		Log.d("TEST", "measured: [0] WIDTH = " + String.valueOf(measuredWidth));


		setMeasuredDimension(measuredWidth, measuredHeight);

	}




}
