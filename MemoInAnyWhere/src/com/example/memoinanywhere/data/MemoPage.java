package com.example.memoinanywhere.data;


public class MemoPage {

	private String name;
	private Texts texts;
	private Images images;
	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name セットする name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return texts
	 */
	public Texts getTexts() {
		return texts;
	}
	/**
	 * @param texts セットする texts
	 */
	public void setTexts(Texts texts) {
		this.texts = texts;
	}
	/**
	 * @return images
	 */
	public Images getImages() {
		return images;
	}
	/**
	 * @param images セットする images
	 */
	public void setImages(Images images) {
		this.images = images;
	}


}
