package com.example.canvasmemo;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private CanvasView canvasView;
	private DrawerLayout drawer;
	private ActionBarDrawerToggle drawerToggle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		canvasView = new CanvasView(this); // 描画用クラス
//		setContentView(canvasView);


		setContentView(R.layout.activity_canvas_drawer);

		canvasView = (CanvasView) findViewById(R.id.left_draw);
//		canvasView.getRootView().setBackgroundColor(Color.argb(00, 255, 255, 255));

		createNotification();
		createDrawer();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_undo:
			break;

		case R.id.action_start_overlay:
			OverlayManager.onUpdateMeasureSize(this); // Window, View ActionBar, // StatusBarのサイズを取得
			OverlayManager.takeCaptureThenAddToOverlay(canvasView); // スクリーンショットを取り、オーバーレイ用のbitmapに合成
			byte[] stream = OverlayManager.createOverlayBuffer(); // オーバーレイ用のbitmapをバッファに変換

			Intent intent = new Intent(this, LayerService.class); // サービスにバッファを送るためにIntentを作成
			intent.putExtra(OverlayManager.overlayName, stream); // バッファをIntentに付属させる
			startService(intent); // バッファを送り、サービスの起動
			break;

		case R.id.action_end_overlay:
			stopService(new Intent(MainActivity.this, LayerService.class)); // サービスを停止
			break;

		default:
			break;
		}
		return true;
	}

	public void createNotification() {

		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0,
				intent, 0);

		// Notificationマネージャのインスタンスを取得
		NotificationManager mgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		// NotificationBuilderのインスタンスを作成
		Notification.Builder builder = new Notification.Builder(
				getApplicationContext());
		builder.setContentIntent(pi).setTicker("テキスト")// ステータスバーに表示されるテキスト
				.setSmallIcon(R.drawable.ic_launcher)// アイコン
				.setContentTitle("タイトル")// Notificationが開いたとき
				.setContentText("メッセージ")// Notificationが開いたとき
				.setWhen(System.currentTimeMillis())// 通知するタイミング
				.setPriority(Integer.MAX_VALUE);
		Notification notification = builder.build();
		notification.flags = Notification.FLAG_NO_CLEAR
				| Notification.FLAG_ONGOING_EVENT;
		mgr.notify(1, notification);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	public void createDrawer() {

		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerToggle = new ActionBarDrawerToggle(this, drawer,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			@Override
			public void onConfigurationChanged(Configuration newConfig) {
				super.onConfigurationChanged(newConfig);
				drawerToggle.onConfigurationChanged(newConfig);
			}

			@Override
			public boolean onOptionsItemSelected(MenuItem item) {
				if (drawerToggle.onOptionsItemSelected(item)) {
					return true;
				}
				return super.onOptionsItemSelected(item);
			}
		};


		drawer.setDrawerListener(drawerToggle);

		// UpNavigationアイコン(アイコン横の<の部分)を有効に
		// NavigationDrawerではR.drawable.drawerで上書き
		getActionBar().setDisplayHomeAsUpEnabled(true);
		// UpNavigationを有効に
		getActionBar().setHomeButtonEnabled(true);


		Button button = (Button) findViewById(R.id.drawer_button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				drawer.closeDrawers();

			}
		});

	}


}
