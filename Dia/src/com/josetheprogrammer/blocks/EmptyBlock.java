package com.josetheprogrammer.blocks;


import java.awt.Image;
import java.awt.Point;

import com.josetheprogrammer.gameObjects.Stage;

/**
 * Represents a dirt block
 * 
 * @author Jose Rivera
 * 
 */

public class EmptyBlock extends Block {

	/**
	 * Creates a new empty block
	 * 
	 * @param stage
	 * @param point
	 */
	public EmptyBlock(Stage stage, Point point) {
		super(stage, point);
		property = BlockProperty.EMPTY;
		type = BlockType.EMPTY;
	}

	/**
	 * Empty blocks are not visible, will not set anything as sprite sheet
	 */
	@Override
	public void setSpriteSheet() {
		return;
	}

	/**
	 * Returns null as an Empty Block is not visible
	 */
	@Override
	public Image getSprite() {
		return null;
	}

}
