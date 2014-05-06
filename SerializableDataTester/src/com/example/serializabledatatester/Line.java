package com.example.serializabledatatester;

import java.io.Serializable;

public class Line implements Serializable{

	private static final long serialVersionUID = 6255752248513019027L;

	public CustomPath path;
	public MyPaint paint;

	public Line() {
		this.path = new CustomPath();
		this.paint = new MyPaint();
	}

	public Line(CustomPath path, MyPaint paint) {
		this.path = new CustomPath(path);
		this.paint = new MyPaint(paint);
	}

//	public void drawLine(Canvas canvas) {
//		canvas.drawPath(path, paint);
//	}

}
