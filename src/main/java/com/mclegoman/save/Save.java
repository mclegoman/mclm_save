/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save;

import com.mclegoman.save.api.entrypoint.SaveModInit;
import com.mclegoman.save.api.event.tick.TickEvents;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.fun.AprilFools;
import com.mclegoman.save.level.LevelFile;
import com.mclegoman.save.level.LevelHelper;
import com.mclegoman.save.rtu.util.Couple;
import com.mclegoman.save.util.StringHelper;
import net.minecraft.client.gui.screen.DeathScreen;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public class Save implements SaveModInit {
	public void init(ModContainer mod) {
		StringHelper.init();
		AprilFools.init();
		TickEvents.register(TickEvents.Tick.START, Data.getVersion().getID(), (client) -> {
			if (LevelFile.shouldProcess) {
				LevelFile.shouldProcess = false;
				LevelFile.dialog.interrupt();
				LevelFile.dialog = null;
				LevelFile.processWorld();
			}
			if (LevelFile.shouldLoad != null) {
				Couple<Boolean, Boolean> loadData = LevelFile.shouldLoad;
				if (loadData.getFirst()) {
					LevelFile.shouldLoad = new Couple<>(false, true);
					LevelFile.loadWorld(loadData.getSecond());
				}
			}
			if (LevelHelper.shouldLoad && client.f_5854988 != null) {
				LevelHelper.shouldLoad = false;
				client.m_8603410(LevelHelper.world);
			}
			if (client.f_0723335 instanceof DeathScreen) {
				if (client.f_6058446.health > 0) {
					client.f_6058446.deathTime = 0;
					client.m_6408915(null);
				}
			}
		});
	}
}