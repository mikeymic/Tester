package com.example.canvasmemosample;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class OverlayManager {




	private int appTop;

	private Bitmap overlayBitmap;

	private Point dispSize;
	private Point realSize;
	private int statusBarHeight;
	private int actionBarHeight;
	private int viewLeft;
	private int viewTop;


	public OverlayManager(Activity activity) {
		actionBarHeight = activity.getActionBar().getHeight();
		getWindowSizes(activity);
		CreateOverlay();
	}

	private void CreateOverlay() {
		overlayBitmap = Bitmap.createBitmap(dispSize.x, dispSize.y, Config.ARGB_8888);
	}

	private void getWindowSizes(Activity activity) {
		// ウィンドウマネージャのインスタンス取得
		WindowManager wm = (WindowManager)activity.getSystemService(activity.WINDOW_SERVICE);
		// ディスプレイのインスタンス生成
		Display disp = wm.getDefaultDisplay();
		dispSize = new Point();
		disp.getSize(dispSize);

		realSize = new Point(0, 0);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//	        // Android 4.2以上
//	        disp.getRealSize(realSize);
//
//	    } else
	    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
	        // Android 3.2以上
	        try {
	            Method getRawWidth = Display.class.getMethod("getRawWidth");
	            Method getRawHeight = Display.class.getMethod("getRawHeight");
	            int width = (Integer) getRawWidth.invoke(disp);
	            int height = (Integer) getRawHeight.invoke(disp);
	            realSize.set(width, height);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    statusBarHeight = realSize.y - dispSize.y;

	}


	private void getMeasuredSizes(View view) {
		viewLeft = view.getLeft();
//		viewTop = actionBarHeight + statusBarHeight;
		viewTop = view.getTop() + actionBarHeight + statusBarHeight;
	}

	public Bitmap takeViewCapture(View view) {
		getMeasuredSizes(view);

		view.setDrawingCacheEnabled(true);
		Bitmap cache = view.getDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(cache);
		view.setDrawingCacheEnabled(false);
		return bitmap;
	}

	public void addBitmapToOverlay(Bitmap bitmap) {
		Canvas overlayCanvas = new Canvas(overlayBitmap);
		overlayCanvas.drawBitmap(bitmap, viewLeft,viewTop, null);
	}

	public void addBitmapToOverlay(Bitmap bitmap, int width, int height) {
		Canvas overlayCanvas = new Canvas(overlayBitmap);
		overlayCanvas.drawBitmap(bitmap, width, height, null);
	}

	public byte[] createOverlayBuffer() {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		overlayBitmap.compress(Bitmap.CompressFormat.PNG, 90, bout);
		return bout.toByteArray();
	}




}

