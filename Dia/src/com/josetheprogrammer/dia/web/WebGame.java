package com.josetheprogrammer.dia.web;



import java.awt.Container;
import java.awt.Point;
import java.net.URL;
import java.util.Observer;

import javax.swing.JApplet;
import javax.swing.JPanel;

import com.josetheprogrammer.dia.blocks.DirtBlock;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.items.GunItem;
import com.josetheprogrammer.dia.items.SwordItem;
import com.josetheprogrammer.dia.listeners.PlayerKeyListener;
import com.josetheprogrammer.dia.mobs.Slime;
import com.josetheprogrammer.dia.view.DrawStage;
import com.josetheprogrammer.dia.view.Resources;





@SuppressWarnings("serial")
public class WebGame extends JApplet {
	
	public static WebGame gameWindow;
	
	//Used to hold our DrawGame
	private Container cp = getContentPane();
	private static Game game;
	
	//Panel that draws the game
	private JPanel drawGame;
	
	/**
	 * Used for starting a quick game at the moment. Quick testing.
	 * @param args
	 */
	public void start() {
		drawGame.setFocusable(true);
		drawGame.requestFocusInWindow();
	}

	public void init() {

		setUpWindow();
		
		URL url = getCodeBase();
		
		Resources resources = new Resources(url);
		game = new Game(resources); 
		drawGame = new DrawStage(game);
		game.addObserver((Observer) drawGame);

		PlayerKeyListener playerListener = new PlayerKeyListener(game.getPlayer());
		drawGame.setFocusable(true);
		drawGame.addKeyListener(playerListener);
		drawGame.addMouseListener(playerListener);
		cp.add(drawGame);
		
		gameWindow = new WebGame();
		gameWindow.setVisible(true);
		
		game.getStage().setBlock(new DirtBlock(game.getStage()), 1, 9);
		game.getStage().setBlock(new DirtBlock(game.getStage()), 5, 9);
		
		game.getStage().setBlock(new DirtBlock(game.getStage()), 8, 9);
		game.getStage().setBlock(new DirtBlock(game.getStage()), 8, 8);
		
		game.getStage().setBlock(new DirtBlock(game.getStage()), 10, 9);
		
		game.getStage().setBlock(new DirtBlock(game.getStage()), 13, 8);
		game.getStage().setItemByIndex(new SwordItem(null, new Point()), 14, 6);
		game.getStage().setBlock(new DirtBlock(game.getStage()), 14, 7);
		
		game.getStage().setItemByIndex(new GunItem(null, new Point()), 10, 8);
		for (int i = 0; i < 20; i++){
			game.getStage().setBlock(new DirtBlock(game.getStage()), i, 10);
			game.getStage().setBlock(new DirtBlock(game.getStage()), i, 11);
		}
		
		game.getStage().addMob(new Slime(game.getStage(), new Point(250, 100)));
		game.getStage().addMob(new Slime(game.getStage(), new Point(600, 100)));


		setVisible(true);
		game.startGame();
	}
	
	
	/**
	 * Setup the basics of our game window
	 */
	private void setUpWindow() {
		setLayout(null);
		setSize(640, 480);
		setLocation(0, 0);
		setName("Dia");
		
	}

}
