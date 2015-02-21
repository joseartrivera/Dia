package com.josetheprogrammer.dia.mobs;

import java.awt.Point;

import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.gameObjects.Stage;

public class CrawlMob extends BasicMob {

	public CrawlMob(Stage stage, Point point) {
		super(stage, point);
		this.setMobName("spider");
		this.setJumpPower(1);
	}

	@Override
	public void applyGravity() {
		// Push mob down if he is in the air and is not jumping
		if (stage.getBlockAt(point.x + 24, point.y + stage.getGravity() + 30)
				.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 6,
						point.y + stage.getGravity() + 30).getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 16,
						point.y + stage.getGravity() + 30).getBlockProperty() == BlockProperty.EMPTY
				&& !jumping) {
			point.translate(0, stage.getGravity());
		}
		// Else make sure we can crawl on a wall
		else if (canClimb()
				&& stage.getBlockAt(point.x + 24, point.y - jumpPower)
						.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 6, point.y - jumpPower)
						.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 16, point.y - jumpPower)
						.getBlockProperty() == BlockProperty.EMPTY && jumping) {
			point.translate(0, -jumpPower);
		} else {
			setJumping(false);
		}
	}

	private boolean canClimb() {
		return getStage().getBlockAt(getPoint().x - getSpeed(),
				getPoint().y + 16).getBlockProperty() != BlockProperty.EMPTY
				|| getStage().getBlockAt(getPoint().x - getSpeed(),
						getPoint().y + 26).getBlockProperty() != BlockProperty.EMPTY
				|| getStage().getBlockAt(getPoint().x - getSpeed(),
						getPoint().y + 8).getBlockProperty() != BlockProperty.EMPTY
				|| getStage().getBlockAt(getPoint().x + getSpeed() + 32,
						getPoint().y + 16).getBlockProperty() != BlockProperty.EMPTY
				|| getStage().getBlockAt(getPoint().x + getSpeed() + 32,
						getPoint().y + 26).getBlockProperty() != BlockProperty.EMPTY
				|| getStage().getBlockAt(getPoint().x + getSpeed() + 32,
						getPoint().y + 8).getBlockProperty() != BlockProperty.EMPTY;
	}
	
	@Override
	public int getY(){
		return super.getY() + 8;
	}
}
