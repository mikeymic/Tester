package com.example.lineadjsample;

import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;

public class ZoomListener implements OnScaleGestureListener{

	public float scaleFactor;
	
	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
//		scaleFactor = detector.getScaleFactor();
		return false;
	}
	
	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		scaleFactor *= detector.getScaleFactor();
		return false;
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		scaleFactor *= detector.getScaleFactor();
	}

}
