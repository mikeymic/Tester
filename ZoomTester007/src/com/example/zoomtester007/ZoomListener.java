package com.example.zoomtester007;

import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

public class ZoomListener implements OnScaleGestureListener {

    public float scaleFactor = 1.0f;
    private float oldFoucusX;
    private float oldFoucusY;
    private float currentFoucusX;
    private float currentFoucusY;


    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
	oldFoucusX = detector.getFocusX();
	oldFoucusY = detector.getFocusY();
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
