/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.render;

import com.mclegoman.save.api.event.EventRegistry;
import com.mclegoman.save.api.event.EventType;

public class RenderEventType extends EventType {
	public final EventRegistry<Renderable> renderableRegistry;
	public RenderEventType() {
		super();
		renderableRegistry = new EventRegistry<>();
	}
	public void register(String identifier, Renderable renderable) {
		renderableRegistry.register(identifier, renderable);
	}
}
