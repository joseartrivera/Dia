package com.josetheprogrammer.dia.projectiles;

import java.awt.Image;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.view.Resources;

/**
 * Fireball projectile, used for fighting mobs
 * 
 * @author Jose Rivera
 * 
 */
public class FireBall extends Projectile {
	private ImageIcon fireball;
	private ImageIcon fireballHit;
	private int initialYSpeed;
	private int bounces;
	private boolean hit;

	public FireBall(Stage stage, int x, int y, int xSpeed, int ySpeed) {
		super(stage,x, y, xSpeed, ySpeed);
		hit = false;
		fireball = Resources.getImage("fireball.gif");
		fireballHit = Resources.getImage("small_explosion.gif");
		initialYSpeed = ySpeed;
		setBounces(3);
	}

	public Image getSprite() {
		if (!hit)
			return fireball.getImage();
		else {
			setDead(true);
			return fireballHit.getImage();
		}
	}

	@Override
	public int getX() {
		return getPoint().x;
	}

	@Override
	public int getY() {
		return getPoint().y;
	}

	/**
	 * Moves bullet 1 step according to set speeds
	 */
	@Override
	public void move() {
		Stage stage = getStage();
		// Check to see if we have hit an enemy
		if (hitEnemy(getX() + 12, getY() + 12)) {
			hit = true;
		}
		// Check if we can move
		else if (stage.getBlockAt(getX() + 12 + getxSpeed(),
				getY() + 12 + getySpeed()).getBlockProperty() == BlockProperty.EMPTY) {
			getPoint().translate(getxSpeed(), getySpeed());
			setySpeed(getySpeed() + 1);
		}
		// We hit a wall, bounce
		else if (bounces > 0) {
			if (getySpeed() > 0)
				this.setySpeed(initialYSpeed);
			else
				this.setySpeed(-initialYSpeed);
			bounces -= 1;
		} else {
			hit = true;
		}
	}

	public int getBounces() {
		return bounces;
	}

	public void setBounces(int bounces) {
		this.bounces = bounces;
	}

}
