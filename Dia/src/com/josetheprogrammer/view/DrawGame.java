package com.josetheprogrammer.view;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.josetheprogrammer.blocks.Block;
import com.josetheprogrammer.gameObjects.Game;
import com.josetheprogrammer.gameObjects.Player;
import com.josetheprogrammer.gameObjects.PlayerInventory;
import com.josetheprogrammer.items.Item;
import com.josetheprogrammer.mobs.Mob;
import com.josetheprogrammer.projectiles.Projectile;






@SuppressWarnings("serial")
public class DrawGame extends JPanel implements Observer {
	public static DrawGame draw;
	private Game game;

	/**
	 * Constructor for our DrawGame panel, used to draw the game
	 */
	public DrawGame(Game game) {
		this.game = game;
		setup();
		setVisible(true);
	}

	/**
	 * Setup the basics of our panel
	 */
	private void setup() {
		setLayout(null);
		setSize(640, 480);
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
		drawPlayer(g2);
		drawMobs(g2);
		drawBlocks(g2);
		drawItems(g2);
		drawInventory(g2);
		drawProjectiles(g2);
	}

	/**
	 * Draws the background for our current stage
	 * @param g2
	 */
	private void drawStage(Graphics2D g2) {
		g2.drawImage(game.getStage().getBackground(), 0, 0, this);
		
	}

	/**
	 * Draws each mob active on the stage
	 * @param g2
	 */
	private void drawMobs(Graphics2D g2) {
		synchronized (game.getStage().getMobs()){
			for (Mob mob : game.getStage().getMobs()){
				g2.drawImage(mob.getSprite(), mob.getX(), mob.getY(), this);
			}
		}
	}

	/**
	 * Draws each active projectile on the stage
	 * @param g2
	 */
	private void drawProjectiles(Graphics2D g2) {
		synchronized (game.getStage().getProjectiles()){
			for (Projectile projectile : game.getStage().getProjectiles())
				g2.drawImage(projectile.getSprite(), projectile.getX(), projectile.getY(), this);
		}
	}

	/**
	 * Draws the inventory UI
	 * @param g2
	 */
	private void drawInventory(Graphics2D g2) {
		PlayerInventory inventory = game.getPlayer().getInventory();
		for (int i = 0; i < inventory.getSize(); i++){

			if (inventory.getSelectedIndex() != i){
				g2.drawImage(inventory.getSprite().getImage(), i * 24 + 6, 6, this);
			}
			else{
				g2.drawImage(inventory.getSelectedSprite().getImage(), i * 24 + 6, 6, this);
			}
			if (inventory.getItemAtIndex(i) != null){
				Item item = inventory.getItemAtIndex(i);
				g2.drawImage(item.getInventorySprite().getImage(), i * 24 + 6 , 6, this);
			}
			
			int health = game.getPlayer().getHealth();
			if (health > 65)
				g2.setColor(Color.GREEN);
			else if (health > 30)
				g2.setColor(Color.ORANGE);
			else
				g2.setColor(Color.RED);
			
			g2.fill3DRect(6, 32,
					health, 6, true);
		}
		
		
	}

	/**
	 * Draws the active blocks on the stage
	 * @param g2
	 */
	private void drawBlocks(Graphics2D g2) {
		Block[][] blocks = game.getStage().getBlocks();
		for (int i = 0; i < blocks.length; i++){
			for (int j = 0; j < blocks[i].length; j++){
				g2.drawImage(blocks[i][j].getSprite(), blocks[i][j].getX(), blocks[i][j].getY(), this);
			}
		}
	}
	
	/**
	 * Draws the active items on the stage
	 * @param g2
	 */
	private void drawItems(Graphics2D g2) {
		Item[][] items = game.getStage().getItems();
		for (int i = 0; i < items.length; i++){
			for (int j = 0; j < items[i].length; j++){
				if (items[i][j] != null)
					g2.drawImage(items[i][j].getSprite().getImage(), items[i][j].getX(),items[i][j].getY(), this);
			}
		}
	}

	/**
	 * Draws the player object
	 * @param g2
	 */
	private void drawPlayer(Graphics2D g2) {
		Player p = game.getPlayer();
		g2.drawImage(p.getSprite().getImage(), p.getX(), p.getY(), this);
		if (p.getEquippedItem() != null)
			g2.drawImage(p.getEquippedItem().getEquippedSprite().getImage(), p.getEquippedItem().getEquippedX(), 
					p.getEquippedItem().getEquippedY(), this);
	}

}
