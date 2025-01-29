/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.render;

import com.mclegoman.save.api.event.Eventable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public final class RenderEvents {
	protected static class RenderEvent {
		public static RenderEventType eventType = new RenderEventType();
	}
	public static class AfterGameGui extends RenderEvent {
	}
	public static class End extends RenderEvent {
	}
	public static void register(Render render, String identifier, Renderable renderable) {
		if (render.equals(Render.AFTER_GAME_GUI)) AfterGameGui.eventType.register(identifier, renderable);
		else if (render.equals(Render.END)) End.eventType.register(identifier, renderable);
	}
	public static void register(Render render, String identifier, Eventable eventable) {
		if (render.equals(Render.AFTER_GAME_GUI)) AfterGameGui.eventType.register(identifier, eventable);
		else if (render.equals(Render.END)) End.eventType.register(identifier, eventable);
	}
	public enum Render {
		AFTER_GAME_GUI,
		END
	}
}
