package com.josetheprogrammer.dia.items;


import java.awt.Image;
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
	protected String itemName;
	protected Point point;
	protected Player player;
	
	public Item(Player player){
		this.player = player;
		this.point = new Point();
		itemName = "";
	}
	
	public abstract ItemType getItemType();

	public Point getPoint() {
		return point;
	}

	public abstract Image getSprite();

	public abstract Image getEquippedSprite();
	
	public abstract int getEquippedX();

	public abstract int getEquippedY();

	public abstract int getEquippedXOffset();

	public abstract int getEquippedYOffset();

	public abstract Image getInventorySprite();

	public int getX() {
		return point.x;
	}

	public int getY() {
		return point.y;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public abstract void useItem();

	public abstract void altUseItem();
	
	public String getItemName(){
		return itemName;
	}
	
	public void setItemName(String itemName){
		this.itemName = itemName;
	}

}
