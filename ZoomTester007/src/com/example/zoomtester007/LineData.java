package com.example.zoomtester007;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

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
