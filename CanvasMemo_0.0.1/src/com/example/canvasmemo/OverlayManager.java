package com.example.canvasmemo;

import java.io.ByteArrayOutputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class OverlayManager {

	float actionBarHeight;
	static float statusBarHeight;
	
	
	private static Point hardwareSize;
	private static Point displaySize;
	private static Bitmap overlayBitmap;
	
	
	private float viewLeft;
	private float viewTop;
	
	public OverlayManager(Context context) {
		onUpdateMeasureSize(context);
	}
	
	public void onUpdateMeasureSize(Context context){
		actionBarHeight = ((Activity) context).getActionBar().getHeight();
		onMeasureHardwareSize(context);
		onMeasureDisplaySize(context);
		onMeasureStatusBarHeight(context);
		createOverlay();
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	private Point onMeasureHardwareSize(Context context) {
		 
		DisplayMetrics displaymetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		
		int x = displaymetrics.widthPixels;
		int y = displaymetrics.heightPixels;
		
	    hardwareSize = new Point(x, y);
	 
		return hardwareSize;
	}
	
	private void onMeasureDisplaySize(Context context) {
		// ウィンドウマネージャのインスタンス取得
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		// ディスプレイのインスタンス生成
		Display display = wm.getDefaultDisplay();
		displaySize = new Point();
		display.getSize(displaySize);

	}
	
	private void onMeasureStatusBarHeight(Context context) {
		Rect rectgle = new Rect();
		 ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(rectgle);
		statusBarHeight = rectgle.top;
		
		
//		statusBarHeight = hardwareSize.y - displaySize.y;
		
		
		Log.d("statusBar", "statusBar height" + statusBarHeight);
	}

	private void onMeasureViewWidth(float top, float left) {
		viewTop = top +  statusBarHeight + actionBarHeight;
		viewLeft = left;
	}

	private void onMeasureViewSize(View view) {
		viewTop =  statusBarHeight + actionBarHeight/2;
//		viewTop = view.getTop() +  statusBarHeight + actionBarHeight;
//		viewTop = view.getTop() +  actionBarHeight;
//		viewTop = view.getTop() +  statusBarHeight;
//		viewTop = actionBarHeight;
//		viewTop = 0;
		viewLeft = view.getLeft();
	}
	
	private static void createOverlay(){
		overlayBitmap = Bitmap.createBitmap(hardwareSize.x, hardwareSize.y+76, Config.ARGB_8888);
	}
	
	public static Bitmap takeCapture(View view) {
		view.setDrawingCacheEnabled(true);
		Bitmap cache = Bitmap.createBitmap(view.getDrawingCache());
		view.setDrawingCacheEnabled(false);
		return cache;
		}

	public void addViewToOverlay(Bitmap bitmap, float top, float left) {
		onMeasureViewWidth(top, left);
		Canvas canvas = new Canvas(overlayBitmap);
		canvas.drawBitmap(bitmap, viewLeft, viewTop, null);
	}

	public void takeCaptureThenAddToOverlay(View view) {
		Canvas canvas = new Canvas(overlayBitmap);
		onMeasureViewSize(view);
		canvas.drawBitmap(takeCapture(view), viewLeft, viewTop, null);
	}
	
	public byte[] createOverlayBuffer(){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		overlayBitmap.compress(CompressFormat.PNG, 100, bos);
		return bos.toByteArray();
	}
	
	

}
