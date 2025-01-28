/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.fun;

import com.mclegoman.save.api.event.tick.TickEvents;
import com.mclegoman.save.config.SaveConfig;
import com.mclegoman.save.data.Data;
import org.lwjgl.input.Keyboard;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class BossMode {
	public static boolean enabled;
	public static void init() {
		TickEvents.register(TickEvents.Tick.START, Data.getVersion().getID() + "_boss_mode", (client) -> {
			if (SaveConfig.instance.allowBossMode.value()) {
				if (Keyboard.getEventKeyState()) BossMode.enabled = (Keyboard.getEventKey() == 48);
				else BossMode.enabled = false;
			} else BossMode.enabled = false;
		});
	}
}
