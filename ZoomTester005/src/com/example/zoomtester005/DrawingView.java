package com.example.zoomtester005;

import java.nio.Buffer;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Class for representing the drawing surface.
 */
public class DrawingView extends View implements Observer {

	// Debugging
	private final boolean D = false;
	private final String tag = "DrawingView";

	/** Move threshold for recognizing a move and click */
	public static final float TOUCH_TOLERANCE = 4f;

	private final Rect src = new Rect();
	private final Rect dst = new Rect();

	private final ZoomRatio zoomRatio = new ZoomRatio();
	private ZoomState zoomState;

	protected boolean zoomEnabled;

	public Bitmap bitmap;
	public Canvas bc;

	public Paint bitPaint;

	public Buffer background;

	protected DrawingPath currPath;
	protected DrawingPath scaledPath;

	public Brush currBrush;
	private float scaledBrushRatio;

	private float drawX;
	private float drawY;

	private float scaledX;
	private float scaledY;

	public DrawingView(Context context) {
		super(context);
		init(context);
	}

	public DrawingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	protected void init(Context context) {
		bitPaint = new Paint(Paint.DITHER_FLAG);
		scaledPath = new DrawingPath();
		currPath = new DrawingPath();
		currBrush = new Brush(10f, Color.BLACK);
	}

	@Override
	protected void onDraw(Canvas c) {
		if (bitmap != null) {
			c.drawColor(0x000000FF);
			if (zoomEnabled) {
				calculateZoomRectangles();

				c.drawBitmap(bitmap, src, dst, bitPaint);

				// Calculate scaling of width on shown brush to match that of
				// drawn paths size
				final Paint p = new Paint(currPath.getBrush().getPaint());
				p.setStrokeWidth(p.getStrokeWidth() * scaledBrushRatio);

				// Draw the current path if user is drawing
				c.drawPath(currPath.getPath(), p);
			} else {
				// Draw previous paths already saved to the bitmap
				c.drawBitmap(bitmap, 0, 0, bitPaint);

				// Draw the current path if user is drawing
				c.drawPath(currPath.getPath(), currPath.getBrush()
						.getPaint());
			}
		}
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		if (zoomState != null && bitmap != null) {
			zoomRatio.updateZoomRatio(right - left, bottom - top,
					bitmap.getWidth(), bitmap.getHeight());
			zoomRatio.notifyObservers();
			zoomEnabled = true;

			calculateZoomRectangles();
		} else
			zoomEnabled = false;
	}

	// ------------------------- Touch methods --------------------------- //

	/**
	 * Creates a new path and starts to track it
	 */
	public void touch_down(float x, float y) {
		final long timeStamp = System.currentTimeMillis();

		currPath.setBrush(currBrush);
		currPath.getPath().reset();
		currPath.getPath().moveTo(x, y);
		currPath.setTimeStamp(timeStamp);
		drawX = x;
		drawY = y;

		if (zoomEnabled) {
			PointF p = scaleCoordinates(x, y);

			scaledPath.setBrush(currBrush);
			scaledPath.getPath().reset();
			scaledPath.getPath().moveTo(p.x, p.y);
			scaledPath.setTimeStamp(timeStamp);

			saveCoordinates(p.x, p.y);
		} else {
			saveCoordinates(x, y);
		}
	}

	/**
	 * Builds on the path if at least one of the new coordinates exceed the
	 * touch tolerance
	 */
	public boolean touch_move(float x, float y) {
		final float dx = Math.abs(x - drawX);
		final float dy = Math.abs(y - drawY);

		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
			currPath.getPath().quadTo(drawX, drawY, (x + drawX) / 2,
					(y + drawY) / 2);
			drawX = x;
			drawY = y;

			if (zoomEnabled) {
				PointF p = scaleCoordinates(x, y);
				scaledPath.getPath().quadTo(scaledX, scaledY,
						(p.x + scaledX) / 2, (p.y + scaledY) / 2);

				saveCoordinates(p.x, p.y);
			} else {
				saveCoordinates(x, y);//おそらく無意味
			}

			return true;
		}

		return false;
	}

	/**
	 * Stores, resets and draws the path to the canvas
	 */
	public void touch_up() {
		currPath.getPath().lineTo(drawX, drawY);
		if (zoomEnabled) {
			scaledPath.getPath().lineTo(scaledX, scaledY);
			drawPath(scaledPath);
			scaledPath.getPath().reset();
			scaledPath.clearCoords();
		} else
			drawPath(currPath);
		currPath.getPath().reset();
		currPath.clearCoords();

	}

	/**
	 * Middle man function to draw and save a path, so we can override this in
	 * multiplayer and draw paths order instead.
	 */
	protected void drawPath(DrawingPath path) {
		bc.drawPath(path.getPath(), currBrush.getPaint());
	}

	/**
	 * Middle man function to save coordinates, so we can override this and
	 * listen to the paths being drawn
	 */
	protected void saveCoordinates(float x, float y) {
		scaledX = x;
		scaledY = y;
	}

	// -------------------------- Zoom methods---------------------------- //

	/**
	 * Sets this views ZoomState and adds itself as an observer to it.
	 */
	public void setZoomState(ZoomState zoomState) {
		if (this.zoomState != null)
			this.zoomState.deleteObservers();

		this.zoomState = zoomState;
		this.zoomState.addObserver(this);

		invalidate();
	}

	/**
	 * Transforms the x and y coordinates from the rectDst plane to the rectSrc
	 * plane.
	 */
	public PointF scaleCoordinates(float x, float y) {
		return new PointF((x - dst.left) / (dst.right - dst.left)
				* (src.right - src.left) + src.left, (y - dst.top)
				/ (dst.bottom - dst.top) * (src.bottom - src.top) + src.top);
	}

	/**
	 * Calculates the scaled imaged that should be displayed
	 */
	public void calculateZoomRectangles() {
		final float ratio = zoomRatio.get();

		final int viewWidth = getWidth();
		final int viewHeight = getHeight();
		final int bitmapWidth = bitmap.getWidth();
		final int bitmapHeight = bitmap.getHeight();

		final float panX = zoomState.getPanX();
		final float panY = zoomState.getPanY();
		final float zoomX = zoomState.getZoomX(ratio) * viewWidth / bitmapWidth;
		final float zoomY = zoomState.getZoomY(ratio) * viewHeight
				/ bitmapHeight;

		// Setup source and destination rectangles
		src.left = (int) (panX * bitmapWidth - viewWidth / (zoomX * 2));
		src.top = (int) (panY * bitmapHeight - viewHeight / (zoomY * 2));
		src.right = (int) (src.left + viewWidth / zoomX);
		src.bottom = (int) (src.top + viewHeight / zoomY);

		dst.left = getLeft();
		dst.top = getTop();
		dst.right = getRight();
		dst.bottom = getBottom();

		// Adjust source rectangle so that it fits within the source image.
		if (src.left < 0) {
			dst.left += -src.left * zoomX;
			src.left = 0;
		}
		if (src.right > bitmapWidth) {
			dst.right -= (src.right - bitmapWidth) * zoomX;
			src.right = bitmapWidth;
		}
		if (src.top < 0) {
			dst.top += -src.top * zoomY;
			src.top = 0;
		}
		if (src.bottom > bitmapHeight) {
			dst.bottom -= (src.bottom - bitmapHeight) * zoomY;
			src.bottom = bitmapHeight;
		}

		// Calculate scaled brush width in new plane
		scaledBrushRatio = ((float) (dst.right - dst.left))
				/ ((float) (src.right - src.left));
	}

	// -------------------------- Observer methods ----------------------- //

	@Override
	public void update(Observable observable, Object data) {
		invalidate();
	}

	// ------------------------- Getters & Setters ----------------------- //

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		this.bc = new Canvas(this.bitmap);

		zoomRatio.updateZoomRatio(getWidth(), getHeight(), bitmap.getWidth(),
				bitmap.getHeight());
		zoomRatio.notifyObservers();
		invalidate();
	}

	public Brush getCurrentBrush() {
		return currBrush;
	}

	public void setCurrentBrush(Brush brush) {
		currBrush = brush;
	}

	public void setBackground(Buffer buffer) {
		background = buffer;
		bitmap.copyPixelsFromBuffer(background);

		invalidate();
	}

	public PointF getLastPoint() {
		if (zoomEnabled)
			return new PointF(scaledX, scaledY);
		return new PointF(drawX, drawY);
	}

	public ZoomRatio getZoomRatio() {
		return zoomRatio;
	}

	public Rect getSrcRect() {
		return src;
	}

	public Rect getDstRect() {
		return dst;
	}

	public float getZoomX() {
		return zoomState.getZoomX(zoomRatio.get());
	}

	public float getZoomY() {
		return zoomState.getZoomY(zoomRatio.get());
	}
}
