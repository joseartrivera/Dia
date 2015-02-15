package com.josetheprogrammer.dia.blocks;


import java.awt.Image;
import java.awt.image.BufferedImage;

import com.josetheprogrammer.dia.gameObjects.Stage;

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
	public EmptyBlock(Stage stage) {
		super(stage);
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

	@Override
	public BufferedImage getSpriteSheet() {
		return null;
	}

	@Override
	public void setSprite(BufferedImage sprite) {
	}

}
