package com.example.serializedatasample;

import java.io.Serializable;

public class Node implements Serializable{

	private int id;
	private float x;
	private float y;
	
	private static final long serialVersionUID = 6255752248513019027L;

	public Node(int id, float x, float y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	public Node() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}






}
