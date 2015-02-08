package com.josetheprogrammer.dia.items;

import java.awt.Point;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.view.Resources;

/**
 * Sword item used for melee to defeat mobs
 * 
 * @author peperivera
 * 
 */
public class SwordItem extends Item {
	private ImageIcon swordRight;
	private ImageIcon swordLeft;
	private ImageIcon jabRight;
	private ImageIcon jabLeft;

	private int actionCount;
	private int maxActionCount;
	private int attackPower;

	private ItemAction action;
	private ItemType type;
	private Point point;
	private Player player;

	public SwordItem(Player player) {
		super();
		this.player = player;
		type = ItemType.SWORD;
		this.point = new Point();
		action = ItemAction.NONE;
		attackPower = 2;

		actionCount = 0;
		maxActionCount = 25;
		
		this.setItemName("sword");

	}

	@Override
	public ItemType getItemType() {
		return type;
	}

	@Override
	public Point getPoint() {
		return point;
	}

	@Override
	public ImageIcon getSprite() {
		return swordRight;
	}

	@Override
	public int getX() {
		return point.x + 6;
	}

	@Override
	public int getY() {
		return point.y - 6;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the sprite depending on the state of the player and item
	 */
	@Override
	public ImageIcon getEquippedSprite() {
		if (action == ItemAction.USE
				&& player.getAction() == Direction.FACE_RIGHT) {
			actionCount++;
			if (actionCount > maxActionCount) {
				actionCount = 0;
				action = ItemAction.NONE;
			}
			return jabRight;
		} else if (action == ItemAction.USE
				&& player.getAction() == Direction.FACE_LEFT) {
			actionCount++;
			if (actionCount > maxActionCount) {
				actionCount = 0;
				action = ItemAction.NONE;
			}
			return jabLeft;
		} else if (player.getAction() == Direction.FACE_RIGHT) {
			return swordRight;
		} else {
			return swordLeft;
		}
	}

	/**
	 * Gets the sprite for when the item is in the inventory
	 */
	@Override
	public ImageIcon getInventorySprite() {
		return swordRight;
	}

	/**
	 * Gets the X coordinate for the item when its equipped on a player
	 */
	@Override
	public int getEquippedX() {
		return player.getX() + this.getEquippedXOffset();
	}

	/**
	 * Gets the Y coordinate for the item when its equipped on a player
	 */
	@Override
	public int getEquippedY() {
		return player.getY() + this.getEquippedYOffset();
	}

	/**
	 * Called when the item is used normally
	 */
	@Override
	public void useItem() {
		action = ItemAction.USE;
		if (player.getAction() == Direction.FACE_LEFT)
			hitEnemy(getEquippedX(), getEquippedY() + 8);
		else
			hitEnemy(getEquippedX() + 24, getEquippedY() + 8);
	}

	/**
	 * Try to hit an enemy, return whether or not we dealt damage to one
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean hitEnemy(int x, int y) {
		boolean damaged = false;
		for (Mob mob : player.getStage().getMobs()) {
			if (!mob.isDead() && mob.contained(x, y)) {
				damaged = true;
				mob.takeDamage(attackPower);
				break;
			}
		}
		return damaged;
	}

	/**
	 * This item has no alternate use
	 */
	@Override
	public void altUseItem() {

	}

	@Override
	public int getEquippedXOffset() {
		if (player.getAction() == Direction.FACE_RIGHT)
			return 18;
		else
			return -10;
	}

	@Override
	public int getEquippedYOffset() {
		return 0;
	}
	
	@Override
	public void setItemName(String itemName){
		this.setItemName(itemName);
		swordRight = Resources.getImage(itemName + "_right.gif");
		swordLeft = Resources.getImage(itemName + "_left.gif");
		jabRight = Resources.getImage(itemName + "_swing_right.gif");
		jabLeft = Resources.getImage(itemName + "_swing_left.gif");
	}

}
