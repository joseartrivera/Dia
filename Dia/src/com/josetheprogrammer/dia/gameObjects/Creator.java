package com.josetheprogrammer.dia.gameObjects;

import java.awt.Point;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.blocks.BlockType;
import com.josetheprogrammer.dia.blocks.SolidBlock;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.items.ItemType;
import com.josetheprogrammer.dia.items.SwordItem;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.mobs.MobType;
import com.josetheprogrammer.dia.mobs.Slime;
import com.josetheprogrammer.dia.projectiles.Bullet;
import com.josetheprogrammer.dia.projectiles.FireBall;
import com.josetheprogrammer.dia.projectiles.Projectile;
import com.josetheprogrammer.dia.projectiles.ProjectileType;

public class Creator {
	public static Block createBlock(BlockType blockType, String name,
			Stage stage) {
		Block block = null;
		switch (blockType) {
		case SOLID:
			block = new SolidBlock(stage);
			block.setBlockName(name);
			break;
		default:
			break;
		}
		return block;
	}

	public static Item createItem(ItemType itemType, String itemName,
			Stage stage) {
		Item item = null;
		switch (itemType) {
		case SWORD:
			item = new SwordItem(stage.getPlayer());
			item.setItemName(itemName);
			break;
		default:
			break;
		}
		return item;
	}

	public static Mob createMob(MobType mobType, String mobName, Stage stage,
			int x, int y) {
		Mob mob = null;
		switch (mobType) {
		case Slime:
			mob = new Slime(stage, new Point(x, y));
			mob.setType(mobType);
			mob.setMobName(mobName);
			break;
		default:
			break;
		}
		return mob;
	}

	public static Projectile createProjectile(ProjectileType projType,
			Stage stage, int x, int y, int xSpeed, int ySpeed) {
		Projectile proj = null;
		switch (projType) {
		case Bullet:
			proj = new Bullet(stage, x, y, xSpeed, ySpeed);
			break;
		case FireBall:
			proj = new FireBall(stage, x, y, xSpeed, ySpeed);
			break;
		default:
			break;
		}
		return proj;
	}
}
