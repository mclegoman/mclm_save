/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event;

public class EventType {
	public static final EventRegistry<Eventable> eventableRegistry = new EventRegistry<>();
	public static void register(String identifier, Eventable eventable) {
		eventableRegistry.register(identifier, eventable);
	}
}
