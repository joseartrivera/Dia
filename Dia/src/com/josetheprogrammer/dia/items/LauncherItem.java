package com.josetheprogrammer.dia.items;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.projectiles.ProjectileType;
import com.josetheprogrammer.dia.view.Resources;

/**
 * Gun item used for range combat against mobs
 * 
 * @author Jose Rivera
 * 
 */

public class LauncherItem extends Item {
	private ImageIcon launcher;
	private ItemType type;
	private int xSpeed;
	private int ySpeed;
	private ProjectileType projType;

	public LauncherItem(Player player, ProjectileType projType, int xSpeed,
			int ySpeed) {
		super(player);
		type = ItemType.LAUNCHER;
		this.projType = projType;
		this.setItemName("gun.png");
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		cooldown = Creator.getBaseProjectileCooldown(projType) * 10;
	}

	@Override
	public ItemType getItemType() {
		return type;
	}

	@Override
	public Image getSprite() {
		return launcher.getImage();
	}

	/**
	 * Gets the sprite depending on the state of the player and item
	 */
	@Override
	public Image getEquippedSprite() {
		if (player.getAction() == Direction.FACE_RIGHT)
			return launcher.getImage();
		else
			return launcher.getImage();
	}

	/**
	 * Gets the sprite for when the item is in the inventory
	 */
	@Override
	public Image getInventorySprite() {
		return launcher.getImage();
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
		if (onCooldown()) return;
		if (player.getAction() == Direction.FACE_RIGHT) {
			player.getStage().addProjectile(
					Creator.createProjectile(projType, player.getStage(),
							player.getX() + 28, player.getY() + 8, xSpeed,
							ySpeed));
		} else {
			player.getStage().addProjectile(
					Creator.createProjectile(projType, player.getStage(),
							player.getX() - 20, player.getY() + 8, -xSpeed,
							ySpeed));
		}
		
		super.useItem();
	}

	/**
	 * This item has no alternate use
	 */
	@Override
	public void altUseItem() {
		super.altUseItem();
	}

	@Override
	public int getEquippedXOffset() {
		if (player.getAction() == Direction.FACE_RIGHT)
			return 9;
		else
			return -1;
	}

	@Override
	public int getEquippedYOffset() {
		return 8;
	}

	@Override
	public void setItemName(String itemName) {
		super.setItemName(itemName);
		launcher = Resources.getImage(itemName);
	}

}
