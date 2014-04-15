package com.example.lineviewtester;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Point;
import android.view.View;

public class LineView extends View {

	private ArrayList<Point> lines;
	
	private int margin = 50;
	
	private Paint paint;
	
	public LineView(Context context) {
		super(context);
		lines = new ArrayList<Point>();
		setBackgroundColor(Color.BLUE);
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
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (lines.size() > 1) {
			for (int i = 0; i < lines.size() - 1; i++) {
				canvas.drawPoint(lines.get(i).x, lines.get(i).y, paint);
				canvas.drawPoint(lines.get(i+1).x, lines.get(i+1).y, paint);
			}
		} else {
			canvas.drawPoint(lines.get(0).x, lines.get(0).y, paint);
		}
		
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		int minX = 50;
		int minY = 50;
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
		
		int measuredWidth = margin*2 + width;
		int measuredHeight = margin*2 + height;
		
		setMeasuredDimension(measuredWidth, measuredHeight);
	}
	
	

}
