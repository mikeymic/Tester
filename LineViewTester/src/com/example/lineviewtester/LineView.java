package com.example.lineviewtester;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.view.View;

public class LineView extends View {

	private ArrayList<Point> lines;

	private int margin = 50;

	private Paint paint;

	private int measuredWidth;

	private int measuredHeight;

	public LineView(Context context) {
		super(context);
		lines = new ArrayList<Point>();
		setBackgroundColor(Color.BLUE);
		setPaint();
		measure(0, 0);
	}

	public void addPoint(Point point) {
		lines.add(point);
	}

	private void setPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		paint.setStrokeWidth(6);
		paint.setColor(Color.RED);
		paint.setStyle(Style.STROKE);
	}

	private void createMeasuredDemention() {

		int minX = 0;
		int minY = 0;
		int maxX = 0;
		int maxY = 0;


		for (int i = 1; i < lines.size(); i++) {
			if (lines.get(i).x == lines.get(0).x) {

				for (int j = 0; j < lines.size(); j++) {
					if (lines.get(j) == lines.get(i)) {
						return;
					}
					lines.get(j).x += lines.get(i).x	;
				}

			}
		}

		for (int i = 1; i < lines.size(); i++) {
			if (lines.get(i).x > minX) {
				maxX = lines.get(i).x;
			}
			if (lines.get(i).y > minY) {
				maxY = lines.get(i).y;
			}
		}


		int width = maxX - minX;
		int height = maxY - minY;

		measuredWidth = margin*2 + width;
		measuredHeight = margin*2 + height;

		setMeasuredDimension(measuredWidth, measuredHeight);


	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		createMeasuredDemention();
		getParent().requestLayout();
		if (lines.size() > 1) {
			for (int i = 0; i < lines.size() - 1; i++) {
				canvas.drawLine(lines.get(i).x, lines.get(i).y, lines.get(i+1).x, lines.get(i+1).y, paint);
			}
		} else {
			canvas.drawPoint(lines.get(0).x, lines.get(0).y, paint);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		createMeasuredDemention();
	}



}
