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
public abstract class Item {
	private String itemName;
	
	public Item(){
		itemName = "";
	}
	
	public abstract ItemType getItemType();

	public abstract Point getPoint();

	public abstract ImageIcon getSprite();

	public abstract ImageIcon getEquippedSprite();
	
	public abstract int getEquippedX();

	public abstract int getEquippedY();

	public abstract int getEquippedXOffset();

	public abstract int getEquippedYOffset();

	public abstract ImageIcon getInventorySprite();

	public abstract int getX();

	public abstract int getY();

	public abstract void setPlayer(Player player);

	public abstract void useItem();

	public abstract void altUseItem();
	
	public String getItemName(){
		return itemName;
	}
	
	public void setItemName(String itemName){
		this.itemName = itemName;
	}

}
