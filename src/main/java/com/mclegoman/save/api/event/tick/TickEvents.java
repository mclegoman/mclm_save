/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.tick;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.HashMap;
import java.util.Map;

@ClientOnly
public final class TickEvents {
	private static final Map<String, Tickable> startRegistry = new HashMap<>();
	private static final Map<String, Tickable> endRegistry = new HashMap<>();
	public static Map<String, Tickable> getStartRegistry() {
		return startRegistry;
	}
	public static Map<String, Tickable> getEndRegistry() {
		return endRegistry;
	}
	public static void register(Tick tick, String identifier, Tickable runnable) {
		if (tick.equals(Tick.START)) startRegistry.put(identifier, runnable);
		else if (tick.equals(Tick.END)) endRegistry.put(identifier, runnable);
	}
	public enum Tick {
		START,
		END
	}
}
