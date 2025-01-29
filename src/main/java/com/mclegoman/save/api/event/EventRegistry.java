/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event;

import java.util.HashMap;
import java.util.Map;

public final class EventRegistry<T> {
	private final Map<String, T> registry = new HashMap<>();
	public EventRegistry() {
	}
	public void register(String identifier, T able) {
		registry.put(identifier, able);
	}
	public Map<String, T> getRegistry() {
		return this.registry;
	}
}
