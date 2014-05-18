package com.example.zoomtester006;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class DrawingView extends View {

    private static final int TOUCH_TOLERANCE = 4;

    // リスナー
    // ScrollListener scrollListener;
    private ZoomListener zoomListener;
    private ScaleGestureDetector scaleGestureDetector;

    // Obverbale
    ZoomRatio zoomRatio;
    ZoomState zoomState;

    // 座標
    private PointF newP = new PointF();
    private PointF oldP = new PointF();

    // path描画
    private DrawLine drawLine;
    private DrawLine scaledLine = new DrawLine();
    private ArrayList<DrawLine> drawLines;
    private ArrayList<DrawLine> scaledLines;
    private Paint drawPaint;
    private Paint scaledPaint = new Paint();

    // bitmap描画
    private Bitmap bitmap;
    private Canvas bmpCanvas;
    private Paint bmpPaint;
    private Rect src = new Rect();
    private Rect dst = new Rect();

    private boolean isZoom;

    private float scaledBrushRatio;

    private float zoomRatioF;
    private float scaleFactor;

    private PointF distP = new PointF();;

    public DrawingView(Context context) {
	super(context);
	init();
	init(context);

    }

    public DrawingView(Context context, AttributeSet attrs) {
	super(context, attrs);
	init();
	init(context);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	init();
	init(context);
    }

    /**
     * ------------------------------ Init Method ------------------------------
     **/

    private void init() {
	drawLines = new ArrayList<DrawLine>();
	scaledLines = new ArrayList<DrawLine>();

	bmpPaint = new Paint();
	bmpPaint.setFilterBitmap(true);

	createDrawPaint();
    }

    private void init(Context context) {
	// scrollListener = new ScrollListener();
	// setOnTouchListener(scrollListener);
	zoomListener = new ZoomListener();
	scaleGestureDetector = new ScaleGestureDetector(context, zoomListener);
    }

    private void createDrawPaint() {
	drawPaint = new Paint();
	drawPaint.setAntiAlias(true);
	drawPaint.setStyle(Style.STROKE);
	drawPaint.setStrokeCap(Cap.ROUND);
	drawPaint.setStrokeJoin(Join.ROUND);
	drawPaint.setStrokeWidth(5);
	drawPaint.setColor(Color.BLUE);
    }

    // 生成タイミングは後で考える
    public void createBitmap(int width, int height) {
	bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	bmpCanvas = new Canvas(bitmap);
	bmpCanvas.drawColor(0x33FFFFFF);
    }

    public PointF scaleCoordinates(float x, float y) {
	return new PointF((x - dst.left) / (dst.width()) * (src.width()) + src.left,
		(y - dst.top) / (dst.height()) * (src.height()) + src.top);
    }

    public void calculateZoomRectangles() {
	final int viewWidth = getWidth();
	final int viewHeight = getHeight();
	final int bmpWidth = bitmap.getWidth();
	final int bmpHeight = bitmap.getHeight();

	// 画面回転した時に比率が変わるので、それを考慮に入れる
	final float zoomX = Math.min(scaleFactor, scaleFactor * zoomRatioF)
		* viewWidth / bmpWidth;
	final float zoomY = Math.min(scaleFactor, scaleFactor * zoomRatioF)
		* viewHeight / bmpHeight;

	final float panY = 0.5f;
	final float panX = 0.5f;

	src.left = (int) ((panX * bmpWidth) - (viewWidth / (zoomX * 2)));
	src.top = (int) ((panY * bmpHeight) - (viewHeight / (zoomY * 2)));
	src.right = (int) (src.left + (viewWidth / zoomX));
	src.bottom = (int) (src.top + (viewHeight / zoomY));

	dst.left = getLeft();
	dst.top = getTop();
	dst.right = getRight();
	dst.bottom = getBottom();

	// 画面をはみでないように設定
	if (src.left < 0) {
	    dst.left += -src.left * zoomX;
	    src.left = 0;
	}
	if (src.right > bmpWidth) {
	    dst.right -= (src.right - bmpWidth) * zoomX;
	    src.right = bmpWidth;
	}
	if (src.top < 0) {
	    dst.top += -src.top * zoomY;
	    src.top = 0;
	}
	if (src.bottom > bmpHeight) {
	    dst.bottom -= (src.bottom - bmpHeight) * zoomY;
	    src.bottom = bmpHeight;
	}

	// ズ―ム倍率に合わせてペンの太さを変える
	scaledBrushRatio = ((float) dst.width() / (float) src.width());
    }

    public void updateZoomRatio(float viewWidth, float viewHeight,
	    float contentWidth, float contentHeight) {
	zoomRatioF = (contentWidth / contentHeight);
    }

    /**
     * ------------------------------ View Method ------------------------------
     **/

    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);

	Log.d("TEST", "[Canvas Width]" + Float.toString(canvas.getWidth()));
	Log.d("TEST", "[Canvas Height]" + Float.toString(canvas.getHeight()));
	Log.d("TEST", "[Bitmap Width]" + Float.toString(bitmap.getWidth()));
	Log.d("TEST", "[Bitmap Height]" + Float.toString(bitmap.getHeight()));

	if (bitmap == null) {
	    return;
	}
	if (drawLine == null) {// 暫定
	    return;
	}

	if (!isZoom) {
	    /** ---------- 通常描画 ---------- **/
	    // 作り終わったpathはACTION_UPでbtimapに描画
	    // btimap描画
	    canvas.drawBitmap(bitmap, 0, 0, bmpPaint);
	    // 作成中のPathをCanvasに描画（終わったらBitmapに）
	    canvas.drawPath(drawLine.getLine(), drawLine.getPaint());
	    /** ---------- 終了 ---------- **/

	} else if (isZoom) {
	    /** ---------- zoom中 ---------- **/

	    // Rectの範囲を判別
	    calculateZoomRectangles();

	    // btimap描画
	    canvas.drawBitmap(bitmap, src, dst, bmpPaint);
	    // ズーム中に作成中のPathを描画（ズーム中なので線の太さを変える）
	    // 終わったらBitmapへ
	    scaledPaint.set(drawLine.getPaint());
	    scaledPaint.setStrokeWidth(drawLine.getPaint().getStrokeWidth() * scaledBrushRatio);
	    canvas.drawPath(drawLine.getLine(), scaledPaint);
	    /** ---------- 終了 ---------- **/
	}

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	super.onSizeChanged(w, h, oldw, oldh);
	createBitmap(w, h);
	updateZoomRatio(w, h, bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

	int pointer = event.getPointerCount();

	if (pointer > 1) {
	    isZoom = true;
	    boolean result = scaleGestureDetector.onTouchEvent(event);
	    scaleFactor = zoomListener.scaleFactor;
	    invalidate();
	    return result;
	}
	newP.set(event.getX(), event.getY());

	switch (event.getAction()) {
	case MotionEvent.ACTION_DOWN:

	    if (isZoom) {
		/** ---------- zoom中 ---------- **/
		PointF pp = scaleCoordinates(newP.x, newP.y);
		scaledLine.setStartLinePoint(pp);
	    }

	    /** ---------- 通常描画 ---------- **/
	    drawLine = new DrawLine();
	    drawLine.setStartLinePoint(newP);
	    drawLine.setPaint(drawPaint);
	    oldP.set(newP);

	    break;
	case MotionEvent.ACTION_MOVE:
	    /*-----------タッチポイントが移動しているかの確認----------*/
	    // 移動していない時
	    distP.set(Math.abs(newP.x - oldP.x), Math.abs(newP.y - oldP.y));
	    if (distP.x < TOUCH_TOLERANCE && distP.y < TOUCH_TOLERANCE) {
		break;
	    }

	    // 移動している時

	    if (isZoom) {
		/** ---------- zoom中 ---------- **/
		PointF pp = scaleCoordinates(newP.x, newP.y);
		scaledLine.addLinePoint(pp);
	    }

	    /** ---------- 通常描画 ---------- **/
	    drawLine.addLinePoint(newP);
	    oldP.set(newP);

	    break;
	case MotionEvent.ACTION_UP://最後だけ処理を分ける
	    if (isZoom) {
		/** ---------- zoom中 ---------- **/
		PointF pp = scaleCoordinates(newP.x, newP.y);
		scaledLine.setLastLinePoint(pp);
		bmpCanvas.drawPath(scaledLine.getLine(), drawLine.getPaint());
//		drawLines.add(scaledLine);//おそらくアンドゥ/リドゥ用？あとは再描画
	    } else {
		/** ---------- 通常描画 ---------- **/
		drawLine.setLastLinePoint(newP);
		bmpCanvas.drawPath(drawLine.getLine(), drawLine.getPaint());
		drawLines.add(drawLine);//おそらくアンドゥ/リドゥ用？あとは再描画
	    }


	    break;
	}
	invalidate();
	return true;
    }

}
