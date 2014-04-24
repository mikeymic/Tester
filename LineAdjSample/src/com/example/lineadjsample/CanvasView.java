package com.example.lineadjsample;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class CanvasView extends View {
	
	private int mode = MODE_DRAW;
	public static final int MODE_DRAW = 0;
	public static final int MODE_ZOOM = 1;
	
	Rect src;
	Rect dst;
	
	ArrayList<Line> lines;
	Line line;
	PointF tp;
	Paint paint;
	
	Bitmap bitmap;
	Canvas bc;
	
	ZoomListener zoomListener;
	ScaleGestureDetector gestureDetector;
	
	float scaleFactor;
	
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
		lines = new ArrayList<Line>();
		
		paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		paint.setStrokeWidth(3);
		paint.setColor(Color.BLACK);
		paint.setAntiAlias(true);
		
		zoomListener = new ZoomListener();
		gestureDetector = new ScaleGestureDetector(getContext(), zoomListener);
	}
	
	
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		bc = new Canvas(bitmap);
		dst = new Rect(0, 0, w, h);
		src = new Rect(0, 0, w, h);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		bc.drawColor(Color.BLUE);
		if (line != null) {
			for (int i = 0; i < lines.size(); i++) {
				bc.drawPath(lines.get(i).getLine(), paint);
			}
		}
		canvas.drawBitmap(bitmap, src, dst, paint);
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		float x = event.getX();
		float y = event.getY();

		tp = new PointF(x, y);
		
		int pointer = event.getPointerCount();
		
		if (pointer == 1) {
			mode = MODE_DRAW;
		} else {
			mode = MODE_ZOOM;
		}
		
		boolean result = true;
		
		if (mode == MODE_DRAW) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				line = new Line();
				line.setStartPointToPath(tp);
				lines.add(line);

				break;
			case MotionEvent.ACTION_MOVE:
				line.addPointToPath(tp);

				break;
			case MotionEvent.ACTION_UP:
				line.setLastPointToPath(tp);

				break;
			}
			result = true;
		}
		else if (mode == MODE_ZOOM) {
			result = gestureDetector.onTouchEvent(event);
			scaleFactor = zoomListener.scaleFactor;
		}
		invalidate();
		return result;
	}

	
	//Pathの座標変換 !拡大縮小するたびに呼ぶ必要あり？
	public PointF scaleCoordinates(float x, float y) {
		return new PointF(
				(x - dst.left) / (dst.right - dst.left) * (src.right - src.left) + src.left,
				(y - dst.top) / (dst.bottom - dst.top) * (src.bottom - src.top) + src.top);
	}



}
