/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event;

import net.minecraft.client.C_5664496;

@FunctionalInterface
public interface Eventable {
    void run(C_5664496 minecraft);
}
