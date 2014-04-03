package com.example.canvasmemosample;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;

public class PaintTool {
	public int defaultColor = Color.BLACK;
	private Style defaultStyle = Style.STROKE;
	private Cap defaultStrokeCap = Cap.ROUND;
	private Join defaultStrokeJoin = Join.ROUND;
	private float defaultStrokeWidth = 6;

	public int ClearColor = Color.argb(30, 255, 255, 255);


	public Paint createPaint() {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(defaultColor);
		paint.setStyle(defaultStyle);
		paint.setStrokeCap(defaultStrokeCap);
		paint.setStrokeJoin(defaultStrokeJoin);
		paint.setStrokeWidth(defaultStrokeWidth);
		return paint;
	}
}
