package com.josetheprogrammer.dia.mobs;

import java.awt.Point;

import com.josetheprogrammer.dia.blocks.BlockProperty;
import com.josetheprogrammer.dia.gameObjects.Direction;
import com.josetheprogrammer.dia.gameObjects.Stage;

public class CrawlMob extends BasicMob {

	public CrawlMob(Stage stage, Point point) {
		super(stage, point);
		this.setMobName("spider");
		this.setJumpPower(1);
	}

	@Override
	public void applyGravity() {
		// Else make sure we can crawl on a wall
		if (stage.getBlockAt(point.x + width-10, point.y - jumpPower)
				.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 6, point.y - jumpPower)
						.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + width/2, point.y - jumpPower)
						.getBlockProperty() == BlockProperty.EMPTY && isJumping()) {
			if (canClimb()) {
				point.translate(0, -jumpPower);
			} else {
				setJumping(false);
			}
		} // Push mob down if he is in the air and is not jumping
		if (stage.getBlockAt(point.x + width-10, point.y + stage.getGravity() + 30)
				.getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + 6,
						point.y + stage.getGravity() + height-2).getBlockProperty() == BlockProperty.EMPTY
				&& stage.getBlockAt(point.x + width/2,
						point.y + stage.getGravity() + height-2).getBlockProperty() == BlockProperty.EMPTY
				&& !isJumping()) {
			point.translate(0, stage.getGravity());
		}
	}

	private boolean canClimb() {
		if (getDirection() == Direction.FACE_LEFT)
		return getStage().getBlockAt(getPoint().x - getSpeed(),
				getPoint().y + height/2).getBlockProperty() != BlockProperty.EMPTY
				|| getStage().getBlockAt(getPoint().x - getSpeed(),
						getPoint().y + height).getBlockProperty() != BlockProperty.EMPTY
				|| getStage().getBlockAt(getPoint().x - getSpeed(),
						getPoint().y + height/4).getBlockProperty() != BlockProperty.EMPTY;
		else
			return getStage().getBlockAt(getPoint().x + getSpeed() + width,
					getPoint().y + height/2).getBlockProperty() != BlockProperty.EMPTY
			|| getStage().getBlockAt(getPoint().x + getSpeed() + width,
					getPoint().y + height).getBlockProperty() != BlockProperty.EMPTY
			|| getStage().getBlockAt(getPoint().x + getSpeed() + width,
					getPoint().y + height/4).getBlockProperty() != BlockProperty.EMPTY;
	}

	@Override
	public int getY() {
		return super.getY() + 8;
	}
	
	@Override
	public void setJumping(boolean jumping){
		if (!jumping && canClimb())
				return;
		this.jumping = jumping;
	}
}
