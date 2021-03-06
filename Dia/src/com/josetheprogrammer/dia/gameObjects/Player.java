package com.josetheprogrammer.dia.gameObjects;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.blocks.Block;
import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.items.Item;
import com.josetheprogrammer.dia.particles.ParticleType;
import com.josetheprogrammer.dia.view.Resources;

/**
 * 
 * Represents the player in the game. Interacts with many different objects.
 * 
 * @author Jose Rivera
 */

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Attributes
	private int health;
	private int maxHealth;
	private int jumpPower;
	private int realJumpPower;
	private int speed;
	private int gravity;
	private int jumpCounter;
	private int maxJumpHeight;
	private int boostDuration;
	private int xBoost;
	private int yBoost;
	private PlayerInventory inventory;
	private Item equipped;
	private int maxExtraJumpCount;
	private int extraJumpCount;

	// Game info
	private Point point;
	private Stage stage;

	// Images
	transient private ImageIcon stand;
	transient private ImageIcon run;
	transient private ImageIcon jump;
	transient private ImageIcon healthbar;

	private final String FOLDER = "player";

	// States
	private boolean running;
	private boolean jumping;
	private boolean dead;
	private Direction direction;

	/**
	 * Creates a new player with the given attributes
	 * 
	 * @param stage
	 * @param startPoint
	 * @param health
	 * @param speed
	 */
	public Player(Stage stage, Point startPoint, int health, int speed) {
		this.stage = stage;
		this.setHealth(health);
		this.setMaxHealth(health);
		this.setJumpPower(9);
		this.realJumpPower = jumpPower;
		this.setSpeed(speed);
		this.point = startPoint;
		gravity = stage.getGravity();
		setInventory(new PlayerInventory(3));
		running = false;
		jumping = false;
		direction = Direction.FACE_RIGHT;
		jumpCounter = 0;
		maxJumpHeight = 13;
		this.setMaxExtraJumpCount(1);

		loadResources();
	}

	private void loadResources() {
		// Load images
		stand = Resources.getImage(FOLDER, "robo_stand.gif");
		run = Resources.getImage(FOLDER, "robo_run.gif");
		jump = Resources.getImage(FOLDER, "robo_jump.gif");
		healthbar = Resources.getImage("images", "healthbar.png");
	}

	/**
	 * Get the correct sprite depending on which states/actions we are in
	 * 
	 * @return
	 */
	public ImageIcon getSprite() {
		if (!isOnGround()) {
			return jump;
		} else if (!running) {
			return stand;
		} else if (running || xBoost > 0) {
			return run;
		} else {
			return stand;
		}
	}

	/**
	 * Handles movement of the player
	 */
	public void move() {
		if (boostDuration > 0) {
			boostXMove();
			return;
		}
		// Move left if we are running and we are facing left
		if ((direction == Direction.FACE_LEFT) && running) {
			if (stage.getBlockAt(point.x - speed + 6, point.y + 16)
					.getBlockProperty() == BlockProperty.EMPTY
					&& stage.getBlockAt(point.x - speed + 6, point.y + 26)
							.getBlockProperty() == BlockProperty.EMPTY
					&& stage.getBlockAt(point.x - speed + 6, point.y + 8)
							.getBlockProperty() == BlockProperty.EMPTY)
				point.translate(-speed, 0);
		}
		// Otherwise check to see if we need to move right
		else if ((direction == Direction.FACE_RIGHT) && running) {
			if (stage.getBlockAt(point.x + speed + 24, point.y + 16)
					.getBlockProperty() == BlockProperty.EMPTY
					&& stage.getBlockAt(point.x + speed + 24, point.y + 26)
							.getBlockProperty() == BlockProperty.EMPTY
					&& stage.getBlockAt(point.x + speed + 24, point.y + 8)
							.getBlockProperty() == BlockProperty.EMPTY)
				point.translate(speed, 0);
		}

	}

	private void boostXMove() {
		// Move left if we are running and we are facing left
		if ((direction == Direction.FACE_LEFT)) {
			if (stage.getBlockAt(point.x - xBoost + 6, point.y + 16)
					.getBlockProperty() == BlockProperty.EMPTY
					&& stage.getBlockAt(point.x - xBoost + 6, point.y + 26)
							.getBlockProperty() == BlockProperty.EMPTY
					&& stage.getBlockAt(point.x - xBoost + 6, point.y + 8)
							.getBlockProperty() == BlockProperty.EMPTY) {
				point.translate(-xBoost, 0);
				stage.addParticles(12, ParticleType.DUST, Color.ORANGE,
						getX() + 64, getY() + 16, 5, 1, 2, 4, 4, 2,
						boostDuration * 2, 6);
				stage.addParticles(4, ParticleType.DUST, Color.RED,
						getX() + 64, getY() + 16, 5, 1, 2, 4, 4, 2,
						boostDuration * 2, 6);
			}
		}
		// Otherwise check to see if we need to move right
		else if ((direction == Direction.FACE_RIGHT)) {
			if (stage.getBlockAt(point.x + xBoost + 24, point.y + 16)
					.getBlockProperty() == BlockProperty.EMPTY
					&& stage.getBlockAt(point.x + xBoost + 24, point.y + 26)
							.getBlockProperty() == BlockProperty.EMPTY
					&& stage.getBlockAt(point.x + xBoost + 24, point.y + 8)
							.getBlockProperty() == BlockProperty.EMPTY) {
				point.translate(xBoost, 0);
				stage.addParticles(12, ParticleType.DUST, Color.ORANGE,
						getX() - 32, getY() + 16, -5, 1, 2, 4, 4, 2,
						boostDuration * 2, 6);
				stage.addParticles(4, ParticleType.DUST, Color.RED,
						getX() - 32, getY() + 16, -5, 1, 2, 4, 4, 2,
						boostDuration * 2, 6);
			}
		}
		boostDuration--;
		if (boostDuration < 1) {
			xBoost = 0;
			yBoost = 0;
		}
	}

	/**
	 * Set the player to running or not
	 * 
	 * @param running
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Set which direction the player is facing
	 * 
	 * @param action
	 */
	public void setAction(Direction direction) {
		this.direction = direction;
	}

	/**
	 * Get which direction the player is facing
	 * 
	 * @return
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * Apply gravity to this player. Should be called repeatedly by stepTimer in
	 * the game.
	 */
	public void applyGravity() {
		// Push character down if he is in the air and is not jumping
		if (stage.getBlockAt(point.x + 24, point.y + gravity + 28)
				.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 6, point.y + gravity + 28)
						.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 16, point.y + gravity + 28)
						.getBlockProperty() == BlockProperty.EMPTY && !jumping) {
			point.translate(0, gravity);
		}
		// If we are jumping, make sure we can go up and move upward
		else if (stage.getBlockAt(point.x + 24, point.y - jumpPower + 4)
				.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 6, point.y - jumpPower + 4)
						.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 16, point.y - jumpPower + 4)
						.getBlockProperty() == BlockProperty.EMPTY && jumping) {
			point.translate(0, -jumpPower);

			// Inertia
			jumpCounter++;
			if (jumpPower > 1)
				jumpPower--;

			// Keep track of when to stop jumping
			if (jumpCounter > maxJumpHeight) {
				jumping = false;
				jumpCounter = 0;
				jumpPower = realJumpPower;
			}
		}
		// grounded
		else if (isOnGround()) {
			extraJumpCount = 0;
			jumping = false;
			jumpCounter = 0;
			jumpPower = realJumpPower;
		}
		// Hit roof
		else {
			jumping = false;
			jumpCounter = 0;
			jumpPower = realJumpPower;
		}
	}

	/**
	 * Checks whether the player is on the ground
	 * 
	 * @return
	 */
	public boolean isOnGround() {
		// Check below us for ground, if we are grounded return true
		return stage.getBlockAt(point.x + 24, point.y + gravity + 32)
				.getBlockProperty() == BlockProperty.GROUND
				|| stage.getBlockAt(point.x + 6, point.y + gravity + 32)
						.getBlockProperty() == BlockProperty.GROUND
				|| stage.getBlockAt(point.x + 16, point.y + gravity + 32)
						.getBlockProperty() == BlockProperty.GROUND;
	}

	/**
	 * Sets the player to jumping
	 * 
	 * @param setJumping
	 */
	public void setJumping(boolean setJumping) {
		// Set us to jumping only if we are also grounded
		if (isOnGround()) {
			this.jumping = setJumping;
			stage.addParticles(3, ParticleType.DUST, Color.ORANGE, getX() + 16,
					getY() + 24, 1, 1, 1, 1, 1, 1, 10, 2);
			stage.addParticles(3, ParticleType.DUST, Color.RED, getX() + 16,
					getY() + 24, 1, 1, 1, 1, 1, 1, 10, 2);
		} else if (extraJumpCount < getMaxExtraJumpCount()) {
			stage.addParticles(3, ParticleType.DUST, Color.ORANGE, getX() + 16,
					getY() + 24, 1, 1, 1, 1, 1, 1, 10, 2);
			stage.addParticles(3, ParticleType.DUST, Color.RED, getX() + 16,
					getY() + 24, 1, 1, 1, 1, 1, 1, 10, 2);
			extraJumpCount++;
			this.jumping = setJumping;
			jumpCounter = 0;
			jumpPower = realJumpPower;
		}
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Applies the given damage to the player, checks for death
	 * 
	 * @param damage
	 */
	public void takeDamage(int damage) {
		health -= damage;
		stage.addParticles(6, ParticleType.DUST, Color.RED, getX() + 16,
				getY() + 16, 1, 1, 6, 6, 2, 2, 10, 3);
		if (health <= 0)
			setDead(true);
	}

	public int getJumpPower() {
		return jumpPower;
	}

	/**
	 * Set the jump power, changes how high the player jumps
	 * 
	 * @param jumpPower
	 */
	public void setJumpPower(int jumpPower) {
		this.jumpPower = jumpPower;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getX() {
		return point.x;
	}

	public int getY() {
		return point.y;
	}

	public PlayerInventory getInventory() {
		return inventory;
	}

	public void setInventory(PlayerInventory inventory) {
		this.inventory = inventory;
	}

	public Stage getStage() {
		return stage;
	}

	/**
	 * Sets the player's currently selected item to this index in the inventory
	 * 
	 * @param index
	 */
	public void setSelectedItem(int index) {
		inventory.setSelectedIndex(index);
		equipped = inventory.getSelectedItem();
	}

	public Item getEquippedItem() {
		return equipped;
	}

	/**
	 * Adds an item to this player's inventory
	 * 
	 * @param item
	 */
	public void addItemToInventory(Item item) {
		inventory.addItem(item);
		setSelectedItem(inventory.getSelectedIndex());
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getBoostDuration() {
		return boostDuration;
	}

	public void setBoostDuration(int boostDuration) {
		this.boostDuration = boostDuration;
	}

	public int getxBoost() {
		return xBoost;
	}

	public void setxBoost(int xBoost) {
		this.xBoost = xBoost;
	}

	public int getyBoost() {
		return yBoost;
	}

	public void setyBoost(int yBoost) {
		this.yBoost = yBoost;
	}

	public void update() {
		this.move();
		this.applyGravity();
		inventory.update();
	}

	public void placeBlock(Block block, int x, int y) {
		stage.setBlock(block, x / stage.BLOCK_SIZE, y / stage.BLOCK_SIZE);

		Block[][] blocks = getStage().getBlocks();
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[i].length; j++) {
				if (blocks[i][j] != null)
					blocks[i][j].resolveTile();
			}
		}
	}

	public ImageIcon getHealthbarSprite() {
		return healthbar;
	}

	public void setHealthbarSprite(ImageIcon healthbar) {
		this.healthbar = healthbar;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public boolean contained(int x, int y) {
		return x > point.x && x < point.x + 32 && y > point.y
				&& y < point.y + 32;
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		loadResources();
	}

	public void dropItem() {
		inventory.removeItemByIndex(inventory.getSelectedIndex());
		setSelectedItem(inventory.getSelectedIndex());

	}

	public int getMaxExtraJumpCount() {
		return maxExtraJumpCount;
	}

	public void setMaxExtraJumpCount(int maxJumpCount) {
		this.maxExtraJumpCount = maxJumpCount;
	}

}
