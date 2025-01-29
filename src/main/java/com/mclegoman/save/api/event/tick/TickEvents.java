/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.tick;

import com.mclegoman.save.api.event.EventRegistry;
import com.mclegoman.save.api.event.Eventable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public final class TickEvents {
	public static class TickEventType {
		protected static final EventRegistry<Eventable> eventableRegistry = new EventRegistry<>();
		public static EventRegistry<Eventable> getEventableRegistry() {
			return eventableRegistry;
		}
	}
	public static class Start extends TickEventType {
	}
	public static class End extends TickEventType {
	}
	public static void register(Tick tick, String identifier, Eventable eventable) {
		if (tick.equals(Tick.START)) Start.eventableRegistry.register(identifier, eventable);
		else if (tick.equals(Tick.END)) End.eventableRegistry.register(identifier, eventable);
	}
	public enum Tick {
		START,
		END
	}
}
