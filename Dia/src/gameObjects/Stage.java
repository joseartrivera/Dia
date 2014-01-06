package gameObjects;

import items.Item;

import java.awt.Image;
import java.awt.Point;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;

import mobs.Mob;

import projectiles.Projectile;
import view.Resources;

import blocks.Block;
import blocks.EmptyBlock;
import blocks.MetalBlock;

/**
 * 
 * Stage represents a level in the game, It holds a 2D array of blocks that
 * represent the layout of the stage.
 * 
 * @author Jose Rivera
 */

public class Stage {
	public final int STAGE_WIDTH = 20;
	public final int STAGE_HEIGHT = 15;
	public final int BLOCK_SIZE = 32;

	// Blocks on the stage
	private Block[][] blocks;

	// Items on the stage
	private Item[][] items;

	// Projectiles on the stage
	Vector<Projectile> projectiles;

	// Mobs on the stage
	Vector<Mob> mobs;

	private Point startPoint;

	private int gravity;

	private Player player;

	// Block determines what blocks are on the outer edge of the stage
	private Block outOfBoundaryBlock;

	// Background
	private ImageIcon background;

	/**
	 * Stage is 640 x 640 pixels, divide pixels by 32 to get the array index
	 */
	public Stage() {

		blocks = new Block[STAGE_WIDTH][STAGE_HEIGHT];
		items = new Item[STAGE_WIDTH][STAGE_HEIGHT];

		// Initialize our blocks and items
		for (int i = 0; i < STAGE_WIDTH; i++) {
			for (int j = 0; j < STAGE_HEIGHT; j++) {
				blocks[i][j] = new EmptyBlock(this, new Point(i * BLOCK_SIZE, j
						* BLOCK_SIZE));
				items[i][j] = null;
			}
		}

		outOfBoundaryBlock = new MetalBlock(this, null);

		projectiles = new Vector<Projectile>();
		mobs = new Vector<Mob>();

		// Where the player will spawn
		setStartPoint(new Point(128, 128));

		// Gravity for this stage
		setGravity(3);

		background = Resources.getImage("sky.png");

	}

	/**
	 * 
	 * Takes in the points on the screen, finds which array index it matches and
	 * returns that block
	 * 
	 */
	public Block getBlockAt(int x, int y) {
		int i = x / BLOCK_SIZE;
		int j = y / BLOCK_SIZE;

		// Slighty to the left causes integer to divide to 0
		if (x < 0) {
			i = -1;
		}

		if (i < STAGE_WIDTH && i >= 0 && j < STAGE_HEIGHT && j >= 0)
			return blocks[i][j];
		else
			return outOfBoundaryBlock;
	}

	/**
	 * Get an item at a specific part on the screen
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Item getItemAt(int x, int y) {
		return items[x / BLOCK_SIZE][y / BLOCK_SIZE];
	}

	/**
	 * Set a block to this block, takes the exact indices
	 * 
	 * @param block
	 * @param i
	 * @param j
	 */
	public void setBlock(Block block, int i, int j) {
		block.getPoint().setLocation(i * BLOCK_SIZE, j * BLOCK_SIZE);
		blocks[i][j] = block;
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

	/**
	 * Gets the start point where the player will spawn
	 * @return
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Set where the player will spawn
	 * @param startPoint
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
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

}
