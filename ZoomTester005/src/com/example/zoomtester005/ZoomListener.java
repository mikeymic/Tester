package com.example.zoomtester005;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

public class ZoomListener implements View.OnTouchListener {

	/**
	 * Enum defining listener modes. Before the view is touched the listener is
	 * in the UNDEFINED mode. Once touch starts it can enter either one of the
	 * other two modes: If the user scrolls over the view the listener will
	 * enter PAN mode, if the user lets his finger rest and makes a long press
	 * the listener will enter ZOOM mode. Switching between DRAWING and not will
	 * have to be done actively.
	 */
	public enum Mode {
		UNDEFINED, PAN, ZOOM, DRAWING
	}

	/** Distance touch can wander before we it's recognized as scrolling */
	private final int touchSlop;

	/** Duration in ms before a press turns into a long press */
	private final int pressTimeout;

	/** Maximum velocity for fling */
	private final int scaledMaximumFlingVelocity;

	/** Velocity tracker for touch events */
	private VelocityTracker velocityTracker;

	/** Current action move */
	private Mode mode = Mode.DRAWING;

	/** Handler for panning and zooming motions */
	private DynamicZoomControl zoomControl;

	/** Coordinates for last registered touch event */
	private float x;
	private float y;

	/** Coordinates for last registered touch down event */
	private float downX;
	private float downY;


	/** Runnable that sets the current mode to ZOOM */
	private final Runnable longPressRunnable = new Runnable() {
		public void run() {
			mode = Mode.ZOOM;
		}
	};

	/**
	 * Creates a ZoomListener in the given context with settings given by the
	 * view configuration
	 */
	public ZoomListener(Context context) {
		pressTimeout = ViewConfiguration.getLongPressTimeout();
		touchSlop = ViewConfiguration.get((Activity) context).getScaledTouchSlop();
		scaledMaximumFlingVelocity = ViewConfiguration.get((Activity) context).getScaledMaximumFlingVelocity();
	}

	// ------------------- View.OnTouchListener methods ------------------ //

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		PointF curr = new PointF(event.getX(), event.getY());

		if (velocityTracker == null)
			velocityTracker = VelocityTracker.obtain();
		velocityTracker.addMovement(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			zoomControl.stopFling();

			if (mode != Mode.DRAWING)
				v.postDelayed(longPressRunnable, pressTimeout);
			else
				((DrawingView) v).touch_down(curr.x, curr.y);

			downX = curr.x;
			downY = curr.y;
			x = curr.x;
			y = curr.y;
			break;

		case MotionEvent.ACTION_MOVE:
			if (mode != Mode.DRAWING) {
				final float dx = (curr.x - x) / v.getWidth();
				final float dy = (curr.y - y) / v.getHeight();

				if (mode == Mode.ZOOM) {
					zoomControl.zoom((float) Math.pow(20, -dy),
							downX / v.getWidth(), downY / v.getHeight());
				} else if (mode == Mode.PAN) {
					zoomControl.pan(-dx, -dy);
				} else {
					final float scrollX = downX - curr.x;
					final float scrollY = downY - curr.y;
					final float dist = (float) Math.sqrt(scrollX * scrollX
							+ scrollY * scrollY);

					if (dist >= touchSlop) {
						v.removeCallbacks(longPressRunnable);
						mode = Mode.PAN;
					}
				}
			} else
				((DrawingView) v).touch_move(curr.x, curr.y);

			x = curr.x;
			y = curr.y;
			break;

		case MotionEvent.ACTION_UP:
			if (mode != Mode.DRAWING) {
				if (mode == Mode.PAN) {
					velocityTracker.computeCurrentVelocity(1000,
							scaledMaximumFlingVelocity);
					zoomControl.startFling(
							-velocityTracker.getXVelocity() / v.getWidth(),
							-velocityTracker.getYVelocity() / v.getHeight());
				} else {
					zoomControl.startFling(0, 0);
				}

				velocityTracker.recycle();
				velocityTracker = null;
				v.removeCallbacks(longPressRunnable);
				mode = Mode.UNDEFINED;
			} else
				((DrawingView) v).touch_up();

			break;

		default:
			velocityTracker.recycle();
			velocityTracker = null;
			v.removeCallbacks(longPressRunnable);
			mode = Mode.UNDEFINED;
			break;
		}

		v.invalidate();
		return true;
	}

	// ------------------------ Getters & Setters ------------------------ //

	public void setControlType(Mode touchState) {
		this.mode = touchState;
	}

	public void setZoomControl(DynamicZoomControl zoomControl) {
		this.zoomControl = zoomControl;
	}
}
