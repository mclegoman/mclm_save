/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.level;

import com.mclegoman.save.data.Data;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.Entity;

public final class World extends Level {
	public World() {
		super(Data.Resources.minecraft.f_7424826);
	}
	protected Entity getEntity(net.minecraft.world.World world, String string) {
		return string.equals("LocalPlayer") ? new InputPlayerEntity(world) : super.getEntity(world, string);
	}
}
