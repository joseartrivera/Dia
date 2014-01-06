package blocks;

import gameObjects.Stage;

import java.awt.Image;
import java.awt.Point;

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
	
	/**
	 * Block represents a solid wall, floor or roof that a player will interact with.
	 * @param stage 
	 * @param point
	 */
	public Block(Stage stage, Point point){
		this.point = point;
		setSpriteSheet();
		property = BlockProperty.GROUND;
		this.stage = stage;
	}
	
	/**
	 * Sets the sprite sheet of this block.
	 */
	public abstract void setSpriteSheet();
	
	/**
	 * Gets the sprite for this block, may change depending on adjacent blocks and sprite sheet.
	 * @return
	 */
	public abstract Image getSprite();

	/**
	 * Returns the type for this block
	 * @return
	 */
	public BlockType getBlockType(){
		return type;
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

}
