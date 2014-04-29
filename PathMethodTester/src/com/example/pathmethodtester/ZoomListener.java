package com.example.pathmethodtester;

import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

public class ZoomListener implements OnScaleGestureListener {

	float scaleFactor = 1.0f;

	/**
	 * @return scaleFactor
	 */
	public float getScaleFactor() {
		return scaleFactor;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		return true;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		scaleFactor *= detector.getScaleFactor();
		return true;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {

	}

}
