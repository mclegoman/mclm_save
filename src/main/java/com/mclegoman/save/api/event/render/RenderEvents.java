/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.render;

import com.mclegoman.save.api.event.Eventable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

import java.util.HashMap;
import java.util.Map;

@ClientOnly
public final class RenderEvents {
	private static final Map<String, Eventable> endRegistry = new HashMap<>();
	public static Map<String, Eventable> getEndRegistry() {
		return endRegistry;
	}
	public static void register(Render render, String identifier, Eventable runnable) {
		if (render.equals(Render.END)) endRegistry.put(identifier, runnable);
	}
	public enum Render {
		END
	}
}
