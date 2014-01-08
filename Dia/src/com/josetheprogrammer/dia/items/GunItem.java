package com.josetheprogrammer.dia.items;


import java.awt.Point;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.projectiles.Bullet;
import com.josetheprogrammer.dia.view.Resources;


/**
 * Gun item used for range combat against mobs
 * 
 * @author Jose Rivera
 * 
 */

public class GunItem implements Item {
	private ImageIcon gunRight;
	private ImageIcon gunLeft;
	private ItemType type;
	private Point point;
	private Player player;

	public GunItem(Player player, Point point) {
		this.player = player;
		type = ItemType.GUN;
		this.point = point;

		gunRight = Resources.getImage("gun_right.png");
		gunLeft = Resources.getImage("gun_left.png");

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
		return gunRight;
	}

	@Override
	public int getX() {
		return point.x;
	}

	@Override
	public int getY() {
		return point.y;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Gets the sprite depending on the state of the player and item
	 */
	@Override
	public ImageIcon getEquippedSprite() {
		if (player.getAction() == Direction.FACE_RIGHT)
			return gunRight;
		else
			return gunLeft;
	}

	/**
	 * Gets the sprite for when the item is in the inventory
	 */
	@Override
	public ImageIcon getInventorySprite() {
		return gunRight;
	}

	/**
	 * Gets the X coordinate for the item when its equipped on a player
	 */
	@Override
	public int getEquippedX() {
		if (player.getAction() == Direction.FACE_RIGHT)
			return player.getX() + 9;
		else
			return player.getX() - 1;
	}

	/**
	 * Gets the Y coordinate for the item when its equipped on a player
	 */
	@Override
	public int getEquippedY() {
		return player.getY() + 8;
	}

	/**
	 * Called when the item is used normally
	 */
	@Override
	public void useItem() {
		int xSpeed;
		int ySpeed;
		int x;
		int y;
		if (player.getAction() == Direction.FACE_RIGHT) {
			xSpeed = 6;
			ySpeed = 0;
			x = player.getX() + 28;
			y = player.getY() + 8;
		} else {
			xSpeed = -6;
			ySpeed = 0;
			x = player.getX() - 20;
			y = player.getY() + 8;
		}
		player.getStage().addProjectile(
				new Bullet(new Point(x, y), player.getStage(), xSpeed, ySpeed));
	}

	/**
	 * This item has no alternate use
	 */
	@Override
	public void altUseItem() {

	}

}
