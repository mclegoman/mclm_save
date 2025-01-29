/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.tick;

import com.mclegoman.save.api.event.EventRegistry;
import com.mclegoman.save.api.event.Eventable;
import com.mclegoman.save.api.event.EventType;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.rtu.util.LogType;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public final class TickEvents {
	public static class StartClient extends EventType {
	}
	public static class EndClient extends EventType {
	}
	protected static class WorldTickEventType {
		public static final EventRegistry<Tickable.World> tickableRegistry = new EventRegistry<>();
		public static void register(String identifier, Tickable.World tickable) {
			WorldTickEventType.tickableRegistry.register(identifier, tickable);
		}
		public static final EventRegistry<Eventable> eventableRegistry = new EventRegistry<>();
		public static void register(String identifier, Eventable eventable) {
			eventableRegistry.register(identifier, eventable);
		}
	}
	public static class StartWorld extends WorldTickEventType {
	}
	public static class EndWorld extends WorldTickEventType {
	}
	public static void register(Tick tick, String identifier, Eventable eventable) {
		if (tick.equals(Tick.START)) StartClient.register(identifier, eventable);
		else if (tick.equals(Tick.END)) EndClient.register(identifier, eventable);
		else if (tick.equals(Tick.START_WORLD)) StartWorld.register(identifier, eventable);
		else if (tick.equals(Tick.END_WORLD)) EndWorld.register(identifier, eventable);
	}
	public static void register(Tick tick, String identifier, Tickable.World tickable) {
		if (tick.equals(Tick.START_WORLD)) StartWorld.register(identifier, tickable);
		else if (tick.equals(Tick.END_WORLD)) EndWorld.register(identifier, tickable);
		else Data.getVersion().sendToLog(LogType.WARN, "Failed to register \"" + identifier + "\" tick event: \"" + tick.name() + "\"  is not a valid type for Tickable.World");
	}
	public enum Tick {
		START,
		END,
		START_WORLD,
		END_WORLD
	}
}
