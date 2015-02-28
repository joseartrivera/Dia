package com.josetheprogrammer.dia.mobs;

import java.awt.Point;
import java.util.Random;

import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.particles.Particle;
import com.josetheprogrammer.dia.projectiles.Projectile;
import com.josetheprogrammer.dia.projectiles.ProjectileType;

public class FireBreatherMob extends BasicMob {
	private ProjectileType projectileType;
	private int fireballs;
	private int fireballLimit;
	private boolean onCooldown;
	private Random rando;

	public FireBreatherMob(Stage stage, Point point) {
		super(stage, point);
		setJumpPower(0);
		setAttackRange(100);
		setRange(0);
		projectileType = ProjectileType.FireBall;
		this.setMobName("molten_golem");
		fireballLimit = 60;
		fireballs = 0;
		rando = new Random();
		onCooldown = false;
	}

	@Override
	public void attack() {
		if (fireballs < fireballLimit) {
			if (!onCooldown && fireballs < (fireballLimit / 3)) {

				Projectile proj = Creator.createProjectile(projectileType,
						stage, getX(), getY()+8, -rando.nextInt(6),
						-rando.nextInt(6) - rando.nextInt(4));
				proj.setEnemyProjectile(true);
				stage.addProjectile(proj);
				fireballs++;
			} else if (!onCooldown && fireballs < fireballLimit) {
				onCooldown = true;
			} else if (onCooldown) {
				fireballs--;
				if (fireballs < 0) {
					onCooldown = false;
				}
			}
		}

	}

	public ProjectileType getProjectileType() {
		return projectileType;
	}

	public void setProjectileType(ProjectileType projectileType) {
		this.projectileType = projectileType;
	}

}
