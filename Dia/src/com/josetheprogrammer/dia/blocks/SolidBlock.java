package com.josetheprogrammer.dia.blocks;


import java.awt.Point;
import java.awt.image.BufferedImage;

import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.view.Resources;


/**
 * Represents a dirt block
 * 
 * @author Jose Rivera
 * 
 */
public class SolidBlock extends Block {

	// Whether this block's sprite is set
	private boolean set;

	private BufferedImage spriteSheet;
	private BufferedImage image;

	/**
	 * Creates a new dirt block on the given stage at the given point
	 * 
	 * @param stage
	 */
	public SolidBlock(Stage stage) {
		super(stage);
		property = BlockProperty.GROUND;
		type = BlockType.DIRT;
		// Set this to false, need to wait for all adjacent dirt blocks to be
		// set before we determine which sprite to assign to this block
		set = false;
	}

	/**
	 * Sets the sprite sheet for this block
	 */
	@Override
	public void setSpriteSheet() {
		spriteSheet = Resources.getSpriteSheet("dirt_tileset.png");
	}
	
	@Override
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	/**
	 * Returns the sprite for this block depending on adjacent blocks
	 */
	@Override
	public BufferedImage getSprite() {
		// If the tile has been set return image
		if (set)
			return image;
		// Otherwise we can resolve the tile
		else {
			resolveTile();
			set = true;
			return image;
		}

	}
	
	@Override
	public void setSprite(BufferedImage sprite) {
		this.image = sprite;
	}

}
