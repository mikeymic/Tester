package com.example.memoinanywhere.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.memoinanywhere.CanvasActivity;

public class MemoView extends FrameLayout {

	int touchX;
	int touchY;

	public MemoView(Context context) {
		super(context);
		// setBackgroundColor(Color.GREEN);
	}

	public MemoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MemoView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see android.view.View#onLayout(boolean changed, int l, int t, int r, int
	 * b)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// padding分を差し引いて子ビュー用の領域を求める
		int winWidth = (r - l) - (getPaddingLeft() - getPaddingRight());
		int winHeight = (b - t) - (getPaddingTop() - getPaddingBottom());

		int width = winWidth - 50;
		int height = winHeight - 100;

		int childCount = getChildCount(); // 子ビューの数を取得

		/*--------------------<< <子ビューの配置> >>--------------------
		 *  レイアウトの方法についてはまた考える ！！！！このままだと絶対にだめ */

		// 子ビューが親ビューに1つ以上ある場合は、子ビューを描画する
		if (childCount != 0) {
			View child = getChildAt(childCount - 1);

			int left = (int) child.getX();
			int top = (int) child.getY();
			int right = left + child.getMeasuredWidth();
			int bottom = top + child.getMeasuredHeight();

			if (top > height - child.getMeasuredWidth()) {
//				bottom = height + 20;
			}
			if (right > width) {
				right = width + 20;
			}
			child.layout(left, top, right, bottom); // 子ビューの描画
		}
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// このViewGroupに指定されているレイアウトのモードを取得
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		/*
		 * レイアウトのモード EXACTLY[match_parent]：ViewGourpから子ビューの大きさを指定したい場合
		 * AT_MOST[wrap_content]：子ビューの大きさを子ビュー自身で決めてもらいたい場合
		 * UNSPECIFIED：ViewGourpのサイズに関係なく、子ビューを任意のサイズに指定しても良い場合
		 * !たとえば、toLeftOfとtoRightOfの両方が指定された場合などは、widthに指定された値は無視される
		 */
		if (widthMode != MeasureSpec.EXACTLY
				|| heightMode != MeasureSpec.EXACTLY) {
			// レイアウトモード今回はこれ
			throw new IllegalStateException("親レイアウトの設置モードを絶対指定にしてください");
			// int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(size,
			// MeasureSpec.EXACTLY);
		}

		// このViewGroupに割り当てられているサイズを取得する
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heidhtSize = MeasureSpec.getSize(heightMeasureSpec);

		// このViewGroupのサイズのセットする
		setMeasuredDimension(widthSize, heidhtSize);

		// // padding分を差し引いて親の幅と高さを求める
		// int width = widthSize - getPaddingLeft() - getPaddingRight();
		// int height = heidhtSize - getPaddingTop() - getPaddingBottom();

		// boolean isVertical = height > width;

	}

	/*
	 * (非 Javadoc)
	 *
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (!CanvasActivity.isCanvasView) {
			return false;
		}

		CustomEditText edit = new CustomEditText(getContext());
		touchX = (int) event.getX();
		touchY = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(
					FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			addView(edit, param);
			edit.setLeft(touchX);
			edit.setTop(touchY);
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			break;
		}
		return true;
	}
}
