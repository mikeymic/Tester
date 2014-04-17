package com.example.lineviewtester;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DropView extends ViewGroup {

	private LineView line;
	private int x;
	private int y;






	public DropView(Context context) {
		super(context);
		setBackgroundColor(Color.GREEN);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		View child = getChildAt(getChildCount()-1);
		if (getChildCount() > 0) {
			int h = child.getMeasuredHeight();
			int w = child.getMeasuredWidth();
			int hh = h /2;
			int hw = w/2;
			child.layout(sx - hw, sy - hh, sx + hw,  sy + hh);
//			child.layout(50, 50, 150,  150);
		}


	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

	}





	int sx;
	int sy;

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		x = (int)event.getX();
		y = (int)event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			sx = x;
			sy = y;
			line = new LineView(getContext().getApplicationContext());
			addView(line);
			line.invalidate();
			line.addPoint(new Point(50, 50));
			getChildAt(getChildCount()-1).invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			int cx = Math.abs(sx -x);
			int cy = Math.abs(sy -y);
			if (cx < 1 || cy < 1) {
				return true;
			}
			line.addPoint(new Point(cx+50, cy+50));
			line.invalidate();
			getChildAt(getChildCount()-1).invalidate();
			break;
		case MotionEvent.ACTION_UP:

			break;
		}
		return true;
	}






}
