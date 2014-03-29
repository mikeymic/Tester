package com.example.callcanvasbynotification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

public class CanvasActivity extends Activity {

    public static final int LINE_THICK = 10;
    public static final int LINE_MIDDLE = 6;
    public static final int LINE_SHIN = 2;
    private CanvasView canvasView;

    public class Line {
	private int color;
	private int width;
	private ArrayList<Point> points;

	public Line(int color, int width) {
		points = new ArrayList<Point>();
		this.color = color;
		this.width = width;
	}

	public int getColor() {
		return color;
	}

	public int getWidth() {
		return width;
	}

	public void addPoint(Point p) {
		points.add(p);
	}

	public ArrayList<Point> getPoints() {
		return points;
	}
}

    public class CanvasView extends View {
	// 	全ての線を管理するリスト
	private ArrayList<Line> lines = new ArrayList<Line>();
	// 一本の線
	private Line aLine;
	// 描画色
	private int currentColor = Color.RED;
	// コンテキスト
	private Context context;
	// 線の太さ
	//private int lineWidth = 6;
	private int currentWidth = CanvasActivity.LINE_MIDDLE;

	public CanvasView(Context context) {
		super(context);
		this.context = context;
	}

	public CanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
	}

	public void undo() {
		if(lines.size() > 0) {
			lines.remove(lines.size() - 1);
		}
		invalidate();
	}

	public void clear() {
		lines.clear();
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawAll(canvas);
	}

	public void drawAll(Canvas canvas) {
		canvas.drawColor(Color.argb(30, 255, 255, 255));
//		canvas.drawColor(Color.argb(255, 255, 255, 255));
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		for(Line line: lines) {
			paint.setColor(line.getColor());
			paint.setStrokeWidth(line.getWidth());
			for(int i = 0; i < (line.getPoints().size() - 1); i++) {
				Point s = line.getPoints().get(i);
				Point e = line.getPoints().get(i + 1);
				canvas.drawLine(s.x, s.y, e.x, e.y, paint);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//return super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			aLine = new Line(currentColor, currentWidth);
			lines.add(aLine);
			break;
		case MotionEvent.ACTION_MOVE:
			int x = (int)event.getX();
			int y = (int)event.getY();
			Point p = new Point(x, y);
			aLine.addPoint(p);
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		invalidate();
		return true;
	}

	public void setColor(int c) {
		currentColor = c;
	}

	public void setLineWidth(int width) {
		currentWidth = width;
	}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	canvasView = new CanvasView(this);
	setContentView(canvasView);

	Button button = new Button(this);
	addContentView(button, new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
	button.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			Drawable drawable = new BitmapDrawable(getResources(), getViewBitmap(canvasView));
			canvasView.setBackground(drawable);
		}
	});
    }

	public Bitmap getViewBitmap(View view) {
		view.setDrawingCacheEnabled(true);
		Bitmap cache = view.getDrawingCache();
		if (cache == null) {
			return null;
		}
		Bitmap bitmap = Bitmap.createBitmap(cache);
		view.setDrawingCacheEnabled(false);
		return bitmap;
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_dialog, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_save_drawing_cache) {
//			Toast.makeText(this, "Add Selected", Toast.LENGTH_SHORT).show();
			AlertDialog.Builder adialog =new AlertDialog.Builder(this);
			adialog.setTitle("アラートダイアログ");
			adialog.setMessage("OK:ボタン押下");

			adialog.setPositiveButton("OK", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {






					Toast.makeText(CanvasActivity.this, "OKボタンが押されました", Toast.LENGTH_SHORT).show();
				}
			});

			adialog.setNegativeButton("NO", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Toast.makeText(CanvasActivity.this, "キャンセルされました", Toast.LENGTH_SHORT).show();
				}
			});
//			adialog.setNeutralButton("XXX", new OnClickListener() {
//
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					Toast.makeText(CanvasActivity.this, "XXXボタンが押されました", Toast.LENGTH_SHORT).show();
//				}
//			});
			adialog.show();


		}

		return super.onOptionsItemSelected(item);
	}


	    private void saveToFile(String filename) {
	        try {
	            FileOutputStream out = openFileOutput(filename, MODE_PRIVATE);

	            canvasView.setDrawingCacheEnabled(false);
	            canvasView.setDrawingCacheEnabled(true);

	            Bitmap bitmap0 = Bitmap.createBitmap(canvasView.getDrawingCache());
	            bitmap0.compress(CompressFormat.PNG, 100, out);

	            out.close();

	        } catch (FileNotFoundException e) {
	        } catch (IOException e) {
	        }
	    }















}
