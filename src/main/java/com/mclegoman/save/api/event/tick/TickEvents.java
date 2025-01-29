/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.tick;

import com.mclegoman.save.api.event.EventType;
import com.mclegoman.save.api.event.Eventable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public final class TickEvents {
	public static class Start extends EventType {
	}
	public static class End extends EventType {
	}
	public static void register(Tick tick, String identifier, Eventable eventable) {
		if (tick.equals(Tick.START)) Start.register(identifier, eventable);
		else if (tick.equals(Tick.END)) End.register(identifier, eventable);
	}
	public enum Tick {
		START,
		END
	}
}
