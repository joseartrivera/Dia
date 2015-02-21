package com.josetheprogrammer.dia.mobs;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.particles.ParticleType;
import com.josetheprogrammer.dia.view.Resources;

/**
 * Slime is a type of mob that the player will fight
 * 
 * @author Jose Rivera
 * 
 */
public class BasicMob extends Mob {

	private ImageIcon stand;
	private ImageIcon move;
	private ImageIcon attack;
	private ImageIcon mobDead;
	private ImageIcon mobDamaged;

	private int attackDuration;
	private int attackCount;

	public BasicMob(Stage stage, Point point) {
		super(stage, point);
		this.setMobName("spider");

		attackDuration = 25;
		attackCount = 0;
	}

	@Override
	public void setMobName(String mobName) {
		super.setMobName(mobName);
		stand = Resources.getImage(mobName + "_stand.gif");
		move = Resources.getImage(mobName + "_move.gif");
		attack = Resources.getImage(mobName + "_attack.gif");
		mobDead = Resources.getImage(mobName + "_dead.png");
		mobDamaged = Resources.getImage(mobName + "_damaged.gif");
	}

	public Image getSprite() {
		if (isDead()) {
			if (mobDead != null)
				return mobDead.getImage();
			else
				return null;
		} else if (isTakingDamage()) {
			setTakingDamage(false);
			if (mobDamaged != null)
				return mobDamaged.getImage();
		} else if (isAttacking()) {
			return attack.getImage();
		} else if (isRunning()) {
			return move.getImage();
		}

		return stand.getImage();
	}

	/**
	 * Moves the mob according to given speed and states
	 */
	public void move() {
		int targetX = getStage().getPlayer().getX();
		int targetY = getStage().getPlayer().getY();

		// Check to see if we are in attack range and are not taking damage
		if (getPoint().distance(targetX, targetY) < getAttackRange()
				&& !isTakingDamage()) {

			setAttacking(true);
			attack();

		}
		// Check to see if player is in range to follow
		else if (getPoint().distance(targetX, targetY) < getRange()
				|| isTakingDamage()) {

			setAttacking(false);

			int speed = getSpeed();

			// If we are taking damage, reverse our direction
			if (isTakingDamage()) {
				speed = -speed * 4;
				getStage().addParticles(6, ParticleType.DUST, Color.RED,
						getX() + 16, getY() + 16, 1, 1, 6, 6, 2, 2, 10, 3);
			}

			// Determine if we need to move left or right
			if (targetX < getX()) {
				direction = Direction.FACE_LEFT;
				setRunning(true);
				if (getStage().getBlockAt(getPoint().x - getSpeed(),
						getPoint().y + 16).getBlockProperty() == BlockProperty.EMPTY
						&& getStage().getBlockAt(getPoint().x - getSpeed(),
								getPoint().y + 26).getBlockProperty() == BlockProperty.EMPTY
						&& getStage().getBlockAt(getPoint().x - getSpeed(),
								getPoint().y + 8).getBlockProperty() == BlockProperty.EMPTY) {
					getPoint().translate(-speed, 0);
				} else {
					setJumping(true);
				}
			} else if (targetX - getX() < speed) {
				setRunning(false);
				return;
			} else {
				direction = Direction.FACE_RIGHT;
				setRunning(true);
				if (getStage().getBlockAt(getPoint().x + getSpeed() + 32,
						getPoint().y + 16).getBlockProperty() == BlockProperty.EMPTY
						&& getStage().getBlockAt(
								getPoint().x + getSpeed() + 32,
								getPoint().y + 26).getBlockProperty() == BlockProperty.EMPTY
						&& getStage().getBlockAt(
								getPoint().x + getSpeed() + 32,
								getPoint().y + 8).getBlockProperty() == BlockProperty.EMPTY) {
					getPoint().translate(speed, 0);
				} else {
					setJumping(true);
				}
			}
		} else {
			setRunning(false);
		}
	}

	/**
	 * Attacks a stage's player, doing damage to them
	 */
	public void attack() {
		if (attackCount < attackDuration) {
			if (attackCount == 0)
				getStage().getPlayer().takeDamage(getAttackPower());

			attackCount++;
		} else {
			setAttacking(false);
			attackCount = 0;
		}

	}

}
