package com.josetheprogrammer.dia.blocks;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import com.josetheprogrammer.dia.gameObjects.Placeable;
import com.josetheprogrammer.dia.gameObjects.Stage;

/**
 * This abstract block class provides methods that all blocks in the game will
 * implement.
 * 
 * @author Jose Rivera
 * 
 */
public abstract class Block implements Placeable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected BlockProperty property;
	protected Point point;
	protected Stage stage;
	protected BlockType type;
	protected String blockName;
	protected Boolean breakable;
	protected int blockHealth;
	protected boolean isTileSet;

	/**
	 * Block represents a solid wall, floor or roof that a player will interact
	 * with.
	 * 
	 * @param stage
	 * @param point
	 */

	public Block(Stage stage) {
		this.point = new Point();
		property = BlockProperty.GROUND;
		this.stage = stage;
		this.blockName = "";
		this.breakable = false;
	}

	/**
	 * Sets the sprite sheet of this block.
	 */
	public abstract void setSpriteSheet();

	/**
	 * Gets the sprite sheet of this block.
	 */
	public abstract BufferedImage getSpriteSheet();

	/**
	 * Gets the sprite for this block, may change depending on adjacent blocks
	 * and sprite sheet.
	 * 
	 * @return
	 */
	public abstract Image getSprite();

	/**
	 * Sets the sprite for this block.
	 * 
	 * @return
	 */
	public abstract void setSprite(BufferedImage sprite);

	/**
	 * Returns the type for this block
	 * 
	 * @return
	 */
	public BlockType getBlockType() {
		return type;
	}

	public void setBlockType(BlockType type) {
		this.type = type;
	}

	/**
	 * Returns the X coordinate for this block on the game screen
	 * 
	 * @return
	 */
	public int getX() {
		return point.x;
	}

	/**
	 * Returns the Y coordinate for this block on the game screen
	 * 
	 * @return
	 */
	public int getY() {
		return point.y;
	}

	public void setX(int x) {
		point.x = x;
	}

	public void setY(int y) {
		point.y = y;
	}

	/**
	 * Returns the Point object for this block
	 * 
	 * @return
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Returns the BlockProperty for this block
	 * 
	 * @return
	 */
	public BlockProperty getBlockProperty() {
		return property;
	}

	public void setBlockProperty(BlockProperty property) {
		this.property = property;
	}

	/**
	 * Determine which sprite to assign to this dirt block depending on adjacent
	 * blocks
	 */
	public void resolveTile() {
		if (getSpriteSheet() != null) {

			Block upBlock = stage.getBlockAt(point.x + 16, point.y - 16);
			Block downBlock = stage.getBlockAt(point.x + 16, point.y + 48);
			Block leftBlock = stage.getBlockAt(point.x - 16, point.y + 16);
			Block rightBlock = stage.getBlockAt(point.x + 48, point.y + 16);
			
			// Get surrounding block names
			String up = upBlock.getBlockName();
			String down = downBlock.getBlockName();
			String left = leftBlock.getBlockName();
			String right = rightBlock.getBlockName();
			
			if (upBlock.getBlockProperty() == BlockProperty.EDGE)
				up = this.getBlockName();
			
			if (downBlock.getBlockProperty() == BlockProperty.EDGE)
				down = this.getBlockName();
			
			if (leftBlock.getBlockProperty() == BlockProperty.EDGE)
				left = this.getBlockName();
			
			if (rightBlock.getBlockProperty() == BlockProperty.EDGE)
				right = this.getBlockName();

			// Left is dirt, right is dirt, up is empty
			if (left.equals(blockName) && right.equals(blockName)
					&& !up.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(0, 0, 32, 32));
			else if (!left.equals(blockName) && !right.equals(blockName)
					&& !up.equals(blockName) & down.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(0, 0, 32, 32));
			else if (!left.equals(blockName) && !right.equals(blockName)
					&& !up.equals(blockName) && !down.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(0, 0, 32, 32));
			// Left is dirt, right is empty, up is empty, down is dirt
			else if (left.equals(blockName) && !right.equals(blockName)
					&& !up.equals(blockName) && down.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(33, 0, 32, 32));
			// Left empty, Right dirt, up empty, down dirt
			else if (!left.equals(blockName) && right.equals(blockName)
					&& !up.equals(blockName) && down.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(66, 0, 32, 32));
			// all empty except right
			else if (!left.equals(blockName) && right.equals(blockName)
					&& !down.equals(blockName) && !up.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(99, 0, 32, 32));
			// all empty except left
			else if (left.equals(blockName) && !right.equals(blockName)
					&& !down.equals(blockName) && !up.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(132, 0, 32, 32));
			// all blockNames surrounding
			else if (left.equals(blockName) && right.equals(blockName)
					&& down.equals(blockName) && up.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(0, 33, 32, 32));
			// all surrounding except bottom
			else if (left.equals(blockName) && right.equals(blockName)
					&& !down.equals(blockName) && up.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(33, 33, 32, 32));
			// left and top are dirt
			else if (left.equals(blockName) && !right.equals(blockName)
					&& !down.equals(blockName) && up.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(66, 33, 32, 32));
			// right and top are dirt
			else if (!left.equals(blockName) && right.equals(blockName)
					&& !down.equals(blockName) && up.equals(blockName))
				setSprite(getSpriteSheet().getSubimage(99, 33, 32, 32));
			else
				setSprite(getSpriteSheet().getSubimage(0, 33, 32, 32));
		}
	}

	public String getBlockName() {
		return blockName;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public boolean isBreakable() {
		return breakable;
	}

	public void setBreakable(Boolean breakable) {
		this.breakable = breakable;
	}

	public int getBlockHealth() {
		return blockHealth;
	}

	public void damageBlock() {
		blockHealth--;
	}

	public void setBlockHealth(int blockHealth) {
		this.blockHealth = blockHealth;
	}

	public abstract void setSprite();

	public void place() {
		if (type == BlockType.DECORATION)
			stage.placeDecoration(this, getX(), getY());
		else
			stage.placeBlock(this, getX(), getY());
	}

	public void remove() {
		stage.placeBlock(null, getX(), getY());
	}

	public boolean isTileSet() {
		return isTileSet;
	}

	public void setTileSet(boolean isTileSet) {
		this.isTileSet = isTileSet;
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		this.setBlockName(blockName);
	}

}
