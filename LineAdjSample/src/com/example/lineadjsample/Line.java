package com.example.lineadjsample;

import java.util.ArrayList;

import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;

public class Line {
	
	ArrayList<PointF> points;
	PointF point;
	Path line;
	
	Line() {
		points = new ArrayList<PointF>();
		point = new PointF();
		line = new Path();
	}
	
	public ArrayList<PointF> getPoints() {
		return points;
	}

	public Path getLine() {
		return line;
	}
	
	public void setStartPointToPath(PointF point) {
		line.moveTo(point.x, point.y);
		if (points.size() >0) {
			points.clear();
		}
		points.add(point);
	}

	public void setLastPointToPath(PointF point) {
		line.setLastPoint(point.x, point.y);
		points.add(point);
	}
	
	public void addPointToPath(PointF point) {
		line.lineTo(point.x, point.y);
		points.add(point);
	}

	public Path reCreateLine(ArrayList<PointF> points) {
		line.reset();
		point = points.get(0);
		line.moveTo(point.x, point.y);
		for (int i = 1; i < points.size(); i++) {
			point = points.get(i);
			line.lineTo(point.x, point.y);
		}
		return line;
	}

	public Path reCreateLine(Rect src, Rect dst) {
		line.reset();
		PointF p = points.get(0);
		point = scaleCoordinates(p.x, p.y, src, dst);
		line.moveTo(point.x, point.y);
		for (int i = 1; i < points.size(); i++) {
			p = points.get(i);
			point = scaleCoordinates(p.x, p.y, src, dst);
			line.lineTo(point.x, point.y);
		}
		return line;
	}
	
	public PointF scaleCoordinates(float x, float y, Rect src, Rect dst) {
		return new PointF(
				(x - dst.left) / (dst.right - dst.left) * (src.right - src.left) + src.left,
				(y - dst.top) / (dst.bottom - dst.top) * (src.bottom - src.top) + src.top);
	}
	
}
