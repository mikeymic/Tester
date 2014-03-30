package com.example.overlaysample;

import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

	public class Line  implements Serializable{
		private int color;
		private int width;
		private ArrayList<Point> points;

		public Line(int color, int width) {
			points = new ArrayList<Point>();
			this.color = color;
			this.width = width;
		}

		public int getColor() {
			return color;
		}

		public int getWidth() {
			return width;
		}

		public void addPoint(Point p) {
			points.add(p);
		}

		public ArrayList<Point> getPoints() {
			return points;
		}
	}

	// 全ての線を管理するリスト
	private ArrayList<Line> lines = new ArrayList<Line>();
	// 一本の線
	private Line aLine;
	// 描画色
	private int currentColor = Color.RED;
	// 線の太さ
	private int currentWidth = 6;

	public MyView(Context context) {
		super(context);
	}

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void undo() {
		if (lines.size() > 0) {
			lines.remove(lines.size() - 1);
		}
		invalidate();
	}

	public void clear() {
		lines.clear();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawAll(canvas);
	}

	public void drawAll(Canvas canvas) {
		canvas.drawColor(Color.argb(0, 255, 255, 255));
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		for (Line line : lines) {
			paint.setColor(line.getColor());
			paint.setStrokeWidth(line.getWidth());
			for (int i = 0; i < (line.getPoints().size() - 1); i++) {
				Point s = line.getPoints().get(i);
				Point e = line.getPoints().get(i + 1);
				canvas.drawLine(s.x, s.y, e.x, e.y, paint);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// return super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			aLine = new Line(currentColor, currentWidth);
			lines.add(aLine);
			break;
		case MotionEvent.ACTION_MOVE:
			int x = (int) event.getX();
			int y = (int) event.getY();
			Point p = new Point(x, y);
			aLine.addPoint(p);
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		invalidate();
		return true;
	}

	public void setColor(int c) {
		currentColor = c;
	}

	public void setLineWidth(int width) {
		currentWidth = width;
	}

}
