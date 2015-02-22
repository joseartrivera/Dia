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
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.blocks.BlockType;
import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.gameObjects.Player;
import com.josetheprogrammer.dia.gameObjects.PlayerInventory;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.items.ItemType;
import com.josetheprogrammer.dia.items.PlaceableItem;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.mobs.MobType;
import com.josetheprogrammer.dia.projectiles.Projectile;
import com.josetheprogrammer.dia.projectiles.ProjectileType;

@SuppressWarnings("serial")
public class StageEditor extends JPanel implements Observer, KeyListener,
		MouseListener, MouseMotionListener {
	public static DrawStage draw;
	private Game game;
	private int cameraWidth, cameraHeight;
	private int x1, x2, y1, y2, centerX, centerY; // Camera view
	private int xSpeed, ySpeed, speed;

	/**
	 * Constructor for our DrawGame panel, used to draw the game
	 */
	public StageEditor(Game game) {
		this.game = game;
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		game.getStage().changeStageDimensions(20, 14);
		setup();
		setVisible(true);

		PlaceableItem item;
		

		item = new PlaceableItem(game.getStage(), Creator.createBlock(
				BlockType.SOLID, "fresh_dirt_tilset.png", game.getStage()));
		
		game.getPlayer().addItemToInventory(item);

		item = new PlaceableItem(game.getStage(), Creator.createBlock(
				BlockType.SOLID, "dungeon_tileset.png", game.getStage()));
		
		game.getPlayer().addItemToInventory(item);
		
		item = new PlaceableItem(game.getStage(), Creator.createMob(
				MobType.Slime, "slime", game.getStage(), 0, 0));
		
		game.getPlayer().addItemToInventory(item);
		
		item = new PlaceableItem(game.getStage(), Creator.createItem(
				ItemType.LAUNCHER, "fireball.gif", game.getStage(),
				ProjectileType.FireBall, 3, 3));
		game.getPlayer().addItemToInventory(item);
		
		item = new PlaceableItem(game.getStage(), Creator.createItem(
				ItemType.LAUNCHER, "gun.png", game.getStage(),
				ProjectileType.FireBall, 3, 3));
		game.getPlayer().addItemToInventory(item);



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
		speed = 12;
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

			int x = ((mousePos.x + x1) / 32) * 32;
			int y = ((mousePos.y + y1) / 32) * 32;
			g2.setColor(Color.ORANGE);
			g2.draw3DRect(x - x1, y - y1, 32, 32, true);

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
			g2.drawImage(image, x - x1, y - y1, this);
		}
	}

	private boolean inView(int x, int y) {
		return x1 - 32 < x && x2 > x && y1 - 32 < y && y2 + 32 > y;
	}

	private void updateCamera() {
		int x = centerX + xSpeed;
		int y = centerY + ySpeed;
		if ((x - cameraWidth) >= 0
				&& (x + cameraWidth) <= game.getStage().getStageWidth() * 32) {
			x1 = x - cameraWidth;
			x2 = x + cameraWidth;
			centerX += xSpeed;
		}
		if ((y - cameraHeight) >= 0
				&& (y + cameraHeight) <= (game.getStage().getStageHeight() * 32) + 32) {
			y1 = y - cameraHeight;
			y2 = y + cameraHeight;
			centerY += ySpeed;
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouse) {
		place(mouse);
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void keyPressed(KeyEvent key) {
		Player player = game.getPlayer();
		switch (key.getKeyChar()) {
		case 'w':
			ySpeed = -speed;
			break;
		case 'a':
			xSpeed = -speed;
			break;
		case 'd':
			xSpeed = speed;
			break;
		case 's':
			ySpeed = speed;
			break;
		case 'p':
			Resources.SaveStage(game.getStage(), "new stage");
			break;
		case 'l':
			Stage stage = new Stage();
			Resources.LoadStage("new stage.stage", stage);
			game.setStage(stage);
		case '1':
			player.setSelectedItem(0);
			break;
		case '2':
			player.setSelectedItem(1);
			break;
		case '3':
			player.setSelectedItem(2);
			break;
		case '4':
			player.setSelectedItem(3);
			break;
		case '5':
			player.setSelectedItem(4);
			break;
		case '6':
			player.setSelectedItem(5);
			break;
		case '7':
			player.setSelectedItem(6);
			break;
		case '8':
			player.setSelectedItem(7);
			break;
		case '9':
			player.setSelectedItem(8);
			break;
		case '0':
			player.setSelectedItem(9);
		default:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent key) {
		switch (key.getKeyChar()) {
		case 'w':
			if (ySpeed < 0)
				ySpeed = 0;
			break;
		case 's':
			if (ySpeed > 0)
				ySpeed = 0;
			break;
		case 'a':
			if (xSpeed < 0)
				xSpeed = 0;
			break;
		case 'd':
			if (xSpeed > 0)
				xSpeed = 0;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent key) {
	}

	@Override
	public void mouseDragged(MouseEvent mouse) {
		place(mouse);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public void place(MouseEvent mouse) {
		Player player = game.getPlayer();
		PlaceableItem item;
		if (mouse.getButton() == MouseEvent.BUTTON1
				&& player.getEquippedItem() != null) {
			item = (PlaceableItem) player.getEquippedItem();
			item.generatePlaceable(game.getStage(), mouse.getX() + x1,
					mouse.getY() + y1);

			item.useItem();
		} else if (mouse.getButton() == MouseEvent.BUTTON3
				&& player.getEquippedItem() != null) {
			item = (PlaceableItem) player.getEquippedItem();
			item.setRemoveX(mouse.getX() + x1);
			item.setRemoveY(mouse.getY() + y1);
			item.altUseItem();
		}
	}

}
