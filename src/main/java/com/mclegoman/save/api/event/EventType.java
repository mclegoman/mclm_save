/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event;

public class EventType {
	public final EventRegistry<Eventable> eventableRegistry;
	public EventType() {
		eventableRegistry = new EventRegistry<>();
	}
	public void register(String identifier, Eventable eventable) {
		eventableRegistry.register(identifier, eventable);
	}
}
