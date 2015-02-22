package com.josetheprogrammer.dia.gameObjects;

import java.awt.Image;

public interface Placeable {
	public Image getSprite();

	public int getX();

	public int getY();
	
	public void setX(int x);
	
	public void setY(int y);

	public void place();
	
	public void remove();
}
