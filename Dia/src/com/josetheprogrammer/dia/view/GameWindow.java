package com.josetheprogrammer.dia.view;

import java.awt.Container;
import java.awt.Point;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.josetheprogrammer.dia.blocks.SolidBlock;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.items.LauncherItem;
import com.josetheprogrammer.dia.items.ItemType;
import com.josetheprogrammer.dia.items.SwordItem;
import com.josetheprogrammer.dia.listeners.PlayerKeyListener;
import com.josetheprogrammer.dia.mobs.Slime;
import com.josetheprogrammer.dia.projectiles.ProjectileType;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	public static GameWindow gameWindow;

	// Used to hold our DrawGame
	private Container cp = getContentPane();
	private static Game game;

	// Panel that draws the game
	private JPanel drawGame, menu, stageEditor;

	/**
	 * Used for starting a quick game at the moment. Quick testing.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		gameWindow = new GameWindow();
		gameWindow.setVisible(true);
	}

	/**
	 * Constructor for the game window
	 */
	public GameWindow() {

		setUpWindow();

		Resources resources = new Resources();
		game = new Game(resources);
		menu = new MainMenu(this);
		cp.add(menu);
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

	public void startNewGame() {
		cp.remove(menu);
		drawGame = new DrawStage(game);
		game.addObserver((Observer) drawGame);
		PlayerKeyListener playerListener = new PlayerKeyListener(
				game.getPlayer());
		drawGame.addKeyListener(playerListener);
		drawGame.addMouseListener(playerListener);
		cp.add(drawGame);

		drawGame.requestFocus();

		game.getStage().setBlock(new SolidBlock(game.getStage()), 1, 9);
		game.getStage().setBlock(new SolidBlock(game.getStage()), 5, 9);

		game.getStage().setBlock(new SolidBlock(game.getStage()), 8, 9);
		game.getStage().setBlock(new SolidBlock(game.getStage()), 8, 8);

		game.getStage().setBlock(new SolidBlock(game.getStage()), 10, 9);

		game.getStage().setBlock(new SolidBlock(game.getStage()), 13, 8);
		game.getStage().setItemByIndex(new SwordItem(null), 14, 6);
		game.getStage().setBlock(new SolidBlock(game.getStage()), 14, 7);

		game.getStage().setItemByIndex(new LauncherItem(game.getPlayer(), ProjectileType.Bullet, 6, 0), 10, 8);
		game.getStage().setItemByIndex(new LauncherItem(null, ProjectileType.FireBall, 4, -8), 9, 8);
		for (int i = 0; i < 20; i++) {
			game.getStage().setBlock(new SolidBlock(game.getStage()), i, 10);
			game.getStage().setBlock(new SolidBlock(game.getStage()), i, 11);
		}

		game.getStage().addMob(new Slime(game.getStage(), new Point(250, 100)));
		game.getStage().addMob(new Slime(game.getStage(), new Point(600, 100)));

		game.startGame();
	}

	public void startEditor() {
		setSize(736, 480);
		cp.remove(menu);
		stageEditor = new StageEditor(game);
		game.addObserver((Observer) stageEditor);
		cp.add(stageEditor);
		
		stageEditor.requestFocus();
		game.startEditMode();
	}

}
