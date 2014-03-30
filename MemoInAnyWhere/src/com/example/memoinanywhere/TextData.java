package com.example.memoinanywhere;

import java.io.Serializable;

public class TextData implements Serializable {

	public TextData(){

	}

	int height;
	int width;
	String text;
	/**
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height セットする height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width セットする width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text セットする text
	 */
	public void setText(String text) {
		this.text = text;
	}

}
