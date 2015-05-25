package com.josetheprogrammer.dia.items;

import java.awt.Color;

import com.josetheprogrammer.dia.gameObjects.Creator;
import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Stage;
import com.josetheprogrammer.dia.projectiles.Projectile;
import com.josetheprogrammer.dia.projectiles.ProjectileType;

public class MeleeTossItem extends MeleeItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MeleeTossItem(Stage stage) {
		super(stage);
		this.setItemName("spear.png");
	}

	@Override
	public void altUseItem() {
		if (onAltCooldown())
			return;
		action = ItemAction.USE;
		Projectile proj;
		if (player.getDirection() == Direction.FACE_RIGHT) {
			proj = Creator.createProjectile(ProjectileType.Bullet,
					player.getStage(), player.getX() + 20, player.getY() + 8,
					8, 0);
			proj.setProjectileName("spear_right.png");
		} else {
			proj = Creator.createProjectile(ProjectileType.Bullet,
					player.getStage(), player.getX() - 12, player.getY() + 8,
					-8, 0);
			proj.setProjectileName("spear_left.png");
		}
		
		proj.setColor(Color.GRAY);
		stage.addProjectile(proj);
		super.altUseItem();
	}

}
