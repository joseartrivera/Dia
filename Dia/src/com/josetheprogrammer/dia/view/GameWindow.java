package com.josetheprogrammer.dia.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
import com.josetheprogrammer.dia.items.MeleeDashItem;
import com.josetheprogrammer.dia.items.MeleeItem;
import com.josetheprogrammer.dia.items.MeleeTossItem;
import com.josetheprogrammer.dia.listeners.EditorListener;
import com.josetheprogrammer.dia.listeners.PlayerKeyListener;
import com.josetheprogrammer.dia.mobs.BasicMob;
import com.josetheprogrammer.dia.mobs.CrawlMob;
import com.josetheprogrammer.dia.mobs.FireBreatherMob;
import com.josetheprogrammer.dia.mobs.FlyingMob;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.projectiles.ProjectileType;

@SuppressWarnings("serial")
public class GameWindow extends JFrame {

	public static GameWindow gameWindow;

	// Used to hold our DrawGame
	private Container cp = getContentPane();
	private static Game game;

	// menu items
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem menuItem;
	private JPanel stageEditorMenu;

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
		game.getStage().setItemByIndex(new MeleeTossItem(game.getStage()), 14,
				6);
		game.getStage().setItemByIndex(new MeleeDashItem(game.getStage()), 14,
				9);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 14, 7);

		game.getStage().setBlock(new NormalBlock(game.getStage()), 10, 0);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 10, 1);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 10, 2);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 10, 3);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 10, 4);
		game.getStage().setBlock(new NormalBlock(game.getStage()), 10, 5);

		game.getStage().setItemByIndex(
				new LauncherItem(game.getStage(), ProjectileType.Bullet, 6, 0),
				10, 8);
		// game.getStage().setItemByIndex(new LauncherItem(null,
		// ProjectileType.FireBall, 4, -8), 9, 8);
		game.getStage().setItemByIndex(
				Creator.createItem(ItemType.LAUNCHER, "fireball.gif",
						game.getStage(), ProjectileType.FireBall, 4, -6), 9, 8);
		for (int i = 0; i < 20; i++) {
			game.getStage().setBlock(new NormalBlock(game.getStage()), i, 10);
			game.getStage().setBlock(new NormalBlock(game.getStage()), i, 11);
		}

		Mob mob = new FlyingMob(game.getStage(), new Point(250, 164));
		mob.setMobName("hollow");
		mob.setSpeed(1);
		mob.setJumpPower(3);
		mob.setRange(200);
		game.getStage().addMob(mob);
		game.getStage().addMob(
				new FireBreatherMob(game.getStage(), new Point(550, 100)));
		mob = new CrawlMob(game.getStage(), new Point(350, 100));
		game.getStage().addMob(mob);
		game.startGame();
	}

	public void startEditor() {

		EditorListener listener = new EditorListener(game, 12);

		setSize(1120, 480);
		cp.remove(mainMenu);
		game.startEditMode();

		stageEditor = new StageEditor(game, listener);
		game.addObserver((Observer) stageEditor);
		stageEditor.setFocusable(true);
		cp.add(stageEditor);
		stageEditor.requestFocus();
		
		stageEditorMenu = new StageEditorMenu(game, EditorMode.Block);
		cp.add(stageEditorMenu);
		

		buildMenu();
	}

	private void buildMenu() {
		MenuAction action = new MenuAction();
		menuBar = new JMenuBar();
		menuBar.setBackground(Color.DARK_GRAY);
		// Build the stage menu.
		menu = new JMenu("Stage");
		menu.setBackground(Color.DARK_GRAY);

		menu.setMnemonic(KeyEvent.VK_T);
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("Save Stage", KeyEvent.VK_S);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(action);

		menu.add(menuItem);

		menuItem = new JMenuItem("Load Stage", KeyEvent.VK_L);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(action);
		menu.add(menuItem);

		// a group of radio button menu items
		menu.addSeparator();

		menuItem = new JMenuItem("Edit Stage Properties", KeyEvent.VK_E);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(action);
		menu.add(menuItem);

		// Build the inventory menu.
		menu = new JMenu("Inventory");
		menu.setBackground(Color.DARK_GRAY);

		menu.setMnemonic(KeyEvent.VK_V);
		menuBar.add(menu);

		// a group of JMenuItems
		menuItem = new JMenuItem("Block Editor", KeyEvent.VK_B);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(action);
		menu.add(menuItem);

		menuItem = new JMenuItem("Mob Editor", KeyEvent.VK_M);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(action);
		menu.add(menuItem);

		menuItem = new JMenuItem("Item Editor", KeyEvent.VK_I);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(action);
		menu.add(menuItem);

		menuItem = new JMenuItem("Decoration Editor", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.CTRL_MASK));
		menuItem.addActionListener(action);
		menu.add(menuItem);

		this.setJMenuBar(menuBar);
		this.validate();
	}

	public class MenuAction extends AbstractAction {

		public void actionPerformed(ActionEvent ae) {
			cp.remove(stageEditorMenu);
			if (ae.getActionCommand().equals("Block Editor")) {
				stageEditorMenu = new StageEditorMenu(game, EditorMode.Block);
				cp.add(stageEditorMenu);
			}
			if (ae.getActionCommand().equals("Mob Editor")) {
				stageEditorMenu = new StageEditorMenu(game, EditorMode.Mob);
			}
			
			cp.add(stageEditorMenu);
			cp.validate();
		}
	}

}
