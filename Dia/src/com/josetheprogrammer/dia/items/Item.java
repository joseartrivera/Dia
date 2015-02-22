package com.josetheprogrammer.dia.items;

import java.awt.Image;
import java.awt.Point;

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
	protected int cooldown;
	protected int altCooldown;
	protected int currentCooldown;
	protected int currentAltCooldown;
	protected int ammo;
	protected final String FOLDER = "items";

	public Item(Player player) {
		this.player = player;
		this.point = new Point();
		itemName = "";
		cooldown = 0;
		altCooldown = 0;
		ammo = 0;
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

	public void useItem() {
		if (!onCooldown())
			currentCooldown = cooldown;
	}

	public void altUseItem() {
		if (!onAltCooldown())
			currentAltCooldown = altCooldown;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getCooldown() {
		return cooldown;
	}

	public int getAltCooldown() {
		return altCooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	public void setaltCooldown(int altCooldown) {
		this.altCooldown = altCooldown;
	}

	public boolean onCooldown() {
		return currentCooldown > 0;
	}

	public int getCurrentCooldown() {
		return currentCooldown;
	}

	public int getAltCurrentCooldown() {
		return currentAltCooldown;
	}

	public boolean onAltCooldown() {
		return currentAltCooldown > 0;
	}

	public void updateCooldowns() {
		if (onCooldown())
			currentCooldown--;
		if (onAltCooldown())
			currentAltCooldown--;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

}
