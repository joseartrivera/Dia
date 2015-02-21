package com.josetheprogrammer.dia.blocks;


import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.josetheprogrammer.dia.gameObjects.Stage;

/**
 * This abstract block class provides methods that all blocks in the game will implement.
 * @author Jose Rivera
 *
 */
public abstract class Block{
	protected BlockProperty property;
	protected Point point;
	protected Stage stage;
	protected BlockType type;
	protected String blockName;
	protected Boolean breakable;
	protected int blockHealth;
	
	/**
	 * Block represents a solid wall, floor or roof that a player will interact with.
	 * @param stage 
	 * @param point
	 */
	
	public Block(Stage stage){
		this.point = new Point();
		property = BlockProperty.GROUND;
		this.stage = stage;
		this.blockName = "";
		this.breakable = false;
	}
	
	public Block(){
		this.point = new Point();
		property = BlockProperty.GROUND;
		this.stage = null;
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
	 * Gets the sprite for this block, may change depending on adjacent blocks and sprite sheet.
	 * @return
	 */
	public abstract Image getSprite();
	
	/**
	 * Sets the sprite for this block.
	 * @return
	 */
	public abstract void setSprite(BufferedImage sprite);

	/**
	 * Returns the type for this block
	 * @return
	 */
	public BlockType getBlockType(){
		return type;
	}
	
	public void setBlockType(BlockType type){
		this.type = type;
	}
	
	/**
	 * Returns the X coordinate for this block on the game screen
	 * @return
	 */
	public int getX(){
		return point.x;
	}
	
	/**
	 * Returns the Y coordinate for this block on the game screen
	 * @return
	 */
	public int getY(){
		return point.y;
	}
	
	/**
	 * Returns the Point object for this block
	 * @return
	 */
	public Point getPoint(){
		return point;
	}

	/**
	 * Returns the BlockProperty for this block
	 * @return
	 */
	public BlockProperty getBlockProperty() {
		return property;
	}
	
	public void setBlockProperty(BlockProperty property){
		this.property = property;
	}
	
	/**
	 * Determine which sprite to assign to this dirt block depending on adjacent
	 * blocks
	 */
	public void resolveTile() {
		if (getSpriteSheet() != null){
			// Get surrounding block types
			BlockType up = stage.getBlockAt(point.x + 16, point.y - 16)
					.getBlockType();
			BlockType down = stage.getBlockAt(point.x + 16, point.y + 48)
					.getBlockType();
			BlockType left = stage.getBlockAt(point.x - 16, point.y + 16)
					.getBlockType();
			BlockType right = stage.getBlockAt(point.x + 48, point.y + 16)
					.getBlockType();
	
			// Left is dirt, right is dirt, up is empty
			if (left == type && right == type && up != type)
				setSprite(getSpriteSheet().getSubimage(0, 0, 32, 32));
			else if (left != type && right != type && up != type & down == type)
				setSprite(getSpriteSheet().getSubimage(0, 0, 32, 32));
			else if (left != type && right != type && up != type && down != type)
				setSprite(getSpriteSheet().getSubimage(0, 0, 32, 32));
			// Left is dirt, right is empty, up is empty, down is dirt
			else if (left == type && right != type && up != type && down == type)
				setSprite(getSpriteSheet().getSubimage(33, 0, 32, 32));
			// Left empty, Right dirt, up empty, down dirt
			else if (left != type && right == type && up != type && down == type)
				setSprite(getSpriteSheet().getSubimage(66, 0, 32, 32));
			// all empty except right
			else if (left != type && right == type && down != type && up != type)
				setSprite(getSpriteSheet().getSubimage(99, 0, 32, 32));
			// all empty except left
			else if (left == type && right != type && down != type && up != type)
				setSprite(getSpriteSheet().getSubimage(132, 0, 32, 32));
			// all types surrounding
			else if (left == type && right == type && down == type && up == type)
				setSprite(getSpriteSheet().getSubimage(0, 33, 32, 32));
			// all surrounding except bottom
			else if (left == type && right == type && down != type && up == type)
				setSprite(getSpriteSheet().getSubimage(33, 33, 32, 32));
			// left and top are dirt
			else if (left == type && right != type && down != type && up == type)
				setSprite(getSpriteSheet().getSubimage(66, 33, 32, 32));
			// right and top are dirt
			else if (left != type && right == type && down != type && up == type)
				setSprite(getSpriteSheet().getSubimage(99, 33, 32, 32));
			else
				setSprite(getSpriteSheet().getSubimage(0, 33, 32, 32));
		}
	}
	
	public String getBlockName(){
		return blockName;
	}
	
	public void setStage(Stage stage){
		this.stage = stage;
	}
	
	public Stage getStage(){
		return stage;
	}
	
	public void setBlockName(String blockName){
		this.blockName = blockName;
	}
	
	public boolean isBreakable(){
		return breakable;
	}
	
	public void setBreakable(Boolean breakable){
		this.breakable = breakable;
	}
	
	public int getBlockHealth() {
		return blockHealth;
	}
	
	public void damageBlock(){
		blockHealth--;
	}

	public void setBlockHealth(int blockHealth) {
		this.blockHealth = blockHealth;
	}
	
	public abstract void setSprite();

}
