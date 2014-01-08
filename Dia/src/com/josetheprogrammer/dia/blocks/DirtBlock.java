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
public class DirtBlock extends Block {

	// Whether this block's sprite is set
	private boolean set;

	private BufferedImage spriteSheet;
	private BufferedImage image;

	/**
	 * Creates a new dirt block on the given stage at the given point
	 * 
	 * @param stage
	 * @param point
	 */
	public DirtBlock(Stage stage, Point point) {
		super(stage, point);
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

	/**
	 * Determine which sprite to assign to this dirt block depending on adjacent
	 * blocks
	 */
	private void resolveTile() {
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
			image = spriteSheet.getSubimage(0, 0, 32, 32);
		else if (left != type && right != type && up != type & down == type)
			image = spriteSheet.getSubimage(0, 0, 32, 32);
		else if (left != type && right != type && up != type && down != type)
			image = spriteSheet.getSubimage(0, 0, 32, 32);
		// Left is dirt, right is empty, up is empty, down is dirt
		else if (left == type && right != type && up != type && down == type)
			image = spriteSheet.getSubimage(33, 0, 32, 32);
		// Left empty, Right dirt, up empty, down dirt
		else if (left != type && right == type && up != type && down == type)
			image = spriteSheet.getSubimage(66, 0, 32, 32);
		// all empty except right
		else if (left != type && right == type && down != type && up != type)
			image = spriteSheet.getSubimage(99, 0, 32, 32);
		// all empty except left
		else if (left == type && right != type && down != type && up != type)
			image = spriteSheet.getSubimage(132, 0, 32, 32);
		// all types surrounding
		else if (left == type && right == type && down == type && up == type)
			image = spriteSheet.getSubimage(0, 33, 32, 32);
		// all surrounding except bottom
		else if (left == type && right == type && down != type && up == type)
			image = spriteSheet.getSubimage(33, 33, 32, 32);
		// left and top are dirt
		else if (left == type && right != type && down != type && up == type)
			image = spriteSheet.getSubimage(66, 33, 32, 32);
		// right and top are dirt
		else if (left != type && right == type && down != type && up == type)
			image = spriteSheet.getSubimage(99, 33, 32, 32);
		else
			image = spriteSheet.getSubimage(0, 33, 32, 32);

	}

}
