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
import net.minecraft.world.World;

public class Execute {
	public static class Render {
		public static void afterGameGui(C_5664496 minecraft, float f) {
			RenderEvents.AfterGameGui.renderableRegistry.getRegistry().forEach((identifier, renderable) -> {
				try {
					minecraft.f_4267957.setupGuiState();
					renderable.run(minecraft, f);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst rendering " + identifier + " at after game gui render, removing from renderable registry:" + error.getLocalizedMessage());
					RenderEvents.AfterGameGui.renderableRegistry.getRegistry().remove(identifier);
				}
			});
			RenderEvents.AfterGameGui.eventableRegistry.getRegistry().forEach((identifier, eventable) -> {
				try {
					minecraft.f_4267957.setupGuiState();
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst rendering " + identifier + " at after game gui render, removing from eventable registry:" + error.getLocalizedMessage());
					RenderEvents.AfterGameGui.eventableRegistry.getRegistry().remove(identifier);
				}
			});
		}
		public static void end(C_5664496 minecraft, float f) {
			RenderEvents.End.renderableRegistry.getRegistry().forEach((identifier, renderable) -> {
				try {
					minecraft.f_4267957.setupGuiState();
					renderable.run(minecraft, f);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst rendering " + identifier + " at end render, removing from renderable registry:" + error.getLocalizedMessage());
					RenderEvents.End.eventableRegistry.getRegistry().remove(identifier);
				}
			});
			RenderEvents.End.eventableRegistry.getRegistry().forEach((identifier, eventable) -> {
				try {
					minecraft.f_4267957.setupGuiState();
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst rendering " + identifier + " at end render, removing from eventable registry:" + error.getLocalizedMessage());
					RenderEvents.End.eventableRegistry.getRegistry().remove(identifier);
				}
			});
		}
	}
	public static class Tick {
		public static void start(C_5664496 minecraft) {
			TickEvents.StartClient.eventableRegistry.getRegistry().forEach((identifier, eventable) -> {
				try {
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifier + " at start tick, removing from eventable registry:" + error.getLocalizedMessage());
					TickEvents.StartClient.eventableRegistry.getRegistry().remove(identifier);
				}
			});
		}
		public static void end(C_5664496 minecraft) {
			TickEvents.EndClient.eventableRegistry.getRegistry().forEach((identifier, eventable) -> {
				try {
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifier + " at end tick, removing from eventable registry:" + error.getLocalizedMessage());
					TickEvents.EndClient.eventableRegistry.getRegistry().remove(identifier);
				}
			});
		}
		public static void startWorld(C_5664496 minecraft, World world) {
			TickEvents.StartWorld.eventableRegistry.getRegistry().forEach((identifier, eventable) -> {
				try {
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifier + " at start world tick, removing from eventable registry:" + error.getLocalizedMessage());
					TickEvents.StartWorld.eventableRegistry.getRegistry().remove(identifier);
				}
			});
			TickEvents.StartWorld.tickableRegistry.getRegistry().forEach((identifier, tickable) -> {
				try {
					tickable.run(minecraft, world);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifier + " at start world tick, removing from eventable registry:" + error.getLocalizedMessage());
					TickEvents.StartWorld.tickableRegistry.getRegistry().remove(identifier);
				}
			});
		}
		public static void endWorld(C_5664496 minecraft, World world) {
			TickEvents.EndWorld.eventableRegistry.getRegistry().forEach((identifier, eventable) -> {
				try {
					eventable.run(minecraft);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifier + " at end world tick, removing from eventable registry:" + error.getLocalizedMessage());
					TickEvents.EndWorld.eventableRegistry.getRegistry().remove(identifier);
				}
			});
			TickEvents.EndWorld.tickableRegistry.getRegistry().forEach((identifier, tickable) -> {
				try {
					tickable.run(minecraft, world);
				} catch (Exception error) {
					Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifier + " at end world tick, removing from eventable registry:" + error.getLocalizedMessage());
					TickEvents.EndWorld.tickableRegistry.getRegistry().remove(identifier);
				}
			});
		}
	}
}
