package com.josetheprogrammer.dia.items;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;

import com.josetheprogrammer.dia.gameObjects.Placeable;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.gameObjects.Stage;

/**
 * Interface for an item, every item in the game will need these methods
 * 
 * @author Jose Rivera
 * 
 */
public abstract class Item implements Placeable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String itemName;
	protected Point point;
	protected Stage stage;
	protected Player player;
	protected int cooldown;
	protected int altCooldown;
	protected int currentCooldown;
	protected int currentAltCooldown;
	protected int ammo;
	protected final String FOLDER = "items";

	public Item(Stage stage) {
		this.player = stage.getPlayer();
		this.point = new Point();
		itemName = "";
		cooldown = 0;
		altCooldown = 0;
		ammo = 0;
		this.stage = stage;
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

	public void setX(int x) {
		point.x = x;
	}

	public void setY(int y) {
		point.y = y;
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

	private void updateCooldowns() {
		if (onCooldown())
			currentCooldown--;
		if (onAltCooldown())
			currentAltCooldown--;
	}

	public boolean drawRotated() {
		return false;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public void place() {
		getStage().setItem(this, getX(), getY());
	}

	public void remove() {
		getStage().setItem(null, getX(), getY());
	}

	public Stage getStage() {
		return stage;
	}

	public void update() {
		updateCooldowns();
		return;
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		this.setItemName(itemName);
	}

}
