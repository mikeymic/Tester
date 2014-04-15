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
			child.layout(x - hw, y - hh, x + hw,  y + hh);
		}
		
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		x = (int)event.getX();
		y = (int)event.getY();
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			line = new LineView(getContext());
			line.addPoint(new Point(x, y));
			addView(line);
			line.invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			line.addPoint(new Point(x, y));
			line.invalidate();
			break;
		case MotionEvent.ACTION_UP:
			
			break;
		}
		return true;
	}
	
	

	
	

}
