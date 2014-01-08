package com.josetheprogrammer.items;


import java.awt.Point;

import javax.swing.ImageIcon;

import com.josetheprogrammer.gameObjects.Direction;
import com.josetheprogrammer.gameObjects.Player;
import com.josetheprogrammer.mobs.Mob;
import com.josetheprogrammer.view.Resources;



/**
 * Sword item used for melee to defeat mobs
 * 
 * @author peperivera
 * 
 */
public class SwordItem implements Item {
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

	public SwordItem(Player player, Point point) {
		this.player = player;
		type = ItemType.SWORD;
		this.point = point;
		action = ItemAction.NONE;
		attackPower = 2;

		actionCount = 0;
		maxActionCount = 25;

		swordRight = Resources.getImage("sword_right.gif");
		swordLeft = Resources.getImage("sword_left.gif");
		jabRight = Resources.getImage("sword_swing_right.gif");
		jabLeft = Resources.getImage("sword_swing_left.gif");

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
		if (player.getAction() == Direction.FACE_RIGHT)
			return player.getX() + 18;
		else
			return player.getX() - 10;
	}

	/**
	 * Gets the Y coordinate for the item when its equipped on a player
	 */
	@Override
	public int getEquippedY() {
		return player.getY();
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

}
