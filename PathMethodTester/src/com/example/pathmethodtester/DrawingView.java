package com.example.pathmethodtester;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class DrawingView extends View {

	Path line;
	ArrayList<Path> lines;
	PointF p;
	Paint paint;

	RectF boundsF;
	Rect bounds;
	Region region;
	Region boundsRegion;

	Path tmp = new Path();

	private RectF outRect;

	PathMeasure measure = new PathMeasure();

	boolean isDoubleTap = false;

	PointF sp = new PointF();
	PointF dp = new PointF();

	GridDrawable gridDrawable;

	ZoomListener zoomListener;
	ScaleGestureDetector scaleDetector;

	float scaleFactor;

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
		createDrawSet();
		createListener();

		boundsF = new RectF(300, 300, 600, 600);
		bounds = new Rect(300, 300, 600, 600);
		outRect = new RectF();
		region = new Region(0,0,10,10);
		boundsRegion = new Region(bounds);

		gridDrawable = new GridDrawable();
		setBackgroundDrawable(gridDrawable);

	}

	private void createListener() {
		zoomListener = new ZoomListener();
		scaleDetector = new ScaleGestureDetector(getContext(), zoomListener);
	}

	private void createDrawSet() {
		line = new Path();
		lines = new ArrayList<Path>();
		p = new PointF();
		createPaint();
	}

	private void createPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setStyle(Style.STROKE);
		paint.setStrokeCap(Cap.ROUND);
		paint.setStrokeJoin(Join.ROUND);
		paint.setStrokeWidth(3);
		paint.setColor(Color.MAGENTA);
	}

	/* (非 Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		//履歴があれば描画

		for (int i = 0; i < lines.size(); i++) {
			tmp.set(lines.get(i));
//			canvas.drawRect(boundsF, paint);

			if (isDoubleTap) {
				tmp.offset(dp.x, dp.y);//オフセット分だけ移動する
				outRect.offsetTo(dp.x, dp.y);
			}

			canvas.drawPath(tmp, paint);

			outRect.union(boundsF);
			}
		//書いたpathの即時反映（タップアップ時に消える）
		canvas.drawPath(line, paint);


		line.computeBounds(boundsF, true);//指定したRectFにpathの範囲のRectFを返す
		canvas.drawRect(outRect, paint);
	}

	/* (非 Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		p.set(event.getX(), event.getY());

		if (event.getPointerCount() > 1) {
			isDoubleTap = true;

			scaleDetector.onTouchEvent(event);
			scaleFactor = zoomListener.getScaleFactor();
			Log.d("TEST", String.valueOf(scaleFactor));
			gridDrawable.setScaleFactor(scaleFactor);

		} else {
			isDoubleTap = false;
		}

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			sp.set(p);
			if (isDoubleTap) {
				break;
			}

			line.moveTo(p.x, p.y);
			break;
		case MotionEvent.ACTION_MOVE:

			if (isDoubleTap) {
				float x = (p.x - sp.x);
				float y = (p.y - sp.y);
				dp.set(x, y);
				gridDrawable.setMoveX(x);
				gridDrawable.setMoveY(y);
				break;
			}

			line.incReserve(event.getHistorySize());//これから入るPathに入るPoint数を指定する
			for (int i =0; i < event.getHistorySize(); i++) {
				line.lineTo(event.getHistoricalX(i), event.getHistoricalY(i));
			}
			line.lineTo(p.x, p.y);
			break;
		case MotionEvent.ACTION_UP:
			if (isDoubleTap) {
				break;
			}

			line.setLastPoint(p.x, p.y);
			lines.add(new Path(line));
			line.reset();


			break;
		}
		invalidate();
		invalidateDrawable(gridDrawable);
		return true;
	}




}
