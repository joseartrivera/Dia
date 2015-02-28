package com.josetheprogrammer.dia.items;

import java.awt.Image;

import com.josetheprogrammer.dia.gameObjects.Stage;

public interface EditibleItem{
	public void generatePlaceable(Stage stage, int x, int y);
	public int getRemoveX();

	public void setRemoveX(int x);

	public int getRemoveY();

	public void setRemoveY(int y);
	
	public Image getInventorySprite();
}
