/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.render;

import com.mclegoman.save.api.event.EventRegistry;
import com.mclegoman.save.api.event.Eventable;
import org.quiltmc.loader.api.minecraft.ClientOnly;

@ClientOnly
public final class RenderEvents {
	protected static class RenderEventType {
		protected static final EventRegistry<Renderable> renderableRegistry = new EventRegistry<>();
		protected static final EventRegistry<Eventable> eventableRegistry = new EventRegistry<>();
		public static EventRegistry<Renderable> getRenderableRegistry() {
			return renderableRegistry;
		}
		public static EventRegistry<Eventable> getEventableRegistry() {
			return eventableRegistry;
		}
	}
	public static class AfterGameGui extends RenderEventType {
	}
	public static class End extends RenderEventType {
	}
	public static void register(Render render, String identifier, Renderable renderable) {
		if (render.equals(Render.AFTER_GAME_GUI)) AfterGameGui.renderableRegistry.register(identifier, renderable);
		else if (render.equals(Render.END)) End.renderableRegistry.register(identifier, renderable);
	}
	public static void register(Render render, String identifier, Eventable eventable) {
		if (render.equals(Render.AFTER_GAME_GUI)) AfterGameGui.eventableRegistry.register(identifier, eventable);
		else if (render.equals(Render.END)) End.eventableRegistry.register(identifier, eventable);
	}
	public enum Render {
		AFTER_GAME_GUI,
		END
	}
}
