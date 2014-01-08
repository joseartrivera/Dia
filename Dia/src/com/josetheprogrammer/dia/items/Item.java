package com.josetheprogrammer.dia.items;


import java.awt.Point;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.gameObjects.Player;

/**
 * Interface for an item, every item in the game will need these methods
 * 
 * @author Jose Rivera
 * 
 */
public interface Item {
	public ItemType getItemType();

	public Point getPoint();

	public ImageIcon getSprite();

	public ImageIcon getEquippedSprite();

	public int getEquippedX();

	public int getEquippedY();

	public ImageIcon getInventorySprite();

	public int getX();

	public int getY();

	public void setPlayer(Player player);

	public void useItem();

	public void altUseItem();
}
