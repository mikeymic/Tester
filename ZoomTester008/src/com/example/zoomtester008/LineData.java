package com.example.zoomtester008;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class LineData {

	List<DrawLine> lines;

	public LineData() {
		lines = new ArrayList<DrawLine>();
	}

	public void addLine(DrawLine drawLine) {
		lines.add(drawLine);
	}

	public int getLineCount() {
		return lines.size();
	}

	public void drawLines(final Canvas canvas) {

		for (int i = 0; i < lines.size(); i++) {
			lines.get(i).drawLine(canvas);
		}
	}

	public void reconstructionDrawLines(float scaleFactor, int basePointIndex, float moveX, float moveY) {
		int count = lines.size();

		for (int i = 0; i < count; i++) {
			Path line = lines.get(i).getLine();
			line.rewind();

			ArrayList<PointF> points = lines.get(i).getLinePoints();
			PointF mp = points.get(1);
			float matrixX = (mp.x * scaleFactor) - ((mp.x * scaleFactor) + mp.x);
			float matrixY = (mp.y * scaleFactor) - ((mp.x * scaleFactor) + mp.x);

			int pCount = points.size();
			for (int j = 0; j < (pCount); j++) {
				float px = points.get(j).x;
				float py = points.get(j).y;

//				float xx = (px * scaleFactor) - ((px * scaleFactor) + moveX);
//				float yy = (py * scaleFactor) - ((py * scaleFactor) + moveY);
				float xx = ((px * scaleFactor) - matrixX) + moveX;
				float yy = ((py * scaleFactor) - matrixY) + moveY;

				if (j == 0) {
					line.moveTo(xx, yy);
				} else if (j == (pCount - 1)) {
					line.setLastPoint(xx, yy);
				} else {
					line.lineTo(xx, yy);
				}
			}
		}
	}

	/**
	 * @return line
	 */
	public DrawLine getDrawLine(int index) {
		return lines.get(index);
	}

	/**
	 * @return line
	 */
	public Path getLine(int index) {
		return lines.get(index).getLine();
	}

	/**
	 * @return line
	 */
	public Paint getPaint(int index) {
		return lines.get(index).getPaint();
	}

	/**
	 * @return lines
	 */
	public List<DrawLine> getLines() {
		return lines;
	}
}
