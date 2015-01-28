package com.josetheprogrammer.dia.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.blocks.DirtBlock;
import com.josetheprogrammer.dia.blocks.EmptyBlock;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.gameObjects.PlayerInventory;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.projectiles.Projectile;

@SuppressWarnings("serial")
public class StageEditor extends JPanel implements Observer, KeyListener,
		MouseListener {
	public static DrawStage draw;
	private Game game;
	private int cameraWidth, cameraHeight;
	private int x1, x2, y1, y2, centerX, centerY; // Camera view
	private int xSpeed, ySpeed, speed, offsetX, offsetY;

	/**
	 * Constructor for our DrawGame panel, used to draw the game
	 */
	public StageEditor(Game game) {
		this.game = game;
		this.addKeyListener(this);
		this.addMouseListener(this);
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
		centerY = 240;
		speed = 8;
		setLocation(0, 0);
		updateCamera(centerX, centerY);
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
		drawPlayer(g2);
		drawMobs(g2);
		drawBlocks(g2);
		drawItems(g2);
		drawInventory(g2);
		drawProjectiles(g2);
		drawMouse(g2);
		centerX += xSpeed;
		centerY += ySpeed;
		
		offsetX = x1 % 32;
		offsetY = y1 % 32;

		if (xSpeed < 0)
			offsetX = -offsetX;
		if (ySpeed < 0)
			offsetY = -offsetY;

		updateCamera(centerX, centerY);
	}

	private void drawMouse(Graphics2D g2) {
		Point mousePos = getMousePosition();
		if (mousePos != null) {

			int x = ((mousePos.x / 32) * 32) + offsetX;
			int y = ((mousePos.y / 32) * 32) + offsetY;

			g2.setColor(Color.ORANGE);
			g2.draw3DRect(x, y, 32, 32, true);

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
	 * Draws the inventory UI
	 * 
	 * @param g2
	 */
	private void drawInventory(Graphics2D g2) {
		PlayerInventory inventory = game.getPlayer().getInventory();
		for (int i = 0; i < inventory.getSize(); i++) {

			if (inventory.getSelectedIndex() != i) {
				g2.drawImage(inventory.getSprite().getImage(), i * 24 + 6, 6,
						this);
			} else {
				g2.drawImage(inventory.getSelectedSprite().getImage(),
						i * 24 + 6, 6, this);
			}
			if (inventory.getItemAtIndex(i) != null) {
				Item item = inventory.getItemAtIndex(i);
				g2.drawImage(item.getInventorySprite().getImage(), i * 24 + 6,
						6, this);
			}

			int health = game.getPlayer().getHealth();
			if (health > 65)
				g2.setColor(Color.GREEN);
			else if (health > 30)
				g2.setColor(Color.ORANGE);
			else
				g2.setColor(Color.RED);

			g2.fill3DRect(6, 32, health, 6, true);
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
					drawImageInView(g2, items[i][j].getSprite().getImage(),
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
		g2.drawImage(p.getSprite().getImage(), centerX, centerY, this);
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
		x1 = x - cameraWidth;
		x2 = x + cameraWidth;
		y1 = y - cameraHeight;
		y2 = y + cameraHeight;
	}

	@Override
	public void mouseClicked(MouseEvent mouse) {
		if (mouse.getButton() == MouseEvent.BUTTON1) {
			game.setBlock(new DirtBlock(game.getStage()), mouse.getX() + x1,
					mouse.getY() + y1);
		} else if (mouse.getButton() == MouseEvent.BUTTON3) {
			game.setBlock(new EmptyBlock(game.getStage()), mouse.getX() + x1,
					mouse.getY() + y1);
		}
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void keyPressed(KeyEvent key) {
		switch (key.getKeyChar()) {
		case 'w':
			ySpeed -= speed;
			break;
		case 'a':
			xSpeed -= speed;
			break;
		case 'd':
			xSpeed += speed;
			break;
		case 's':
			ySpeed += speed;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		switch (key.getKeyChar()) {
		case 'w':
		case 's':
			ySpeed = 0;
			break;
		case 'a':
		case 'd':
			xSpeed = 0;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
	}
}
