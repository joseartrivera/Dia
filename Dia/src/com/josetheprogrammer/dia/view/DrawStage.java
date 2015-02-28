package com.josetheprogrammer.dia.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.gameObjects.PlayerInventory;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.particles.Particle;
import com.josetheprogrammer.dia.projectiles.Projectile;

@SuppressWarnings("serial")
public class DrawStage extends JPanel implements Observer {
	public static DrawStage draw;
	private Game game;
	private int cameraWidth, cameraHeight;
	private int x1, x2, y1, y2; // Camera view

	/**
	 * Constructor for our DrawGame panel, used to draw the game
	 */
	public DrawStage(Game game) {
		this.game = game;
		x1 = 0;
		x2 = 640;
		y1 = 0;
		y2 = 480;
		setup();
		setVisible(true);
	}

	/**
	 * Setup the basics of our panel
	 */
	private void setup() {
		setLayout(null);
		setSize(640, 480);
		cameraWidth = 640 / 2;
		cameraHeight = 480 / 2;
		setLocation(0, 0);

	}

	/**
	 * Game will call this method when the graphics need to be updated
	 */
	@Override
	public void update(Observable observable, Object object) {
		game = (Game) observable;
		this.repaint();
	}

	/**
	 * Paints the game
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		drawStage(g2);
		drawBlocks(g2);
		drawMobs(g2);
		drawItems(g2);
		drawInventory(g2);
		drawProjectiles(g2);
		drawParticles(g2);
		updateCamera(game.getPlayer().getX(), game.getPlayer().getY());
		drawPlayer(g2);
	}

	private void drawParticles(Graphics2D g2) {
		synchronized (game.getStage().getParticles()) {
			for (Particle part : game.getStage().getParticles()) {
				if (inView(part.getX(), part.getY())) {
					g2.setColor(part.getColor());
					g2.drawRect(part.getX() - x1, part.getY() - y1, 1, 1);
				}
			}
		}
	}

	/**
	 * Draws the background for our current stage
	 * 
	 * @param g2
	 */
	private void drawStage(Graphics2D g2) {
		g2.drawImage(game.getStage().getBackground(), 0, 0, this);

	}

	/**
	 * Draws each mob active on the stage
	 * 
	 * @param g2
	 */
	private void drawMobs(Graphics2D g2) {
		synchronized (game.getStage().getMobs()) {
			for (Mob mob : game.getStage().getMobs()) {
				int x = mob.getX();
				int y = mob.getY();
				if (inView(x, y)) {
					Image mobImage = mob.getSprite();
					if (mobImage != null) {
						x = x - x1;
						y = y - y1;
						if (mob.getDirection() == Direction.FACE_RIGHT) {
							g2.drawImage(mobImage, x, y, this);
						} else {
							g2.drawImage(mobImage, x + mobImage.getWidth(this),
									y, x, y + mobImage.getHeight(this), 0, 0,
									mobImage.getWidth(this),
									mobImage.getHeight(this), this);
						}
					}
				}
			}
		}
	}

	/**
	 * Draws each active projectile on the stage
	 * 
	 * @param g2
	 */
	private void drawProjectiles(Graphics2D g2) {
		synchronized (game.getStage().getProjectiles()) {
			for (Projectile projectile : game.getStage().getProjectiles())
				drawImageInView(g2, projectile.getSprite(), projectile.getX(),
						projectile.getY());
		}
	}

	/**
	 * Draws the inventory UI
	 * 
	 * @param g2
	 */
	private void drawInventory(Graphics2D g2) {
		int x = 6;
		int width = 0;
		PlayerInventory inventory = game.getPlayer().getInventory();

		g2.drawImage(inventory.getInventoryLeftImage().getImage(), x, 6, this);
		x += inventory.getInventoryLeftImage().getIconWidth();
		for (int i = 0; i < inventory.getSize(); i++) {

			if (inventory.getSelectedIndex() != i) {
				g2.drawImage(inventory.getSprite(i).getImage(), x, 6, this);
				width = inventory.getSprite(i).getIconWidth();
			} else {
				g2.drawImage(inventory.getSelectedSprite(i).getImage(), x, 6,
						this);
				width = inventory.getSprite(i).getIconWidth();
			}
			if (inventory.getItemAtIndex(i) != null) {
				Item item = inventory.getItemAtIndex(i);
				g2.drawImage(item.getInventorySprite(), x, 6, this);
				if (item.onCooldown()) {
					g2.setColor(Color.BLUE);
					g2.fill3DRect(x, 30, (item.getCurrentCooldown() * 23)
							/ item.getCooldown(), 3, false);

					g2.setColor(Color.BLACK);
					g2.draw3DRect(x, 30, 23, 3, true);
				}
				if (item.onAltCooldown()) {
					g2.setColor(Color.CYAN);
					g2.fill3DRect(x, 33, (item.getAltCurrentCooldown() * 23)
							/ item.getAltCooldown(), 3, false);
					g2.setColor(Color.BLACK);
					g2.draw3DRect(x, 33, 23, 3, true);
				}
			}
			x += width;
		}

		g2.drawImage(inventory.getInventoryRightImage().getImage(), x, 5, this);

		int health = game.getPlayer().getHealth();

		if (health > 65)
			g2.setColor(Color.GREEN);
		else if (health > 30)
			g2.setColor(Color.ORANGE);
		else
			g2.setColor(Color.RED);
		g2.drawImage(game.getPlayer().getHealthbarSprite().getImage(), 6, 40,
				this);
		g2.fill3DRect(10, 43, (82 * game.getPlayer().getHealth())
				/ game.getPlayer().getMaxHealth(), 5, true);

	}

	/**
	 * Draws the active blocks on the stage
	 * 
	 * @param g2
	 */
	private void drawBlocks(Graphics2D g2) {
		Block[][] blocks = game.getStage().getBlocks();
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if (blocks[i][j] != null)
					drawImageInView(g2, blocks[i][j].getSprite(),
							blocks[i][j].getX(), blocks[i][j].getY());
			}
		}
	}

	/**
	 * Draws the active items on the stage
	 * 
	 * @param g2
	 */
	private void drawItems(Graphics2D g2) {
		Item[][] items = game.getStage().getItems();
		for (int i = 0; i < items.length; i++) {
			for (int j = 0; j < items[i].length; j++) {
				if (items[i][j] != null)
					drawImageInView(g2, items[i][j].getSprite(),
							items[i][j].getX(), items[i][j].getY());
			}
		}
	}

	/**
	 * Draws the player object
	 * 
	 * @param g2
	 */
	private void drawPlayer(Graphics2D g2) {
		Player p = game.getPlayer();
		Item equipped = p.getEquippedItem();
		int x = p.getX() - x1;
		int y = p.getY() - y1;
		g2.drawImage(p.getSprite().getImage(), x, y, this);
		
		if (p.getDirection() == Direction.FACE_RIGHT) {
			g2.drawImage(p.getSprite().getImage(), x, y, this);
		} else {
			g2.drawImage(p.getSprite().getImage(), x + p.getSprite().getImage().getWidth(this),
					y, x, y + p.getSprite().getImage().getHeight(this), 0, 0,
					p.getSprite().getImage().getWidth(this),
					p.getSprite().getImage().getHeight(this), this);
		}
		
		if (equipped != null) {
			Image equipImg = equipped.getEquippedSprite();
			x = x + equipped.getEquippedXOffset();
			y = y + equipped.getEquippedYOffset();
			
			
			if (p.getDirection() == Direction.FACE_RIGHT) {
				if (equipped.drawRotated()){
					g2.rotate(0.7, x + equipImg.getWidth(this)/2, y + equipImg.getHeight(this) + 4);
				}
					g2.drawImage(equipImg, x, y, this);
			} else {
				if (equipped.drawRotated()){
					g2.rotate(-0.7, x + equipImg.getWidth(this)/2, y +equipImg.getHeight(this) + 4);
				}
				g2.drawImage(equipImg, x + equipImg.getWidth(this), y, x, y
						+ equipImg.getHeight(this), 0, 0,
						equipImg.getWidth(this), equipImg.getHeight(this), this);
			}
		}
	}

	private void drawImageInView(Graphics2D g2, Image image, int x, int y) {
		if (inView(x, y)) {
			g2.drawImage(image, x - x1, y - y1, this);
		}
	}

	private boolean inView(int x, int y) {
		return x1 - 32 < x && x2 + 32 > x && y1 - 32 < y && y2 + 32 > y;
	}

	private void updateCamera(int x, int y) {
		if ((x - cameraWidth) >= 0
				&& (x + cameraWidth) <= game.getStage().getStageWidth() * 32) {
			x1 = x - cameraWidth;
			x2 = x + cameraWidth;
		}
		if ((y - cameraHeight) >= 0
				&& (y + cameraHeight) <= (game.getStage().getStageHeight() * 32) + 32) {
			y1 = y - cameraHeight;
			y2 = y + cameraHeight;
		}
	}

}
