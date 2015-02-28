package com.josetheprogrammer.dia.items;

import java.awt.Image;

import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Placeable;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.mobs.MobType;

public class PlaceableMob extends Item implements EditibleItem {

	private Placeable item;
	private Placeable placeable;
	private MobType mobType;
	private String name;
	@SuppressWarnings("unused")
	private Stage stage;

	public PlaceableMob(MobType mobType, String name, Stage stage) {
		super(stage);
		this.mobType = mobType;
		this.name = name;
		this.stage = stage;
		this.item = Creator.createMob(mobType, name, stage, 0, 0);
	}

	public void generatePlaceable(Stage stage, int x, int y) {
		this.placeable = Creator.createMob(mobType, name, stage, x, y);
		placeable.setX(x);
		placeable.setY(y);
	}

	@Override
	public void useItem() {
		placeable.place();
	}

	@Override
	public void altUseItem() {
		item.remove();
	}

	@Override
	public ItemType getItemType() {
		return null;
	}

	@Override
	public Image getSprite() {
		return null;
	}

	@Override
	public Image getEquippedSprite() {
		return null;
	}

	@Override
	public int getEquippedX() {
		return 0;
	}

	@Override
	public int getEquippedY() {
		return 0;
	}

	@Override
	public int getEquippedXOffset() {
		return 0;
	}

	@Override
	public int getEquippedYOffset() {
		return 0;
	}

	@Override
	public Image getInventorySprite() {
		return item.getSprite();
	}

	public int getRemoveX() {
		return item.getX();
	}

	public void setRemoveX(int x) {
		item.setX(x);
	}

	public int getRemoveY() {
		return item.getY();
	}

	public void setRemoveY(int y) {
		item.setY(y);
	}

}
