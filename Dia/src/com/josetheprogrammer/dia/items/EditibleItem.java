package com.josetheprogrammer.dia.items;

import java.awt.Image;

import com.josetheprogrammer.dia.gameObjects.Stage;

public interface EditibleItem{
	public void generatePlaceable(Stage stage, int x, int y);
	
	public Image getInventorySprite();
}
