package com.example.linetest;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

	public int mode = 0;
	public static final int MODE_ADD = 1;
	public static final int MODE_DIVIDE = 2;
	public static final int MODE_CROP = 3;
	public static final int MODE_ZOOM = 4;
	
	public ArrayList<Path> lines;
	public Path line;
	
	public ArrayList<Path> zoomLines;
	public Path zoomLine;
	Paint paint;
	Matrix matrix;
	
	Bitmap bitmap;
	Canvas bc;
	
	Rect src;
	Rect dst;
	
	int height;
	int width;
	
	
	float zoomState = 2;

	public CanvasView(Context context) {
		super(context);
		init();
	}
	public CanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}
	
	private void init() {
		lines = new ArrayList<Path>();
		zoomLines = new ArrayList<Path>();
		
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		paint.setStrokeWidth(3);
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		
		matrix = new Matrix();
		matrix.postScale(zoomState, zoomState);
		
	}
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		bc = new Canvas(bitmap);
		dst = new Rect(0, 0, w, h);
		src = new Rect(0, 0, w, h);
		height = h;
		width = w;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		bc = new Canvas(bitmap);
		bc.drawColor(Color.WHITE);
		switch (mode) {
		case MODE_ADD:
			for (int i = 0; i < lines.size(); i++) {
				bc.drawPath(lines.get(i), paint);
			}
			dst.set(0, 0, width, height);
			src.set(0, 0, width, height);
			break;
		case MODE_DIVIDE:
			break;
		case MODE_CROP:
			break;
		case MODE_ZOOM:
			bitmap = Bitmap.createBitmap(width*2, height*2, Config.ARGB_8888);
			bc = new Canvas(bitmap);
			bc.drawColor(Color.WHITE);
			for (int i = zoomLines.size(); i < lines.size(); i++) {
				zoomLine = new Path();
				lines.get(i).transform(matrix , zoomLine);
				zoomLines.add(zoomLine);
			}
			Paint p = new Paint(paint);
			p.setStrokeWidth(paint.getStrokeWidth()*2);
			for (int i = 0; i < zoomLines.size(); i++) {
			bc.drawPath(zoomLines.get(i), p);
			int w = width/4;
			int h = height/4;
			dst.set(0, 0, width*2, height*2);
			src.set(w, h, w*3, h*3);
			}
			break;
		}

		canvas.drawBitmap(bitmap, src, dst, null);
		
	}
	Path path = null;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		float x = event.getX();
		float y = event.getY();
		Matrix m = new Matrix();
		
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lines.add(line = new Path());
			line.moveTo(x, y);
			if (mode == MODE_ZOOM) {
				path = new Path();
				path.moveTo(x, y);
				zoomLines.add(path);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			line.lineTo(x, y);
			if (mode == MODE_ZOOM) {
				path.lineTo(x, y);
			}
			break;
		case MotionEvent.ACTION_UP:
			line.setLastPoint(x, y);
			if (mode == MODE_ZOOM) {
				path.setLastPoint(x, y);
				m.postScale(1.0f/zoomState, 1.0f/zoomState);
				line.transform(m);
			}
			break;
		}
		invalidate();
		return true;
	}

}
