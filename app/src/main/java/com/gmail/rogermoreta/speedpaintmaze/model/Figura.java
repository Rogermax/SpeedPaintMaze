package com.gmail.rogermoreta.speedpaintmaze.model;

public abstract class Figura {

	protected int id;
	protected int x;
	protected int y;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getId() {
		return id;
	}

	public abstract boolean cointainsPoint(int x, int y);
}