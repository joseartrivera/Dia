package com.josetheprogrammer.dia.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import com.josetheprogrammer.dia.blocks.NormalBlock;
import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Game;
import com.josetheprogrammer.dia.items.ItemType;
import com.josetheprogrammer.dia.items.LauncherItem;
import com.josetheprogrammer.dia.items.SwordItem;
import com.josetheprogrammer.dia.listeners.PlayerKeyListener;
import com.josetheprogrammer.dia.mobs.BasicMob;
import com.josetheprogrammer.dia.projectiles.ProjectileType;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	public static GameWindow gameWindow;

	// Used to hold our DrawGame
	private Container cp = getContentPane();
	private static Game game;
	
	//menu items
	private JMenuBar menuBar;
	private JMenu menu, submenu;
	private JMenuItem menuItem;
	private JRadioButtonMenuItem rbMenuItem;
	private JCheckBoxMenuItem cbMenuItem;

	// Panel that draws the game
	private JPanel drawGame, mainMenu, stageEditor;

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
		mainMenu = new MainMenu(this);
		cp.add(mainMenu);
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
		cp.remove(mainMenu);
		drawGame = new DrawStage(game);
		game.addObserver((Observer) drawGame);
		PlayerKeyListener playerListener = new PlayerKeyListener(
				game.getPlayer());
		drawGame.addKeyListener(playerListener);
		drawGame.addMouseListener(playerListener);
		cp.add(drawGame);

		drawGame.requestFocus();

		game.getStage().setBlock(new NormalBlock(game.getStage()), 1, 9);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 5, 9);

		game.getStage().setBlock(new NormalBlock(game.getStage()), 8, 9);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 8, 8);

		game.getStage().setBlock(new NormalBlock(game.getStage()), 10, 9);

		game.getStage().setBlock(new NormalBlock(game.getStage()), 13, 8);
		game.getStage().setItemByIndex(new SwordItem(null), 14, 6);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 14, 7);

		game.getStage().setItemByIndex(new LauncherItem(game.getPlayer(), ProjectileType.Bullet, 6, 0), 10, 8);
		//game.getStage().setItemByIndex(new LauncherItem(null, ProjectileType.FireBall, 4, -8), 9, 8);
		game.getStage().setItemByIndex(Creator.createItem(ItemType.LAUNCHER, "fireball.gif", game.getStage(), ProjectileType.FireBall, 4, -6),9,8);
		for (int i = 0; i < 20; i++) {
			game.getStage().setBlock(new NormalBlock(game.getStage()), i, 10);
			game.getStage().setBlock(new NormalBlock(game.getStage()), i, 11);
		}

		game.getStage().addMob(new BasicMob(game.getStage(), new Point(250, 100)));
		game.getStage().addMob(new BasicMob(game.getStage(), new Point(600, 100)));

		game.startGame();
	}

	public void startEditor() {
		
		setSize(640, 480);
		cp.remove(mainMenu);

		stageEditor = new StageEditor(game);
		game.addObserver((Observer) stageEditor);
		cp.add(stageEditor);
		stageEditor.requestFocus();
		game.startEditMode();
		buildMenu();
	}
	
	
	private void buildMenu(){
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.DARK_GRAY);
		//Build the stage menu.
		menu = new JMenu("Stage");
		menu.setBackground(Color.DARK_GRAY);
		
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);

		//a group of JMenuItems
		menuItem = new JMenuItem("Block Editor", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		

		menu.add(menuItem);

		menuItem = new JMenuItem("Fireball",
		                         new ImageIcon("Images/fireball.gif"));
		menuItem.setMnemonic(KeyEvent.VK_B);
		menu.add(menuItem);

		//a group of radio button menu items
		menu.addSeparator();
		ButtonGroup group = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("A radio button menu item",new ImageIcon("Images/fireball.gif"));
		rbMenuItem.setSelected(true);
		rbMenuItem.setMnemonic(KeyEvent.VK_R);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);

		rbMenuItem = new JRadioButtonMenuItem("Another one");
		rbMenuItem.setMnemonic(KeyEvent.VK_O);
		group.add(rbMenuItem);
		menu.add(rbMenuItem);
		
		//Build the inventory menu.
		menu = new JMenu("Inventory");
		menu.setBackground(Color.DARK_GRAY);
		
		menu.setMnemonic(KeyEvent.VK_A);
		menuBar.add(menu);
		
		//a group of JMenuItems
		menuItem = new JMenuItem("Block Editor", KeyEvent.VK_T);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menu.add(menuItem);
		
		this.setJMenuBar(menuBar);
		this.validate();
	}

}
