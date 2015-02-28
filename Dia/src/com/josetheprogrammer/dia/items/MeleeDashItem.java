package com.josetheprogrammer.dia.items;

import com.josetheprogrammer.dia.gameObjects.Stage;

public class MeleeDashItem extends MeleeItem{

	public MeleeDashItem(Stage stage) {
		super(stage);
	}
	
	@Override
	public void altUseItem(){
		if (onAltCooldown())
			return;
		player.setBoostDuration(6);
		player.setxBoost(16);
		player.setyBoost(-2);
		super.altUseItem();
	}

}
