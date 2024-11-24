/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.api.event;

import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.ArrayList;
import java.util.List;

@ClientOnly
public final class TickEvents {
	private static final List<Runnable> startRegistry = new ArrayList<>();
	private static final List<Runnable> endRegistry = new ArrayList<>();
	public static List<Runnable> getStartRegistry() {
		return startRegistry;
	}
	public static List<Runnable> getEndRegistry() {
		return endRegistry;
	}
	public static void register(Tick tick, Runnable runnable) {
		if (tick.equals(Tick.START)) startRegistry.add(runnable);
		else if (tick.equals(Tick.END)) endRegistry.add(runnable);
	}
	public enum Tick {
		START,
		END
	}
}
