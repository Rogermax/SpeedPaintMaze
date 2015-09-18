package com.gmail.rogermoreta.speedpaintmaze.model;

public class Circulo extends Figura {

	private int radio;

	public Circulo(int id, int x, int y, int radio) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.radio = radio;
	}

	public int getRadio() {
		return radio;
	}

	@Override
	public boolean cointainsPoint(int x, int y) {
		return (x-this.x)*(x-this.x)+(y-this.y)*(x-this.y) < radio*radio;
	}
}