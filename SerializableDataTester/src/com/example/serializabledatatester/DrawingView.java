package com.example.serializabledatatester;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

	private Bitmap bitmap;
	private Canvas bmpCanvas;

	CustomPath customPath;
	MyPaint paint;
	Line line;

	Canvas canvas;

	ArrayList<Line> lines;

	public void setLines(ArrayList<Line> lines) {
		this.lines = lines;
	}

	public DrawingView(Context context) {
		super(context);
		init();
	}

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		paint = createPaint();

	}

	private MyPaint createPaint() {
		MyPaint paint = new MyPaint();
		paint.setFilterBitmap(true);
		paint.setAntiAlias(true);
		paint.setStrokeWidth(6);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.RED);
		return paint;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Config.ARGB_8888);
		bmpCanvas = new Canvas(bitmap);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		this.canvas = canvas;

		for (int i = 0; i < lines.size(); i++) {
			canvas.drawPath(lines.get(i).path, lines.get(i).paint);
		}

		if (customPath != null) {
			canvas.drawPath(customPath, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float x = event.getX();
		float y = event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			customPath = new CustomPath();
			customPath.moveTo(x, y);
			break;
		case MotionEvent.ACTION_MOVE:
			customPath.lineTo(x, y);
			break;
		case MotionEvent.ACTION_UP:
			customPath.lineTo(x, y);
			lines.add(new Line(customPath, paint));
			break;
		}
		invalidate();
		return true;
	}



}
