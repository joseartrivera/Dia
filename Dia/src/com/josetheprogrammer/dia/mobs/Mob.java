package com.josetheprogrammer.dia.mobs;

import java.awt.Image;
import java.awt.Point;

import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Stage;

/**
 * A mob is an enemy in the game, the player will usually work to defeat these
 * 
 * @author Jose Rivera
 * 
 */

public abstract class Mob {

	protected Point point;
	protected Stage stage;
	protected MobType type;
	protected String mobName;
	protected int health;
	protected int speed;
	protected int attackPower;
	protected int jumpPower;
	protected int jumpCount;
	protected int maxJumpHeight;

	// The range the mob can "see" the player
	protected int range;

	// The range when a mob will attack a player
	protected int attackRange;

	protected boolean jumping;
	protected boolean running;
	protected boolean attacking;
	protected boolean takingDamage;

	protected boolean dead;
	
	protected Direction direction;

	public Mob(Stage stage, Point point) {
		this.point = point;
		this.stage = stage;
		this.speed = 1;
		this.attackPower = 6;
		this.health = 10;
		this.jumpPower = 4;
		this.jumpCount = 0;
		this.maxJumpHeight = 10;
		this.range = 140;
		this.attackRange = 30;
		dead = false;
		this.direction = Direction.FACE_LEFT;
	}

	public abstract Image getSprite();

	public abstract void move();

	public abstract void attack();

	/**
	 * Applies gravity to mobs. Should be called constantly from stepTimer.
	 */
	public void applyGravity() {
		// Push mob down if he is in the air and is not jumping
		if (stage.getBlockAt(point.x + 24, point.y + stage.getGravity() + 30)
				.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 6,
						point.y + stage.getGravity() + 30).getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 16,
						point.y + stage.getGravity() + 30).getBlockProperty() == BlockProperty.EMPTY
				&& !jumping) {
			point.translate(0, stage.getGravity());
		}
		// If the mob is jumping, make sure it can go up and move upward
		else if (stage.getBlockAt(point.x + 24, point.y - jumpPower)
				.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 6, point.y - jumpPower)
						.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 16, point.y - jumpPower)
						.getBlockProperty() == BlockProperty.EMPTY && jumping) {
			point.translate(0, -jumpPower);

			// Keep track of when to stop jumping
			jumpCount++;
			if (jumpCount > maxJumpHeight) {
				jumping = false;
				jumpCount = 0;
			}
		} else {
			jumping = false;
			jumpCount = 0;
		}
	}

	/**
	 * This method returns true if the given coords are contained within this
	 * mob
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contained(int x, int y) {
		return x > point.x && x < point.x + 32 && y > point.y
				&& y < point.y + 32;
	}

	public Point getPoint() {
		return point;
	}

	public int getX() {
		return point.x;
	}

	public int getY() {
		return point.y;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	/**
	 * Causes the mob to take the given damage, checks for death
	 * 
	 * @param damage
	 * @return
	 */
	public int takeDamage(int damage) {
		health -= damage;
		setTakingDamage(true);
		if (health <= 0)
			setDead(true);
		return health;
	}

	public Stage getStage() {
		return stage;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;

	}

	public int getRange() {
		return range;
	}

	public void setRange(int range) {
		this.range = range;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isJumping() {
		return jumping;
	}

	/**
	 * Whether the mob is on the ground
	 * 
	 * @return
	 */
	public boolean isOnGround() {
		// Check below us for ground, if we are grounded return true
		return stage
				.getBlockAt(point.x + 24, point.y + stage.getGravity() + 32)
				.getBlockProperty() == BlockProperty.GROUND
				|| stage.getBlockAt(point.x + 6,
						point.y + stage.getGravity() + 32).getBlockProperty() == BlockProperty.GROUND
				|| stage.getBlockAt(point.x + 16,
						point.y + stage.getGravity() + 32).getBlockProperty() == BlockProperty.GROUND;
	}

	public void setJumping(boolean setJumping) {
		// Set us to jumping only if we are also grounded
		if (isOnGround())
			this.jumping = setJumping;
	}

	public int getJumpPower() {
		return jumpPower;
	}

	public void setJumpPower(int jumpPower) {
		this.jumpPower = jumpPower;
	}

	public int getJumpCount() {
		return jumpCount;
	}

	public void setJumpCount(int jumpCount) {
		this.jumpCount = jumpCount;
	}

	public int getMaxJumpHeight() {
		return maxJumpHeight;
	}

	public void setMaxJumpHeight(int maxJumpHeight) {
		this.maxJumpHeight = maxJumpHeight;
	}

	public int getAttackRange() {
		return attackRange;
	}

	public void setAttackRange(int attackRange) {
		this.attackRange = attackRange;
	}

	public boolean isAttacking() {
		return attacking;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

	public boolean isTakingDamage() {
		return takingDamage;
	}

	public void setTakingDamage(boolean takingDamage) {
		this.takingDamage = takingDamage;
	}

	public MobType getType() {
		return type;
	}

	public void setType(MobType type) {
		this.type = type;
	}

	public String getMobName() {
		return mobName;
	}

	public void setMobName(String mobName) {
		this.mobName = mobName;
	}
	
	public Direction getDirection(){
		return direction;
	}
	
	public void setDirection(Direction direction){
		this.direction = direction;
	}
	
	protected boolean canMove() {
		if (getDirection() == Direction.FACE_LEFT) {
			return getStage().getBlockAt(getPoint().x - getSpeed(),
					getPoint().y + 16).getBlockProperty() == BlockProperty.EMPTY
					&& getStage().getBlockAt(getPoint().x - getSpeed(),
							getPoint().y + 26).getBlockProperty() == BlockProperty.EMPTY
					&& getStage().getBlockAt(getPoint().x - getSpeed(),
							getPoint().y + 8).getBlockProperty() == BlockProperty.EMPTY;
		} else {
			return getStage().getBlockAt(getPoint().x + getSpeed() + 32,
					getPoint().y + 16).getBlockProperty() == BlockProperty.EMPTY
					&& getStage().getBlockAt(getPoint().x + getSpeed() + 32,
							getPoint().y + 26).getBlockProperty() == BlockProperty.EMPTY
					&& getStage().getBlockAt(getPoint().x + getSpeed() + 32,
							getPoint().y + 8).getBlockProperty() == BlockProperty.EMPTY;
		}
	}
	
	protected boolean inAttackRange(int targetX, int targetY){
		return getPoint().distance(targetX, targetY) < getAttackRange()
		&& !isTakingDamage();
	}
	
	protected boolean inFollowRange(int targetX, int targetY){
		return getPoint().distance(targetX, targetY) < getRange();
	}

}
