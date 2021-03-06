package com.josetheprogrammer.dia.gameObjects;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.blocks.NormalBlock;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.particles.Particle;
import com.josetheprogrammer.dia.particles.ParticleType;
import com.josetheprogrammer.dia.projectiles.Projectile;
import com.josetheprogrammer.dia.view.Resources;

/**
 * 
 * Stage represents a level in the game, It holds a 2D array of blocks that
 * represent the layout of the stage.
 * 
 * @author Jose Rivera
 */

public class Stage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stageWidth = 20;
	private int stageHeight = 14;
	public final int BLOCK_SIZE = 32;
	private String stageName;

	// Blocks on the stage
	private Block[][] blocks;
	
	private Block[][] decorations;

	// Items on the stage
	private Item[][] items;

	// Projectiles on the stage
	Vector<Projectile> projectiles;

	// Mobs on the stage
	Vector<Mob> mobs;

	// Particles on stage
	Vector<Particle> particles;

	private Point startPoint;

	private int gravity;

	private Player player;

	// Block determines what blocks are on the outer edge of the stage
	private Block outOfBoundaryBlock;
	private Block emptyBlock;

	// Background
	transient private ImageIcon background;
	private String backgroundName;

	/**
	 * Stage is 640 x 640 pixels, divide pixels by 32 to get the array index
	 */
	public Stage() {

		blocks = new Block[getStageWidth()][getStageHeight()];
		items = new Item[getStageWidth()][getStageHeight()];
		decorations = new Block[getStageWidth()][getStageHeight()];

		outOfBoundaryBlock = new NormalBlock(this);
		outOfBoundaryBlock.setBlockName("");
		outOfBoundaryBlock.setBlockProperty(BlockProperty.EDGE);
		emptyBlock = new NormalBlock(this);
		emptyBlock.setBlockType(null);
		emptyBlock.setBlockProperty(BlockProperty.EMPTY);

		projectiles = new Vector<Projectile>();
		mobs = new Vector<Mob>();
		particles = new Vector<Particle>();

		// Where the player will spawn
		startPoint = new Point(128, 128);

		// Gravity for this stage
		setGravity(3);

		this.setBackground("dungeon.png");
		stageName = "default";
	}

	/**
	 * 
	 * Takes in the points on the screen, finds which array index it matches and
	 * returns that block
	 * 
	 */
	public Block getBlockAt(int x, int y) {
		Block block;
		int i = x / BLOCK_SIZE;
		int j = y / BLOCK_SIZE;

		// Slighty to the left causes integer to divide to 0
		if (x < 0) {
			i = -1;
		}

		if (i < getStageWidth() && i >= 0 && j < getStageHeight() && j >= 0) {
			block = blocks[i][j];
			if (block == null)
				block = emptyBlock;
		} else
			block = outOfBoundaryBlock;
		return block;
	}
	
	public Block getDecorationAt(int x, int y) {
		Block block = null;
		int i = x / BLOCK_SIZE;
		int j = y / BLOCK_SIZE;

		// Slighty to the left causes integer to divide to 0
		if (x < 0) {
			i = -1;
		}

		if (i < getStageWidth() && i >= 0 && j < getStageHeight() && j >= 0) {
			block = decorations[i][j];
		} 
		return block;
	}

	/**
	 * Get an item at a specific part on the screen
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Item getItemAt(int x, int y) {
		Item item = null;
		int i = x / BLOCK_SIZE;
		int j = y / BLOCK_SIZE;
		
		// Slighty to the left causes integer to divide to 0
		if (x < 0) {
			i = -1;
		}

		if (i < getStageWidth() && i >= 0 && j < getStageHeight() && j >= 0) {
			item = items[i][j];
		}
		return item;
	}

	/**
	 * Set a block to this block, takes the exact indices
	 * 
	 * @param block
	 * @param i
	 * @param j
	 */
	public void setBlock(Block block, int i, int j) {
		if (i < getStageWidth() && i >= 0 && j < getStageHeight() && j >= 0) {
			if (block != null) {
				block.setStage(this);
				block.getPoint().setLocation(i * BLOCK_SIZE, j * BLOCK_SIZE);
			}
			blocks[i][j] = block;
		}
	}

	public void placeBlock(Block block, int x, int y) {
		setBlock(block, x / BLOCK_SIZE, y / BLOCK_SIZE);

		Block[][] blocks = getBlocks();
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if (blocks[i][j] != null)
					blocks[i][j].resolveTile();
			}
		}
	}
	
	public void setDecoration(Block block, int i, int j) {
		if (i < getStageWidth() && i >= 0 && j < getStageHeight() && j >= 0) {
			if (block != null) {
				block.setStage(this);
				block.getPoint().setLocation(i * BLOCK_SIZE, j * BLOCK_SIZE);
			}
			decorations[i][j] = block;
		}
	}

	public void placeDecoration(Block block, int x, int y) {
		setDecoration(block, x / BLOCK_SIZE, y / BLOCK_SIZE);

		Block[][] blocks = getBlocks();
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if (decorations[i][j] != null)
					decorations[i][j].resolveTile();
			}
		}
	}

	/**
	 * Sets a position on the screen to this item
	 * 
	 * @param item
	 * @param i
	 * @param j
	 */
	public void setItem(Item item, int i, int j) {
		i = i / BLOCK_SIZE;
		j = j / BLOCK_SIZE;
		if (item != null)
			item.getPoint().setLocation(i * BLOCK_SIZE, j * BLOCK_SIZE + 12);

		items[i][j] = item;
	}

	/**
	 * Sets a block on the 2D array representing this stage to the given item
	 * 
	 * @param item
	 * @param i
	 * @param j
	 */

	public void setItemByIndex(Item item, int i, int j) {
		if (item != null)
			item.getPoint().setLocation(i * BLOCK_SIZE, j * BLOCK_SIZE + 12);
		items[i][j] = item;
	}

	public Block[][] getBlocks() {
		return blocks;
	}
	
	public Block[][] getDecorations(){
		return decorations;
	}

	public Item[][] getItems() {
		return items;
	}

	/**
	 * Returns all projectiles on this stage
	 * 
	 * @return
	 */
	public Vector<Projectile> getProjectiles() {
		return projectiles;
	}

	/**
	 * adds a projectile to this stage
	 * 
	 * @param projectile
	 */
	public void addProjectile(Projectile projectile) {
		synchronized (projectiles) {
			projectiles.add(projectile);
		}
	}

	/**
	 * Updates projectiles, removing dead projectiles and moving alive
	 * projectiles
	 */
	public void updateProjectiles() {
		synchronized (projectiles) {
			Iterator<Projectile> iter = projectiles.iterator();
			while (iter.hasNext()) {
				Projectile projectile = iter.next();
				if (!projectile.isDead())
					projectile.move();
				else
					iter.remove();
			}
		}
	}

	/**
	 * Updates projectiles, removing dead projectiles and moving alive
	 * projectiles
	 */
	public void updateEditModeProjectiles() {
		synchronized (projectiles) {
			Iterator<Projectile> iter = projectiles.iterator();
			while (iter.hasNext()) {
				Projectile projectile = iter.next();
				if (projectile.isDead())
					iter.remove();
			}
		}
	}

	/**
	 * Returns all mobs on this stage
	 * 
	 * @return
	 */
	public Vector<Mob> getMobs() {
		return mobs;
	}

	/**
	 * Adds a mob to this stage
	 * 
	 * @param mob
	 */
	public void addMob(Mob mob) {
		synchronized (mobs) {
			mobs.add(mob);
		}
	}

	/**
	 * Applies gravity and moves each mob on the stage
	 */
	public void updateMobs() {
		synchronized (mobs) {
			Iterator<Mob> iter = mobs.iterator();
			while (iter.hasNext()) {
				Mob mob = iter.next();
				if (!mob.isDead()) {
					mob.applyGravity();
					mob.move();
				} else
					mob.applyGravity();
			}
		}
	}

	public void updateParticles() {
		synchronized (particles) {
			Iterator<Particle> iter = particles.iterator();
			while (iter.hasNext()) {
				Particle particle = iter.next();
				if (particle.isActive())
					particle.move();
				else
					iter.remove();
			}
		}
	}

	public void updateEditModeMobs() {
		synchronized (mobs) {
			Iterator<Mob> iter = mobs.iterator();
			while (iter.hasNext()) {
				Mob mob = iter.next();
				if (mob.isDead()) {
					iter.remove();
				}
			}
		}
	}

	/**
	 * Gets the start point where the player will spawn
	 * 
	 * @return
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Set where the player will spawn
	 * 
	 */
	public void setStartPoint(int x, int y) {
		startPoint.setLocation(x, y);
	}

	public int getGravity() {
		return gravity;
	}

	public void setGravity(int gravity) {
		this.gravity = gravity;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Image getBackground() {
		return background.getImage();
	}

	public void setBackground(String backgroundName) {
		this.backgroundName = backgroundName;
		background = Resources.getImage("backgrounds", backgroundName);
	}

	public int getStageHeight() {
		return stageHeight;
	}

	public int getStageWidth() {
		return stageWidth;
	}

	public void changeStageDimensions(int width, int height) {
		Block[][] newBlocks = new Block[width][height];
		Item[][] newItems = new Item[width][height];
		Block[][] newDecorations = new Block[width][height];

		int copyWidth;
		int copyHeight;

		if (width < getStageWidth())
			copyWidth = width;
		else
			copyWidth = getStageWidth();

		if (height < getStageHeight())
			copyHeight = height;
		else
			copyHeight = getStageHeight();

		// Copy over blocks/items
		for (int i = 0; i < copyWidth; i++) {
			for (int j = 0; j < copyHeight; j++) {
				newBlocks[i][j] = blocks[i][j];
				newItems[i][j] = items[i][j];
				newDecorations[i][j] = decorations[i][j];
			}
		}

		stageWidth = width;
		stageHeight = height;
		blocks = newBlocks;
		items = newItems;
		decorations = newDecorations;
	}


	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public Vector<Particle> getParticles() {
		return particles;
	}

	public boolean editorDelete(int x, int y) {
		synchronized (mobs) {
			Iterator<Mob> iter = mobs.iterator();
			while (iter.hasNext()) {
				Mob mob = iter.next();
				if (mob.contained(x, y)) {
					iter.remove();
					return true;
				}
			}
		}

		Item item = this.getItemAt(x, y);
		if (item != null) {
			item.remove();

			return true;
		}
		
		Block block = this.getDecorationAt(x, y);

		if (block != null) {
			this.placeDecoration(null, x, y);
			return true;
		}

		block = this.getBlockAt(x, y);

		if (block != null) {
			this.placeBlock(null, x, y);
			return true;
		}
		return false;
	}

	public void addParticles(int amount, ParticleType type, Color color, int x,
			int y, int dx, int dy, int xSpread, int ySpread, int dxSpread,
			int dySpread, int duration, int durationSpread) {
		int partXSpread = 0;
		int partYSpread = 0;
		int partdxSpread = 0;
		int partdySpread = 0;
		int partDurationSpread = 0;
		Random rando = new Random();

		synchronized (particles) {
			Particle particle;
			for (int i = 0; i <= amount; i++) {
				if (xSpread > 0)
					partXSpread = (rando.nextInt(xSpread * 2) - xSpread);
				if (ySpread > 0)
					partYSpread = (rando.nextInt(ySpread * 2) - ySpread);
				if (dxSpread > 0)
					partdxSpread = (rando.nextInt(dxSpread * 2) - dxSpread);
				if (dySpread > 0)
					partdySpread = (rando.nextInt(dySpread * 2) - dySpread);
				if (durationSpread > 0)
					partDurationSpread = (rando.nextInt(durationSpread * 2) - durationSpread);
				particle = new Particle(type, x + partXSpread, y + partYSpread,
						dx + partdxSpread, dy + partdySpread, duration
								+ partDurationSpread, color);
				particles.add(particle);
			}
		}

	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		this.setBackground(backgroundName);
		outOfBoundaryBlock.setBlockProperty(BlockProperty.EDGE);
	}
}
