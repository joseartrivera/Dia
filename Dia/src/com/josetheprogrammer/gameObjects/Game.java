package com.josetheprogrammer.gameObjects;

import java.awt.Point;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import com.josetheprogrammer.view.Resources;


/**
 * Dia is a 2D side-scrolling action/puzzle platformer game meant to be very
 * customizable. This class represents the game itself.
 * 
 * @author Jose Rivera
 * 
 */
public class Game extends Observable {
	private Stage stage;
	private Player player;
	private Timer stepTimer;
	public Resources resources;

	/**
	 * Creates a new game of Dia
	 */
	public Game() {
		resources = new Resources();
		stage = new Stage();
		player = new Player(stage, new Point(stage.getStartPoint().x,
				stage.getStartPoint().y), 100, 4);
		stage.setPlayer(player);
		startTimers();

	}

	/**
	 * Get the game's player
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Get the current stage of the game
	 * 
	 * @return
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Start the step timer, controls gravity and other aspects of the game
	 */
	private void startTimers() {
		stepTimer = new Timer();
		stepTimer.schedule(new GameTasks(), 0, 20);
	}

	/**
	 * This class is a task that the stepTimer will continously execute. Updates
	 * projectiles, mobs, gravity, and player movements
	 * 
	 * @author Jose Rivera
	 * 
	 */
	private class GameTasks extends TimerTask {

		@Override
		public void run() {
			stage.updateProjectiles();
			stage.updateMobs();
			player.move();
			player.applyGravity();
			setChanged();
			notifyObservers();
		}
	}

}
