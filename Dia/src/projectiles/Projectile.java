package projectiles;

import gameObjects.Stage;

import java.awt.Image;
import java.awt.Point;

import mobs.Mob;

/**
 * Represents a projectile in our game
 * 
 * @author Jose Rivera
 * 
 */
public abstract class Projectile {
	private Stage stage;
	private Point point;
	private int xSpeed;
	private int ySpeed;
	private int attackPower;
	private boolean dead;

	public Projectile(Point point, Stage stage, int xSpeed, int ySpeed) {
		this.point = point;
		this.setxSpeed(xSpeed);
		this.setySpeed(ySpeed);
		this.stage = stage;
		setAttackPower(1);
	}

	/**
	 * Returns whether the projectile has hit an enemy or not
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean hitEnemy(int x, int y) {
		boolean damaged = false;
		for (Mob mob : stage.getMobs()) {
			if (!mob.isDead() && mob.contained(x, y)) {
				damaged = true;
				mob.takeDamage(attackPower);
				break;
			}
		}
		return damaged;
	}

	public abstract Image getSprite();

	public Point getPoint() {
		return point;
	}

	public abstract int getX();

	public abstract int getY();

	public abstract void move();

	public int getxSpeed() {
		return xSpeed;
	}

	public void setxSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}

	public int getySpeed() {
		return ySpeed;
	}

	public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
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

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

}
