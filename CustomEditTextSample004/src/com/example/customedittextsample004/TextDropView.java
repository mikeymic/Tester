package com.example.customedittextsample004;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class TextDropView extends ViewGroup {

	private Context context;
	private int x;
	private int y;

	public TextDropView(Context context) {
		super(context);
		this.context = context;
	}
	public TextDropView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	public TextDropView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	int i = 0;
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {


		EditTextUnit child = (EditTextUnit) getChildAt(getChildCount()-1);


		int childHeight = child.getMeasuredHeight();
		int childWidth = child.getMeasuredWidth();

		int childHalfHeight = childHeight / child.getLineHeight();
		int childHalfWidth = childWidth / 2;

		int left = x - childHalfWidth;
		int top = y - childHalfHeight;
//		int top = y;
		int right = x + childHalfWidth;
		int bottom = y + childHeight;

		i++;
		Log.d("TEST", "EditTextUnit: [" + i +"] HEIGHT = " + String.valueOf(child.getHeight()));
		Log.d("TEST", "EditTextUnit: [" + i +"] WIDTH = " + String.valueOf(child.getWidth()));
		Log.d("TEST", "EditTextUnit: [" + i +"] LEFT = " + String.valueOf(left));
		Log.d("TEST", "EditTextUnit: [" + i +"] TOP = " + String.valueOf(top));
		Log.d("TEST", "EditTextUnit: [" + i +"] RIGHT = " + String.valueOf(right));
		Log.d("TEST", "EditTextUnit: [" + i +"]  BOTTOM = " + String.valueOf(bottom));

		child.layout(left, top, right, bottom);


	}

	/* (非 Javadoc)
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		View child = getChildAt(getChildCount()-1);
		child.measure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	/* (非 Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		x = (int) event.getX();
		y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			addView(new EditTextUnit(getContext()));
			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_UP:

			break;
		}
		return true;
	}



}
