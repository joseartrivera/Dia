package com.josetheprogrammer.dia.blocks;

import java.awt.Image;
import java.awt.image.BufferedImage;

import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.view.Resources;

/**
 * Represents a dirt block
 * 
 * @author Jose Rivera
 * 
 */
public class NormalBlock extends Block {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Whether this block's sprite is set
	transient private boolean set;

	transient private BufferedImage spriteSheet;
	transient private BufferedImage spriteSheetImage;
	transient private Image singleBlockImage;

	/**
	 * Creates a new dirt block on the given stage at the given point
	 * 
	 * @param stage
	 */
	public NormalBlock(Stage stage) {
		super(stage);
		property = BlockProperty.GROUND;
		type = BlockType.SOLID;
		setTileSet(true);
		this.setBlockName("dungeon_tileset.png");
		// Set this to false, need to wait for all adjacent dirt blocks to be
		// set before we determine which sprite to assign to this block
		set = false;
	}

	/**
	 * Sets the sprite sheet for this block
	 */
	@Override
	public void setSpriteSheet() {
		String folder = "tilesets";
		if (type == BlockType.DECORATION)
			folder = "decoration_tilesets";
		spriteSheet = Resources.getSpriteSheet(folder, this.getBlockName());
	}

	@Override
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	/**
	 * Returns the sprite for this block depending on adjacent blocks
	 */
	@Override
	public Image getSprite() {
		if (singleBlockImage != null)
			return singleBlockImage;
		// If the tile has been set return image
		else if (set)
			return spriteSheetImage;
		// Otherwise we can resolve the tile
		else {
			resolveTile();
			set = true;
			return spriteSheetImage;
		}

	}

	@Override
	public void setSprite() {
		String folder = "blocks";
		if (type == BlockType.DECORATION){
			folder = "decoration";
		}
		singleBlockImage = Resources.getImage(folder, blockName).getImage();
	}

	@Override
	public void setBlockName(String blockName) {
		super.setBlockName(blockName);
		if (isTileSet)
			setSpriteSheet();
		else
			setSprite();
	}

	@Override
	public void setSprite(BufferedImage sprite) {
		this.spriteSheetImage = sprite;
	}

}
