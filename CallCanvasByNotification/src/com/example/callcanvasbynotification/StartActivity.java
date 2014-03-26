package com.example.callcanvasbynotification;


import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	String url = "http://www.google.com";
	Uri uri = Uri.parse(url);
//	新たにアクティビティーを開始するためにPendingIntentを取得する
//	Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	
	Intent intent = new Intent(StartActivity.this, CanvasActivity.class);

	
	PendingIntent pi = PendingIntent.getActivity(StartActivity.this, 0, intent, 0);
	
//	Notificationマネージャのインスタンスを取得
	NotificationManager mgr = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

	
	//	NotificationBuilderのインスタンスを作成
	Notification.Builder builder = new Notification.Builder(getApplicationContext());
	builder
				.setContentIntent(pi)
				.setTicker("テキスト")//ステータスバーに表示されるテキスト
				.setSmallIcon(R.drawable.ic_launcher)//アイコン
				.setContentTitle("タイトル")//Notificationが開いたとき
				.setContentText("メッセージ")//Notificationが開いたとき
				.setWhen(System.currentTimeMillis())//通知するタイミング
				.setPriority(Integer.MAX_VALUE);
	Notification notification = builder.build();
	notification.flags = Notification.FLAG_NO_CLEAR|Notification.FLAG_ONGOING_EVENT;
	
	mgr.notify(1, notification);
	finish();    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.start, menu);
	return true;
    }

}
