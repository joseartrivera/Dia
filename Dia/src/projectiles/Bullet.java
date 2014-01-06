package projectiles;

import gameObjects.Stage;

import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import view.Resources;

import blocks.BlockProperty;

/**
 * Bullet projectile, used for fighting mobs
 * 
 * @author Jose Rivera
 * 
 */
public class Bullet extends Projectile {
	private ImageIcon bullet;
	private ImageIcon bulletHit;
	private boolean hit;

	public Bullet(Point point, Stage stage, int xSpeed, int ySpeed) {
		super(point, stage, xSpeed, ySpeed);
		hit = false;
		bullet = Resources.getImage("bullet.png");
		bulletHit = Resources.getImage("bullet_hit.png");
	}

	public Image getSprite() {
		if (!hit)
			return bullet.getImage();
		else {
			setDead(true);
			return bulletHit.getImage();
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
		}
		// We hit a wall, we have hit
		else {
			hit = true;
		}

	}

}
