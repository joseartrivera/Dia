package com.josetheprogrammer.dia.items;

import java.awt.Image;

import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Placeable;
import com.josetheprogrammer.dia.gameObjects.Stage;

public class PlaceableItem extends Item implements EditibleItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Placeable item;
	private Placeable placeable;
	private ItemType itemType;
	private Enum<?> type;
	private String itemName;
	@SuppressWarnings("unused")
	private Stage stage;

	public PlaceableItem(ItemType itemType, String itemName, Stage stage, Enum<?> type) {
		super(stage);
		this.itemType = itemType;
		this.itemName = itemName;
		this.stage = stage;
		this.type = type;
		this.item = Creator.createItem(itemType, itemName, stage, type, 0, 0);
	}

	public void generatePlaceable(Stage stage, int x, int y) {
		placeable = this.item = Creator.createItem(itemType, itemName, stage, type, 0, 0);
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


}
