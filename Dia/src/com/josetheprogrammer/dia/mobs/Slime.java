package com.josetheprogrammer.dia.mobs;


import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.view.Resources;



/**
 * Slime is a type of mob that the player will fight
 * 
 * @author Jose Rivera
 * 
 */
public class Slime extends Mob {

	private ImageIcon standLeft;
	private ImageIcon standRight;
	private ImageIcon moveLeft;
	private ImageIcon moveRight;
	private ImageIcon attackRight;
	private ImageIcon attackLeft;
	private ImageIcon slimeDead;
	private ImageIcon slimeDamaged;

	private Direction action;
	boolean running;

	private int attackDuration;
	private int attackCount;

	public Slime(Stage stage, Point point) {
		super(stage, point);
		standLeft = Resources.getImage("slime_left.gif");
		standRight = Resources.getImage("slime_right.gif");
		moveLeft = Resources.getImage("slime_left_move.gif");
		moveRight = Resources.getImage("slime_right_move.gif");
		attackRight = Resources.getImage("slime_attack_right.gif");
		attackLeft = Resources.getImage("slime_attack_left.gif");
		slimeDead = Resources.getImage("slime_dead.png");
		slimeDamaged = Resources.getImage("slime_damaged.gif");

		action = Direction.FACE_LEFT;

		attackDuration = 25;
		attackCount = 0;
	}

	public Image getSprite() {
		if (isDead()) {
			return slimeDead.getImage();
		} else if (isTakingDamage()) {
			setTakingDamage(false);
			return slimeDamaged.getImage();
		} else if (action == Direction.FACE_LEFT && isAttacking()) {
			return attackLeft.getImage();
		} else if (action == Direction.FACE_RIGHT && isAttacking()) {
			return attackRight.getImage();
		} else if (action == Direction.FACE_LEFT && !running) {
			return standLeft.getImage();
		} else if (action == Direction.FACE_RIGHT && !running) {
			return standRight.getImage();
		} else if (action == Direction.FACE_LEFT && running) {
			return moveLeft.getImage();
		} else if (action == Direction.FACE_RIGHT && running) {
			return moveRight.getImage();
		} else {
			return standRight.getImage();
		}
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
			}

			// Determine if we need to move left or right
			if (targetX < getX()) {
				action = Direction.FACE_LEFT;
				setRunning(true);
				if (getStage().getBlockAt(getPoint().x - getSpeed(),
						getPoint().y + 16).getBlockProperty() == BlockProperty.EMPTY
						&& getStage().getBlockAt(getPoint().x - getSpeed(),
								getPoint().y + 26).getBlockProperty() == BlockProperty.EMPTY
						&& getStage().getBlockAt(getPoint().x - getSpeed(),
								getPoint().y + 8).getBlockProperty() == BlockProperty.EMPTY)
					getPoint().translate(-speed, 0);
				else
					setJumping(true);
			} 
			else if (targetX - getX() < speed){
				return;
			}
			else {
				action = Direction.FACE_RIGHT;
				setRunning(true);
				if (getStage().getBlockAt(getPoint().x + getSpeed() + 32,
						getPoint().y + 16).getBlockProperty() == BlockProperty.EMPTY
						&& getStage().getBlockAt(
								getPoint().x + getSpeed() + 32,
								getPoint().y + 26).getBlockProperty() == BlockProperty.EMPTY
						&& getStage().getBlockAt(
								getPoint().x + getSpeed() + 32,
								getPoint().y + 8).getBlockProperty() == BlockProperty.EMPTY)
					getPoint().translate(speed, 0);
				else
					setJumping(true);
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
