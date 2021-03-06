package com.josetheprogrammer.dia.gameObjects;

import java.awt.Point;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.blocks.BlockType;
import com.josetheprogrammer.dia.blocks.NormalBlock;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.items.ItemType;
import com.josetheprogrammer.dia.items.LauncherItem;
import com.josetheprogrammer.dia.items.MeleeDashItem;
import com.josetheprogrammer.dia.items.MeleeItem;
import com.josetheprogrammer.dia.items.MeleeTossItem;
import com.josetheprogrammer.dia.mobs.CrawlMob;
import com.josetheprogrammer.dia.mobs.FireBreatherMob;
import com.josetheprogrammer.dia.mobs.FlyingMob;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.mobs.MobType;
import com.josetheprogrammer.dia.mobs.BasicMob;
import com.josetheprogrammer.dia.projectiles.Bullet;
import com.josetheprogrammer.dia.projectiles.FireBall;
import com.josetheprogrammer.dia.projectiles.Projectile;
import com.josetheprogrammer.dia.projectiles.ProjectileType;

public class Creator {
	public static Block createBlock(BlockType blockType, String name,
			boolean tileset, Stage stage) {
		Block block = null;
		switch (blockType) {
		case SOLID:
			block = new NormalBlock(stage);
			block.setTileSet(tileset);
			block.setBlockName(name);
			break;
		case DECORATION:
			block = new NormalBlock(stage);
			block.setBlockType(blockType);
			block.setTileSet(tileset);
			block.setBlockName(name);
			break;
		default:
			break;
		}
		return block;
	}

	public static Item createDefaultItem(String itemName, Stage stage) {
		Item item = null;

		if (itemName.equals("fireball.gif")) {
			item = createItem(ItemType.LAUNCHER, itemName, stage,
					ProjectileType.FireBall, 4, -6);
		} else if (itemName.equals("gun.png")) {
			item = createItem(ItemType.LAUNCHER, itemName, stage,
					ProjectileType.Bullet, 8, 0);
		} else if (itemName.equals("spear.png")) {
			item = createItem(ItemType.TOSS, itemName, stage, null, 0, 0);
		} else if (itemName.equals("sword.gif")) {
			item = createItem(ItemType.SWORD, itemName, stage, null, 0, 0);
		} else if (itemName.equals("Dobble_axe.png")) {
			item = createItem(ItemType.DASH, itemName, stage, null, 0, 0);
		}

		return item;
	}

	public static Item createItem(ItemType itemType, String itemName,
			Stage stage, Enum<?> type, int xSpeed, int ySpeed) {
		Item item = null;
		switch (itemType) {
		case SWORD:
			item = new MeleeItem(stage);
			item.setItemName(itemName);
			break;
		case LAUNCHER:
			item = new LauncherItem(stage, (ProjectileType) type, xSpeed,
					ySpeed);
			item.setItemName(itemName);
			break;
		case TOSS:
			item = new MeleeTossItem(stage);
			item.setItemName(itemName);
			break;
		case DASH:
			item = new MeleeDashItem(stage);
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
		case SLIME:
			mob = new BasicMob(stage, new Point(x, y));
			mob.setType(mobType);
			mob.setMobName(mobName);
			break;
		case HOLLOW:
			mob = new FlyingMob(stage, new Point(x, y));
			mob.setType(mobType);
			mob.setMobName(mobName);
			break;
		case SPIDER:
			mob = new CrawlMob(stage, new Point(x, y));
			mob.setType(mobType);
			mob.setMobName(mobName);
		case REDSPIDER:
			mob = new CrawlMob(stage, new Point(x, y));
			mob.setType(mobType);
			mob.setSpeed(2);
			mob.setMobName(mobName);
			break;
		case MOLTEN_GOLEM:
			mob = new FireBreatherMob(stage, new Point(x, y));
			mob.setType(mobType);
			mob.setSpeed(2);
			mob.setMobName(mobName);
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

	public static int getBaseProjectileCooldown(ProjectileType projType) {
		switch (projType) {
		case Bullet:
			return 0;
		case FireBall:
			return 2;
		default:
			return 0;
		}
	}
}
