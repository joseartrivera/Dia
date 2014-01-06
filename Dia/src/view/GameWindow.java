package view;

import gameObjects.Game;

import items.GunItem;
import items.SwordItem;

import java.awt.Container;
import java.awt.Point;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import listeners.PlayerKeyListener;
import mobs.Slime;

import blocks.DirtBlock;



@SuppressWarnings("serial")
public class GameWindow extends JFrame {
	
	public static GameWindow gameWindow;
	
	//Used to hold our DrawGame
	private Container cp = getContentPane();
	private static Game game;
	
	//Panel that draws the game
	private JPanel drawGame;
	
	/**
	 * Used for starting a quick game at the moment. Quick testing.
	 * @param args
	 */
	public static void main(String[] args) {
		gameWindow = new GameWindow();
		gameWindow.setVisible(true);
		
		game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), 1, 9);
		game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), 5, 9);
		
		game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), 8, 9);
		game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), 8, 8);
		
		game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), 10, 9);
		
		game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), 13, 8);
		game.getStage().setItemByIndex(new SwordItem(null, new Point()), 14, 6);
		game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), 14, 7);
		
		game.getStage().setItemByIndex(new GunItem(null, new Point()), 10, 8);
		for (int i = 0; i < 20; i++){
			game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), i, 10);
			game.getStage().setBlock(new DirtBlock(game.getStage(), new Point()), i, 11);
		}
		
		game.getStage().addMob(new Slime(game.getStage(), new Point(250, 100)));
		game.getStage().addMob(new Slime(game.getStage(), new Point(600, 100)));

	}
	
	/**
	 * Constructor for the game window
	 */
	public GameWindow() {

		setUpWindow();

		game = new Game(); 
		drawGame = new DrawGame(game);
		game.addObserver((Observer) drawGame);
		cp.add(drawGame);
		PlayerKeyListener playerListener = new PlayerKeyListener(game.getPlayer());
		addKeyListener(playerListener);
		addMouseListener(playerListener);
		

		setVisible(true);
	}
	
	/**
	 * Setup the basics of our game window
	 */
	private void setUpWindow() {
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 480);
		setLocation(0, 0);
		setResizable(false);
		setTitle("Dia");
		
	}

}
