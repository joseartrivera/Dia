package com.josetheprogrammer.dia.items;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.particles.ParticleType;
import com.josetheprogrammer.dia.view.Resources;

/**
 * Sword item used for melee to defeat mobs
 * 
 * @author peperivera
 * 
 */
public class SwordItem extends Item {
	private ImageIcon sword;
	private ImageIcon jab;

	private int actionCount;
	private int maxActionCount;
	private int attackPower;

	private ItemAction action;
	private ItemType type;

	public SwordItem(Player player) {
		super(player);
		type = ItemType.SWORD;
		action = ItemAction.NONE;
		attackPower = 2;

		actionCount = 0;
		maxActionCount = 25;
		altCooldown = 80;
		this.setItemName("sword.gif");

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
	public Image getSprite() {
		return sword.getImage();
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
	public Image getEquippedSprite() {
		if (action == ItemAction.USE) {
			actionCount++;
			if (actionCount > maxActionCount) {
				actionCount = 0;
				action = ItemAction.NONE;
			}
			return jab.getImage();
		} else {
			return sword.getImage();
		}
	}

	/**
	 * Gets the sprite for when the item is in the inventory
	 */
	@Override
	public Image getInventorySprite() {
		return sword.getImage();
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
		if (onCooldown())
			return;
		action = ItemAction.USE;
		if (player.getAction() == Direction.FACE_LEFT) {
			hitEnemy(getEquippedX(), getEquippedY() + 8);
			hitBlock(getEquippedX(), getEquippedY() + 8);
		} else {
			hitEnemy(getEquippedX() + 24, getEquippedY() + 8);
			hitBlock(getEquippedX() + 24, getEquippedY() + 8);
		}
		super.useItem();
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

	protected boolean hitBlock(int x, int y) {
		boolean damaged = false;
		Block block = player.getStage().getBlockAt(x, y);
		if (block != null && block.isBreakable()) {
			damaged = true;
			block.damageBlock();
			player.getStage().addParticles(12, ParticleType.DUST, Color.BLACK,
					block.getX() + 16, block.getY() + 16, 0, 0, 12, 12, 1, 1, 10,
					4);
			if (block.getBlockHealth() < 1) {
				player.placeBlock(null, x, y);
			}
		}
		return damaged;
	}

	/**
	 * This item has no alternate use
	 */
	@Override
	public void altUseItem() {
		if (onAltCooldown())
			return;
		player.setBoostDuration(6);
		player.setxBoost(16);
		player.setyBoost(-2);
		super.altUseItem();
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
	public void setItemName(String itemName) {
		super.setItemName(itemName);
		sword = Resources.getImage(itemName);
		jab = Resources.getImage("use_" + itemName);
	}

}
