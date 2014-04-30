package com.example.zoomtester008;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class DrawLine {

	private Path line;
	private ArrayList<PointF> linePoints;
	private final Paint paint;

	public final Path sLine = new Path();

	public DrawLine() {
		line = new Path();
		linePoints = new ArrayList<PointF>();
		paint = new Paint();
	}

	public DrawLine(Path path, ArrayList<PointF> points, Paint paint) {
		line = new Path(path);
		linePoints = new ArrayList<PointF>(points);
		this.paint = new Paint(paint);
	}

	public DrawLine(DrawLine drawLine) {
		line = new Path(drawLine.getLine());
		linePoints = new ArrayList<PointF>(drawLine.getLinePoints());
		paint = new Paint(drawLine.paint);
	}

//	public void setStartLinePoint(PointF p) {
//		line.moveTo(p.x, p.y);
//		linePoints.add(p);
//	}
//
//	public void addLinePoint(PointF p) {
//		line.lineTo(p.x, p.y);
//		linePoints.add(p);
//	}
//
//	public void setLastLinePoint(PointF p) {
//		line.setLastPoint(p.x, p.y);
//		linePoints.add(p);
//	}

	public void clear() {
		line.rewind();
		linePoints.clear();
	}

	public void drawLine(Canvas canvas) {
		canvas.drawPath(line, paint);
	}

	/**
	 * ------------------------------
	 * getter&setter------------------------------
	 **/
	public Path getLine() {
		return line;
	}

	public ArrayList<PointF> getLinePoints() {
		return linePoints;
	}

	public Paint getPaint() {
		return paint;
	}

	public void setLine(Path line) {
		this.line = line;
	}

	public void setLinePoints(ArrayList<PointF> linePoints) {
		this.linePoints = linePoints;
	}

	public void setPaint(Paint paint) {
		this.paint.set(paint);
	}
}
