/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.tick;

import com.mclegoman.save.api.event.Eventable;
import com.mclegoman.save.api.event.EventType;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public final class TickEvents {
	public static class TickEvent {
		public static EventType eventType;
		public TickEvent() {
			 eventType = new EventType();
		}
	}
	public static class Start extends TickEvent {
		public Start() {
			super();
		}
	}
	public static class End extends TickEvent {
		public End() {
			super();
		}
	}
	public static void register(Tick tick, String identifier, Eventable eventable) {
		if (tick.equals(Tick.START)) Start.eventType.register(identifier, eventable);
		else if (tick.equals(Tick.END)) End.eventType.register(identifier, eventable);
	}
	public enum Tick {
		START,
		END
	}
}
