package com.example.canvasmemosample;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.os.IBinder;
import android.view.WindowManager;
import android.widget.Toast;

public class LayerService extends Service {

	static CanvasView view;

	WindowManager wm;
	/*
	 * (非 Javadoc)
	 *
	 * @see android.app.Service#onStartCommand(android.content.Intent, int,
	 * int)
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		// Viewからインフレータを作成する
//		LayoutInflater layoutInflater = LayoutInflater.from(this);

		// 重ね合わせするViewの設定を行う
		WindowManager.LayoutParams params = new WindowManager.LayoutParams(
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
				WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
				PixelFormat.TRANSLUCENT);

		// WindowManagerを取得する
		wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

		// レイアウトファイルから重ね合わせするViewを作成する
//		view = layoutInflater.inflate(R.layout.overlay, null);
		byte[] data = intent.getByteArrayExtra("OverlayView");
		Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
		view = new CanvasView(this);
		view.setBackground(new BitmapDrawable(getResources(), bmp));
		// Viewを画面上に重ね合わせする
		wm.addView(view, params);

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "MyService#onCreate", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		// サービスが破棄されるときには重ね合わせしていたViewを削除する
		wm.removeView(view);
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
}