package com.example.memoinanywhere;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.memoinanywhere.view.MemoView;

public class CanvasActivity extends Activity {

	ArrayList<EditText> editArray;
	private MemoView memoView;
	private FrameLayout rootFrame;

	private int WC = FrameLayout.LayoutParams.WRAP_CONTENT;
	private int MP = FrameLayout.LayoutParams.MATCH_PARENT;


	public static boolean isCanvasView;




	/* (非 Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//ActionBarの非表示
		ActionBar actionBar = getActionBar();
		actionBar.hide();

		setContentView(R.layout.activity_cange_layer);

		rootFrame = (FrameLayout) findViewById(R.id.frame_container);

		memoView = new MemoView(this);
		FrameLayout.LayoutParams pm = new FrameLayout.LayoutParams(MP, MP);
		pm.gravity = Gravity.TOP&Gravity.LEFT;
		rootFrame.addView(memoView, pm);

drawingView = new View(this);
		FrameLayout.LayoutParams pm2 = new FrameLayout.LayoutParams(WC, WC);
		pm.gravity = Gravity.TOP&Gravity.LEFT;
		rootFrame.addView(drawingView, pm2);


		Button btn1 = (Button) findViewById(R.id.button_del_view);
		btn1.setOnClickListener(onClickDelViewButton);
		Button btn2 = (Button) findViewById(R.id.button_layer_change);
		btn2.setOnClickListener(onClickLayerChangeButton);
		Button btn3 = (Button) findViewById(R.id.button_event_change);
		btn3.setOnClickListener(onClickeventChangeButton);


		drawingView.setOnTouchListener(onTouchFrame );

		if (rootFrame.getChildAt(rootFrame.getChildCount()-1) == memoView) {
			isCanvasView = true;
		}

	}

	private OnTouchListener onTouchFrame = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (isCanvasView) {
				return false;
			}
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
			}
			return true;
		}
	};






	private OnClickListener onClickDelViewButton = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (memoView.getChildCount() != 0) {
				memoView.removeViewAt(0);
			}
		}
	};

	/* (非 Javadoc)
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */




	/*レイヤーのタッチイベントの切り替えについて
	 *
	 * ーonTouchEventの取得権の順番としては、Activity→上のview→下のviewになる
	 *
	 * ーonTouchEventのreturn値：
	 * 		returnの値がtrue場合：次のMotionEventへ進むか待機（動かした場合はMotion.EVENT_MOVE：指を離した場合はMotion.EVENT_UPになる）
	 * 		returnの値がfalse場合：次のMotionEventへは進まない（動かしても、指を離しても、EVENT_DOWN以外のイベントは取れない。もう一度タッチして次のonTouchEventにいかないといけない）
	 *
	 * 今回は、下の階層のViewのonTouchEventに移動してもらいたいので、
	 * ActivityのonTouchEventは常にfalseを返すようにする（ぶっちゃけいらない）
	 * 			!!!このonTouchEvent、はっきり言っていらないけど、このActivityのonTouchEventの処理中のタイミングで、各Viewのクラスに変数とか渡したい場合とかには有効だし
	 * 				このonTouchEventがあると、個人的に流れが把握し易いので、最終版までは一応残しておく
	 *
	 * ー下層Viewについて
	 * 初期の順番としては、
	 * 		上：CanvasView
	 * 		下：View							となっている
	 *この状態で上下のどっちのonTouchEventをとりたいのかを決めるために、Booleanでフラグを立てる必要がある（方法その１として。・・・他はしらない）
	 *今回はBoolean isCanvasView という変数を設定した。
	 *
	 *[手順]
	 *ーCanvasViewのonTouchEventを起動したい場合：
	 *		そのまま処理すれば良い
	 *
	 *ーViewのonTouchEventを起動したい場合：
	 *		もしも、isCanvasViewがfalseなら、onTouchEvent にfalseを返す（そうすることで、次のonTouchEventに移る）
	 *
	 *レイヤーの上下関係が逆になったときのことを考えて、対象のView全てにフラグの処理を記述しておく
	 *（コードは順次処理されていくので、最初にフラグの処理を記述しないとだめ(処理をきちっとifとelseで分けるならその限りではない)）
	 *
	 *
	 *
	 * */

	//一応不要コード[そのうち削除]
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (isCanvasView) {
			memoView.onTouchEvent(event);
		} else {
			drawingView.onTouchEvent(event);
		}
		return false;
	}

	//レイアウトの切り替え
	private OnClickListener onClickLayerChangeButton = new OnClickListener() {

		@Override
		public void onClick(View v) {

			View view[] = new View[2];
			for (int j = 0; j < view.length; j++) {
				view[j] = rootFrame.getChildAt(j);
			}
			rootFrame.removeAllViews();
			for (int i = view.length-1; i >= 0; i--) {
				rootFrame.addView(view[i]);
			}


		}
	};
	private OnClickListener onClickeventChangeButton = new OnClickListener() {

		@Override
		public void onClick(View v) {
			isCanvasView = !isCanvasView;

		}
	};
	private View drawingView;





}
