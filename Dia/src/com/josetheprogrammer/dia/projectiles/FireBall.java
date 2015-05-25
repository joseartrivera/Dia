package com.josetheprogrammer.dia.projectiles;

import java.awt.Color;

import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.particles.ParticleType;

/**
 * Fireball projectile, used for fighting mobs
 * 
 * @author Jose Rivera
 * 
 */
public class FireBall extends Projectile {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int initialYSpeed;
	private int bounces;

	public FireBall(Stage stage, int x, int y, int xSpeed, int ySpeed) {
		super(stage,x, y, xSpeed, ySpeed);
		hit = false;
		setProjectileName("fireball.gif");
		setColor(Color.RED);
		initialYSpeed = ySpeed;
		setBounces(3);
	}

	/**
	 * Moves bullet 1 step according to set speeds
	 */
	@Override
	public void move() {
		Stage stage = getStage();
		// Check to see if we have hit an enemy
		if (hit(getX() + 12, getY() + 12)) {
			hit = true;
			stage.addParticles(2, ParticleType.DUST, getColor(), getX() + 12,
					getY() + 12, 0, 0, 0, 0, 4, 4, 10, 0);
		}
		// Check if we can move
		else if (stage.getBlockAt(getX() + 12 + getxSpeed(),
				getY() + 12 + getySpeed()).getBlockProperty() == BlockProperty.EMPTY) {
			getPoint().translate(getxSpeed(), getySpeed());
			setySpeed(getySpeed() + 1);
			stage.addParticles(1, ParticleType.DUST, getColor(), getX() + 12,
					getY() + 12, 0, 0, 0, 0, 0, 0, 10, 0);
		}
		// We hit a wall, bounce
		else if (bounces > 0) {
			if (getySpeed() > 0)
				this.setySpeed(initialYSpeed);
			else
				this.setySpeed(-initialYSpeed);
			if (stage.getBlockAt(getX() + 12 + getxSpeed(),
					getY() + 12 + getySpeed()).getBlockProperty() != BlockProperty.EMPTY)
				this.setxSpeed(-this.getxSpeed());
			bounces -= 1;
		} else {
			hit = true;
			stage.addParticles(2, ParticleType.DUST, getColor(), getX() + 12,
					getY() + 12, 0, 0, 0, 0, 4, 4, 10, 0);
		}
	}

	public int getBounces() {
		return bounces;
	}

	public void setBounces(int bounces) {
		this.bounces = bounces;
	}

}
