package com.example.zoomtester007;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;


public class DrawingView extends View {

	PointF p = new PointF();

	LineData lineData = new LineData();

	DrawLine drawLine = new DrawLine();
	Paint paint;

	Path tmp = new Path();
	ArrayList<PointF> tmpP = new ArrayList<PointF>();
	ArrayList<PointF> ppp;
	ArrayList<PointF> ppp2;

	ArrayList<Path> ps = new ArrayList<Path>();
	Path t = new Path();

	ScaleGestureDetector scaleDetector;
	ZoomListener zoomListener;

	float scaleFactor = 1.0f;


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

	public void init() {
		createPaint();

		zoomListener = new ZoomListener();
		scaleDetector = new ScaleGestureDetector(getContext(), zoomListener);
	}

	private void createPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		paint.setStrokeWidth(3);
		paint.setColor(Color.GREEN);
	}


	/* (非 Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

//		lineData.drawLines(canvas);

		int count = lineData.getLineCount();

			for (int i = 0; i < count; i++) {
				Path t = lineData.getDrawLine(i).getLine();
				t.rewind();

				ppp = lineData.getDrawLine(i).getLinePoints();
				for (int j = 0; j < ppp.size(); j++) {
					if (j == 0) {
						t.moveTo(scaleX(ppp.get(j).x), scaleY(ppp.get(j).y));
					} else {
						t.lineTo(scaleX(ppp.get(j).x), scaleY(ppp.get(j).y));
					}
				}
				canvas.drawPath(t, paint);
			}



//		canvas.restore();

		canvas.drawPath(tmp, paint);
//		canvas.save();


	}
	/* (非 Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		scaleDetector.onTouchEvent(event);
		scaleFactor = zoomListener.scaleFactor;

		p.set(event.getX(), event.getY());

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			tmp.moveTo(p.x, p.y);
			tmpP.add(new PointF(p.x , p.y));

			break;
		case MotionEvent.ACTION_MOVE:
			PointF tp = tmpP.get(tmpP.size()-1);
//			tmp.quadTo(tp.x, tp.y, (p.x + tp.x)/2, (p.y + tp.y)/2);
			tmp.lineTo(p.x, p.y);
			tmpP.add(new PointF(p.x , p.y));
			break;
		case MotionEvent.ACTION_UP:
			lineData.addLine(new DrawLine(tmp, tmpP, paint));
			tmp.rewind();
			tmpP = new ArrayList<PointF>();
			break;
		}
		invalidate();
		return true;
	}

	public float scaleX(float x) {
		return x*scaleFactor-(x/2);
	}
	public float scaleY(float y) {
		return y*scaleFactor-(y/2);
	}





}
