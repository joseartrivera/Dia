package com.josetheprogrammer.dia.blocks;


import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.view.Resources;



/**
 * Represents a metal block
 * 
 * @author Jose Rivera
 * 
 */
public class MetalBlock extends Block{
	private ImageIcon spriteSheet;

	/**
	 * Creates a new metal block on the given stage at the given point
	 * 
	 * @param stage
	 * @param point
	 */
	public MetalBlock(Stage stage) {
		super(stage);
		property = BlockProperty.GROUND;
		type = BlockType.METAL;
	}

	/**
	 * Sets the sprite sheet for this block
	 */
	@Override
	public void setSpriteSheet() {
		spriteSheet = Resources.getImage("metalblock.png");
	}
	
	/**
	 * Returns the sprite for this block
	 */
	@Override
	public Image getSprite() {
		return spriteSheet.getImage();
	}

	@Override
	public BufferedImage getSpriteSheet() {
		return null;
	}

	@Override
	public void setSprite(BufferedImage sprite) {
	}

}
