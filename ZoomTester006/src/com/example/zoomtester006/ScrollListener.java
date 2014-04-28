package com.example.zoomtester006;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class ScrollListener implements OnTouchListener {

    PointF p = new PointF();
    
    @Override
    public boolean onTouch(View view, MotionEvent event) {
	
	p.set(event.getX(), event.getY());
	
	switch (event.getAction()) {
	case MotionEvent.ACTION_DOWN:

	    break;
	case MotionEvent.ACTION_MOVE:

	    break;
	case MotionEvent.ACTION_UP:

	    break;
	}
	return true;
    }

}
