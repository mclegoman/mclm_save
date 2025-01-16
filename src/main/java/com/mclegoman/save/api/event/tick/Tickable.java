/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.api.event.tick;

import net.minecraft.client.C_5664496;

@FunctionalInterface
public interface Tickable {
    void run(C_5664496 minecraft);
}
