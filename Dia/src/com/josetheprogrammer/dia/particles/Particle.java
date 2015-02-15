package com.josetheprogrammer.dia.particles;

import java.awt.Color;

public class Particle {
	private ParticleType type;
	private int x;
	private int y;
	private int dx;
	private int dy;
	private Color color;
	private int duration;

	public Particle(ParticleType type, int x, int y, int dx, int dy,
			int duration, Color color) {
		this.setType(type);
		this.setX(x);
		this.setY(y);
		this.setDx(dx);
		this.setDy(dy);
		this.setColor(color);
		this.setDuration(duration);
	}

	public ParticleType getType() {
		return type;
	}

	public void setType(ParticleType type) {
		this.type = type;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDy() {
		return dy;
	}

	public void setDy(int dy) {
		this.dy = dy;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isActive() {
		return duration > 0;
	}

	public void move() {
		x = x + dx;
		y = y + dy;
		duration--;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
