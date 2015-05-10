package com.josetheprogrammer.dia.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.gameObjects.PlayerInventory;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.listeners.EditorListener;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.projectiles.Projectile;

@SuppressWarnings("serial")
public class StageEditor extends JPanel implements Observer {
	public static DrawStage draw;
	private Game game;
	private int cameraWidth, cameraHeight;
	private int centerX, centerY; // Camera view
	private EditorListener editKey;

	/**
	 * Constructor for our DrawGame panel, used to draw the game
	 */
	public StageEditor(Game game, EditorListener editKey) {
		this.game = game;
		this.editKey = editKey;
		//this.addKeyListener(editKey);
		editKey.setKeys(this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW), this.getActionMap());
		this.addMouseListener(editKey);
		this.addMouseMotionListener(editKey);
		game.getStage().changeStageDimensions(40, 28);
		setup();
		setVisible(true);
	}

	/**
	 * Setup the basics of our panel
	 */
	private void setup() {
		setLayout(null);
		setSize(640, 480);
		cameraWidth = 320;
		cameraHeight = 240;
		centerX = 320;
		centerY = (game.getStage().getStageHeight() * 32) + 32 - 240;
		setLocation(0, 0);
		updateCamera();
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
		drawMobs(g2);
		drawBlocks(g2);
		drawItems(g2);
		drawInventory(g2);
		drawProjectiles(g2);
		drawMouse(g2);

		updateCamera();
	}

	private void drawMouse(Graphics2D g2) {
		Point mousePos = getMousePosition();
		if (mousePos != null) {

			int x = ((mousePos.x + editKey.x1) / 32) * 32;
			int y = ((mousePos.y + editKey.y1) / 32) * 32;
			g2.setColor(Color.ORANGE);
			g2.draw3DRect(x - editKey.x1, y - editKey.y1, 32, 32, true);

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

	private void drawInventory(Graphics2D g2) {
		int x = 0;
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
				// g2.drawImage(item.getInventorySprite(), x, 6, this);
				g2.drawImage(item.getInventorySprite(), x + 4, 10, x + 20, 24,
						0, 0, 32, 32, this);

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

	}

	/**
	 * Draws each mob active on the stage
	 * 
	 * @param g2
	 */
	private void drawMobs(Graphics2D g2) {
		synchronized (game.getStage().getMobs()) {
			for (Mob mob : game.getStage().getMobs()) {
				drawImageInView(g2, mob.getSprite(), mob.getX(), mob.getY());
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

	private void drawImageInView(Graphics2D g2, Image image, int x, int y) {
		if (inView(x, y)) {
			g2.drawImage(image, x - editKey.x1, y - editKey.y1, this);
		}
	}

	private boolean inView(int x, int y) {
		return editKey.x1 - 32 < x && editKey.x2 > x && editKey.y1 - 32 < y && editKey.y2 + 32 > y;
	}

	private void updateCamera() {
		int x = centerX + editKey.xSpeed;
		int y = centerY + editKey.ySpeed;
		if ((x - cameraWidth) >= 0
				&& (x + cameraWidth) <= game.getStage().getStageWidth() * 32) {
			editKey.x1 = x - cameraWidth;
			editKey.x2 = x + cameraWidth;
			centerX += editKey.xSpeed;
		}
		if ((y - cameraHeight) >= 0
				&& (y + cameraHeight) <= (game.getStage().getStageHeight() * 32) + 32) {
			editKey.y1 = y - cameraHeight;
			editKey.y2 = y + cameraHeight;
			centerY += editKey.ySpeed;
		}
	}

}
