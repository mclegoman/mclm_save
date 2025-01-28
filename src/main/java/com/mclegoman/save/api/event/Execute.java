/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event;

import com.mclegoman.save.api.event.render.RenderEvents;
import com.mclegoman.save.api.event.tick.TickEvents;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.rtu.util.LogType;
import net.minecraft.client.C_5664496;

public class Execute {
	public static class Render {
		public static void afterGameGui(C_5664496 minecraft) {
			RenderEvents.getAfterGameGuiRegistry().forEach((identifier, eventable) -> {
				try {
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst rendering " + identifier + " at after game gui render, removing from registry:" + error.getLocalizedMessage());
					RenderEvents.getAfterGameGuiRegistry().remove(identifier);
				}
			});
		}
		public static void end(C_5664496 minecraft) {
			RenderEvents.getEndRegistry().forEach((identifier, eventable) -> {
				try {
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst rendering " + identifier + " at end render, removing from registry:" + error.getLocalizedMessage());
					RenderEvents.getEndRegistry().remove(identifier);
				}
			});
		}
	}
	public static class Tick {
		public static void start(C_5664496 minecraft) {
			TickEvents.getStartRegistry().forEach((identifier, eventable) -> {
				try {
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifier + " at start tick, removing from registry:" + error.getLocalizedMessage());
					TickEvents.getStartRegistry().remove(identifier);
				}
			});
		}
		public static void end(C_5664496 minecraft) {
			TickEvents.getEndRegistry().forEach((identifier, eventable) -> {
				try {
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifier + " at end tick, removing from registry:" + error.getLocalizedMessage());
					TickEvents.getEndRegistry().remove(identifier);
				}
			});
		}
	}
}
