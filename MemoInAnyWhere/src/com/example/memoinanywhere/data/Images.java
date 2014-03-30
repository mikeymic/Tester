package com.example.memoinanywhere.data;

import java.util.ArrayList;

import android.widget.ImageView;

public class Images extends ArrayList<ImageView>{

	private int id;
	private int x;
	private int y;

	/**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param id セットする id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param x セットする x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @param y セットする y
	 */
	public void setY(int y) {
		this.y = y;
	}

}
