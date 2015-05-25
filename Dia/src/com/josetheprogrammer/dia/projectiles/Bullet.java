package com.josetheprogrammer.dia.projectiles;

import java.awt.Color;

import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.particles.ParticleType;

/**
 * Bullet projectile, used for fighting mobs
 * 
 * @author Jose Rivera
 * 
 */
public class Bullet extends Projectile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Bullet(Stage stage, int x, int y, int xSpeed, int ySpeed) {
		super(stage, x, y, xSpeed, ySpeed);
		setProjectileName("bullet.png");
		setColor(Color.YELLOW);
		hit = false;
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
			stage.addParticles(1, ParticleType.DUST, getColor(), getX() + 12,
					getY() + 12, 0, 0, 0, 0, 3, 3, 10, 0);
		}
		// Check if we can move
		else if (stage.getBlockAt(getX() + 12 + getxSpeed(),
				getY() + 12 + getySpeed()).getBlockProperty() == BlockProperty.EMPTY) {
			getPoint().translate(getxSpeed(), getySpeed());
			stage.addParticles(1, ParticleType.DUST, getColor(), getX() + 12,
					getY() + 12, 0, 0, 0, 0, 0, 0, 10, 0);
		}
		// We hit a wall, we have hit
		else {
			hit = true;
			stage.addParticles(1, ParticleType.DUST, getColor(), getX() + 12,
					getY() + 12, 0, 0, 0, 0, 3, 3, 10, 0);
		}

	}

}
