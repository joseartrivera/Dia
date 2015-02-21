package com.josetheprogrammer.dia.mobs;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.projectiles.Projectile;
import com.josetheprogrammer.dia.projectiles.ProjectileType;
import com.josetheprogrammer.dia.view.Resources;

public class FlyingMob extends BasicMob {

	private ImageIcon ghost;
	private boolean isGhost;
	private int attackCooldown;
	private int currentAttackCooldown;
	private ProjectileType projectileType;

	public FlyingMob(Stage stage, Point point) {
		super(stage, point);
		this.setMobName("hollow");
		setJumpPower(1);
		setAttackRange(300);
		projectileType = ProjectileType.FireBall;
		attackCooldown = 40;
	}

	@Override
	public void setMobName(String mobName) {
		super.setMobName(mobName);
		ghost = Resources.getImage(mobName + "_ghost.png");
	}

	@Override
	public Image getSprite() {
		if (isGhost)
			return ghost.getImage();
		return super.getSprite();
	}

	@Override
	protected boolean canMove() {
		setGhost(!super.canMove());
		return true;
	}

	public void setGhost(boolean isGhost) {
		this.isGhost = isGhost;
	}

	public boolean isGhost() {
		return isGhost;
	}

	@Override
	public void applyGravity() {
		if (currentAttackCooldown > 0)
			currentAttackCooldown--;
		return;
	}

	@Override
	protected boolean inAttackRange(int targetX, int targetY) {
		return getX() < targetX + 8 && getX() > targetX - 8
				&& getPoint().distance(getX(), targetY) < getAttackRange()
				&& !isTakingDamage();
	}

	@Override
	public void attack() {
		if (currentAttackCooldown < 1) {
			currentAttackCooldown = attackCooldown;
			Projectile proj = Creator.createProjectile(projectileType, stage, getX(), getY(), 0, 2);
			proj.setEnemyProjectile(true);
			proj.setProjectileName("hollow_bomb.gif");
			proj.setColor(Color.MAGENTA);
			stage.addProjectile(proj);
		}

	}

	public ProjectileType getProjectileType() {
		return projectileType;
	}

	public void setProjectileType(ProjectileType projectileType) {
		this.projectileType = projectileType;
	}
}
