package com.josetheprogrammer.dia.projectiles;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.gameObjects.Placeable;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.mobs.Mob;
import com.josetheprogrammer.dia.view.Resources;

/**
 * Represents a projectile in our game
 * 
 * @author Jose Rivera
 * 
 */
public abstract class Projectile implements Placeable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stage stage;
	private Point point;
	private boolean altUse;
	private int xSpeed;
	private int ySpeed;
	private int attackPower;
	private boolean dead;
	private String projectileName;
	transient private ImageIcon projectileImage;
	private Color color;
	protected boolean hit;

	// Fired from enemy?
	private boolean enemyProjectile;

	public Projectile(Stage stage, int x, int y, int xSpeed, int ySpeed) {
		this.point = new Point(x, y);
		this.setxSpeed(xSpeed);
		this.setySpeed(ySpeed);
		this.stage = stage;
		setAttackPower(1);
	}

	/**
	 * Returns whether the projectile has hit an enemy or not
	 * 
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

	protected boolean hitPlayer(int x, int y) {
		boolean damaged = false;
		if (stage.getPlayer().contained(x, y)) {
			damaged = true;
			stage.getPlayer().takeDamage(attackPower);
		}
		return damaged;
	}

	protected boolean hit(int x, int y) {
		if (isEnemyProjectile())
			return hitPlayer(x, y);
		else
			return hitEnemy(x, y);
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

	public void setX(int x) {
		point.x = x;
	}

	public void setY(int y) {
		point.y = y;
	}

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

	public boolean isAltUse() {
		return altUse;
	}

	public void setAltUse(boolean altUse) {
		this.altUse = altUse;
	}

	public boolean isEnemyProjectile() {
		return enemyProjectile;
	}

	public void setEnemyProjectile(boolean enemyProjetile) {
		this.enemyProjectile = enemyProjetile;
	}

	public Image getSprite() {
		if (hit)
			setDead(true);
		return projectileImage.getImage();
	}

	public void setProjectileName(String projectileName) {
		this.projectileName = projectileName;
		projectileImage = Resources.getImage("projectiles", projectileName);
	}

	public String getProjectileName() {
		return projectileName;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void place() {
		stage.addProjectile(this);
	}

	public void remove() {
		setDead(true);
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
		this.setProjectileName(projectileName);
	}

}
