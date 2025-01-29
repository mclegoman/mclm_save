/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.level;

import net.minecraft.world.World;

public class LevelHelper {
	public static boolean shouldLoad;
	public static World world;
	public static void loadWorld(World world) {
		LevelHelper.shouldLoad = true;
		LevelHelper.world = world;
	}
}
