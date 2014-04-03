package com.example.canvasmemosample;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

	private Bitmap bitmap;
	private Paint paint;
	private Path path;
	private float y;
	private float x;
	private PaintTool paintTool;

	private ArrayList<Path> paths;

	private boolean isEraseMode = false;

	private int lastPathIndex;



	public CanvasView(Context context) {
		super(context);
		paintTool = new PaintTool();
		paint = paintTool.createPaint();
	}

	/* (非 Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(Color.TRANSPARENT);

		if (bitmap != null) {
			canvas.drawBitmap(bitmap, 0, 0, null);
		}

		if (path != null) {
			canvas.drawPath(path, paint);

			if (isEraseMode) {
				paint.setColor(paintTool.defaultColor);
				paint.setXfermode(null);
				isEraseMode = !isEraseMode;
			}
		}


	}

	/* (非 Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		x = event.getX();
		y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			path = new Path();
			path.moveTo(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			path.lineTo(x, y);
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			path.lineTo(x, y);
			bitmapCapture();
			invalidate();

			if (paths == null) {
				paths = new ArrayList<Path>();
			}
			if (paths != null) {
				paths.add(path);
				lastPathIndex = paths.size()-1;
			}

			break;
		}

		return true;
	}

	public void undoLine() {
		int lastPath = paths.size()-1;

		path = new Path(paths.get(lastPath));
		paint.setColor(Color.WHITE);

		bitmapCapture();
		isEraseMode = true;
		invalidate();

		paths.remove(lastPath);
	}


	public void bitmapCapture() {
		setDrawingCacheEnabled(true);
		bitmap = Bitmap.createBitmap(getDrawingCache());
		setDrawingCacheEnabled(false);
	}



}
