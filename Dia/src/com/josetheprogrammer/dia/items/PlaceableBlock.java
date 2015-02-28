package com.josetheprogrammer.dia.items;

import java.awt.Image;

import com.josetheprogrammer.dia.blocks.BlockType;
import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Placeable;
import com.josetheprogrammer.dia.gameObjects.Stage;

public class PlaceableBlock extends Item implements EditibleItem {

	private Placeable item;
	private Placeable placeable;
	private BlockType blockType;
	private boolean tileset;
	private String name;
	@SuppressWarnings("unused")
	private Stage stage;

	public PlaceableBlock(BlockType blockType, String blockName,
			boolean tileset, Stage stage) {
		super(stage);
		this.blockType = blockType;
		this.name = blockName;
		this.stage = stage;
		this.tileset = tileset;
		this.item = Creator.createBlock(blockType, name, tileset, stage);
	}

	public void generatePlaceable(Stage stage, int x, int y) {
		placeable = Creator.createBlock(blockType, name, tileset, stage);
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
